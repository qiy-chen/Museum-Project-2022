package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer>{

	Ticket findTicketByTicketId(int aTicketId);
	
}