# Plan

We plan to do white-box testing for the unit tests and integration tests. Our unit tests check if service methods work, using a test stub. The test stub is a Mock repository which returns expected outputs when prompted by the service methods.

#### For each unit test

We plan to have at least 95% coverage on all essential lines of the service class that it is testing on.

We plan to test at least 90% of the branches present in the service class that it is testing on.

#### For each integration test

We plan to have at least 95% coverage on all essential lines of the service class and controller class that it is testing on.

We also plan to test at least 90% of the possible branches in the service class and controller class that it is testing on. The branch which leads to a successful operation must be covered by the test.

### Overall Coverage Summary
![](https://media.discordapp.net/attachments/1025068314185187401/1043688345307463771/Screenshot_2022-11-19_19-42-40.png?width=1440&height=306)

# Report

### Unit Tests

#### Ticket
Coverage: 98,2% on TicketService.java

Branches: 19/20 branches on TicketService.java

### Integration Tests

#### Ticket
Coverage: 99,1% on TicketService.java

100% on TicketController.java

Branches: 19/20 branches on TicketService.java

2/2 branches on TicketController.java