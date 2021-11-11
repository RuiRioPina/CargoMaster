CREATE TABLE Client(
    idClient INTEGER     CONSTRAINT pk_idClient PRIMARY KEY,
    name     VARCHAR(42) CONSTRAINT nn_name     NOT NULL
);

CREATE TABLE Amount(
    idAmount        INTEGER         CONSTRAINT pk_idAmount        PRIMARY KEY,
    maxWeight       NUMBER(5,3)     CONSTRAINT nn_maxWeight       NOT NULL,
    weightEmpty     NUMBER(4,3)     CONSTRAINT nn_weightEmpty     NOT NULL,
    maxWeightPacked NUMBER(5,3)     CONSTRAINT nn_maxWeightPacked NOT NULL,
    maxVolume       NUMBER(5,3)     CONSTRAINT nn_maxVolume       NOT NULL,
    CONSTRAINT ck_maxWeightPacked   CHECK(maxWeightPacked<=maxWeight)
);

CREATE TABLE Dimensions(
    idDimensions INTEGER        CONSTRAINT   pk_idDimensions  PRIMARY KEY,
    lenght       NUMBER(5,3)    CONSTRAINT   nn_lenght        NOT NULL,
    height       NUMBER(5,3)    CONSTRAINT   nn_height        NOT NULL
);

CREATE TABLE Container(
    iso          VARCHAR(4)  CONSTRAINT  pk_iso PRIMARY KEY,
    certificate  VARCHAR(6)  CONSTRAINT  nn_certificate NOT NULL,
    idAmount     INTEGER    REFERENCES Amount(idAmount),
    idClient     INTEGER    REFERENCES CLIENT(idClient),
    idDimensions INTEGER    REFERENCES Dimensions(idDimensions)
);

CREATE TABLE NumberContainer(
    ownerPrefix         VARCHAR(3) CONSTRAINT  nn_ownerPrefix NOT NULL,
    equipmentIdentifier VARCHAR(1) CONSTRAINT  nn_equipmentIdentifier NOT NULL,
    serialNumber        VARCHAR(6) CONSTRAINT  nn_serialNumber NOT NULL,
    checkDigit          VARCHAR(1) CONSTRAINT  nn_checkDigit NOT NULL,
    iso                 VARCHAR(4) CONSTRAINT  nn_iso NOT NULL,
    CONSTRAINT  fk_iso                    FOREIGN KEY (iso)    REFERENCES Container(iso),
    CONSTRAINT k_ownerPrefix              UNIQUE(ownerPrefix),
    CONSTRAINT k_serialNumber             UNIQUE(serialNumber),
    CONSTRAINT k_chekDigit                UNIQUE(checkDigit),
    CONSTRAINT ck_reg_ownerPrefix         CHECK(REGEXP_LIKE(ownerPrefix '[A-Z]{3}') ),
    CONSTRAINT ck_reg_equipmentIdentifier CHECK(REGEXP_LIKE(equipmentIdentifier '[U|J|Z]') )
);

CREATE TABLE Local(
    idLocal INTEGER CONSTRAINT  pk_idLocal PRIMARY KEY
);

CREATE TABLE Energy(
    idEnergy     INTEGER      CONSTRAINT  pk_idEnergy     PRIMARY kEY,
    nrGenerators INTEGER      CONSTRAINT  nn_nrGerators     NOT NULL,
    power        NUMBER(6,3)  CONSTRAINT  nn_power          NOT NULL
);

CREATE TABLE Measure(
    idMeasure   INTEGER         CONSTRAINT  pk_idMeasure     PRIMARY KEY,
    lenght      NUMBER(5,3)     CONSTRAINT  nn_lenghtMeasure NOT NULL,
    Weight      NUMBER(5,3)     CONSTRAINT  nn_weight        NOT NULL,
    capacity    NUMBER(5,3)     CONSTRAINT  nn_capacity      NOT NULL
);

CREATE TABLE Worker(
    idWorker         INTEGER        CONSTRAINT  pk_idWorker         PRIMARY KEY,
    name             VARCHAR(42)    CONSTRAINT  nn_nameWorker       NOT NULL,
    walled           VARCHAR(200)   CONSTRAINT  nn_walled           NOT NULL,
    phoneNumber      NUMBER(9,0)    CONSTRAINT  nn_phoneNumber      NOT NULL,
    nrIdentification INTEGER        CONSTRAINT  nn_nrIdentification NOT NULL,
    CONSTRAINT k_nrIdentification   UNIQUE(nrIdentification)
);

CREATE TABLE TraficManager(
    idWorker         INTEGER CONSTRAINT  pk_idWorkerManager         PRIMARY KEY,
    CONSTRAINT fk_idWorker FOREIGN KEY (idWorker) REFERENCES Worker(idWorker)
);

