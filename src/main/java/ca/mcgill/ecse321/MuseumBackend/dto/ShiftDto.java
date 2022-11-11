package ca.mcgill.ecse321.MuseumBackend.dto;

import java.sql.Date;

import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;

public class ShiftDto {

	private int workdayId;
	private Date startTime;
	private Date endTime;

	private Museum museum;
	
	public ShiftDto(Date startTime, Date endTime, int workDayId, Museum museum) {
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.workdayId = workDayId;
		this.museum = museum;

	}
	
}
