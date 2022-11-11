package ca.mcgill.ecse321.MuseumBackend.controller;

import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.dto.ShiftDto;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;
import ca.mcgill.ecse321.MuseumBackend.service.PersonService;
import ca.mcgill.ecse321.MuseumBackend.service.ShiftService;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ShiftRestController {
    @Autowired
    private ShiftService service;

    @GetMapping(value = {"/museum/shifts/{workDayId}","/museum/shifts/{workDayId}/"})
    public ShiftDto getShiftById(@PathVariable("workDayId") int workDayId) throws IllegalArgumentException {
        return convertToDto(service.getShiftById(workDayId));
    }

    @GetMapping(value = {"/museum/shifts/{workDayId}", "/museum/shifts/{workDayId}/"})
    public List<EmployeeResponseDto> getShiftEmployeesById(@PathVariable("workDayId") int workDayId) throws IllegalArgumentException{
    ShiftDto shiftDto = convertToDto(service.getShiftById(workDayId));
        return shiftDto.getEmployees();
    }


    @PostMapping(value = {"/museum/shifts/{workDayId}", "/museum/shifts/{workDayId}/"})
    public ShiftDto createShift(@PathVariable("workDayId") int workDayId, @RequestParam Date startTime, @RequestParam Date endTime, @RequestParam Museum museum) throws IllegalArgumentException {
        Shift shift = service.createShift(new Shift(startTime, endTime, workDayId, museum));
        return convertToDto(shift);
    }

    private ShiftDto convertToDto(Shift s) {
        if(s==null) {
            throw new IllegalArgumentException("There is no such Shift!");
        }
        ShiftDto shiftDto = new ShiftDto(s.getStartTime(), s.getEndTime(), s.getWorkDayId(), s.getMuseum(), s.getEmployees());
        return shiftDto;
    }

}
