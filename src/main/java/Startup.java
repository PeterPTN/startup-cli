import java.util.ArrayList;

public class Startup {
  // DESC: Each startup holds its own location
  private String name;
  private ArrayList<String> locationCells;

  public Startup(String name) {
    this.name = name;
  }

  // DESC: Set location from helper
  // Eg. A4, A5, A6
  public void setLocationCells(ArrayList<String> locs) {
    locationCells = locs;
  }

  public String checkYourself(String userInput) {
    String result = "miss";
    int index = locationCells.indexOf(userInput);
    System.out.println(locationCells);

    // DESC: Positive int represents found index
    if (index >= 0) {
      locationCells.remove(index);

      if (locationCells.isEmpty()) {
        result = "kill";
        System.out.println("Ouch! You sunk " + name + " : ( ");
      } else {
        result = "hit";
      }
    }
    return result;
  }

  public String getName() {
    return name;
  }
}
