package ca.mcgill.ecse321.MuseumBackend.service;


import ca.mcgill.ecse321.MuseumBackend.model.Employee;
import ca.mcgill.ecse321.MuseumBackend.model.Museum;
import ca.mcgill.ecse321.MuseumBackend.model.Person;
import ca.mcgill.ecse321.MuseumBackend.model.Shift;
import ca.mcgill.ecse321.MuseumBackend.repository.EmployeeRepository;
import ca.mcgill.ecse321.MuseumBackend.repository.ShiftRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestShiftService {

    @Mock
    ShiftRepository shiftRepository;
    @Mock
    EmployeeRepository employeeRepository;


    @InjectMocks
    ShiftService shiftService;


    private Shift initializeTestShift() {
        Museum museum = new Museum();
        museum.setMuseumId(12);
        double sixHoursInMillisecond = Double.parseDouble("2.16e+7");
        long workHours = (long) sixHoursInMillisecond;
        Date startTime = new Date(Calendar.getInstance().getTimeInMillis());
        Date endTime = new Date(Calendar.getInstance().getTimeInMillis() + workHours);
        return new Shift(startTime, endTime, 54, museum);
    }

    @Test
    public void testCreateShift() {
        Shift shift = initializeTestShift();
        when(shiftRepository.save(shift)).thenAnswer((InvocationOnMock invocation) -> shift);
        assertEquals(shift, shiftService.createShift(shift));
    }

    @Test
    public void testFindShiftById() {
        Shift shift = initializeTestShift();
        when(shiftRepository.findShiftByWorkDayId(shift.getWorkDayId())).thenAnswer((InvocationOnMock invocation) -> shift);
        assertEquals(shift.getWorkDayId(), shiftService.getShiftById(shift.getWorkDayId()).getWorkDayId());
    }

    @Test
    public void testChangeShiftDate() {
        Shift shift0 = initializeTestShift();
        Date startTime = shift0.getStartTime();
        Date endTime = shift0.getEndTime();
        when(shiftRepository.findShiftByWorkDayId(shift0.getWorkDayId())).thenAnswer((InvocationOnMock invocation) -> shift0);
        Date startTime1 = new Date(5);
        Date endTime1 = new Date(6);
        shiftService.changeShiftDate(shift0.getWorkDayId(), startTime1, endTime1);
        assertEquals(54, shift0.getWorkDayId());
        assertNotEquals(startTime,shift0.getStartTime());
        assertNotEquals(endTime,shift0.getEndTime());


    }

    @Test
    public void testAddEmployeeToShift() {
        Shift shift0 = initializeTestShift();
        Museum museum = shift0.getMuseum();
        when(shiftRepository.findShiftByWorkDayId(shift0.getWorkDayId())).thenAnswer((InvocationOnMock invocation) -> shift0);
        Person Sam = new Person("email","password","Sam",museum);
        Sam.addPersonRole(new Employee(5,Sam));
        when(employeeRepository.findEmployeeByPersonRoleId(Sam.getPersonRole(0).getPersonRoleId())).thenAnswer((InvocationOnMock invocation) -> Sam.getPersonRole(0));
        shiftService.addEmployeeToShift(shift0.getWorkDayId(),Sam.getPersonRole(0).getPersonRoleId());
        assertEquals(Sam.getPersonRole(0).getPersonRoleId(), shift0.getEmployee(0).getPersonRoleId());
    }

    @Test
    public void testRemoveEmployeeFromShift() {
        Shift shift0 = initializeTestShift();
        Museum museum = shift0.getMuseum();
        when(shiftRepository.findShiftByWorkDayId(shift0.getWorkDayId())).thenAnswer((InvocationOnMock invocation) -> shift0);
        Person Sam = new Person("email","password","Sam",museum);
        Sam.addPersonRole(new Employee(5,Sam));
        when(employeeRepository.findEmployeeByPersonRoleId(Sam.getPersonRole(0).getPersonRoleId())).thenAnswer((InvocationOnMock invocation) -> Sam.getPersonRole(0));
        shiftService.addEmployeeToShift(shift0.getWorkDayId(), Sam.getPersonRole(0).getPersonRoleId());
        Person Brock = new Person("email0","password","Brock",museum);
        Brock.addPersonRole(new Employee(6,Brock));
        when(employeeRepository.findEmployeeByPersonRoleId(Brock.getPersonRole(0).getPersonRoleId())).thenAnswer((InvocationOnMock invocation) -> Brock.getPersonRole(0));
        shiftService.addEmployeeToShift(shift0.getWorkDayId(), Brock.getPersonRole(0).getPersonRoleId());
        Person Stacy = new Person("email1","password","Stacy",museum);
        Stacy.addPersonRole(new Employee(7,Stacy));
        when(employeeRepository.findEmployeeByPersonRoleId(Stacy.getPersonRole(0).getPersonRoleId())).thenAnswer((InvocationOnMock invocation) -> Stacy.getPersonRole(0));
        shiftService.addEmployeeToShift(shift0.getWorkDayId(), Stacy.getPersonRole(0).getPersonRoleId());
        int brockEmployeeIndex = shift0.getEmployees().indexOf(Brock.getPersonRole(0));
        shiftService.removeEmployeeFromShift(shift0.getWorkDayId(), (Employee) Brock.getPersonRole(0));
        assertNotEquals(shift0.getEmployee(brockEmployeeIndex).getPersonRoleId(), Brock.getPersonRole(0).getPersonRoleId());
        assertNotEquals(-1, shift0.getEmployees().indexOf(Sam.getPersonRole(0)));
        assertNotEquals(-1, shift0.getEmployees().indexOf(Stacy.getPersonRole(0)));
        assertEquals(Sam.getPersonRole(0).getPersonRoleId(), shift0.getEmployee(shift0.getEmployees().indexOf(Sam.getPersonRole(0))).getPersonRoleId());
        assertEquals(Stacy.getPersonRole(0).getPersonRoleId(), shift0.getEmployee(shift0.getEmployees().indexOf(Stacy.getPersonRole(0))).getPersonRoleId());
    }

    @Test
    public void testDeleteShift() {
        Shift shift0 = initializeTestShift();
        when(shiftRepository.findShiftByWorkDayId(shift0.getWorkDayId())).thenAnswer((InvocationOnMock invocation) -> shift0);
        shiftService.deleteShift(shift0.getWorkDayId());
        assertNull(shift0.getMuseum());
    }

}
