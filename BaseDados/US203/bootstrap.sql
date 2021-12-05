--eliminar tabelas (eventualmente) existentes
DROP TABLE UserApplication   CASCADE CONSTRAINTS PURGE;
DROP TABLE Address           CASCADE CONSTRAINTS PURGE;
DROP TABLE Worker            CASCADE CONSTRAINTS PURGE;
DROP TABLE FleetManager      CASCADE CONSTRAINTS PURGE;
DROP TABLE ShipCaptain       CASCADE CONSTRAINTS PURGE;
DROP TABLE ChiefEletrical    CASCADE CONSTRAINTS PURGE;
DROP TABLE TraficManager     CASCADE CONSTRAINTS PURGE;
DROP TABLE TruckDriver       CASCADE CONSTRAINTS PURGE;
DROP TABLE Position          CASCADE CONSTRAINTS PURGE;
DROP TABLE Manifest          CASCADE CONSTRAINTS PURGE;
DROP TABLE Dimension         CASCADE CONSTRAINTS PURGE;
DROP TABLE Container         CASCADE CONSTRAINTS PURGE;
DROP TABLE CapacityContainer CASCADE CONSTRAINTS PURGE;
DROP TABLE Client            CASCADE CONSTRAINTS PURGE;
DROP TABLE Facility          CASCADE CONSTRAINTS PURGE;
DROP TABLE Vehicle           CASCADE CONSTRAINTS PURGE;
DROP TABLE TRUCK             CASCADE CONSTRAINTS PURGE;
DROP TABLE Trip              CASCADE CONSTRAINTS PURGE;
DROP TABLE Energy            CASCADE CONSTRAINTS PURGE;
DROP TABLE Ship              CASCADE CONSTRAINTS PURGE;
DROP TABLE PositionShip      CASCADE CONSTRAINTS PURGE;
DROP TABLE Transceiver       CASCADE CONSTRAINTS PURGE;
DROP TABLE ContainerManifest CASCADE CONSTRAINTS PURGE;
DROP TABLE TripFacility      CASCADE CONSTRAINTS PURGE;
DROP TABLE FacilityManager   CASCADE CONSTRAINTS PURGE;
DROP TABLE FacilityStaff     CASCADE CONSTRAINTS PURGE;
DROP TABLE Country           CASCADE CONSTRAINTS PURGE;
DROP TABLE TypeContainer     CASCADE CONSTRAINTS PURGE;
DROP TABLE Continent         CASCADE CONSTRAINTS PURGE;
DROP SEQUENCE idVehicle;


--criar tabelas
CREATE TABLE UserApplication(
    idUser   NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    email    VARCHAR(42) CONSTRAINT nn_UserApplication_email    NOT NULL,
    password VARCHAR(42) CONSTRAINT nn_UserApplication_password NOT NULL,
    CONSTRAINT uk_UserApplication_email UNIQUE(email),
    CONSTRAINT pk_UserApplication_idUser PRIMARY KEY(idUser)
);

CREATE TABLE Address(
    codAddress INTEGER     CONSTRAINT pk_Address_codAddress PRIMARY KEY,
    nrDoor     INTEGER     CONSTRAINT nn_Address_nrDoor NOT NULL,
    street     VARCHAR(42) CONSTRAINT nn_Address_street NOT NULL,
    parish     VARCHAR(42) CONSTRAINT nn_Address_parish NOT NULL
);

CREATE TABLE Worker(
    idWorker         NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    nameWorker       VARCHAR(42)    CONSTRAINT  nn_Worker_nameWorker              NOT NULL,
    nrIdentification INTEGER        CONSTRAINT  nn_Worker_nrIdentification        NOT NULL,
    codAddress       INTEGER        REFERENCES  Address(codAddress),
    idUser           INTEGER        REFERENCES  UserApplication(idUser),
    CONSTRAINT uk_Worker_nrIdentification   UNIQUE(nrIdentification),
    CONSTRAINT pk_Worker_idWorker           PRIMARY KEY(idWorker)
);

CREATE TABLE FleetManager(
    idFleet    NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    idWorker   NUMBER   REFERENCES Worker(idWorker),
    CONSTRAINT  pk_FleetManager_idFleet PRIMARY KEY(idFleet)
);

