CREATE OR REPLACE FUNCTION FNC_manifestOccupancySmaller1
RETURN VARCHAR2 IS
    idVehicle   INTEGER;
    taxa        NUMBER;
    startDate   DATE;
    endDate     Date;
    startPort   INTEGER;
    endPort     INTEGER;
    ILevel      varchar2(32767);

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
            ILevel:= 'output: ' || 'Local de partida e hora '|| startPort ||', '|| startDate || ' Local de chegada e hora '|| endPort ||', '|| endDate;
END IF;
END LOOP;
Close cTripVehicle;

RETURN ILevel;
END;

DECLARE
result varchar2(32767);
begin
   result:= FNC_manifestOccupancySmaller1;
   DBMS_OUTPUT.PUT_LINE(result);
end;