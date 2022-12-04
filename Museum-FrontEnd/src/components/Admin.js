import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

class AdminRequestDto {
  constructor(email) {
    this.email = email
  }
}

export default {
  name: 'admins',
  data() {
    return {
      admins: [],
      requestedAdminIndex: 0,
      newAdmin: {},
      errorAdmin: '',
      email: '',
      id: 0,
      response: []
    }
  },
  created: function() {
    AXIOS.get('/admin')
      .then(response => {
        this.admins = response.data
      })
      .catch(e => {
        this.errorAdmin = e
      })
  },
  methods: {
    createAdmin: function(email) {
      AXIOS.post('/admin',new AdminRequestDto(email))
        .then(response => {
          this.admins.push(response.data)
          this.errorAdmins = ''
          this.newAdmin = response.data
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorAdmin = errorMsg
        })
    },
    getAdminByID: function(id) {
      AXIOS.get('/admin/'.concat(id))
        .then(response => {
          if(!this.admins.includes(response.data)) {
            this.admins.push(response.data)
            this.newAdmin = {}
          }
          this.requestedAdminIndex = this.admins.indexOf(response.data)
          this.id = 0
          this.errorAdmin = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorAdmin = errorMsg
        })
    },
    deleteAdmin: function(id) {
      AXIOS.delete('/admin/'.concat(id))
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorAdmin = errorMsg
        })
      this.created()
      this.id = 0
      this.errorAdmin = ''
    }
  }
}
