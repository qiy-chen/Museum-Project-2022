import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ShiftResponseDto(startTime,endTime,workDayId,museum,employees) {
  this.startTime = startTime
  this.endTime = endTime
  this.workDayId = workDayId
  this.museum = museum
  this.employees = employees
}

function ShiftRequestDto(startTime,endTime,workDayId,museum) {
  this.startTime = startTime
  this.endTime = endTime
  this.workDayId = workDayId
  this.museum = museum
}

export default {
  name: "shifts",
  data() {
    return {
      shifts: [],
      requestedShiftIndex: 0,
      newShift: '',
      errorShift: '',
      startTime: '',
      endTime: '',
      workDayId: 0,
      museum: 0,
      employeeId: 0,
      response: []
    }
  },
  created: function () {
    AXIOS.get('/shift')
      .then(response =>{
        this.shifts = response.data
      })
      .catch(e => {
        this.errorShift = e
      })
  },
  methods: {
    createShift: function (startTime,endTime,workDayId,museum) {
      AXIOS.post('/shift', {
        startTime: startTime,
        endTime: endTime,
        workDayId: workDayId,
        museum: museum
      })
        .then(response => {
          this.shifts.push(response.data)
          this.errorShift = ''
          this.newShift = ''
          this.startTime = ''
          this.endTime = ''
          this.workDayId = 0

        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    getShiftById: function (workDayId) {
      AXIOS.get('/shift/'.concat(workDayId))
        .then(response => {
          if (!this.shifts.includes(response.data)) {
            this.shifts.push(response.data)
            this.errorShift = ''
            this.newShift = ''
          }
          this.requestedShiftIndex = this.shifts.indexOf(response.data)
          this.workDayId = 0
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    deleteShift: function (workDayId) {
      AXIOS.put('/shift/'.concat(workDayId))
      AXIOS.get('/shift')
        .then(response => {
          this.shifts = response.data
          this.workDayId = 0
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
          })
    },
    addEmployeeToShift: function (workDayId,employeeId) {
      AXIOS.get('/shift/'.concat(workDayId))
        .then(response => {
          this.requestedShiftIndex = this.shifts.indexOf(response.data)
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
      AXIOS.post('shift/employees/'.concat(workDayId),employeeId)
        .then(response => {
          this.shifts[this.requestedShiftIndex] = response.data
          this.employeeId = 0
          this.requestedShiftIndex = 0
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    }



  }
}
