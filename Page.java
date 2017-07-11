import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Page {
  String title;
  char[][] screen;
  Aircraft ac;
  Navigation nv;

  public Page(String title, Aircraft ac, Navigation nv) throws FileNotFoundException{
    this.ac = ac;
    this.nv = nv;
    this.title = title;
    screen = new char[14][24];
    Scanner fscan = new Scanner(new File("./"+title+".txt"));
    populateArray(fscan);
  }

  public void renderScreen () {
    System.out.println(" ________________________");
    for (int x = 0; x < 14; x++) {
      System.out.print("|");
      for (int y = 0; y < 24; y++) 
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
          System.out.println("::read. ");
          col = fillRow(row, col, getValue(parts[i++], Integer.parseInt(parts[i]), col));
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
    renderScreen();
    return col;
  }

  private int fillRow(int row, int col, String fill) {
    for (char c: fill.toCharArray()) 
      screen[row][col++] = c;
    renderScreen();
    return col;
  }


}
