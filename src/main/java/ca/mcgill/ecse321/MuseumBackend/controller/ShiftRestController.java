package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.ShiftRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class ShiftRestController {
    @Autowired
    private ShiftService service;

    @GetMapping(value = "/shift/{workDayId}")
    public ResponseEntity<ShiftResponseDto> getShiftById(@PathVariable int workDayId) throws IllegalArgumentException {
        return new ResponseEntity<>(convertToResponseDto(service.getShiftById(workDayId)), HttpStatus.OK);
    }

    //@PutMapping(value = "/shifts/{workDayId}")
    //public ShiftDto addEmployeeToShift(@PathVariable("workDayId") int workDayId, @RequestParam EmployeeRequestDto employee) throws IllegalArgumentException {
        //Employee modelEmployee = employee.toModel();
        //return convertToDto(service.addEmployeeToShift(workDayId, modelEmployee));
    //}


    //@PutMapping(value = "/shifts/{workDayId}")
    //public ShiftDto removeEmployeeFromShift(@PathVariable("workDayId") int workDayId, @RequestParam EmployeeRequestDto employee) throws IllegalArgumentException {
        //Employee modelEmployee = employee.toModel();
        //return convertToDto(service.removeEmployeeFromShift(workDayId, modelEmployee));
    //}


    @DeleteMapping(value = "/shifts/{workDayId}")
    public void deleteShift(@PathVariable("workDayId") int workDayId) throws IllegalArgumentException {
        service.deleteShift(workDayId);
    }



    //@GetMapping(value = "/shifts/{workDayId}/")
    //public List<EmployeeResponseDto> getShiftEmployeesById(@PathVariable("workDayId") int workDayId) throws IllegalArgumentException{
    //ShiftDto shiftDto = convertToDto(service.getShiftById(workDayId));
        //return shiftDto.getEmployees();
    //}



    @PostMapping("/shifts")
    public ResponseEntity<ShiftResponseDto> createShift(@RequestBody ShiftRequestDto shiftRequestDto) throws IllegalArgumentException {
        return new ResponseEntity<>(convertToResponseDto(service.createShift(shiftRequestDto.toModel())), HttpStatus.CREATED);
    }
    private ShiftResponseDto convertToResponseDto(Shift s) {
        if(s==null) {
            throw new IllegalArgumentException("There is no such Shift!");
        }
        ShiftResponseDto shiftResponseDto = new ShiftResponseDto(s.getStartTime(), s.getEndTime(), s.getWorkDayId(), s.getMuseum(), s.getEmployees());
        return shiftResponseDto;
    }

}
