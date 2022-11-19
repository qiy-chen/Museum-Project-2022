# Admin Service
## Create an Admin
URL: /admin

Path Variable: None 

Request Body: AdminRequestDto adminRequest

HTTP Method: POST

Description: Creates an admin role and applies it to an existing person in the databse.

## Read an admin
URL: /admin/{id}

Path Variable: id

Request Body: None

HTTP Method: GET

Description: Read an admin from the database, found using its identifier "PersonRoleId". The service returns an AdminResponseDto object of the admin found.

## Delete an admin
URL: "/admin/{id}

Path Variable: id

Request Body: None

HTTP Method: Delete

Description: Delete an Admin given their ID.
