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
							<li class="nav-item"><a class="nav-link" href="#delete">Delete
									Ticket</a></li>
							<li class="nav-item"><a class="nav-link" href="#update">Update
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
						<label for="fname">Date:</label><br> <input type="date"
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
			<section class="page-section" id="alltickets">
				<div class="container">
					<div class="text_center">
						<div>
							<h2 class="section-heading text-uppercase">All Tickets</h2>

						</div>
						<div>&nbsp;</div>
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
				</div>
			</section>
			<div class="text_center">
				<h2 class="section-heading text-uppercase">Unpurchased Tickets</h2>
				<table class="center">
					<tr>
						<th>Ticket Id</th>
						<div>&nbsp;</div>
						<th>Ticket Date</th>
						<div>&nbsp;</div>
						<th>Ticket Price</th>
						<div>&nbsp;</div>
					</tr>
					<tr v-for="ticket in unpurchasedTickets">
						<td>{{ ticket.ticketId}}</td>
						<div>&nbsp;</div>
						<td>{{ ticket.ticketDate.toString() }}</td>
						<div>&nbsp;</div>
						<td>{{ ticket.price }}</td>
						<div>&nbsp;</div>
					</tr>
				</table>
			</div>
			<section class="page-section" id="delete">
				<div class="container">
					<div class="text-center">
						<h2 class="section-heading text-uppercase">Delete Ticket</h2>
						<h3 class="section-subheading text-muted">Select which ticket
							to delete.</h3>
						<label for="fname2">ticketId:</label> <select v-model="ticketId">
							<option v-for="ticket in allTickets">
								{{ticket.ticketId}}</option>
						</select>
						<div>&nbsp;</div>
						<div>
							<button class="btn btn-primary" @click="deleteTicket(ticketId)">DELETE</button>
						</div>
					</div>
				</div>
			</section>
			<section class="page-section" id="update">
				<div class="container">
					<div class="text-center">
						<h2 class="section-heading text-uppercase">Update Ticket</h2>
						<h3 class="section-subheading text-muted">Enter ticket
							information down below.</h3>
						<label for="fname3">ticketId:</label> <select v-model="ticketId">
							<option v-for="ticket in allTickets">
								{{ticket.ticketId}}</option>
						</select>
						<div>&nbsp;</div>
						<label for="fname">Date:</label><br> <input type="date"
							v-model="ticketDate" id="fname" name="fname" value="2022-12-05"
							min="2022-01-01" max="2025-12-31"><br>
						<div>&nbsp;</div>
						<label for="fname2">Ticket Price:</label><br> <input
							type="number" v-model="price" id="fname2" name="fname2"><br>
						<div>&nbsp;</div>
						<div>
							<button class="btn btn-primary"
								@click="updateTicket(ticketDate, price, ticketId)">UPDATE</button>
						</div>
					</div>
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
