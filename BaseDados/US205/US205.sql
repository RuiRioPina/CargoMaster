--US205
create or replace function fnc_getContainersToOffload (idShipCap integer, codePort Facility.codeFacility%TYPE)
RETURN SYS_REFCURSOR IS
cursor_containers SYS_REFCURSOR;
nextDate Date;
BEGIN

SELECT MIN(dateManifest) INTO nextDate FROM manifest,TripFacility, Facility, Trip where dateManifest > SYSDATE AND Manifest.typeManifest = 'offload' 
AND Manifest.idTrip = TripFacility.idTrip AND TripFacility.idTrip = Trip.idTrip AND TripFacility.codeFacility = codePort AND Trip.idShipCaptain = idShipCap;

open cursor_containers for 
select container.numberContainer,container.type,container.load, position.x, position.y,position.z, facility.nameFacility, manifest.datemanifest from Container, ContainerManifest ,Position, Manifest,Facility, TripFacility,Trip
WHERE Container.numberContainer LIKE ContainerManifest.nrContainer 
AND ContainerManifest.idPosition = Position.idPosition
AND ContainerManifest.idManifest = Manifest.idManifest
AND Manifest.dateManifest = nextDate
AND Manifest.codeFacility = codePort
AND Manifest.codeFacility = TripFacility.codeFacility
AND TripFacility.idTrip = Manifest.idTrip
AND Facility.codeFacility = TripFacility.codeFacility
AND TripFacility.idTrip = Trip.idTrip
AND Trip.idShipCaptain = idShipCap;
return cursor_containers;
end;
/
--EXECUTION 
SET SERVEROUTPUT ON;
DECLARE
l_containers          sys_refcursor;
v_numberContainer     Container.numberContainer%TYPE; 
v_type                Container.type%TYPE; 
v_load                Container.load%TYPE; 
v_x                   INTEGER; 
v_y                   INTEGER ; 
v_z                   INTEGER;
v_nameFacility        Facility.nameFacility%TYPE;
v_dateManifest        date;
begin
l_containers := fnc_getContainersToOffload(2,3);
loop
fetch l_containers into v_numberContainer, v_type, v_load, v_x, v_y, v_z, v_nameFacility, v_dateManifest;
exit when l_containers%notfound;
dbms_output.put_line('CONTAINER NUMBER - ' || v_numberContainer ||'  TYPE - '|| v_type || '  LOAD - ' || v_load ||'  POSITION (X,Y,Z) - '|| v_x  ||','|| v_y ||','|| v_z ||'   NEXT PORT - '|| v_nameFacility || ' DATE - '||v_dateManifest);
end loop;
end;