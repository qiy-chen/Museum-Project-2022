import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function artworkRequestDto(artworkName, roomId, museumId, value, isLoanable){
  this.artworkName = artworkName
  this.roomId = roomId
  this.museumId = museumId
  this.value = value
  this.isLoanable = isLoanable
  this.artworkId
}

class DisplayDto {
  constructor(roomNumber,maxArtworks,museumId) {
      this.roomNumber = roomNumber;
      this.maxArtworks = maxArtworks;
      this.museumId = museumId;
      this.roomId;
      this.numberOfArtworks;
  }
}
class StorageDto {
  constructor(roomNumber,museumId) {
      this.roomNumber = roomNumber;
      this.museumId = museumId;
      this.roomId;
  }
}

export default {
  name: 'artworkManager',
  data() {
    return {

    artworksOnDisplay: [],
	  artworksInRoom: [],
	  artworksInStorage: [],
	  artworks: [],
    artworkId: '',
    errorArtwork: '',
    artworkResponse: '',
    roomId: '', //for artworks,
    value: '',
    isLoanable: '',
    displays: [],
	  storages: [],
	  storageNb: '',
	  displayNb: '',
    newDisplay: {},
    newStorage:{},
	  maxArt: '',
	  RoomId: '', //for rooms
	  MuseumId: '',
    errorDisplay: '',
    errorStorage: '',
    storageResponse: '',
    displayResponse: '',
    response: []
    }
  },

  created() {
    AXIOS.get('/artwork')
    .then(response => {
      this.artworks = response.data
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorArtwork = errorMsg
	})
    AXIOS.get('/display')
    .then(response => {
      // JSON responses are automatically parsed.
      this.displays = response.data
    })
    .catch(e => {
      this.errorDisplay = e
    })
        //Load all storage
    AXIOS.get('/storage')
    .then(response => {
      // JSON responses are automatically parsed.
      this.storages = response.data
    })
    .catch(e => {
      this.errorStorage = e
    })
  },

  methods: {

	createArtwork: function (artworkName, roomId, museumId) {
        AXIOS.post('/artwork/',  new artworkRequestDto(artworkName, roomId, museumId, 12, false), {})
          .then(response => {
            this.artworks.push(reponse.data)
            console.log(artworks)
            this.artworkId = response.data.artworkId
            this.errorArtwork = ''
            this.roomId =''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
    },
    
    updateArtwork: function (artworkId, artworkName, value, isLoanable) {
        AXIOS.put('/artwork/'.concat(artworkId),  new artworkRequestDto(artworkName, 0, 0, value, true), {})
          .then(response => {
            console.log(response.data)
            this.created()
            this.errorArtwork = ''
            this.artworkId = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
    },

    moveArtwork: function (artworkId, roomId, museumId) {
        AXIOS.put('/artwork/room/'.concat(artworkId),  new artworkRequestDto('Help', roomId, museumId, 0.0, false), {})
          .then(response => {
            console.log(response)
            this.created()
            this.errorArtwork = ''
            this.artworkId = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
    },
    
    deleteArtwork: function (artworkId) {
        AXIOS.delete('/artwork/'.concat(artworkId), {}, {})
          .then(response => {
            this.created()
            this.errorArtwork = ''
            this.artworkId = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
	  },

	getAllArtwork: function () {
        AXIOS.get('/artwork/', {}, {})
          .then(response => {
            console.log(response)
            this.artworks = response.data
            this.errorArtwork = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
	  },
	  
	  getArtworkById: function (artworkId) {
        AXIOS.get('/artwork/'.concat(artworkId), {}, {})
          .then(response => {
            console.log(response)
            this.artworkResponse = response.data
            this.errorArtwork = ''
            this.artworkId = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
      },
	

  	getArtworkOnDisplay: function () {
        AXIOS.get('/display/artworks', {}, {})
          .then(response => {
            console.log(response)
            this.artworksOnDisplay = response.data
            this.errorArtwork = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
	  },
	  
	getArtworkInStorage: function () {
        AXIOS.get('/storage/artworks', {}, {})
          .then(response => {
            console.log(response)
            this.artworksInStorage = response.data
            this.errorArtwork = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
      },

  getArtworkByRoom: function (roomId) {
        AXIOS.get('/artwork/room/'.concat(roomId), {}, {})
          .then(response => {
            console.log(response)
            this.artworksInRoom = response.data
            this.errorArtwork = ''
            this.roomId = ''
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
      },
      createDisplayRoom: function (roomNumber,maxArtworks,museumId) {
        AXIOS.post('/display', new DisplayDto(roomNumber,maxArtworks,museumId))
          .then(response => {
            this.displays.push(response.data)
            this.RoomId = response.data.roomId;
            this.numberOfArtworks = response.data.numberOfArtworks;
            console.log(this.displays)
            this.errorDisplay = ''
          })
          .catch(e => {
            let errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorDisplay = errorMsg
          })
      },
      createStorageRoom: function (roomNumber,museumId) {
        AXIOS.post('/storage', new StorageDto(roomNumber,museumId))
          .then(response => {
            this.storages.push(response.data)
            this.RoomId = response.data.roomId;
            this.errorStorage = ''
          })
          .catch(e => {
            let errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorStorage = errorMsg
          })
      }

    }

}