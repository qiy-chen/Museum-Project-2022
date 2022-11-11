package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public class ShiftDto {

	private int workdayId;
	private Date startTime;
	private Date endTime;

	private Museum museum;
	private List<EmployeeResponseDto> employees;
	
	public ShiftDto(Date startTime, Date endTime, int workDayId, Museum museum, List<Employee> employees) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.workdayId = workDayId;
		this.museum = museum;
		for(Employee e : employees) {
			EmployeeResponseDto employee = new EmployeeResponseDto(e);
			this.employees.add(employee);
		}

	}
	
}
