import axios from 'axios'
import Employee from './Employee'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


class ShiftRequestDto {
  constructor(startTime, endTime, museum) {
    this.startTime = startTime
    this.endTime = endTime
    this.museum = museum
  }
}

export default {
  name: 'shifts',
  data() {
    return {
      employees: [],
      shifts: [],
      requestedShiftIndex: 0,
      newShift: {},
      errorShift: '',
      dateMap: {},
      workDayId: 0,
      employeeId: 0,
      employeeIds: [],
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
    getAllEmployee: function () {
      AXIOS.get('/employee', {}, {})
        .then(response => {
          console.log(response.data)
          this.employees = response.data
          this.errorEmployee = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEmployee = errorMsg
        })
      },
    createShift: function (startTime,endTime,museum) {
      AXIOS.post('/shift', new ShiftRequestDto(startTime, endTime, museum))
        .then(response => {
          this.shifts.push(response.data)
          this.errorShift = ''
          this.newShift = response.data

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
            this.newShift = {}
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
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
      this.created()
      this.workdDayId = 0
      this.errorShift = ''
    },
    addEmployeeToShift: function (workDayId,employeeId) {
      this.methods.getShiftById(workDayId)
      AXIOS.post('shift/employees/'.concat(workDayId),employeeId)
        .then(response => {
          this.shifts[this.requestedShiftIndex] = response.data
          this.employeeId = 0
          this.requestedShiftIndex = 0
          this.errorShift = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    removeEmployeeFromShift: function(workDayId,employeeId) {
      AXIOS.put('/shift/employees/'.concat(workDayId),employeeId)
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
      this.created()
      this.workDayId = 0
      this.employeeId = 0
      this.errorShift = ''

    },
    getAllShiftEmployees: function(workDayId) {
      AXIOS.get('/shift/employees/'.concat(workDayId))
        .then(response => {
          this.employeeIds = response.data
          this.workDayId = 0
          this.errorShift = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    changeShiftDate: function(workDayId,dateMap) {
      AXIOS.put('/shift/'.concat(workDayId),dateMap)
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
      this.created()
      this.workDayId = 0
      this.dateMap = {}
      this.errorShift = ''
    },
    getAllShift: function () {
      AXIOS.get('/shift', {}, {})
        .then(response => {
          console.log(response)
          this.shifts = response.data
          this.errorShift = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    setShift: function(startDate,endDate,employeeId) {
      workDayId = this.createShift(startDate,endDate,1)
      this.addEmployeeToShift(workDayId, employeeId)
    },

    'changeMonth' (start, end, current) {
    console.log('changeMonth', start.format(), end.format(), current.format())
    },
    'eventClick' (event, jsEvent, pos) {
    console.log('eventClick', event, jsEvent, pos)
    },
    'dayClick' (day, jsEvent) {
    console.log('dayClick', day, jsEvent)
    },
    'moreClick' (day, events, jsEvent) {
    console.log('moreCLick', day, events, jsEvent)
    }
  },
  components : {
      'full-calendar': require('vue-fullcalendar')
  }
}
