CREATE OR REPLACE function shipCaptainManifestYear(shipCaptainID Worker.idworker%type,yearStart date,yearEnd date) return integer
 is
 numberOfManifests INTEGER;

 BEGIN
 Select count(*)into numberOfManifests from manifest where (datemanifest>=yearStart and datemanifest <= yearEnd)AND typemanifest='load'AND idtrip IN (Select idtrip from trip where idshipcaptain=shipCaptainID);
 return numberOfManifests;
 end;
/
 Declare
    shipcaptainid Worker.idworker%type;
    yearStart Date;
    yearEnd Date;
Begin
    shipcaptainid:=2;
    yearStart:='2020-01-01';
    yearEnd:='2020-12-31';
    dbms_output.put_line(shipCaptainManifestYear(shipcaptainid,yearStart,yearEnd));
end;   
/