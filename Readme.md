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

## Use Case Diagram

![US101_SSD](/docs/UCD.svg)

These are all the Use Cases to be developed during Sprint 1.

## Domain Model

![US101_SSD](/docs/DM.svg)

Through the analysis of the requirements document we spotted these concepts, which can be useful for the development of 
the solution to the functionalities pretended.

## Relational Model

![Relational Model](/BaseDados/US108/modelos/logico.jpg)


## US101 

### As a traffic manager, I which to import ships from a text file into a BST.

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

### As a traffic manager I wish to search the details of a ship using any of its codes: MMSI, IMO or Call Sign.

### Analysis:

### System Sequence Diagram
![US102_SSD](/docs/US102/US102_SSD.svg)

The Traffic Manager will need to type the code to search the ship so that It can present the user the details for that ship. With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US102_SSD](/docs/US102/US102_DM.svg)

### Design

### Class Diagram
![US102_CD](/docs/US102/US102_CD.svg)

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

## US103

### As a traffic manager I wish to have the positional messages temporally organized and associated with each of the ships.

### Analysis:

### System Sequence Diagram
![US103_SSD](/docs/US103/US103_SSD.svg)

The Traffic Manager will need to organize ship messages and check a position(s) in a data or period.

### Domain Model Diagram
![US103_SSD](/docs/US103/US103_DM.svg)

### Design

### Class Diagram
![US103_CD](/docs/US103/US103_CD.svg)

The class ShipStore has the three methods to complete this US. Organize messages, check position in a data and check positions in a period.

### Sequence Diagram
![US103_SD](/docs/US103/US103_SD.svg)

The system organizes ship positional messages and checks the position of a ship in a data or period.

## Implementation
### Organizing Ship Messages

    public void organizeShipMessage() {
        Map<Integer, List<Ship>> shipsByLevel = store.nodesByLevel();
        for (Map.Entry<Integer, List<Ship>> entry : shipsByLevel.entrySet()) {
            for (Ship ship : entry.getValue()) {
                ship.getRoute().getRoute().sort(Comparator.comparing(ShipDynamic::getBaseDateTime));
            }
        }
        }

### Check Position Data

    public Location getPositionOfShipData(String mMSI, String baseDateTime) {
        Location location = null;

        for (Ship ship : store.inOrder()) {
            for (int i = 0; i < ship.getRoute().getRoute().size(); i++)
                if (ship.getRoute().getRoute().get(i).getBaseDateTime().equals(baseDateTime) 
                        && ship.getShipId().getMmsi().equals(mMSI)) {
                    location = ship.getRoute().getRoute().get(i).getLocation();
                }
        }
        return location
        }
        
### Check Position Period

    public List<Location> getPositionOfShipPeriod(String mMSI, String baseDateTime1, String baseDateTime2) throws ParseException {
        organizeShipMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d1 = sdf.parse(baseDateTime1);
        Date d2 = sdf.parse(baseDateTime2);

        List<Location> position = new ArrayList<>();

        for (Ship ship : store.inOrder()) {
            for (int j = 0; j < ship.getRoute().getRoute().size(); j++)
                if (sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).after(d1) &&
                        sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).before(d2) && ship.getShipId().getMmsi().equals(mMSI)) {
                    position.add(ship.getRoute().getRoute().get(j).getLocation());
                }
        }

        return position;
    }
    
