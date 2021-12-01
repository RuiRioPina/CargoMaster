--US208

create or replace FUNCTION func_getContainersFromCertainManifest(p_mmsi ship.mmsi%type) return integer
is 
    c_count int(3);
begin
SELECT COUNT(*) into c_count FROM( SELECT c.IDMANIFEST
                                             FROM CONTAINERMANIFEST c WHERE
                                                     c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE UPPER(m.TYPEMANIFEST) LIKE 'CARGO' AND m.IDVEHICLE IN (SELECT  t.IDVEHICLE
                                                                                                                                                                        FROM TRIP t INNER JOIN VEHICLE v ON t.IDVEHICLE LIKE v.IDVEHICLE
                                                                                                                                                                                                        WHERE v.IDVEHICLE IN (SELECT IdVehicle FROM SHIP WHERE MMSI = p_mmsi))));
    return c_count;  
end;
/

create or replace FUNCTION func_getCapacityFromGivenShip(p_mmsi ship.mmsi%type) return integer
is 
    capacity int(3);
begin
SELECT CAPACITY into capacity FROM SHIP WHERE MMSI = p_mmsi;                                                                                                                                                                               
    return capacity;  
end;
/


 --execuçao--
 set serveroutput on;
 Declare
    mmsi number;
    number_of_containers number ;
    capacity number;
Begin
   
    mmsi := 123456789;
    number_of_containers := func_getContainersFromCertainManifest(mmsi);
    capacity := func_getCapacityFromGivenShip(mmsi);


    dbms_output.put_line('number of containers= ' || number_of_containers);
    dbms_output.put_line('capacity = ' || capacity);
    dbms_output.put_line('Occupancy rate of the ship on this cargo manifest is : ' || (number_of_containers / capacity) * 100 || '%');
end;   

                                                                                                                            