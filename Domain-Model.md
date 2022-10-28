# Class Diagram
![unnamed](https://user-images.githubusercontent.com/98911345/198682819-ee9ca272-d856-421a-907f-9653b0441905.jpg)

# Design Choices 
Our design team made three key design decisions for the domain model of the Museum Management System. First, users and their role have a player-role relationship for flexible functionality such as changing roles, having multiple roles, or allowing roles to have a distinct features (such as allowing customers to be associated with tickets). Secondly, tickets and shifts have dates to allow control over the museum schedule using controller methods. Finally, artworks go into rooms which are either a display room with a limited size or the storage room which has no size limit. By making the display and storage tooms subclasses distributes their shared charactersitics while giving a limit to the number of artworks to only the display rooms.

The classes Artwork, Loan, Person, Ticket, Room are all contained inside the Museum Class using compositions.