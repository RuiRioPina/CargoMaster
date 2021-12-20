create or replace FUNCTION func_getRouteFromClientContainer(p_nameClient Client.idClient%type, p_numberContainer Container.numberContainer%type) RETURN SYS_REFCURSOR IS
    cursor_route SYS_REFCURSOR;
    numberContainer container.numberContainer%TYPE;
    typeContainer container.type%TYPE;
    loadContainer container.load%TYPE;
    xContainer position.x%TYPE;
    yContainer position.y%TYPE;
    zContainer position.z%TYPE;
    nameFacility facility.nameFacility%TYPE;
    arrival_date tripfacility.arrivalDate%TYPE;
    departure_Date tripfacility.departureDate%TYPE;
begin

    open cursor_route for
        select container.numberContainer,container.type,container.load,manifest.typemanifest ,position.x, position.y,position.z, trip.IDVEHICLE, facility.nameFacility, tripfacility.arrivalDate, tripfacility.departureDate  from Container, ContainerManifest ,Position, Manifest,Facility, TripFacility,Trip
        WHERE Container.numberContainer LIKE p_numberContainer AND Container.idClient LIKE p_nameClient
          AND ContainerManifest.idPosition = Position.idPosition
          AND Container.numberContainer = ContainerManifest.nrContainer
          AND ContainerManifest.idManifest = Manifest.idManifest
          AND Manifest.codeFacility = TripFacility.codeFacility
          AND TripFacility.idTrip = Manifest.idTrip
          AND Facility.codeFacility = TripFacility.codeFacility
          AND TripFacility.idTrip = Trip.idTrip;

    FETCH cursor_route INTO numberContainer, typeContainer, loadContainer, xContainer, yContainer, zContainer, nameFacility, arrival_date, departure_date;
    LOOP

        if cursor_route%notfound then
            raise_application_error(-20005,'O contentor/cliente nao existe ou o cliente nao possui esse contentor');
        else
            return cursor_route;
        end if;
    END LOOP;
end;
/

