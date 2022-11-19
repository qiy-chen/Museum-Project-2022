package ca.mcgill.ecse321.MuseumBackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.MuseumBackend.exception.TicketException;
import ca.mcgill.ecse321.MuseumBackend.model.Customer;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import ca.mcgill.ecse321.MuseumBackend.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TestTicketService {

  // Replace the repository with a "mock" that exposes the same interface
  @Mock
  TicketRepository ticketRepo;
  
  // Get a service that uses the mock repository
  @InjectMocks
  @Spy
  TicketService ticketService;
  
  @Mock
  CustomerService customerService;

  @Test
  public void testGetTicketById() {
    // Tell the mocked repository how to behave
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);

    // Test that the service behaves properly
    Ticket ticket = ticketService.getTicketByTicketId(id);

    assertNotNull(ticket);
    assertEquals(id, ticket.getTicketId());
  }

  @Test
  public void testGetTicketByInvalidId() {
    final int invalidId = 99;

    // Mock: if asking for a ticket with invalid ID, return null
    when(ticketRepo.findTicketByTicketId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.getTicketByTicketId(invalidId));

    // check results
    assertEquals("Ticket not found.", ex.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
  }

  @Test
  public void testGetAllTickets() {
    // Tell the mocked repository how to behave
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);

    final int id1 = 2;
    final Ticket ticketTwo = new Ticket();
    ticketTwo.setTicketId(id1);

    final int id2 = 3;
    final Ticket ticketThree = new Ticket();
    ticketThree.setTicketId(id2);
    
    List<Ticket> ticketList = new ArrayList<Ticket>();
    ticketList.add(ticketOne);
    ticketList.add(ticketTwo);
    ticketList.add(ticketThree);
    when(ticketRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> ticketList);

    // Test that the service behaves properly
    List<Ticket> tickets = ticketService.getAllTickets();

    assertEquals(3,tickets.size());
    assertEquals(id1, tickets.get(1).getTicketId());
  }

  @Test
  public void getAllTicketsFromCustomer() {
    int customerId = 10;
    Customer customer1 = new Customer();
    customer1.setPersonRoleId(customerId);
    
    
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    ticketOne.setCustomer(customer1);

    final int id1 = 2;
    final Ticket ticketTwo = new Ticket();
    ticketTwo.setTicketId(id1);
    ticketTwo.setCustomer(customer1);

    int customerId2 = 20;
    Customer customer2 = new Customer();
    customer2.setPersonRoleId(customerId2);
    final int id2 = 3;
    final Ticket ticketThree = new Ticket();
    ticketThree.setTicketId(id2);
    ticketThree.setCustomer(customer2);
   
    List<Ticket> ticketList = new ArrayList<Ticket>();
    ticketList.add(ticketOne);
    ticketList.add(ticketTwo);
    ticketList.add(ticketThree);
    when(ticketRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> ticketList);

    // Test that the service behaves properly
    List<Ticket> tickets = ticketService.getAllTicketsFromCustomer(customerId);
    assertEquals(2, tickets.size());
    
    assertEquals(customerId, tickets.get(0).getCustomer().getPersonRoleId());
  }

  @Test
  public void testCreateTicket() {
    // Mock: just return the ticket with no modification
    when(ticketRepo.save(any(Ticket.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

    // test set up
    final Ticket ticket = new Ticket();
    int id = ticket.getTicketId();
    ticket.setTicketDate(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0));
    ticket.setPrice(10.00);

    // call method
    Ticket returnedticket = ticketService.createTicket(ticket);

    // check results
    assertNotNull(returnedticket);
    assertEquals(id, returnedticket.getTicketId());
    // Check that the service actually saved the ticket
    verify(ticketRepo, times(1)).save(ticket);
  }
  @Test
  public void testCreateInvalidTicket() {
    // test set up
    final Ticket ticket = new Ticket();
    ticket.setTicketId(9);
    ticket.setTicketDate(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0));
    ticket.setPrice(-1);

    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.createTicket(ticket));

    // check results
    assertEquals("The ticket to be created contains invalid data.", ex.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
  }
  
  @Test
  public void testCreateInvalidTicket2() {
    // test set up
    final Ticket ticket = new Ticket();
    ticket.setTicketId(9);
    ticket.setPrice(10);

    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.createTicket(ticket));

    // check results
    assertEquals("The ticket to be created contains invalid data.", ex.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
  }

  
  @Test
  public void testReplaceTicket() {
    // Tell the mocked repository how to behave
    when(ticketRepo.save(any(Ticket.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    ticketOne.setTicketDate(LocalDateTime.of(1990,Month.JANUARY, 1, 0, 0, 0));
    ticketOne.setPrice(1.00);
    //ticketOne is stored in repo
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);

    final int id1 = 2;
    final Ticket ticketTwo = new Ticket();
    ticketTwo.setTicketId(id1);
    ticketTwo.setTicketDate(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0));
    ticketTwo.setPrice(10.00);
    
    // Test that the service behaves properly
    Ticket ticket = ticketService.replaceTicket(ticketTwo, id);

    assertNotNull(ticket);
    assertEquals(id, ticket.getTicketId());
    assertEquals(10.00,ticket.getPrice());
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),ticket.getTicketDate());
  }
  @ExtendWith(MockitoExtension.class)
  @MockitoSettings(strictness = Strictness.LENIENT)
  @Test
  public void testReplaceTicket2() {
    // Tell the mocked repository how to behave
    when(ticketRepo.save(any(Ticket.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    
    final int id = 999999;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    //ticketOne is stored in repo
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> null);

    final int id1 = 2;
    final Ticket ticketTwo = new Ticket();
    ticketTwo.setTicketId(id1);
    ticketTwo.setTicketDate(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0));
    ticketTwo.setPrice(10.00);
    
    // Test that the service behaves properly
    Ticket ticket = ticketService.replaceTicket(ticketTwo, id);

    assertNotNull(ticket);
    assertEquals(id, ticket.getTicketId());
    assertEquals(10.00,ticket.getPrice());
    assertEquals(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0),ticket.getTicketDate());
  }
  @Test
  public void testReplaceInvalidTicket() {
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    //ticketOne is stored in repo
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);

    final int id1 = 2;
    final Ticket ticketTwo = new Ticket();
    ticketTwo.setTicketId(id1);
    ticketTwo.setTicketDate(LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0));
    ticketTwo.setPrice(-9);
    
    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.replaceTicket(ticketTwo, id));

    // check results
    assertEquals("The ticket to be created contains invalid data.", ex.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
  }
  @Test
  public void testReplaceInvalidTicket2() {
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    //ticketOne is stored in repo
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);

    final int id1 = 2;
    final Ticket ticketTwo = new Ticket();
    ticketTwo.setTicketId(id1);
    ticketTwo.setPrice(10.00);
    
    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.replaceTicket(ticketTwo, id));

    // check results
    assertEquals("The ticket to be created contains invalid data.", ex.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
  }
  
  @Test
  public void testDeleteTicket() {
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    ticketOne.setTicketId(id);
    //ticketOne is stored in repo
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);
    
    boolean success = ticketService.deleteTicket(id);
    assertEquals(true, success);
  }
  @Test
  public void testDeleteNonExistentTicket() {
    final int id = 1;

    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> null);
    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.deleteTicket(id));

    // check results
    assertEquals("Ticket not found.", ex.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    
  }

    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    public void testCancelTicket() {
      
      // Tell the mocked repository how to behave
      final int id = 1;
      final Ticket ticketOne = new Ticket();
      
      final int customerId = 10;
      final Customer customer = new Customer();
      ticketOne.setTicketId(id);
      customer.setPersonRoleId(customerId);
      ticketOne.setCustomer(customer);

      when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);
      when(customerService.getCustomerById(customerId)).thenAnswer((InvocationOnMock invocation) -> customer);

      
      
      //Set 'current date' to 4 days before the ticket date
      final LocalDateTime ticketDate = LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0);
      ticketOne.setTicketDate(ticketDate);
      final LocalDateTime currentDate = ticketDate.minus(Duration.ofDays(4));


      //Give this 'current date instead'
      Mockito.doReturn(currentDate).when(ticketService).getCurrentDate();
      
      boolean success = ticketService.cancelTicket(id, customerId);
      assertEquals(true, success);
      
      
    }
    
  @ExtendWith(MockitoExtension.class)
  @MockitoSettings(strictness = Strictness.LENIENT)
  @Test
  public void testCancelTicketWrongCustomer() {
    
    // Tell the mocked repository how to behave
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    
    final int customerId = 10;
    final Customer customer = new Customer();
    ticketOne.setTicketId(id);
    customer.setPersonRoleId(customerId);
    ticketOne.setCustomer(customer);
    
    final int customerId1 = 20;
    final Customer customer1 = new Customer();
    customer.setPersonRoleId(customerId1);
    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);
    when(customerService.getCustomerById(customerId1)).thenAnswer((InvocationOnMock invocation) -> customer1);

    
    
    //Set 'current date' to 4 days before the ticket date
    final LocalDateTime ticketDate = LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0);
    ticketOne.setTicketDate(ticketDate);
    
    final LocalDateTime currentDate = ticketDate.minus(Duration.ofDays(4));
    //Give this 'current date instead'
    Mockito.doReturn(currentDate).when(ticketService).getCurrentDate();
    
    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.cancelTicket(id, customerId1));

    // check results
    assertEquals("Wrong customer id.", ex.getMessage());
    assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
  }
  
  @ExtendWith(MockitoExtension.class)
  @MockitoSettings(strictness = Strictness.LENIENT)
  @Test
  public void testCancelTicketTooLate() {
    
    // Tell the mocked repository how to behave
    final int id = 1;
    final Ticket ticketOne = new Ticket();
    
    final int customerId = 10;
    final Customer customer = new Customer();
    ticketOne.setTicketId(id);
    customer.setPersonRoleId(customerId);
    ticketOne.setCustomer(customer);

    when(ticketRepo.findTicketByTicketId(id)).thenAnswer((InvocationOnMock invocation) -> ticketOne);
    when(customerService.getCustomerById(customerId)).thenAnswer((InvocationOnMock invocation) -> customer);

    
    
    //Set 'current date' to 2 days before the ticket date
    final LocalDateTime ticketDate = LocalDateTime.of(2000,Month.JANUARY, 1, 0, 0, 0);
    ticketOne.setTicketDate(ticketDate);
    
    final LocalDateTime currentDate = ticketDate.minus(Duration.ofDays(2));
    //Give this 'current date instead'
    Mockito.doReturn(currentDate).when(ticketService).getCurrentDate();
    
    // call method, and obtain resulting exception
    TicketException ex = assertThrows(TicketException.class, () -> ticketService.cancelTicket(id, customerId));

    // check results
    assertEquals("Too late to make cancelation.", ex.getMessage());
    assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
  }

}
