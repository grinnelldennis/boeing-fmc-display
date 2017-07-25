import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

class Page {
  //GENERIC SCREEN INFORMATION
  String title;
  char[][] screen;
  Scanner scan;
  
  DataInterface bridge;
  final int ROW_SIZE = 14;
  final int COL_SIZE = 24;
  
  HashMap<String, Field> fieldsLocation;    //<location, fieldId>
  HashMap<String, Field> outputf;           //Fields Pending of Output
  HashMap<String, String> buttons;          //<screenLocation, buttonId>

  public Page(String title, DataInterface bridge, Fields inputf) throws FileNotFoundException{
    this.bridge = bridge; 
    this.title = title;
    //INIT OBJECTS
    scan = new Scanner(System.in);
    outputf = new HashMap<>();
    fieldsLocation = new HashMap<>();
    buttons = new HashMap<>();
    screen = new char[ROW_SIZE][COL_SIZE];
    //INIT PAGE
    clearScreen();
    Scanner fscan = new Scanner(new File("./pages/"+title+".txt"));
    parseFile(fscan, inputf);
    if (true) {   //DEBUG
      System.out.println("Page contains " + buttons.size() + " buttons.");
      System.out.println("Page contains " + fieldsLocation.size() + " fields.");
    }
  } 

  public String openPage(Fields fd) {
    updateScreen(fd);
    return inputListener(fd);
  }

  private void updateScreen(Fields fd) {
    printFilledOutputs(fd);
    renderToScreen();
  }

  private void printFilledOutputs(Fields fd) {
    for (String key : outputf.keySet())
      if (fd.containsKey(key)){
        Field f = outputf.get(key);
        fill(f.row, f.col, formatValueString(fd.get(key), f.maxSpaces, f.col));
      }
  }

  // model button presses & typed-inputs
  public String inputListener(Fields fd) {
    String input = scan.nextLine().toUpperCase();
    while (!isAButtonPress(input)) {
      clearRow(13);
      String[] inputs = input.split(" "); 
      if (inputs.length == 2 && fieldsLocation.containsKey(inputs[0])) {
        String pos = inputs[0], value = inputs[1]; 
        screen = bridge.read(pos, fieldsLocation.get(pos), value, screen, fd);
      } else
        fill(13, 0, "INVALID COMMAND");
      updateScreen(fd);
      input = scan.nextLine().toUpperCase();
      System.out.println("Input:: " + input);
    } 
    return buttons.get(input);
  }

  private boolean isAButtonPress(String input) {
    return buttons.containsKey(input);  
  }

  /**
  * Renders 2d array to screen
  */
  public void renderToScreen () {
    System.out.println("______________________________");
    for (int x = 0; x < ROW_SIZE; x++) {
      if (x % 2 == 0 && x != 0)
        System.out.print(" -|");
      else
        System.out.print("  |");
      for (int y = 0; y < COL_SIZE; y++) 
        System.out.print(screen[x][y]);
      if (x % 2 == 0 && x != 0)
        System.out.print("|- (" + x + ")\n");
      else
        System.out.print("|  (" + x + ")\n");
    }
  }

  /**
  * Takes a file of text that represents a fmc screen and parse the 
  *   instructions from the text file. Ignores comment lines and calls
  *   parseLine to parse each individual lines
  * @param fscan, an open file Scanner
  */
  private void parseFile(Scanner fscan, Fields inputf) {
    int rowIndex = 0;
    while (fscan.hasNext()) {
      String line = fscan.nextLine();
      if (!line.startsWith(";")) {
        parseLine(line, rowIndex, inputf);
        rowIndex++;
      }
    }
    fscan.close();
  }

  /**
  * Takes a line of text that represents a line on the fmc and parse the 
  *   instructions from the text file. Is able to parse keywords
  * @param line, line of text from instruction file
  * @param row, row to write into 
  */
  private void parseLine(String line, int row, Fields inputf) {
    int spaces, col = 0;
    Field field;
    String[] parts = line.split(" ");
    for (int i = 0; i < parts.length; i++) {
          System.out.println("::reading.. " + parts[i] + parts[i+1]);     //DEBUG
      String action = parts[i], fieldId;
      switch (parts[i++]) {
        case "SPACE":
          col = fill(row, col, ' ', Integer.parseInt(parts[i]));
          break;
        case "READ":
          col = fill(row, col, getValue(parts[i++], Integer.parseInt(parts[i]), col));
          break;
        case "IN-ESS":    
        case "IN-OPT":
        case "IN-BNK": 
          fieldId = parts[i++];
          spaces = Integer.parseInt(parts[i]);
          field = new Field(fieldId, spaces, row, col);
          inputf.create(fieldId);
          fieldsLocation.put(getPosition(row, col), field);
          
          if (action.endsWith("ESS")) 
            col = fill(row, col, '0', spaces);
          else if (action.endsWith("OPT")) 
            col = fill(row, col, '-', spaces);
          else if (action.endsWith("BNK")) 
            col = fill(row, col, ' ', spaces);
          break;
        case "OUT":
          fieldId = parts[i++];
          spaces = Integer.parseInt(parts[i]);
          outputf.put(fieldId, new Field(fieldId, spaces, row, col, ""));
          col = fill(row, col, ' ', spaces);
          break;
        case "PRINT":
          col = fill(row, col, parts[i]);
          break;
        case "BUTTON":
          col = processButton(row, col, parts[i]);
          break;
      }
    }
  }

  private String getPosition(int row, int col) {
    return "" + row + ((col==0)? "L" : "R");
  }

  private int processButton(int row, int col, String part) {
    String[] parts = part.split("/");
    if (parts.length == 2)
      part = parts[0] + " " + parts[1];
    if (col == 0) 
      buttons.put(getPosition(row, col), part.substring(1));
    else
      buttons.put(getPosition(row, col), part.substring(0, part.length()-1));
    return fill(row, col, part);
  }

  /**
  * Calls bridge to get necessary information to display on screen
  * @param key, String to recover value for from bridge
  * @param maxSpaces, maximum length allocated for s on screen
  * @param col, an integer
  * @return a String from bridge formatted by formatValueString
  * @see formatValueString()
  */
  private String getValue(String key, int maxSpaces, int col) {
    return formatValueString(bridge.getValueFor(key), maxSpaces, col);
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

  /**
  * Fills Screen with character fill "times"
  * e.g. writing whitespace 6 times
  * @param row, target row
  * @param col, target col
  * @param fill, fill for target [row][col]
  * @param times, number of times to fill with character
  * @return col, incremented position along the screen
  */
  private int fill(int row, int col, char fill, int times) {
    for (int i = 0; i < times; i++) 
      screen[row][col++] = fill;
    return col;
  }

  /**
  * Fills Screen
  * e.g. writing "POS INIT" to screen
  * @param row, target row
  * @param col, target col
  * @param fill, fill for target [row][col]
  * @return col, incremented position along the screen
  */
  private int fill(int row, int col, String fill) {
    for (char c: fill.toCharArray()) 
      screen[row][col++] = c;
    return col;
  }

  /**
   * Initializes the 2d array with whitespace
   */
  private void clearScreen() {
    for (int x = 0; x < ROW_SIZE; x++)
      fill(x, 0, ' ', COL_SIZE);
  }

  private void clearRow(int r) {
    fill(r, 0, ' ', COL_SIZE);
  }

  
}
