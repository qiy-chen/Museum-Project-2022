import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
function EmployeeDto(employeeId) {
  this.employeeId = employeeId
  let name
}
export default {
  name: 'shift',
  data() {
    return {
      employees: [],
      newEmployee: '',
      errorEmployee: '',
      response: []
    }
  },
  created: function () {
    this.employees = []
  },
  methods: {
    addEmployee: function (employeeId) {
      let employee = new EmployeeDto(employeeId)
      this.employees.push(employee)
      this.newEmployee = ''
    }
  }
}
