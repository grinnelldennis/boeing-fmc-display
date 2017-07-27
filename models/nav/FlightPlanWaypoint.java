package modelsnav;
import modelsinterface.Waypoint;;
/*
  Interactive waypoint to encapsulate the use of all other implementation of
   waypoints for the use of FMC & route-keeping.
                                                                              */
public class FlightPlanWaypoint {
  final int UNDEFINED = -99999;
  Waypoint waypoint;
  //Calculated
  int passingSpeed;
  int passingAltitude;
  int nextHeading;
  //Restrictions
  boolean hasRestriction;
  int restrictSpeed;
  int aboveAltitude;
  int belowAltitude;
  int until;

  public FlightPlanWaypoint(Waypoint wp) {
    waypoint = wp;
    hasRestriction = false;
    setSpeedRestriction(UNDEFINED);
    setBelowAltitude(UNDEFINED);
    setAboveAltitude(UNDEFINED);
    setRestrictSpeed(UNDEFINED);
    setPassingSpeed(UNDEFINED);
    setPassingAltitude(UNDEFINED);
    setUntilAltitude(UNDEFINED);
  }

  /*
      Setter Methods
                      */
  public void setSpeedRestriction(int speed) {
    passingSpeed = speed;
    hasRestriction = true;
  }
  public void setBelowAltitude(int alt) {
    belowAltitude = alt;
    hasRestriction = true;
  }
  public void setAboveAltitude(int alt) {
    aboveAltitude = alt;
    hasRestriction = true;
  }
  public void clearRestrictions() {
    setRestrictSpeed(UNDEFINED);
    setBelowAltitude(UNDEFINED);
    setAboveAltitude(UNDEFINED);
  }
  public void setUntilAltitude(int until) {
    this.until = until;
  }

  public void setPassingSpeed(int speed) {
    passingSpeed = speed;
  }
  public void setPassingAltitude(int alt) {
    passingAltitude = alt;
  }
  public void setRestrictSpeed(int speed) {
    restrictSpeed = speed;
  }
  
  /*
      Getter Methods
   */ 
  public String getWaypointId() {
    return waypoint.getId();
  }
  public int getUntilAltitude() {
    return until;
  }
  public boolean isAVector() {
    return waypoint.isAVector();
  }


}
