create or replace PROCEDURE proc_occupancyRateShips(codefacilityUse Facility.codefacility%type,monthStart date, monthEnd date) AS
start_date date;
  end_date date;
  occupRate number;
 shipslots number;
  
BEGIN
start_date := monthStart;
  end_date := monthEnd;
  SELECT dockingslots into shipslots from facility where codefacility=codefacilityuse;
  While start_date <= end_date
  loop
  SELECT COUNT(*)into occuprate from tripfacility where codefacility=codeFacilityUse and start_date between arrivaldate AND DEPARTUREDATE;
  occuprate:= (occuprate/shipslots)*100;
  
   dbms_output.put_line(start_date);
  dbms_output.put_line(occuprate);
  start_date:= start_date+1;
  end loop;
  end; 
  /
  declare
firstday date;
facCode FACILITY.codefacility%type;

lastday date;
begin
facCode:=29002;
firstday:= '2022-06-01';
lastday:= '2022-06-30';
 proc_occupancyRateShips(facCode,firstday,lastday);
end;
/
create or replace PROCEDURE proc_occupancyRateContainers(codefacilityUse Facility.codefacility%type,monthStart date, monthEnd date) AS
start_date date;
  end_date date;
  occupRate number;
 containerslots number;
  
BEGIN
start_date := monthStart;
  end_date := monthEnd;
  SELECT capacity into containerslots from facility where codefacility=codefacilityuse;
  While start_date <= end_date
  loop
  occuprate:= func_getcontainersbeforedate(codefacilityuse,start_date);
  occuprate:= (occuprate/containerslots)*100;
  
   dbms_output.put_line(start_date);
  dbms_output.put_line(occuprate);
  start_date:= start_date+1;
  end loop;
  end; 
  /
  declare
firstday date;
facCode FACILITY.codefacility%type;

lastday date;
begin
facCode:=29002;
firstday:= '2022-06-01';
lastday:= '2022-06-30';
proc_occupancyRateContainers(facCode,firstday,lastday);
end;