import * as people from "./Person"
import * as customers from "./Customer";
export default {
  name: 'register_scripts',
  data() {
    return {
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      errorPerson: '',
    }
  },
  created: function() {
    people.default.created()
    customers.default.created()
  },

  methods: {
    createNewPersonAndMakeCustomer: function(email,password,firstName,lastName,museum) {
      if(!email) {
        this.errorPerson += 'Email cannot be blank! '
      }
      if(!password) {
        this.errorPerson += 'Password cannot be blank! '
      }
      if(!firstName) {
        this.errorPerson += 'First name cannot be blank! '
      }
      if(!lastName) {
      this.errorPerson += 'Last name cannot be blank! '
      }
      if(this.errorPerson)return

      people.default.methods.createPerson(email,password,firstName,lastName,museum)
      let loanIDs = []
      setTimeout(() =>customers.default.methods.createCustomer(email,loanIDs),5000)
      this.errorPerson = ''
    },
    getPersonByEmail: function (email) {
      AXIOS.get('/person/'.concat(email))
        .then(response => {
          if(response.data) {
            this.errorPerson = 'Email already exists!'
          }
        })
        .catch(e => {
        })
    },
  }



}
