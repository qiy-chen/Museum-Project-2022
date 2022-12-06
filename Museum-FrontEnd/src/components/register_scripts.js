import * as people from "./Person"
import * as customers from "./Customer";
import Router from "@/router/index.js"
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
  name: 'register_scripts',
  data() {
    return {
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      errorPerson: '',
    }
  },
  created: function() {
    
    people.default.created()
    customers.default.created()
  },

  methods: {
    createNewPersonAndMakeCustomer: function(email,password,firstName,lastName,museum) {
      this.errorPerson = ''
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
      this.getPersonByEmail(email)
      if(this.errorPerson)return
      people.default.methods.createPerson(email,password,firstName,lastName,museum)
      let loanIDs = []
      setTimeout(() =>customers.default.methods.createCustomer(email,loanIDs),5000)
      this.errorPerson = ''
      Router.push({name: 'login'})
    },
    getPersonByEmail: function (email) {
      if(!email)return
      AXIOS.get('/person/'.concat(email))
        .then(response => {
          console.log(response.data)
          if(response.data) {
            this.errorPerson = 'Email already exists!'
          }
        })
        .catch(e => {
        })
    },
  }



}
