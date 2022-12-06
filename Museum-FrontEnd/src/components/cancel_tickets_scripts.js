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
      customerTickets: [],
      selectedTicket: '',
      ticketDate: '',
      errorTickets: []
    }
  },
  created: function () {
    //Load all customer tickets
    AXIOS.get('/customer/tickets/'.concat(localStorage.getItem('id')))
    .then(response => {
      // JSON responses are automatically parsed.
      this.customerTickets = response.data
    })
    .catch(e => {
      this.errorTicket += e
    })
  },

  methods: {
    cancelTicket: function(ticketId) {
      AXIOS.delete('/customers/'.concat(localStorage.getItem('id')),new IdRequestDto(ticketId))
        .then(response => {
          window.location.reload()
        })
        .catch(e => {
          this.errorTickets = e.response.message
        })
    }
  }

}
