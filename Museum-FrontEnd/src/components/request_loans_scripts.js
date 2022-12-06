import axios from 'axios'
import Router from '@/router/index.js'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

class LoanRequestDto {
  constructor(LoanStatusAsNumber,startDate,endDate,numOfDays,rentalFee,artworkId,customerId,museumId) {
    this.LoanStatusAsNumber = LoanStatusAsNumber
    this.startDate = startDate
    this.endDate = endDate
    this.numOfDays = numOfDays
    this.rentalFee = rentalFee
    this.artworkId = artworkId
    this.customerId = customerId
    this.museumId = museumId
  }
}
export default {
  name: 'request_loans_scripts',
  data() {
    return {
      loans: [],
      artworks: [],
      startDate: '',
      endDate: '',
      artworkName: '',
    }
  },
  
    created () {
    // Initializing loans from backend
    //Load all loans
    AXIOS.get('/loans')
    .then(response => {
      // JSON responses are automatically parsed.
      this.loans = response.data
    })
    .catch(e => {
      this.errorLoan = e.response.data.message
    })
    
    AXIOS.get('/artwork')
    .then(response => {
      this.artworks = response.data
    })
    .catch(e => {
      let errorMsg = e.response.data.message
      console.log(errorMsg)
      this.errorArtwork = errorMsg
	})
  },
  methods: {
    requestLoan: function (startDate, endDate,numOfDays, artworkId) {
		 AXIOS.post('/loans', new LoanRequestDto(2,startDate,endDate,numOfDays,9.99*1.15, artworkId, localStorage.getItem('id')), 69).then(response => {
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
    }
}
