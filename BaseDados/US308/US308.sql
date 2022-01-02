create or replace trigger test_number_containers_not_exceed_capacity
    before insert or update on ContainerManifest for each row
declare
    number_of_containers number(5);
    capacity number(11);
    p_mmsi number(11);
    ex_erro EXCEPTION;


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