CREATE TABLE ShipCaptain(
    qualification VARCHAR(42)  CONSTRAINT  nn_ShipCaptain_qualificationShipCaptain  NOT NULL,
    idWorker      NUMBER       CONSTRAINT  pk_ShipCaptain_idWorkerShipCaptain       PRIMARY KEY
);

CREATE TABLE ChiefEletrical(
    idChiefEletrical NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    qualification    VARCHAR(42)    CONSTRAINT  nn_ChiefEletrical_qualification        NOT NULL,
    idWorker         NUMBER         REFERENCES Worker(idWorker),
    CONSTRAINT  pk_ChiefEletrical_idChiefEletrical     PRIMARY KEY(idChiefEletrical)
);

CREATE TABLE TraficManager(
    idWorker NUMBER CONSTRAINT  pk_TraficManager_idWorkerManager  PRIMARY KEY
);

CREATE TABLE TruckDriver(
    drivingLicense  VARCHAR(42)         CONSTRAINT  nn_TruckDriver_drivingLicense       NOT NULL,
    idWorker        NUMBER              CONSTRAINT  pk_TruckDriver_idWorkerTruckDriver  PRIMARY KEY
);

CREATE TABLE Position(
    idPosition NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    X          INTEGER   CONSTRAINT  nn_Position_x           NOT NULL,
    y          INTEGER   CONSTRAINT  nn_Position_y           NOT NULL,
    z          INTEGER   CONSTRAINT  nn_Position_z           NOT NULL,
    idWorker   INTEGER   REFERENCES  TraficManager(idWorker),
    CONSTRAINT  pk_Position_idPosition  PRIMARY KEY(idPosition)
);

CREATE TABLE Dimension(
    idDimension  NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    length       NUMBER(10,2)    CONSTRAINT   nn_Dimension_lenght        NOT NULL,
    height       NUMBER(10,2)    CONSTRAINT   nn_Dimension_height        NOT NULL,
    CONSTRAINT   pk_Dimension_idDimensions  PRIMARY KEY(idDimension)
);

CREATE TABLE Client(
    idClient               NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    nameClient             VARCHAR(42) CONSTRAINT nn_Client_name             NOT NULL,
    nrIdentificationClient INTEGER     CONSTRAINT nn_Client_nrIdentification NOT NULL,
    idUser                 NUMBER      REFERENCES UserApplication(idUser),
    CONSTRAINT uk_Client_nrIdentification      UNIQUE(nrIdentificationClient),
    CONSTRAINT pk_Client_idClient         PRIMARY KEY(idClient)
);

CREATE TABLE TypeContainer(
    type        VARCHAR(42)   CONSTRAINT  pk_TypeContainer_type        PRIMARY KEY,
    temperature NUMBER(6,3)   CONSTRAINT  nn_TypeContainer_temperature NOT NULL
);

CREATE TABLE Container(
    iso             VARCHAR(4)   CONSTRAINT  nn_Container_iso             NOT NULL,
    certificate     VARCHAR(6)   CONSTRAINT  nn_Container_certificate     NOT NULL,
    numberContainer VARCHAR(11)  CONSTRAINT  pk_Conatiner_numberContainer PRIMARY KEY,
    load            VARCHAR(100) CONSTRAINT  nn_Container_load            NOT NULL,
    idDimension     NUMBER       REFERENCES Dimension(idDimension),
    idClient        NUMBER       REFERENCES Client(idClient),
    type            VARCHAR(42)  REFERENCES TypeContainer(type),
    CONSTRAINT      ck_reg_Container_numberContainer CHECK(REGEXP_LIKE(numberContainer, '[A-Z]{3}[U|J|Z][0-9]{7}') )
);

CREATE TABLE CapacityContainer(
    numberContainer VARCHAR(11)      CONSTRAINT pk_CapacityContainer_numberContainer PRIMARY KEY,
    maxWeight       NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_maxWeight       NOT NULL,
    weightEmpty     NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_weightEmpty     NOT NULL,
    maxWeightPacked NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_maxWeightPacked NOT NULL,
    maxVolume       NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_maxVolume       NOT NULL
);


CREATE TABLE Continent(
    continent VARCHAR(42) CONSTRAINT  pk_Continent_Continent  PRIMARY KEY  
);

