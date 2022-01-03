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
DROP TABLE TripVehicle       CASCADE CONSTRAINTS PURGE;
DROP TABLE PortDistance      CASCADE CONSTRAINTS PURGE;
DROP TABLE Border            CASCADE CONSTRAINTS PURGE;
DROP TABLE AuditTrail        CASCADE CONSTRAINTS PURGE;
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
    nrIdentification INTEGER        CONSTRAINT  pk_Worker_nrIdentification        PRIMARY KEY,
    nameWorker       VARCHAR(42)    CONSTRAINT  nn_Worker_nameWorker              NOT NULL,
    codAddress       INTEGER        REFERENCES  Address(codAddress),
    idUser           INTEGER        REFERENCES  UserApplication(idUser)
);

CREATE TABLE FleetManager(
    nrIdentification INTEGER    CONSTRAINT  pk_FleetManager_nrIdentification    PRIMARY KEY
);

CREATE TABLE ShipCaptain(
    nrIdentification INTEGER     CONSTRAINT  pk_ShipCaptain_nrIdentification PRIMARY KEY,
    qualification    VARCHAR(42) CONSTRAINT  nn_ShipCaptain_qualification    NOT NULL
);

CREATE TABLE ChiefEletrical(
    nrIdentification INTEGER     CONSTRAINT  pk_ChiefEletrical_nrIdentification PRIMARY KEY,
    qualification    VARCHAR(42) CONSTRAINT  nn_ChiefEletrical_qualification    NOT NULL
);

CREATE TABLE TraficManager(
    nrIdentification INTEGER    CONSTRAINT  pk_TraficManager_nrIdentification    PRIMARY KEY
);

CREATE TABLE TruckDriver(
    nrIdentification INTEGER     CONSTRAINT  pk_TruckDriver_nrIdentification    PRIMARY KEY,
    drivingLicense   VARCHAR(42) CONSTRAINT  nn_TruckDriver_drivingLicense      NOT NULL
);

CREATE TABLE Position(
    idPosition         NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    X                  INTEGER   CONSTRAINT  nn_Position_x           NOT NULL,
    y                  INTEGER   CONSTRAINT  nn_Position_y           NOT NULL,
    z                  INTEGER   CONSTRAINT  nn_Position_z           NOT NULL,
    nrIdentification   INTEGER   REFERENCES  TraficManager(nrIdentification),
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
    numberContainer VARCHAR(11)  CONSTRAINT  pk_Conatiner_numberContainer PRIMARY KEY,
    iso             VARCHAR(4)   CONSTRAINT  nn_Container_iso             NOT NULL,
    certificate     VARCHAR(6)   CONSTRAINT  nn_Container_certificate     NOT NULL,
    load            VARCHAR(100) CONSTRAINT  nn_Container_load            NOT NULL,
    maxWeight       NUMBER(10,2) CONSTRAINT  nn_Container_maxWeight       NOT NULL,
    weightEmpty     NUMBER(10,2) CONSTRAINT  nn_Container_weightEmpty     NOT NULL,
    maxWeightPacked NUMBER(10,2) CONSTRAINT  nn_Container_maxWeightPacked NOT NULL,
    maxVolume       NUMBER(10,2) CONSTRAINT  nn_Container_maxVolume       NOT NULL,
    idDimension     NUMBER       REFERENCES Dimension(idDimension),
    idClient        NUMBER       REFERENCES Client(idClient),
    type            VARCHAR(42)  REFERENCES TypeContainer(type),
    CONSTRAINT      ck_reg_Container_numberContainer CHECK(REGEXP_LIKE(numberContainer, '[A-Z]{3}[U|J|Z][0-9]{7}') )
);

CREATE TABLE Continent(
    continent VARCHAR(42) CONSTRAINT  pk_Continent_Continent  PRIMARY KEY
);

CREATE TABLE Country(
    country  VARCHAR(42) CONSTRAINT  pk_Country_country PRIMARY KEY,
    continent VARCHAR(42) REFERENCES Continent(continent),
    alpha2_Code     VARCHAR(10) CONSTRAINT  nn_Country_alpha2_Code NOT NULL,
    alpha3_Code     VARCHAR(10) CONSTRAINT  nn_Country_alpha3_Code NOT NULL,
    population      INTEGER     CONSTRAINT  nn_Country_population  NOT NULL,
    capitalName     VARCHAR(42) CONSTRAINT  nn_Country_capitalName NOT NULL,
    latitudeCapital NUMBER(8,5) CONSTRAINT nn_Country__latitude   NOT NULL,
    longitudeCapital NUMBER(8,5) CONSTRAINT nn_Country_longitude  NOT NULL
);

CREATE TABLE Border(
    country  VARCHAR(42),
    country1 VARCHAR(42) REFERENCES Country(country),
    CONSTRAINT pk_Border_country_country1 PRIMARY KEY(country, country1)
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
                         dockingSlots       INTEGER,
                         capacity           INTEGER     CONSTRAINT  nn_Facility_capacity           NOT NULL,
                         country            VARCHAR(42) REFERENCES  Country(country),
                         CONSTRAINT ck_Facility_Latitude        CHECK(latitude<=90 AND latitude>=-90),
                         CONSTRAINT ck_Facility_Longitude       CHECK(longitude<=180 AND longitude>=-180),
                         CONSTRAINT ck_reg_FacilitytypeFacility CHECK(REGEXP_LIKE(typeFacility, 'port|warehouse|PORT|WAREHOUSE') )
);

CREATE TABLE PortDistance(
    facility     INTEGER,
    codeFacility INTEGER REFERENCES Facility(codeFacility),
    distance     NUMBER CONSTRAINT  nn_PortDistance_distance NOT NULL,
    CONSTRAINT   pk_PortDistance_facility_codeFacility PRIMARY KEY(facility, codeFacility)
);

CREATE TABLE Truck(
    registration            VARCHAR(6) PRIMARY KEY,
    nrIdentification        INTEGER    REFERENCES TruckDriver(nrIdentification),
    idVehicle               INT        REFERENCES Vehicle(idVehicle)
);

CREATE TABLE Trip(
    idTrip          NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    idCargoManifest NUMBER CONSTRAINT nn_Trip_idCargoManifest NOT NULL CONSTRAINT uk_Trip_idCargoManifest UNIQUE,
    endDateTrip     DATE   CONSTRAINT nn_Trip_endDateTrip     NOT NULL,
    startdateTrip   DATE   CONSTRAINT nn_Trip_startdateTrip   NOT NULL,
    startFacility   INTEGER REFERENCES Facility(codeFacility),
    endFacility     INTEGER REFERENCES Facility(codeFacility),
    ChiefEletricalNrIdentificatio INTEGER   REFERENCES ChiefEletrical(nrIdentification),
    ShipCaptainNrIdentification   INTEGER   REFERENCES ShipCaptain(nrIdentification),
    FleetManagerNrIdentification  INTEGER   REFERENCES FleetManager(nrIdentification),
    startVehicle  INT REFERENCES Vehicle(idVehicle),
    CONSTRAINT    pk_Trip_idTrip_idCargoManifest    PRIMARY KEY(idTrip, idCargoManifest)
);

