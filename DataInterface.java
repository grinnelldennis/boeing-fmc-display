import java.util.HashMap;
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
  //SCREEN IO
  char[][] screen;
  HashMap<String, Field> fields;

  public DataInterface(Aircraft ac, Navigation nv, World wr, NavigationDatabase nd, FlightPlan fp) {
    screen = new char[14][24];
    this.ac = ac;
    this.nv = nv;
    this.wr = wr;
    this.nd = nd;
    this.fp = fp;
  }


  /* Input-Output Methods */

  public char[][] readInput(String pos, String key, String value, char[][] screen, HashMap<String, Field> fields) {
    this.screen = screen;
    this.fields = fields;

    String[] keys = key.split("-");
      System.out.println("READINPUT::KEY: " + key);
    if (writeInputToScreen(keys[0], keys[1], value)) {
      Field f = fields.get(pos);
      if (fields.containsKey(pos))
        writeToRowOnScreen(f.startRow, f.startCol, 
          formatValueString(value, f.maxSpaces, f.startCol)); 
    } else
      writeErrorMessage("INVALID COMMAND");
    return screen;
  }


  /* Screen-Drawing Methods */

  private void writeErrorMessage(String s) {
    if (s.length() < 24) writeToRowOnScreen(13, 0, s);
    else writeToRowOnScreen(13, 0, s.substring(0, 24));
  }

  /**
  * Fills FMC screen with character fill "times", starting from col on row
  *   by overwriting the existing character on the 2D array 
  * e.g. writing whitespaces 6 times
  * @param row, target row
  * @param col, target col
  * @param fill, fill for target [row][col]
  * @param times, number of times to fill with character
  * @return col, incremented position along the screen
  */
  private void writeToRowOnScreen(int row, int col, char fill, int times) {
    for (int i = 0; i < times; i++) 
      screen[row][col++] = fill;
  }

  /**
  * Fills FMC screen with string "fill", starting from col on row
  *   by overwriting the existing character on the 2D array 
  * e.g. writing "POS INIT" to screen
  * @param row, target row
  * @param col, target col
  * @param fill, fill for target [row][col]
  * @return col, incremented position along the screen
  */
  private void writeToRowOnScreen(int row, int col, String fill) {
    for (char c: fill.toCharArray()) 
      screen[row][col++] = c;
  }

  /**
  * Formats String s to fit into maxSpaces. Col determines whether formatted
  *   String is right or left justified by if necessary prepending or appending
  *   s with white spaces.
  * @param s, string to be formatted
  * @param maxSpaces, maximum length allocated for s on screen
  * @param col, left jusitifed if col equals 0
  * @return formatted string ready for fillScreen()
  */
  private String formatValueString(String s, int maxSpaces, int col) {
    String spaces = "";
    if (s.length() < maxSpaces)  {
      for (int i = s.length(); i < maxSpaces; i++)
        spaces = spaces + " ";
      if (col == 0)
        return s + spaces;
      else 
        return spaces + s;
    } else
    return s.substring(0, maxSpaces);
  }



  /* Parser-Select-Writer Methods */

  private boolean writeInputToScreen(String category, String key, String value) {
    System.out.println ("parseKey " + key + " " + value);
    switch(category) {
      case "FP":
        return parseFlightPlan(key, value);
      case "I":
        return parseInformation(key, value);
      default:
        return false;
    } 
  }

  private boolean parseInformation(String key, String value){
    switch(key) {
      case "AIRPORT":
        if (airportExists(value)) {
          writeToRowOnScreen(4, 6, formatValueString(nd.airports.get(value).runways.get(1).coord.getLatNSDot(), 8, 1)); 
          writeToRowOnScreen(4, 15, formatValueString(nd.airports.get(value).runways.get(1).coord.getLonEWDot(), 9, 1));
        } break;
    }
    return true;
  }

  private boolean parseFlightPlan(String key, String value) {
    switch(key) {
      case "ORGNARPT":  //Origin Airport
        if (airportExists(value))
          fp.setOrigin(nd.airports.get(value));
        break;
      case "DESTARPT":  //Destination Airport
        if (airportExists(value))
          fp.setDestination(nd.airports.get(value));
        break;
      case "FLTNO":   //Flight Number
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


  /* Object Read-Only Methods */

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




}