CREATE TABLE Position(
    idPosition INTEGER   CONSTRAINT  pk_idPosition  PRIMARY KEY,
    X          INTEGER   CONSTRAINT  nn_x           NOT NULL,
    y          INTEGER   CONSTRAINT  nn_y           NOT NULL,
    z          INTEGER   CONSTRAINT  nn_z           NOT NULL,
    idWorker   INTEGER   REFERENCES  TraficManager(idWorker)
);

CREATE TABLE UnloadManifest(
    idUnderloadManifest INTEGER     CONSTRAINT  pk_idUnderloadManifest PRIMARY KEY,
    grossWeight         NUMBER(5,3) CONSTRAINT  nn_grossWeightUnload   NOT NULL,
    idLocal             INTEGER     REFERENCES  Local(idLocal),
    idPosition          INTEGER     REFERENCES  Position(idPosition)
);

CREATE TABLE Vehicle(
    idVehicle INTEGER   CONSTRAINT  pk_idVehicle PRIMARY KEY,
    idLocal   INTEGER   REFERENCES Local(idLocal)
);

CREATE TABLE LoadManifest(
    idLoadManifest INTEGER      CONSTRAINT  pk_idLoadManifest   PRIMARY KEY,
    grossWeight    NUMBER(5,3)  CONSTRAINT  nn_grossWeightLoad  NOT NULL,
    iso            VARCHAR(4)   REFERENCES Container(iso),
    idPosition     INTEGER      REFERENCES Position(idPosition),
    idVehicle      INTEGER      REFERENCES Vehicle(idVehicle)
);

CREATE TABLE Ports(
    country     VARCHAR(42) CONSTRAINT  nn_country          NOT NULL,
    continent   VARCHAR(42) CONSTRAINT  nn_Continent        NOT NULL,
    name        VARCHAR(42) CONSTRAINT  nn_namePorts        NOT NULL,
    latitude    NUMBER(5,3) CONSTRAINT  nn_latitudePorts    NOT NULL,
    longitude   NUMBER(6,3) CONSTRAINT  nn_longitudePorts   NOT NULL,
    idLocal     INTEGER     CONSTRAINT  pk_idLocalPorts     PRIMARY KEY,
    CONSTRAINT fk_idLocalPorts  FOREIGN KEY (idLocal)       REFERENCES LOCAL(idLocal),
    CONSTRAINT ck_maxLatitudePorts   CHECK(latitude<=90),
    CONSTRAINT ck_minLatitudePorts   CHECK(latitude>=-90),
    CONSTRAINT ck_maxLongitudePorts  CHECK(longitude<=-180),
    CONSTRAINT ck_minLongitudePorts  CHECK(longitude>=-180)
);

CREATE TABLE Warehouse(
    country     VARCHAR(42) CONSTRAINT nn_countryWarehouse      NOT NULL,
    continent   VARCHAR(42) CONSTRAINT nn_continentWarehouse    NOT NULL,
    name        VARCHAR(42) CONSTRAINT nn_nameWarehouse         NOT NULL,
    latitude    NUMBER(5,3) CONSTRAINT nn_latitudeWarehouse     NOT NULL,
    longitude   NUMBER(6,3) CONSTRAINT nn_longitudeWarehouse    NOT NULL,
    idLocal     INTEGER         CONSTRAINT  pk_idLocalWarehouse   PRIMARY KEY,
    CONSTRAINT fk_idLocal       FOREIGN KEY (idLocal)             REFERENCES LOCAL(idLocal),
    CONSTRAINT ck_maxLatitude   CHECK(latitude<=90),
    CONSTRAINT ck_minLatitude   CHECK(latitude>=-90),
    CONSTRAINT ck_maxLongitude  CHECK(longitude<=-180),
    CONSTRAINT ck_minLongitude  CHECK(longitude>=-180)
);

CREATE TABLE TruckDriver(
    drivingLicense  VARCHAR(42)         CONSTRAINT  nn_drivingLicense       NOT NULL,
    idWorker        INTEGER             CONSTRAINT  pk_idWorkerTruckDriver  PRIMARY KEY,
    CONSTRAINT fk_idWorkerTruckDriver   FOREIGN KEY (idWorker)              REFERENCES Worker(idWorker)
);

CREATE TABLE Truck(
    registration VARCHAR(6) PRIMARY KEY,
    idVehicle    INTEGER    REFERENCES Vehicle(idVehicle),
    idWorker     INTEGER    REFERENCES TruckDriver(idWorker)
);

CREATE TABLE ChiefEletrical(
    idChiefEletrical INTEGER        CONSTRAINT  pk_idChiefEletrical     PRIMARY KEY,
    qualification    VARCHAR(42)    CONSTRAINT  nn_qualification        NOT NULL,
    idWorker        INTEGER         REFERENCES Worker(idWorker)
);

