# Loan Service
## Create a Loan
URL: /loans

Path Variable: None 

Request Body: LoanRequestDto loanRequestDto 

HTTP Method: POST

Description: Create a loan that is stored in the loan repository. The service returns a LoanResponseDto object of the loan created.

## Read a loan
URL: /loans/{id}

Path Variable: id

Request Body: None

HTTP Method: GET

Description: Read a loan stored in the loan repository, found using its id. The service returns a LoanResponseDto object of the loan found.

## Delete a loan
URL: "/loans/delete/{id}/", "/loans/delete/{id}"

Path Variable: id

Request Body: None

HTTP Method: Post

Description: Delete a loan using the service method delete. The artwork and customer have the loan removed from their lists. Return a LoanResponseDto of the same loan.

## Deny a loan
URL: "/loans/deny/{id}", "/loans/deny/{id}"

Path Variable: id: The ID of the loan.

Request Body: none

HTTP Method: Post

Description: Take a loanId as input and find it from the repository. This status will be checked and the loan will be denied depending on if the status is Approved or Requested. A LoanResponseDto is returned from the loan.

## Approve a Loan
URL: "/loans/approve/{id}", "/loans/approve/{id}"

Path Variable: id: The ID of the loan.

Request Body: None

HTTP Method: Post

Description: Set the status of the loan with loanId to Approved, but will only change if the status is requested. Returns a LoanResponseDto of the updated loan.

## Return an Artwork and End loan
URL: "/loans/return/{id}", "/loans/return/{id}"

Path Variable: id: the ID of the loan

Request Body: None

HTTP Method: Post

Description: Find a loan from the loan id and set the status to returned. Only change if the loan is currently approved. Return a LoanResponseDto of the loan.

## Get all Loans
URL: "/loans"

Path Variable: None

Request Body: None

HTTP Method: Get

Description: Returns a list of all the loans as a list of LoanResponseDto.