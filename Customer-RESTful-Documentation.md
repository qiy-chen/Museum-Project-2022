# Customer Service
## Create a Customer
URL: /customers

Path Variable: None 

Request Body: CustomerRequestDto customerRequestDto 

HTTP Method: POST

Description: Create a customer that is stored in the customer repository. The service returns a CustomerResponseDto object of the customer created.

## Read a customer
URL: /customer/{id}

Path Variable: id

Request Body: None

HTTP Method: GET

Description: Read a customer stored in the customer repository, found using its id. The service returns a CustomerResponseDto object of the customer found.

## Delete a customer
URL: "/customer/{id}"

Path Variable: id

Request Body: None

HTTP Method: Delete

Description: Delete a customer using the service method delete. The artwork and customer have the customer removed from their lists. Return a CustomerResponseDto of the same customer.

## Get all Customers
URL: "/customers"

Path Variable: None

Request Body: None

HTTP Method: Get

Description: Returns a list of all the customers as a list of CustomerResponseDto.

# Get all Loans for one customer
URL: "/customer/loans/{id}"

Path Variable: id

Request Body: None

HTTP Method: Get

Description: Returns a list of all the loans as a list of LoanResponseDto.

# Get all Tickets for one customer
URL: "/customer/tickets/{id}"

Path Variable: id

Request Body: None

HTTP Method: Get

Description: Returns a list of all the tickets as a list of TicketResponseDto.
