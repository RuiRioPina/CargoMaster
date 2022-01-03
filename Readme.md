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

This report is divided into the different User Stories developed across all Sprints:

### Sprint 1 

* US101 Rui Pina

* US102 Rui Pina

* US103 Jorge Ferreira

* US104 Rafael Leite

* US106 Jorge Ferreira

* US107 Rafael Leite

* US108 Tiago Ribeiro

* US109 Rui Pina

* US110 Jorge Ferreira

### Sprint 2

* US201 Rui Pina

* US202 Rafael Leite

* US203 Tiago Ribeiro

* US204 Rafael Leite

* US205 Jorge Ferreira

* US206 Jorge Ferreira

* US207 Rafael Leite

* US208 Rui Pina

* US209 Rui Pina

* US210 Tiago Ribeiro

### Sprint 3

* US301 Rafael Leite

* US302 Jorge Ferreira

* US303 Rui Pina

* US304 Jorge Ferreira

* US305 Rui Pina

* US306 Tiago Ribeiro

* US307 Tiago Ribeiro

* US308 Rui Pina

* US309 Jorge Ferreira

* US310 Rafael Leite

* US311 Rafael Leite

* US312 Rafael Leite

* US313 Rui Pina

* US314 Jorge Ferreira

* US315 Rafael Leite

* US316 Rafael Leite

* US317 Rui Pina

* US318 Rui Pina

* US319 Jorge Ferreira

* US320 Rafael Leite


# Sprint 1

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

![US101_SSD](/docs/Sprint 1/US101/US101_SSD.svg)

The Traffic Manager will need to select the csv to import the ships from by typing it. With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US101_SSD](/docs/Sprint 1/US101/US101_DM.svg)

### Design
### Class Diagram
![US101_CD](/docs/Sprint 1/US101/US101_CD.svg)

We will use the Store pattern to store the "Ships" on the class ShipStore, which will contain an AVL tree containing the instances
of Ships imported from the csv. The class Ship has been decomposed into smaller classes that only have one function, in compliance
with the GRASP pattern.

### System Diagram
![US101_SD](/docs/Sprint 1/US101/US101_SD.svg)

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

## Testing

## Test 1: Import the ships from the csv and make sure the ship exists

    @Test
    void importShips() {
        String fileName = "csvFiles/sships.csv";
        List<Ship> shipsList = ImportShips.importShips(fileName);
        for (Ship ships : shipsList) {
            shipStore.addShipToAVL(ships);
        }
        Ship expected = shipStore.findShipDetails("IMO9395044");
        assertEquals(expected, shipStore.getStore().smallestElement());
    }

We import the ships using the the method importShips, and then we assert that the smallest element is the expected one (Varamo), using the findDetails method.

## Test 2: Import the ships from the csv and make sure the ship exists

    @Test
    void importShips1() {
    String fileName = "csvFiles/shipID.csv";
    List<Ship> shipsList = ImportShips.importShips(fileName);
    String code = "338888000";
    for (Ship ships : shipsList) {
        ships.getShipId().setSearchCode(code);
        shipStore.addShipToAVL(ships);
    }
    Ship ship = shipStore.findShipDetails(code);
    Ship actual = shipStore.getStore().find(ship).getElement();
    Ship expected = shipStore.getStore().find(ship).getElement();
    assertEquals(actual, expected);
    }

We import the ships using the the method importShips, and then we assert that the smallest element is the expected one, using the findDetails method.

## Test 3: Import the ships from the csv and assert that a ship was in another csv was not imported

    @Test
    void importShips3() {
    String fileName = "csvFiles/shipID.csv";
    List<Ship> shipsList = ImportShips.importShips(fileName);
    String code = "IMO9395044";
    for (Ship ships : shipsList) {
         ships.getShipId().setSearchCode(code);
         shipStore.addShipToAVL(ships);
    }
    Throwable exception = assertThrows(IllegalArgumentException.class,
    ()->{ Ship ship = shipStore.findShipDetails(code);;} );
    }

We import the ships using the method importShips, that a ship existent in another csv is one imported.



## Review

There are some validations that have turned out to be ambiguous that could not get responded by the client and the solution
was done this. If there were some validations that were not the intended, it can be easily changed and will not affect the system too much.

## US102

### As a traffic manager I wish to search the details of a ship using any of its codes: MMSI, IMO or Call Sign.

### Analysis:

### System Sequence Diagram
![US102_SSD](/docs/Sprint 1/US102/US102_SSD.svg)

The Traffic Manager will need to type the code to search the ship so that It can present the user the details for that ship. With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US102_SSD](/docs/Sprint 1/US102/US102_DM.svg)

### Design

### Class Diagram
![US102_CD](/docs/Sprint 1/US102/US102_CD.svg)

The design patterns used in US102 are the same as in the US101, since we basically only need to use the result of US101 and manipulate it.
For the intended solution it was added some methods such as returnCodeAccordingToTheCodeFormat(code) in the class Identification, findShipDetails and reorganizeAVLAccordingToTheCode(code) on the shipStore.

### Sequence Diagram
![US101_SD](/docs/Sprint 1/US102/US102_SD.svg)

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

