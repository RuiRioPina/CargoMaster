CREATE TABLE Client(
    idClient INTEGER     CONSTRAINT pk_Client_idClient PRIMARY KEY,
    name     VARCHAR(42) CONSTRAINT nn_Client_name     NOT NULL
);

CREATE TABLE Amount(
    idAmount        INTEGER         CONSTRAINT pk_Amount_idAmount        PRIMARY KEY,
    maxWeight       NUMBER(5,3)     CONSTRAINT nn_Amount_maxWeight       NOT NULL,
    weightEmpty     NUMBER(4,3)     CONSTRAINT nn_Amount_weightEmpty     NOT NULL,
    maxWeightPacked NUMBER(5,3)     CONSTRAINT nn_Amount_maxWeightPacked NOT NULL,
    maxVolume       NUMBER(5,3)     CONSTRAINT nn_Amount_maxVolume       NOT NULL,
    CONSTRAINT ck_Amount_maxWeightPacked   CHECK(maxWeightPacked<=maxWeight)
);

CREATE TABLE Dimension(
    idDimension  INTEGER        CONSTRAINT   pk_Dimension_idDimensions  PRIMARY KEY,
    lenght       NUMBER(5,3)    CONSTRAINT   nn_Dimension_lenght        NOT NULL,
    height       NUMBER(5,3)    CONSTRAINT   nn_Dimension_height        NOT NULL
);

CREATE TABLE Container(
    iso          VARCHAR(4)  CONSTRAINT  pk_Container_iso         PRIMARY KEY,
    certificate  VARCHAR(6)  CONSTRAINT  nn_Container_certificate NOT NULL,
    idAmount     INTEGER    REFERENCES Amount(idAmount),
    idClient     INTEGER    REFERENCES CLIENT(idClient),
    idDimension  INTEGER    REFERENCES Dimension(idDimension)
);

CREATE TABLE NumberContainer(
    ownerPrefix         VARCHAR(3) CONSTRAINT  nn_NumberContainer_ownerPrefix           NOT NULL,
    equipmentIdentifier VARCHAR(1) CONSTRAINT  nn_NumberContainer_equipmentIdentifier   NOT NULL,
    serialNumber        VARCHAR(6) CONSTRAINT  nn_NumberContainer_serialNumber          NOT NULL,
    checkDigit          VARCHAR(1) CONSTRAINT  nn_NumberContainer_checkDigit            NOT NULL,
    iso                 VARCHAR(4) CONSTRAINT  nn_NumberContainer_iso                   NOT NULL,
    CONSTRAINT  uk_NumberContainer_ownerPrefix              UNIQUE(ownerPrefix),
    CONSTRAINT  uk_NumberContainer_serialNumber             UNIQUE(serialNumber),
    CONSTRAINT  uk_NumberContainer_chekDigit                UNIQUE(checkDigit),
    CONSTRAINT  ck_reg_NumberContainer_ownerPrefix          CHECK(REGEXP_LIKE(ownerPrefix, '[A-Z]{3}') ),
    CONSTRAINT  ck_reg_NumberContainer_equipmentIdentifier  CHECK(REGEXP_LIKE(equipmentIdentifier, '[U|J|Z]') )
);

CREATE TABLE Local(
    idLocal INTEGER CONSTRAINT  pk_Local_idLocal PRIMARY KEY
);

CREATE TABLE Energy(
    idEnergy     INTEGER      CONSTRAINT  pk_Energy_idEnergy       PRIMARY kEY,
    nrGenerators INTEGER      CONSTRAINT  nn_Energy_nrGerators     NOT NULL,
    power        NUMBER(6,3)  CONSTRAINT  nn_Energy_power          NOT NULL
);

CREATE TABLE Measure(
    idMeasure   INTEGER         CONSTRAINT  pk_Measure_idMeasure     PRIMARY KEY,
    lenght      NUMBER(5,3)     CONSTRAINT  nn_Measure_lenghtMeasure NOT NULL,
    Weight      NUMBER(5,3)     CONSTRAINT  nn_Measure_weight        NOT NULL,
    capacity    NUMBER(5,3)     CONSTRAINT  nn_Measure_capacity      NOT NULL
);

