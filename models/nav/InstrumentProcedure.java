package modelsnav;

import java.util.ArrayList;
import java.util.HashMap;

/*
  Represents a single instrument procedure, i.e. SID and STAR
*/
class Procedure {

  String id;   //Procedure Name
  String type;    //SID/STAR
  ArrayList<FlightPlanWaypoint> baseProcedure;
  HashMap<String, ArrayList<FlightPlanWaypoint>> runwayProcedures;
  HashMap<String, ArrayList<FlightPlanWaypoint>> transitions;

  public Procedure(String ident, String type) {
    this.id = ident;
    this.type = type;
    baseProcedure = new ArrayList<>();
    runwayProcedures = new HashMap<>();
    transitions = new HashMap<>();
  }

  public void setBaseProcedure(ArrayList<FlightPlanWaypoint> proc) {
    this.baseProcedure = proc;
  }

  public void addBaseFlightPlanWaypoint(FlightPlanWaypoint fixProc) {
    this.baseProcedure.add(fixProc);
  }

  public void setProcedure(String type, String id, ArrayList<FlightPlanWaypoint> proc) {
	  if (type.equals("Transition"))
	    addTransition(id, proc);
	  else if (type.equals("Runway"))
	    addRunwayProcedure(id, proc);
  }

  public void addRunwayProcedure(String id, ArrayList<FlightPlanWaypoint> runwayProc) {
    this.runwayProcedures.put(id, runwayProc);
  }

  public void addTransition(String id, ArrayList<FlightPlanWaypoint> transition) {
    this.transitions.put(id, transition);
  }

}
