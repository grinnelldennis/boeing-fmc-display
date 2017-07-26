package modelsnav;
import modelsinterface.Waypoint;
/*
  Data structure that represents a defined procedural fix (SID/STAR),
   containing all information from a single line within the FSX-PMDG
   Airport-FIX file.
*/
class AirportWaypoint implements Waypoint {

  String id;
  Coordinate coord;

  public AirportWaypoint(String name, String lat, String lon) {
    this.id = name;
    this.coord = new Coordinate(lat, lon);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Coordinate getCoordinate() {
    return coord;
  }


}
