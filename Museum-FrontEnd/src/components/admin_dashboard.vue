<template>
    <div class="admin_dashboard">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Customer Dashboard</title>
            <!-- Flaticon-->
            <link rel="icon" type="image/x-icon" href="src/assets/homePage/museumLogo.png" />
            <!-- Font Awesome icons (free version)-->
            <!-- Google fonts-->
            <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
            <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
            <!-- Core theme CSS (includes Bootstrap)-->
            <link href="src/styles.css" rel="stylesheet" />
        </head>
        <body id="page-top">
            <!-- Navigation-->
            <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
                <div class="container">
                    <a class="navbar-brand" href="#page-top"><img src="src/assets/homePage/MWLogoWide.png" alt="..." /></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                        Menu
                        <i class="fas fa-bars ms-1"></i>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarResponsive">
                        <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                            <li class="nav-item"><a class="nav-link" href="#youremployees">Your Employees</a></li>
                            <li class="nav-item"><a class="nav-link" href="#yourartworks">Your Artworks</a></li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">Account</a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#!">Go to Art Gallery</a></li>
                                    <li><a class="dropdown-item" href="file:///Users/alextsah/Documents/GitHub/project-group-15/Museum-Frontend/purchase_ticket.html">Buy Tickets</a></li>
                                    <li><a class="dropdown-item" href="request_loan.html">Loan Artwork</a></li>
                                    <li><a class="dropdown-item" href="index.html">Log out</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            </body>
            <header class="masthead">
                <div class="container">
                    <div class="masthead-subheading">Welcome Admin</div>
                </div>
            </header>
            <section class="page-section" id="youremployees">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Employees</h2>
                        <h3 class="section-subheading text-muted">These are all your currently registered employees</h3>
                    </div>
                        <div class="card-body">
                        <vuejs-simple-dataTable :options="employeeTableOptions">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Position</th>
                                    <th>Office</th>
                                    <th>Age</th>
                                    <th>Start date</th>
                                    <th>Salary</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>Name</th>
                                    <th>Position</th>
                                    <th>Office</th>
                                    <th>Age</th>
                                    <th>Start date</th>
                                    <th>Salary</th>
                                </tr>
                            </tfoot>
                            <tbody>
                                <tr>
                                    <td>Jonas Alexander</td>
                                    <td>Developer</td>
                                    <td>San Francisco</td>
                                    <td>30</td>
                                    <td>2010/07/14</td>
                                    <td>$86,500</td>
                                </tr>
                                <tr>
                                    <td>Shad Decker</td>
                                    <td>Regional Director</td>
                                    <td>Edinburgh</td>
                                    <td>51</td>
                                    <td>2008/11/13</td>
                                    <td>$183,000</td>
                                </tr>
                                <tr>
                                    <td>Michael Bruce</td>
                                    <td>Javascript Developer</td>
                                    <td>Singapore</td>
                                    <td>29</td>
                                    <td>2011/06/27</td>
                                    <td>$183,000</td>
                                </tr>
                                <tr>
                                    <td>Donna Snider</td>
                                    <td>Customer Support</td>
                                    <td>New York</td>
                                    <td>27</td>
                                    <td>2011/01/25</td>
                                    <td>$112,000</td>
                                </tr>
                            </tbody>
                        </vuejs-simple-dataTable>
                        </div>
                </div> 
            </section>
            <div class="text-center">
                <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'schedule'})">Schedule Assistant</a>
            </div>
            <section class="page-section" id="yourartworks">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Artworks </h2>
                        <h3 class="section-subheading text-muted">Go manage your Artworks</h3>
                        <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'artworkDashboard'})">Manage your Artworks</a>
                    </div>
                </div>
            </section>
            <section class="page-section" id="yourrooms">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Rooms </h2>
                        <h3 class="section-subheading text-muted">Go manage your Rooms</h3>
                        <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'room_dashboard'})">Manage your Rooms</a>
                    </div>
                </div>
            </section>

            <!-- schedule viewer -->
            <section class="page-section" id="scheduleView">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Schedule</h2>
                    <full-calendar class="test-fc" :events="fcEvents" 
                    first-day='1' locale="fr"
                    @changeMonth="changeMonth"
                    @eventClick="eventClick"
                    @dayClick="dayClick"
                    @moreClick="moreClick">
                        <template slot="fc-event-card" scope="p">
                            <p><i class="fa">sadfsd</i> {{ p.event.title }} test</p>
                        </template>
                    </full-calendar>
                    </div>
                </div>
            </section>

            <footer class="footer py-4">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-4 text-lg-start">Copyright &copy; Museum Website 2022</div>
                        <div class="col-lg-4 my-3 my-lg-0"></div>
                        <div class="col-lg-4 text-lg-end">
                            <a class="link-dark text-decoration-none me-3" href="#!">Privacy Policy</a>
                            <a class="link-dark text-decoration-none" href="#!">Terms of Use</a>
                        </div>
                    </div>
                </div>
            </footer>
    </div>
</template>

<script>
    src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" 
    crossorigin="anonymous"
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
    src="src/assets/scripts.js"
    src="https://cdn.startbootstrap.com/sb-forms-latest.js"

    
    let demoEvents = [
        {
            title    : 'Sunny 725-727',
            start    : '2022-12-25',
            end      : '2022-12-27',
            cssClass : 'family'
        },
        {
            title : 'Lunfel 726-727',
            start : '2017-02-26',
            end : '2017-02-27',
            cssClass : ['home', 'work']
        },
        {
            title : 'Lunfel 2/27-2/28',
            start : '2017-02-27',
            end : '2017-02-28'
        },
        {
            title : 'Lunfel 2/27-2/28',
            start : '2017-02-27',
            end : '2017-02-28'
        },
        {
            title : 'Lunfel 2/27-2/28',
            start : '2017-02-27',
            end : '2017-02-28'
        },
        {
            title : 'Lunfel 2/26-3/05',
            start : '2017-02-26',
            end : '2017-03-05'
        },
        {
            title : 'Lunfel 1/27-1/28',
            start : '2017-01-27',
            end : '2017-01-28'
        },
        {
            title : 'Lunfel 1/27-2/2',
            start : '2017-01-27',
            end : '2017-02-02'
        },
        {
            title : 'Lunfel 3/27-3/28',
            start : '2017-03-27',
            end : '2017-03-28'
        },
    ];
    export default {
        data () {
            return {
                name:'Sunny!',
                fcEvents : demoEvents
            }
        },
        methods : {
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
</script>

<style>
    @import '*/Museum-FrontEnd/scr/styles.css';
</style>