import * as people from "./Person"
import * as employees from "./Employee";
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
  name: 'admin_scripts',
  data() {
    return {
      employees: [],
      people: [],
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      errorPerson: '',
    }
  },
  created: function() {
  
    AXIOS.get('/employee')
      .then(response => {
        this.employees = response.data
      })
      .catch(e => {
        this.errorEmployee = e
      })
  },

  methods: {
    createNewPersonAndMakeEmployee: function(email,password,firstName,lastName,museum) {
      if(!email) {
        this.errorPerson += 'Email cannot be blank! '
      }
      if(!password) {
        this.errorPerson += 'Password cannot be blank! '
      }
      if(!firstName) {
        this.errorPerson += 'First name cannot be blank! '
      }
      if(!lastName) {
      this.errorPerson += 'Last name cannot be blank! '
      }
      if(this.errorPerson)return

      people.default.methods.createPerson(email,password,firstName,lastName,museum)
      let shiftIDs = []
      setTimeout(() => employees.default.methods.createEmployee(email,shiftIDs),5000)
      this.errorPerson = ''
    },
    getPersonByEmail: function (email) {
      AXIOS.get('/person/'.concat(email))
        .then(response => {
          this.foundPerson = response.data
          this.email = ''
          this.password = ''
          this.errorPerson = ''
        })
        .catch(e => {
          this.foundPerson = []
          this.errorPerson = 'Wrong Email!'
        })
    },
  }

}
