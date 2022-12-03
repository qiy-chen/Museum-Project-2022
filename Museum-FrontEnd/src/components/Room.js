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
  name: 'manageroom',
  data () {
    return {
	  displays: [],
	  storages: [],
	  storageNb: '',
	  displayNb: '',
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
	
		createStorage: function (storageNB,RoomId, MuseumId) {
      AXIOS.post('/storage/', {}, {
		  params: {
          roomNumber = storageNB,
          roomId = RoomId,
          museumId = MuseumId
        }
})
        .then(response => {
        // JSON responses are automatically parsed.
          this.storages.push(response.data)
          this.errorStorage = ''
          this.storageNB = ''
          this.RoomId = ''
          this.MuseumId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStorage = errorMsg
        })
    },
    
   createDisplay: function (displayNB,RoomId, MaxArt, MuseumId) {
      AXIOS.post('/display/', {}, {
		  params: {
          roomNumber = displayNB,
          roomId = RoomId,
          maxArtworks = MaxArt,
          museumId = MuseumId
        }
})
        .then(response => {
        // JSON responses are automatically parsed.
          this.displays.push(response.data)
          this.errorDisplay = ''
          this.displayNB = ''
          this.MaxArt = ''
          this.RoomId = ''
          this.MuseumId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorDisplay = errorMsg
        })
    },
	
	getStorage: function (RoomId) {
      AXIOS.get('/storage/'.concat(RoomId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          this.storageResponse = response.data
          this.errorStorage = ''
          this.RoomId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStorage = errorMsg
        })
    },
    
    	getDisplay: function (RoomId) {
      AXIOS.get('/display/'.concat(RoomId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.displayResponse = response.data
          this.errorDisplay = ''
          this.RoomId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorDisplay = errorMsg
        })
    },
    
      deleteDisplay: function (RoomId) {
      AXIOS.delete('/display/'.concat(RoomId), {}, {})
        .then(response => {
       		getAllDisplay()
       		this.displayResponse = response.data
          this.errorDisplay = ''
          this.RoomId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorDisplay = errorMsg
        })
    },
    
          deleteStorage: function (storageId) {
      AXIOS.delete('/storage/'.concat(storageId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          getAllStorage()
          this.storageResponse = response.data
          this.errorStorage = ''
          this.storageId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStorage = errorMsg
        })
    },
    
    getAllStorage:  function () {
      AXIOS.get('/storage', {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.storages = response.data
          this.errorStorage = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStorage = errorMsg
        })
    },
        getAllDisplay:  function () {
      AXIOS.get('/display', {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.displays = response.data
          this.errorDisplay = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorDisplay = errorMsg
        })
    },
    
  }
}