CREATE TABLE TripVehicle(
    idTrip          NUMBER,
    idCargoManifest NUMBER,
    idVehicle       INT,
    CONSTRAINT    pk_TripVehicle_idTrip_idCargoManifest_idVehicle    PRIMARY KEY(idTrip, idCargoManifest, idVehicle)
);

CREATE TABLE TripFacility(
    idTrip           NUMBER,
    idCargoManifest  NUMBER,
    codeFacility     INTEGER,
    arrivalDate   DATE CONSTRAINT nn_TripFacility_arrivalDate   NOT NULL,
    departureDate DATE CONSTRAINT nn_TripFacility_departureDate NOT NULL,
    CONSTRAINT   pk_TripFacility PRIMARY KEY(idTrip,idCargoManifest,codeFacility)
);

CREATE TABLE Manifest(
    idManifest      NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    grossWeight     NUMBER(6,3)  CONSTRAINT nn_Manifest_grossWeight  NOT NULL,
    typeManifest    VARCHAR(42)  CONSTRAINT nn_Manifest_typeManifest NOT NULL,
    dateManifest    DATE         CONSTRAINT nn_Manifest_dateManifest NOT NULL,
    idTrip          NUMBER,
    codeFacility    NUMBER,
    idCargoManifest NUMBER,
    CONSTRAINT     ck_reg_Manifest_typeManifest CHECK(REGEXP_LIKE(typeManifest, 'LOAD|OFFLOAD|CARGO|load|offload|cargo') ),
    CONSTRAINT     pk_Manifest_idManifest       PRIMARY KEY(idManifest)
);

CREATE TABLE ContainerManifest(
    idManifest  NUMBER,
    nrContainer VARCHAR(11),
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
    CONSTRAINT uk_imo               UNIQUE(imo)
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
    nrIdentification INTEGER     CONSTRAINT pk_FacilityManager_nrIdentification    PRIMARY KEY,
    type             VARCHAR(42) CONSTRAINT nn_FacilityManager_type              NOT NULL,
    codeFacility     NUMBER      CONSTRAINT nn_FacilityManager_codeFacility      NOT NULL
);

CREATE TABLE FacilityStaff(
    nrIdentification INTEGER     CONSTRAINT pk_FacilityStaff_nrIdentification    PRIMARY KEY,
    type             VARCHAR(42) CONSTRAINT pk_FacilityStaff_type                 NOT NULL,
    codeFacility     NUMBER      CONSTRAINT nn_FacilityStaff_codeFacility         NOT NULL
);

CREATE TABLE AuditTrail(
    idAudit    NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    registDate DATE,
    author     VARCHAR(42),
    action     VARCHAR(6),
    idCargoManifest NUMBER,
    idManifest  NUMBER,
    nrContainer VARCHAR(11),
    CONSTRAINT pk_AuditTrail_author_registDate PRIMARY KEY(idAudit)
);

-- chaves estrangeiras
ALTER TABLE ContainerManifest ADD CONSTRAINT  fk_ContainerManifest_numberContainer   FOREIGN KEY (nrContainer)                         REFERENCES Container(numberContainer);
ALTER TABLE ContainerManifest ADD CONSTRAINT  fk_ContainerManifest_idManifest        FOREIGN KEY (idManifest)                          REFERENCES Manifest(idManifest);
ALTER TABLE TripFacility      ADD CONSTRAINT  fk_TripFacility_idTrip                 FOREIGN KEY (idTrip,idCargoManifest)              REFERENCES Trip(idTrip,idCargoManifest);
ALTER TABLE TripFacility      ADD CONSTRAINT  fk_TripFacility_codeFacility           FOREIGN KEY (codeFacility)                        REFERENCES Facility(codeFacility);
ALTER TABLE ShipCaptain       ADD CONSTRAINT  fk_ShipCaptain_nrIdentification        FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE FleetManager      ADD CONSTRAINT  fk_FleetManager_nrIdentification       FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE TraficManager     ADD CONSTRAINT  fk_TraficManager_nrIdentification      FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE FacilityStaff     ADD CONSTRAINT  fk_FacilityStaff_nrIdentification      FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE FacilityManager   ADD CONSTRAINT  fk_FacilityManager_nrIdentification    FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE TruckDriver       ADD CONSTRAINT  fk_TruckDriver_nrIdentification        FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE ChiefEletrical    ADD CONSTRAINT  fk_ChiefEletrical_nrIdentification     FOREIGN KEY (nrIdentification)                    REFERENCES Worker(nrIdentification);
ALTER TABLE Manifest          ADD CONSTRAINT  fk_Manifest_idTrip                     FOREIGN KEY (idTrip,idCargoManifest,codeFacility) REFERENCES TripFacility(idTrip,idCargoManifest,codeFacility);
ALTER TABLE TripVehicle       ADD CONSTRAINT  fk_TripVehicle_idTrip_idCargoManifest  FOREIGN KEY (idTrip,idCargoManifest)              REFERENCES Trip(idTrip,idCargoManifest);
ALTER TABLE TripVehicle       ADD CONSTRAINT  fk_TripVehicle_idVehicle               FOREIGN KEY (idVehicle)                           REFERENCES Vehicle(idVehicle);
ALTER TABLE Border            ADD CONSTRAINT  fk_Border_country                      FOREIGN KEY (country)                             REFERENCES Country(country);
ALTER TABLE PortDistance      ADD CONSTRAINT  fk_PortFacility_facility               FOREIGN KEY (facility)                            REFERENCES Facility(codefacility);
ALTER TABLE AuditTrail        ADD CONSTRAINT  fk_AuditTrail_idManifest_nrContainer   FOREIGN KEY (idManifest,nrContainer)              REFERENCES ContainerManifest(idManifest,nrContainer);

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
/
create or replace trigger test_number_containers_not_exceed_capacity
    before insert or update on ContainerManifest for each row
declare
    number_of_containers number(5);
    capacity number(11);
    p_mmsi number(11);
    ex_erro EXCEPTION;
    id_Manifest ContainerManifest.idManifest%type;


begin
        SELECT COUNT(c.IDMANIFEST) into number_of_containers
FROM CONTAINERMANIFEST c WHERE
        c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE m.idManifest = :new.idManifest AND m.IDTRIP IN (SELECT  t.IDTRIP
                                                                                                                    FROM TRIP t INNER JOIN TRIPVEHICLE tv ON t.IDTRIP LIKE tv.IDTRIP
                                                                                                                    WHERE tv.IDVEHICLE IN (SELECT IdVehicle FROM Vehicle v WHERE v.IDVEHICLE IN (SELECT IdVehicle FROM SHIP))));
SELECT mmsi into p_mmsi FROM SHIP WHERE idVehicle IN (SELECT v.IdVehicle from Vehicle v where v.IDVEHICLE = (SELECT tv.IDVEHICLE from TripVehicle tv WHERE tv.IDTRIP IN (SELECT t.IDTRIP from Trip t WHERE t.IdTrip = (SELECT m.idTrip FROM MANIFEST m WHERE m.IDMANIFEST = :new.idManifest))));   
    capacity := func_getcapacityfromgivenship(p_mmsi);
    if capacity <= number_of_containers then
        raise_application_error(-20009,'TENTOU INTRODUZIR MAIS CONTENTORES DO QUE A CAPACIDADE DO NAVIO');
    end if;