## Testing 

### Test 1: Make sure the findDetails method finds the correct ship using its callsign.
    @Test
    public void searchShipDetails() throws IOException {
    final String fileToBeWrittenTo = "resultSearchDetails.txt";
        try {
             Ship ship = store.findShipDetails("DHBN");
             System.out.println(ship);
             PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }

### Test 2: Make sure the findDetails method finds the correct ship using its mmsi.

        public void searchShipDetais1() throws IOException {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";

        try {
            Ship ship = store.findShipDetails("367008090");
            System.out.println(ship);
            PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }

### Test 3: Make sure the findDetails method finds the correct ship using its imo.

        @Test
        public void searchShipDetails2() throws IOException {
        final String fileToBeWrittenTo = "resultSearchDetails.txt";

        try {
            Ship ship = store.findShipDetails("IMO7437068");
            System.out.println(ship);
            PrintToFile.print(ship.toString(), fileToBeWrittenTo);
        } catch (IllegalArgumentException e) {
            System.out.println("The vessel to be searched does not exist. Please introduce a valid code!");
        }
    }


## Review

The AVL was the chosen solution since it has a more efficient way of searching a ship [O(nlogn)] than a BST [O(n^2)] or another type of structure.

## US103

### As a traffic manager I wish to have the positional messages temporally organized and associated with each of the ships.

### Analysis:

### System Sequence Diagram
![US103_SSD](/docs/Sprint 1/US103/US103_SSD.svg)

The Traffic Manager will need to organize ship messages and check a position(s) in a data or period.

### Domain Model Diagram
![US103_SSD](/docs/Sprint 1/US103/US103_DM.svg)

### Design

### Class Diagram
![US103_CD](/docs/Sprint 1/US103/US103_CD.svg)

The class ShipStore has the three methods to complete this US. Organize messages, check position in a data and check positions in a period.

### Sequence Diagram
![US103_SD](/docs/Sprint 1/US103/US103_SD.svg)

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

![US104_SSD](/docs/Sprint 1/US104/US104_SSD.svg)

The traffic manager types the code for the ship and the system creates tha map with that ship's data.

### Domain Model Diagram
![US104_DM](/docs/Sprint 1/US104/US104_DM.svg)

### Design

### Class Diagram
![US104_CD](/docs/Sprint 1/US104/US104_CD.svg)

The System receives a Ship and using its data gathers the data necessary to create the summary. Therefore practically all methods in this class diagram only retrieve and manipulate date.

### Sequence Diagram
![US104_SD](/docs/Sprint 1/US104/US104_SD.svg)

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
	
##	Test

I started by creating a custom ship and route and retrieved the data myself and then checked if the program got the same.

##  Review

I used a map to store the data so that a certain piece of the summary could be retrieved depending on the traffic manager's wishes.

## US106 

## As a Traffic Manager I want to get the top-N ships with the most kilometres travelled and their average speed (MeanSOG).

## Analysis

# System Sequence Diagram

![US106_SSD](/docs/Sprint 1/US106/US106_SSD.svg)

The traffic manager wants the top N ships with most travelled distance and their meanSog for every vessel type, in a period.

### Domain Model Diagram
![US106_DM](/docs/Sprint 1/US106/US106_DM.svg)

### Design

### Class Diagram
![US106_CD](/docs/Sprint 1/US106/US106_CD.svg)

### Sequence Diagram
![US106_SD](/docs/Sprint 1/US106/US106_SD.svg)

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
        
### TEST for getTopNShips

    @Test
    public void getTopNShipsSMALL() throws ParseException, IOException {
        Map <Integer, List <Ship> > exp = new HashMap<>();
        List <Ship> ship1 = new ArrayList<>();
        store.organizeShipMessage();
        for (Ship ship : store.getStore().inOrder()) {
            ship1.add(ship);
        }
        List <Ship> ship2 = new ArrayList<>();
        ship2.add(ship1.get(4));
        ship2.add(ship1.get(12));
        List <Ship> ship3 = new ArrayList<>();
        ship3.add(ship1.get(16));
        ship3.add(ship1.get(3));
        List <Ship> ship4 = new ArrayList<>();
        ship4.add(ship1.get(5));
        ship4.add(ship1.get(8));
        exp.put(60,ship4);
        exp.put(70,ship3);
        exp.put(80,ship2);
        Map<Integer, List <Ship> > res = store.getTopNShips(2,"31/12/2020 00:00","31/12/2020 23:59");
        assertEquals(res,exp);
    }
        
##  Review

This US was very difficult to implement due to the extreme conditions to meet all requirements. I was undecided on the best way to return the information, a Map or a BST.

## US107 

## As a Traffic manager, I want to return pairs of ships with routes with close departure/arrival coordinates (no more than 5 Kms away) and with different Travelled Distance.

## Analysis

# System Sequence Diagram

![US107_SSD](/docs/Sprint 1/US107/US_107_SSD.svg)

The traffic manager receive a list of all the pairs of ships that are within the program and meet those conditions

### Domain Model Diagram
![US107_DM](/docs/Sprint 1/US107/US_107_DM.svg)

### Design

### Class Diagram
![US104_C7](/docs/Sprint 1/US107/US_107_CD.svg)

There´s a method that checks if a ship fits the conditions asked by the traffic manager and another one that gets the travelled distance difference , after that a list of Pairs is created to
store those pairs, after that there are methods that sort the list according to the acceptance criteria.

### Sequence Diagram
![US107_SD](/docs/Sprint 1/US107/US_107_SD.svg)

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
	
## Testing 

To test this US I used a couple of made up points to see if the ships would appear. After that, I used the bShips file to test the sorting .	
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

# Sprint 2

## US201

### As a Port manager, I which to import ports from a text file and create a 2D-tree with port locations.

### Analysis
### System Sequence Diagram

![US201_SSD](/docs/Sprint 2/US201/US201_SSD.svg)

The Port manager will need to select the csv to import the ports from by typing it. With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US101_SSD](/docs/Sprint 2/US201/US201_DM.svg)

### Design
### Class Diagram
![US101_CD](/docs/Sprint 2/US201/US201_CD.svg)

We will use the Store pattern to store the "Ports" on the class PortStore, which will contain an 2DTree tree containing the instances
of Ports imported from the csv. The class Port has been decomposed into smaller classes that only have one function, in compliance
with the GRASP pattern.

### System Diagram
![US101_SD](/docs/Sprint 2/US201/US201_SD.svg)

The system will receive the file to be imported and will scan through its lines, one by one, on the elements to be added to the ports instances.
When all the Ports have been created, the system will add it to the 2DTree in the PortStore.

## Implementation

### Go through the lines and create the Port Object

     while ((line = br.readLine()) != null) {
                size++;
                String[] elements = line.split(splitBy);
                if (port == null || port.getCode() != Integer.parseInt(elements[2])) {
                    try {
                        continent = elements[0];
                        country = elements[1];
                        code = Integer.parseInt(elements[2]);
                        namePort = elements[3];
                        location = createLocation(elements);
                        port = new Port(continent, country, code, namePort, location);
                        store.addToList(port, Double.parseDouble(port.getLocation().getLongitude()), Double.parseDouble(port.getLocation().getLatitude()));
                    } catch (Exception e) {
                        port = null;
                    }
                }
            }
     store.insert();

### Create the Location of a port
    private static Location createLocation(String[] elements) {
        String lat = elements[4];
        String lon = elements[5];
        return new Location(lat, lon);
    }
### Add the ports to the KDTree

         store.addToList(port, Double.parseDouble(port.getLocation().getLongitude()), Double.parseDouble(port.getLocation().getLatitude()));
         (...)
         store.insert();


## Testing

## Test 1: Check whether It is comparing the y correctly when adding to the KDTree

    @Test
    void killComparator3Y() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpY.compare(node, node1);
        int expected = 0;
        assertEquals(expected, actual);
    }