CREATE TABLE Country(
    country  VARCHAR(42) CONSTRAINT  pk_Country_country PRIMARY KEY,
    continent VARCHAR(42) REFERENCES Continent(continent)
);

CREATE TABLE Vehicle(
    idVehicle INT CONSTRAINT pk_Vehicle_idVehicle PRIMARY KEY
);

CREATE SEQUENCE idVehicle MINVALUE 1 START WITH 1;


CREATE TABLE Facility(
    codeFacility       INTEGER     CONSTRAINT  pk_Facility_codeFacility       PRIMARY KEY,
    nameFacility       VARCHAR(42) CONSTRAINT  nn_Facility_nameFacility       NOT NULL,
    latitude           NUMBER(8,5) CONSTRAINT  nn_Facility_latitude           NOT NULL,
    longitude          NUMBER(8,5) CONSTRAINT  nn_Facility_longitudePort      NOT NULL,
    typeFacility       VARCHAR(42) CONSTRAINT  nn_Facility_typeFacility       NOT NULL,  
    country            VARCHAR(42) REFERENCES  Country(country),
    CONSTRAINT ck_Facility_Latitude        CHECK(latitude<=90 AND latitude>=-90),
    CONSTRAINT ck_Facility_Longitude       CHECK(longitude<=180 AND longitude>=-180),
    CONSTRAINT ck_reg_FacilitytypeFacility CHECK(REGEXP_LIKE(typeFacility, 'port|warehouse|PORT|WAREHOUSE') )
);

CREATE TABLE Truck(
    registration VARCHAR(6) PRIMARY KEY,
    idWorker     NUMBER     REFERENCES TruckDriver(idWorker),
    idVehicle    INT        REFERENCES Vehicle(idVehicle)
);

CREATE TABLE Trip(
    idTrip        NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    endDateTrip   DATE       CONSTRAINT nn_Trip_endDateTrip   NOT NULL,
    startdateTrip DATE       CONSTRAINT nn_Trip_startdateTrip NOT NULL,
    startFacility INTEGER REFERENCES Facility(codeFacility),
    endFacility   INTEGER REFERENCES Facility(codeFacility),
    idVehicle     INTEGER REFERENCES Vehicle(idVehicle),
    idChiefEletrical NUMBER   REFERENCES ChiefEletrical(idChiefEletrical),
    idShipCaptain    NUMBER   REFERENCES ShipCaptain(idWorker),
    idFleet          NUMBER   REFERENCES FleetManager(idFleet),
    CONSTRAINT    pk_Trip_idTrip       PRIMARY KEY(idTrip)
);


CREATE TABLE TripFacility(
    idTrip        NUMBER,
    codeFacility  NUMBER,
    arrivalDate   DATE CONSTRAINT nn_TripFacility_arrivalDate   NOT NULL,
    departureDate DATE CONSTRAINT nn_TripFacility_departureDate NOT NULL,
    CONSTRAINT   pk_TripFacility PRIMARY KEY(idTrip,codeFacility)
);

CREATE TABLE Manifest(
    idManifest     NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    grossWeight    NUMBER(6,3)  CONSTRAINT nn_Manifest_grossWeight  NOT NULL,
    typeManifest   VARCHAR(42)  CONSTRAINT nn_Manifest_typeManifest NOT NULL,
    dateManifest   DATE         CONSTRAINT nn_Manifest_dateManifest NOT NULL,
    idTrip         NUMBER,
    codeFacility   NUMBER,
    CONSTRAINT     ck_reg_Manifest_typeManifest CHECK(REGEXP_LIKE(typeManifest, 'LOAD|OFFLOAD|CARGO|load|offload|cargo') ),
    CONSTRAINT     pk_Manifest_idManifest       PRIMARY KEY(idManifest)
);

CREATE TABLE ContainerManifest(
  idManifest  NUMBER      REFERENCES Manifest(idManifest),
  nrContainer VARCHAR(11) ,
  idPosition NUMBER       REFERENCES Position(idPosition),
  CONSTRAINT pk_ContainerManifest PRIMARY KEY(idManifest, nrContainer)
);

