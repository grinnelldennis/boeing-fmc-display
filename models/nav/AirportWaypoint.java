/*
  Data structure that represents a defined procedural fix (SID/STAR),
   containing all information from a single line within the FSX-PMDG
   Airport-FIX file.
*/
class AirportWaypoint implements Waypoint {

  String id;
  Coordinate coord;

  public NavigationFix(String name, String lat, Stirng lon) {
    this.ident = name;
    this.coord = new Coordinate(lat, lon);
  }


}
