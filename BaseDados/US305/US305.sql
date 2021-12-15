/		

create or replace FUNCTION func_getRouteFromClientContainer(p_nameClient Client.nameClient%type, p_numberContainer Container.numberContainer%type) RETURN SYS_REFCURSOR IS
    cursor_route SYS_REFCURSOR;
begin

    open cursor_route for
        select container.numberContainer,container.type,container.load, position.x, position.y,position.z, facility.nameFacility, tripfacility.arrivalDate, tripfacility.departureDate  from Container, ContainerManifest ,Position, Manifest,Facility, TripFacility,Trip
        WHERE Container.numberContainer LIKE p_numberContainer AND Container.idClient LIKE p_nameClient
          AND ContainerManifest.idPosition = Position.idPosition
          AND Container.numberContainer = ContainerManifest.nrContainer
          AND ContainerManifest.idManifest = Manifest.idManifest
          AND Manifest.codeFacility = TripFacility.codeFacility
          AND TripFacility.idTrip = Manifest.idTrip
          AND Facility.codeFacility = TripFacility.codeFacility
          AND TripFacility.idTrip = Trip.idTrip;
    return cursor_route;
end;
/

--EXECUTION 
SET SERVEROUTPUT ON;
DECLARE
    l_containers sys_refcursor;
    v_numberContainer Container.numberContainer%TYPE;
    v_type Container.type%TYPE;
    v_load Container.load%TYPE;
    v_x INTEGER;
    v_y INTEGER ;
    v_z INTEGER;
    v_nameFacility Facility.nameFacility%TYPE;
    v_arrivalDate date;
    v_departureDate  date;
begin
    l_containers := func_getRouteFromClientContainer(1,'ABCU1113456');
    loop
        fetch l_containers into v_numberContainer, v_type, v_load, v_x, v_y, v_z, v_nameFacility, v_arrivalDate, v_departureDate;
        exit when l_containers%notfound;
        dbms_output.put_line('CONTAINER NUMBER - ' || v_numberContainer ||'  TYPE - '|| v_type || '  LOAD - ' || v_load ||'  POSITION (X,Y,Z) - '|| v_x  ||','|| v_y ||','|| v_z ||'   NEXT PORT - '|| v_nameFacility || ' ARRIVAL DATE - '||v_arrivalDate || ' DEPARTURE DATE - '||v_departureDate);
    end loop;
end;

