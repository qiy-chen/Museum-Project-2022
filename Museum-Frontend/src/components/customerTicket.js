import axios from 'axios'
var config = require('../../config')

var backendConfigurer = function(){
  switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
  }
};

//Dtos
function IdRequestDto(id){
	this.id = id
}
function TicketRequestDto(date, price){
	this.date = date
	this.price = price
}

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'customertickets',
  data () {
    return {
      unpurchasedTickets: [],
      customerTickets: [],
      ticketId = '',
      selectedPersonRoleId: '',
      errorTicket: '',
      response: []
    }
  },
  created: function () {
    // Initializing tickets from backend
    //Load all tickets
    errorTicket=''
        //Load all unpurchased tickets
    AXIOS.get('/tickets/buy')
    .then(response => {
      // JSON responses are automatically parsed.
      this.unpurchasedTickets = response.data
    })
    .catch(e => {
      this.errorTicket += e
    })
        //Load all customer's 'tickets
    AXIOS.get('/customers/'.concat(selectedPersonRoleId))
    .then(response => {
      // JSON responses are automatically parsed.
      this.customerTickets = response.data
    })
    .catch(e => {
      this.errorTicket += e
    })
  },
  methods: {
    
    getAllUnpurchasedTickets: function () {
      AXIOS.get('/tickets/buy', {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.allUnpurchasedTickets = response.data
          this.errorTicket = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
    getCustomerTickets: function (personRoleId) {
      AXIOS.get('/customers/'.concat(personRoleId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.customerTickets = response.data
          this.errorTicket = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
    getCustomerTickets: function (customerId,ticketId) {
      AXIOS.post('/customers/'.concat(customerId), {}, {
	      params: {
          id = ticketId,
        }
})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.customerTickets = response.data
          this.errorTicket = ''
          this.ticketId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },

	  cancelTicket: function (customerId,ticketId) {
      AXIOS.delete('/customers/'.concat(customerId), {}, {
	      params: {
          id = ticketId,
        }
})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.allTickets.push(response.data)
          this.errorTicket = ''
          this.ticketId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
  }
}