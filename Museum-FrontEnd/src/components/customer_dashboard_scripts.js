import axios from 'axios'
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
    console.log(localStorage)
    AXIOS.get('/customer/'.concat(localStorage.getItem('id')))
      .then(response => {
        this.customer = response.data
        console.log(this.customer)
      })
      .catch(e => {
        this.errorCustomer = e
      })
    setTimeout(() => {
      AXIOS.get('/customer/tickets/'.concat(localStorage.getItem('id')))
        .then(response => {
          this.theirTickets = response.data
          console.log(this.theirTickets)
        })
        .catch(e => {
          this.errorCustomer = e
        })
    },500)
  }

}