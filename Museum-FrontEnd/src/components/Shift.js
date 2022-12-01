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
      newShift: '',
      errorShift: '',
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
    createShift: function (ShiftRequestDto) {
      AXIOS.post('/shift', {
        startTime: ShiftRequestDto.startTime,
        endTime: ShiftRequestDto.endTime,
        workDayId: ShiftRequestDto.workDayId,
        museum: ShiftRequestDto.museum
      })
        .then(response => {
          this.shifts.push(response.data)
          this.errorShift = ''
          this.newShift = ''
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
    },
    getShiftById: function (workDayId) {
      AXIOS.get('/shift/'.concat(workDayId),{})
        .then(response => {
          if(!this.shifts.includes(response.data)) {
            this.shifts.push(response.data)
            this.errorShift = ''
            this.newShift = ''
          }
        })
        .catch(e => {
          let errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorShift = errorMsg
        })
      return response.data
    },




  }
}
