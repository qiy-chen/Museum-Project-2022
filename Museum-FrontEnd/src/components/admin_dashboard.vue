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
                    <div class="container">
                        <table class="styled-table">
                            <thead>
                                <tr>
                                    <th>EmployeeId</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- placeholder values to be removed when script is ready-->
                                <tr class="active-row">
                                    <td>#260972321</td> 
                                    <td>Alex Tsahageas</td>
                                    <td>alexa@mcgill.ca</td><button type="button">Fire Employee</button>
                                </tr>
                                <tr>
                                    <td>#260972321</td>
                                    <td>Nick Tsahageas</td>
                                    <td>alexa@mcgill.ca</td><button type="button">Fire Employee</button>
                                </tr>
                                <tr>
                                    <td>#260972321</td>
                                    <td>Andy Tsahageas</td>
                                    <td>alexa@mcgill.ca</td><button type="button">Fire Employee</button>
                                </tr>
                                <tr>
                                    <td>#260972321</td>
                                    <td>Sandy Tsahageas</td>
                                    <td>alexa@mcgill.ca</td><button type="button">Fire Employee</button>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div> 
            </section>
            <div class="text-center">
                <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: ''})">Schedule Assistant</a>
            </div>
            <section class="page-section" id="yourartworks">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Your Artworks </h2>
                        <h3 class="section-subheading text-muted">Go manage your Artworks</h3>
                        <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'artwork_dashboard'})">Manage your Artworks</a>
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
<script>
    src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" 
    crossorigin="anonymous"
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
    src="src/scripts.js"
    src="https://cdn.startbootstrap.com/sb-forms-latest.js"
</script>
<style>
    @import '*/Museum-FrontEnd/scr/styles.css';
</style>