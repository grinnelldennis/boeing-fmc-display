package modelsnav;

import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
public class Intercept implements Waypoint {
  String radial;
  
  public Intercept(String s) {
    this.radial = s;
  }
  
  @Override
  public String getId() {
    return radial;
  }

  @Override
  public Coordinate getCoordinate() {
    return null;
  }

  @Override
  public String getInfo() {
    return "RAD " + radial;
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
