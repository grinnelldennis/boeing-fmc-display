package modelsnav;

public class Runway {

  String id;
  int length;
  int elevation;
  int heading;
  double ilsFrequency;
  public Coordinate coord;

  public Runway (String runwayId, int length, String lat, String lon, double ilsRadio, int elevation, int heading) {
    this.id = runwayId;
    this.length = length;
    this.elevation = elevation;
    this.ilsFrequency = ilsRadio;
    this.coord = new Coordinate(lat, lon);
    this.heading = heading;
  }

  public Runway(String s) {
    this(s.substring(28, 31), //RunwayId
        Integer.parseInt(s.substring(33, 39)), //Length
        s.substring(39, 49),
        s.substring(49, 60),
        Double.parseDouble(s.substring(60, 66)),
        Integer.parseInt(s.substring(70, 74)),
        Integer.parseInt(s.substring(66, 69)));
  }

}
