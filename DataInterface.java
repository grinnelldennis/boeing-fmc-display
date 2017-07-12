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

  public DataInterface(Aircraft ac, Navigation nv, World wr) {
    this.ac = ac;
    this.nv = nv;
    this.wr = wr;
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

  public void writeTo(String key, String value) {
    
  }

}