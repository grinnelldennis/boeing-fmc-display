package modelsnav;

import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
public class Vectors implements Waypoint {

    
  @Override
  public String getId() {
    return "VECTORS";
  }

  @Override
  public Coordinate getCoordinate() {
    return null;
  }

  @Override
  public String getInfo() {
    return getId();
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