end;
/

create or replace trigger test_number_containers_not_exceed_capacity
    before insert or update on ContainerManifest for each row
declare
    number_of_containers number(5);
    capacity number(11);
    p_mmsi number(11);
    ex_erro EXCEPTION;
    id_Manifest ContainerManifest.idManifest%type;


begin
        SELECT COUNT(c.IDMANIFEST) into number_of_containers
FROM CONTAINERMANIFEST c WHERE
        c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE m.idManifest = :new.idManifest AND m.IDTRIP IN (SELECT  t.IDTRIP
                                                                                                                    FROM TRIP t INNER JOIN TRIPVEHICLE tv ON t.IDTRIP LIKE tv.IDTRIP
                                                                                                                    WHERE tv.IDVEHICLE IN (SELECT IdVehicle FROM Vehicle v WHERE v.IDVEHICLE IN (SELECT IdVehicle FROM SHIP))));
SELECT mmsi into p_mmsi FROM SHIP WHERE idVehicle IN (SELECT v.IdVehicle from Vehicle v where v.IDVEHICLE = (SELECT tv.IDVEHICLE from TripVehicle tv WHERE tv.IDTRIP IN (SELECT t.IDTRIP from Trip t WHERE t.IdTrip = (SELECT m.idTrip FROM MANIFEST m WHERE m.IDMANIFEST = :new.idManifest))));   
    capacity := func_getcapacityfromgivenship(p_mmsi);
    if capacity <= number_of_containers then
        raise_application_error(-20009,'TENTOU INTRODUZIR MAIS CONTENTORES DO QUE A CAPACIDADE DO NAVIO');
    end if;
end;
/

create or replace trigger PortFull
before insert or update on MANIFEST
for each row

declare
    manifest      SYS_REFCURSOR;
    codFacility  INTEGER;
    occupancy    Number(8,3);
    taxa         Number(8,3);

BEGIN

    OPEN manifest FOR
    SELECT codeFacility
    FROM MANIFEST
    WHERE UPPER(TYPEMANIFEST) LIKE 'OFFLOAD';

    fetch manifest into codFacility;

    occupancy := fnc_occupancyRate(codFacility);
    taxa:= 100;

    IF occupancy >= taxa THEN
        raise_application_error (-20007, 'The port is full, you can not create a manifest for the port.');
    END IF;
END;
/

--UserApplication
INSERT INTO UserApplication(email, password) VALUES('JoaoAlmeida@gmail.com','FleetManagerJoao');
INSERT INTO UserApplication(email, password) VALUES('BrunoRibeiro@gmail.com','ChipCaptainBruno');
INSERT INTO UserApplication(email, password) VALUES('OscarManuel@gmail.com','ChiefEletricalOscar');
INSERT INTO UserApplication(email, password) VALUES('jorge@shipcaptain.com','12345');
INSERT INTO UserApplication(email, password) VALUES('Rui Silva@gmail.com','12345');
INSERT INTO UserApplication(email, password) VALUES('Fernado@gmail.com','96457');
INSERT INTO UserApplication(email, password) VALUES('Rogerio@gmail.com','1465');
INSERT INTO UserApplication(email, password) VALUES('Alberto@gmail.com','416489');

--Client
INSERT INTO Client (nameClient, nrIdentificationClient, idUser) VALUES ('Rui Silva',1,5);

--Address
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES(1090,459,'Vilarinha','Aldoar');
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES(4100,92,'Igreja de Ramalde','Ramalde');
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES(4300,5,'Campo 24 de Agosto','Bonfim');
INSERT INTO Address(codAddress,nrDoor,street,parish) VALUES (1,47,'Saint TomÃƒÆ’Ã‚Â© Street','Paranhos');

--Worker

INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(1, 1090, 'Joao',459);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(2, 4100, 'Bruno',92);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(3, 4300, 'Oscar',5);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(4, 1,'Jorge',345);
INSERT INTO Worker(idUser, codAddress, nameWorker, nrIdentification) VALUES(5, 1090,'Bernado',5497);

--TruckDriver
INSERT INTO TruckDriver(nrIdentification,drivingLicense) VALUES(5,'C1E');

--FleetManager
INSERT INTO FleetManager(nrIdentification) VALUES (459);

--ShipCaptain
INSERT INTO ShipCaptain(nrIdentification, qualification) VALUES(92,'Master');
INSERT INTO ShipCaptain(nrIdentification, qualification) VALUES(345,'Master');

--ChiefEletrical
INSERT INTO ChiefEletrical(nrIdentification, qualification) VALUES(5,'QNQ 4');

--vehicle
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);

INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);
INSERT INTO Vehicle VALUES(idVehicle.NEXTVAL);

--Truck
INSERT INTO Truck(registration,nrIdentification,idVehicle) VALUES('103JS',5,6);

--Continent
INSERT INTO CONTINENT(continent) Values('Europe');
INSERT INTO CONTINENT(continent) VALUES('America');
INSERT INTO CONTINENT(continent) VALUES('Asia');
INSERT INTO CONTINENT(continent) VALUES('Africa');
INSERT INTO CONTINENT(continent) VALUES('Oceania');


INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Cyprus','Europe','CY','CYP',0.85,'Nicosia',35.16666667,33.366667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Malta','Europe','MT','MLT',0.44,'Valletta',35.88333333,14.5);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Greece','Europe','GR','GRC',10.76,'Athens',37.98333333,23.733333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Portugal','Europe','PT','PRT',10.31,'Lisbon',38.71666667,-9.133333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Turkey','Europe','TR','TUR',79.81,'Ankara',39.93333333,32.866667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Armenia','Europe','AM','ARM',3.01,'Yerevan',40.16666667,44.5);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Spain','Europe','ES','ESP',46.53,'Madrid',40.4,-3.683333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Albania','Europe','AL','ALB',2.88,'Tirana',41.31666667,19.816667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Georgia','Europe','GE','GEO',3.71,'Tbilisi',41.68333333,44.833333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Italy','Europe','IT','ITA',60.59,'Rome',41.9,12.483333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Macedonia','Europe','MK','MKD',2.07,'Skopje',42,21.433333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Montenegro','Europe','ME','MNE',0.62,'Podgorica',42.43333333,19.266667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Kosovo','Europe','KO','KOS',1.77,'Pristina',42.66666667,21.166667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Bulgaria','Europe','BG','BGR',7.1,'Sofia',42.68333333,23.316667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Monaco','Europe','MC','MCO',0.04,'Monaco',43.73333333,7.416667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Bosnia','Europe','BA','BIH',3.75,'Sarajevo',43.86666667,18.416667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Romania','Europe','RO','ROU',19.64,'Bucharest',44.43333333,26.1);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Serbia','Europe','RS','SRB',7.04,'Belgrade',44.83333333,20.5);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Croatia','Europe','HR','HRV',4.15,'Zagreb',45.8,16);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Slovenia','Europe','SI','SVN',2.06,'Ljubljana',46.05,14.516667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Switzerland','Europe','CH','CHE',8.42,'Bern',46.91666667,7.466667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Moldova','Europe','MD','MDA',3.55,'Chisinau',47,28.85);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Liechtenstein','Europe','LI','LIE',0.04,'Vaduz',47.13333333,9.516667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Hungary','Europe','HU','HUN',9.8,'Budapest',47.5,19.083333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Slovakia','Europe','SK','SVK',5.44,'Bratislava',48.15,17.116667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Austria','Europe','AT','AUT',8.77,'Vienna',48.2,16.366667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('France','Europe','FR','FRA',66.99,'Paris',48.86666667,2.333333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Luxembourg','Europe','LU','LUX',0.59,'Luxembourg',49.6,6.116667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Czech Republic','Europe','CZ','CZE',10.57,'Prague',50.08333333,14.466667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Ukraine','Europe','UA','UKR',42.59,'Kyiv',50.43333333,30.516667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Belgium','Europe','BE','BEL',11.37,'Brussels',50.83333333,4.333333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('United Kingdom','Europe','GB','GBR',65.81,'London',51.5,-0.083333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Poland','Europe','PL','POL',38.42,'Warsaw',52.25,21);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Netherlands','Europe','NL','NLD',17.08,'Amsterdam',52.35,4.916667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Germany','Europe','DE','DEU',82.8,'Berlin',52.51666667,13.4);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Ireland','Europe','IE','IRL',4.77,'Dublin',53.31666667,-6.233333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Belarus','Europe','BY','BLR',9.48,'Minsk',53.9,27.566667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Lithuania','Europe','LT','LTU',2.85,'Vilnius',54.68333333,25.316667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Denmark','Europe','DK','DNK',5.75,'Copenhagen',55.66666667,12.583333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Russia','Europe','RU','RUS',146.5,'Moscow',55.75,37.6);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Latvia','Europe','LV','LVA',1.98,'Riga',56.95,24.1);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Sweden','Europe','SE','SWE',10,'Stockholm',59.33333333,18.05);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Estonia','Europe','EE','EST',1.32,'Tallinn',59.43333333,24.716667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Norway','Europe','NO','NOR',5.26,'Oslo',59.91666667,10.75);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Finland','Europe','FI','FIN',5.5,'Helsinki',60.16666667,24.933333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Iceland','Europe','IS','ISL',0.34,'Reykjavik',64.15,-21.95);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Mexico','America','MX','MEX',128.9,'Mexico City',19.43333333,-99.133333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Canada','America','CA','CAN',37.7,'Ottawa',45.41666667,-75.7);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('United States','America','US','USA',331,'Washington',38.89511,-77.03637);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Panama','America','PA','PAN',4.3,'Panama City',8.966666667,-79.533333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Costa Rica','America','CR','CRI',5.1,'San Jose',9.933333333,-84.083333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Nicaragua','America','NI','NIC',6.6,'Managua',12.13333333,-86.25);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('El Salvador','America','SV','SLV',6.48,'San Salvador',13.7,-89.2);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Honduras','America','HN','HND',9.9,'Tegucigalpa',14.1,-87.216667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Guatemala','America','GT','GTM',17.91,'Guatemala City',14.61666667,-90.516667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Belize','America','BZ','BLZ',397.6,'Belmopan',17.25,-88.766667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Uruguay','America','UY','URY',3.35,'Montevideo',-34.85,-56.166667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Argentina','America','AR','ARG',41.67,'Buenos Aires',-34.58333333,-58.666667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Chile','America','CL','CHL',16.8,'Santiago',-33.45,-70.666667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Paraguay','America','PY','PRY',6.24,'Asuncion',-25.26666667,-57.666667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Bolivia','America','BO','BOL',9.7,'La Paz',-16.5,-68.15);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Brazil','America','BR','BRA',206.12,'Brasilia',-15.78333333,-47.916667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Peru','America','PE','PER',28.22,'Lima',-12.05,-77.05);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Ecuador','America','EC','ECU',14.88,'Quito',-0.216666667,-78.5);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Colombia','America','CO','COL',46.86,'Bogota',4.6,-74.083333);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Suriname','America','SR','SUR',0.04,'Paramaribo',5.833333333,-55.166667);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Guyana','America','GY','GUY',786.5,'Georgetown',6.8,-58.15);
INSERT INTO COUNTRY(country, continent, alpha2_Code ,alpha3_Code, population, capitalName, latitudeCapital, longitudeCapital) VALUES('Venezuela','America','VE','VEN',31.02,'Caracas',10.48333333,-66.866667);

