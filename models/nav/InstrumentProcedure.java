import java.util.ArrayList;
import java.util.HashMap;

/*
  Represents a single SID/STAR procedure
*/
class InstrumentProcedure {

  String ident;   //Procedure Name
  String type;    //SID/STAR
  ArrayList<ProcedureFix> baseProcedure;
  HashMap<String, ArrayList<ProcedureFix>> runwayProcedures;
  HashMap<String, ArrayList<ProcedureFix>> transitions;

  public InstrumentProcedure(String ident, String type) {
    this.ident = ident;
    this.type = type;
    baseProcedure = new ArrayList<>();
    runwayProcedures = new HashMap<>();
    transitions = new HashMap<>();
  }

  public void setBaseProcedure(ArrayList<ProcedureFix> proc) {
    this.baseProcedure = proc;
  }

  public void addBaseProcedureFix(ProcedureFix fixProc) {
    this.baseProcedure.add(fixProc);
  }

  public void setProcedure(String type, String id, ArrayList<ProcedureFix> proc) {
	  if (type.equals("Transition"))
	    addTransition(id, proc);
	  else if (type.equals("Runway"))
	    addRunwayProcedure(id, proc);
  }

  public void addRunwayProcedure(String id, ArrayList<ProcedureFix> runwayProc) {
    this.runwayProcedures.put(id, runwayProc);
  }

  public void addTransition(String id, ArrayList<ProcedureFix> transition) {
    this.transitions.put(id, transition);
  }

}
