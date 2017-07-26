package modelsinterface;
import modelsnav.*;
/*
  INTERFACE for a navigatable fix.
                                                                              */
public interface Waypoint {

  /**
  * @return display name
  */
  public String getId();

  /**
  * @return coordinates of waypoint
  */
  public Coordinate getCoordinate();


}
