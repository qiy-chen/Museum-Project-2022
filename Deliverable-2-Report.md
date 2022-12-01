## Key Design Decisions
For this deliverable, our group made design decisions about the functionality of our controller. We decided that objects should be created in a specific order such as creating a person account before adding a person role. For example, when creating a customer, you must provide a valid person email for an existing person account. In testing, we decided to improve coverage by testing for all different exceptions using invalid inputs at the service layer, and checking for only for a successful input at the controller layer.
## Build System
To run the application, you can use any command line, where you must open the project, and input the command ./gradlew bootRun. The application will be deployed on a website but the website has not been built yet. 
To build locally with Gradle, cd into the root directory and run the command ```./gradlew build -xtest```. This should give the following output:

> <img width="682" alt="Screen Shot 2022-11-19 at 8 30 41 PM" src="https://user-images.githubusercontent.com/98911345/202878586-9c530d5a-134b-4a9a-a158-1227bff1664b.png">

## Test Run 
As seen below the tests run locally using ```./gradlew test```

![image](https://user-images.githubusercontent.com/98911345/202879412-026de01a-703e-41c4-8244-23b93e490f11.png)


## Meetings
Nov. 2:  
* Present: Sam, Emma, Alex, Jeanine
* Goal: success spectrum assignment completion 
* Topics: finished assignment, chose next meeting, discussed deliverable 2

Nov. 5:  
* Present: Elie, Sam, Emma, Alex, Jeanine
* Topics: completed use case list, assigned classes for controller methods and testing
* Action Items: Everyone will begin assigned service methods and controller methods

Nov. 11:  
* Present: Everyone
* Goal: review controller methods, choose next meeting and tasks
* Topics: Compared DTO organization, created deliverable timeline
* Action Items: 
  * Alex will setup the project backlog
  * Everyone will finish service methods and tests tomorrow, and integration tests and controller methods for Sunday

Nov. 15:
* Present: Sam, Elie, Emma, Alex, Jeanine
* Topics: integration tests debugging

Nov. 19:
* Present: Everyone
* Topics: final merging and documentation 
* Action Items: 
  * Everyone should add their testing documentation to the Wiki
  * Project Backlog should be updated 