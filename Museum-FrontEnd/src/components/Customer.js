import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

class CustomerRequestDto {
  constructor(email, loanIDs) {
    this.email = email
    this.loanIDs = loanIDs
  }
}

export default {
  name: 'customers',
  data() {
    return{
      customers: [],
      requestedCustomerIndex: 0,
      newCustomer: {},
      errorCustomer: '',
      email: '',
      ticketsForCustomer: [],
      loansForCustomer: [],
      id: 0,
      response: []
    }
  },
  created: function() {
    AXIOS.get('/customer')
      .then(response => {
        this.customers = response.data
      })
      .catch(e => {
        this.errorCustomer = e
      })
  },
  methods: {
    createCustomer: function(email,loanIDs) {
      AXIOS.post('/customer', new CustomerRequestDto(email,loanIDs))
        .then(response => {
          this.customers.push(response.data)
          this.errorCustomer = ''
          this.newCustomer = response.data
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
    },
    getCustomerByID: function(id) {
      AXIOS.get('/customer/'.concat(id))
        .then(response => {
          if(!this.customers.includes(response.data)) {
            this.customers.push(response.data)
            this.newCustomer = {}
          }
          this.requestedCustomerIndex = this.customers.indexOf(response.data)
          this.id = 0
          this.errorCustomer = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
    },
    deleteCustomer: function(id) {
      AXIOS.delete('/customer/'.concat(id))
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
      this.created()
      this.id = 0
      this.errorCustomer = ''
    },
    getTicketsForCustomer: function(id) {
      AXIOS.get('/customer/tickets/'.concat(id))
        .then(response => {
          this.ticketsForCustomer = response.data
          this.id = 0
          this.errorCustomer = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
    },
    getLoansForCustomer: function(id) {
      AXIOS.get('/customer/loans/'.concat(id))
        .then(response => {
          this.loansForCustomer = response.data
          this.id = 0
          this.errorCustomer = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorCustomer = errorMsg
        })
    }
  }
}
