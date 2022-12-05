import axios from 'axios'
import login_scripts from "./login_scripts";
import inject from 'vue'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


export default {
  name: 'customer_dashboard_scripts',
  data() {
    return {
      email: '',
      theirTickets: [],
      customer: [],
      errorCustomer: ''

    }
  },
  created: function() {
    AXIOS.get('/customer/'.concat(localStorage.getItem('userEmail')))
      .then(response => {
        this.customer = response.data
        console.log(localStorage.getItem('userEmail'))
      })
      .catch(e => {
        this.errorCustomer = e
      })
    setTimeout(() => {
      AXIOS.get('/customer/tickets/'.concat(this.customer.id))
        .then(response => {
          this.theirTickets = response.data
        })
        .catch(e => {
          this.errorCustomer = e
        })
    },500)
  }

}