CREATE TABLE ShipCaptian(
    qualification VARCHAR(42)  CONSTRAINT  nn_qualificationShipCaptian   NOT NULL,
    idWorker      INTEGER      CONSTRAINT  pk_idWorkerShipCaptian        PRIMARY KEY,
    CONSTRAINT fk_idWorkerShipCaptian FOREIGN KEY (idWorker)             REFERENCES Worker(idWorker)
);

CREATE TABLE FleetManager(
    idFleet    INTEGER   CONSTRAINT  pk_idFleet PRIMARY KEY,
    idWorker   INTEGER   REFERENCES Worker(idWorker)
);

CREATE TABLE PortStaff(
    idWorker      INTEGER           CONSTRAINT  pk_idWorkerPortStaff        PRIMARY KEY,
    CONSTRAINT    fk_idWorkerPortStaff FOREIGN KEY (idWorker)               REFERENCES Worker(idWorker),
    idLocal       REFERENCES Ports(idLocal)
);

Create TABLE PortManager(
    idWorker      INTEGER           CONSTRAINT  pk_idWorkerPortManager      PRIMARY KEY,
    CONSTRAINT    fk_idWorkerPortManager FOREIGN KEY (idWorker)             REFERENCES Worker(idWorker),
    idLocal       REFERENCES Ports(idLocal)
);

CREATE Table WarehouseStaff(
    idWorker      INTEGER           CONSTRAINT  pk_idWarehouseStaff      PRIMARY KEY,
    CONSTRAINT    fk_idWarehouseStaff FOREIGN KEY (idWorker)             REFERENCES Worker(idWorker),
    idLocal       REFERENCES Warehouse(idLocal)
);

Create TABLE WarehouseManager(
    idWorker      INTEGER           CONSTRAINT  pk_idWarehouseManager      PRIMARY KEY,
    CONSTRAINT    fk_idWarehouseManager FOREIGN KEY (idWorker)             REFERENCES Worker(idWorker),
    idLocal       REFERENCES Warehouse(idLocal)
);

CREATE Table Ship(
    mmsi    CHAR(9)     CONSTRAINT pk_mmsi        PRIMARY KEY,
    draft   NUMBER(5,3) CONSTRAINT nn_draft       NOT NULL,
    name    VARCHAR(42) CONSTRAINT nn_nameShip    NOT NULL,   
    imo     NUMBER(7,0) CONSTRAINT nn_NumberShip  NOT NULL,
    CONSTRAINT k_imo    UNIQUE(imo),
    idVehicle           REFERENCES Vehicle(idVehicle),
    idMeasure           REFERENCES Measure(idMeasure),
    idEnergy            REFERENCEs Energy(idEnergy),
    idChiefEletrical    REFERENCES ChiefEletrical(idChiefEletrical),
    idWorker            REFERENCES ShipCaptian(idWorker),
    idFleet             REFERENCES FleetManager(idFleet)
);

CREATE TABLE PositionShip(
    mmsi        CHAR(9)      CONSTRAINT pk_mmsiPositionShip        PRIMARY KEY,
    latitude    NUMBER(5,3)  CONSTRAINT nn_latitudePositionShip    NOT NULL,
    longitude   NUMBER(6,3)  CONSTRAINT nn_longitudePositionShip   NOT NULL,
    cog         INTEGER      CONSTRAINT nn_cog                     NOT NULL,
    sog         NUMBER(6,3)  CONSTRAINT nn_sog                     NOT NULL,
    heading     NUMBER(6,3)  CONSTRAINT nn_headingPositionShip     NOT NULL,
    position    NUMBER(9,0)  CONSTRAINT nn_positionPositionShip    NOT NULL,
    transceiver VARCHAR(42)  CONSTRAINT nn_transceiver             NOT NULL,
    CONSTRAINT ck_maxLatitudePositionShip   CHECK(latitude<=90),
    CONSTRAINT ck_minLatitudePositionShip   CHECK(latitude>=-90),
    CONSTRAINT ck_maxLongitudePositionShip  CHECK(longitude<=-180),
    CONSTRAINT ck_minLongitudePositionShip  CHECK(longitude>=-180),
    CONSTRAINT ck_maxCogPositionShip        CHECK(longitude<=359),
    CONSTRAINT ck_minCogPositionShip        CHECK(longitude>=0),
    CONSTRAINT ck_maxSogPositionShip        CHECK(longitude<=359),
    CONSTRAINT ck_minSogPositionShip        CHECK(longitude>=0)
);

drop table MEASURE;
drop table DIMENSIONS;
DROP TABLE CLIENT;
DROP TABLE AMOUNT;
drop TABLE TraficManager;
drop TABLE ports;
drop table Ship;
drop table PositionShip;

drop table CD;
drop table MUSICA;