INSERT INTO BORDER(country1, country) VALUES('Belize','Mexico');
INSERT INTO BORDER(country1, country) VALUES('Canada','United States');
INSERT INTO BORDER(country1, country) VALUES('Costa Rica','Nicaragua');
INSERT INTO BORDER(country1, country) VALUES('Costa Rica','Panama');
INSERT INTO BORDER(country1, country) VALUES('Guatemala','Mexico');
INSERT INTO BORDER(country1, country) VALUES('Guatemala','El Salvador');
INSERT INTO BORDER(country1, country) VALUES('Honduras','El Salvador');
INSERT INTO BORDER(country1, country) VALUES('Honduras','Nicaragua');
INSERT INTO BORDER(country1, country) VALUES('Mexico','United States');
INSERT INTO BORDER(country1, country) VALUES('Panama','Colombia');
INSERT INTO BORDER(country1, country) VALUES('Argentina','Bolivia');
INSERT INTO BORDER(country1, country) VALUES('Argentina','Brazil');
INSERT INTO BORDER(country1, country) VALUES('Argentina','Chile');
INSERT INTO BORDER(country1, country) VALUES('Argentina','Paraguay');
INSERT INTO BORDER(country1, country) VALUES('Argentina','Uruguay');
INSERT INTO BORDER(country1, country) VALUES('Bolivia','Brazil');
INSERT INTO BORDER(country1, country) VALUES('Bolivia','Chile');
INSERT INTO BORDER(country1, country) VALUES('Bolivia','Paraguay');
INSERT INTO BORDER(country1, country) VALUES('Bolivia','Peru');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Colombia');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Guyana');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Paraguay');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Peru');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Suriname');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Uruguay');
INSERT INTO BORDER(country1, country) VALUES('Brazil','Venezuela');
INSERT INTO BORDER(country1, country) VALUES('Chile','Peru');
INSERT INTO BORDER(country1, country) VALUES('Colombia','Ecuador');
INSERT INTO BORDER(country1, country) VALUES('Colombia','Peru');
INSERT INTO BORDER(country1, country) VALUES('Colombia','Venezuela');
INSERT INTO BORDER(country1, country) VALUES('Ecuador','Peru');
INSERT INTO BORDER(country1, country) VALUES('Guyana','Suriname');
INSERT INTO BORDER(country1, country) VALUES('Guyana','Venezuela');
INSERT INTO BORDER(country1, country) VALUES('Albania','Greece');
INSERT INTO BORDER(country1, country) VALUES('Albania','Macedonia');
INSERT INTO BORDER(country1, country) VALUES('Albania','Montenegro');
INSERT INTO BORDER(country1, country) VALUES('Albania','Kosovo');
INSERT INTO BORDER(country1, country) VALUES('Germany','Austria');
INSERT INTO BORDER(country1, country) VALUES('Germany','Belgium');
INSERT INTO BORDER(country1, country) VALUES('Germany','Denmark');
INSERT INTO BORDER(country1, country) VALUES('Germany','France');
INSERT INTO BORDER(country1, country) VALUES('Germany','Netherlands');
INSERT INTO BORDER(country1, country) VALUES('Germany','Luxembourg');
INSERT INTO BORDER(country1, country) VALUES('Germany','Poland');
INSERT INTO BORDER(country1, country) VALUES('Germany','Czech Republic');
INSERT INTO BORDER(country1, country) VALUES('Germany','Switzerland');
INSERT INTO BORDER(country1, country) VALUES('Armenia','Georgia');
INSERT INTO BORDER(country1, country) VALUES('Armenia','Turkey');
INSERT INTO BORDER(country1, country) VALUES('Austria','Slovakia');
INSERT INTO BORDER(country1, country) VALUES('Austria','Slovenia');
INSERT INTO BORDER(country1, country) VALUES('Austria','Hungary');
INSERT INTO BORDER(country1, country) VALUES('Austria','Italy');
INSERT INTO BORDER(country1, country) VALUES('Austria','Liechtenstein');
INSERT INTO BORDER(country1, country) VALUES('Austria','Czech Republic');
INSERT INTO BORDER(country1, country) VALUES('Austria','Switzerland');
INSERT INTO BORDER(country1, country) VALUES('Belgium','France');
INSERT INTO BORDER(country1, country) VALUES('Belgium','Netherlands');
INSERT INTO BORDER(country1, country) VALUES('Belgium','Luxembourg');
INSERT INTO BORDER(country1, country) VALUES('Belarus','Latvia');
INSERT INTO BORDER(country1, country) VALUES('Belarus','Lithuania');
INSERT INTO BORDER(country1, country) VALUES('Belarus','Poland');
INSERT INTO BORDER(country1, country) VALUES('Belarus','Russia');
INSERT INTO BORDER(country1, country) VALUES('Belarus','Ukraine');
INSERT INTO BORDER(country1, country) VALUES('Bosnia','Croatia');
INSERT INTO BORDER(country1, country) VALUES('Bosnia','Montenegro');
INSERT INTO BORDER(country1, country) VALUES('Bosnia','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Bulgaria','Greece');
INSERT INTO BORDER(country1, country) VALUES('Bulgaria','Macedonia');
INSERT INTO BORDER(country1, country) VALUES('Bulgaria','Romania');
INSERT INTO BORDER(country1, country) VALUES('Bulgaria','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Bulgaria','Turkey');
INSERT INTO BORDER(country1, country) VALUES('Croatia','Slovenia');
INSERT INTO BORDER(country1, country) VALUES('Croatia','Hungary');
INSERT INTO BORDER(country1, country) VALUES('Croatia','Montenegro');
INSERT INTO BORDER(country1, country) VALUES('Croatia','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Slovakia','Hungary');
INSERT INTO BORDER(country1, country) VALUES('Slovakia','Poland');
INSERT INTO BORDER(country1, country) VALUES('Slovakia','Czech Republic');
INSERT INTO BORDER(country1, country) VALUES('Slovakia','Ukraine');
INSERT INTO BORDER(country1, country) VALUES('Slovenia','Hungary');
INSERT INTO BORDER(country1, country) VALUES('Slovenia','Italy');
INSERT INTO BORDER(country1, country) VALUES('Spain','France');
INSERT INTO BORDER(country1, country) VALUES('Spain','Portugal');
INSERT INTO BORDER(country1, country) VALUES('Estonia','Latvia');
INSERT INTO BORDER(country1, country) VALUES('Estonia','Russia');
INSERT INTO BORDER(country1, country) VALUES('Finland','Norway');
INSERT INTO BORDER(country1, country) VALUES('Finland','Russia');
INSERT INTO BORDER(country1, country) VALUES('Finland','Sweden');
INSERT INTO BORDER(country1, country) VALUES('France','Italy');
INSERT INTO BORDER(country1, country) VALUES('France','Luxembourg');
INSERT INTO BORDER(country1, country) VALUES('France','Monaco');
INSERT INTO BORDER(country1, country) VALUES('France','Switzerland');
INSERT INTO BORDER(country1, country) VALUES('Georgia','Russia');
INSERT INTO BORDER(country1, country) VALUES('Georgia','Turkey');
INSERT INTO BORDER(country1, country) VALUES('Greece','Macedonia');
INSERT INTO BORDER(country1, country) VALUES('Greece','Turkey');
INSERT INTO BORDER(country1, country) VALUES('Hungary','Romania');
INSERT INTO BORDER(country1, country) VALUES('Hungary','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Hungary','Ukraine');
INSERT INTO BORDER(country1, country) VALUES('Ireland','United Kingdom');
INSERT INTO BORDER(country1, country) VALUES('Italy','Switzerland');
INSERT INTO BORDER(country1, country) VALUES('Kosovo','Macedonia');
INSERT INTO BORDER(country1, country) VALUES('Kosovo','Montenegro');
INSERT INTO BORDER(country1, country) VALUES('Kosovo','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Latvia','Lithuania');
INSERT INTO BORDER(country1, country) VALUES('Latvia','Russia');
INSERT INTO BORDER(country1, country) VALUES('Liechtenstein','Switzerland');
INSERT INTO BORDER(country1, country) VALUES('Lithuania','Poland');
INSERT INTO BORDER(country1, country) VALUES('Lithuania','Russia');
INSERT INTO BORDER(country1, country) VALUES('Macedonia','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Moldova','Romania');
INSERT INTO BORDER(country1, country) VALUES('Moldova','Ukraine');
INSERT INTO BORDER(country1, country) VALUES('Norway','Sweden');
INSERT INTO BORDER(country1, country) VALUES('Norway','Russia');
INSERT INTO BORDER(country1, country) VALUES('Montenegro','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Poland','Czech Republic');
INSERT INTO BORDER(country1, country) VALUES('Poland','Russia');
INSERT INTO BORDER(country1, country) VALUES('Poland','Ukraine');
INSERT INTO BORDER(country1, country) VALUES('Romania','Serbia');
INSERT INTO BORDER(country1, country) VALUES('Romania','Ukraine');
INSERT INTO BORDER(country1, country) VALUES('Russia','Ukraine');



