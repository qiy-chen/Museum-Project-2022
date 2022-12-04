import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Register from '@/components/register'
import Login from '@/components/login'
import CustomerDashboard from '@/components/customer_dashboard'
import EmployeeDashboard from '@/components/employeeDashboard'
import BuyTickets from '@/components/buy_tickets'
import RequestLoan from '@/components/request_loan'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/Hello/register',
      name: 'register',
      component: Register
    },
    {
      path: '/Hello/login',
      name: 'login',
      component: Login
    },
    {
      path: '/login/employeeDashboard',
      name: 'employeeDashboard',
      component: EmployeeDashboard
    },
    {
      path: '/login/customer_dashboard',
      name: 'customer_dashboard',
      component: CustomerDashboard
    },
    {
      path: '/customer_dashboard/buy_tickets',
      name: 'buy_tickets',
      component: BuyTickets
    },
    {
      path: '/customer_dashboard/request_loan',
      name: 'request_loan',
      component: RequestLoan
    }
  ]
})
