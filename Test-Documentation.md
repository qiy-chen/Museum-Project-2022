## Test Overview
For each repository class, a corresponding repository tests class was made containing tests for writing to and reading from the database. Tests also check one class attribute and one reference between classes. Below are examples for two specific tests explaining the general procedure used for all tests:

### Ticket Persistence Test
An instance of a museum, a customer and a ticket is created with specific attributes.

They are saved to their respective repositories. They are then set to null before starting the test.

The ticket reference is found using the repository find function, findTicketByTicketId(ticketId), and the test checks if each attribute retrieved of the class and each associated class are correct compared to the values that were set up at the start.

### Shift Persistence Test
An instance of a museum and a work shift is created with specific attributes.

They are saved to their respective repositories. They are then set to null before starting the test.

The ticket reference is found using the repository find function, findShiftByShiftId(shiftId), and the test checks if each attribute retrieved of the class and each associated class are correct compared to the values that were set up at the start.

## Test Contributions
We divided persistence testing between group members as follows:
* Display room: Emma  
* Storage room: Alex  
* Artwork: Elie  
* Person: Sam  
* Customer: Elie  
* Admin: Emma  
* Employee: Jeanine  
* Ticket: Qi  
* Loan: Jeanine  
* Shift: Qi  
* Museum: Alex  
