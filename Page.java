import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

class Page {
  String title;
  char[][] screen;
  DataInterface bridge;
  final int ROW_SIZE = 14;
  final int COL_SIZE = 24;
  Scanner scan;
  HashMap<String, String> buttons;      //<screenLocation, buttonId>
  HashMap<String, Field> fields;       //<screenLocation, fieldId>

  private class Field{
    String fieldId;
    int maxSpaces;
    int startRow;
    int startCol;
    public Field(String fieldId, int maxSpaces, int r, int c) {
      this.fieldId = fieldId; this.maxSpaces = maxSpaces; 
      this.startRow = r; this.startCol = c;
    }
  }

  public Page(String title, DataInterface bridge) throws FileNotFoundException{
    this.bridge = bridge; 
    this.title = title;
    scan = new Scanner(System.in);
    fields = new HashMap<>();
    buttons = new HashMap<>();
    screen = new char[ROW_SIZE][COL_SIZE];

    fillWithSpace();
    Scanner fscan = new Scanner(new File("./pages/"+title+".txt"));
    parseFile(fscan);
    //LOAD DEBUG
    System.out.println("Page contains " + buttons.size() + " buttons.");
    System.out.println("Page contains " + fields.size() + " fields.");
  }

  public String openPage() {
    renderToScreen();
    return inputListener();
  }

  // model button presses & typed-inputs
  public String inputListener() {
    String input = scan.nextLine().toUpperCase();
    while (!isAButtonPress(input)) { 
      fillRow(13, 0, ' ', 24);
      String[] inputs = input.split(" "); 
      if (inputs.length == 2 && fields.containsKey(inputs[0]))
        if (bridge.writeToFmc(fields.get(inputs[0]).fieldId, inputs[1])) {
          Field fill = fields.get(inputs[0]);
          fillRow(fill.startRow, fill.startCol, 
            formatValueString(inputs[1], fill.maxSpaces, fill.startCol)); 
        }
      else
        fillRow(13, 0, "INVALID COMMAND");
      updateScreen();
      input = scan.nextLine().toUpperCase();
      System.out.println("Input:: " + input);
    } 
    return buttons.get(input);
  }

  private void updateScreen() {
    renderToScreen();
  }

  private boolean isAButtonPress(String input) {
    return buttons.containsKey(input);  
  }

  /**
  * Initializes the 2d array with whitespaces
  */
  private void fillWithSpace() {
    for (int x = 0; x < ROW_SIZE; x++)
      fillRow(x, 0, ' ', COL_SIZE);
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
  private void parseFile(Scanner fscan) {
    int rowIndex = 0;
    while (fscan.hasNext()) {
      String line = fscan.nextLine();
      if (!line.startsWith(";")) {
        parseLine(line, rowIndex);
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
  private void parseLine(String line, int row) {
    int spaces, col = 0;
    String[] parts = line.split(" ");
    for (int i = 0; i < parts.length; i++) {
      System.out.println("::reading.. " + parts[i] + parts[i+1]);     //debug
      switch (parts[i++]) {
        case "SPACE":
          col = fillRow(row, col, ' ', Integer.parseInt(parts[i]));
          break;
        case "READ":
          col = fillRow(row, col, getValue(parts[i++], Integer.parseInt(parts[i]), col));
          break;
        case "IN-ESS":    //"Essential" Inputs, shows up as Vertical Boxes
          spaces = Integer.parseInt(parts[i+1]);
          fields.put(getPosition(row, col), new Field(parts[i++], spaces, row, col));
          col = fillRow(row, col, '0',spaces);
          break;
        case "IN-OPT":    //"Optional" Inputs, shows up as Dashes
          spaces = Integer.parseInt(parts[i+1]);
          fields.put(getPosition(row, col), new Field(parts[i++], spaces, row, col));
          col = fillRow(row, col, '-', spaces);
          break;
        case "IN-BNK":    //"Blank" Inputs, shows up as Blanks
          spaces = Integer.parseInt(parts[i+1]);
          fields.put(getPosition(row, col), new Field(parts[i++], spaces, row, col));
          col = fillRow(row, col, ' ', spaces);
          break;
        case "PRINT":
          col = fillRow(row, col, parts[i]);
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
    return fillRow(row, col, part);
  }

  /**
  * Calls bridge to get neceessary information to display on screen and format
  *   such value.
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
  private int fillRow(int row, int col, char fill, int times) {
    for (int i = 0; i < times; i++) 
      screen[row][col++] = fill;
    return col;
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
  private int fillRow(int row, int col, String fill) {
    for (char c: fill.toCharArray()) 
      screen[row][col++] = c;
    return col;
  }


}
