--US208

create or replace FUNCTION func_getContainersFromCertainManifest(p_mmsi ship.mmsi%type, p_Manifest ContainerManifest.idManifest%type) return int
    is
    c_count number(3);
    capacity number(3);
begin
    SELECT COUNT(*) into c_count FROM( SELECT c.IDMANIFEST
                                       FROM CONTAINERMANIFEST c WHERE
                                               c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE m.idManifest = p_manifest AND m.IDTRIP IN (SELECT  t.IDTRIP
                                                                                                                                                      FROM TRIP t INNER JOIN VEHICLE v ON t.IDVEHICLE LIKE v.IDVEHICLE
                                                                                                                                                      WHERE v.IDVEHICLE IN (SELECT IdVehicle FROM SHIP WHERE MMSI = p_mmsi))));
    capacity := func_getCapacityFromGivenShip(p_mmsi);
    return (c_count / capacity) * 100;
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




                                                                                                                            