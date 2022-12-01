## Model 
Below is a block diagram of the Museum Management System. It illustrates the different components and shows how they interact/connect with each other. 

![ArchitectureModel](https://user-images.githubusercontent.com/98911345/205117116-76b7c29b-3c76-4b20-9a14-10cd1363466e.jpg)

## Key Functional Components 
**1. MVC (Model-View-Controller)**

The Model component manages the system data and associated operations on that data. The View component defines and manages how the data is presented to the user. The Controller component manages user interaction (e.g., key presses, mouse clicks, etc.) and passes these interactions to the View and the Model.

**2. Layered Components (Frontend-Backend-Persistence)**

The system is organized into layers with related functionality associated with each layer. A layer provides services to the layer above it (usually) so the lowest-level layers represent core services like the repository which are used throughout the system. 

* **2.1 Embedded Layered Components (REST Controller-Business Service)**

  * Business Service methods implement the business functionality and call the persistence layer which is where the data is stored. 

  * REST controller methods provide access to the service at specific access points via HTTP calls using REST API.

**3. Repository (Business Entities - Database)**

All data in the system is held in the central repository that is accessible to the Business Service. Components do not interact directly with the data, only through the repository.
