import java.util.ArrayList;

class FlightPlan {
  //turn into type-Airport
  Airport origin;  //ORGNARPT, ICAO
  Airport destination; //DESTARPT, ICAO
  String flightNo;  //FLTNON
  ArrayList<Navaid> route;

  public FlightPlan() {

  }

  public void setOrigin(Airport icao) { origin = icao; }
  public void setDestination(Airport icao) { destination = icao; }
  public void setFlightNumber(String s) { flightNo = s; }

  public void addWaypoint(Navaid waypoint) {}
  public void addWaypoint() {}

    //TODO: Route Object
  }

}
