<template>
    <div class="artworkDashboard">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Artwork DashBoard</title>
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
                            <li class="nav-item"><a class="nav-link" href="#services">Add Artwork</a></li>
                            <li class="nav-item"><a class="nav-link" href="#update">Update Artwork</a></li>
                            <li class="nav-item"><a class="nav-link" href="#moveartwork">Move Artwork</a></li>
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
                    <div class="masthead-subheading text-uppercase">Welcome to your Artwork Dashboard</div>
                </div>
            </header>
            <!-- Services-->
            <section class="page-section" id="services">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Add Artwork</h2>
                        <h3 class="section-subheading text-muted">Enter artwork information down below.</h3>
                        <label for="fname">Name:</label><br>
                            <input type="text" v-model="artworkName" id="fname" name="fname"><br>
                        <div>&nbsp;</div>
                        <label for="fname2">RoomId:</label>
                            <select v-model="roomId">
                            <option v-for="display in displays" > {{display.roomId}}</option>
                            <option v-for="storage in storages"> {{storage.roomId}}</option>
                            </select>
                        <div>&nbsp;</div>

                        <div>
                            <button class ="btn btn-primary" @click="createArtwork(artworkName, roomId, 69)">ADD</button>

                        </div>
                    </div>
                </div>

            <div class="text_center">
                        &nbsp;
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
            <section class="page-section" id="services">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Delete Artwork</h2>
                        <h3 class="section-subheading text-muted">Select which artwork to delete.</h3>
                        <label for="fname2">artworkId:</label>
                            <select v-model="artworkId">
                            <option v-for="artwork in artworks" > {{artwork.artworkId}}</option>
                            </select>
                        <div>&nbsp;</div>
                        <div>
                            <button class ="btn btn-primary" @click="deleteArtwork(artworkId)">DELETE</button>
                        </div>
                    </div>
                </div>
            </section>
            <section class="page-section" id="update">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Update Artwork</h2>
                        <h3 class="section-subheading text-muted">Enter artwork information down below.</h3>
                        <label for="fname2">ArtworkId:</label>
                            <select v-model="artworkId">
                            <option v-for="artwork in artworks" > {{artwork.artworkId}}</option>
                            </select>
                        <div>&nbsp;</div>
                        <label for="fname">Name:</label><br>
                            <input type="text" v-model="artworkName" id="fname" name="fname"><br>
                        <div>&nbsp;</div>
                        <label for="fname">Value:</label><br>
                            <input type="number" v-model="value" id="fname" name="fname"><br>
                        <div>&nbsp;</div>
                        <label for="numbers">Loanable?</label>
                        <select name="numbers" v-model="isLoanable" id="numbers">
                            <option value="1">true</option>
                            <option value="2">false</option>
                        </select>
                        <div>&nbsp;</div>
                        <div>
                            <button class ="btn btn-primary" @click="updateArtwork(artworkId, artworkName, value, true)" >UPDATE</button>
                        </div>
                    </div>
                </div>
            </section>

            <section class="page-section" id="moveartwork">
                <div class="container">
                    <div class="text-center">
                        <h2 class="section-heading text-uppercase">Move Artwork</h2>
                        <h3 class="section-subheading text-muted">Find by artwork id and select room to move to.</h3>
                        <label for="fname">Artwork ID:</label><br>
                            <input type="number" v-model="artworkId" id="fname" name="fname"><br>

                        <div>&nbsp;</div>
                        <label for="fname">Room ID:</label><br>
                            <input type="number" v-model="roomId" id="fname" name="fname"><br>
                        <div>&nbsp; </div>
                        <div>
                            <button class ="btn btn-primary" @click="moveArtwork(artworkId, roomId, 69)" >MOVE</button>
                        </div>
                    </div>
                </div>
            </section>
            <section class="page-section" id="moveartwork">
                <div class="container">
                    <div class="text-center">

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

<script src="./artworkManager.js">
</script>

<style>
    @import '../assets/styles.css';
</style>
