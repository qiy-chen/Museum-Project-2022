# Deliverable 1 Report 

## Key Design Decisions
**Project Management:** Our group chose to create one branch per group member, and to divide tasks by classes so that it would be simple to merge changes, and to prevent overlap in individual work. We scheduled meetings with strong milestones between meetings, where individual tasks like the JPA annotation for different classes were all completed so we could merge all work done and proceed to next steps which are reliant on that milestone.  

**Domain Model:** Our design team made three key design decisions for the domain model of the Museum Management System. First, users and their role have a player-role relationship for flexible functionality such as changing roles, having multiple roles, or allowing roles to have a distinct features (such as allowing customers to be associated with tickets). Secondly, tickets and shifts have dates to allow control over the museum schedule using controller methods. Finally, artworks go into rooms which are either a display room with a limited size or the storage room which has no size limit. By making the display and storage rooms subclasses distributes their shared characteristics while giving a limit to the number of artworks to only the display rooms.  

**Testing:** While testing, we set and get the primary keys of objects to check for successful writing to and reading from the database. We create associated objects to check the persistence of object references. Our model has many two way references to make retrieval of associated objects easy, and uses hibernate annotations to enable removal of certain foreign keys from a table when deleting objects to allow the clearing of such two-way related tables.

## Meetings

### Meeting 1

Date: Oct 15

Summary:
* Present: everyone
* Created first draft of domain model, and established sprint 1 timeline
* Action items: 
  * complete requirements by next meeting

### Meeting 2

Date: Oct 18

Summary:
* Present: Sam, Emma, Alex, Jeanine, Qi
* Checked and prioritized all requirements, and distributed top 6 requirements for use-case diagrams
* Action items: 
  * Log top 15 requirements as issues on GitHub
  * Finish use-case diagrams and specifications

### Meeting 3

Date: Oct 23

Summary:
* Present: Sam, Emma, Alex, Elie, Jeanine
* Checked use-case diagrams and specifications and added them into the wiki, finished requirements, finalized domain model, distributed tests and JPA annotations
* Action items:
  * Alex will change user titles for consistency throughout diagrams and requirements
  * Sam will initiate the project using Spring and setup the build dependencies
  * Jeanine will reformat and organize the wiki

### Meeting 4

Date: Oct 26 at 7pm

* Present: everyone
* Fixed domain model, created all crud repositories and JPA annotations, finished project report and domain model description
* Action items:
  * each member will complete their assigned persistence tests

### Meeting 5

Date: Oct 26 at 7pm

* Present: everyone
* final check and deliverable 1 submission