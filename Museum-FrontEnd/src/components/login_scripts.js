import * as person from "./Person"
import * as customer from "./Customer"
import * as employee from "./Employee"
import * as admin from "./Admin"
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
  name: 'login_scripts',
  data() {
    return {
      email: '',
      password: '',
      foundPerson: [],
      errorPerson: ''
    }
  },
  created: function() {
    person.default.created()
    customer.default.created()
    employee.default.created()
    admin.default.created()
  },
  methods: {
    loginToRightRole: function(email,password) {
      this.getPersonByEmail(email)
      this.checkPersonIsAdmin(email,password)
      this.checkPersonIsEmployee(email,password)
      this.checkPersonIsCustomer(email,password)
    },
    getPersonByEmail: function (email) {
      if(email === '') {
        this.foundPerson = []
        this.errorPerson = 'Wrong Email!'
        return
      }
      AXIOS.get('/person/'.concat(email))
        .then(response => {
          this.foundPerson = response.data
          this.email = ''
          this.password = ''
          this.errorPerson = ''
          console.log(response.data)
        })
        .catch(e => {
          this.foundPerson = []
          this.errorPerson = 'Wrong Email!'
        })
    },
    checkPersonIsCustomer: function(email, password) {
      setTimeout(() => {if(this.foundPerson.password === password) {
      this.foundPerson.personRoleIds.forEach(element => {
        AXIOS.get('/customer/'.concat(element))
          .then(response => {
            this.errorPerson = ''
            Router.push({name: 'customer_dashboard'})
          })
          .catch(e => {
            console.log(e.response.message)
          })
      })
    } else {
        this.errorPerson = 'Wrong Password!'
      }},500)
      console.log(this.errorPerson)
      },
    checkPersonIsEmployee: function(email, password) {
      setTimeout(() => {if(this.foundPerson.password === password) {
        this.foundPerson.personRoleIds.forEach(element => {
          AXIOS.get('/employee/'.concat(element))
            .then(response => {
              this.errorPerson = ''
              Router.push({name: 'employeeDashboard'})
            })
            .catch(e => {
            })
        })
      } else {
        this.errorPerson = 'Wrong Password!'
      }},500)
    },
    checkPersonIsAdmin: function(email, password) {
      setTimeout(() => {if(this.foundPerson.password === password) {
        this.foundPerson.personRoleIds.forEach(element => {
          AXIOS.get('/admin/'.concat(element))
            .then(response => {
              this.errorPerson = ''
              Router.push({name: 'admin_dashboard'})
            })
            .catch(e => {
            })
        })
      }else {
        this.errorPerson = 'Wrong Password!'
      }},500)
    }
  }

}
