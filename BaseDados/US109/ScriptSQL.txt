/* Domain Restrictions.*/

INSERT INTO Client(idClient, name)/*Pass*/
VALUES(1,'Joao');


INSERT INTO Amount(idAmount, maxWeight, weightEmpty, maxWeightPacked , maxVolume)/*Pass*/
VALUES(1,3000,200,200,3000);


/*TABLE : Energy*/

INSERT INTO Energy(idEnergy, nrGenerators, power)/*Pass */
VALUES(3,40, 408);

INSERT INTO Energy(idEnergy, nrGenerators, power)/*Fail, since nrGenerators is not nullable */
VALUES(3,null, 408);

INSERT INTO Measure(idMeasure, lenght, Weight, capacity)/*Pass */
VALUES(3,300, 408, 500);

INSERT INTO Measure(idMeasure, lenght, Weight, capacity)/*Fail, since length is not nullable */
VALUES(3,null, 408, 500);

INSERT INTO Dimension(idDimension, lenght, height)/*Pass */
VALUES(1,1, 1);




INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Pass */
VALUES(1,'Jorge Ferreira', 'Rua do Jorge' ,931943829, 3);

INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Pass */
VALUES(5,'Bruno Lima', 'Rua do Bruno' ,911943529, 5);

INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Pass */
VALUES(2,'Igor Monteiro', 'Rua do Igor' ,921943529, 2);

INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Pass */
VALUES(6,'Rafael Mendes', 'Rua do Rafael' ,951946229, 6);

INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Fail, since the phone number has to have 9 digits. */
VALUES(1,'Jorge Ferreira', 'Rua do Jorge' ,1245125, 3);

INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Fail, since the nrIdentification is unique in here it isn't. */
VALUES(5,'Jorge Ferreiras', 'Rua dos Jorges' ,931943879, 3);

INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Fail, since address is not nullable */
VALUES(1,'Jorge Ferreiras', null ,12455125, 3); 


/*TABLE : NumberContainer*/

INSERT INTO Container(iso, certificate, idAmount, idClient, idDimension)/*Fail, since length is not nullable */
VALUES('3243','345123', 1, 1,1);

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Pass*/
VALUES('ABC','U', 'A324', '4', '3243');

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail since the the equipment identifier, can only be of U/J/Z*/
VALUES('ABZ','B', 'A124', '5', 'ISO3');

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail since the the ownerPrefix has to be 3 letters and this one has only 2*/
VALUES('AZ','U', 'A124', '5', 'ISO3');

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail since the ownerPrefix has no letters*/
VALUES('333','U', 'A124', '5', 'ISO4');

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail since the serialNumber has to be unique */
VALUES('APO','U', 'A324', '7', 'IS43');


INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail since the checkDigit has to be unique */
VALUES('APC','U', 'A129', '4', 'ISO9');

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Pass*/
VALUES('AZV','U', 'A124', '9', 'ISO3');






INSERT INTO TraficManager(idWorker)/*Pass */
VALUES(1); 

INSERT INTO Position(idPosition, x, y, z, idWorker)/*Pass. */
VALUES(1,2, 3 ,4, 1); 

INSERT INTO Position(idPosition, x, y, z, idWorker)/*Fail, since y is not nullable */
VALUES(1,2, null ,4, 5); 

INSERT INTO Local(idLocal)/*Pass */
VALUES(32); 

INSERT INTO Local(idLocal)/*Pass */
VALUES(1); 

INSERT INTO Position(idPosition, x, y, z, idWorker)/*Pass */
VALUES(50,2, 3 ,4, 1); 

INSERT INTO UnloadManifest(idUnderloadManifest, grossWeight, idLocal, idPosition)/*Pass */
VALUES(32,30000 , 32 ,1); 

INSERT INTO Vehicle(idVehicle, idLocal)/*Pass */
VALUES(1, 32); 

INSERT INTO LoadManifest(idLoadManifest, grossWeight, iso, idPosition, idVehicle)/*Pass*/
VALUES (1,1000,'3243',50,1);



INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Pass */
VALUES ('Morocco', 'Africa', 'Agadir', 84, 142, 32);

INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Fail, since latitude is more than 90 */
VALUES ('Portugal', 'Europe', 'Sines', 91, 142, 1);

INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Fail, since latitude is less than 90 */
VALUES ('Portugal', 'Europe', 'Sines', -91, 142, 1);

INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Fail, since longitude is more than 180 */
VALUES ('Portugal', 'Europe', 'Sines', 43, 181, 1);

INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Fail, since longitude is less than 180*/
VALUES ('Portugal', 'Europe', 'Sines', 21, -181, 1);

INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Pass*/
VALUES ('Portugal', 'Europe', 'Sines', 30, 142, 1);


INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Fail, since latitude is more than 90 */
VALUES ('Portugal', 'Europe', 'Sines', 91, 142, 1);

INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Fail, since latitude is less than 90 */
VALUES ('Portugal', 'Europe', 'Sines', -91, 142, 1);

INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Fail, since longitude is more than 180 */
VALUES ('Portugal', 'Europe', 'Sines', 43, 181, 1);

INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Fail, since longitude is less than 180*/
VALUES ('Portugal', 'Europe', 'Sines', 21, -181, 1);



INSERT INTO TruckDriver(drivingLicense, idWorker)/*Pass*/
VALUES ('drivingLicense',1);

INSERT INTO Vehicle(idVehicle, idLocal)/*Pass*/
VALUES (5,1);


INSERT INTO Truck(registration, idVehicle, idWorker)/*Pass*/
VALUES ('4124',1,1);

INSERT INTO TraficManager(idWorker)/*Pass*/
VALUES (5);

INSERT INTO ChiefEletrical(idChiefEletrical, qualification, idWorker)/*Pass*/
VALUES (1,'Eletric',1);

INSERT INTO ShipCaptain(qualification, idWorker)/*Pass*/
VALUES ('Eletric',1);

INSERT INTO FleetManager(idFleet, idWorker)/*Pass*/
VALUES (1,1);

INSERT INTO PortStaff(idWorker, idLocal)/*Pass*/
VALUES (5,32);

INSERT INTO PortManager(idWorker, idLocal)/*Pass*/
VALUES (6,32);

INSERT INTO Local(idLocal)/*Pass*/
VALUES (52);

INSERT INTO Local(idLocal)/*Pass*/
VALUES (42);

INSERT INTO WarehouseStaff(idWorker, idLocal)/*Pass*/
VALUES (1,1);

SELECT * FROM Warehouse;



INSERT INTO WarehouseManager(idWorker, idLocal)/*Pass*/
VALUES (1,1);


/*TABLE : PositionShip*/

INSERT INTO Ship(mmsi, draft, name,imo) 
VALUES (211331640, 23, '2ream sqwesof Waking','IMO2344334');

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, -120.136, 107.3, 15.8, 0, 25, 'A');  /*Pass*/
SELECT * FROM PositionShip;

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 94.238, -120.136, 107.3, 15.8, 107,25,'A');/*Fail since the latitude is not in the interval [-90,90]*/

SELECT * FROM Ship where mmsi = 211331640;

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, -420.136, 107.3, 15.8, 107,25,'A');/*Fail since the longitude is not in the interval [-180,180]*/

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, 500.136, 107.3, 15.8, 107,25,'A');/*Fail since the longitude is not in the interval [-180,180]*/

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, -120.136, 400.3, 15.8, 107,25,'A');/*Fail since the cog is not in the interval [0,359]*/

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, -120.136, -100.3, 15.8, 107,25,'A');/*Fail since the cog is not in the interval [0,359]*/

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, -120.136, 100.3, 0.4, -407,25,'A');/*Fail since the heading is not in [0,359]*/

INSERT INTO PositionShip(mmsi,latitude,longitude,cog,sog,heading,position,transceiver) 
VALUES (211331640, 34.238, -120.136, 100.3, 0.4, 207,25,'C');/*Fail since the transceiver can only be "A" or "B"*/

SELECT * FROM PositionShip;

/*TABLE: Ship*/