## Test 2: Check whether It is comparing the x correctly when adding to the KDTree

    @Test
    void killComparatorX() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpX.compare(node1, node2);
        int expected = -1;
        assertEquals(expected, actual);
    }

## Test 3: See whether the compareAxis is being done as intended

    @Test
    void killCompareAxisMutants1() {

        node1.compareAxis(node2, 0);
        node1.compareAxis(node3, 1);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    node1.compareAxis(node2, -1);
                });
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> {
                    node1.compareAxis(node2, 2);
                });
    }




## Review

The way I import the Ports to a list first, for it to balance it out and then do the insertions in the KDTree is the way
to go. But I wonder if I could have optimized it as much as I could by approaching the problem differently.

## US202

###  As a Traffic manager, I which to find the closest port of a ship given its CallSign, on a certain DateTime.

### Analysis
### System Sequence Diagram

![US202_SSD](/docs/Sprint 2/US202/US_202_SSD.svg)

The traffic manager chooses a date and a call sign to find the closest port of.

### Domain Model Diagram
![US201_SSD](/docs/Sprint 2/US202/US_202_DM.svg)

### Design
### Class Diagram
![US202_CD](/docs/Sprint 2/US202/US_202_CD.svg)

The ship's message is obtained from the ships in the shipStore and then the kdTree's nearestneighbour search is used to find the closest port.

### System Diagram
![US202_SD](/docs/Sprint 2/US202/US_202_SD.svg)

The system searches for the ship message and then finds the closest Port

## Implementation

### finding the ship messsage

     private ShipDynamic findShipDynamicUsingCallSign(String callSign ,String baseDateTime){
        for (Ship ship:store.inOrder()){
            if (ship.getShipId().getCallsign().equals(callSign)){
                for (ShipDynamic shipDynamic: ship.getRoute().getRoute()){
                    if (shipDynamic.getBaseDateTime().equals(baseDateTime)){
                        return shipDynamic;
                    }
                }
            }
        }
        return null;
    }

