# Requirements Model

## List of requirements

### Functional
1. The system shall allow the admin to create, update and delete employee accounts (hire/fire).
2. The system shall allow the admin to assign and update work shifts to employees.
3. The system shall allow employees and the admin to move, store / display artifacts.
4. The system shall allow anyone to create a visitors's account if they input a non-registered email account, name, and password. Sam:)
5. The system shall allow registered visitors to purchase tickets to visit the museum.
6. The system shall allow registered visitors to request loans for loanable artifacts.
7. The system shall allow employees and admin to manage loan requests.
8. The system shall allow anyone to view all artifacts and their status (loanable/not, on loan/in storage/on display, location if on display).
9. The system shall limit the number of artifacts to 300 for each of the 5 large display rooms and 200 for each of the 5 display small rooms.
10. The system shall allow registered visitors to submit donations for approval to the museum


### Non-functional
11. The system shall generate a log file for diagnosis in the event of a failure (e.g., crashes).
12. The system shall have a maximum event response time of 2 minutes.
13. The system shall allow 90% of all new users to create an account in under 10 minutes without any prior experience.
14. The system shall be accessible via the web frontend for all admin, employees and visitors.
15. The system shall prevent non-admin users from performing admin-only functionalities using security measures.



### Use-Case Diagrams

The system shall allow registered visitors to purchase tickets to visit the museum. 

![ECSE 321-User-Dragram drawio](https://user-images.githubusercontent.com/92070091/196830112-40c62e0b-245e-4e08-942d-17802b8de012.png)

### F07 - The system shall allow employees and admin to manage loan request (Elie)

![casediagram1 drawio](https://user-images.githubusercontent.com/90018206/197311385-31984713-d0c1-4dd8-b8d2-ffb6973bd445.png)

Description: The manager decides if an artifact is loanable or not loanable. If it is loanable, the manager sets a price per day (P) for the loan. The client requests a loan for a specific number of days, and for a price Pm (only if Pm >= P). The manager or the employee can either approve or refuse the loan request of the client. The manager, the employee and the client can view the information about the specific loan, the status, the date of loan, time left. The manager and employee can view all loans’ information. The client can request a loan extension if needed. The manager or the employee can either approve or refuse the loan extension of the client. The client must return the loaned artifact withing the approved time period. The employee or manager verifies that the artifact has been returned. 

Main success scenario:
1.	The manager decides artifact is loanable 
2.	The client requests artifact loan 
3.	The loan is approved by the employee or the manager 
4.	The client, or the manager or the employee views the loan’s information
5.	The client requests an extension for the loan
6.	The extension is approved by the employee or the manager 
7.	The employee or the manager verifies the return 

Alternative Scenarios:

A.	Alternative Scenario A

1a. The manager decides artifact is not loanable 

B.	Alternative Scenario B 

3b. The loan is refused by the employee or the manager 

C.	Alternative Scenario C

5c. The client does not request an extension on the loan 

7c. The manager or the employer verifies the return of the artifact

D.	Alternative Scenario D 

6d. The extension for the loan is refused by the employee or the manager

7d. The manager or the employer verifies the return of the artifact 