--Falility
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES(1,'Portonave',90,180,'Portugal','warehouse' ,500,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES(2,'Porto de LeixÃƒÆ’Ã‚Âµes',80,90,'Portugal','port', 1000,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES(3,'Porto de Aveiro',15,70,'Portugal','port', 3000,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES (4,'Berlengas Port', 12, 12, 'Portugal','port',1500,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES (5,'Fisgao Port', 25, 41, 'Portugal','port', 2000,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES (6,'Alvalade Port', 75, 22, 'Portugal','port', 1000,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity,dockingSlots) VALUES(29002,'Liverpool',53.46666667,-3.033333333,'United Kingdom','port',2000,20);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(14635,'Los Angeles',33.71666667,-118.2666667,'United States','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(25007,'New Jersey',40.66666667,-74.16666667,'United States','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(20301,'Rio Grande',-32.06666667,-52.06666667,'Brazil','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(20351,'Salvador',-12.96666667,-38.51666667,'Brazil','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(27248,'Santos',-23.93333333,-46.31666667,'Brazil','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(22226,'Halifax',44.65,-63.56666667,'Canada','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(25350,'Vancouver',49.28333333,-123.1166667,'Canada','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(27792,'San Vicente',-36.73333333,-73.15,'Chile','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(28082,'Valparaiso',-33.01666667,-71.63333333,'Chile','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(28261,'Buenaventura',3.916666667,-77.05,'Colombia','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(28313,'Cartagena',10.41666667,-75.53333333,'Colombia','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(18012,'Brest',48.4,-4.5,'France','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(18326,'Dunkirk',51.05,2.366666667,'France','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(18476,'Ponta Delgada',37.73333333,-25.66666667,'Portugal','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(23428,'Funchal',32.65,-16.91666667,'Portugal','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(13012,'Leixoes',41.18333333,-8.7,'Portugal','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(13390,'Setubal',38.5,-8.916666667,'Portugal','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(17386,'Barcelona',41.33333333,2.166666667,'Spain','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(18937,'Valencia',39.45,-0.3,'Spain','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(30045,'Callao',-12.05,-77.16666667,'Peru','port',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility, capacity) VALUES(10860,'Matarani',-17,-72.1,'Peru','port',2000);

INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility,capacity) VALUES(7,'Armazem de Vigo',25,65,'Portugal','warehouse',2000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility,capacity) VALUES(8,'Armazem de Braga',80,5,'Portugal','warehouse',4000);
INSERT INTO Facility(codeFacility, nameFacility, latitude, longitude, country, typeFacility,capacity) VALUES(9,'Armazem de Galiza',20,80,'Spain','warehouse',2);

INSERT INTO portDistance(facility,codefacility,distance) VALUES (17386,29002,1781);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (17386,14635,7791);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (17386,25007,3728);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (17386,18937,164);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,17386,1457);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,30045,5796);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,18326,378);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,23428,1114);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,13012,511);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,29002,406);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,14635,7368);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,10860,6231);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,25007,3029);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,13390,693);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18012,18937,1329);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,17386,5233);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,18012,4810);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,30045,1124);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,28313,669);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,18326,5100);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,23428,4132);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,13012,4583);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,29002,4933);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,14635,3047);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,10860,1561);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,25007,2372);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,13390,4563);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28261,18937,5105);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,17386,6219);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,13012,5569);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,29002,5919);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,14635,3654);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,10860,442);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,25007,3358);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,13390,5549);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (30045,18937,6091);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,17386,4614);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,18012,4203);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,30045,1655);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,18326,4492);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,23428,3513);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,13012,3976);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,29002,4336);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,14635,3227);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,10860,2090);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,25007,1857);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,13390,3947);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28313,18937,4486);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,17386,1777);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,30045,6086);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,23428,1432);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,13012,831);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,29002,597);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,14635,7658);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,10860,6521);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,25007,3296);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,13390,1013);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18326,18937,1649);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,17386,1123);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,30045,5118);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,13012,655);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,29002,1429);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,14635,6690);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,10860,5553);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,25007,2768);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,13390,536);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (23428,18937,995);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,17386,3229);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,18012,2431);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,28261,2691);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,30045,3677);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,28313,2175);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,18326,2688);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,23428,2279);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,13012,2466);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,29002,2493);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,14635,5249);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,10860,4112);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,25007,600);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,27792,5139);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,13390,2507);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,18937,3101);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,28082,4952);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (22226,25350,6358);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13012,17386,969);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13012,29002,835);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13012,14635,7141);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13012,25007,2948);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13012,13390,207);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13012,18937,841);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (29002,14635,7491);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (29002,25007,3115);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (14635,25007,4930);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,17386,6654);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,13012,6004);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,29002,6354);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,14635,4083);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,25007,3793);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,13390,5984);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (10860,18937,6526);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,17386,1497);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,18012,1128);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,28261,3772);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,30045,4758);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,28313,3163);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,18326,1441);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,23428,530);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,22226,1754);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,13012,817);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,29002,1358);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,14635,6330);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,10860,5193);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,25007,2253);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,20301,4579);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,20351,3187);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,27792,6220);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,27248,4058);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,13390,804);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,18937,1369);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,28082,6033);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18476,25350,7439);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,17386,5413);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,18012,5532);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,28261,5252);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,30045,4191);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,28313,4720);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,18326,5852);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,23428,4430);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,22226,5321);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,13012,5082);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,29002,5841);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,14635,7401);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,10860,3903);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,25007,5461);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,20351,1458);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,27792,2743);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,27248,608);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,13390,4942);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,18937,5285);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,28082,2963);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20301,25350,8540);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,17386,4027);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,18012,4139);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,28261,3953);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,30045,4939);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,28313,3325);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,18326,4459);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,23428,3038);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,22226,3927);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,13012,3690);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,29002,4447);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,14635,6511);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,10860,5287);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,25007,4064);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,27792,4127);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,27248,936);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,13390,3548);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,18937,3899);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,28082,4347);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (20351,25350,7620);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,17386,7681);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,18012,7258);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,28261,2586);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,30045,1498);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,28313,3117);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,18326,7548);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,23428,6580);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,13012,7031);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,29002,7381);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,14635,4934);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,10860,1186);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,25007,4820);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,13390,7011);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,18937,7553);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27792,28082,236);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,17386,4892);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,18012,5011);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,28261,4826);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,30045,4760);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,28313,4199);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,18326,5331);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,23428,3907);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,22226,4800);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,13012,4561);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,29002,5320);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,14635,7384);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,10860,4472);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,25007,4937);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,27792,3312);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,13390,4418);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,18937,4764);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,28082,3532);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (27248,25350,8493);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13390,17386,799);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13390,29002,1017);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13390,14635,7121);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13390,25007,3011);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (13390,18937,671);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18937,29002,1653);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18937,14635,7663);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (18937,25007,3600);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,17386,7494);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,18012,7071);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,28261,2399);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,30045,1299);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,28313,2930);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,18326,7361);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,23428,6393);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,13012,6844);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,29002,7194);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,14635,4806);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,10860,965);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,25007,4633);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,13390,6824);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (28082,18937,7366);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,17386,8900);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,18012,8477);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,28261,4156);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,30045,4763);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,28313,4336);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,18326,8767);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,23428,7799);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,13012,8250);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,29002,8600);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,14635,1162);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,10860,5192);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,25007,6039);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,27792,6043);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,13390,8230);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,18937,8772);
INSERT INTO portDistance(facility,codefacility,distance) VALUES (25350,28082,5915);