### nearestneighboursearch
    private T findNearestNeighbour(Node<T> node, double x, double y,
                                   Node<T> closestNode, boolean divX) {
        if (node == null) {
            return null;
        }
        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
        double closestDist = Point2D.distanceSq(closestNode.coords.x,
                closestNode.coords.y, x, y);
        if (closestDist > d) {
            closestNode.info=node.getInfo();
            closestNode.coords.x=node.coords.getX();
            closestNode.coords.y=node.coords.getY();
        }
        double delta = divX ? x - node.coords.x : y - node.coords.y;

        double delta2 = delta * delta;
        Node<T> node1 = delta < 0 ? node.left : node.right;
        Node<T> node2 = delta < 0 ? node.right : node.left;
        findNearestNeighbour(node1, x, y, closestNode, !divX);
        if (delta2 < closestDist) {
            findNearestNeighbour(node2, x, y, closestNode, !divX);

        }

        return closestNode.info;
    }

## Testing

## Test 1: if the correct port is returned

    @Test
    public void getClosestPort(){
        Location location1 = new Location("10.41666667","-75.53333333");
        Location location2 = new Location("43.2","10.2");
        Location location22 = new Location("43.2","10.2");

        Location location3= new Location("44.65","-63.56666667");
        Location location4= new Location("-20.00","40.00");

        Port port1= new Port("America","Colombia",28313,"Cartagena",location1);
        Port port2= new Port("Europe","Italy",98732,"port2",location2);
        Port port22= new Port("Europe","Italy",98732,"port2",location2);
        Port port3= new Port("America","Canada",22226,"Halifax",location3);
        Port port4= new Port("Europe","Spain",98734,"port4",location4);
        assertEquals(dynamic.getClosestPort().toString(),port3.toString());
        assertNotEquals(dynamic.getClosestPort().toString(),port1.toString());
    }


## Review

Still haven't changed the AVL search.

  
## US204

### As a Client, I want to know the current situation of a specific container being used to transport my goods.

### Analysis:

### System Sequence Diagram
![US204_SSD](/docs/Sprint 2/US204/US_204_SSD.svg)

The Client will introduce the container number and receive the data

### Domain Model Diagram
![US_204_DM](/docs/Sprint 2/US204/US_204_DM.svg)

### Design

### Class Diagram
![US204_CD](/docs/Sprint 2/US204/US_204_CD.svg)

We have used a ContainerController to connect the "UI" layer which in this case is the Test Class ContainerControllerTest
The connection with the PLSQL function is done in the ContainerDB class.

### Sequence Diagram
![US204_SD](/docs/Sprint 2/US204/US_204_SD.svg)

The system will receive the container number and the find its whereabouts

## Implementation
### Call the PLSQL Function

    public String getContainerStatus(DatabaseConnection connection,String numberContainer) throws SQLException {
        String res = "";
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_GETSTATUSCONTAINER(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, numberContainer);

            callStmt.execute();
            res  = (String)callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

## Review

## US205

### As Ship Captain, I want the list of containers to be offloaded in the next port, including container identifier, type, position, and load.

### Analysis:

### System Sequence Diagram
![US205_SSD](/docs/Sprint 2/US205/US205_SSD.svg)

### Domain Model Diagram
![US_205_DM](/docs/Sprint 2/US205/US205_DM.svg)

### Design

### Class Diagram
![US205_CD](/docs/Sprint 2/US205/US205_CD.svg)

### Sequence Diagram
![US205_SD](/docs/Sprint 2/US205/US205_SD.svg)


## Implementation
### Call the PLSQL Function


     public List<Container> getContainersToOffloadInNextPort(DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        List<Container> list = new LinkedList<>();

        ResultSet rSet;

        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fnc_getContainersToOffload(?,?)}");) {
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.setInt(2, idShipCap);
            callStmtAux.setString(3, portCode);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while (rSet.next()) {
                list.add(new Container(rSet.getString(1), new TypeContainer(rSet.getString(2)),
                        rSet.getString(3), new Position(rSet.getInt(4), rSet.getInt(5),
                        rSet.getInt(6)), new Port(rSet.getString(7)), rSet.getString(8)));
            }
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
        return list;
    }

## Review


## US206

### As Ship Captain, I want the list of containers to be offloaded in the next port, including container identifier, type, position, and load.

### Analysis:

### System Sequence Diagram
![US206_SSD](/docs/Sprint 2/US206/US206_SSD.svg)

### Domain Model Diagram
![US206_DM](/docs/Sprint 2/US206/US206_DM.svg)

### Design

### Class Diagram
![US206_CD](/docs/Sprint 2/US206/US206_CD.svg)

### Sequence Diagram
![US206_SD](/docs/Sprint 2/US206/US206_SD.svg)

## Implementation
### Call the PLSQL Function


      public List<Container> getContainersToLoadInNextPort(DatabaseConnection connection, int idShipCap, String portCode) throws SQLException {
        List<Container> l = new LinkedList<>();

        ResultSet rSet;

        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call fnc_getContainersToLoad(?,?)}");) {
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.setInt(2, idShipCap);
            callStmtAux.setString(3, portCode);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while (rSet.next()) {
                l.add(new Container(rSet.getString(1), new TypeContainer(rSet.getString(2)),
                        rSet.getString(3), new Position(rSet.getInt(4), rSet.getInt(5),
                        rSet.getInt(6)), new Port(rSet.getString(7)), rSet.getString(8)));

            }
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
        return l;
    }

## Review


## US207

