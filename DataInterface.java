/**
*  DATAINTERFACE
*   
*   Act as intermediary between PAGE and AIRCRAFT, WORLD and NAVIGATION
*   Performs READ-ONLY TASK from the aforementioned Objects to populate states
*    required by FMC
*/

class DataInterface {

  Aircraft ac;
  Navigation nv;
  World wr;
  NavigationDatabase nd;
  FlightPlan fp;

  public DataInterface(Aircraft ac, Navigation nv, World wr, NavigationDatabase nd, FlightPlan fp) {
    this.ac = ac;
    this.nv = nv;
    this.wr = wr;
    this.nd = nd;
    this.fp = fp;
  }

  public String getValueFor(String key) {
    if (key.startsWith("AC-") && ac.attributes.containsKey(key))
      return ac.attributes.get(key);
    else if (key.startsWith("NV-") && nv.attributes.containsKey(key))
      return nv.attributes.get(key);
    else if (key.startsWith("WR-") && wr.attributes.containsKey(key))
      return wr.attributes.get(key);
    else
      return "-ERR";
  }

  public boolean writeToFmc(String key, String value) {
    System.out.println ("writeToFmc() " + key + " " + value);
    String[] keys = key.split("-");
    return parseKey(keys[0], keys[1], value);
  }

  private boolean parseKey(String category, String key, String value) {
    System.out.println ("parseKey " + key + " " + value);
    switch(category) {
      case "FP":
        return parseFlightPlan(key, value);
      case "I":
        return false;
      default:
        return false;
    } 
    // return false;
  }

  private boolean parseFlightPlan(String key, String value) {
    switch(key) {
      case "ORGNARPT":
        if (airportExists(value))
          fp.setOrigin(nd.airports.get(value));
        break;
      case "DESTARPT":
        if (airportExists(value))
          fp.setDestination(nd.airports.get(value));
        break;
      case "FLTNO":
          fp.setFlightNumber(value);
        break;
      default:
        return false;
    }
    return true;
  }

  private boolean airportExists(String icao) {
    return nd.airports.containsKey(icao);
  }

}