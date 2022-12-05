import axios from 'axios'
import Router from '@/router/index.js'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

class TicketRequestDto {
  constructor(ticketDate,price) {
    this.ticketDate = ticketDate
    this.price = price
  }
}

export default {
  name: 'buy_tickets_scripts',
  data() {
    return {
      tickets: [],
      ticketDate: '',
      price: 0,
      errorTickets: ''
    }
  },
  methods: {
    purchaseTicket: function(ticketDate,price) {
      AXIOS.post('/customers/'.concat(localStorage.getItem('id')),new TicketRequestDto(ticketDate,price))
        .then(response => {
          Router.push({name: 'customer_dashboard'})
        })
        .catch(e => {

        })
    }
  }

}