CREATE TABLE Energy(
    idEnergy     NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    nrGenerators INTEGER       CONSTRAINT  nn_Energy_nrGerators     NOT NULL,
    power        NUMBER(10,2)  CONSTRAINT  nn_Energy_power          NOT NULL,
    CONSTRAINT  pk_Energy_idEnergy       PRIMARY kEY(idEnergy)
);

CREATE TABLE Ship(
    mmsi      NUMBER(9,0) CONSTRAINT pk_Ship_mmsi        PRIMARY KEY,
    draft     NUMBER(5,3) CONSTRAINT nn_Ship_draft       NOT NULL,
    nameShip  VARCHAR(42) CONSTRAINT nn_Ship_nameShip    NOT NULL,   
    imo       VARCHAR(10) CONSTRAINT nn_Ship_imo         NOT NULL,
    typeShip  INTEGER     CONSTRAINT nn_Ship_typeShip    NOT NULL,
    capacity  INTEGER     CONSTRAINT nn_Ship_capacity    NOT NULL,
    length    NUMBER(6,3) CONSTRAINT nn_Ship_length      NOT NULL,
    width     NUMBER(6,3) CONSTRAINT nn_Ship_width       NOT NULL,
    callsign  VARCHAR(42) CONSTRAINT nn_Ship_callsign    NOT NULL,
    idVehicle        INT      REFERENCES Vehicle(idVehicle),
    idEnergy         NUMBER   REFERENCEs Energy(idEnergy),
    CONSTRAINT ck_reg_Ship_mmsi     CHECK(REGEXP_LIKE(mmsi, '[0-9]{9}') ),
    CONSTRAINT ck_reg_Ship_imo      CHECK(REGEXP_LIKE(imo, '[IMO][0-9]{7}') ),
    CONSTRAINT ck_Ship_minTypeShip  CHECK(typeShip>=0),
    CONSTRAINT ck_Ship_minCapacity  Check(capacity>0),
    CONSTRAINT uk_imo   UNIQUE(imo)
);

CREATE TABLE PositionShip(
    idPositionShip NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    latitude       NUMBER(8,5)  CONSTRAINT nn_PositionShip__latitude            NOT NULL,
    longitude      NUMBER(8,5)  CONSTRAINT nn_PositionShip__longitude           NOT NULL,
    cog            INTEGER      CONSTRAINT nn_PositionShip_cog                  NOT NULL,
    sog            NUMBER(6,3)  CONSTRAINT nn_PositionShip_sog                  NOT NULL,
    heading        NUMBER(6,3)  CONSTRAINT nn_PositionShip_headingPositionShip  NOT NULL,
    baseDateTime   Date         CONSTRAINT nn_PositionShip_baseDateTime         NOT NULL,
    cargo          VARCHAR(42)  CONSTRAINT nn_PositionShip_cargo                NOT NULL,
    mmsi           NUMBER(9,0)  REFERENCES   Ship(mmsi),
    CONSTRAINT ck_PositionShip_maxLatitude  CHECK(latitude<=90 OR latitude=91),
    CONSTRAINT ck_PositionShip_minLatitude  CHECK(latitude>=-90),
    CONSTRAINT ck_PositionShip_Longitude    CHECK(longitude<=181 AND longitude>=-180),
    CONSTRAINT ck_PositionShip_Cog          CHECK(cog<=359 AND cog>=0),
    CONSTRAINT ck_PositionShip_maxheading   CHECK(heading<=359 OR heading=511),
    CONSTRAINT ck_PositionShip_minheading   CHECK(heading>=0),
    CONSTRAINT pk_PositionShip_idPositionShip       PRIMARY KEY(idPositionShip)
);

CREATE TABLE Transceiver(
    idTransceiver  NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    code           VARCHAR(1) CONSTRAINT nn_Transceiver_code          NOT NULL,
    idPositionShip NUMBER REFERENCES PositionShip(idPositionShip),
    CONSTRAINT     ck_reg_Transceiver_code      CHECK(REGEXP_LIKE(code,'[A|B]')),
    CONSTRAINT     pk_Transceiver_idTransceiver PRIMARY KEY(idTransceiver)
);

