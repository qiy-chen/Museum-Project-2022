import * as person from "./Person"
import * as customer from "./Customer"
import * as employee from "./Employee"
import * as admin from "./Admin"
import customer_dashboard_scripts from "./customer_dashboard_scripts";
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
  components: {
    customer_dashboard_scripts
  },
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
      if(email === '') {
        this.foundPerson = []
        this.errorPerson = 'Wrong Email!'
        return
      }
      AXIOS.get('/person/'.concat(email))
        .then(response => {
          this.foundPerson = response.data
          localStorage.setItem('userEmail',email)
          this.checkPersonIsAdmin(email,password)
          this.checkPersonIsEmployee(email,password)
          this.checkPersonIsCustomer(email,password)
          this.password = ''
          this.email = ''
          this.errorPerson = ''
        })
        .catch(e => {
          this.foundPerson = []
          this.errorPerson = 'Wrong Email!'
        })



    },
    getPersonByEmail: function (email) {
    },
    checkPersonIsCustomer: function(email, password) {
      setTimeout(() => {if(this.foundPerson.password === password) {
      this.foundPerson.personRoleIds.forEach(element => {
        AXIOS.get('/customer/'.concat(element))
          .then(response => {
            this.errorPerson = ''
            localStorage.setItem('id',element.toString())
            Router.push({name: 'customer_dashboard'})
          })
          .catch(e => {
          })
      })
    } else {
        this.errorPerson = 'Wrong Password!'
      }},500)
    },
    checkPersonIsEmployee: function(email, password) {
      let result = false
      setTimeout(() => {if(this.foundPerson.password === password) {
        this.foundPerson.personRoleIds.forEach(element => {
          AXIOS.get('/employee/'.concat(element))
            .then(response => {
              this.errorPerson = ''
              localStorage.setItem('id',element.toString())
              Router.push({name: 'employeeDashboard'})
              result = true
            })
            .catch(e => {
            })
        })
      } else {
        this.errorPerson = 'Wrong Password!'
      }},500)
      return result
    },
    checkPersonIsAdmin: function(email, password) {
      setTimeout(() => {if(this.foundPerson.password === password) {
        this.foundPerson.personRoleIds.forEach(element => {
          AXIOS.get('/admin/'.concat(element))
            .then(response => {
              this.errorPerson = ''
              localStorage.setItem('id',element.toString())
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
