# Shift REST Controller
## Create a Shift

URL: /shift

Request Body: ShiftRequestDto

HTTP Method: POST

Description: Takes a shift request transfer object with the details corresponding to a Shift object to be created, and returns a shift response transfer object with the details of the created shift in the body of a ResponseEntity.

## Read a Shift

URL: /shift/{workDayId}

Path Variable: int workDayId

HTTP Method: GET

Description: Takes an identifying integer corresponding to a shift from the shift repository from the request to find the corresponding shift, and returns a shift response transfer object with the details of the requested Shift object in the body of a ResponseEntity.

## Change Shift date

URL: /shift/{workDayId}/

Path Variable: int workDayId

Request Body: Map<String,String>

HTTP Method: PUT

Description: Takes an identifying integer corresponding to a shift from the shift repository and a map holding strings representing dates, and returns a shift response tranfer object with the details of the Shift object with the new start and end dates.

## Add Employees to Shift

URL: /shift/employees/{workDayId}

Path Variable: int workDayId

Request Body: Integer

HTTP Method: POST

Description: Takes 2 identifying integers corresponding to a shift and employee from the shift repository and employee repository respectively from the request to add that employee to the shift, and returns a shift response transfer object with the details of the Shift object with the newly added employee in the body of a ResponseEntity.

## Remove Employees from Shift

URL: /shift/employees/{workDayId}

Path Variable: int workDayId

Request Body: Integer

HTTP Method: PUT

Description: Takes 2 identifying integers corresponding to a shift and employee from the shift repository and employee repository respectectively from the request to remove that employee from the shift.

## Get All Shift's Employees' Ids

URL: /shift/employees/{workDayId}

Path Variable: int workDayId

HTTP Method: GET

Description: Takes an identfying integer corresponding to a shift from the shift repository from the request, and returns a list of Integers corresponding to all the id of the employees assigned to the shift in the body of a ResponseEntity.

## Delete Shift

URL: /shift/{workDayId}

Path Variable: int workDayId

HTTP Method: PUT

Description: Takes an identfying integer corresponding to a shift from the shift repository from the request and deletes that shift.

