<template>
    <div class="customer_dashboard">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Request Loan Dashboard</title>
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
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Account</a>
                                <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
                                <b-dropdown-item href="#">Gallery</b-dropdown-item>
                                <b-dropdown-item href="#">Dashboard</b-dropdown-item>
                                <b-dropdown-item href="#!">Log out</b-dropdown-item>
                            </b-dropdown>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            </body>
            <header class="masthead">
                <div class="container">
                    <div class="masthead-subheading">Request Loan</div>
                </div>
            </header>
            <section class="page-section" id="yourloans">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Select your loan details</h2>
                    </div>
                    <form>
                        <label for="fname3">Artwork Id:</label> <label for="fname3"></label>
					<select v-model="artworkId">
						<option v-for="artwork in artworks">
							{{artwork.artworkId}}</option>
					</select>
					<div>&nbsp;</div>
                        <label for="fname">Start Date:</label><br> <input type="date"
							v-model="ticketDate" id="fname" name="fname" value="2022-12-05"
							min="2022-01-01" max="2025-12-31"><br>
						<div>&nbsp;</div>
						<label for="fname">End Date:</label><br> <input type="date"
							v-model="ticketDate" id="fname" name="fname1" value="2022-12-05"
							min="2022-01-01" max="2025-12-31"><br>
						<div>&nbsp;</div>
                            <label for="fname90">Loan Duration (day):</label><br> <input
							type="number" v-model="numOfDays" id="fname2" name="fname2"><br>
						<div>&nbsp;</div>
                    </form>
                    <div>
                        <td>Loan fee: $9.99 + tax</td>
                    </div>
                    <div>
                        <a class="btn btn-dark btn-xl text-uppercase" @click="requestLoan(startDate, endDate,numOfDays, artworkId)">Request!</a>  
                    </div>
                </div>
                            <div class="text_center">
                        <table class="center">
                            <tr>
                                <th>Artwork Name</th>
                                <div>&nbsp;</div> 
                                <th>Artwork Id</th>
                                <div>&nbsp;</div> 
                                <th>Artwork Value</th>
                                <div>&nbsp;</div> 
                                <th>Artwork Loanable</th>
                                <div>&nbsp;</div> 
                                <th>Artwork Room Id</th>
                            </tr>

                        
                            <tr v-for="artwork in artworks">
                                <td>{{ artwork.artworkName}}</td>
                                <div>&nbsp;</div>
                                <td>{{ artwork.artworkId }}</td>
                                <div>&nbsp;</div>
                                <td>{{ artwork.value }}</td>
                                <div>&nbsp;</div>
                                <td>{{ artwork.isLoanable }}</td>
                                <div>&nbsp;</div>
                                <td>{{ artwork.roomId }}</td>
                            </tr>
                        </table>
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
            <!-- Core theme JS-->
            <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
            <!-- * *                               SB Forms JS                               * *-->
            <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
            <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
    </div>
</template>
<script>
    src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" 
    crossorigin="anonymous"
    src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
    src="./request_loans_scripts.js"
    src="https://cdn.startbootstrap.com/sb-forms-latest.js"
</script>
<style>

</style>