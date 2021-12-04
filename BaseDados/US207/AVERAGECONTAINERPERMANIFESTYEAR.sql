create or replace FUNCTION func_getContainersFromOneManifest( p_Manifest Manifest.idManifest%type) return integer
is 
    c_count int(3);
begin
    SELECT COUNT(*) into c_count FROM( SELECT c.IDMANIFEST
                                   FROM CONTAINERMANIFEST c WHERE
                                           c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE m.idManifest = p_manifest ));
    return c_count;  
end;
/

create or replace FUNCTION func_getAverageContainerShipCaptainYear(shipCaptainID Worker.idworker%type,yearStart date,yearEnd date) return number
is
averageContainers number;
totalcontainers number;
cont number;
CURSOR c_containers is Select idmanifest from manifest where (datemanifest>=yearStart and datemanifest <= yearEnd)AND typemanifest='load'AND idtrip IN (Select idtrip from trip where idshipcaptain=shipCaptainID);
c manifest.idmanifest%type;
begin
totalcontainers:=0;
cont:=0;
open c_containers;
loop
fetch c_containers into c;
EXIT WHEN c_containers%notFound;
        totalcontainers:= totalcontainers + func_getContainersFromOneManifest(c);
        cont := cont +1;
    end loop;
   averagecontainers:=totalcontainers/cont;
return averagecontainers;
end;
/

 set serveroutput on;
   Declare
    shipcaptainid Worker.idworker%type;
    yearStart Date;
    yearEnd Date;
    averagecontainer number;
Begin
    shipcaptainid:=2;
    yearStart:='2020-01-01';
    yearEnd:='2020-12-31';
    averagecontainer:=func_getAverageContainerShipCaptainYear(shipcaptainid,yearStart,yearEnd);
    dbms_output.put_line(averagecontainer);
   
    
end;   
