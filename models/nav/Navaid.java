package model;

class Navaid {
  
  Coordinate coord;
  String name;
  String brief;
  String type;    // ADF/DME/VOR/NDB/
  double freq; 
  char designator;  


  public Navaid (String i, String b, String t, double f, double lat, double lon) {
    this.name = i;
    coord = new Coordinate(lat, lon); 
    this.brief = b; 
    this.type = t;
    this.freq = f; 
  }


}