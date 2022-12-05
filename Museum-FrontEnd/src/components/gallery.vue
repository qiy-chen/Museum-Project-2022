<template>
    <div class="gallery">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Museum Website</title>
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
                    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
                        <div class="input-group">
                            <input class="form-control" type="text" placeholder="Search for artwork..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
                            <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
                        </div>
                    </form>
                    <div class="collapse navbar-collapse" id="navbarResponsive">
                        <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                            <li class="nav-item"><a class="nav-link" href="#services">Gallery</a></li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Account</a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="#!">Log out</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    
                </div>
            </nav>
            <!-- Masthead-->
            <header class="masthead">
                <div class="container">
                    <div class="masthead-subheading">Welcome to The Gallery</div>
                </div>
            </header>
            <!-- Services-->
            <section class="page-section" id="services">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Gallery</h2>
                        <h3 class="section-subheading text-muted">Click on the artwork you wish to loan</h3>
                    </div>
                    <div class="row text-center">
                        <div class="col-md-4">
                            <h4 class="my-3">Name</h4>
                            <p class="text-muted">Price</p>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Loan</a>  
                        </div>
                        <div class="col-md-4">
                            <h4 class="my-3">Name</h4>
                            <p class="text-muted">Price</p>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Loan</a>  
                        </div>
                        <div class="col-md-4">
                            <h4 class="my-3">Name</h4>
                            <p class="text-muted">Price</p>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Loan</a>  
                        </div>
                    </div>
                </div>
            </section>
            <section class="page-section" id="services">
                <div class="container">
                    <div class="text-center">
                    </div>
                    <div class="row text-center">
                        <div class="col-md-4">
                            <h4 class="my-3">Name</h4>
                            <p class="text-muted">Price</p>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Loan</a>  
                        </div>
                        <div class="col-md-4">
                            <h4 class="my-3">Name</h4>
                            <p class="text-muted">Price</p>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Loan</a>  
                        </div>
                        <div class="col-md-4">
                            <h4 class="my-3">Name</h4>
                            <p class="text-muted">Price</p>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'request_loan'})">Loan</a>  
                        </div>
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
        </body>
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
    @import '*/Museum-FrontEnd/src/styles.css';
</style>
