package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShiftRequestDto {
    @Autowired
    private EmployeeService employeeService;
    private int workDayId;
    private Date startTime;
    private Date endTime;

    private Museum museum;
    private List<Integer> employeeIds = new ArrayList<>();

    public ShiftRequestDto(Date startTime, Date endTime, int workDayId, Museum museum) {
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getWorkDayId() {
        return workDayId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public void setEndTime(Date endTime) {
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
