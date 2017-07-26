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

}
