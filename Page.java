import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Page {
  String title;
  char[][] screen;
  Aircraft ac;
  Navigation nv;
  World wr;
  final int ROW_SIZE = 14;
  final int COL_SIZE = 24;

  public Page(String title, Aircraft ac, Navigation nv, World wr) throws FileNotFoundException{
    this.ac = ac;
    this.nv = nv;
    this.wr = wr;
    this.title = title;
    screen = new char[ROW_SIZE][COL_SIZE];
    fillWithSpace();
    Scanner fscan = new Scanner(new File("./pages/"+title+".txt"));
    populateArray(fscan);
  }

  private void fillWithSpace() {
    for (int x = 0; x < ROW_SIZE; x++)
      fillRow(x, 0, ' ', COL_SIZE);
  }

  public void renderScreen () {
    System.out.println(" ________________________");
    for (int x = 0; x < ROW_SIZE; x++) {
      System.out.print("|");
      for (int y = 0; y < COL_SIZE; y++) 
        System.out.print(screen[x][y]);
      System.out.print("|\n");
    }
  }

  private void populateArray(Scanner fscan) {
    int rowIndex = 0;
    while (fscan.hasNext()) {
      String line = fscan.nextLine();
      if (!line.startsWith(";")) {
        parseLine(line, rowIndex);
        rowIndex++;
      }
    }
  }

  private void parseLine(String line, int row) {
    int col = 0;
    String[] parts = line.split(" ");
    for (int i = 0; i < parts.length; i++) {
      switch (parts[i++]) {
        case "SPACE":
          System.out.println("::spaces. " + parts[i]);     //debug
          col = fillRow(row, col, ' ', Integer.parseInt(parts[i]));
          break;
        case "READ":
          System.out.println("::read. ");                  //DEBUG
          col = fillRow(row, col, getValue(parts[i++], Integer.parseInt(parts[i]), col));
          break;
        case "INPUT":
          //TODO: Deal with Fields Pending of Inputs
          col = fillRow(row, col, 'I', Integer.parseInt(parts[i+=1]));
          break;
        case "PRINTW":
          System.out.println("::print. " + parts[i]);     //debug
          col = fillRow(row, col, parts[i]);
          break;
      }
    }
  }

  private String getValue(String key, int maxSpaces, int col) {
    String ret = retreiveValueFromHash(key);
    return formatValueString(ret, maxSpaces, col);
  }

  private String retreiveValueFromHash(String key) {
    if (key.startsWith("AC-") && ac.attributes.containsKey(key))
      return ac.attributes.get(key);
    else if (key.startsWith("NV-") && nv.attributes.containsKey(key))
      return nv.attributes.get(key);
    else if (key.startsWith("WR-") && wr.attributes.containsKey(key))
      return wr.attributes.get(key);
    else
      return "-ERR";
  }

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
  *   returns index, a new position on along the screen
  */
  private int fillRow(int row, int col, char fill, int times) {
    for (int i = 0; i < times; i++) 
      screen[row][col++] = fill;
    //renderScreen();         //DEBUG
    return col;
  }

  private int fillRow(int row, int col, String fill) {
    for (char c: fill.toCharArray()) 
      screen[row][col++] = c;
    //renderScreen();         //DEBUG
    return col;
  }


}
