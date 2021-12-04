INSERT INTO UserApplication(email,password)VALUES ('AndrewSmith@gmail.com','password');
INSERT INTO Client(nameClient, nrIdentificationClient,iduser) VALUES ('Andrew Smith',1,1);

INSERT INTO UserApplication(email,password)VALUES ('JohnTrafficManager@shipsinc.com','TrafficManager');
INSERT INTO Address(codaddress,nrdoor,street,parish) VALUES (1,1,'TrafficManagerStreet','TrafficManagerParish');
INSERT INTO Worker(codaddress,iduser,nameworker,nridentification) VALUES(1,2,'John Trafficmanager',1);
Insert INTO TraficManager(idworker) VALUES(1);

INSERT INTO UserApplication(email,password)VALUES ('JulianShipCaptain@shipsinc.com','ShipCaptain');
INSERT INTO Address(codaddress,nrdoor,street,parish) VALUES (2,2,'ShipCaptinStreet','ShipCaptinParish');
INSERT INTO Worker(codaddress,iduser,nameworker,nridentification) VALUES(2,3,'Julian Shipcaptain',2);
INSERT INTO ShipCaptain(idworker,qualification) VALUES(2,'Experiencia de 20 anos');

INSERT INTO UserApplication(email,password)VALUES ('EdwardFleetManager@shipsinc.com','FleetManager');
INSERT INTO Address(codaddress,nrdoor,street,parish) VALUES (3,3,'FleetManagerStreet','FleetManagerParish');
INSERT INTO Worker(codaddress,iduser,nameworker,nridentification) VALUES(3,4,'Edward FleetManager',3);
INSERT INTO FleetManager(idworker) VALUES(3);

INSERT INTO UserApplication(email,password)VALUES ('RobertChiefEletrical@shipsinc.com','ChiefEletrical');
INSERT INTO Address(codaddress,nrdoor,street,parish) VALUES (4,4,'ChiefEletricalStreet','ChiefEletricalParish');
INSERT INTO Worker(codaddress,iduser,nameworker,nridentification) VALUES(4,5,'Robert ChiefEletrical',4);
INSERT INTO ChiefEletrical(idworker,qualification) VALUES(4,'Experiencia de 20 anos');

INSERT INTO UserApplication(email,password)VALUES ('JohnSmith@gmail.com','password1');
INSERT INTO Client(nameClient, nrIdentificationClient,iduser) VALUES ('John Smith',2,6);

INSERT INTO TypeContainer(type,temperature) VALUES(1,-4);
INSERT INTO Dimension(height,length) Values(3.40,2.02);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('XDDD','CERT6N','CBCU2123456','Expensive car',1,1,1);

INSERT INTO TypeContainer(type,temperature) VALUES(2,-3);
INSERT INTO Dimension(height,length) Values(3.20,2.12);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('ABCD','CERT1N','ABCU1113456','Bottled Wine',1,2,2);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('CDCD','CERT2P','TVCU2124466','Medicine',2,1,2);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('ACDS','CERT3G','XGCU2123466','Motorbike',2,1,2);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('TTVB','CERT4F','SSDU2124366','Cash',2,1,2);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('SSET','CERT5P','FFAU2124566','Rations',2,1,2);
INSERT INTO  Container(iso,certificate,numbercontainer,load,idclient,iddimension,type) VALUES ('DFRE','CERT6V','LLCU2124766','Seeds',2,1,2);

INSERT INTO POSITION(idworker,x,y,z) VALUES(1,1,1,1);
INSERT INTO POSITION(idworker,x,y,z) VALUES(1,1,2,1);
INSERT INTO POSITION(idworker,x,y,z) VALUES(1,2,1,1);
INSERT INTO POSITION(idworker,x,y,z) VALUES(1,2,2,1);
INSERT INTO POSITION(idworker,x,y,z) VALUES(1,3,2,1);


INSERT INTO CONTINENT(continent) VALUES('Europe');
INSERT INTO COUNTRY(country,continent) VALUES('Portugal','Europe');
INSERT INTO COUNTRY(country,continent) VALUES('Spain','Europe');

INSERT INTO FACILITY(codefacility,country,latitude,longitude,namefacility,typefacility) VALUES(13012,'Portugal',41.18,-8.7,'Porto de Leixões','port');
INSERT INTO FACILITY(codefacility,country,latitude,longitude,namefacility,typefacility) VALUES(17386,'Spain',41.33,2.167,'Porto de Barcelona','port');
INSERT INTO FACILITY(codefacility,country,latitude,longitude,namefacility,typefacility) VALUES(18476,'Portugal',37.733,-25.6667,'Porto de Leixões','port');

INSERT INTO VEHICLE(idvehicle) VALUES(1);
INSERT INTO Energy(nrGenerators,power) VALUES (3,500);
INSERT INTO SHIP(mmsi,idvehicle,idenergy,draft,nameship,imo,typeship,capacity,length,width,callsign)VALUES('987654321',1,1,9.5,'BigShip','IMO9395043',2,70,166,25,'C4SQ1');

INSERT INTO Trip(idvehicle,idShipCaptain,idFleet,idChiefEletrical,startfacility,endfacility,startdatetrip,enddatetrip) VALUES(1,2,1,1,13012,18476,'2020-05-13','2020-07-2');
INSERT INTO tripfacility(idtrip,codefacility,arrivaldate,departuredate)VALUES(1,13012,'2020-05-11','2020-05-13');
INSERT INTO tripfacility(idtrip,codefacility,arrivaldate,departuredate)VALUES(1,17386,'2020-06-09','2020-06-11');
INSERT INTO tripfacility(idtrip,codefacility,arrivaldate,departuredate)VALUES(1,18476,'2020-07-02','2020-07-20');
INSERT INTO MANIFEST(idtrip,codefacility,grossweight,typeManifest,dateManifest)VALUES(1,13012,10,'load','2020-05-12');
INSERT INTO MANIFEST(idtrip,codefacility,grossweight,typeManifest,dateManifest)VALUES(1,17386,50,'offload','2020-06-10');
INSERT INTO MANIFEST(idtrip,codefacility,grossweight,typeManifest,dateManifest)VALUES(1,17386,45,'load','2020-06-10');
INSERT INTO MANIFEST(idtrip,codefacility,grossweight,typeManifest,dateManifest)VALUES(1,18476,95,'offload','2020-07-03');
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('CBCU2123456',1,1);
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('ABCU1113456',1,2);
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('ABCU1113456',2,2);
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('TVCU2124466',3,2);
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('XGCU2123466',3,3);
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('SSDU2124366',3,4);
INSERT INTO containerManifest(nrContainer,idmanifest,idposition) VALUES('FFAU2124566',3,5);
INSERT INTO UserApplication(email,password)VALUES ('AnthonyShipCaptain@shipsinc.com','ShipCaptain');
INSERT INTO Address(codaddress,nrdoor,street,parish) VALUES (5,5,'ShipCaptainStreet','ShipCaptainParish');
INSERT INTO Worker(codaddress,iduser,nameworker,nridentification) VALUES(5,7,'Julian Shipcaptain',7);
INSERT INTO ShipCaptain(idworker,qualification) VALUES(5,'Experiencia de 20 anos');


