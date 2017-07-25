class Runway {
  
  String id;
  int length;
  int elevation;
  int heading;
  double ilsFrequency;
  Coordinate coord;
  
  public Runway (String runwayId, int length, Coordinate coord, double ilsRadio, int elevation, int heading) {
    this.id = runwayId;
    this.length = length;
    this.elevation = elevation;
    this.ilsFrequency = ilsRadio;
    this.coord = coord;
    this.heading = heading;
  }
  
}