### TEST for CheckPositionData

     @Test
     public void getPositionOfShipData() throws IOException {
        Location expected = new Location("54.27307", "-164.07348");

        String MMSI = "636019825";
        String baseDateTime = "31/12/2020 23:27";

        Location result = store.getPositionOfShipData(MMSI, baseDateTime);
        Location error = store.getPositionOfShipData("333333333","31/12/2020 23:27");
        
        assertEquals(expected,result);
        assertNotEquals(error, result
       
     }

## Review

Ships positional messages are organized and a txt is generated with all information to fullfil the requirements

## US104 

## As a traffic manager I wish to make a Summary of a ship's movements.

## Analysis

# System Sequence Diagram

![US104_SSD](/docs/US104/US104_SSD.svg)

The traffic manager types the code for the ship and the system creates tha map with that ship's data.

### Domain Model Diagram
![US104_DM](/docs/US104/US104_DM.svg)

### Design

### Class Diagram
![US104_CD](/docs/US104/US104_CD.svg)

The System receives a Ship and using its data gathers the data necessary to create the summary. Therefore practically all methods in this class diagram only retrieve and manipulate date.

### Sequence Diagram
![US104_SD](/docs/US104/US104_SD.svg)

The system starts by receiving a ship, then creates an empty map and puts all the data in it, after that it returns the filled map.

## Implementation
### Creation of the map
	public SummaryOfShipMovement(Ship ship){
	this.ship=ship;
	this.summaryMap=new HashMap<>();
	summaryMap.put("MMSI",ship.getShipId().getMmsi());
	summaryMap.put("Vessel Name",ship.getShipId().getShipName());
	summaryMap.put("Start Base Date Time",ship.getStartBaseDateTime());
	summaryMap.put("End Base Date Time",ship.getEndBaseDateTime());
	summaryMap.put("Total Movement Time",calculateTravelTime());
	summaryMap.put("Total Number Of Movements",String.format("%d",ship.getRoute().getSize()));
	summaryMap.put("Max SOG",String.format("%.2f",ship.getRoute().getMaxSog()));
	summaryMap.put("Mean SOG",String.format("%.2f",ship.getRoute().getMeanSog()));
	summaryMap.put("Max COG",String.format("%.2f",ship.getRoute().getMaxCog()));
	summaryMap.put("Mean COG",String.format("%.2f",ship.getRoute().getMeanCog()));
	summaryMap.put("Travelled Distance",String.format("%.4f km",ship.getRoute().getTravelledDistance()));
	summaryMap.put("Delta Distance",String.format("%.4f km",ship.getRoute().getDeltaDistance()));
    }
	
### Example of a get() method
public double getMaxCog(){
        double temp=0;
        for (ShipDynamic shipd: route) {
            if (shipd.getCog()>temp){
                temp=shipd.getCog();
            }
        }
        return temp;
    }

##  Review

I used a map to store the data so that a certain piece of the summary could be retrieved depending on the traffic manager's wishes.

## US106 

## As a Traffic Manager I want to get the top-N ships with the most kilometres travelled and their average speed (MeanSOG).

## Analysis

# System Sequence Diagram

![US106_SSD](/docs/US106/US106_SSD.svg)

The traffic manager wants the top N ships with most travelled distance and their meanSog for every vessel type, in a period.

### Domain Model Diagram
![US106_DM](/docs/US106/US106_DM.svg)

### Design

### Class Diagram
![US106_CD](/docs/US106/US106_CD.svg)

### Sequence Diagram
![US106_SD](/docs/US106/US106_SD.svg)

The system starts by inutilize the messages without the period, then gets the top N with the most travelled distance and their meanSOG.

## Implementation
### getTopNShips
    ...
	for (Ship ship : avl.inOrder()) {
            for (int j = 0; j < ship.getRoute().getRoute().size(); j++) {
                if (!(sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).after(d1) &&
                        sdf.parse(ship.getRoute().getRoute().get(j).getBaseDateTime()).before(d2))) {
                    ship.getRoute().getRoute().remove(j);
                    ;
                }
            }
        }
     ...
     for (Integer vesselType : map.keySet()) {
            for (int i = 0; i < n && n <= map.get(vesselType).size(); i++) {
                topNShips.add(map.get(vesselType).get(i));
                sh.add(map.get(vesselType).get(i));
                map2.put(vesselType, topNShips);

            }
            topNShips = new ArrayList<>();
        }
        ...
        }
        
##  Review

This US was very difficult to implement due to the extreme conditions to meet all requirements. I was undecided on the best way to return the information, a Map or a BST.

## US107 

## As a Traffic manager, I want to return pairs of ships with routes with close departure/arrival coordinates (no more than 5 Kms away) and with different Travelled Distance.

## Analysis

# System Sequence Diagram

![US107_SSD](/docs/US107/US_107_SSD.svg)

The traffic manager receive a list of all the pairs of ships that are within the program and meet those conditions

### Domain Model Diagram
![US107_DM](/docs/US107/US_107_DM.svg)

### Design

### Class Diagram
![US104_C7](/docs/US107/US_107_CD.svg)

There´s a method that checks if a ship fits the conditions asked by the traffic manager and another one that gets the travelled distance difference , after that a list of Pairs is created to
store those pairs, after that there are methods that sort the list according to the acceptance criteria.

### Sequence Diagram
![US107_SD](/docs/US104/US_107_SD.svg)

The system starts by creating a list of the pair of ships and then creates another list with the travelled distance difference. After that it sorts bothe lists according to the acceptance 
criteria. 

## Implementation
### Checking if a ship is close
	public boolean isClose(Ship ship){
        if (Route.distance(this.route.getDepartureLat(),this.route.getDepartureLong(),ship.getRoute().getDepartureLat(),ship.getRoute().getDepartureLong())>5){
            return false;
        }
        if (Route.distance(this.route.getArrivalLat(),this.route.getArrivalLong(),ship.getRoute().getArrivalLat(),ship.getRoute().getArrivalLong())>5){
            return false;
        }
        if (Route.distance(this.route.getArrivalLat(),this.route.getArrivalLong(),ship.getRoute().getArrivalLat(),ship.getRoute().getArrivalLong())==0){
            return false;
        }
        return true;
    }
	
