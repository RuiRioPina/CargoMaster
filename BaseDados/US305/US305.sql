create FUNCTION func_getRouteFromClientContainer(p_nameClient Client.idClient%type, p_numberContainer Container.numberContainer%type) RETURN SYS_REFCURSOR IS
    cursor_route SYS_REFCURSOR;
    typeContainer container.type%TYPE;
    loadContainer container.load%TYPE;
    typeManifest manifest.typemanifest%TYPE;
    xContainer position.x%TYPE;
    yContainer position.y%TYPE;
    zContainer position.z%TYPE;
    idVehicle   tripVehicle.idVehicle%TYPE;
    nameFacility facility.nameFacility%TYPE;
    arrival_date tripfacility.arrivalDate%TYPE;
    departure_Date tripfacility.departureDate%TYPE;
    contagem number;
begin

    open cursor_route for
        select container.type,container.load,manifest.typemanifest ,position.x, position.y,position.z, tripVehicle.idVehicle, facility.nameFacility, tripfacility.arrivalDate, tripfacility.departureDate  from Container, ContainerManifest ,Position, Manifest,Facility, TripFacility,TripVehicle, Truck, Ship, Trip
        WHERE Container.numberContainer LIKE p_numberContainer AND Container.idClient LIKE p_nameClient
          AND ContainerManifest.idPosition = Position.idPosition
          AND Container.numberContainer = ContainerManifest.nrContainer
          AND ContainerManifest.idManifest = Manifest.idManifest
          AND Manifest.codeFacility = TripFacility.codeFacility
          AND Trip.idTrip = TripVehicle.idTrip
          AND (TripVehicle.idVehicle = Truck.idVehicle
            OR TripVehicle.idVehicle = Ship.idVehicle)
          AND TripFacility.idTrip = Manifest.idTrip
          AND Facility.codeFacility = TripFacility.codeFacility
          AND TripFacility.idTrip = Trip.idTrip;
          
    SELECT COUNT(1) into contagem from Container, ContainerManifest ,Position, Manifest,Facility, TripFacility,TripVehicle, Truck, Ship, Trip
    WHERE Container.numberContainer LIKE p_numberContainer AND Container.idClient LIKE p_nameClient
      AND ContainerManifest.idPosition = Position.idPosition
      AND Container.numberContainer = ContainerManifest.nrContainer
      AND ContainerManifest.idManifest = Manifest.idManifest
      AND Manifest.codeFacility = TripFacility.codeFacility
      AND Trip.idTrip = TripVehicle.idTrip
      AND (TripVehicle.idVehicle = Truck.idVehicle
        OR TripVehicle.idVehicle = Ship.idVehicle)
      AND TripFacility.idTrip = Manifest.idTrip
      AND Facility.codeFacility = TripFacility.codeFacility
      AND TripFacility.idTrip = Trip.idTrip;
    if (contagem = 0) then
        raise_application_error(-20005,'O contentor/cliente nao existe ou o cliente nao possui esse contentor');
    end if;

    return cursor_route;
end;
/

