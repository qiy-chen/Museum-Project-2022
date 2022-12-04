<template>
    <div class="schedule">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Schedule Dashboard</title>
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
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Account</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#!">Go to Art Gallery</a></li>
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
                <div class="masthead-subheading">Welcome to your Schedule Page</div>
            </div>
        </header>
        <section>
            <div class="text-center">
                <h2 class="section-heading text-uppercase">Current Shifts</h2>
            </div>
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Employee</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- placeholder values to be removed when script is ready-->
                    <tr class="active-row">
                        <td>#260972321</td>
                        <td>01/01/2023</td>
                        <td>9.99</td>
                    </tr>
                    <tr>
                        <td>#260958121</td>
                        <td>25/12/2022</td>
                        <td>9.99</td>
                    </tr>
                    <tr>
                        <td>#260958121</td>
                        <td>20/12/2022</td>
                        <td>9.99</td>
                    </tr>
                    <tr>
                        <td>#260958121</td>
                        <td>20/12/2022</td>
                        <td>9.99</td>
                    </tr>
                </tbody>
            </table>
        </section>
        <section class="page-section" id="addshift">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Add a Shift</h2>
                    <label for="start"> Shift Start Date:</label>
                    <input type="date" id="start" name="trip-start"
                        value="2022-12-05"
                        min="2022-01-01" max="2025-12-31">
                    <label for="start"> Shift End Date:</label>
                    <input type="date" id="end" name="trip-start"
                        value="2022-12-05"
                        min="2022-01-01" max="2025-12-31">
                    <label for="employee">Employee:</label>
                    <select name="employee" id="employee">
                        <option value="1">Employee#1</option>
                        <option value="2">Employee#2</option>
                        <option value="3">Employee#3</option>
                        <option value="4">Employee#4</option>
                        <option value="5">Employee#5</option>
                        <option value="6">Employee#6</option>
                        <option value="7">Employee#7</option>
                    </select>
                    <div>&nbsp</div>
                    <div>
                        <a class="btn btn-dark btn-xl text-uppercase" href="#!">Add Shift!</a>
                    </div>
                </div>
            </div>
        </section>
        <section class="page-section" id="removeshift">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Remove a Shift</h2>
                    <label for="employee">Employee:</label>
                    <select name="employee" id="employee">
                        <option value="1">Employee#1</option>
                        <option value="2">Employee#2</option>
                        <option value="3">Employee#3</option>
                        <option value="4">Employee#4</option>
                        <option value="5">Employee#5</option>
                        <option value="6">Employee#6</option>
                        <option value="7">Employee#7</option>
                    </select>
                    <label for="employee">Shift Id:</label>
                    <select name="employee" id="employee">
                        <option value="1">Employee#1</option>
                        <option value="2">Employee#2</option>
                        <option value="3">Employee#3</option>
                        <option value="4">Employee#4</option>
                        <option value="5">Employee#5</option>
                        <option value="6">Employee#6</option>
                        <option value="7">Employee#7</option>
                    </select>
                    <div>&nbsp</div>
                    <div>
                        <a class="btn btn-dark btn-xl text-uppercase" href="#!">Remove their Shift!</a>
                    </div>
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
    @import '*/Museum-FrontEnd/src/styles.css';
</style>