### Creating the list 
	public List<Pair<Ship, Ship>> getCloseShips() {
        List<Pair<Ship, Ship>> pairList = new ArrayList<>();
        for (Ship ship : store.inOrder()) {
            if (ship.getRoute().getTravelledDistance() > 10) {
                for (Ship ship2 : store.inOrder()) {
                    if (ship.isClose(ship2)) {
                        Pair<Ship, Ship> pair1 = new Pair<>(ship, ship2);
                        pairList.add(pair1);
                    }
                }
            }
        }
        return pairList;
    }

### Part of the sorting
	private void sortByDescendingOrder(){
        int cont =0;

        for (int i=0;i<travelledDistanceList1.size();i++){
            int j=i;
            while (Integer.parseInt(shipPairList1.get(j).getFirst().getShipId().getMmsi())==Integer.parseInt(shipPairList1.get(i).getFirst().getShipId().getMmsi())){
                cont++;
                 j++;
                 if (j==travelledDistanceList1.size()){
                     break;
                 }
            }
            if (cont>1) {
                for (int b = i; b < i + cont; b++) {
                    for (int d = i + 1; d < (i + cont ); d++) {
                        if (travelledDistanceList1.get(d - 1) <= travelledDistanceList1.get(d)) {
                            Pair<Ship, Ship> tempShip = new Pair<>(shipPairList1.get(d - 1).getFirst(), shipPairList1.get(d - 1).getSecond());
                            double tempDouble = travelledDistanceList1.get(d - 1);
                            shipPairList1.set(d - 1, shipPairList1.get(d));
                            travelledDistanceList1.set(d - 1, travelledDistanceList1.get(d));
                            shipPairList1.set(d, tempShip);
                            travelledDistanceList1.set(d, tempDouble);
                        }
                    }
                }
            }
            cont=0;
        }
    }
	
##  Review

The sorting methods may be a little overcomplicated.

## US109

### As Project Manager, I want the team to draft an SQL script to test whether the database verifies all the data integrity restrictions that are required to fulfil the purpose of the system and the business constraints of the UoD.

## Implementation

You can find the file sql script in the BaseDados folder.

## US110

###As Project Manager, I want the team to define the naming conventions to apply when defining identifiers or writing SQL or PL/SQL code. The naming conventions mayevolve as new database and programming objects are known.

### Naming Conventions

You can find the file with the naming conventions in the BaseDados folder.




#=======================================================================|End of Report|====================================================

# README

This is the repository template used for student repositories in LAPR Projets.

## Java source files

Java source and test files are located in folder src.

## Maven file

Pom.xml file controls the project build.

### Notes
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

```shell
$ mvn test
```
## How to generate the javadoc for source code?

Execute the "javadoc:javadoc" goal.

```shell
$ mvn javadoc:javadoc
```
This generates the source code javadoc in folder "target/site/apidocs/index.html".

## How to generate the javadoc for test cases code?

Execute the "javadoc:test-javadoc" goal.

```shell
$ mvn javadoc:test-javadoc
```
This generates the test cases javadoc in folder "target/site/testapidocs/index.html".

## How to generate Jacoco's Code Coverage Report?

Execute the "jacoco:report" goal.

```shell
$ mvn test jacoco:report
```

This generates a jacoco code coverage report in folder "target/site/jacoco/index.html".

## How to generate PIT Mutation Code Coverage?

Execute the "org.pitest:pitest-maven:mutationCoverage" goal.

```shell
$ mvn test org.pitest:pitest-maven:mutationCoverage
```
This generates a PIT Mutation coverage report in folder "target/pit-reports/YYYYMMDDHHMI".

## How to combine different maven goals in one step?

You can combine different maven goals in the same command. For example, to locally run your project just like on jenkins, use:

```shell
$ mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage
```
## How to perform a faster pit mutation analysis?

Do not clean build => remove "clean"

Reuse the previous report => add "-Dsonar.pitest.mode=reuseReport"

Use more threads to perform the analysis. The number is dependent on each computer CPU => add "-Dthreads=4"

Temporarily remove timestamps from reports.

Example:
```shell
$ mvn test jacoco:report org.pitest:pitest-maven:mutationCoverage -DhistoryInputFile=target/fasterPitMutationTesting-history.txt -DhistoryOutputFile=target/fasterPitMutationTesting-history.txt -Dsonar.pitest.mode=reuseReport -Dthreads=4 -DtimestampedReports=false
```
## Where do I configure my database connection?

Each group should configure their database connection on the file:
* src/main/resources/application.properties