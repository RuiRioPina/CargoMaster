create or replace trigger PortFull
before insert or update on MANIFEST
                            for each row

declare
manifest      SYS_REFCURSOR;
    codFacility  INTEGER;
    occupancy    Number(8,3);
    taxa         Number(8,3);

BEGIN

OPEN manifest FOR
SELECT codeFacility
FROM MANIFEST
WHERE UPPER(TYPEMANIFEST) LIKE 'OFFLOAD';

fetch manifest into codFacility;

occupancy := fnc_occupancyRate(codFacility);
    taxa:= 100;

    IF occupancy >= taxa THEN
        raise_application_error (-20007, 'The port is full, you can not create a manifest for the port.');
END IF;

END;