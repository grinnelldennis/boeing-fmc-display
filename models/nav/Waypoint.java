/*
  INTERFACE for a navigatable fix.
                                                                              */
interface Waypoint {
  /**
  * @param ac, the aircraft
  * @return whether aircraft's has cleared the waypoint
  */
  public boolean isCleared(Aircraft ac);

  /**
  * @return display name
  */
  public String getId();

  /**
  * @return coordinates of waypoint
  */
  public Coordinate getCoordinate();


}
