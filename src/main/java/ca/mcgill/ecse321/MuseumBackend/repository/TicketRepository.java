package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Integer>{

	Ticket findTicketByTicketId(int aTicketId);
	
}