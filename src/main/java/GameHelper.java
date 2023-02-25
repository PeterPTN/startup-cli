import java.util.*;

import enums.Direction;

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

    startupCount++;
    int increment = getIncrement();

    while (!success & attempts++ < MAX_ATTEMPTS) {
      int location = random.nextInt(GRID_SIZE);

      for (int i = 0; i < startupCoords.length; i++) {
        startupCoords[i] = location;
        location += location;
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
    int finalLocation = startupCoords[startupCoords.length - 1];
    Direction horizontal = Direction.HORIZONTAL_INCREMENT;

    if (increment == horizontal.getIncrement()) {
      return (
        calcRowFromIndex(startupCoords[0]) == calcRowFromIndex(finalLocation)
      );
    } else {
      return finalLocation < GRID_SIZE;
    }
  }

  // Coords Available
  boolean coordsAvailable(int[] startupCoords) {
    System.out.println(startupCoords);

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
