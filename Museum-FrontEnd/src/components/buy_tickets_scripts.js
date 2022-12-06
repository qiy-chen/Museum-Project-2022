import axios from 'axios'
import Router from '@/router/index.js'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

//Dtos
function IdRequestDto(id){
	this.id = id
}
function TicketRequestDto(ticketDate, price){
	this.ticketDate = ticketDate
	this.price = price
}

export default {
  name: 'buy_tickets_scripts',
  data() {
    return {
      unpurchasedTickets: [],
      ticketDate: '',
      errorTickets: ''
    }
  },
  
   created: function () {
    //Load all unpurchased tickets
    AXIOS.get('/tickets/buy')
    .then(response => {
      // JSON responses are automatically parsed.
      this.unpurchasedTickets = response.data
    })
    .catch(e => {
      this.errorTickets = e.response.message
    })
  },
  methods: {
    purchaseTicket: function(ticketId) {
      AXIOS.post('/customers/'.concat(localStorage.getItem('id')),new IdRequestDto(ticketId))
        .then(response => {
          Router.push({name: 'customer_dashboard'})
        })
        .catch(e => {
          this.errorTickets = e.response.message
        })
    }
  }

}