### As Ship Captain, I want to know how many cargo manifests I have transported during a given year and the average number of containers per manifest.

### Analysis:

### System Sequence Diagram
![US207_SSD](/docs/Sprint 2/US207_V2/US_207_SSD.svg)

The Client will introduce the container number and receive the data

### Domain Model Diagram
![US_207_DM](/docs/Sprint 2/US207_V2/US_207_DM.svg)

### Design

### Class Diagram
![US207_CD](/docs/Sprint 2/US207_V2/US_207_CD.svg)

We have used a ContainerController to connect the "UI" layer which in this case is the Test Class ContainerControllerTest
The connection with the PLSQL function is done in the ContainerDB class.

### Sequence Diagram
![US204_SD](/docs/Sprint 2/US207_V2/US_207_SSD1.svg)

![US204_SD](/docs/Sprint 2/US207_V2/US_207_SSD2.svg)

The system will receive the container number and the find its whereabouts

## Implementation
### Call the PLSQL Function

    public int getContainerManifestsYear(DatabaseConnection connection,int shipCaptainID, int year){
        int res = 0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_SHIPCAPTAINMANIFESTYEAR(?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, shipCaptainID);
            String stringDate1=year+"/01/01 00:01";
            String stringDate2=year+"/12/31 23:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            java.util.Date date1 = null;
            java.util.Date date2 = null;
            try {
                date1 = sdf.parse(stringDate1);
                date2= sdf.parse(stringDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date1!= null;
            assert date2!= null;
            long millis1 = date1.getTime();
            long millis2 = date2.getTime();
            Date startDate = new Date(millis1);
            Date endDate= new Date(millis2);
            callStmt.setDate(3,startDate);
            callStmt.setDate(4,endDate);

            callStmt.execute();
            res  = (Integer) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public double getAverageContainersForManifestYear(DatabaseConnection connection,int shipCaptainID, int year){
        double res = 0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_GETAVERAGECONTAINERSHIPCAPTAINYEAR(?,?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.DOUBLE);
            callStmt.setInt(2, shipCaptainID);
            String stringDate1=year+"/01/01 00:01";
            String stringDate2=year+"/12/31 23:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            java.util.Date date1 = null;
            java.util.Date date2 = null;
            try {
                date1 = sdf.parse(stringDate1);
                date2= sdf.parse(stringDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date1!= null;
            assert date2!= null;
            long millis1 = date1.getTime();
            long millis2 = date2.getTime();
            Date startDate = new Date(millis1);
            Date endDate= new Date(millis2);
            callStmt.setDate(3,startDate);
            callStmt.setDate(4,endDate);

            callStmt.execute();
            res  = (Double) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

## Review


## US208

### As Ship Captain, I want to know the occupancy rate (percentage) of a given ship for a given cargo manifest. Occupancy rate is the ratio between total number of containers in the ship coming from a given manifest and the total capacity of the ship, i.e., the maximum number of containers the ship can load.

### Analysis:

### System Sequence Diagram
![US208_SSD](/docs/Sprint 2/US208/US208_SSD.svg)

The Ship Captain will introduce the mmsi of the ship and the manifest, where the occupancy rate will be seen.

### Domain Model Diagram
![US208_DM](/docs/Sprint 2/US208/US208_DM.svg)

### Design

### Class Diagram
![US208_CD](/docs/Sprint 2/US208/US208_CD.svg)

We have used a ContainerController to connect the "UI" layer which in this case is the Test Class ContainerControllerTest
The connection with the PLSQL function is done in the ContainerDB class.

### Sequence Diagram
![US208_SD](/docs/Sprint 2/US208/US208_SD.svg)

The system will receive the mmsi for the ship to be searched, and the manifest to be searched. Then it will call the PLSQL function and
present the result to the User

## Implementation
### Call the PLSQL Function

    public int getOccupancyRateFromCertainManifest(DatabaseConnection connection, String mmsi, Integer idManifest) throws SQLException {
        int res = 0;
        try(CallableStatement callStmt = connection.getConnection().prepareCall("{ ? = call FUNC_GETCONTAINERSFROMCERTAINMANIFEST(?,?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, Integer.parseInt(mmsi));
            callStmt.setInt(3, idManifest);

            callStmt.execute();
            res  = (Integer) callStmt.getObject(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

## Review



## US209

### As Ship Captain, I want to know the occupancy rate of a given ship at a given moment.

### Analysis:

### System Sequence Diagram
![US209_SSD](/docs/Sprint 2/US209/US209_SSD.svg)

The Ship Captain will introduce the mmsi of the ship and the time, where the occupancy rate will be seen. 

### Domain Model Diagram
![US209_DM](/docs/Sprint 2/US209/US209_DM.svg)

### Design

### Class Diagram
![US209_CD](/docs/Sprint 2/US209/US209_CD.svg)

We have used a ContainerController to connect the "UI" layer which in this case is the Test Class ContainerControllerTest
The connection with the PLSQL function is done in the ContainerDB class.

### Sequence Diagram
![US209_SD](/docs/Sprint 2/US209/US209_SD.svg)

The system will receive the mmsi for the ship to be searched, and the date to be searched. Then it will call the PLSQL function and
present the result to the User

## Implementation
### Call the PLSQL Function

    public int getOccupancyRateFromDate(DatabaseConnection connection, String mmsi, String dateToBeIntroduced) throws SQLException {
    int result = 0;
    try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call func_getContainersFromCertainDate(?,?)}");){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
    java.util.Date date = null;
    try {
    date = sdf.parse(dateToBeIntroduced);
    } catch (ParseException e) {
    e.printStackTrace();
    }
    assert date != null;
    long millis = date.getTime();
    Date date1 = new Date(millis);

            callStmtAux.registerOutParameter(1, OracleTypes.INTEGER);
            callStmtAux.setInt(2, Integer.parseInt(mmsi));
            callStmtAux.setDate(3,date1);
            callStmtAux.execute();
            result  = (Integer) callStmtAux.getObject(1);
        }catch(SQLException ignored) {
            ignored.printStackTrace();
        }
        return result;
    }

## Review

# Sprint 3

## US302

### [US302] As a Traffic manager I wish to colour the map using as few colours as possible.

### Analysis
### System Sequence Diagram

![US303_SSD](/docs/Sprint3/US302/US302_SSD.svg)

### Domain Model Diagram
![US302_DM](/docs/Sprint3/US302/US302_DM.svg)

### Design
### Class Diagram
![US302_CD](/docs/Sprint3/US302/US302_CD.svg)

### System Diagram
![US302_SD](/docs/Sprint3/US302/US302_SD.svg)

## Implementation

### Color Map
    
    public static Map<GraphLocation, Integer> colorMap(Graph<GraphLocation, Double> G) {

        GraphLocation v = G.vertices().get(0);
        for (GraphLocation vertex : G.vertices()) {
            Collection<GraphLocation> adjV = G.adjVertices(vertex);
            adjV.removeIf(vert -> !vert.getClass().equals(Country.class));
            Collection<GraphLocation> vSize = G.adjVertices(v);
            vSize.removeIf(vert -> !vert.getClass().equals(Country.class));
            if (adjV.size() > vSize.size()) {
                v = vertex;
            } }

        Map<GraphLocation, Integer> coloredMap = new HashMap<>();
        Queue<GraphLocation> queue = new LinkedList<>();
        queue.add(v);

        for (GraphLocation country : G.vertices()) {    // Atribuição de -1 como cor a todos os países (default)
            coloredMap.put(country, -1); }

        int n = 100;                                    // Número máximo de cores
        int colorsCardinality = 0;

        while (!queue.isEmpty()) {
            GraphLocation i = queue.poll();
            Collection<Edge<GraphLocation, Double>> edges = G.incomingEdges(i);
            Collection<GraphLocation> adjV = G.adjVertices(i);

            for (GraphLocation e : adjV) {
                int colorEnd = coloredMap.get(e);
                if (colorEnd == -1) {
                    queue.add(e);
                } }

            for (int j = 0; j < n; j++) {
                coloredMap.put(i, j);
                if (colorsCardinality < j) {
                    colorsCardinality = j;
                }
                if (isSafe(edges, coloredMap)) {
                    break;
                } } }

        Map<GraphLocation, Integer> countriesColored = new HashMap<>();
        for (GraphLocation g : coloredMap.keySet()) {
            if (g.getClass().equals(Country.class) && coloredMap.get(g) != -1) {
                countriesColored.put(g, coloredMap.get(g));
            } else if (g.getClass().equals(Country.class)) {
                countriesColored.put(g, 0);
            } }

        return countriesColored;
    }

### isSafe

    public static boolean isSafe(Collection<Edge<GraphLocation, Double>> edges, Map<GraphLocation, Integer> coloredMap) {
        for (Edge<GraphLocation, Double> e : edges) {
            GraphLocation startVertex = e.getVOrig();
            GraphLocation endVertex = e.getVDest();
            int colorStart = coloredMap.get(startVertex);
            int colorEnd = coloredMap.get(endVertex);
            if (colorStart == colorEnd)
                return false;
        }
        return true;
    }
  
## Testing

## Test 1: Check map colored size

     @Test
     void colorMap() throws IOException {
        CountryPortGraph cPG = new CountryPortGraph();
        ColorMapController cMP = new ColorMapController();

        Graph<GraphLocation,Double> graph = cPG.createGraphWithPortsAndCountries(2);
        Map <GraphLocation,Integer> mapColored = cMP.colorMap(graph);

        assertEquals(68, mapColored.size());
        assertEquals(0, mapColored.get(graph.vertices().get(34)));
     }

## Test 2: Empty in is Safe method

    @Test
    void testIsSafe() {
        ArrayList<Edge<GraphLocation, Double>> edges = new ArrayList<>();
        assertTrue(ColorMap.isSafe(edges, new HashMap<>()));
    }

    
## US303

### [US303] As a Traffic manager I wish to know which places (cities or ports) are closest to all other places (closeness places).

### Analysis
### System Sequence Diagram

![US303_SSD](/docs/Sprint 3/US303/US303_SSD.svg)

The Traffic manager will need to select the number of closeness countries/ports he wants to see . With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US303_SSD](/docs/Sprint 3/US303/US303_DM.svg)

### Design
### Class Diagram
![US303_CD](/docs/Sprint 3/US303/US303_CD.svg)

We used the class CountryPortGraph which will contain the ports and countries inserted into a graph.
Both classes (Port and Country) implement the interface GraphLocation in order to override common methods both classes will need to have


### System Diagram
![US303_SD](/docs/Sprint 3/US201/US201_SD.svg)

The system will receive the number of countries to get the closeness ports/countries.
With the graph containing both types of instances we can calculate the average distance between one instance to all the 
others, in the same country, and store it in itself.
With that we will sort the graph by lowest average distance and get the n lowest.

## Implementation

### Calculate and assign the closeness to the graphLocations instances

     private static <V> void assignCloseness(LinkedList<V> vectors) {
        for (V country1 : vectors) {
            GraphLocation country1Casted;
            double cont = 0;
            double sumDistance = 0;

            if(country1 instanceof Country) {
                country1Casted = (Country) country1;
            }else{
                country1Casted = (Port) country1;
            }
            for (V country2 : vectors) {
                GraphLocation country2Casted;

                if(country2 instanceof Country) {
                    country2Casted = (Country) country2;
                }else{
                    country2Casted = (Port) country2;
                }
                if ((!(country1Casted).getName().equals((country2Casted).getName())) && (country1Casted).getContinent().getName().equals((country2Casted).getContinent().getName())) {
                    sumDistance += Calculator.calculateLocationDifference((country1Casted).getLocation(), (country2Casted).getLocation());
                    cont++;
                }
                
            }
            if (cont != 0) {
                country1Casted.setAverageCloseness(sumDistance / cont);
            }
        }

### Get all GraphLocations from the graph which are in the same continent
    private static List<GraphLocation> getAllCountriesFromContinent(String continent, LinkedList<GraphLocation> vLinkedList) {
        List<GraphLocation> countriesInContinent = new ArrayList<>();
        for (GraphLocation graph : vLinkedList) {
            if (graph.getContinent().getName().equals(continent)) {
                countriesInContinent.add(graph);
            }
        }
        return countriesInContinent;
    }
### Sort the graph by closeness

         public List<GraphLocation> calculateCloseness(int numberOfGraphs, String continent) {
        vLinkedList = Algorithms.breadthFirstSearch(matrixGraph, matrixGraph.vertex(3));
        assert vLinkedList != null;
        vLinkedList.sort(Comparator.comparingDouble(GraphLocation::getCloseness));
        graphLocations = getAllCountriesFromContinent(continent, vLinkedList);
        if (graphLocations.size() >= numberOfGraphs) {
            return getAllCountriesFromContinent(continent, vLinkedList).subList(0, numberOfGraphs);
        } else {
            return Collections.emptyList();
        }
    }


## Testing

## Test 1: Check whether It is comparing the y correctly when adding to the KDTree

    @Test
    void killComparator3Y() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpY.compare(node, node1);
        int expected = 0;
        assertEquals(expected, actual);
    }


## Test 2: Check whether It is comparing the x correctly when adding to the KDTree

    @Test
    void killComparatorX() {
        KDTree<Port> kdTree = new KDTree();
        KDTree.Node<Port> node = new KDTree.Node<Port>(port, Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude()), new KDTree.Node<Port>(Double.parseDouble(port1.getLocation().getLongitude()), Double.parseDouble(port1.getLocation().getLatitude())), new KDTree.Node<Port>(Double.parseDouble(port2.getLocation().getLongitude()), Double.parseDouble(port2.getLocation().getLatitude())), false);
        int actual = kdTree.cmpX.compare(node1, node2);
        int expected = -1;
        assertEquals(expected, actual);
    }

## Test 3: See whether the compareAxis is being done as intended

    @Test
    void killCompareAxisMutants1() {

        node1.compareAxis(node2, 0);
        node1.compareAxis(node3, 1);
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    node1.compareAxis(node2, -1);
                });
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> {
                    node1.compareAxis(node2, 2);
                });
    }




## Review

There could be a better way to present the result to the user. I just haven't figured out how.

## US302

### [US304] As Ship Captain, I want to have access to audit trails for a given container of a given cargo manifest, that is, I want to have access to a list of all operations performed on a given container of a given manifest, in chronological order.

### Analysis
### System Sequence Diagram

![US304_SSD](/docs/Sprint3/US304/US304_SSD.svg)

### Domain Model Diagram
![US304_DM](/docs/Sprint3/US304/US304_DM.svg)

### Design
### Class Diagram
![US304_CD](/docs/Sprint3/US304/US304_CD.svg)

### System Diagram
![US304_SD](/docs/Sprint3/US304/US304_SD.svg)

## Implementation
  
    create or replace trigger AuditTrail1 after insert
    on ContainerManifest for each row
    declare
    cargo NUMBER;
    begin
    select idCargoManifest into cargo from Manifest where Manifest.idManifest = :new.idManifest;
    INSERT INTO AuditTrail (registDate, author, action, idCargoManifest, idManifest, nrContainer)
    VALUES (SYSDATE,TO_CHAR(USER),'INSERT',cargo,  :new.idManifest, :new.nrContainer);
    EXCEPTION
    WHEN NO_DATA_FOUND
    THEN
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20299)));
    WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20298)));
    end;
    /


    create or replace trigger AuditTrail2 before update
    on ContainerManifest for each row
    declare
    cargo NUMBER;
    begin
    select idCargoManifest into cargo from Manifest where Manifest.idManifest = :new.idManifest;
    INSERT INTO AuditTrail (registDate, author, action, idCargoManifest, idManifest, nrContainer)
    VALUES (SYSDATE,TO_CHAR(USER),'UPDATE',cargo, :new.idManifest,:new.nrContainer);
    EXCEPTION
    WHEN NO_DATA_FOUND
    THEN
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20299)));
    WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20298)));
    end;
    /

    create or replace trigger AuditTrail3 before delete
    on ContainerManifest for each row
    declare
    cargo NUMBER;
    begin
    select idCargoManifest into cargo from Manifest where Manifest.idManifest = :new.idManifest;
    INSERT INTO AuditTrail (registDate, author, action, idCargoManifest, idManifest, nrContainer)
    VALUES (SYSDATE,TO_CHAR(USER),'DELETE',cargo, :new.idManifest,:new.nrContainer);
    EXCEPTION
    WHEN NO_DATA_FOUND
    THEN
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20299)));
    WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20298)));
    end;


    create or replace function fnc_getAudit (idCargo Number, container AuditTrail.nrContainer%TYPE)
    RETURN SYS_REFCURSOR IS
    cursor_audit SYS_REFCURSOR;
    begin
    open cursor_audit for
    select * from AuditTrail where idCargoManifest = idCargo AND nrContainer = container;
    return cursor_audit;
    end;

  