/*MMSI TEST*/
INSERT INTO Ship(mmsi, draft, name,imo) 
VALUES (123456732, 23, '2ream sqwesof Waking','IMO2344534');
/*o primeiro valor tem 9 digitos(no MMSI) e consequentemente devia ser inserido com sucesso,enquanto o segundo tem 8 digitos e nao deveria ser inserido*/
INSERT INTO Ship(mmsi, draft, name,imo) 
VALUES (12345672, 23, '2ream sqwesof Wakins','IMO2344334');
/*IMO TEST*/
INSERT INTO Ship(mmsi, draft, name,imo) 
VALUES (123656731, 23, '2ream sqwesossf Waking','IMO2353442');
/*o primeiro valor tem 7 digitos(no IMO) e consequentemente devia ser inserido com sucesso,enquanto o segundo tem 8 digitos e nao deveria ser inserido*/
INSERT INTO Ship(mmsi, draft, name,imo) 
VALUES (163151722, 23, '2ream sqwesof Wakins','IMO435349');

/*Fail since the imo is a unique attribute and this imo already has this value*/
INSERT INTO Ship(mmsi, draft, name,imo) 
VALUES (123151722, 23, '2ream sqwesof Wakins','IMO2353442');


INSERT INTO Call(hourCall, dateCall, mmsi)/*Fail, since the foreign key (mmsi) does not exist*/
VALUES ('23:30:20',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),'123456732');

















/* Identity Restrictions.*/
/*Table Client:*/

INSERT INTO Client(idClient, name)/*Pass*/
VALUES(5,'Joao');

INSERT INTO Client(idClient, name)/*Fail, since the primary key is unique*/
VALUES(5,'Paulo');

INSERT INTO Client(idClient, name)/*Fail, since the primary key cannot be null*/
VALUES(null,'Paulo');

INSERT INTO Amount(idAmount, maxWeight, weightEmpty, maxWeightPacked , maxVolume)/*Fail, since the primary key is unique*/
VALUES(1,3000,200,200,3000);

INSERT INTO Energy(idEnergy, nrGenerators, power)/*Fail, since the primary key is unique*/
VALUES(3,40, 408);

INSERT INTO Measure(idMeasure, lenght, Weight, capacity)/*Fail, since the primary key is unique*/
VALUES(3,300, 408, 500);

INSERT INTO Dimension(idDimension, lenght, height)/*Fail, since the primary key is unique*/
VALUES(1,1, 1);


INSERT INTO Worker(idWorker, name, address, phoneNumber, nrIdentification)/*Fail, since the primary key is unique*/
VALUES(1,'Jorge Ferreira', 'Rua do Jorge' ,931943829, 3);

INSERT INTO Container(iso, certificate, idAmount, idClient, idDimension)/*Fail, since the primary key is unique*/
VALUES('3243','345123', 1, 1,1);

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail, since the primary key is unique*/
VALUES('ABC','U', 'A324', '4', '3243');

INSERT INTO TraficManager(idWorker)/*Fail, since the primary key is unique*/
VALUES(1); 

INSERT INTO Position(idPosition, x, y, z, idWorker)/*Fail, since the primary key is unique*/
VALUES(1,2, 3 ,4, 1); 

INSERT INTO Local(idLocal)/*Fail, since the primary key is unique*/
VALUES(32); 

INSERT INTO UnloadManifest(idUnderloadManifest, grossWeight, idLocal, idPosition)/*Fail, since the primary key is unique*/
VALUES(32,30000 , 32 ,1); 

INSERT INTO Vehicle(idVehicle, idLocal)/*Fail, since the primary key is unique*/
VALUES(1, 32); 

INSERT INTO LoadManifest(idLoadManifest, grossWeight, iso, idPosition, idVehicle)/*Fail, since the primary key is unique*/
VALUES (1,1000,'3243',50,1);

INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Fail, since the primary key is unique*/
VALUES ('Morocco', 'Africa', 'Agadir', 84, 142, 32);

INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Fail, since the primary key is unique*/
VALUES ('Portugal', 'Europe', 'Sines', 30, 142, 1);

INSERT INTO TruckDriver(drivingLicense, idWorker)/*Fail, since the primary key is unique*/
VALUES ('drivingLicense',1);

INSERT INTO Vehicle(idVehicle, idLocal)/*Fail, since the primary key is unique*/
VALUES (5,1);


INSERT INTO Truck(registration, idVehicle, idWorker)/*Fail, since the primary key is unique*/
VALUES ('4124',1,1);


INSERT INTO TraficManager(idWorker)/*Fail, since the primary key is unique*/
VALUES (5);

INSERT INTO ChiefEletrical(idChiefEletrical, qualification, idWorker)/*Fail, since the primary key is unique*/
VALUES (1,'Eletric',1);

