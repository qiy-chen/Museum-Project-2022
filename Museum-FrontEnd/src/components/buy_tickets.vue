<template>
    <div class="register">
        <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Purchase Tickets Dashboard</title>
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
                        <li class="nav-item dropdown">
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
                                <b-dropdown-item href="#">Gallery</b-dropdown-item>
                                <b-dropdown-item href="#">Dashboard</b-dropdown-item>
                                <b-dropdown-item href="#!">Log out</b-dropdown-item>
                            </b-dropdown>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        </body>
        <header class="masthead">
            <div class="container">
                <div class="masthead-subheading">Purchase Tickets</div>
            </div>
        </header>
        <section class="page-section" id="yourtickets">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Select your Tickets</h2>
                </div>
                <form>
                    <label for="start">Date:</label>
                    <input type="date" id="start" name="trip-start"
                        value="2022-12-05"
                        min="2022-01-01" max="2025-12-31">
                    <label for="numbers">Number of People:</label>
                    <select name="numbers" id="numbers">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                    </select>
                <div>
                    <label for="fname">Promo Code:</label><br>
                    <input type="text" id="fname" name="fname"><br>
                </div>
                </form>
                <div>
                    <td>Price per ticket: $9.99 + tax</td>
                </div>
                <a class="btn btn-dark btn-xl text-uppercase" @click="">Purchase!</a>
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
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
    src="src/scripts.js"
    src="https://cdn.startbootstrap.com/sb-forms-latest.js"
    src="https://use.fontawesome.com/releases/v6.1.0/js/all.js"
    crossorigin="anonymous"
</script>
<style>

</style>
