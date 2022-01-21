/*Viagens de barco acabadas com taxa menor que 66 */
CREATE OR REPLACE PROCEDURE PROC_manifestOccupancySmaller
IS

    idVehicle   INTEGER;
    taxa        NUMBER;
    startDate   DATE;
    endDate     Date;
    startPort   INTEGER;
    endPort     INTEGER;

    CURSOR cTripVehicle IS
        WITH idTripTemporario AS (
            SELECT tr.idTrip, tr.idVehicle
            FROM TripVehicle tr
            INNER JOIN Ship s ON tr.idVehicle = s.idVehicle
        )SELECT t.startDateTrip ,it.idVehicle, t.endDateTrip, t.startFacility, t.endFacility
        FROM Trip t
        INNER JOIN idTripTemporario it ON t.idTrip = it.idTrip
        WHERE TRUNC(t.endDateTrip)<= TRUNC(SYSDATE);

BEGIN

    OPEN cTripVehicle;
    LOOP
        FETCH cTripVehicle INTO startDate, idVehicle, endDate, startPort, endPort;
        exit when cTripVehicle%notfound;
        taxa:=FNC_manifestOccupancyRate(idVehicle,startDate,endDate);
        IF(taxa<66) THEN
            dbms_output.put_line('Viaguens de barco acabadas com taxa menor que 66');
            dbms_output.put_line('Local de partida e hora '|| startPort ||', '|| startDate);
            dbms_output.put_line('Local de chegada e hora '|| endPort ||', '|| endDate );
            dbms_output.put_line('---------------------------------------------------');
        END IF;
    END LOOP;
    Close cTripVehicle;

END PROC_manifestOccupancySmaller;

begin
    PROC_manifestOccupancySmaller;
end;