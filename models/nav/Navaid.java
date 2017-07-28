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
    this.coord = new Coordinate(lat, lon);
    this.brief = b;
    this.type = t;
    this.freq = Double.parseDouble(f);
  }
  
  /**
   * Constructor for creating a navaid from PMDG wpNavAID file
   * @param s, a single line from the file
   */
  public Navaid (String s) {
    this(s.substring(24, s.indexOf(" ", 24)), // Identifier, 5 characters-long
        s.substring(0, 24), // Brief, 24 characters-long
        s.substring(29, 33), // Type, 4 characters-long
        s.substring(54, s.length()-1), // Frequency, 6 characters-long
        s.charAt(s.length()-1),
        s.substring(33, 43), // Latitude, 10 characters-long
        s.substring(43, 54)); // Longitude, 11 characters-long
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
    return id + " " + coord.getLatNSDot() + " " + coord.getLonEWDot() + " " + type + " " + freq;
  }
  
  @Override
  public String toString() {
    return getInfo();
  }

  @Override
  public boolean isAVector() {
    return false;
  }
}
