<template>
    <div class="employeeDashboard">
        <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Employee DashBoard</title>
        <!-- Flaticon-->
        <link rel="icon" type="image/x-icon" href="../assets/homePage/museumLogo.png" />
        <!-- Font Awesome icons (free version)-->
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    </head>
    <body id="page-top">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand" href="#page-top"><img src="../assets/homePage/MWLogoWide.png" alt="..." /></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ms-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                        <li class="nav-item"><a class="nav-link" href="#scheulde">Schedule</a></li>
                        <li class="nav-item"><a class="nav-link" href="#artwork">Artwork</a></li>
                        <li class="nav-item dropdown">
                            <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
                                <b-dropdown-item @click="$router.push({name: 'Hello'})">Log out</b-dropdown-item>
                            </b-dropdown>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container">
                <div class="masthead-subheading text-uppercase">Welcome to your Employee Dashboard</div>
            </div>
        </header>
        <!-- Services-->
        <section class="page-section" id="scheulde">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Schedule</h2>
                    <h3 class="section-subheading text-muted">View all your shifts below.</h3>
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
        <section class="page-section" id="artwork">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Artwork Dashboard</h2>
                    <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'artworkDashboard'})">Artwork Dashboard</a>
                </div>
            </div>
        </section>
        
        <!-- Footer-->
        <footer class="footer py-4">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-start">Copyright &copy; Museum Website 2022</div>
                    <div class="col-lg-4 my-3 my-lg-0">
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="GitHub"><i class="fab fa-github"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Email"><i class="fab fa-facebook-f"></i></a>
                    </div>
                    <div class="col-lg-4 text-lg-end">
                        <a class="link-dark text-decoration-none me-3" href="#!">Privacy Policy</a>
                        <a class="link-dark text-decoration-none" href="#!">Terms of Use</a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Bootstrap core JS-->
        
        <!-- Core theme JS-->
        
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <!-- * *                               SB Forms JS                               * *-->
        <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        
    </body>
    </div>
</template>
<script src='./Employee.js'>

    // full calendar from: https://github.com/Wanderxx/vue-fullcalendar

    let fcEvents = 'shifts'
    
</script>

<style>
    @import '*/Museum-FrontEnd/scr/assets/styles.css';
</style>