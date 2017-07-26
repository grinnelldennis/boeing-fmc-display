package modelsnav;
import modelsinterface.Waypoint;;
/*
  Interactive waypoint to encapsulate the use of all other implementation of
   waypoints for the use of FMC & route-keeping.
                                                                              */
class FlightPlanWaypoint {
  final int UNDEFINED = -99999;
  Waypoint waypoint;
  //Calculated
  int passingSpeed;
  int passingAltitude;
  int nextHeading;
  //Restrictions
  boolean hasRestriction;
  int belowSpeed;
  int aboveAltitude;
  int belowAltitude;

  public FlightPlanWaypoint(Waypoint wp) {
    waypoint = wp;
    hasRestriction = false;
    setSpeedRestriction(UNDEFINED);
    setBelowAltitude(UNDEFINED);
    setAboveAltitude(UNDEFINED);
    setBelowSpeed(UNDEFINED);
    setPassingSpeed(UNDEFINED);
    setPassingAltitude(UNDEFINED);
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
    setBelowSpeed(UNDEFINED);
    setBelowAltitude(UNDEFINED);
    setAboveAltitude(UNDEFINED);
  }
  public void setPassingSpeed(int speed) {
    passingSpeed = speed;
  }
  public void setPassingAltitude(int alt) {
    passingAltitude = alt;
  }
  public void setBelowSpeed(int speed) {
    belowSpeed = speed;
  }


}
