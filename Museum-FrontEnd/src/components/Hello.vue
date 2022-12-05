<template>
  <div class="Hello">
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
                        <li class="nav-item"><a class="nav-link" href="#services">Services</a></li>
                        <li class="nav-item"><a class="nav-link" href="#team">Team</a></li>
                        <li class="nav-item dropdown">
                            <b-dropdown id="navbarDropdown" text="Account" class="m-md-2">
                                <b-dropdown-item @click="$router.push({name: 'login'})">Log in</b-dropdown-item>
                                <b-dropdown-item @click="$router.push({name: 'register'})">Register</b-dropdown-item>
                            </b-dropdown>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container">
                <div class="masthead-subheading">Welcome To Our Museum Website!</div>
                <div class="masthead-heading text-uppercase">It's Nice To Meet You</div>
                <a class="btn btn-primary btn-xl text-uppercase" href="#services">Tell Me More</a>
            </div>
        </header>
        <!-- Services-->
        <section class="page-section" id="services">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Services</h2>
                    <h3 class="section-subheading text-muted">Create a free account to access these online services.</h3>
                </div>
                <div class="row text-center">
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-plus fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">View the Gallery</h4>
                        <p class="text-muted">View the incredible artwork housed at this museum.</p>
                    </div>
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-plus fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Purchase Tickets</h4>
                        <p class="text-muted">Come visit the museum. Tickets can be purchased online.</p>
                    </div>
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-plus fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Request a Loan</h4>
                        <p class="text-muted">Now you can bring home select pieces of artwork from our museum.</p>
                    </div>
                </div>
                <div class="container">
                    <div class="row">
                        <h4 class="my-3">   </h4>
                        <h4 class="my-3">   </h4>
                        <div class="col">
                            <div class="team-member">
                                <a class="btn btn-primary btn-xl text-uppercase" @click="$router.push({name: 'login'})">Log in</a>
                            </div>
                        </div>

                        
                        <div class="col">
                            <div class="team-member">
                              <a class="btn btn-primary btn-xl text-uppercase" @click="$router.push({name: 'register'})">Sign up</a>                           
                             </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Team-->
        <section class="page-section bg-light" id="team">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Our Amazing Team</h2>
                    <h3 class="section-subheading text-muted">Meet the developers of the Museum Management System.</h3>
                </div>
                <div class="row">
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="../assets/homePage/img/team/sam.jpeg" alt="..." />
                            <h4>Sam Faubert</h4>
                            <p class="text-muted">Scrum Manager</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="../assets/homePage/img/team/elie.jpg" alt="..." />
                            <h4>Elie Abdo</h4>
                            <p class="text-muted">Software Developer</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="../assets/homePage/img/team/emma.jpg" alt="..." />
                            <h4>Emma Kawczynski</h4>
                            <p class="text-muted">Software Developer</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="../assets/homePage/img/team/alex.jpg" alt="..." />
                            <h4>Alex Tsahageas</h4>
                            <p class="text-muted">Project Manager</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="../assets/homePage/img/team/qi.jpg" alt="..." />
                            <h4>Qi Chen</h4>
                            <p class="text-muted">Software Developer</p>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="../assets/homePage/img/team/jeanine.jpg" alt="..." />
                            <h4>Jeanine Looman</h4>
                            <p class="text-muted">Software Developer</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 mx-auto text-center"><p class="large text-muted">The Museum Management System was designed for ECSE 321 at McGill University for the fall semester of 2022. Our team worked diligently, using state of the collaborative version control platforms and various programming languages.</p></div>
                </div>
            </div>
        </section>
        <!-- Footer-->
        <footer class="footer py-4">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-start">Copyright &copy; Museum Website 2022</div>
                    <div class="col-lg-4 my-3 my-lg-0">
                    </div>
                </div>
            </div>
        </footer>
    </body>
  </div>
</template>

<script>
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"  
  src="https://cdn.startbootstrap.com/sb-forms-latest.js"
  src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" 
  src="../scripts.js"
  crossorigin="anonymous"

  // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar 
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 74,
        });
    };

    // Collapse responsive navbar when toggler is visible
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    @import '../assets/styles.css';
</style>
