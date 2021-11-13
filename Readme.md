# Report

# Abstract #
A software company needs to develop a product that supports the delivery of pharmaceutical products.
This service should allow managing clients, products, orders, deliveries, etc.

# Introduction #
The purpose of this report is to document the entire development process of LAPR3's integrating project.

The development process of this project was based on the knowledge acquired in the UCs ESINF, BDDAD
and also on LAPR3's Scrum Methodology and Jira.

For ESINF we used Data Structures and for BDDAD Databases SQL.
We also applied analysis and design engineering and object oriented programming (OO) patterns learned so far.

The aim of this project is to create a software which allows a cargo shipping company to handle their logistics. This company 
operates through land and sea, across different countries and has several warehouses spread along the world.

This report is divided into the differents User Stories developed during Sprint 1:

* US101 Rui Pina

* US102 Rui Pina

* US103 Jorge Ferreira

* US104 Rafael Leite

* US106 Jorge Ferreira

* US107 Rafael Leite

* US108 Tiago Ribeiro

* US109 Rui Pina

* US110 Jorge Ferreira

##Use Case Diagram

![US101_SSD](/docs/UCD.svg)

These are all the Use Cases to be developed during Sprint 1.

##Domain Model

![US101_SSD](/docs/DM.svg)

Through the analysis of the requirements document we spotted these concepts, which can be useful for the development of 
the solution to the functionalities pretended.

## Relational Model

![Relational Model](/BaseDados/US108/logico.pdf)


## US101 

###As a traffic manager, I which to import ships from a text file into a BST.

### Analysis
### System Sequence Diagram

![US101_SSD](/docs/US101/US101_SSD.svg)

The Traffic Manager will need to select the csv to import the ships from by typing it. With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US101_SSD](/docs/US101/US101_DM.svg)

### Design
### Class Diagram
![US101_CD](/docs/US101/US101_CD.svg)

We will use the Store pattern to store the "Ships" on the class ShipStore, which will contain an AVL tree containing the instances
of Ships imported from the csv. The class Ship has been decomposed into smaller classes that only have one function, in compliance
with the GRASP pattern.

### System Diagram
![US101_SD](/docs/US101/US101_SD.svg)

The system will receive the file to be imported and will scan through its lines, one by one, on the elements to be added to the ships instances.
It will only store the elements regarding its identification, if it is a new ship. If not it only stores its movements and adds it to the refering Ship.
When all the Ships have been created, the system will add it to the AVL in the ShipStore.

## Implementation

### Go through the lines and create the Ship Object

    br = new BufferedReader(new FileReader(fileName));
    br.readLine();
    while ((line = br.readLine()) != null) {
    size++;
    String[] elements = line.split(splitBy);
    if (ship == null || !ship.getShipId().getMmsi().equals(elements[0])) {

    try {
    route = new Route();
    idShip = createIdentification(elements);
    characteristics = createCharacteristics(elements);
    ship = new Ship(idShip, characteristics, null);
    ships.add(ship);
    } catch (Exception e) {
    ship = null;
    LOGGER.log(Level.INFO, String.format("Failed to import line %d", size));
    }
    }

### Create the Movements of a ship
    if (ship != null) {
    try {
        shipDynamic = createShipDynamic(elements);
        route.add(shipDynamic);
        ship.setRoute(route);
    } catch (Exception e) {
        LOGGER.log(Level.INFO, String.format("Failed to import line %d", size));
    }
    }
### Add the ships to the AVL

    for (Ship ship1 : ships) {
    store.addShipToAVL(ship1);
    }

## Review

There are some validations that have turned out to be ambiguous that could not get responded by the client and the solution
was done this. If there were some validations that were not the intended, it can be easily changed and will not affect the system too much.

## US102

###As a traffic manager I wish to search the details of a ship using any of its codes: MMSI, IMO or Call Sign.

### Analysis:

### System Sequence Diagram
![US102_SSD](/docs/US102/US102_SSD.svg)

The Traffic Manager will need to type the code to search the ship so that It can present the user the details for that ship. With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US102_SSD](/docs/US102/US102_DM.svg)

### Design

### Class Diagram
![US102_CD](/docs/US101/US102_CD.svg)

The design patterns used in US102 are the same as in the US101, since we basically only need to use the result of US101 and manipulate it.
For the intended solution it was added some methods such as returnCodeAccordingToTheCodeFormat(code) in the class Identification, findShipDetails and reorganizeAVLAccordingToTheCode(code) on the shipStore.

### Sequence Diagram
![US101_SD](/docs/US102/US102_SD.svg)

The system will receive the code for the ship to be searched, and will set the searchCode for the Ships to that code. With that the system will find
what type of code has been introduced by the user, using the ShipValidation class, which contains auxiliary methods just to validate data.
With the type of code determined, a new AVL will be created and it will start building itself using the type of code introduced as the comparator method.
Since the AVL is now ordered correctly, we know can efficiently go through the AVL and find it. If the ship was found we display the details of said ship in a textfile for the user, if not
a message will appear warning the user that no ship was found with that code.

