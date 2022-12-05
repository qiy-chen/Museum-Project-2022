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

class DisplayDto {
    constructor(roomNumber,maxArtworks,museumId) {
        this.roomNumber = roomNumber;
        this.maxArtworks = maxArtworks;
        this.museumId = museumId;
    }
  }

export default {
  name: 'manageroom',
  data () {
    return {
	  displays: [],
	  storages: [],
	  storageNb: '',
	  displayNb: '',
    newDisplay: {},
	  maxArt: '',
	  RoomId: '',
	  MuseumId: '',
    errorDisplay: '',
    errorStorage: '',
    storageResponse: '',
    displayResponse: '',
    response: []
    }
  },
  created: function () {
    // Initializing dispplays from backend
    //Load all displays
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
	createDisplayRoom: function (roomNumber,maxArtworks,museumId) {
        AXIOS.post('/display', new DisplayDto(roomNumber,maxArtworks,museumId))
          .then(response => {
            console.log(response.data)
            this.displays.push(response.data)
            this.errorDisplay = ''
            this.newDisplay = response.data
          })
          .catch(e => {
            let errorMsg = e.response.data.message
            console.log(errorMsg)
            this.errorDisplay = errorMsg
          })
      }
  }
}