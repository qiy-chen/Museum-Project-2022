import axios from 'axios'
var config = require('../../config')

var backendConfigurer = function(){
  switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
  }
};

function artworkRequestDto(artworkName, roomId, museumId, value, isLoanable){
  this.artworkName = artworkName
  this.roomId = roomId
  this.museumId = museumId
  this.value = value
  this.isLoanable = isLoanable
}

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'ArtManage',
  data() {
    return {

    artworksOnDisplay: [],
	  artworksInRoom: [],
	  artworksInStorage: [],
	  artworks: [],
    artworkId: 0,
    errorArtwork: '',
    artworkResponse: '',
    roomId: 0,
    }
  },

  created() {
    AXIOS.get('/artwork')
    .then(response => {
      console.log(response)
      this.artworks = response.data
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorArtwork = errorMsg
	})
    
  },

  methods: {

	createArtwork: function (artworkName, roomId, museumId) {
        AXIOS.post('/artwork/',  new artworkRequestDto(artworkName, roomId, museumId, 0.0, false), {})
          .then(response => {
            console.log(response)
            this.artworks.push(reponse.data)
            this.errorArtwork = ''
            this.roomId =0
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
    },
    
    updateArtwork: function (artworkId, artworkName, value, isLoanable) {
        AXIOS.put('/artwork/'.concat(artworkId),  new artworkRequestDto(artworkName, 0, 0, value, isLoanable), {})
          .then(response => {
            console.log(response)
            this.created()
            this.errorArtwork = ''
            this.artworkId = 0 
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
    },

    moveArtwork: function (artworkId, roomId) {
        AXIOS.put('/artwork/room/'.concat(artworkId),  new artworkRequestDto('', roomId, 0, 0, false), {})
          .then(response => {
            console.log(response)
            this.created()
            this.errorArtwork = ''
            this.artworkId = 0 
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
            console.log(response)
            this.created()
            this.errorArtwork = ''
            this.artworkId = 0 
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
            this.artworkId = 0
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
            this.roomId = 0
          })
          .catch(e => {
            var errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorArtwork = errorMsg
          })
      },

    }

}