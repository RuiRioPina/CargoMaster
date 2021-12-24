create or replace trigger CargoShipAvailability 
before insert or update on Trip for each row

declare
ex_erro EXCEPTION;
bool boolean;
start_date Date;
end_date Date;
id_vehicle trip.startvehicle%type;
total number;
cursor cursors is select distinct startVehicle, startDateTrip, endDateTrip from Trip where startVehicle = :new.startVehicle;

begin
open cursors;
bool := false;
loop
fetch cursors into id_vehicle, start_date, end_date;
select count(startVehicle) into total
from trip 
where startVehicle = id_vehicle
and (:new.startDateTrip between start_date and end_date) or (:new.endDateTrip between start_date and end_date);

if total > 1 then
bool :=true;
end if;

exit when cursors%notfound;
end loop;

if bool = true then
raise ex_erro;
end if;

exception when
ex_erro then
raise_application_error (-20009, 'The ship is not available so it is not possible to register a trip and consenquently a cargo manifest');

end ;
