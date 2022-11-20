# Employee Service
## Create an employee
URL: /employees

Path Variable: None 

Request Body: employeeRequestDto employeeRequestDto 

HTTP Method: POST

Description: Create an employee that is stored in the employee repository. The service returns a employeeResponseDto object of the employee created.

## Read an Employee
URL: /employee/{id}

Path Variable: id

Request Body: None

HTTP Method: GET

Description: Read a employee stored in the employee repository, found using its id. The service returns a employeeResponseDto object of the employee found.

## Delete a employee
URL: "/employee/{id}"

Path Variable: id

Request Body: None

HTTP Method: Delete

Description: Delete a employee using the service method delete. The artwork and employee have the employee removed from their lists. Return a employeeResponseDto of the same employee.

## Get all employees
URL: "/employees"

Path Variable: None

Request Body: None

HTTP Method: Get

Description: Returns a list of all the employees as a list of employeeResponseDto.

## Get all Shifts for one employee
URL: "/employee/shifts/{id}"

Path Variable: id

Request Body: None

HTTP Method: Get

Description: Returns a list of all the shifts as a list of ShiftResponseDto.
