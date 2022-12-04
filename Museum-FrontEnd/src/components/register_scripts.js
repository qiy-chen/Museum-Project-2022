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
      errorPerson: people.default.data().errorPerson,
      errorCustomer: customers.default.data().errorCustomer
    }
  },
  created: function() {
    people.default.created()
    customers.default.created()
  },

  methods: {
    createNewPersonAndMakeCustomer: function(email,password,firstName,lastName,museum) {
      people.default.methods.createPerson(email,password,firstName,lastName,museum)
      let loanIDs = []
      setTimeout(() =>customers.default.methods.createCustomer(email,loanIDs),5000)
    }
  }

}
