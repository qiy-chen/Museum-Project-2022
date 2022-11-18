# Ticket Service
## Read a Ticket
URL: /tickets/{ticketId}

Path Variable: ticketId: The id of the ticket to be read.

Request Body: None

HTTP Method: GET

Description: Read a ticket that is stored in the ticket repository, found using its id. The service returns a TicketResponseDto object of the Ticket found.

## Read all Tickets
URL: /tickets

Path Variable: None

Request Body: None

HTTP Method: GET

Description: Read all tickets that are stored in the ticket repository. The service returns a list of all TicketResponseDto objects stored in the ticket repository.

## Create a Ticket
URL: /tickets

Path Variable: None

Request Body: TicketRequestDto object

HTTP Method: POST

Description: Create a ticket based on the TicketRequestDto object and store it in the ticket repository. The service returns a TicketResponseDto object of the Ticket created.

## Replace/Update a Ticket
URL: /tickets/{ticketId}

Path Variable: ticketId: The id of the ticket to be replaced.

Request Body: TicketRequestDto object

HTTP Method: PUT

Description: Replace the ticket that has the ticketId with the attributes of the ticket based on the TicketRequestDto object and store it in the ticket repository. If the Ticket with ticketId doesn't exist, a new Ticket with ticketId will be created. The target id is kept. The service returns a TicketResponseDto object of the Ticket saved.

## Delete a Ticket
URL: /tickets/{ticketId}

Path Variable: ticketId: The id of the ticket to be deleted.

Request Body: None

HTTP Method: DELETE

Description: Delete the ticket with ticket id. Note that this operation also removes the associations the ticket had with other classes. The service returns true after the deletion is done.

## Browse all Tickets of a customer
URL: /customers/{roleId}

Path Variable: roleId: The id of the customer that want to browse their tickets.

Request Body: None

HTTP Method: GET

Description: Read all tickets that are stored in the ticket repository associated with the customer (from the id given in the URL). The service returns a list of all TicketResponseDto objects stored in the ticket repository associated with the customer.

## Purchase a Ticket
URL: /customers/{roleId}

Path Variable: roleId: The id of the customer that want to purchase a Ticket.

Request Body: TicketRequestDto object

HTTP Method: POST

Description: Create a ticket based on the TicketRequestDto object, associate it with the customer (from the id given in the URL) and store it in the ticket repository. The service returns a TicketResponseDto object of the Ticket saved.

## Cancel a Ticket
URL: /customers/{roleId}

Path Variable: roleId: The id of the customer that want to cancel a Ticket.

Request Body: IdRequestDto object

HTTP Method: DELETE

Description: If the ticket's date is at least 3 days later than the current date and is associated with the customer (from the id given in the URL) , delete the ticket with the id given by the IdRequestDto object. The service returns true after the deletion is done.