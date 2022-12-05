<template>
    <div class="room_dashboard">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Room DashBoard</title>
            <!-- Flaticon-->
            <link rel="icon" type="image/x-icon" href="../assets/homePage/museumLogo.png" />
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
                            <li class="nav-item"><a class="nav-link" href="#display">Create Display</a></li>
                            <li class="nav-item"><a class="nav-link" href="#storage">Create Storage</a></li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Actions</a>
                                <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
                                <b-dropdown-item href="#">Employee Dashboard</b-dropdown-item>
                                <b-dropdown-item href="#">Admin Dashboard</b-dropdown-item>
                                <b-dropdown-item href="#!">Log out</b-dropdown-item>
                            </b-dropdown>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- Masthead-->
            <header class="masthead">
                <div class="container">
                    <div class="masthead-subheading text-uppercase">Welcome to your Room Dashboard</div>
                </div>
            </header>
            <!-- Services-->
            <section class="page-section" id="display">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Create a Display Room</h2>
                        <h3 class="section-subheading text-muted">Enter room information down below.</h3>
                        <label for="fname">Room number:</label><br>
                        <input class="form-control" id="inputroomNumber" type="text" v-model="roomNumber" placeholder="Room number" />
                        <label for="fname">Max artworks:</label><br>
                        <input class="form-control" id="inputmaxArtworks" type="text" v-model="maxArtworks" placeholder="maxArtworks" />
                        <div>&nbsp;</div>   
                        <div>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="createDisplayRoom(roomNumber,maxArtworks,69)" >Create Display Room</a>
                            <div>&nbsp;</div>  
                        </div>
                        <div class="text_center">
                        <table class="center">
                            <tr>
                                <th>Room Number</th>
                                <div>&nbsp;</div> 
                                <th>Max Artworks</th>
                            </tr>
                            <tr v-for="display in displays">
                                <td>{{ display.roomNumber}}</td>
                                <div>&nbsp;</div>
                                <td>{{ display.maxArtworks }}</td>
                            </tr>
                        </table>
                        </div>
                    </div>
                </div>
            </section>
            <section class="page-section" id="storage">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Create a Storage Room</h2>
                        <h3 class="section-subheading text-muted">Enter room information down below.</h3>
                        <label for="fname">Room number:</label><br>
                        <input class="form-control" id="inputroomNumber" type="text" v-model="roomNumber" placeholder="Room number" />
                        <div>&nbsp;</div>   
                        <div>
                            <a class="btn btn-dark btn-xl text-uppercase" @click="">Create Storage Room</a>
                        </div>
                    </div>
                </div>
            </section>
            <div class="text-center">
                <a class="btn btn-dark btn-xl text-uppercase" @click="$router.push({name: 'admin_dashboard'})">Return to your dashboard</a>
            </div>
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

<script
   src="./Room.js">
</script>

<style>
    @import '../assets/styles.css';
</style>
