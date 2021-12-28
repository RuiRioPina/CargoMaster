create or replace trigger PortFull
before insert on MANIFEST
for each row

declare
    ex_erro      EXCEPTION;
    occupancy    SYS_REFCURSOR;
    offload      SYS_REFCURSOR;
    load         SYS_REFCURSOR;
    valor        BOOLEAN;
    cont         INTEGER:=0;
    dates        Date;
    o            Number;
    l            Number;
    id_Facility  Facility.CODEFACILITY%type;
    id           NUMBER;
    max_capacity Number;
    CURSOR       cursor_MANIFEST IS SELECT distinct idManifest, dateManifest
                                    FROM MANIFEST
                                    WHERE UPPER(TYPEMANIFEST) LIKE 'OFFLOAD';

BEGIN

    OPEN cursor_MANIFEST;
    FETCH cursor_MANIFEST INTO id, dates;
    valor:=false;

    OPEN occupancy FOR
        SELECT f.codeFacility, capacity
        FROM Facility f
        where f.codeFacility IN (SELECT m.codeFacility
                                FROM MANIFEST m
                                WHERE idManifest = id);


    OPEN offload FOR
        SELECT m.IDMANIFEST
        FROM MANIFEST m
        WHERE UPPER(m.TYPEMANIFEST) LIKE 'OFFLOAD'
            AND TRUNC(dateManifest) <= TRUNC(dates)
            AND idManifest = id;


    OPEN load FOR
        SELECT m.IDMANIFEST
        FROM MANIFEST m
        WHERE UPPER(m.TYPEMANIFEST) LIKE 'LOAD'
            AND TRUNC(dateManifest) <= TRUNC(dates)
            AND idManifest = id;

    LOOP
        fetch occupancy     into id_Facility, max_capacity;
        fetch offload       into o;
        fetch load          into l;


        IF(id LIKE o) THEN
            cont:=cont + 1;
        END IF;

        IF(id LIKE l) THEN
            cont:=cont - 1;
        END IF;

        exit when cursor_MANIFEST%notfound;
    END LOOP;

    IF cont>=max_capacity THEN
        valor:=true;
    END IF;

    IF valor = true THEN
        raise ex_erro;
    END IF;

    EXCEPTION WHEN
    ex_erro then
    raise_application_error (-20005, 'The port is full, you can not create a manifest for the port.');

END PortFull;