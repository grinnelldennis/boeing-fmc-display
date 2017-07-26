package modelsnav;
import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical navaid, containing all
   information from a single line within the FSX-PMDG navNAV file.
                                                                              */
public class Navaid implements Waypoint{

  String id;
  Coordinate coord;
  //Extra Information
  String brief;
  String type;    // ADF/DME/VOR/NDB/
  double freq;
  char designator;

  public Navaid (String i, String b, String t, String f, char d, String lat, String lon) {
    this.id = i;
    coord = new Coordinate(lat, lon);
    this.brief = b;
    this.type = t;
    this.freq = Double.parseDouble(f);
  }
  
  public boolean hasRestrictions() {
    return false;
  }
  @Override
  public String getId() {
    return id;
  }
  @Override
  public Coordinate getCoordinate() {
    return coord;
  }
  
  public String getInfo() {
    return id + coord.getLatNSDot() + coord.getLonEWDot() + type + freq;
  }
}
