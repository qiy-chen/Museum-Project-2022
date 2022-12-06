# Build System Information
* [How to run Frontend](https://github.com/McGill-ECSE321-Fall2022/project-group-15/wiki/Build-System#how-to-run-frontend)
* [Important information for the Frontend](https://github.com/McGill-ECSE321-Fall2022/project-group-15/wiki/Build-System#important-information-for-the-frontend)
* [Build System](https://github.com/McGill-ECSE321-Fall2022/project-group-15/wiki/Build-System#build-system)
* [How to Run Frontend](https://github.com/McGill-ECSE321-Fall2022/project-group-15/wiki/Build-System#how-to-run-backend)
* [Test Run and Pass Locally](https://github.com/McGill-ECSE321-Fall2022/project-group-15/wiki/Build-System#test-run-locally)

## How to run Frontend
Step 1: After downloading the repository as a .zip file, in terminal cd into the Museum-Frontend folder

`cd */GitHub/project-group-15/Museum-Frontend`

Step 2: Run the following command 

`npm install`

Step 3: You may be prompted to download an additional dependency. Simply follow the command shown. 

Step 4: Run the following command

`npm run dev`

This should launch the Frontend in your default browser. The following page should start
<img width="1552" alt="image" src="https://user-images.githubusercontent.com/98911345/205796431-78b79c2b-2cd5-4f6c-a49c-e31fb24df764.png">


## Important information for the Frontend
When the frontend launches it creates a museum and an admin account for the admin to login. To log in using the admin account (and hence use all their functionalities) you must use the following credentials:

**Username/Email: admin@hotmail.com**

**Password: 123** 

<img width="1552" alt="image" src="https://user-images.githubusercontent.com/98911345/205796573-78c5c471-20b3-4bbb-adaa-082c2382fdd8.png">


* In the admin dashboard, to try out the functionalities of an employee, you must make an employee account. 
* To try out the functionalities of a customer, simply make an account in the Register page. 
* If a page is ever black, try refreshing it!

## Build System
To run the application, you can use any command line, where you must open the project, and input the command ./gradlew bootRun. The application will be deployed on a website but the website has not been built yet. 
To build locally with Gradle, cd into the root directory and run the command ```./gradlew build -xtest```. This should give the following output:

 <img width="682" alt="Screen Shot 2022-11-19 at 8 30 41 PM" src="https://user-images.githubusercontent.com/98911345/202878586-9c530d5a-134b-4a9a-a158-1227bff1664b.png">

## How to run Backend

Step 1: Open the project in your editor (e.g. Eclipse)

Step 2: Run the MuseumBackendApplication.java file as a Java Application. This can be found in 

`../project-group-15/src/main/java/ca/mcgill/ecse321/MuseumBackend/MuseumBackendApplication.java`

## Test Run Locally 
As seen below, all repository, service and integration tests run and pass locally. We implemented one (1) additional test so there are now 150 tests.

<img width="546" alt="image" src="https://user-images.githubusercontent.com/98911345/205798859-e20ee5b3-320a-43d2-98c3-bf1038c87493.png">



