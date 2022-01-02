
CREATE OR REPLACE function func_getstatuscontainer(numberContainerVariable ContainerManifest.nrcontainer%type,clientID Client.idclient%type) return varchar 
is
messageoutput varchar(100);
typeOfManifest manifest.typemanifest%type;
nameOfThing varchar(100);
typeOfFacility varchar(100);
containerClientId Client.idclient%type;
cont number;
 BEGIN
 SELECT count(*) into cont FROM manifest WHERE idManifest=(SELECT MAX(idmanifest) FROM CONTAINERMANIFEST WHERE nrcontainer = numberContainerVariable);
 messageoutput:= 'container number does not exist';
 IF (cont=0) THEN
 raise_application_error(-20010,'O contentor não existe');
 END IF;

 

SELECT idclient into containerClientID from container where nUMBErcontainer =numberContainerVariable;
IF containerclientid!= clientID THEN
raise_application_error(-20011,'O contentor não pertence a esse cliente');
END IF;
 IF containerclientid= clientID THEN
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
 END IF;

    return messageoutput;
EXCEPTION
 WHEN no_data_found THEN 
  dbms_output.put_line('container number does not exist');
 
end;
/   
 
 
 
 
 Declare
    numberContainer ContainerManifest.nrcontainer%type;
    clientIDtest Client.idclient%type;
Begin
      numbercontainer := 'JORU1234513';
    clientIDtest:=1;
    dbms_output.put_line(func_getstatuscontainer(numbercontainer,clientIDtest));
end;   
/