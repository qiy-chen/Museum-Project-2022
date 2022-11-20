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

HTTP Method: PUT

