/* Validando o facto de nao se introduzir um container num ship com a capacidade excedida */
create or replace trigger test_number_containers_not_exceed_capacity
    before insert or update on ContainerManifest for each row
declare
    number_of_containers number(5);
    capacity number(11);
    mmsi number(11);
    ex_erro EXCEPTION;
    id_Manifest ContainerManifest.idManifest%type;
    cursor cursor_manifest is
        SELECT distinct c.IDMANIFEST
        FROM CONTAINERMANIFEST c WHERE
                c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE m.idManifest = :new.idManifest AND m.IDTRIP IN (SELECT  t.IDTRIP
                                                                                                                            FROM TRIP t INNER JOIN VEHICLE v ON t.IDVEHICLE LIKE v.IDVEHICLE
                                                                                                                            WHERE v.IDVEHICLE IN (SELECT IdVehicle FROM SHIP)));
    cursor cursor_mmsi is SELECT mmsi FROM SHIP WHERE idVehicle IN (SELECT v.IdVehicle from Vehicle v where IDVEHICLE = (SELECT t.IDVEHICLE from Trip t where t.IdTrip = (SELECT m.idTrip FROM MANIFEST m WHERE m.IDMANIFEST = id_Manifest)));

begin

    fetch cursor_manifest into id_Manifest;
    fetch cursor_mmsi into mmsi;
    number_of_containers := func_getcontainersfromonemanifest(id_Manifest);
    capacity := func_getcapacityfromgivenship(mmsi);
    if capacity <= number_of_containers then
        raise_application_error(-20009,'TENTOU INTRODUZIR MAIS CONTENTORES DO QUE A CAPACIDADE DO NAVIO');
    end if;
end;
