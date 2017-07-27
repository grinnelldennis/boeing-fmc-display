package modelsnav;

import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
public class Fix implements Waypoint {
  String id;
  Coordinate coord;

  public Fix(String id, String lat, String lon) {
    this.id = id;
    this.coord = new Coordinate(lat, lon);
  }
  
  /**
   * Constructor for creating a fix from PMDG wpNavFIX file
   * @param s, a single line from the file
   */
  public Fix(String s) {
    this(s.substring(0, s.indexOf(" ")), 
        s.substring(30, 40), s.substring(40, 51));
  }
  
  @Override
  public String getId() {
    return id;
  }

  @Override
  public Coordinate getCoordinate() {
    return coord;
  }

  @Override
  public String getInfo() {
    return id + " " + coord.getLatNSDot() + " " + coord.getLonEWDot();
  }



}
