package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;

public class ShiftResponseDto {

	private int workDayId;
	private Date startTime;
	private Date endTime;

	private Museum museum;
	private List<Integer> employeeIds = new ArrayList<>();
	
	public ShiftResponseDto() {

	}

	public ShiftResponseDto(Date startTime, Date endTime, int workDayId, Museum museum, List<Employee> employees) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.workDayId = workDayId;
		this.museum = museum;
			for (Employee e : employees) {
				Integer employeeId = e.getPersonRoleId();
				this.employeeIds.add(employeeId);
			}
		}




	public int getWorkDayId() {
		return workDayId;
	}

	public void setWorkdayId(int workdayId) {
		this.workDayId = workdayId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Museum getMuseum() {
		return museum;
	}

	public void setMuseum(Museum museum) {
		this.museum = museum;
	}
	public List<Integer> getEmployees() {
		return employeeIds;
	}

	public void setEmployees(List<Integer> employeeIds) {
		this.employeeIds = employeeIds;
	}
}