CREATE TABLE FacilityManager(
    type         VARCHAR(42) CONSTRAINT pk_FacilityManager_type         PRIMARY KEY,
    idWorker     NUMBER      CONSTRAINT nn_FacilityManager_idWorker     NOT NULL,
    codeFacility NUMBER      CONSTRAINT nn_FacilityManager_codeFacility NOT NULL
);

CREATE TABLE FacilityStaff(
    type         VARCHAR(42) CONSTRAINT pk_FacilityStaff_type         PRIMARY KEY,
    idWorker     NUMBER      CONSTRAINT nn_FacilityStaff_idWorker     NOT NULL,
    codeFacility NUMBER      CONSTRAINT nn_FacilityStaff_codeFacility NOT NULL
);

-- chaves estrangeiras
ALTER TABLE CapacityContainer ADD CONSTRAINT  fk_CapacityContainer_numberContainer   FOREIGN KEY (numberContainer)     REFERENCES Container(numberContainer);
ALTER TABLE TruckDriver       ADD CONSTRAINT  fk_TruckDriver_idWorker_TruckDriver    FOREIGN KEY (idWorker)            REFERENCES Worker(idWorker);
ALTER TABLE ShipCaptain       ADD CONSTRAINT  fk_ShipCaptain_idWorkerShipCaptain     FOREIGN KEY (idWorker)            REFERENCES Worker(idWorker);
ALTER TABLE TraficManager     ADD CONSTRAINT  fk_TraficManager_idWorker              FOREIGN KEY (idWorker)            REFERENCES Worker(idWorker);
ALTER TABLE ContainerManifest ADD CONSTRAINT  fk_ContainerManifest_numberContainer   FOREIGN KEY (nrContainer)         REFERENCES Container(numberContainer);
ALTER TABLE TripFacility      ADD CONSTRAINT  fk_TripFacility_idTrip                 FOREIGN KEY (idTrip)              REFERENCES Trip(idTrip);
ALTER TABLE TripFacility      ADD CONSTRAINT  fk_TripFacility_codeFacility           FOREIGN KEY (codeFacility)        REFERENCES Facility(codeFacility);
ALTER TABLE FacilityManager   ADD CONSTRAINT  fk_FacilityManager_idWorker            FOREIGN KEY (idWorker)            REFERENCES Worker(idWorker);
ALTER TABLE FacilityStaff     ADD CONSTRAINT  fk_FacilityStaff_idWorker              FOREIGN KEY (idWorker)            REFERENCES Worker(idWorker);
ALTER TABLE Manifest          ADD CONSTRAINT  fk_Manifest_idTrip                     FOREIGN KEY (idTrip,codeFacility) REFERENCES TripFacility(idTrip,codeFacility);



--preencher tabelas

--UserApplication
INSERT INTO UserApplication(email, password) VALUES('JoaoAlmeida@gmail.com','FleetManagerJoao');
INSERT INTO UserApplication(email, password) VALUES('BrunoRibeiro@gmail.com','ChipCaptainBruno');
INSERT INTO UserApplication(email, password) VALUES('OscarManuel@gmail.com','ChiefEletricalOscar');
INSERT INTO UserApplication(email, password) VALUES('jorge@shipcaptain.com','12345');
INSERT INTO UserApplication(email, password) VALUES('sergioconceicao@fcporto.com','12345');
INSERT INTO UserApplication(email, password) VALUES('Fernado@gmail.com','96457');
INSERT INTO UserApplication(email, password) VALUES('Rogerio@gmail.com','1465');
INSERT INTO UserApplication(email, password) VALUES('Alberto@gmail.com','416489');

--Client
INSERT INTO Client (nameClient, nrIdentificationClient, idUser) VALUES ('Sérgio Conceição',1,5);

--Address
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES(1090,459,'Vilarinha','Aldoar');
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES(4100,92,'Igreja de Ramalde','Ramalde');
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES(4300,5,'Campo 24 de Agosto','Bonfim');
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES (1,47,'Saint Tomé Street','Paranhos');

--Worker
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(1, 1090, 'Joao',459);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(2, 4100, 'Bruno',92);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(3, 4300, 'Oscar',5);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(4, 1,'Jorge',345);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(5, 1090,'Bernado',5497);

--TruckDriver
INSERT INTO TruckDriver(idWorker,drivingLicense) VALUES(5,'C1E');

