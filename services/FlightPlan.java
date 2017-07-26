package services;

import java.util.ArrayList;
import modelsnav.Airport;
import modelsnav.Navaid;

public class FlightPlan {
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

  public void addWaypoint(Navaid waypoint) {
    //TODO: STUB
  }
  public void addWaypoint() {
    //TODO: STUB
    }

  
  
}