--Trip
INSERT INTO Trip(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(1,1,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2003/05/06 08:15:30', 'yyyy/mm/dd hh24:mi:ss'),92,459,5,1);
INSERT INTO Trip(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(2,1,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2003/05/06 08:15:30', 'yyyy/mm/dd hh24:mi:ss'),92,459,5,1);
INSERT INTO Trip(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(3,1,3,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2003/05/06 08:15:30', 'yyyy/mm/dd hh24:mi:ss'),92,459,5,1);
INSERT INTO Trip(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(4,2,3,TO_DATE('2021/12/02 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2021/12/21 17:35:30', 'yyyy/mm/dd hh24:mi:ss'),345,459,5,2);
INSERT INTO TRIP(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(5,3,29002,TO_DATE('2022/03/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2022/06/12 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),345,459,5,5);
INSERT INTO TRIP(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(6,3,29002,TO_DATE('2022/03/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2022/06/12 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),345,459,5,3);
INSERT INTO TRIP(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip,ShipCaptainNrIdentification   ,FleetManagerNrIdentification  ,ChiefEletricalNrIdentificatio, startVehicle ) VALUES(7,3,29002,TO_DATE('2022/03/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2022/06/05 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),345,459,5,6);

INSERT INTO Trip(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip)
VALUES(8,7,9,TO_DATE('2022/01/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2022/01/10 8:15:30', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO Trip(idCargoManifest, startFacility, endFacility, startDateTrip, endDateTrip)
VALUES(9,8,9,TO_DATE('2022/02/17 10:02:44', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2022/03/01 8:15:30', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO TripVehicle (idTrip, idCargoManifest, idVehicle) VALUES (1,1,1);
INSERT INTO TripVehicle (idTrip, idCargoManifest, idVehicle) VALUES (2,2,1);
INSERT INTO TripVehicle (idTrip, idCargoManifest, idVehicle) VALUES (3,3,1);
INSERT INTO TripVehicle (idTrip, idCargoManifest, idVehicle) VALUES (4,4,2);
INSERT INTO TripVehicle (idTrip, idCargoManifest, idVehicle) VALUES (5,5,5);
INSERT INTO TripVehicle (idTrip, idCargoManifest, idVehicle) VALUES (6,6,6);

INSERT INTO TripVehicle(idTrip, idCargoManifest, idvehicle) VALUES(8,8,7);
INSERT INTO TripVehicle(idTrip, idCargoManifest, idvehicle) VALUES(9,9,8);


--Energy
INSERT INTO Energy(nrGenerators,power) VALUES(2,1200);

--Ship
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES(123456789,1,4,'Vasco Da Gama','IMO1234567',4,400,30,18,'LAT07');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES(987654321,2,4,'MagalhÃƒÆ’Ã‚Â£es','IMO7654321',2,200,20,15,'R1U2F');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES(987654322,3,4,'Oceano','IMO7654322',2,350,25,19,'LE4576');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign) VALUES (123456788,4,1,'VARAMO','IMO1234568',9,100,1,1,'E4576');
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign, idEnergy) VALUES(123456780,5,77,'DORNAS COSTA','IMO1234577',77,100,25,25,'77DC',1);
INSERT INTO Ship(mmsi, idVehicle, draft, nameShip, imo, typeShip, capacity, length, width, callsign, idEnergy) VALUES(923456780,6,44,'BP MAR','IMO5544444',44,2,25,25,'77DC',1);

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
INSERT INTO POSITION (x,y,z) VALUES (0,0,0);
INSERT INTO POSITION (x,y,z) VALUES (1,0,0);
INSERT INTO POSITION (x,y,z) VALUES (2,0,0);
INSERT INTO POSITION (x,y,z) VALUES (3,0,0);

--Dimension
INSERT INTO Dimension (length, height) VALUES (12,12);

--typeContainer
INSERT INTO typeContainer (type,temperature) VALUES ('Refrigerated',7);
INSERT INTO typeContainer (type,temperature) VALUES ('MAXMINUS5',-5);

--Container
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('1','ABC', 'ABCU1827364','Light bulbs',5000,2000,3000,1000,1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('1','ABC', 'ABDU1827364','Chocolate',5000,2000,3000,1000,1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('20BB','CER212','JORU1234568','Strawberries',5000,2000,3000,1000,1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('20CC','CER212','JORU1234569','Melons',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('20DD','CER212','JORU1234564','Lemon',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('20EE','CER212','JORU1234563','Kiwi',5000,2000,3000,1000,1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('20FF','CER212','JORU1234553','Cherries',5000,2000,3000,1000,1,1,'MAXMINUS5');
INSERT INTO Container (iso,certificate,numberContainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idDimension,idClient,type) VALUES ('20GG','CER212','JORU1234513','Figs',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO  Container(iso,certificate,numbercontainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idclient,iddimension,type) VALUES ('ABCD','CERT1N','ABCU1113456','Bottled Wine',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO  Container(iso,certificate,numbercontainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idclient,iddimension,type) VALUES ('CDCD','CERT2P','TVCU2124466','Medicine',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO  Container(iso,certificate,numbercontainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idclient,iddimension,type) VALUES ('ACDS','CERT3G','XGCU2123466','Motorbike',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO  Container(iso,certificate,numbercontainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idclient,iddimension,type) VALUES ('TTVB','CERT4F','SSDU2124366','Cash',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO  Container(iso,certificate,numbercontainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idclient,iddimension,type) VALUES ('SSET','CERT5P','FFAU2124566','Rations',5000,2000,3000,1000,1,1,'Refrigerated');
INSERT INTO  Container(iso,certificate,numbercontainer,load,MAXWEIGHT,WEIGHTEMPTY,MAXWEIGHTPACKED,MAXVOLUME,idclient,iddimension,type) VALUES ('DFRE','CERT6V','LLCU2124766','Seeds',5000,2000,3000,1000,1,1,'Refrigerated');

--TripFacility
INSERT INTO TripFacility(idTrip,idCargoManifest, codeFacility,arrivalDate,departureDate) VALUES(4,4,6,TO_DATE('2021/12/05 17:02:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/05 23:45', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest, codeFacility,arrivalDate,departureDate) VALUES(4,4,1,TO_DATE('2021/12/03 20:00:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/03 21:45', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(4,4,4,TO_DATE('2021/12/14 10:00:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/14 10:55', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(4,4,2,TO_DATE('2021/12/02 09:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/02 09:45', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(4,4,5,TO_DATE('2021/12/19 18:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/19 22:45', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(4,4,3,TO_DATE('2021/12/21 07:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2021/12/21 22:45', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(5,5,3,TO_DATE('2022/03/01 07:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/03/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(5,5,4,TO_DATE('2022/04/02 08:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/04/05 10:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(5,5,29002,TO_DATE('2022/06/05 08:15:30', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/06/11 11:32:42', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(6,6,3,TO_DATE('2022/03/01 07:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/03/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(6,6,29002,TO_DATE('2022/06/09 07:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/06/11 10:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(idTrip,idCargoManifest,codeFacility,arrivalDate,departureDate) VALUES(6,6,5,TO_DATE('2022/06/05 08:15:30', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/06/07 11:32:42', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO TripFacility(IDTRIP, IDCARGOMANIFEST, CODEFACILITY, ARRIVALDATE, DEPARTUREDATE) VALUES(8,8,7,TO_DATE('2022/01/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/01/03 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(IDTRIP, IDCARGOMANIFEST, CODEFACILITY, ARRIVALDATE, DEPARTUREDATE) VALUES(8,8,8,TO_DATE('2022/01/06 22:30:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2003/01/07 9:00:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(IDTRIP, IDCARGOMANIFEST, CODEFACILITY, ARRIVALDATE, DEPARTUREDATE) VALUES(8,8,9,TO_DATE('2022/01/10 07:15:00', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/03/01 8:15:30', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO TripFacility(IDTRIP, IDCARGOMANIFEST, CODEFACILITY, ARRIVALDATE, DEPARTUREDATE) VALUES(9,9,8,TO_DATE('2022/02/17 10:02:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/02/17 12:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO TripFacility(IDTRIP, IDCARGOMANIFEST, CODEFACILITY, ARRIVALDATE, DEPARTUREDATE) VALUES(9,9,9,TO_DATE('2022/03/01 07:30:30', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2022/03/01 8:15:30', 'yyyy/mm/dd hh24:mi:ss'));

--Manifest

INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2021/12/02 09:35:00', 'yyyy/mm/dd hh24:mi:ss'), 4,2,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codeFacility, IDCARGOMANIFEST) VALUES (40,'load',TO_DATE('2021/12/03 20:00:44', 'yyyy/mm/dd hh24:mi:ss'),4,1,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codeFacility, IDCARGOMANIFEST) VALUES (50,'offload',TO_DATE('2021/12/05 17:02:44', 'yyyy/mm/dd hh24:mi:ss'),4,6,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codeFacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2021/12/14 10:00:00', 'yyyy/mm/dd hh24:mi:ss'),4,4,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2021/12/19 18:35:00', 'yyyy/mm/dd hh24:mi:ss'), 4,5,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'offload',TO_DATE('2021/12/21 17:30:00', 'yyyy/mm/dd hh24:mi:ss'),4,3,4);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2022/03/01 17:30:00', 'yyyy/mm/dd hh24:mi:ss'),5,3,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'offload',TO_DATE('2022/04/03 14:30:00', 'yyyy/mm/dd hh24:mi:ss'),5,4,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2022/04/04 12:30:00', 'yyyy/mm/dd hh24:mi:ss'),5,4,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'offload',TO_DATE('2022/06/05 11:30:00', 'yyyy/mm/dd hh24:mi:ss'),5,29002,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2022/06/07 11:32:42', 'yyyy/mm/dd hh24:mi:ss'),5,29002,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'offload',TO_DATE('2022/06/09 11:30:00', 'yyyy/mm/dd hh24:mi:ss'),5,29002,5);
INSERT INTO Manifest(grossWeight,typeManifest,dateManifest,idTrip,codefacility, IDCARGOMANIFEST) VALUES (50,'load',TO_DATE('2021/03/15 11:30:00', 'yyyy/mm/dd hh24:mi:ss'),6,5,6);

INSERT INTO Manifest(grossWeight, typeManifest, dateManifest, idTrip, idCargoManifest, codeFacility) VALUES(120,'OFFLOAD',TO_DATE('2022/01/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'),8,8,7);
INSERT INTO Manifest(grossWeight, typeManifest, dateManifest, idTrip, idCargoManifest, codeFacility) VALUES(400,'LOAD',TO_DATE('2022/01/06 22:30:00', 'yyyy/mm/dd hh24:mi:ss'),8,8,8);
INSERT INTO Manifest(grossWeight, typeManifest, dateManifest, idTrip, idCargoManifest, codeFacility) VALUES(200,'OFFLOAD',TO_DATE('2022/01/10 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),8,8,9);

INSERT INTO Manifest(grossWeight, typeManifest, dateManifest, idTrip, idCargoManifest, codeFacility) VALUES(200,'LOAD',TO_DATE('2022/02/17 10:02:44', 'yyyy/mm/dd hh24:mi:ss'),9,9,8);
INSERT INTO Manifest(grossWeight, typeManifest, dateManifest, idTrip, idCargoManifest, codeFacility) VALUES(200,'LOAD',TO_DATE('2022/03/01 8:15:30', 'yyyy/mm/dd hh24:mi:ss'),9,9,9);

--ContainerManifest
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (1,'ABCU1827364',2);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (1,'ABDU1827364',3);

INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'JORU1234568',1);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (2,'JORU1234569',6);

INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'ABCU1827364',2);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'ABDU1827364',3);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (3,'JORU1234568',1);

INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (4,'JORU1234563',4);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (4,'JORU1234513',6);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (5,'JORU1234553',5);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (5,'JORU1234564',3);

INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (6,'JORU1234569',1);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (6,'JORU1234563',4);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (6,'JORU1234513',6);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (6,'JORU1234553',5);
INSERT INTO ContainerManifest (idManifest,nrContainer,idPosition) VALUES (6,'JORU1234564',3);

INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(7,'ABCU1113456',8);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(7,'TVCU2124466',9);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(7,'XGCU2123466',10);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(7,'SSDU2124366',11);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(8,'TVCU2124466',9);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(8,'SSDU2124366',11);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(9,'FFAU2124566',9);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(9,'LLCU2124766',11);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(10,'FFAU2124566',9);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(10,'LLCU2124766',11);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(10,'ABCU1113456',8);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(10,'XGCU2123466',10);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(11,'ABCU1113456',1);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(11,'XGCU2123466',2);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(12,'ABCU1113456',1);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(12,'XGCU2123466',2);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(13,'XGCU2123466',10);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(13,'ABCU1113456',8);

/
create or replace trigger CargoShipAvailability 
before insert or update on Trip for each row

declare
ex_erro EXCEPTION;
bool boolean;
start_date Date;
end_date Date;
id_vehicle trip.startvehicle%type;
total number;
cursor cursors is select distinct startVehicle, startDateTrip, endDateTrip from Trip where startVehicle = :new.startVehicle;

begin
open cursors;
bool := false;
loop
fetch cursors into id_vehicle, start_date, end_date;
select count(startVehicle) into total
from trip 
where startVehicle = id_vehicle
and (:new.startDateTrip between start_date and end_date) or (:new.endDateTrip between start_date and end_date);

if total > 1 then
bool :=true;
end if;

exit when cursors%notfound;
end loop;

if bool = true then
raise ex_erro;
end if;

exception when
ex_erro then
raise_application_error (-20009, 'The ship is not available so it is not possible to register a trip and consenquently a cargo manifest');

end ;
/
/*ADICIONAR MAIS CONTENTORES DO QUE A CAPACIDADE DO NAVIO US308*/
/*EXPECTED TRIGGER ERROR - "TENTOU INTRODUZIR MAIS CONTENTORES DO QUE A CAPACIDADE DO NAVIO"*/

INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(13,'LLCU2124766',11);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(13,'TVCU2124466',3);
INSERT INTO CONTAINERMANIFEST(idmanifest,nrcontainer,idPosition) VALUES(13,'ABDU1827364',1);