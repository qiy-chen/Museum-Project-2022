package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.Exception.MuseumBackendException;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketRequestDto;
import ca.mcgill.ecse321.MuseumBackend.dto.TicketResponseDto;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TestTicketService {

	// Replace the repository with a "mock" that exposes the same interface
	@Mock
	TicketRepository ticketRepo;
	
	// Get a service that uses the mock repository
	@InjectMocks
	TicketService ticketService;
	
	@Test
	public void testGetTicketById() {
		// Tell the mocked repository how to behave
		final int id = 1;
		final Ticket ticketOne = new Ticket();
		ticketOne.setTicketId(id);
		when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);
		
		// Test that the service behaves properly
		TicketResponseDto ticket = ticketService.getTicketByTicketId(id);
		
		assertNotNull(ticket);
		assertEquals(id, ticket.getTicketId());
	}
	
	@Test
	public void testGetTicketByInvalidId() {
		final int invalidId = 99;
		
		// Mock: if asking for a ticket with invalid ID, return null
		when(ticketRepo.findTicketByTicketId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
		
		// call method, and obtain resulting exception
		MuseumBackendException ex = assertThrows(MuseumBackendException.class, () -> ticketService.getTicketByTicketId(invalidId));
		
		// check results
		assertEquals("ticket not found.", ex.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testCreateticket() {
		// Mock: just return the ticket with no modification
		when(ticketRepo.save(any(Ticket.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		// test set up
		int id = 1;
		TicketRequestDto ticketOneDto = new TicketRequestDto();
		ticketOneDto.setTicketId(id);
		
		int customerId = 2;
		int museumId = 3;
		
		// call method
		TicketResponseDto returnedticket = ticketService.createTicket(ticketOneDto,customerId,museumId);
		
		// check results
		assertNotNull(returnedticket);
		assertEquals(id, returnedticket.getTicketId());
		// Check that the service actually saved the ticket
		verify(ticketRepo, times(1)).save(ticketOne);
	}
	
}
