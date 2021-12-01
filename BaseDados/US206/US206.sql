--US206
create or replace function fnc_getContainers (idd integer, dat Date, codeFac Facility.codeFacility%TYPE)
RETURN SYS_REFCURSOR IS
cursor_containers SYS_REFCURSOR;
BEGIN
 open cursor_containers for 
select container.numberContainer,container.type,container.load, position.x, position.y,position.z, facility.namefacility, manifest.datemanifest from Container, ContainerManifest ,Position, Manifest, Facility,TripFacility,Trip, Vehicle, Ship
WHERE Container.numberContainer = ContainerManifest.nrContainer 
AND ContainerManifest.idManifest = Manifest.idManifest
AND ContainerManifest.idPosition = Position.idPosition
AND dat < Manifest.dateManifest 
AND Manifest.typeManifest = 'load' 
AND manifest.idvehicle = Vehicle.idvehicle
AND manifest.idmanifest = facility.idmanifest
AND codeFac = Facility.codeFacility
AND TripFacility.codeFacility = Facility.codeFacility
AND Trip.idTrip = TripFacility.idTrip
AND Vehicle.idvehicle = ship.idvehicle
AND Ship.idShipCaptain = idd;

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
l_containers := fnc_getContainers (1,TO_DATE('2021/12/02 04:34:21', 'yyyy/mm/dd hh24:mi:ss'),3);
loop
fetch l_containers into v_numberContainer, v_type, v_load, v_x, v_y, v_z, v_nameFacility, v_dateManifest;
exit when l_containers%notfound;
dbms_output.put_line('CONTAINER NUMBER - ' || v_numberContainer ||'  TYPE - '|| v_type || '  LOAD - ' || v_load ||'  POSITION (X,Y,Z) - '|| v_x  ||','|| v_y ||','|| v_z ||'   NEXT PORT - '|| v_nameFacility || ' DATE - '||v_dateManifest);
end loop;
end;