## Testing

## Test: Check sql return

     @Test
    void getAudit() throws SQLException {
        AuditController ac = new AuditController();
        DatabaseConnection connection = new DatabaseConnection("jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10713/xepdb1?oracle.net.disableOob=true", "LAPR3_G076", "mypassword");
        List<AuditTrail> audit = ac.getAudit(connection,4,"JORU1234563");
        for (int i = 0; i < audit.size(); i++) {
            System.out.println(audit.get(i));
        }
        String a = "[AuditTrail - Author = 'LAPR3_G076' Date = '2021-12-21 00:06:27' nrContainer = '4' Action = 'LAPR3_G076' idCargo = 'INSERT' idManifest = '4, AuditTrail - Author = 'LAPR3_G076' Date = '2021-12-21 00:06:27' nrContainer = '6' Action = 'LAPR3_G076' idCargo = 'INSERT' idManifest = '4]";
        String b = "Author = 'LAPR3_G076' Date = '2021-12-27 23:36:23' nrContainer = 'JORU1234563' Action = 'INSERT' idCargo = '4' idManifest = '4\n" +
                "AuditTrail - Author = 'LAPR3_G076' Date = '2021-12-27 23:36:23' nrContainer = 'JORU1234563' Action = 'INSERT' idCargo = '4' idManifest = '6\n";
        assertEquals(2, audit.size());
        assertNotEquals(audit.toString(),b);
        assertNotEquals(a,audit.toString());
    }

    