CREATE TABLE Worker(
    idWorker         INTEGER        CONSTRAINT  pk_Worker_idWorker         PRIMARY KEY,
    name             VARCHAR(42)    CONSTRAINT  nn_Worker_nameWorker       NOT NULL,
    walled           VARCHAR(200)   CONSTRAINT  nn_Worker_walled           NOT NULL,
    phoneNumber      NUMBER(9,0)    CONSTRAINT  nn_Worker_phoneNumber      NOT NULL,
    nrIdentification INTEGER        CONSTRAINT  nn_Worker_nrIdentification NOT NULL,
    CONSTRAINT uk_nrIdentification   UNIQUE(nrIdentification)
);

CREATE TABLE TraficManager(
    idWorker INTEGER CONSTRAINT  pk_TraficManager_idWorkerManager  PRIMARY KEY
);

CREATE TABLE Position(
    idPosition INTEGER   CONSTRAINT  pk_Position_idPosition  PRIMARY KEY,
    X          INTEGER   CONSTRAINT  nn_Position_x           NOT NULL,
    y          INTEGER   CONSTRAINT  nn_Position_y           NOT NULL,
    z          INTEGER   CONSTRAINT  nn_Position_z           NOT NULL,
    idWorker   INTEGER   REFERENCES  TraficManager(idWorker)
);

CREATE TABLE UnloadManifest(
    idUnderloadManifest INTEGER     CONSTRAINT  pk_UnloadManifest_idUnderloadManifest PRIMARY KEY,
    grossWeight         NUMBER(5,3) CONSTRAINT  nn_UnloadManifest_grossWeightUnload   NOT NULL,
    idLocal             INTEGER     REFERENCES  Local(idLocal),
    idPosition          INTEGER     REFERENCES  Position(idPosition)
);

CREATE TABLE Vehicle(
    idVehicle INTEGER   CONSTRAINT  pk_Vehicle_idVehicle PRIMARY KEY,
    idLocal   INTEGER   REFERENCES  Local(idLocal)
);

CREATE TABLE LoadManifest(
    idLoadManifest INTEGER      CONSTRAINT  pk_LoadManifest_idLoadManifest   PRIMARY KEY,
    grossWeight    NUMBER(5,3)  CONSTRAINT  nn_LoadManifest_grossWeightLoad  NOT NULL,
    iso            VARCHAR(4)   REFERENCES Container(iso),
    idPosition     INTEGER      REFERENCES Position(idPosition),
    idVehicle      INTEGER      REFERENCES Vehicle(idVehicle)
);

CREATE TABLE Port(
    country     VARCHAR(42) CONSTRAINT  nn_country          NOT NULL,
    continent   VARCHAR(42) CONSTRAINT  nn_Continent        NOT NULL,
    name        VARCHAR(42) CONSTRAINT  nn_namePorts        NOT NULL,
    latitude    NUMBER(5,3) CONSTRAINT  nn_latitudePorts    NOT NULL,
    longitude   NUMBER(6,3) CONSTRAINT  nn_longitudePorts   NOT NULL,
    idLocal     INTEGER     CONSTRAINT  pk_idLocalPorts     PRIMARY KEY,
    CONSTRAINT ck_Port_maxLatitude   CHECK(latitude<=90),
    CONSTRAINT ck_Port_minLatitude   CHECK(latitude>=-90),
    CONSTRAINT ck_Port_maxLongitude  CHECK(longitude<=-180),
    CONSTRAINT ck_Port_minLongitude  CHECK(longitude>=-180)
);

