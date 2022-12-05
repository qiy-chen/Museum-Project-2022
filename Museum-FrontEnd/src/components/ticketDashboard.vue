<template>
	<div class="ticketDashboard">
		<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Ticket DashBoard</title>
<!-- Flaticon-->
<link rel="icon" type="image/x-icon"
	href="../assets/homePage/museumLogo.png" />
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700"
	rel="stylesheet" type="text/css" />
		</head>
		<body id="page-top">
			<!-- Navigation-->
			<nav class="navbar navbar-expand-lg navbar-dark fixed-top"
				id="mainNav">
				<div class="container">
					<a class="navbar-brand" href="#page-top"><img
						src="../assets/homePage/MWLogoWide.png" alt="..." /></a>
					<button class="navbar-toggler" type="button"
						data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
						aria-controls="navbarResponsive" aria-expanded="false"
						aria-label="Toggle navigation">
						Menu <i class="fas fa-bars ms-1"></i>
					</button>
					<div class="collapse navbar-collapse" id="navbarResponsive">
						<ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
							<li class="nav-item"><a class="nav-link" href="#services">Add
									Ticket</a></li>
							<li class="nav-item"><a class="nav-link" href="#update">Update
									Ticket</a></li>
							<li class="nav-item"><a class="nav-link" href="#moveartwork">Update
									Ticket</a></li>
							<li class="nav-item dropdown"><b-dropdown
									id="navbarDropdown" text="Account" class="m-md-2">
								<b-dropdown-item @click="$router.push({name: 'Hello'})">Log
								out</b-dropdown-item> </b-dropdown></li>
						</ul>
					</div>
				</div>
			</nav>
			<!-- Masthead-->
			<header class="masthead">
				<div class="container">
					<div class="masthead-subheading text-uppercase">Welcome to
						your Ticket Dashboard</div>
				</div>
			</header>
			<!-- Services-->
			<section class="page-section" id="services">
				<div class="container">
					<div class="text-center">
						<h2 class="section-heading text-uppercase">Add Ticket</h2>
						<h3 class="section-subheading text-muted">Enter ticket
							information down below.</h3>
						<label for="fname">Date:</label><br>
            <input type="date"
							v-model="ticketDate" id="fname" name="fname" value="2022-12-05"
							min="2022-01-01" max="2025-12-31"><br>
						<div>&nbsp;</div>
						<label for="fname2">Ticket Price:</label><br> <input
							type="number" v-model="price" id="fname2" name="fname2"><br>
						<div>&nbsp;</div>
						<label for="fname2">Number of Tickets:</label><br> <input
							type="number" v-model="nb" id="fname3" name="fname3"><br>
						<div>&nbsp;</div>
						<div>
							<button class="btn btn-primary"
								@click="createTicket(ticketDate, price, nb)">ADD</button>
						</div>
					</div>
				</div>
			</section>
			<div class="text_center">
				<table class="center">
					<tr>
						<th>Ticket Id</th>
						<div>&nbsp;</div>
						<th>Ticket Date</th>
						<div>&nbsp;</div>
						<th>Ticket Price</th>
						<div>&nbsp;</div>
					</tr>
					<tr v-for="ticket in allTickets">
						<td>{{ ticket.ticketId}}</td>
						<div>&nbsp;</div>
						<td>{{ ticket.ticketDate.toString() }}</td>
						<div>&nbsp;</div>
						<td>{{ ticket.price }}</td>
						<div>&nbsp;</div>
					</tr>
				</table>
			</div>
			<section class="page-section" id="services">
				<div class="container">
					<div class="text-center">
						<h2 class="section-heading text-uppercase">Delete Artwork</h2>
						<h3 class="section-subheading text-muted">Select which
							artwork to delete.</h3>
						<label for="fname2">artworkId:</label> <select v-model="artworkId">
							<option v-for="artwork in artworks">
								{{artwork.artworkId}}</option>
						</select>
						<div>&nbsp;</div>
						<div>
							<button class="btn btn-primary" @click="deleteArtwork(artworkId)">DELETE</button>
						</div>
					</div>
				</div>
			</section>
			<section class="page-section" id="update">
				<div class="container">
					<div class="text-center">
						<h2 class="section-heading text-uppercase">Update Artwork</h2>
						<h3 class="section-subheading text-muted">Enter artwork
							information down below.</h3>
						<label for="fname">Artwork ID:</label><br> <input
							type="number" v-model="artworkId" id="fname" name="fname"><br>
						<div>&nbsp;</div>
						<label for="fname">Name:</label><br> <input type="text"
							v-model="artworkName" id="fname" name="fname"><br>
						<div>&nbsp;</div>
						<label for="fname">Value:</label><br> <input type="number"
							v-model="value" id="fname" name="fname"><br>
						<div>&nbsp;</div>
						<label for="numbers">Loanable?</label> <select name="numbers"
							v-model="isLoanable" id="numbers">
							<option value="1">true</option>
							<option value="2">false</option>
						</select>
						<div>&nbsp;</div>
						<div>
							<button class="btn btn-primary"
								@click="updateArtwork(artworkId, artworkName, value, true)">UPDATE</button>
						</div>
					</div>
				</div>
			</section>

			<section class="page-section" id="moveartwork">
				<div class="container">
					<div class="text-center">
						<h2 class="section-heading text-uppercase">Move Artwork</h2>
						<h3 class="section-subheading text-muted">Find by artwork id
							and select room to move to.</h3>
						<label for="fname">Artwork ID:</label><br> <input
							type="number" v-model="artworkId" id="fname" name="fname"><br>

						<div>&nbsp;</div>
						<label for="fname">Room ID:</label><br> <input type="number"
							v-model="roomId" id="fname" name="fname"><br>
						<div>&nbsp;</div>
						<div>
							<button class="btn btn-primary"
								@click="moveArtwork(artworkId, roomId, 69)">MOVE</button>
						</div>
					</div>
				</div>
			</section>
			<section class="page-section" id="moveartwork">
				<div class="container">
					<div class="text-center"></div>
				</div>
			</section>
			<!-- Footer-->
			<footer class="footer py-4">
				<div class="container">
					<div class="row align-items-center">
						<div class="col-lg-4 text-lg-start">Copyright &copy; Museum
							Website 2022</div>
						<div class="col-lg-4 my-3 my-lg-0">
							<a class="btn btn-dark btn-social mx-2" href="#!"
								aria-label="GitHub"><i class="fab fa-github"></i></a> <a
								class="btn btn-dark btn-social mx-2" href="#!"
								aria-label="Email"><i class="fab fa-facebook-f"></i></a>
						</div>
						<div class="col-lg-4 text-lg-end">
							<a class="link-dark text-decoration-none me-3" href="#!">Privacy
								Policy</a> <a class="link-dark text-decoration-none" href="#!">Terms
								of Use</a>
						</div>
					</div>
				</div>
			</footer>
		</body>
	</div>
</template>

<script src="./Ticket.js">
</script>

<style>
@import '../assets/styles.css';
</style>
