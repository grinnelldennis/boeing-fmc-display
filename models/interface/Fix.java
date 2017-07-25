

/*
  INTERFACE for a navigation fix, could either be a
*/

interface Fix {

  /**
  * @param aircraftCoord, the present coordiinate of the aircraft
  * @return whether aircraft's has cleared the waypoint
  */
  public boolean isCleared(Coordinate aircraftCoord);

  /**
  * @return whether fix has passing restrictions
  */
  public boolean hasRestrictions();


}