CREATE TABLE Warehouse(
    country     VARCHAR(42) CONSTRAINT nn_Warehouse_countryWarehouse      NOT NULL,
    continent   VARCHAR(42) CONSTRAINT nn_Warehouse_continentWarehouse    NOT NULL,
    name        VARCHAR(42) CONSTRAINT nn_Warehouse_nameWarehouse         NOT NULL,
    latitude    NUMBER(5,3) CONSTRAINT nn_Warehouse_latitudeWarehouse     NOT NULL,
    longitude   NUMBER(6,3) CONSTRAINT nn_Warehouse_longitudeWarehouse    NOT NULL,
    idLocal     INTEGER     CONSTRAINT pk_Warehouse_idLocalWarehouse      PRIMARY KEY,
    CONSTRAINT ck_Warehouse_maxLatitude   CHECK(latitude<=90),
    CONSTRAINT ck_Warehouse_minLatitude   CHECK(latitude>=-90),
    CONSTRAINT ck_Warehouse_maxLongitude  CHECK(longitude<=-180),
    CONSTRAINT ck_Warehouse_minLongitude  CHECK(longitude>=-180)
);

CREATE TABLE TruckDriver(
    drivingLicense  VARCHAR(42)         CONSTRAINT  nn_TruckDriver_drivingLicense       NOT NULL,
    idWorker        INTEGER             CONSTRAINT  pk_TruckDriver_idWorkerTruckDriver  PRIMARY KEY
);

CREATE TABLE Truck(
    registration VARCHAR(6) PRIMARY KEY,
    idVehicle    INTEGER    REFERENCES Vehicle(idVehicle),
    idWorker     INTEGER    REFERENCES TruckDriver(idWorker)
);

CREATE TABLE ChiefEletrical(
    idChiefEletrical INTEGER        CONSTRAINT  pk_ChiefEletrical_idChiefEletrical     PRIMARY KEY,
    qualification    VARCHAR(42)    CONSTRAINT  nn_ChiefEletrical_qualification        NOT NULL,
    idWorker         INTEGER        REFERENCES Worker(idWorker)
);

CREATE TABLE ShipCaptian(
    qualification VARCHAR(42)  CONSTRAINT  nn_ShipCaptian_qualificationShipCaptian   NOT NULL,
    idWorker      INTEGER      CONSTRAINT  pk_ShipCaptian_idWorkerShipCaptian        PRIMARY KEY
);

CREATE TABLE FleetManager(
    idFleet    INTEGER   CONSTRAINT  pk_FleetManager_idFleet PRIMARY KEY,
    idWorker   INTEGER   REFERENCES Worker(idWorker)
);

CREATE TABLE PortStaff(
    idWorker      INTEGER    CONSTRAINT  pk_PortStaff_idWorkerPortStaff        PRIMARY KEY,
    idLocal       REFERENCES Port(idLocal)
);

Create TABLE PortManager(
    idWorker      INTEGER      CONSTRAINT  pk_PortManager_idWorkerPortManager      PRIMARY KEY,
    idLocal       REFERENCES   Port(idLocal)
);

CREATE Table WarehouseStaff(
    idWorker      INTEGER    CONSTRAINT  pk_WarehouseStaff_idWarehouseStaff      PRIMARY KEY,
    idLocal       REFERENCES Warehouse(idLocal)
);

Create TABLE WarehouseManager(
    idWorker      INTEGER    CONSTRAINT  pk_WarehouseManager_idWarehouseManager      PRIMARY KEY,
    idLocal       REFERENCES Warehouse(idLocal)
);

