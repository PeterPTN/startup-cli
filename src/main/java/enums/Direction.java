package enums;


// DESC: Enums to replace private static finals consts
public enum Direction {
  HORIZONTAL_INCREMENT(1),
  VERTICAL_INCREMENT(Direction.GRID_LENGTH);

  private static final int GRID_LENGTH = 7;
  private final int increment;

  // DESC: Multiple constructors needed to differentiate constant types
  Direction(int increment) {
    this.increment = increment;
  }

  public int getIncrement() {
    return increment;
  }
}
