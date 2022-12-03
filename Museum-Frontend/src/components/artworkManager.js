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
      artworkId: '',
      errorArtwork: '',
      artworkResponse: '',
      roomId: '',
    }
  },

  created() {
    AXIOS.get('/artwork')
    .then(response => {
      console.log(response)
      this.artworksOnDisplay = response.data
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorArtwork = errorMsg
	})
    
  },

  methods: {

	createArtwork: function (name, roomId, museumId) {
        AXIOS.post('/artwork/', {}, {
        params: {
		  name = name,
		  
        }})
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


      
    }

}