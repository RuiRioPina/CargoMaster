CREATE OR REPLACE FUNCTION fnc_getPositionShip(DateTime Date)
RETURN SYS_REFCURSOR IS
PositionSip_NoTrip SYS_REFCURSOR;
BEGIN
OPEN PositionSip_NoTrip FOR
    SELECT ps.mmsi, ps.latitude, ps.longitude
    FROM PositionShip ps
    WHERE ps.baseDateTime =(SELECT MAX(baseDateTime)
                            FROM PositionShip
                            Where mmsi IN (SELECT s.mmsi
                                           FROM Ship s
                                                    INNER JOIN Vehicle v ON v.idVehicle = s.idVehicle
                                           Where s.idVehicle IN (SELECT t.idVehicle
                                                                 From Trip t
                                                                 WHERE TRUNC(t.endDateTrip) < TRUNC(DateTime)
                                           )
                            )
        );
RETURN PositionSip_NoTrip;
END;
/

-- EXECUTION
SET SERVEROUTPUT ON;
DECLARE
    ps_Position     sys_refcursor;
    ps_mmsi         NUMBER;
    ps_latitude     NUMBER;
    ps_longitude    NUMBER;


BEGIN
    ps_Position:=fnc_getPositionShip( NEXT_DAY(SYSDATE, 'SEGUNDA') );
    FETCH ps_Position INTO ps_mmsi, ps_latitude, ps_longitude;
    LOOP
        FETCH ps_Position INTO ps_mmsi, ps_latitude, ps_longitude;
        EXIT WHEN ps_Position%NOTFOUND;
        dbms_output.put_line('mmsi: '|| ps_mmsi || ', Latitude: ' || ps_latitude || ', Longitude: ' || ps_longitude);
    END LOOP;
END;
/