--FleetManager
INSERT INTO FleetManager(idWorker) VALUES (1);

--ShipCaptain
INSERT INTO ShipCaptain(idWorker, qualification) VALUES(2,'Master');
INSERT INTO ShipCaptain(idWorker, qualification) VALUES(4,'Master');

--ChiefEletrical
INSERT INTO ChiefEletrical(idWorker, qualification) VALUES(3,'QNQ 4');

--vehicle
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);

--Truck
INSERT INTO Truck(registration,idWorker,idVehicle) VALUES('103JS',5,6);

--Continent
INSERT INTO Continent(continent) VALUES ('Europe');

--Country
INSERT INTO Country(country, continent) VALUES('Portugal','Europe');
INSERT INTO Country(country, continent) VALUES('França','Europe');
INSERT INTO Country(country, continent) VALUES('Espanha','Europe');


--Falility
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility) VALUES(1,'Portonave',90,180,'França','warehouse');
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility) VALUES(2,'Porto de Leixões',80,90,'Portugal','port');
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility) VALUES(3,'Porto de Aveiro',15,70,'Espanha','port');
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility) VALUES (4,'Berlengas Port', 12, 12, 'Portugal','port');
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility) VALUES (5,'Fisgao Port', 25, 41, 'Portugal','port');
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility) VALUES (6,'Alvalade Port', 75, 22, 'Portugal','port');

--Trip
INSERT INTO Trip(idVehicle, startFacility, endFacility, startDateTrip, endDateTrip,idShipCaptain,idFleet,idChiefEletrical) VALUES(1,1,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2003/05/06 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),2,1,1);
INSERT INTO Trip(idVehicle, startFacility, endFacility, startDateTrip, endDateTrip,idShipCaptain,idFleet,idChiefEletrical) VALUES(1,1,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2003/05/06 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),2,1,1);
INSERT INTO Trip(idVehicle, startFacility, endFacility, startDateTrip, endDateTrip,idShipCaptain,idFleet,idChiefEletrical) VALUES(1,1,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2003/05/06 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),2,1,1);
INSERT INTO Trip(idVehicle, startFacility, endFacility, startDateTrip, endDateTrip,idShipCaptain,idFleet,idChiefEletrical) VALUES(2,2,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2021/12/06 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),2,1,1);

--Energy
INSERT INTO Energy(nrGenerators,power) VALUES(2,1200);

--Ship
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES(123456789,1,4,'Vasco Da Gama','IMO1234567',4,400,30,18,'LAT07');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES(987654321,2,4,'Magalhães','IMO7654321',2,200,20,15,'R1U2F');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES(987654322,3,4,'Oceano','IMO7654322',2,350,25,19,'LE4576');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES (123456788,4,1,'VARAMO','IMO1234568',9,100,1,1,'E4576');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign, idEnergy) VALUES(123456780,5,77,'DORNAS COSTA','IMO1234577',77,100,25,25,'77DC',1);

