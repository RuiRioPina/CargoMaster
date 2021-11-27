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

CREATE Table WarehouseStaff(
    idWorker NUMBER    CONSTRAINT  pk_WarehouseStaff_idWarehouseStaff    PRIMARY KEY
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

CREATE TABLE Manifest(
    idManifest     NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    grossWeight    NUMBER(6,3)  CONSTRAINT nn_Manifest_grossWeight  NOT NULL,
    typeManifest   VARCHAR(42)  CONSTRAINT nn_Manifest_typeManifest NOT NULL,
    dateManifest   DATE         CONSTRAINT nn_Manifest_dateManifest NOT NULL,
    idPosition     NUMBER       REFERENCES Position(idPosition),
    CONSTRAINT     ck_reg_Manifest_typeManifest CHECK(REGEXP_LIKE(typeManifest, 'LOAD|UNLOAD|load|unload') ),
    CONSTRAINT     pk_Manifest_iDManifest   PRIMARY KEY(idManifest)
);


CREATE TABLE Container(
    iso             VARCHAR(4)   CONSTRAINT  pk_Container_iso             PRIMARY KEY,
    certificate     VARCHAR(6)   CONSTRAINT  nn_Container_certificate     NOT NULL,
    typeContainer   INTEGER      CONSTRAINT  nn_Container_typeContainer   NOT NULL,
    numberContainer VARCHAR(11)  CONSTRAINT  nn_Conatiner_numberContainer NOT NULL,
    load            VARCHAR(100) CONSTRAINT  NN_Container_load            NOT NULL,
    idManifest      NUMBER       REFERENCES Manifest(idManifest),
    idDimension     NUMBER       REFERENCES Dimension(idDimension),
    CONSTRAINT      ck_Container_typeConteiner       CHECK(typeContainer>=0),
    CONSTRAINT      ck_reg_Container_numberContainer CHECK(REGEXP_LIKE(numberContainer, '[A-Z]{3}[U|J|Z][1-9]{7}') )
);

CREATE TABLE CapacityContainer(
    iso             VARCHAR(4)       CONSTRAINT pk_CapacityContainer_iso             PRIMARY KEY,
    maxWeight       NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_maxWeight       NOT NULL,
    weightEmpty     NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_weightEmpty     NOT NULL,
    maxWeightPacked NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_maxWeightPacked NOT NULL,
    maxVolume       NUMBER(10,2)     CONSTRAINT nn_CapacityContainer_maxVolume       NOT NULL
);

CREATE TABLE Client(
    idClient               NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    nameClient             VARCHAR(42) CONSTRAINT nn_Client_name             NOT NULL,
    nrIdentificationClient INTEGER     CONSTRAINT nn_Client_nrIdentification NOT NULL,
    iso                    VARCHAR(4)  REFERENCES Container(iso),
    idUser                 NUMBER      REFERENCES UserApplication(idUser),
    CONSTRAINT uk_Client_nrIdentification      UNIQUE(nrIdentificationClient),
    CONSTRAINT pk_Client_idClient         PRIMARY KEY(idClient)
);

CREATE TABLE Facility(
    codeFacility    INTEGER     CONSTRAINT  pk_Facility_codeFacility  PRIMARY KEY,
    country         VARCHAR(42) CONSTRAINT  nn_Facility_country       NOT NULL,
    continent       VARCHAR(42) CONSTRAINT  nn_Facility_Continent     NOT NULL,
    nameFacility    VARCHAR(42) CONSTRAINT  nn_Facility_nameFacility  NOT NULL,
    latitude        NUMBER(5,3) CONSTRAINT  nn_Facility_latitude      NOT NULL,
    longitude       NUMBER(6,3) CONSTRAINT  nn_Facility_longitudePort NOT NULL,
    idManifest      NUMBER      CONSTRAINT  nn_Facility_idManifest    NOT NULL,
    idWorker        NUMBER      REFERENCES  WarehouseStaff(idWorker),
    CONSTRAINT ck_Facility_Latitude   CHECK(latitude<=90 AND latitude>=-90),
    CONSTRAINT ck_Facility_Longitude  CHECK(longitude<=180 AND longitude>=-180)
);

CREATE TABLE Vehicle(
    idVehicle INT CONSTRAINT pk_Vehicle_idVehicle PRIMARY KEY
);

CREATE SEQUENCE idVehicle MINVALUE 1 START WITH 1;

CREATE TABLE Truck(
    registration VARCHAR(6) PRIMARY KEY,
    idWorker     NUMBER     REFERENCES TruckDriver(idWorker),
    idVehicle    INT        REFERENCES Vehicle(idVehicle)
);

CREATE TABLE Trip(
    idTrip        NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    dateTrip      DATE       CONSTRAINT nn_Trip_dateTrip      NOT NULL,
    endDateTrip   DATE       CONSTRAINT nn_Trip_endDateTrip   NOT NULL,
    startdateTrip DATE       CONSTRAINT nn_Trip_startdateTrip NOT NULL,
    startFacility INTEGER REFERENCES Facility(codeFacility),
    endFacility   INTEGER REFERENCES Facility(codeFacility),
    idVehicle     INTEGER REFERENCES Vehicle(idVehicle),
    codeFacility  INTEGER REFERENCES Facility(codeFacility),
    CONSTRAINT    pk_Trip_idTrip       PRIMARY KEY(idTrip)
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
    idVehicle        INT      REFERENCES Vehicle(idVehicle),
    idEnergy         NUMBER   REFERENCEs Energy(idEnergy),
    idChiefEletrical NUMBER   REFERENCES ChiefEletrical(idChiefEletrical),
    idShipCaptain    NUMBER   REFERENCES ShipCaptain(idWorker),
    idFleet          NUMBER   REFERENCES FleetManager(idFleet),
    CONSTRAINT ck_reg_Ship_mmsi     CHECK(REGEXP_LIKE(mmsi, '[0-9]{9}') ),
    CONSTRAINT ck_reg_Ship_imo      CHECK(REGEXP_LIKE(imo, '[IMO][0-9]{7}') ),
    CONSTRAINT ck_Ship_minTypeShip  CHECK(typeShip>=0),
    CONSTRAINT ck_Ship_minCapacity  Check(capacity>0),
    CONSTRAINT uk_imo   UNIQUE(imo)
);

CREATE TABLE CallShip(
    hourCallShip VARCHAR(8),
    dateCallShip DATE,
    mmsi         NUMBER(9,0) CONSTRAINT nn_Call_mmsi     NOT NULL,
    CONSTRAINT pk_CALL_hour_dateCallShip PRIMARY KEY (hourCallShip, dateCallShip,mmsi),
    CONSTRAINT ck_reg_Call_hourCallShip  CHECK(REGEXP_LIKE(hourCallShip, '[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}') )
);

CREATE TABLE PositionShip(
    idPositionShip NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    latitude       NUMBER(5,3)  CONSTRAINT nn_PositionShip__latitude            NOT NULL,
    longitude      NUMBER(6,3)  CONSTRAINT nn_PositionShip__longitude           NOT NULL,
    cog            INTEGER      CONSTRAINT nn_PositionShip_cog                  NOT NULL,
    sog            NUMBER(6,3)  CONSTRAINT nn_PositionShip_sog                  NOT NULL,
    heading        NUMBER(6,3)  CONSTRAINT nn_PositionShip_headingPositionShip  NOT NULL,
    baseDateTime   Date         CONSTRAINT nn_PositionShip_baseDateTime         NOT NULL,
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

Create TABLE WarehouseManager(
    idWorker NUMBER     CONSTRAINT  pk_WarehouseManager_idWarehouseManager  PRIMARY KEY,
    code     NUMBER     REFERENCES Facility(codeFacility)
);

Create TABLE PortManager(
    idWorker NUMBER     CONSTRAINT  pk_PortManager_idWorkerPortManager  PRIMARY KEY,
    code     NUMBER     REFERENCES Facility(codeFacility)
);

CREATE TABLE PortStaff(
    idWorker NUMBER     CONSTRAINT  pk_PortStaff_idWorkerPortStaff  PRIMARY KEY,
    code     NUMBER     REFERENCES Facility(codeFacility)
);

ALTER TABLE CapacityContainer ADD CONSTRAINT  fk_CapacityContainer_iso               FOREIGN KEY (iso)               REFERENCES Container(iso);
ALTER TABLE TruckDriver       ADD CONSTRAINT  fk_TruckDriver_idWorker_TruckDriver    FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE CallShip          ADD CONSTRAINT  fk_Call_mmsi                           FOREIGN KEY (mmsi)              REFERENCES Ship(mmsi);
ALTER TABLE ShipCaptain       ADD CONSTRAINT  fk_ShipCaptain_idWorkerShipCaptain     FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE WarehouseManager  ADD CONSTRAINT  fk_WarehouseManager_idWorker           FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE PortManager       ADD CONSTRAINT  fk_PortManager_idWorkerPortManager     FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE WarehouseStaff    ADD CONSTRAINT  fk_WarehouseStaff_idWarehouseStaff     FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE PortStaff         ADD CONSTRAINT  fk_PortStaff_idWorkerPortStaff         FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE TraficManager     ADD CONSTRAINT  fk_TraficManager_idWorker              FOREIGN KEY (idWorker)          REFERENCES Worker(idWorker);
ALTER TABLE Facility          ADD CONSTRAINT  fk_Facility_idManifest                 FOREIGN KEY (idManifest)        REFERENCES Manifest(idManifest);