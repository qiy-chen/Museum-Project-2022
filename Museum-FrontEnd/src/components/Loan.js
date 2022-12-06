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


var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'manageloans',
  data () {
    return {
      loans: [],
    LoanStatusAsNumber: '',
    startDate: '',
    endDate: '',
    numOfDays: '',
    rentalFee: '',
    artworkId: '',
    customerId: '',
    museumId: '',
    loanId: '',
      selectedLoan:'',
      errorLoan: '',
      response: []
    }
  },
  created: function () {
    // Initializing tickets from backend
    //Load all loans
    errorLoan=''
    AXIOS.get('/loans')
    .then(response => {
      // JSON responses are automatically parsed.
      this.loans = response.data
    })
    .catch(e => {
      this.errorLoan += e
    })
  },
  methods: {
	
	getLoan: function (loanId) {
      AXIOS.get('/loans/'.concat(loanId))
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.selectedLoan = response.data
          this.errorLoan = ''
          this.loanId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
    },
   	getAllLoans: function () {
      AXIOS.get('/loans', {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //this.ticket.push(response.data)
          this.loans = response.data
          this.errorLoan = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
    },
	
    createLoan: function (LoanStatusAsNumber, startDate, endDate,numOfDays, rentalFee, artworkId, customerId, museumId ) {
		 AXIOS.post('/loans', {}, {
        params: {
          LoanStatusAsNumber: LoanStatusAsNumber,
    startDate: startDate,
    endDate: endDate,
    numOfDays: numOfDays,
    rentalFee: rentalFee,
    artworkId: artworkId,
    customerId: customerId,
    museumId: museumId,
        }}).then(response => {
        // JSON responses are automatically parsed.
          this.loans.push(response.data)
          this.LoanStatusAsNumber = ''
    	this.startDate =  ''
    	this.endDate =  ''
    	this.numOfDays =  ''
    	this.rentalFee =  ''
    	this.artworkId =  ''
    	this.customerId =  ''
    	this.museumId =  ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
   },
    
      deleteLoan: function (loanId) {
      AXIOS.post('/loans/delete/'.concat(loanId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //Update values
          getAllLoans()
         this.response = response.data
          this.errorLoan = ''
          loanId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
    },
    
      denyLoan: function (loanId) {
      AXIOS.post('/loans/deny/'.concat(loanId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //Update values
          getAllLoans()
         this.response = response.data
          this.errorLoan = ''
          loanId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
    },
    
          approveLoan: function (loanId) {
      AXIOS.post('/loans/approve/'.concat(loanId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //Update values
          getAllLoans()
         this.response = response.data
          this.errorLoan = ''
          loanId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
    },
    
          returnLoan: function (loanId) {
      AXIOS.post('/loans/return/'.concat(loanId), {}, {})
        .then(response => {
        // JSON responses are automatically parsed.
          //Update values
          getAllLoans()
         this.response = response.data
          this.errorLoan = ''
          loanId = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLoan = errorMsg
        })
    },
    
  }
}