## US305

### [US305] As Client, I want to know the route of a specific container I am leasing.

### Analysis
### System Sequence Diagram

![US305_SSD](/docs/Sprint 3/US305/US305_SSD.svg)

The Client will need to type his identifying number and the number of container he wants to see the route from . With that the system will do its operations and will present the user the result.

### Domain Model Diagram
![US305_DM](/docs/Sprint 3/US305/US305_DM.svg)

### Design
### Class Diagram
![US305_CD](/docs/Sprint 3/US305/US305_CD.svg)

We have used a ContainerController to connect the "UI" layer which in this case is the Test Class ContainerControllerTest
The connection with the PLSQL function is done in the ContainerDB class.

### System Diagram
![US305_SD](/docs/Sprint 3/US305/US305_SD.svg)

The system will receive the identifying number for the client and the number of the container to be searched, and the manifest to be searched. Then it will call the PLSQL function and
present the result to the User


## Implementation
### Call the PLSQL Function

    public List<Container> getRoute(DatabaseConnection connection, int idClient, String numberContainer) throws SQLException {
        List<Container> list = new LinkedList<>();

        ResultSet rSet;

        try (CallableStatement callStmtAux = connection.getConnection().prepareCall("{ ? = call func_getRouteFromClientContainer(?,?)}")) {
            callStmtAux.registerOutParameter(1, OracleTypes.CURSOR);
            callStmtAux.setInt(2, idClient);
            callStmtAux.setString(3, numberContainer);
            callStmtAux.execute();
            rSet = (ResultSet) callStmtAux.getObject(1);
            while (rSet.next()) {
                list.add(new Container(rSet.getString(1), new TypeContainer(rSet.getString(2)),
                        rSet.getString(3), rSet.getString(4), new Position(rSet.getInt(5), rSet.getInt(6),
                        rSet.getInt(7)), rSet.getInt(8), new Port(rSet.getString(9)), rSet.getString(10),rSet.getString(11)));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }

## Review



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