/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
class FixWaypoint implements Waypoint {
  String id;
  Coordinate coord;

  public boolean hasRestrictions() {
    return false;
  }



}
