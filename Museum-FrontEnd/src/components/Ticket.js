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
function TicketRequestDto(ticketDate, price){
	this.ticketDate = ticketDate
	this.price = price
}

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'managetickets',
  data () {
    return {
      allTickets: [],
      unpurchasedTickets: [],
      customerTickets: [],
      ticketDate: '',
      price:'',
      ticketId: '',
      errorTicket: '',
      nb: '',
      selectedPersonRoleId: '',
      selectedTicket:'',
      response: []
    }
  },
  created: function () {
    // Initializing tickets from backend
    //Load all tickets
    this.errorTicket=''
    AXIOS.get('/tickets')
    .then(response => {
      // JSON responses are automatically parsed.
      this.allTickets = response.data
    })
    .catch(e => {
      this.errorTicket += e
    })
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
	
	getTicket: function (ticketId) {
      AXIOS.get('/tickets/'.concat(ticketId))
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.selectedTicket = response.data
          this.errorTicket = ''
          this.ticketId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
   	getAllTickets: function () {
      AXIOS.get('/tickets', {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.allTickets = response.data
          this.errorTicket = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
    getAllUnpurchasedTickets: function () {
      AXIOS.get('/tickets/buy', {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.unpurchasedTickets = response.data
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
          this.personRoleId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
	
    createTicket: function (ticketDate, price, nb) {
	for (let i = 0; i<nb; i++){
		      AXIOS.post('/tickets',new TicketRequestDto(ticketDate,price))
                  .then(response => {
        // JSON responses are automatically parsed.
          this.allTickets.push(response.data)
          this.unpurchasedTickets.push(response.data)
          this.errorTicket = ''
          this.newTicketDate = ''
          this.newTicketPrice = ''
          this.nb = 0
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
	}
   },

    updateTicket: function (ticketDate,price,ticketId) {
      AXIOS.put('/tickets/'.concat(ticketId), new TicketRequestDto(ticketDate,price))
        .then(response => {
        // JSON responses are automatically parsed.
        //Update values
         window.location.reload();
			this.response = response.data
          this.errorTicket = ''
          this.newTicketDate = ''
          this.newTicketPrice = ''
          this.ticketId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
      deleteTicket: function (ticketId) {
      AXIOS.delete('/tickets/'.concat(ticketId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //Update values
         window.location.reload();
         this.response = response.data
          this.errorTicket = ''
          ticketId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
        purchaseTickets: function (selectedPersonRoleId,ticketId) {
      AXIOS.post('/customers/'.concat(selectedPersonRoleId), {}, {
	      params: {
          id: ticketId,
        }
})
        .then(response => {
        // JSON responses are automatically parsed.
          if (!this.customerTickets.includes(response.data)) this.customerTickets.push(response.data)
          this.errorTicket = ''
          this.ticketId = ''
          this.selectedPersonRoleId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },

	  cancelTicket: function (selectedPersonRoleId,ticketId) {
      AXIOS.delete('/customers/'.concat(selectedPersonRoleId), {}, {
	      params: {
          id: ticketId,
        }
})
        .then(response => {
        // JSON responses are automatically parsed.
        //Update values
         getAllTickets()
         getAllUnpurchasedTickets()
         getCustomerTickets()
         this.response = response.data
          this.errorTicket = ''
          this.ticketId = ''
          this.selectedPersonRoleId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTicket = errorMsg
        })
    },
    
  }
}