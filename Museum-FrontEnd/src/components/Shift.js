import * as people from "./Person"
import * as employees from "./Employee";
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


class ShiftRequestDto {
  constructor(startTime, endTime, museumId) {
    this.startTime = startTime
    this.endTime = endTime
    this.museumId = museumId
  }
}

export default {
  name: 'shifts',
  data() {
    return {
      employees: [],
      people: [],
      shifts: [],
      requestedShiftIndex: 0,
      newShift: [],
      errorShift: '',
      dateMap: {},
      workDayId: 0,
      employeeId: 0,
      startTime: '',
      endTime: '',
      employeeIds: [],
      response: []
    }
  },
  created: function () {
    setTimeout(() =>{
      if (window.location.href.substr(-2) !== '?r') {
    window.location = window.location.href + '?r' ;window.location.reload();
}},500)
    AXIOS.get('/shift')
      .then(response =>{
        this.shifts = response.data
      })
      .catch(e => {
        this.errorShift = e
      })
      AXIOS.get('/employee')
      .then(response => {
        this.employees = response.data
      })
      .catch(e => {
        this.errorEmployee = e
      })
  },
  methods: {
    createShift: function (startDate,endDate) {
      AXIOS.post('/shift', new ShiftRequestDto(startDate, endDate, 69))
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
          this.newShift= response.data
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
      this.workdDayId = 0
      this.errorShift = ''
    },
    addEmployeeToShift: function (workDayId,employeeId) {
      AXIOS.post('/shift/employees/'.concat(workDayId),{employeeId: employeeId})
        .then(response => {
          this.newShift = response.data
          setTimeout(this.created(),5000)
          this.errorShift = ''
          this.workDayId = 0
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
      setTimeout(this.created(),5000)
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
          this.shifts = response.data
          this.errorShift = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    setShift: function(startTime,endTime,employeeId) {
      let start = startTime.toString().concat(' 08:00')
      let end = endTime.toString().concat(' 17:00')
      this.createShift(start,end)
      console.log(this.shifts)
      console.log(start)
      console.log(end)
      let workDayId = this.shifts[this.shifts.length-1].workDayId
      console.log(workDayId)
      console.log(employeeId)
      setTimeout(() => {this.addEmployeeToShift(workDayId, employeeId)}, 5000)
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