--PositionShip
INSERT INTO PositionShip(mmsi,cog, sog, latitude, heading, longitude, baseDateTime, cargo) VALUES(123456789, 12.3, 13.3, 90, 359, 180,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'),'NA');
INSERT INTO PositionShip(mmsi,cog, sog, latitude, heading, longitude, baseDateTime, cargo) VALUES(123456789, 12.3, 13.3, 75, 359, 170,TO_DATE('2003/05/05 9:45:30', 'yyyy/mm/dd hh24:mi:ss'),'NA');
INSERT INTO PositionShip(mmsi,cog, sog, latitude, heading, longitude, baseDateTime, cargo) VALUES(123456789, 12.3, 13.3, 81, 359, 165,TO_DATE('2003/05/06 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),'78');
INSERT INTO PositionShip(mmsi,cog, sog, latitude, heading, longitude, baseDateTime, cargo) VALUES(987654321, 12.3, 13.3, 80, 359, 175,TO_DATE('2003/05/06 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),'78');

--Position
INSERT INTO Position (X,Y,z) VALUES (12,12,12);
INSERT INTO Position (X,Y,z) VALUES (1,1,1);
INSERT INTO Position (x,y,z) VALUES (4,4,4);
INSERT INTO Position (x,y,z) VALUES (3,3,1);
INSERT INTO Position (x,y,z) VALUES (8,5,8);
INSERT INTO Position (x,y,z) VALUES (7,5,1);
INSERT INTO Position (x,y,z) VALUES (6,5,6);

--Dimension
INSERT INTO Dimension (length, height) VALUES (12,12);

--typeContainer
INSERT INTO typeContainer (type,temperature) VALUES ('Refrigerated',7);
INSERT INTO typeContainer (type,temperature) VALUES ('MAXMINUS5',-5);

--Container
INSERT INTO Container (iso,certificate,numberContainer,load) VALUES ('1','ABC', 'ABCU1827364', 100);
INSERT INTO Container (iso,certificate,numberContainer,load) VALUES ('1','ABC', 'ABDU1827364', 100);
INSERT INTO Container (iso,certificate,numberContainer,load,idDimension,idClient,type) VALUES ('20BB','CER212','JORU1234568','Strawberrys',1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,idDimension,idClient,type) VALUES ('20CC','CER212','JORU1234569','Melons',1,1,'Refrigerated');
INSERT INTO Container (iso,certificate,numberContainer,load,idDimension,idClient,type) VALUES ('20DD','CER212','JORU1234564','Lemon',1,1,'Refrigerated');
INSERT INTO Container (iso,certificate,numberContainer,load,idDimension,idClient,type) VALUES ('20EE','CER212','JORU1234563','Kiwi',1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,idDimension,idClient,type) VALUES ('20FF','CER212','JORU1234553','Cherries',1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,idDimension,idClient,type) VALUES ('20GG','CER212','JORU1234513','Figs',1,1,'Refrigerated');

--CapacityConatiner

INSERT INTO CapacityContainer(numberContainer,maxWeight,weightEmpty,maxWeightPacked,maxVolume) VALUES('ABCU1827364',2000,500,1500,3000);
INSERT INTO CapacityContainer(numberContainer,maxWeight,weightEmpty,maxWeightPacked,maxVolume) VALUES('JORU1234513',4000,800,3200,5000);

--TripFacility
INSERT INTO TripFacility(idTrip,codeFacility,arrivalDate,departureDate) VALUES(4,3,TO_DATE('2021/12/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021-12-30 23:45:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,codeFacility,arrivalDate,departureDate) VALUES(4,2,TO_DATE('2021/12/03 20:00:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021-12-15 9:45:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,codeFacility,arrivalDate,departureDate) VALUES(4,1,TO_DATE('2021/12/14 10:00:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021-12-20 10:55:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,codeFacility,arrivalDate,departureDate) VALUES(4,4,TO_DATE('2021/12/02 09:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/02 09:45:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,codeFacility,arrivalDate,departureDate) VALUES(4,5,TO_DATE('2021/12/19 18:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/19 22:45:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,codeFacility,arrivalDate,departureDate) VALUES(4,6,TO_DATE('2021/12/21 07:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/21 22:45:00', 'yyyy/mm/dd hh24:mi:ss'));

--Manifest
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codeFacility) VALUES (50,'load',TO_DATE('2021/12/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),4,3);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codeFacility) VALUES (40,'cargo',TO_DATE('2021/12/03 20:00:44', 'yyyy/mm/dd hh24:mi:ss'),4,2);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codeFacility) VALUES (50,'offload',TO_DATE('2021/12/14 10:00:00', 'yyyy/mm/dd hh24:mi:ss'),4,1);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility) VALUES (50,'load',TO_DATE('2021/12/02 09:35:00', 'yyyy/mm/dd hh24:mi:ss'), 4,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility) VALUES (50,'load',TO_DATE('2021/12/19 18:35:00', 'yyyy/mm/dd hh24:mi:ss'), 4,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility) VALUES (50,'offload',TO_DATE('2021/12/21 17:30:00', 'yyyy/mm/dd hh24:mi:ss'),4,6);

--ContainerManifest
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'ABCU1827364',2);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'ABDU1827364',3);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (1,'JORU1234568',1);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (1,'JORU1234569',2);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'JORU1234563',4);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'JORU1234513',6);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'JORU1234553',5);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'JORU1234564',3);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234568',1);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234569',2);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234563',4);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234513',6);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234553',5);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234564',3);