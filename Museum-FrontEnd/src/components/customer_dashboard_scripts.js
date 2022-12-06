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
      theirLoans: [],
      customer: [],
      errorCustomer: ''

    }
  },
  created: function() {
    setTimeout(() =>{
      if (window.location.href.substr(-2) !== '?r') {
    window.location = window.location.href + '?r' ;window.location.reload();
}},500)
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
    setTimeout(() => {
      AXIOS.get('/customer/loans/'.concat(this.customer.id))
        .then(response => {
          this.theirLoans = response.data
        })
        .catch(e => {
          this.errorCustomer = e
        })
    },500)
  }

}
