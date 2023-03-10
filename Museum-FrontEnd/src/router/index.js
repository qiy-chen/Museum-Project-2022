import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Register from '@/components/register'
import Login from '@/components/login'
import CustomerDashboard from '@/components/customer_dashboard'
import EmployeeDashboard from '@/components/employeeDashboard'
import BuyTickets from '@/components/buy_tickets'
import RequestLoan from '@/components/request_loan'
import AdminDashboard from '@/components/admin_dashboard'
import ArtworkDashboard from '@/components/artworkDashboard'
import Gallery from '@/components/gallery'
import Schedule from '@/components/schedule'
import Room from '@/components/room_dashboard'
import Ticket from '@/components/ticketDashboard'
import cancelTicket from '@/components/cancel_tickets'

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
      path: '/login/admin_dashboard',
      name: 'admin_dashboard',
      component: AdminDashboard
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
    },
    {
      path: '/employeeDashboard/artworkDashboard',
      name: 'artworkDashboard',
      component: ArtworkDashboard
    },
    {
      path: '/admin_dashboard/ticketDashboard',
      name: 'ticketDashboard',
      component: Ticket
    },
        {
      path: '/customer_dashboard/cancel_tickets',
      name: 'cancelTickets',
      component: cancelTicket
    },
    {
      path: '/customer_dashboard/gallery',
      name: 'gallery',
      component: Gallery
    },
    {
      path: '/admin_dashboard/schedule',
      name: 'schedule',
      component: Schedule
    },
    {
      path: 'login/admin_dashboard/room_dashboard',
      name: 'room_dashboard',
      component: Room
    }
  ]
})