CREATE Table Ship(
    mmsi    NUMBER(9,0) CONSTRAINT pk_Ship_mmsi        PRIMARY KEY,
    draft   NUMBER(5,3) CONSTRAINT nn_Ship_draft       NOT NULL,
    name    VARCHAR(42) CONSTRAINT nn_Ship_nameShip    NOT NULL,   
    imo     VARCHAR(10) CONSTRAINT nn_Ship_NumberShip  NOT NULL,
    CONSTRAINT uk_imo   UNIQUE(imo),
    idVehicle           REFERENCES Vehicle(idVehicle),
    idMeasure           REFERENCES Measure(idMeasure),
    idEnergy            REFERENCEs Energy(idEnergy),
    idChiefEletrical    REFERENCES ChiefEletrical(idChiefEletrical),
    idWorker            REFERENCES ShipCaptian(idWorker),
    idFleet             REFERENCES FleetManager(idFleet),
    CONSTRAINT ck_reg_Ship_mmsi      CHECK(REGEXP_LIKE(mmsi, '[0-9]{9}') ),
    CONSTRAINT ck_reg_Ship_imo       CHECK(REGEXP_LIKE(imo, '[IMO][0-9]{7}') )
);

CREATE TABLE PositionShip(
    mmsi        NUMBER(9,0)  CONSTRAINT pk_mmsiPositionShip        PRIMARY KEY,
    latitude    NUMBER(5,3)  CONSTRAINT nn_latitudePositionShip    NOT NULL,
    longitude   NUMBER(6,3)  CONSTRAINT nn_longitudePositionShip   NOT NULL,
    cog         INTEGER      CONSTRAINT nn_cog                     NOT NULL,
    sog         NUMBER(6,3)  CONSTRAINT nn_sog                     NOT NULL,
    heading     NUMBER(6,3)  CONSTRAINT nn_headingPositionShip     NOT NULL,
    position    NUMBER(9,0)  CONSTRAINT nn_positionPositionShip    NOT NULL,
    transceiver VARCHAR(42)  CONSTRAINT nn_transceiver             NOT NULL,
    CONSTRAINT ck_PositionShip_maxLatitude  CHECK(latitude<=90),
    CONSTRAINT ck_PositionShip_minLatitude  CHECK(latitude>=-90),
    CONSTRAINT ck_PositionShip_nullLatitude check(latitude=91),
    CONSTRAINT ck_PositionShip_maxLongitude CHECK(longitude<=-180),
    CONSTRAINT ck_PositionShip_minLongitude CHECK(longitude>=-180),
    CONSTRAINT ck_PositionShip_nulLongitude CHECK(longitude=181),
    CONSTRAINT ck_PositionShip_maxCog       CHECK(longitude<=359),
    CONSTRAINT ck_PositionShip_minCog       CHECK(longitude>=0),
    CONSTRAINT ck_PositionShip_maxheading   CHECK(longitude<=359),
    CONSTRAINT ck_PositionShip_minheading   CHECK(longitude>=0)
);

ALTER TABLE Container        ADD CONSTRAINT  fk_NumberContainer_iso                 FOREIGN KEY (iso)        REFERENCES Container(iso);
ALTER TABLE TraficManager    ADD CONSTRAINT  fk_TraficManager_idWorker              FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE Port             ADD CONSTRAINT  fk_Port_idLocal                        FOREIGN KEY (idLocal)    REFERENCES LOCAL(idLocal);
ALTER TABLE Warehouse        ADD CONSTRAINT  fk_Warehouse_idLocal                   FOREIGN KEY (idLocal)    REFERENCES LOCAL(idLocal);
ALTER TABLE TruckDriver      ADD CONSTRAINT  fk_TruckDriver_idWorkerTruckDriver     FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE ShipCaptian      ADD CONSTRAINT  fk_ShipCaptian_idWorkerShipCaptian     FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE PortStaff        ADD CONSTRAINT  fk_PortStaff_idWorkerPortStaff         FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE PortManager      ADD CONSTRAINT  fk_PortManager_idWorkerPortManager     FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE WarehouseStaff   ADD CONSTRAINT  fk_WarehouseStaff_idWarehouseStaff     FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE WarehouseManager ADD CONSTRAINT  fk_WarehouseManager_idWarehouseManager FOREIGN KEY (idWorker)   REFERENCES Worker(idWorker);
ALTER TABLE PositionShip     ADD CONSTRAINT  fk_PositionShip_mmsi                   FOREIGN KEY (mmsi)       REFERENCES Ship(mmsi);