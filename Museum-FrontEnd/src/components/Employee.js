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
      id: 0,
      employeeShifts: [],
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
      AXIOS.post('/employee',new EmployeeRequestDto(email,shiftIDs))
        .then(response => {
          this.employees.push(response.data)
          this.errorEmployee = ''
          this.newEmployee = response.data
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEmployee = errorMsg
        })
    },
    getEmployeeByID: function(id) {
      AXIOS.get('/employee/'.concat(id))
        .then(response => {
          if(!this.employees.includes(response.data)) {
            this.employees.push(response.data)
            this.newEmployee = {}
          }
          this.requestedShiftIndex = this.employees.indexOf(response.data)
          this.id = 0
          this.errorEmployee = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEmployee = errorMsg
        })
    },
    fireEmployee: function(id) {
      AXIOS.delete('/employee/'.concat(id))
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEmployee = errorMsg
        })
        this.created()
        this.id = 0
        this.errorEmployee = ''
    },
    getShiftsForEmployee: function(id) {
      AXIOS.get('/employee/shifts/'.concat(id))
        .then(response => {
          this.employeeShifts = response.data
          this.id = 0
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEmployee = errorMsg
        })
    }


  }
}
