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
      artwork: [],
      startDate: '',
      endDate: '',
      artworkName: '',
    }
  },
  methods: {
    requestLoan: function(startDate,endDate,artworkName) {

    }
  }
}

