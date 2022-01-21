create or replace function fnc_shipIdleDays (fleetId Integer, mmsiEntered String)
RETURN INTEGER is
total INTEGER;
totalDays number;
v_mmsi ship.mmsi%TYPE;
v_days number;
v_startDate Date;
v_endDate Date;
v_firstDay Date;
v_lastDay Date;

cursor c_cursors is select  mmsi, endDateTrip - startDateTrip DAYS, endDateTrip, startDateTrip  from (
select mmsi,startvehicle idVehicle, startDateTrip, endDateTrip from Trip ,Ship WHERE Ship.idVehicle = Trip.startVehicle
AND endDateTrip > trunc(sysdate,'YEAR') AND startDateTrip < LAST_DAY (ADD_MONTHS (TRUNC (SYSDATE, 'YEAR'), 11)) AND mmsi = mmsiEntered AND
FleetManagerNrIdentification = fleetId);

begin
total := 0;
select trunc(sysdate,'YEAR') into v_firstDay from dual;
select LAST_DAY (ADD_MONTHS (TRUNC (SYSDATE, 'YEAR'), 11)) into v_lastDay FROM DUAL;
select to_date('01-JAN-'||to_char(to_number(to_char(sysdate,'YYYY'))+1),'DD-MM-YYYY')-trunc(sysdate,'YYYY') into totalDays from dual;
open c_cursors;
loop
fetch c_cursors into v_mmsi, v_days, v_enddate, v_startDate;
exit when c_cursors%notfound;

if (v_startDate between v_firstDay and v_lastDay) and (v_endDate between v_firstDay and v_lastDay) then
total := total + 1+ v_days; 
end if;

if v_startDate > v_firstDay  AND v_endDate > v_lastDay then
total := total + 1 +(v_lastDay - v_startDate);
end if;

if v_startDate < v_firstDay  AND v_endDate < v_lastDay then
total := total + 1 + (v_endDate - v_firstday);
end if;

if v_startDate < v_firstDay  AND v_endDate > v_lastDay then
total := total + totalDays;
end if;

end loop;

return totalDays - total;
end;

/
create or replace function fnc_getMmsi (fleetId Integer)
return sys_refcursor is
c_cursor sys_refcursor;

begin
open c_cursor for select DISTINCT mmsi  from Trip ,Ship WHERE Ship.idVehicle = Trip.startVehicle
AND endDateTrip > trunc(sysdate,'YEAR') AND startDateTrip < LAST_DAY (ADD_MONTHS (TRUNC (SYSDATE, 'YEAR'), 11)) AND
FleetManagerNrIdentification = fleetId;

return c_cursor;
end;

/
SET SERVEROUTPUT ON;
DECLARE
v_mmsi ship.mmsi%TYPE; 
c_cursor sys_refcursor;

begin
c_cursor := fnc_getMmsi(459);
loop
fetch c_cursor into v_mmsi;
exit when c_cursor%notfound;
dbms_output.put_line('MMSI - ' || v_mmsi ||'  IDLE TIME - '||fnc_shipIdleDays(459,v_mmsi) ||' DAYS');
end loop;
end;
