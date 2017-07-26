package services;

import java.util.ArrayList;

import modelsinterface.Waypoint;
import modelsnav.Airport;

public class FlightPlan {
  Airport origin;  //ORGNARPT, ICAO
  Airport destination; //DESTARPT, ICAO
  String flightNo;  //FLTNON
  ArrayList<Waypoint> route;

  public FlightPlan() {
    origin = null;
    destination = null;
    flightNo = null;
    route = new ArrayList<>();
  }

  public void setOrigin(Airport airport) { 
    origin = airport; 
  }
  public void setDestination(Airport airport) { 
    destination = airport; 
  }
  public void setFlightNumber(String s) { 
    flightNo = s; 
  }

  public void addWaypoint(Waypoint waypoint) {
    route.add(waypoint);
  }
  public void addWaypoint(Waypoint waypoint, int index) {
    if (index < route.size())
      route.add(index, waypoint);
  }
  
  public String printRoute() {
    String ret = origin.getIcao();
    for (Waypoint wp: route) 
      ret = ret + "-> " + wp.getId();
    return ret + "-> " + destination.getIcao();
  }

  
  
}
