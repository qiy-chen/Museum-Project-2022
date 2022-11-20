# Person REST Controller
## Read a Person

URL: /person/{email}

Path Variable: String email

HTTP Method: GET

Description: Takes an identifying string corresponding to a person from the person repository from the request, and returns a person response transfer object with the details of the corresponding Person object in the body of a ResponseEntity.

## Create a Person

URL: /person

Request Body: PersonRequestDto

HTTP Method: POST

Description: Takes a person request transfer object with the details corresponding to a Person object to be created, and returns a person response transfer object with the details of the created person in the body of a ResponseEntity.

## Change Person Name and/or Password

URL: /person/{email}/

Path Variable: String email

Request Body: Map<String,String>

HTTP Method: PUT

Description: Takes an identifying string corresponding to a person from the person repository and a map holding strings representing a new name and/or password, and returns a person response tranfer object with the details of the Person object with the new name and password.

## Get Person's Roles

URL: /person/person_roles/{email}

Path Variable: String email

HTTP Method: GET

Description: Takes an identfying string corresponding to a person from the person repository from the request, and returns a list of Integers corresponding to all the ids of the roles assigned to the person in the body of a ResponseEntity.

## Delete Person

URL: /person/{email}

Path Variable: String email

HTTP Method: PUT

Description:  Takes an identfying string corresponding to a person from the person repository from the request and deletes that person.