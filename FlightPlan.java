class FlightPlan {
  //turn into type-Airport
  Airport origin;  //ORGNARPT, ICAO
  Airport destination; //DESTARPT, ICAO
  String flightNo;  //FLTNON
  // ArrayList<Waypoints> route;

  public FlightPlan() {

  }

  public void setOrigin(Airport icao) {
    origin = icao;
  }

  public void setDestination(Airport icao) {
    destination = icao;
  }

  public void setFlightNumber(String s) {
    flightNo = s;
  }

}