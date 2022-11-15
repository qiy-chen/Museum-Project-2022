package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;

import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public class ShiftDto {

	private int id;
	private Date startTime;
	private Date endTime;
	
	public ShiftDto(Shift shift) {
		
		this.id = shift.getWorkDayId();
		this.startTime = shift.getStartTime();
		this.endTime = shift.getEndTime();
	}
	
}
