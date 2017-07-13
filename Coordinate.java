class Coordinate{

  double latitude;
  double longitude;

  public Coordinate(double lat, double lon) {
    this.latitude = lat; this.longitude = lon;
  }
  
  public Coordinate(String ns, String hh1, String mm1, String ew, String hh2, String mm2) {
    this(ns, Double.parseDouble(hh1), Double.parseDouble(mm1), 
          ew, Double.parseDouble(hh2), Double.parseDouble(mm2));
  }

  public Coordinate(String ns, double hh1, double mm1, String ew, double hh2, double mm2) {
    latitude = combineHourMinute(hh1, mm1);
    longitude = combineHourMinute(hh2, mm2);
    if (ns.equals("S")) latitude*=-1;
    if (ew.equals("W")) longitude*=-1;
  }

  private double combineHourMinute(double hh, double mm) {
    return hh + (double) Double.parseDouble("."+Double.toString(mm).replace(".",""));
  }

  public String getLatNSDot() {
    double dec = (latitude - (Math.floor(latitude)))*100;
    if (latitude>0)
      return "N" + (int)(Math.floor(latitude)) + "D" + dec;
    else
      return "S" + (int)(Math.floor(latitude))*-1 + "D" + dec;
  }

  public String getLonEWDot() {
    double dec = (longitude - (Math.floor(longitude)))*100;
    if (longitude>0)
      return "E" + ((int)(Math.floor(longitude))) + "D" + dec;
    else
      return "W" + (int)((Math.floor(longitude))*-1) + "D" + dec;
  }

  
}