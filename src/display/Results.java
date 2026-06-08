package display;

import main.Game;

public class Results {
    private static int correctlyDispatched;
    private static int incorrectlyDispatched;
    private static int correctlyDeparted;
    private static int incorrectlyDeparted;
    private static int impatienceFilled;
    private static int interrogationItemUsage;
    private static int oddityCount;
    private static int dayScore;
    private static int day;
    private static int totalScore;

    public Results (Game game) {

    }
    public static int getDay () {
        return day;
    }
    public static void setDay (int i) {
        day = i;
    }

    public static void setTotalScore (int i) {
        totalScore = i;
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
        //display each results category in a cloumn then show each final result addition style (ex: correctly dipatched - 25 Oddities - score 200) which adds to the total score, total score displays below & after all the other scores. then save all data required for next day (totalscore, checkpointhealth, perment impaitnece multiper, daynumber).
        //every day that passes impatience multipers become less forgiving, and oddities on average will have more stats displayed on their document with less incorrect
        day++;
        dayScore = 0;
        dayScore = (int) (correctlyDeparted + correctlyDispatched) * 100 - (incorrectlyDeparted + incorrectlyDispatched) * 25 - impatienceFilled * 50 - interrogationItemUsage / oddityCount * 25;
        totalScore += dayScore;
        System.out.println(dayScore);
    }

    public static void totalReset () {
        System.out.println("All your progress has been lost, cry about it.");
    }
}