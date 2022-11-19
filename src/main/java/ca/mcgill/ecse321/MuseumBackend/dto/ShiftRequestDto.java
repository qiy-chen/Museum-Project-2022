package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftRequestDto {
    @Autowired
    private EmployeeService employeeService;
    private int workDayId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Museum museum;
    private List<Integer> employeeIds = new ArrayList<>();

    public ShiftRequestDto(LocalDateTime startTime, LocalDateTime endTime, int workDayId, Museum museum) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDayId = workDayId;
        this.museum = museum;
    }
    public ShiftRequestDto() {

    }

    public Shift toModel() {
        Shift shift = new Shift(startTime, endTime, workDayId, museum);
        for(Integer e : employeeIds) {
            Employee employee = employeeService.getEmployeeById(e);
            shift.addEmployee(employee);
        }
        return shift;

    }

    public Museum getMuseum() {
        return museum;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getWorkDayId() {
        return workDayId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setWorkdayId(int workdayId) {
        this.workDayId = workdayId;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }
}
