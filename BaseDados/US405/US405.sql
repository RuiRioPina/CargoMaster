/* Taxa de ocupação por manifesto de um determinado navio durante um período de tempo*/
CREATE OR REPLACE FUNCTION FNC_manifestOccupancyRate(vehicle INTEGER, startDate DATE, endDate DATE)
    RETURN NUMBER IS
    type_manifest       VARCHAR(42);
    nr_manifest         Number;
    ship_capacity       INTEGER;
    cont                INTEGER;
    result              NUMBER(8,3);
    taxa                NUMBER(8,3);


    CURSOR cManifest IS
        WITH idTripTemporario AS (
            SELECT idTrip
            From TripVehicle
            Where idVehicle = vehicle
        )SELECT m.typeManifest
        FROM Manifest m
        INNER JOIN idTripTemporario t ON t.idTrip = m.idTrip
        WHERE TO_CHAR(m.dateManifest) >= TO_CHAR(startDate)
          AND TO_CHAR(m.dateManifest) <= TO_CHAR(endDate);

    CURSOR cNrManifest IS
        WITH idTripTemporario1 AS (
            SELECT idTrip
            From TripVehicle
            Where idVehicle = 1
        )SELECT COUNT(DISTINCT m.idTrip )
        FROM Manifest m
        INNER JOIN idTripTemporario1 t ON m.idTrip = t.idTrip
        WHERE TRUNC(m.dateManifest) >= TRUNC(startDate)
          AND TRUNC(m.dateManifest) <= TRUNC(endDate);

BEGIN

    result:=0.0;
    cont:=0.0;
    OPEN cNrManifest;
    FETCH cNrManifest INTO nr_manifest;

    SELECT capacity INTO ship_capacity
    FROM Ship
    WHERE idVehicle = vehicle;

    OPEN cManifest;
    LOOP
        FETCH cManifest INTO type_manifest;
        exit when cManifest%notfound;

        IF (UPPER(type_manifest) = 'LOAD' ) THEN
            cont:=cont +1;
        ELSE
            cont:=cont -1;
        END IF;

    END LOOP;
    Close cManifest;

    IF(cont=0 OR ship_capacity=0) THEN
        taxa:=0.0;
    ELSE
        result :=cont/ship_capacity;
        taxa:=result/nr_manifest;
    END IF;


    Close cNrManifest;
    RETURN taxa;
END;


begin
    dbms_output.put_line('Taxa de ocupação por manifesto de um determinado navio durante um período de tempo');
    dbms_output.put_line(FNC_manifestOccupancyRate(1,TO_DATE('2003/05/03 10:02:44', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2003/05/11 8:15:30', 'yyyy/mm/dd hh24:mi:ss')));
end;