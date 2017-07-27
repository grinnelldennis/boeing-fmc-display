package modelsnav;

import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
public class Track implements Waypoint {
  String track;

  public Track(String id, String lat, String lon) {
    this.track = id;
  }
  
  /**
   * Constructor for creating a fix from PMDG wpNavFIX file
   * @param s, a single line from the file
   */
  public Track(String s) {
    this.track = s;
  }
  
  @Override
  public String getId() {
    return track;
  }

  @Override
  public Coordinate getCoordinate() {
    return null;
  }

  @Override
  public String getInfo() {
    return "TRK " + track;
  }

  @Override
  public String toString() {
    return getInfo();
  }
  
  @Override
  public boolean isAVector() {
    return true;
  }



}
