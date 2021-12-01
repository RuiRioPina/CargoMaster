INSERT INTO UserApplication (email, password) 
VALUES ('jorge@shipcaptain.com','12345');

INSERT INTO Address (codAddress, nrDoor,street,parish)
VALUES (1,47,'Saint Tomé Street','Paranhos');

INSERT INTO Worker (nameWorker,nrIdentification, codAddress, idUser) 
VALUES ('Jorge',12345,1,1);

INSERT INTO ShipCaptain (qualification, idWorker)
VALUES ('Master',1);

INSERT INTO Vehicle (idVehicle)
VALUES (1);

INSERT INTO Ship (mmsi,draft,nameShip,imo,typeShip,capacity,length,width,idVehicle,idEnergy,idChiefEletrical,idShipCaptain,idFleet)
VALUES ('123456789',1,'VARAMO','IMO1234567',9,100,1,1,1,null,null,1,null);

INSERT INTO Position (X,Y,z) 
VALUES (12,12,12);

INSERT INTO Container (iso,certificate,numberContainer, load)
VALUES ('1','ABC', 'ABCU1827364', 100);

INSERT INTO Container (iso,certificate,numberContainer, load)
VALUES ('1','ABC', 'ABDU1827364', 100);


INSERT INTO ContainerManifest (idManifest,nrContainer)
VALUES (2,'ABCU1827364');

INSERT INTO ContainerManifest (idManifest,nrContainer)
VALUES (2,'ABDU1827364');


INSERT INTO Manifest (grossWeight,typeManifest,dateManifest, idVehicle)
VALUES (50,'load',TO_DATE('2021/12/05 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 1);

INSERT INTO Manifest (grossWeight,typeManifest,dateManifest, idVehicle)
VALUES (40,'cargo',TO_DATE('2021-12-30 23:45:00', 'yyyy/mm/dd hh24:mi:ss'), 1);

INSERT INTO Manifest (grossWeight,typeManifest,dateManifest, idVehicle)
VALUES (50,'unload',TO_DATE('2021-12-05 07:45:00', 'yyyy/mm/dd hh24:mi:ss'), 1);

select*from manifest;

SELECT COUNT(*) FROM( SELECT v.idVehicle
            FROM Vehicle v
            WHERE v.idVehicle = ( SELECT t.idVehicle
                                        FROM Trip
                                        WHERE t.idVehicle = (SELECT m.idVehicle
                                                            FROM Manifest) ORDER BY 1));
INSERT INTO TRIP (dateTrip, endDateTrip, startDateTrip, startFacility, endFacility, idVehicle)
VALUES(TO_DATE('2021-12-07 07:45:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2021-12-12 07:45:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2021-12-05 07:45:00', 'yyyy/mm/dd hh24:mi:ss'), null, null, 1);
                                                            
                                                            
                                                            