import enums.Direction;
import java.util.*;

// Encapsulation omitted for testing purposes
public class GameHelper {

  private static final String ALPHABET = "abcdefg";
  private static final int GRID_SIZE = 49;
  private static final int MAX_ATTEMPTS = 200;
  private static final int GRID_LENGTH = 7;
  private final int[] grid = new int[GRID_SIZE];
  private final Random random = new Random();
  private int startupCount = 0;

  public String getUserInput(String prompt) {
    System.out.print(prompt + ": ");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine().toLowerCase();
  }

  // Place startup
  public ArrayList<String> placeStartup(int startupSize) {
    int[] startupCoords = new int[startupSize];
    int attempts = 0;
    boolean success = false;

    // DESC: Alternative vertical or horizontal layout
    // Depending on startupCount
    startupCount++;
    int increment = getIncrement();

    while (!success & attempts++ < MAX_ATTEMPTS) {
      int location = random.nextInt(GRID_SIZE);

      for (int i = 0; i < startupCoords.length; i++) {
        startupCoords[i] = location;
        location += increment;
      }

      if (startupFits(startupCoords, increment)) {
        success = coordsAvailable(startupCoords);
      }
    }

    savePositionToGrid(startupCoords);
    ArrayList<String> alphaCells = convertCoordsToAlphaFormat(startupCoords);
    // System.out.println("Placed at: "+ alphaCells);
    return alphaCells;
  }

  // Startup fits
  boolean startupFits(int[] startupCoords, int increment) {
    int firstLocation = startupCoords[0];
    int finalLocation = startupCoords[startupCoords.length - 1];
    Direction horizontal = Direction.HORIZONTAL_INCREMENT;

    if (increment == horizontal.getIncrement()) {
      // DESC: Check horizontal startup does not go over right edge
      // eg. 30/7 == 32/7 == true (4 and 4 - rounds down)
      // but 26/7 == 28/7 == false (3 and 4)
      return calcRowFromIndex(firstLocation) == calcRowFromIndex(finalLocation);
    } else {
      //DESC: Checks vertical row does not exceed past bottom of grid
      return finalLocation < GRID_SIZE;
    }
  }

  // Coords Available
  boolean coordsAvailable(int[] startupCoords) {
    for (int coord : startupCoords) {
      if (grid[coord] != 0) {
        // System.out.println("position: " + coord + " already taken.");
        return false;
      }
    }
    return true;
  }

  // Save position to grid
  void savePositionToGrid(int[] startupCoords) {
    // DESC: Mark grid as occupied
    // Eg. grid[30],[31],[32] == 1
    for (int index : startupCoords) {
      grid[index] = 1;
    }
  }

  // Convert coords to alpha format
  private ArrayList<String> convertCoordsToAlphaFormat(int[] startupCoords) {
    ArrayList<String> alphaCells = new ArrayList<String>();

    for (int index : startupCoords) {
      String alphaCoords = getAlphaCoordsFromIndex(index);
      alphaCells.add(alphaCoords);
    }

    return alphaCells;
  }

  // Get alpha coords from index
  String getAlphaCoordsFromIndex(int index) {
    int row = calcRowFromIndex(index);
    // DESC: If vertical column returns same remainder
    // Thus shares the same Alpha
    // If horizontal remainder will be incremented by 1
    // Thus changing Alpha
    int column = index % GRID_LENGTH;
    String letter = ALPHABET.substring(column, column + 1);
    return letter + row;
  }

  // Calculate row from index
  private int calcRowFromIndex(int index) {
    return index / GRID_LENGTH;
  }

  // Get increment
  private int getIncrement() {
    Direction horizontal = Direction.HORIZONTAL_INCREMENT;
    Direction vertical = Direction.VERTICAL_INCREMENT;

    if (startupCount % 2 == 0) {
      return horizontal.getIncrement();
    } else {
      return vertical.getIncrement();
    }
  }
}
