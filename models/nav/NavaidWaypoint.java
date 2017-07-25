/*
  Data structure that represents a defined aeronautical navaid, containing all
   information from a single line within the FSX-PMDG navNAV file.
                                                                              */
class NavaidWaypoint implements Waypoint{

  String id;
  Coordinate coord;
  //Extra Information
  String brief;
  String type;    // ADF/DME/VOR/NDB/
  double freq;
  char designator;

  public Navaid (String i, String b, String t, double f, double lat, double lon) {
    this.name = i;
    coord = new Coordinate(lat, lon);
    this.brief = b;
    this.type = t;
    this.freq = f;
  }
  public boolean hasRestrictions() {
    return false;
  }


}
