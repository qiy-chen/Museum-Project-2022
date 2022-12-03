import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
function EmployeeRequestDto(email,shiftIDs) {
  this.email = email
  this.shiftIDs = shiftIDs
}

export default {
  name: 'employees',
  data() {
    return{
      employees: [],
      requestedEmployeeIndex: 0,
      newEmployee: {},
      errorEmployee: '',
      email: '',
      shiftIDs: [],
      response: [],
    }
  },
  created: function() {
    AXIOS.get('/employees')
      .then(response => {
        this.employees = response.data
      })
      .catch(e => {
        this.errorEmployee = e
      })
  },
  methods: {
    createEmployee: function(email,shiftIDs) {
      AXIOS.post('/employee',EmployeeRequestDto(email,shiftIDs))
        .then(response => {
          this.employees.push(response.data)
          this.errorEmployee = ''
          this.newEmployee = {}
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEmployee = errorMsg
        })
    },


  }
}
