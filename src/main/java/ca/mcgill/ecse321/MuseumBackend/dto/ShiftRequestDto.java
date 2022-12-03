package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;
import ca.mcgill.ecse321.MuseumBackend.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShiftRequestDto {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MuseumService museumService;
    private String startTime;
    private String endTime;

    private int museumId;
    private List<Integer> employeeIds = new ArrayList<>();

    public ShiftRequestDto(String startTime, String endTime, int museumId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.museumId = museumId;
    }
    public ShiftRequestDto() {

    }

    public Shift toModel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
        Shift shift = new Shift(LocalDateTime.parse(startTime,formatter), LocalDateTime.parse(endTime,formatter), museumService.getMuseumById(museumId));
        for(Integer e : employeeIds) {
            Employee employee = employeeService.getEmployeeById(e);
            shift.addEmployee(employee);
        }
        return shift;

    }

    public int getMuseumId() {
        return museumId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }
}
