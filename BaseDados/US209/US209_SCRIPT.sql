create FUNCTION func_getContainersFromCertainDate(p_mmsi ship.mmsi%type, p_departureDate TripFacility.departureDate%type) return INTEGER IS
    manifest SYS_REFCURSOR;
    unload SYS_REFCURSOR;
    load SYS_REFCURSOR;
    number_of_containers number := 0;
    u                 number;
    l                 number;
    c                 number;
    capacity number;
    res number :=0;
begin

    open manifest for
        SELECT c.IDMANIFEST
        FROM CONTAINERMANIFEST c WHERE
                c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE m.IDTRIP IN (SELECT  t.IDTRIP
                                                                                         FROM TRIPFACILITY t WHERE t.DEPARTUREDATE < p_departureDate));

    capacity := func_getCapacityFromGivenShip(p_mmsi);
    open unload for
        SELECT c.IDMANIFEST

        FROM CONTAINERMANIFEST c WHERE
                c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE UPPER(m.TYPEMANIFEST) LIKE 'UNLOAD');

    open load for
        SELECT c.IDMANIFEST

        FROM CONTAINERMANIFEST c WHERE
                c.IDMANIFEST IN ( SELECT m.IDMANIFEST FROM MANIFEST m WHERE UPPER(m.TYPEMANIFEST) LIKE 'LOAD');


    loop
        fetch manifest into c;
        fetch unload into u;
        fetch load into l;


        exit when manifest%notfound;
        IF(c LIKE u)

        THEN
            res:= res - func_getcontainersfromcertainmanifest(p_mmsi, c);
        END IF;

        IF(c LIKE l)
        THEN
            res:= res + func_getcontainersfromcertainmanifest(p_mmsi, c);
        END IF;


    end loop;

    return res;
end;
/

