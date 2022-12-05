<template>
    <div class="admin_dashboard">
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
                        <ul class="navbar-nav ms-auto py-4 py-lg-0">
                            <li class="nav-item"><a class="nav-link" href="#youremployees">Employees</a></li>
                            <li class="nav-item"><a class="nav-link" href="#yourartworks">Artworks</a></li>
                            <li class="nav-item"><a class="nav-link" href="#yourrooms">Rooms</a></li>
                            <li class="nav-item"><a class="nav-link" href="#scheduleView">Schedule</a></li>
                            <li class="nav-item dropdown">
                            <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
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
                    <div class="masthead-subheading">Welcome Admin</div>
                </div>
            </header>
            <section class="page-section" id="youremployees">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Employees</h2>
                        <h3 class="section-subheading text-muted">These are all your currently registered employees</h3>
                    <h2 class="section-heading text-uppercase">Register a new employee</h2>
                        <h3 class="section-subheading text-muted">Enter employee information down below.</h3>
                        <label for="inputFirstName">Enter first name:</label>
                        <input class="form-control" id="inputFirstName" type="text" v-model="firstName" placeholder="Confirm email" />
                        <div>&nbsp;</div>
                        <label for="inputLastName">Enter last name:</label>
                        <input class="form-control" id="inputLastName" type="text" v-model="lastName" placeholder="Confirm email" />
                        <div>&nbsp;</div> 
                        <label for="inputEmail">Enter email:</label>
                        <input class="form-control" id="inputEmail" type="text" v-model="email" placeholder="Confirm email" />
                        <div>&nbsp;</div>   
                        <label for="inputPassword">Enter password:</label>
                        <input class="form-control" id="inputPassword" type="text" v-model="password" placeholder="Confirm password" />
                        <div>&nbsp;</div>        
                        <div>
                            <div class="d-grid"><button type="button" class="btn btn-primary" @click="createNewPersonAndMakeEmployee(email,password,firstName,lastName,69)">Create Employee</button></div>                        </div> 
                    </div>
                    <table class="center">
                            <tr>
                                <th>Employee email</th>
                            </tr>
                            <tr v-for="employee in employees">
                                <td>{{ employee.email }}</td>
                            </tr>
                    </table>
                </div>
            </section>
            <section class="page-section" id="scheduleView">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Your Scheduler</h2>
                    <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'schedule'})">Schedule Assistant</a>
                </div>
            </section>
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

<script src="./admin_scripts.js">
</script>

<style>
    @import '*/Museum-FrontEnd/scr/assets/styles.css';
</style>