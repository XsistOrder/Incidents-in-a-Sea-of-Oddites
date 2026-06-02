package display;

import main.Game;

public class Results {
    public static int correctlyDispatched;
    public static int incorrectlyDispatched;
    public static int correctlyDeparted;
    public static int incorrectlyDeparted;
    public static int impatienceFilled;
    public static int interrogationItemUsage;
    public static int oddityCount;
    public static int dayScore;
    public static int totalScore;

    public Results (Game game) {

    }

    public static void addToOddityCount () {
        oddityCount++;
    }

    public static void addOddityDispatchResults (boolean success) {
        if (success) {
            correctlyDispatched++;
        } else {
            incorrectlyDispatched++;
        }
    }
    public static void addOddityDepartResults (boolean success) {
        if (success) {
            correctlyDeparted++;
        } else {
            incorrectlyDeparted++;
        }
    }
    public static void addImpatienceFillResults () {
        impatienceFilled++;
    }
    public static void addInterrogationItemResults (int usage) {
        interrogationItemUsage++;
    }

    public static void dayReset () {
        //display each results category in a table addition/subtraticon display then display the total score & show button to move onto the next day or quit out saving from the day.
        dayScore = (int) (correctlyDeparted + correctlyDispatched) * 100 - (incorrectlyDeparted + incorrectlyDispatched) * 25 - impatienceFilled * 50 - interrogationItemUsage / oddityCount * 25;
        totalScore += dayScore;
    }

    public static void totalReset () {
        System.out.println("All your progress has been lost, cry about it.");
    }
}