## Implementation
### Go through the AVL and set the searchCode to the ships

    BST.Node<Ship> s;
        AVL<Ship> shipAVL;
        Ship ship = null;
        ArrayList<Ship> ship2 = new ArrayList<>();
        for (Ship ships : store.posOrder()) {
            ships.getShipId().setSearchCode(code);
            ship2.add(ships);
        }

### Reorganize the AVL with the appropriate type of Code
    public AVL<Ship> reorganizeShipAVLAccordingToTheCode(List<Ship> ship) {
        AVL<Ship> newShipAVL = new AVL<>();
        for (Ship ship3 : ship) {
            newShipAVL.insert(ship3);
        }
        return newShipAVL;
    }
### Search for the ship in the AVL

    for (Ship value : shipAVL.posOrder()) {
            s = shipAVL.find(value);
            if (s.getElement().getShipId().getImoID().equals(code) || s.getElement().getShipId().getCallsign().equals(code)
                    || s.getElement().getShipId().getMmsi().equals(code)) {
                return s.getElement();
            }
        }

## Review

The AVL was the chosen solution since it has a more efficient way of searching a ship [O(nlogn)] than a BST [O(n^2)] or another type of structure.



## US109

###As Project Manager, I want the team to draft an SQL script to test whether the database verifies all the data integrity restrictions that are required to fulfil the purpose of the system and the business constraints of the UoD.

### Analysis:

There was no Analysis done, since it is a simple script that won't have any interaction with user.
### Design

There was no Design done, since it is a simple script that won't have any interaction with user.

## Implementation

![US109_Script](/BaseDados/US109/ScriptSQL.sql)

## Review






#=======================================================================|End of Report|====================================================

# README

This is the repository template used for student repositories in LAPR Projets.

## Java source files

Java source and test files are located in folder src.

## Maven file

Pom.xml file controls the project build.

# Notes
In this file, DO NOT EDIT the following elements:

* groupID
* artifactID
* version
* properties

Beside, students can only add dependencies to the specified section of this file.

## Eclipse files

The following files are solely used by Eclipse IDE:

* .classpath
* .project

## IntelliJ Idea IDE files

The following folder is solely used by Intellij Idea IDE :

* .idea

# How was the .gitignore file generated?
.gitignore file was generated based on https://www.gitignore.io/ with the following keywords:

- Java
- Maven
- Eclipse
- NetBeans
- Intellij

# Who do I talk to?
In case you have any problem, please email Nuno Bettencourt (nmb@isep.ipp.pt).

# How do I use Maven?

## How to run unit tests?

Execute the "test" goals.

`$ mvn test`

## How to generate the javadoc for source code?

Execute the "javadoc:javadoc" goal.

`$ mvn javadoc:javadoc`

This generates the source code javadoc in folder "target/site/apidocs/index.html".

## How to generate the javadoc for test cases code?

Execute the "javadoc:test-javadoc" goal.

`$ mvn javadoc:test-javadoc`

This generates the test cases javadoc in folder "target/site/testapidocs/index.html".

## How to generate Jacoco's Code Coverage Report?

Execute the "jacoco:report" goal.

`$ mvn test jacoco:report`

This generates a jacoco code coverage report in folder "target/site/jacoco/index.html".

## How to generate PIT Mutation Code Coverage?

Execute the "org.pitest:pitest-maven:mutationCoverage" goal.

`$ mvn test org.pitest:pitest-maven:mutationCoverage`

This generates a PIT Mutation coverage report in folder "target/pit-reports/YYYYMMDDHHMI".

## How to combine different maven goals in one step?

You can combine different maven goals in the same command. For example, to locally run your project just like on jenkins, use:

`$ mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage`

## How to perform a faster pit mutation analysis?

Do not clean build => remove "clean"

Reuse the previous report => add "-Dsonar.pitest.mode=reuseReport"

Use more threads to perform the analysis. The number is dependent on each computer CPU => add "-Dthreads=4"

Temporarily remove timestamps from reports.

Example:

`$ mvn test jacoco:report org.pitest:pitest-maven:mutationCoverage -DhistoryInputFile=target/fasterPitMutationTesting-history.txt -DhistoryOutputFile=target/fasterPitMutationTesting-history.txt -Dsonar.pitest.mode=reuseReport -Dthreads=4 -DtimestampedReports=false`

## Where do I configure my database connection?

Each group should configure their database connection on file:

* src/main/resources/application.properties

# Oracle repository

If you get the following error:

```
[ERROR] Failed to execute goal on project 
bike-sharing: Could not resolve dependencies for project 
lapr3:bike-sharing:jar:1.0-SNAPSHOT: 
Failed to collect dependencies at 
com.oracle.jdbc:ojdbc7:jar:12.1.0.2: 
Failed to read artifact descriptor for 
com.oracle.jdbc:ojdbc7:jar:12.1.0.2: 
Could not transfer artifact 
com.oracle.jdbc:ojdbc7:pom:12.1.0.2 
from/to maven.oracle.com (https://maven.oracle.com): 
Not authorized , ReasonPhrase:Authorization Required. 
-> [Help 1]
```

Follow these steps:

https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides

You do not need to set a proxy.

You can use existing dummy Oracle credentials available at http://bugmenot.com.