### F01 - The Museum Management system shall allow the admin to create, update and delete employee accounts
#### Diagram:
![image](https://user-images.githubusercontent.com/97963882/197402283-be8381b6-9bd3-4a8b-900c-3cc5b8cc41fa.png)

#### Use-Case Specification: The admin hires a new employee  
Main success scenario:  
1. the admin hires an employee
2. the admin creates an account for the new employee
3. the admin assigns shifts to the new employee 
  
Alternate scenarios:  
* 2a. The employee already has an account
* 2b. The admin tries to register the new account using an email that is already registered with another employee account
  * 2b.1 The employee provides another email for the admin to create the account
* 3a. The admin does not assign any shifts to the new employee right away

### F03 - The Museum Management System shall allow employees and the admin to move, store / display artifacts.

#### Diagram:
<img width="565" alt="F03_UseCaseDiagram" src="https://user-images.githubusercontent.com/98911345/197401953-221a6925-67d9-427e-918b-81299b3393f0.png">

#### Use-Case Specification:

### F04 - The system shall allow anyone to create, update, and delete their client account. 

#### Diagram:
![Visitor Register drawio](https://user-images.githubusercontent.com/97861909/197406917-a11a98e2-5cbd-40c0-8515-e16af9f85481.png)

#### Use-Case Specification: A client creates an account
Main success scenario:
1. The client visits the system.
2. The client registers account for first time.

Alternate Scenarios:
* 2a. The client already has an account
* 2b. The client needs help registering account
  * 2b.1 The employee registers account for client

### F05 - The Museum Management system shall allow registered visitors to purchase tickets to visit the museum. 

#### Diagram: 
![ECSE 321-User-Dragram drawio](https://user-images.githubusercontent.com/92070091/196830112-40c62e0b-245e-4e08-942d-17802b8de012.png)

#### Use-Case Specification:

### F07 - The Museum Management system shall allow employees and admin to manage loan request (Elie)

#### Diagram: 
![casediagram1 drawio](https://user-images.githubusercontent.com/90018206/197401714-3e604a19-7b5c-46ea-8610-d90bfb329fb2.png)

Description: The manager decides if an artifact is loanable or not loanable. If it is loanable, the manager sets a price per day (P) for the loan. The client requests a loan for a specific number of days, and for a price Pm (only if Pm >= P). The manager or the employee can either approve or refuse the loan request of the client. The manager, the employee and the client can view the information about the specific loan, the status, the date of loan, time left. The manager and employee can view all loans’ information. The client can request a loan extension if needed. The manager or the employee can either approve or refuse the loan extension of the client. The client must return the loaned artifact withing the approved time period. The employee or manager verifies that the artifact has been returned. 

#### Use-Case Specification:
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
- 1a. The manager decides artifact is not loanable   

B.	Alternative Scenario B   
- 3b. The loan is refused by the employee or the manager  

C.	Alternative Scenario C  
- 5c. The client does not request an extension on the loan 
- 7c. The manager or the employer verifies the return of the artifact  

D.	Alternative Scenario D  
- 6d. The extension for the loan is refused by the employee or the manager
- 7d. The manager or the employer verifies the return of the artifact 

### F08 - The Museum Management System shall allow anyone to view artifacts and their status (ie. loanable/not, on loan, in storage, on display).

#### Diagram: 
<img width="581" alt="Screen Shot 2022-10-23 at 11 41 08 AM" src="https://user-images.githubusercontent.com/49253947/197401750-7cd4249d-8b5b-458a-982b-7b58584c9b5a.png">

#### Use-Case Specification: