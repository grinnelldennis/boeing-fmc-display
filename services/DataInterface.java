package services;

import java.util.HashMap;

import global.StaticData;
import global.World;
import modelsinterface.Field;
import modelsfmc.Fields;
import modelsnav.Airport;
/**
*  DATAINTERFACE
*   
*   Act as intermediary between PAGE and AIRCRAFT, WORLD and NAVIGATION
*   Performs READ-ONLY TASK from the aforementioned Objects to populate states
*    required by FMC
*/

public class DataInterface {

  StaticData sd;
  NavigationDatabase nd;
  FlightPlan fp;
  //SCREEN INPUT/OUTPUT
  char[][] screen;
  HashMap<String, Field> fields;

  public DataInterface(StaticData sd, World wr, 
      NavigationDatabase nd, FlightPlan fp) {
    screen = new char[14][24];
    this.sd = sd;
    this.nd = nd;
    this.fp = fp;
  }

  /* 
   *    Input-Output Methods 
   *                            */
  /**
   * Reads input from page. If input->valid, it updates the corresponding 
   *  objects to reflect the user input. It otherwise prints error message.
   * @param pos, corresponding position of input on page
   * @param f, field where the input is going 
   * @param value, input
   * @param sr, screen
   * @param fd, all the fields 
   * @return
   */
  public char[][] read(String pos, Field f, String value, char[][] sr, Fields fd) {
    this.screen = sr;
    String[] keys = f.getFieldId().split("-");
    if (writeInputToScreen(keys[0], keys[1], value, fd))
      writeFormattedValueToField(f, value);
    else
      writeErrorMessage("INVALID COMMAND");
    return screen;
  }


  /* 
   *    Screen-Drawing Methods 
   *                             */
  private void writeErrorMessage(String s) {
    if (s.length() < 24) writeToRowOnScreen(13, 0, s);
    else writeToRowOnScreen(13, 0, s.substring(0, 24));
  }

  private void writeFormattedValueToField(Field f, String value) {
    writeToRowOnScreen(f.getRow(), f.getCol(), 
        formatValueString(value, f.getMaxSpaces(), f.getCol())); 
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
  * @param col, left justified if col equals 0
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



  /* 
   *    Parser-Select-Writer Methods 
   *                                    */
  private boolean writeInputToScreen(String category, String key, String value, Fields fd) {
    System.out.println ("parseKey " + key + " " + value);
    switch(category) {
      case "FP":
        return parseFlightPlan(key, value, fd);
      case "I":
        return parseInformation(key, value, fd);
      default:
        return false;
    } 
  }

  private boolean parseInformation(String key, String value, Fields fd){
    switch(key) {
      case "AIRPORT":
        if (airportExists(value)) {
          writeToRowOnScreen(4, 6, formatValueString(nd.getAirports().get(value).getRunways().get(1).coord.getLatNSDot(), 8, 1)); 
          writeToRowOnScreen(4, 15, formatValueString(nd.getAirports().get(value).getRunways().get(1).coord.getLonEWDot(), 9, 1));
        } break;
    }
    return true;
  }

  private boolean parseFlightPlan(String key, String value, Fields fd) {
    switch(key) {
      case "ORGNARPT":  //Origin Airport
        if (airportExists(value)) {
          fd.put("FP-"+key, value);
          fp.setOrigin(nd.getAirports().get(value));
          //clear flight-plan
        }
        break;
      case "DESTARPT":  //Destination Airport
        if (airportExists(value)){
          fd.put("FP-"+key, value);
          System.out.println("FP-"+key + " " +value);
          fp.setDestination(nd.getAirports().get(value));
        } break;
      case "FLTNO":   //Flight Number
          fd.put("FP-"+key, value);
          fp.setFlightNumber(value);
        break;
      default:
        return false;
    }
    return true;
  }

  private boolean airportExists(String icao) {
    return nd.getAirports().containsKey(icao);
  }


  /* 
   *  Object Read-Only Methods 
   *                              */
  public String getStaticValueFor(String key) {
    if (key.startsWith("AC-"))
      return sd.get("ac", key);
    else if (key.startsWith("NV-"))
      return sd.get("nv", key);
    else if (key.startsWith("WR-"))
      return sd.get("wr", key);
    else
      return "-ERR";
  }




}


