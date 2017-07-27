package services;

import java.util.ArrayList;

import modelsinterface.Waypoint;
import modelsnav.Airport;
import modelsnav.Procedure;
import modelsnav.FlightPlanWaypoint;

public class FlightPlan {
  Airport origin; 
  Procedure sid;
  Procedure star;
  Airport destination; 
  String flightNo;  
  ArrayList<FlightPlanWaypoint> route;

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
    route.add(new FlightPlanWaypoint(waypoint));
  }
  public void addWaypoint(Waypoint waypoint, int index) {
    if (index < route.size())
      route.add(index, new FlightPlanWaypoint(waypoint));
  }
  
  public String printRoute() {
    String ret = origin.getIcao();
    for (FlightPlanWaypoint fpwp: route) 
      ret = ret + "-> " + fpwp.getWaypointId();
    return ret + "-> " + destination.getIcao();
  }

  
  
}