INSERT INTO ShipCaptain(qualification, idWorker)/*Fail, since the primary key is unique*/
VALUES ('Eletric',1);

INSERT INTO FleetManager(idFleet, idWorker)/*Fail, since the primary key is unique*/
VALUES (1,1);

INSERT INTO PortStaff(idWorker, idLocal)/*Fail, since the primary key is unique*/
VALUES (5,32);

INSERT INTO PortManager(idWorker, idLocal)/*Fail, since the primary key is unique*/
VALUES (6,32);

INSERT INTO WarehouseStaff(idWorker, idLocal)/*Fail, since the primary key is unique*/
VALUES (1,1);

INSERT INTO WarehouseManager(idWorker, idLocal)/*Fail, since the primary key is unique*/
VALUES (1,1);

INSERT INTO Call(hourCall, dateCall, mmsi)/*Fail, since the primary key is unique*/
VALUES ('23:30:20',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),'123456732');
















/* Referential Restrictions.*/

INSERT INTO Container(iso, certificate, idAmount, idClient, idDimension)/*Fail, since the foreign keys do not exist*/
VALUES('3253','345123', 60, 90,70);

INSERT INTO NumberContainer(ownerPrefix, equipmentIdentifier, serialNumber, checkDigit, iso)/*Fail, since the foreign key (iso) does not exist*/
VALUES('ABD','U', 'A212', '8', '32');

INSERT INTO TraficManager(idWorker)/*Fail, since the foreign key (idWorker) does not exist*/
VALUES(58); 

INSERT INTO Position(idPosition, x, y, z, idWorker)/*Fail, since the foreign key (idWorker) does not exist*/
VALUES(5,2, 3 ,4, 93); 

INSERT INTO UnloadManifest(idUnderloadManifest, grossWeight, idLocal, idPosition)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES(10,30000 , 82 ,1); 

INSERT INTO UnloadManifest(idUnderloadManifest, grossWeight, idLocal, idPosition)/*Fail, since the foreign key (idPosition) does not exist*/
VALUES(10,30000 , 1 ,34); 

INSERT INTO Vehicle(idVehicle, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES(9, 82); 

INSERT INTO LoadManifest(idLoadManifest, grossWeight, iso, idPosition, idVehicle)/*Fail, since the foreign key (idVehicle) does not exist*/
VALUES (40,1000,'3243',1,62);

INSERT INTO LoadManifest(idLoadManifest, grossWeight, iso, idPosition, idVehicle)/*Fail, since the foreign key (idPosition) does not exist*/
VALUES (40,1000,'3243',20,1);

INSERT INTO Port(country, continent, name, latitude, longitude, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES ('France', 'Europe', 'Nice', 83, 141, 89);

INSERT INTO Warehouse(country, continent, name, latitude, longitude, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES ('Portugal', 'Europe', 'Lamego', 30, 142, 87);

INSERT INTO TruckDriver(drivingLicense, idWorker)/*Fail, since the foreign key (idWorker) does not exist*/
VALUES ('drivingLicense',78);

INSERT INTO Vehicle(idVehicle, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES (34,85);


INSERT INTO Truck(registration, idVehicle, idWorker)/*Fail, since the foreign key (idWorker) does not exist*/
VALUES ('4124',1,1);


INSERT INTO TraficManager(idWorker)/*Fail, since the foreign key (idWorker) does not exist*/
VALUES (82);

INSERT INTO ChiefEletrical(idChiefEletrical, qualification, idWorker)/*Fail, since the foreign key (idWorker) does not exist*/
VALUES (72,'Eletric',82);

INSERT INTO ShipCaptain(qualification, idWorker)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES ('Captain',58);

INSERT INTO FleetManager(idFleet, idWorker)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES (12,42);

INSERT INTO PortStaff(idWorker, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES (43,82);

INSERT INTO PortManager(idWorker, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES (82,64);

INSERT INTO WarehouseStaff(idWorker, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES (58,72);

INSERT INTO WarehouseManager(idWorker, idLocal)/*Fail, since the foreign key (idLocal) does not exist*/
VALUES (23,64);

INSERT INTO Call(hourCall, dateCall, mmsi)/*Fail, since the foreign key (mmsi) does not exist*/
VALUES ('23:30:20',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),'134341243');
