package modelsnav;

import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
public class Heading implements Waypoint {
  int heading;

  public Heading(String heading) {
    this(Integer.parseInt(heading));
  }
  
  /**
   * Constructor for creating a fix from PMDG wpNavFIX file
   * @param s, a single line from the file
   */
  public Heading(int heading) {
    this.heading = heading;
  }
  
  @Override
  public String getId() {
    return ""+heading;
  }

  @Override
  public Coordinate getCoordinate() {
    return null;
  }

  @Override
  public String getInfo() {
    return "HDG " + getId();
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
