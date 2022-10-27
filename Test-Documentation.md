### Ticket Persistence Test
An instance of a museum, a customer and a ticket is created with specific attributes.

They are saved to their respective repositories. They are then set to null before starting the test.

The ticket reference is found using the repository find function, findTicketByTicketId(ticketId), and the test checks if each attribute retrieved of the class and each associated class are correct compared to the values that were set up at the start.

### Shift Persistence Test
An instance of a museum and a work shift is created with specific attributes.

They are saved to their respective repositories. They are then set to null before starting the test.

The ticket reference is found using the repository find function, findShiftByShiftId(shiftId), and the test checks if each attribute retrieved of the class and each associated class are correct compared to the values that were set up at the start.

