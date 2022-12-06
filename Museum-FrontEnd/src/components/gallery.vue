<template>
    <div class="gallery">
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Museum Website</title>
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
                            <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
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
                    <div class="masthead-subheading">Welcome to The Gallery</div>
                </div>
            </header>
            <!-- Services-->
            <div class="text_center">
                    &nbsp;
                    <table class="styled-table">
                        <tr>
                            <th>Name</th>
                            <div>&nbsp;</div>
                            <th>Id</th>
                            <div>&nbsp;</div>
                            <th>Value</th>
                            <div>&nbsp;</div>
                            <th>Loanable</th>
                            <div>&nbsp;</div>
                            <th>RoomId</th>
                        </tr>
                        <tr v-for="artwork in artworksOnDisplay">
                            <td width="100px"> {{ artwork.artworkName }}</td>
                            <div>&nbsp;</div>
                            <td width="100px">{{artwork.artworkId }}</td>
                            <div>&nbsp;</div>
                            <td width="5px">{{ artwork.value }}</td>
                            <div>&nbsp;</div>
                            <td width="5px">{{ artwork.isLoanable }}</td>
                            <div>&nbsp;</div>
                            <td width="5px">{{ artwork.roomId }}</td>
                        </tr>
                    </table>
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
<script src="./artworkClient.js">
</script>
<style>
    @import '*/Museum-FrontEnd/src/styles.css';
</style>

<style>

.styled-table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    font-family: sans-serif;
    min-width: 400px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    margin-left: auto;
    margin-right: auto;
}

.styled-table thead tr {
    background-color: #009879;
    color: #b30f0f;
    text-align: left;
}

.styled-table th,
.styled-table td {
    padding: 12px 60px;
}

.styled-table tbody tr {
    border-bottom: 1px solid #bf0f0f;
}

.styled-table tbody tr:nth-of-type(even) {
    background-color: #c40707;
}

.styled-table tbody tr:last-of-type {
    border-bottom: 2px solid #d33232;
}

.styled-table tbody tr.active-row {
    font-weight: bold;
    color: #ce1111;
}

</style>