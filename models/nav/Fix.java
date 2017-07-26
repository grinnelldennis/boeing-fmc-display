package modelsnav;

import modelsinterface.Waypoint;

/*
  Data structure that represents a defined aeronautical fix, containing all
   information from a single line within the FSX-PMDG navWPT file.
                                                                              */
class Fix implements Waypoint {
  String id;
  Coordinate coord;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Coordinate getCoordinate() {
    return coord;
  }



}
