
/*
  Object that stores geographical information based on a location's latitude
   and latitude, as doubles, in the format of +/-00.00000, +/-000.00000
*/
class Coordinate{
  double latitude;
  double longitude;
  /**
  * @param lat, a double, +/-00.000000
  * @param lon, a double, +/-000.000000
  */
  public Coordinate(double lat, double lon) {
    this.latitude = lat; this.longitude = lon;
  }

  /**
  * @param lat, a String, +/-00.000000
  * @param lon, a String, +/-000.000000
  */
  public Coordinate(String lat, String lon) {
    this(Double.parseDouble(lat), Double.parseDouble(lon));
  }

  /**
  * @param ns, a String, single character (N/S) denoting North or South
  * @param hh, a String, digits representing degrees
  * @param mm, a String, digits representing minutes
  * @param ew, a String, single character (E/W) denoting East-West
  */
  public Coordinate(String ns, String hh1, String mm1, String ew, String hh2, String mm2) {
    this(ns, Double.parseDouble(hh1), Double.parseDouble(mm1),
          ew, Double.parseDouble(hh2), Double.parseDouble(mm2));
  }

  /**
  * @param ns, a String, single character, i.e. N/S, denoting North or South
  * @param hh, a double representing degrees
  * @param mm, a double representing minutes
  * @param ew, a String, single character, i.e. E/W, denoting East-West
  */
  public Coordinate(String ns, double hh1, double mm1, String ew, double hh2, double mm2) {
    latitude = combineHourMinute(hh1, mm1);
    longitude = combineHourMinute(hh2, mm2);
    if (ns.equals("S")) latitude*=-1;
    if (ew.equals("W")) longitude*=-1;
  }


  public double combineHourMinute(double hh, double mm) {
    return hh + (double) Double.parseDouble("."+Double.toString(mm).replace(".",""));
  }

  /**
  * Returns Laotitude in format N00.00000 or S00.00000
  */
  public String getLatNSDot() {
    double dec = (latitude - (Math.floor(latitude)))*100;
    if (latitude>0)
      return "N" + (int)(Math.floor(latitude)) + "D" + dec;
    else
      return "S" + (int)(Math.floor(latitude))*-1 + "D" + dec;
  }

  /**
  * Returns Laotitude in format E000.00000 or W000.00000
  */
  public String getLonEWDot() {
    double dec = (longitude - (Math.floor(longitude)))*100;
    if (longitude>0)
      return "E" + ((int)(Math.floor(longitude))) + "D" + dec;
    else
      return "W" + (int)((Math.floor(longitude))*-1) + "D" + dec;
  }


}
