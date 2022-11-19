package ca.mcgill.ecse321.MuseumBackend.dto;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftResponseDto {

	private int workDayId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private Museum museum;
	private List<Integer> employeeIds = new ArrayList<>();
	
	public ShiftResponseDto() {

	}

	public ShiftResponseDto(LocalDateTime startTime, LocalDateTime endTime, int workDayId, Museum museum, List<Employee> employees) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.workDayId = workDayId;
		this.museum = museum;
			for (Employee e : employees) {
				Integer employeeId = e.getPersonRoleId();
				this.employeeIds.add(employeeId);
			}
		}

	public ShiftResponseDto(Shift shift) {
		
		this.startTime = shift.getStartTime();
		this.endTime = shift.getEndTime();
		this.workDayId = shift.getWorkDayId();
		this.museum = shift.getMuseum();
			for (Employee e : shift.getEmployees()) {
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

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
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
