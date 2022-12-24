Critter Chronologer Project Starter
Critter Chronologer a Software as a Service application that provides a scheduling interface for a small business that takes care of animals. This Spring Boot project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.

Getting Started
Dependencies
IntelliJ IDEA Community Edition (or Ultimate) recommended
Java SE Development Kit 8+
Maven
MySQL Server 8 (or another standalone SQL instance)
Postman
Part of this project involves configuring a Spring application to connect to an external data source. Before beginning this project, you must install a database to connect to. Here are instructions for installing MySQL 8.

You should install the Server and Connector/J, but it is also convenient to install the Documentation and Workbench.

Alternately, you may wish to run MySQL in a docker container, using these instructions.

After installing the Server, you will need to create a user that your application will use to perform operations on the server. You should create a user that has all permissions on localhost using the sql command found here.

Another SQL database may be used if desired, but do not use the H2 in-memory database as your primary datasource.

Installation
Clone or download this repository.
Open IntelliJ IDEA.
In IDEA, select File -> Open and navigate to the critter directory within this repository. Select that directory to open.
The project should open in IDEA. In the project structure, navigate to src/main/java/com.udacity.jdnd.course3.critter.
Within that directory, click on CritterApplication.java and select Run -> Debug CritterApplication.
Open a browser and navigate to the url: http://localhost:8082/test
You should see the message "Critter Starter installed successfully" in your browser.

Testing
Once you have completed the above installation, you should also be able to run the included unit tests to verify basic functionality as you complete it. To run unit tests:

Within your project in IDEA, Navigate to src/test/java/com.udacity.jdnd.course3.critter.
Within that directory, click on CritterFunctionalTest.java and select Run -> Run CritterFunctionalTest.
A window should open showing you the test executions. All 9 tests should fail and if you click on them they will show java.lang.UnsupportedOperationeException as the cause.

As you complete the objectives of this project, you will be able to verify progress by re-running these tests.

Tested Conditions
Tests will pass under the following conditions:

testCreateCustomer - UserController.saveCustomer returns a saved customer matching the request
testCreateEmployee - UserController.saveEmployee returns a saved employee matching the request
testAddPetsToCustomer - PetController.getPetsByOwner returns a saved pet with the same id and name as the one saved with UserController.savePet for a given owner
testFindPetsByOwner - PetController.getPetsByOwner returns all pets saved for that owner.
testFindOwnerByPet - UserController.getOwnerByPet returns the saved owner used to create the pet.
testChangeEmployeeAvailability - UserController.getEmployee returns an employee with the same availability as set for that employee by UserControler.setAvailability
testFindEmployeesByServiceAndTime - UserController.findEmployeesForService returns all saved employees that have the requested availability and skills and none that do not
testSchedulePetsForServiceWithEmployee - ScheduleController.createSchedule returns a saved schedule matching the requested activities, pets, employees, and date
testFindScheduleByEntities - ScheduleController.getScheduleForEmployee returns all saved schedules containing that employee. ScheduleController.getScheduleForPet returns all saved schedules for that pet. ScheduleController.getScheduleForCustomer returns all saved schedules for any pets belonging to that owner.
Postman
In addition to the included unit tests, a Postman collection has been provided.

Open Postman.
Select the Import button.
Import the file found in this repository under src/main/resource/Udacity.postman_collection.json
Expand the Udacity folder in postman.
Each entry in this collection contains information in its Body tab if necessary and all requests should function for a completed project. Depending on your key generation strategy, you may need to edit the specific ids in these requests for your particular project.

Built With
Spring Boot - Framework providing dependency injection, web framework, data binding, resource management, transaction management, and more.
Google Guava - A set of core libraries used in this project for their collections utilities.
H2 Database Engine - An in-memory database used in this project to run unit tests.
MySQL Connector/J - JDBC Drivers to allow Java to connect to MySQL Server
License
This project is licensed under the MIT License - see the LICENSE.md
