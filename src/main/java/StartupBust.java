
import java.util.ArrayList;

public class StartupBust {

  private GameHelper helper = new GameHelper();
  private ArrayList<Startup> startups = new ArrayList<Startup>();
  private int numberOfGuesses;

  private void setUpGame() {
    Startup one = new Startup("Poniez");
    Startup two = new Startup("Hacqi");
    Startup three = new Startup("Cabista");

    startups.add(one);
    startups.add(two);
    startups.add(three);

    System.out.println("Your goal is to sink three startups");
    System.out.printf(
      "%s, %s, %s",
      one.getName(),
      two.getName(),
      three.getName()
    );
    System.out.println("Try to sink them all in the fewest number of guesses");

    for (Startup startup : startups) {
      ArrayList<String> newLocation = helper.placeStartup(3);
      startup.setLocationCells(newLocation);
    }
  }

  private void startPlaying() {
    while (!startups.isEmpty()) {
      String userGuess = helper.getUserInput("Enter a guess");
      checkUserGuess(userGuess);
    }
    finishGame();
  }

  private void checkUserGuess(String userGuess) {
    numberOfGuesses++;
    String result = "miss";

    for (Startup startup : startups) {
      result = startup.checkYourself(userGuess);

      if (result.equals("hit")) {
        break;
      }

      if (result.equals("kill")) {
        // Remove at arrayList level
        startups.remove(startup);
        break;
      }
    }

    System.out.println(result);
  }

  private void finishGame() {
    System.out.println("All Startups are dead! Your stock is now worthless");
    if (numberOfGuesses <= 18) {
      System.out.println("It only took you " + numberOfGuesses + " guesses.");
      System.out.println("You got out before your options sank.");
    } else {
      System.out.println(
        "Took you long enough. " + numberOfGuesses + " guesses."
      );
      System.out.println("Fish are dancing with your options");
    }
  }

  public static void main(String[] args) {
    StartupBust game = new StartupBust();
    game.setUpGame();
    game.startPlaying();
  }
}
