
/*taxa de ocupação do armazem*/
create or replace FUNCTION fnc_occupancyRate(codFacility INTEGER)
RETURN NUMBER IS
    manifest      SYS_REFCURSOR;
    occupancyRate SYS_REFCURSOR;
    offload       SYS_REFCURSOR;
    load          SYS_REFCURSOR;
    cont          INTEGER:=0;
    Taxas         Number(8,3);
    oc            INTEGER;
    o             Number;
    l             Number;
    ma            Number;

BEGIN
    OPEN occupancyRate FOR
        SELECT capacity
        FROM Facility
        where codeFacility=codFacility;


    OPEN manifest FOR
         SELECT m.idManifest
         FROM MANIFEST m
         WHERE TRUNC(m.dateManifest) <= TRUNC(SYSDATE)
            AND m.codeFacility=codFacility;


    OPEN offload FOR
        SELECT mo.IDMANIFEST
        FROM MANIFEST mo
        WHERE UPPER(mo.TYPEMANIFEST) LIKE 'OFFLOAD'
            AND TRUNC(mo.dateManifest) <= TRUNC(SYSDATE)
            AND mo.codeFacility=codFacility;


    OPEN load FOR
         SELECT ml.IDMANIFEST
         FROM MANIFEST ml
         WHERE UPPER(ml.TYPEMANIFEST) LIKE 'LOAD'
            AND TRUNC(ml.dateManifest) <= TRUNC(SYSDATE)
            AND ml.codeFacility=codFacility;

    LOOP
        fetch occupancyRate into oc;
        fetch manifest      into ma;
        fetch offload       into o;
        fetch load          into l;

        exit when manifest%notfound;
        IF(ma LIKE o) THEN
            cont:=cont + 1;
        END IF;

        IF(ma LIKE l) THEN
            cont:=cont - 1;
        END IF;

    END LOOP;
    Taxas := (cont*100)/oc;

RETURN Taxas ;
END;


-- EXECUTION
SET SERVEROUTPUT ON;
begin
    dbms_output.put_line(fnc_occupancyRate(3));
end;



--Estimativa dos contentores que saem do armazem nos proximos 30 dias
create or replace FUNCTION fnc_offcontainer(codFacility INTEGER)
RETURN INTEGER IS
    manifest      SYS_REFCURSOR;
    load          SYS_REFCURSOR;
    cont          INTEGER:=0;
    l             Number;
    ma            Number;

BEGIN
OPEN manifest FOR
    SELECT m.IDMANIFEST
    FROM MANIFEST m
    WHERE TRUNC(m.dateManifest) >= TRUNC(SYSDATE)
        AND TRUNC(m.dateManifest) <= TRUNC(SYSDATE+30)
        AND m.codeFacility=codFacility;

OPEN load FOR
    SELECT ml.IDMANIFEST
    FROM MANIFEST ml
    WHERE UPPER(ml.TYPEMANIFEST) LIKE 'LOAD'
        AND TRUNC(ml.dateManifest) >= TRUNC(SYSDATE)
        AND TRUNC(ml.dateManifest) <= TRUNC(SYSDATE+30)
        AND ml.codeFacility=codFacility;

LOOP
    fetch manifest into ma;
    fetch load     into l;

    exit when manifest%notfound;

    IF(ma LIKE l) THEN
        cont:=cont + 1;
    END IF;

END LOOP;

RETURN cont;
END;

-- EXECUTION
SET SERVEROUTPUT ON;
begin
    dbms_output.put_line(fnc_offcontainer(2));
end;