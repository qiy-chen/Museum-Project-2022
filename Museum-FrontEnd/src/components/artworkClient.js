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
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
  name: 'ArtClient',
  data() {
    return {
      artworksOnDisplay: [],
      artworksInRoom: [],
      artworkId: 0,
      errorArtwork: '',
      artworkResponse: '',
      roomId: 0,
      artworkName: '',
    }
  },

  created() {
    setTimeout(() =>{
      if (window.location.href.substr(-2) !== '?r') {
    window.location = window.location.href + '?r' ;window.location.reload();
}},500)
    AXIOS.get('/display/artworks')
    .then(response => {
      console.log(response.data)
      this.artworksOnDisplay = response.data
      this.errorArtwork = ''
      this.artworkName = response.data.artworkName
    })
    .catch(e => {
      var errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorArtwork = errorMsg
    })
    
  },

  methods: {
    getArtworkOnDisplay: function () {
        AXIOS.get('/display/artworks', {}, {})
          .then(response => {
            console.log(response.data)
            this.artworksOnDisplay = response.data
            this.errorArtwork = ''
            this.artworkName = response.data.artworkName
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




