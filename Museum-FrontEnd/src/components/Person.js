import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function PersonRequestDto(email,password,firstName,lastName,museum) {
  this.email = email
  this.password = password
  this.name = firstName.concat(" ").concat(lastName)
  this.museum = museum
}

export default {
  name: 'people',
  data() {
    return {
      people: [],
      requestedPersonIndex: 0,
      newPerson: {},
      errorPerson: '',
      email: '',
      inputMap: {},
      personRoleIds: [],
      response: []
    }
  },
  created: function() {
    AXIOS.get('/person')
      .then(response => {
        this.people = response.data
      })
      .catch(e => {
        this.errorPerson = e
      })
  },
  methods: {
    createPerson: function (email,password,firstName,lastName,museum) {
      AXIOS.post('/person', new PersonRequestDto(email,password,firstName,lastName,museum))
        .then(response => {
          this.people.push(response.data)
          this.errorPerson = ''
          this.newPerson = response.data
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorPerson = errorMsg
        })
    },

    getPersonByEmail: function (email) {
      AXIOS.get('/person/'.concat(email))
        .then(response => {
          if (!this.people.includes(response.data)) {
            this.people.push(response.data)
            this.newPerson = {}
          }
          this.requestedPersonIndex = this.people.indexOf(response.data)
          this.email = ''
          this.errorPerson = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorPerson = errorMsg
        })
    },
    deletePerson: function (email) {
      AXIOS.put('/person/'.concat(email))
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorPerson = errorMsg
        })
      this.created()
      this.email = ''
      this.errorPerson = ''
    },
    changePersonNameAndPassword: function (email, inputMap) {
      AXIOS.put('/person/'.concat(email), inputMap)
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorPerson = errorMsg
        })
      this.created()
      this.email = ''
      this.inputMap = {}
      this.errorPerson = ''
    },
    getAllPersonRoles: function (email) {
      AXIOS.get('/person/person_roles/'.concat(email))
        .then(response => {
          this.personRoleIds = response.data
          this.email = ''
          this.errorPerson = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorPerson = errorMsg
        })
    }
  }
}

