<template>
    <div class="customer_dashboard">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Customer Dashboard</title>
            <!-- Flaticon-->
            <link rel="icon" type="image/x-icon" href="../assets/homePage/museumLogo.png" />
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
                    <a class="navbar-brand" href="#page-top"><img src="../assets/homePage/MWLogoWide.png" alt="..." /></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                        Menu
                        <i class="fas fa-bars ms-1"></i>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarResponsive">
                        <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                            <li class="nav-item"><a class="nav-link" href="#yourtickets">Your Tickets</a></li>
                            <li class="nav-item"><a class="nav-link" href="#yourloans">Your Loans</a></li>
                            <li class="nav-item"><a class="nav-link" href="#gallery">Gallery</a></li>
                            <li class="nav-item dropdown">
                                <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
                                <b-dropdown-item @click="$router.push({name: 'gallery'})">Gallery</b-dropdown-item>
                                <b-dropdown-item @click="$router.push({name: 'buy_tickets'})">Tickets</b-dropdown-item>
                                <b-dropdown-item @click="$router.push({name: 'request_loan'})">Loans</b-dropdown-item>
                                <b-dropdown-item @click="$router.push({name: 'Hello'})">Log out</b-dropdown-item>
                            </b-dropdown>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            </body>
            <header class="masthead">
                <div class="container">
                    <div class="masthead-subheading">Welcome Customer</div>
                </div>
            </header>
            <section class="page-section" id="yourtickets">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Tickets</h2>
                        <h3 class="section-subheading text-muted">These are all your upcoming tickets</h3>
                    </div>
                    <div class="container">
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>TicketId</th>
                                    <th>Date</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                            <tbody>
                               <li v-for="ticket in theirTickets">
                                 {{ticket.ticketId}} {{ticket.ticketDate.toString()}} {{ticket.price}}
                               </li>
                            </tbody>
                        </table>
                    </div>
                </div>
                <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'buy_tickets'})">Buy Tickets</a>
            </section>
            <section class="page-section" id="yourloans">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Loans</h2>
                        <h3 class="section-subheading text-muted">These are all your active loans</h3>
                    </div>
                </div>
                <div class="container">
                    <table class="styled-table">
                        <thead>
                            <tr>
                                <th>LoanId</th>
                                <th>Status</th>
                                <th>Nb Days</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>Rental Fee</th>
                            </tr>
                        </thead>
                        <tbody>
                               <li v-for="loan in theirLoans">
                                 {{loan.loanId}} {{loan.status}} {{loan.numOfDays}} {{loan.startDate.toString()}} {{loan.endDate.toString()}} {{loan.rentalFee}} 
                               </li>
                        </tbody>
                    </table>
                </div>
                <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Request new loan</a>
            </section>
            <section id="gallery">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">View Gallery</h2>
                        <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'gallery'})">Gallery</a>
                    </div>
                </div>
            </section>
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
    </div>
</template>
<script src="./customer_dashboard_scripts.js">
</script>
<style>
</style>
