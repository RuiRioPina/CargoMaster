create or replace trigger AuditTrail1 after insert
on ContainerManifest for each row

declare
cargo NUMBER;
begin
select idCargoManifest into cargo from Manifest where Manifest.idManifest = :new.idManifest;
INSERT INTO AuditTrail (registDate, author, action, idCargoManifest, idManifest, nrContainer)
VALUES (SYSDATE,TO_CHAR(USER),'INSERT',cargo,  :new.idManifest, :new.nrContainer);

EXCEPTION
    WHEN NO_DATA_FOUND
    THEN
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20299)));
    WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20298)));
end;
/


create or replace trigger AuditTrail2 before update
on ContainerManifest for each row

declare
cargo NUMBER;
begin
select idCargoManifest into cargo from Manifest where Manifest.idManifest = :new.idManifest;
INSERT INTO AuditTrail (registDate, author, action, idCargoManifest, idManifest, nrContainer)
VALUES (SYSDATE,TO_CHAR(USER),'UPDATE',cargo, :new.idManifest,:new.nrContainer);

EXCEPTION
    WHEN NO_DATA_FOUND
    THEN
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20299)));
    WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20298)));
end;
/

create or replace trigger AuditTrail3 before delete
on ContainerManifest for each row

declare
cargo NUMBER;
begin
select idCargoManifest into cargo from Manifest where Manifest.idManifest = :old.idManifest;
INSERT INTO AuditTrail (registDate, author, action, idCargoManifest, idManifest, nrContainer)
VALUES (SYSDATE,TO_CHAR(USER),'DELETE',cargo, :new.idManifest,:old.nrContainer);

EXCEPTION
    WHEN NO_DATA_FOUND
    THEN
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20299)));
    WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(TO_CHAR(SQLERRM(-20298)));
end;


create or replace function fnc_getAudit (idCargo Number, container AuditTrail.nrContainer%TYPE)
RETURN SYS_REFCURSOR IS
cursor_audit SYS_REFCURSOR;

begin
open cursor_audit for
select * from AuditTrail where idCargoManifest = idCargo AND nrContainer = container;

return cursor_audit;
end;

/
--EXECUTION 
SET SERVEROUTPUT ON;
DECLARE
cursor_audit          sys_refcursor;
Autor  AuditTrail.Author%TYPE; 
nrContainer AuditTrail.nrContainer%TYPE;  
idCargoManifest NUMBER;
idManifest NUMBER;
idAudit NUMBER;
action AuditTrail.action%TYPE;
dataAtual DATE;
begin
cursor_audit := fnc_getAudit(4,'JORU1234563');
loop
fetch cursor_audit into idAudit, dataAtual, autor, action, idCargoManifest, idManifest, nrContainer;
exit when cursor_audit%notfound;
dbms_output.put_line('ID - ' || idAudit || ' AUTHOR - ' || Autor ||'  DATE - '|| dataAtual || '  ACTION - ' || action ||'  CONTAINER - ' || nrContainer ||'  ID CARGO - ' || idCargoManifest || ' ID MANIFEST - ' || idmanifest );
end loop;
end;