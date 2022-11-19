package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class ShiftRestController {
    @Autowired
    private ShiftService service;

    @GetMapping(value = "/shift/{workDayId}")
    public ResponseEntity<ShiftResponseDto> getShiftById(@PathVariable int workDayId) throws IllegalArgumentException, HttpMessageNotReadableException {
        /**
         * Takes an identifying integer corresponding to a shift from the shift repository from the request to find the corresponding
         * shift, and returns a shift response transfer object with the details of the requested Shift object in the body of a ResponseEntity
         * @param workDayId An identifying integer equal to the value corresponding to a shift
         * @return A ResponseEntity with a body of a shift response transfer object with the details of the requested Shift object
         */
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(convertToResponseDto(service.getShiftById(workDayId)), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/shift/employees/{workDayId}")
    public ResponseEntity<ShiftResponseDto> addEmployeeToShift(@PathVariable int workDayId, @RequestBody int employeeId) throws IllegalArgumentException {
        /**
         * Takes 2 identifying integers corresponding to a shift and employee from the shift repository and employee repository respectively
         * from the request to add that employee to the shift, and returns a shift response transfer object with the details of the Shift object
         * with the newly added employee in the body of a ResponseEntity
         * @param workDayId An identifying integer equal to the value corresponding to a shift
         * @param employeeId An identifying integer equal to the value corresponding to an employee
         * @return 
         */
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(convertToResponseDto(service.addEmployeeToShift(workDayId,employeeId)), httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "shift/employees/{workDayId}")
    public ResponseEntity<Integer[]> getAllShiftEmployeeIds(@PathVariable int workDayId) throws IllegalArgumentException {
        return new ResponseEntity<>(convertToResponseDto(service.getShiftById(workDayId)).getEmployees().toArray(new Integer[0]),HttpStatus.OK);
    }


    @PutMapping(value = "/shift/{workDayId}/")
    public void changeShiftDate(@PathVariable int workDayId, @RequestBody Map<String, String> dateMap) throws IllegalArgumentException {
        service.changeShiftDate(workDayId, dateMap.get("startTimeValue"),dateMap.get("endTimeValue"));

    }


    @PutMapping(value = "/shift/{workDayId}")
    public void deleteShift(@PathVariable int workDayId) throws IllegalArgumentException {
        service.deleteShift(workDayId);
    }

    @PutMapping(value = "/shift/employees/{workDayId}")
    public void removeEmployeeFromShift(@PathVariable int workDayId, @RequestBody int employeeId) throws IllegalArgumentException {
        service.removeEmployeeFromShift(workDayId,employeeId);
    }


    @PostMapping(value = "/shift")
    public ResponseEntity<ShiftResponseDto> createShift(@RequestBody ShiftRequestDto shiftRequestDto) throws IllegalArgumentException {
        /**
         * Takes a shift request transfer object with the details corresponding to a Shift object to be created,
         * and returns a shift response transfer object with the details of the created shift in the body of a ResponseEntity
         * @param shiftRequestDto a shift request transfer object with the details corresponding to a Shift object to be created
         * @return A ResponseEntity with a body of a shift response transfer object with the details of the created Shift object
         */
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(convertToResponseDto(service.createShift(shiftRequestDto.toModel())), httpHeaders,HttpStatus.CREATED);
    }
    private ShiftResponseDto convertToResponseDto(Shift s) {
        /**
         * Takes a Shift object to create a new shift response transfer object with corresponding details
         * @param s A Shift object to be modeled for the shift response transfer object
         * @return A shift response transfer object with the details corresponding to the inputted Shift object
         */
        if(s==null) {
            throw new IllegalArgumentException("There is no such Shift!");
        }
        ShiftResponseDto shiftResponseDto = new ShiftResponseDto(s.getStartTime(), s.getEndTime(), s.getWorkDayId(), s.getMuseum(), s.getEmployees());
        return shiftResponseDto;
    }

}
