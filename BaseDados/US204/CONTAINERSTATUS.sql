CREATE OR REPLACE function ver_estado_containers(numberContainerVariable Container.numberContainer%type) return varchar 
is
messageoutput varchar(100);
typeOfManifest manifest.typemanifest%type;
nameOfThing varchar(100);
typeOfFacility varchar(100);
 BEGIN
 messageoutput:= 'na';
SELECT typemanifest into typeOfManifest FROM manifest WHERE idManifest=(SELECT MAX(idmanifest) FROM CONTAINERMANIFEST WHERE nrcontainer = numberContainerVariable);
IF typeOfManifest='load' THEN
SELECT nameShip into nameOfThing from ship where idvehicle=( SELECT idvehicle FROM vehicle where idvehicle =(SELECT idvehicle FROM TRIP WHERE idtrip =(SELECT idtrip FROM MANIFEST WHERE idManifest=(SELECT MAX(idmanifest) FROM CONTAINERMANIFEST WHERE nrcontainer = numberContainerVariable))));
    messageoutput := 'SHIP, ' || nameofthing;
    END IF;
IF typeOfManifest='offload' THEN
SELECT nameFacility into nameOfThing from facility WHERE codefacility =(SELECT codeFacility FROM MANIFEST WHERE idManifest=(SELECT MAX(idmanifest) FROM CONTAINERMANIFEST WHERE nrcontainer = numberContainerVariable));
SELECT typeFacility into typeOfFacility from facility WHERE codefacility =(SELECT codeFacility FROM MANIFEST WHERE idManifest=(SELECT MAX(idmanifest) FROM CONTAINERMANIFEST WHERE nrcontainer = numberContainerVariable));

 messageOutput:= typeOfFacility ||', '|| nameOfThing;
 END IF;
    

return messageoutput;
end;
/   
    

 Declare
    numberContainer Container.numberContainer%type;
Begin
    numbercontainer := 'XGCU2123466';
    dbms_output.put_line(ver_estado_containers(numbercontainer));
end;   
/
 set serveroutput on;
/
 CREATE OR REPLACE function shipCaptainManifestYear(shipCaptainID Worker.idworker%type,year Integer) return integer
 is
 numberOfManifests INTEGER;
 BEGIN
 Select count(*)into numberOfManifests from manifest where (datemanifest>=year||'-01-01' and datemanifest <= year||'-12-31')AND typemanifest='load'AND idtrip=(Select idtrip from trip where idshipcaptain=shipCaptainID));
 return numberOfManifests;
 end;
/
 Declare
    shipcaptainid Worked.idworker%type;
    year INTEGER;
Begin
    year:=2020;
    shipcaptainid=2;
    dbms_output.put_line(shipCaptainManifestYear(shipcaptainid,year));
end;   
/

