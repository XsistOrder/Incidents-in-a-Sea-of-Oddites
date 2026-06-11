package display;

import main.Game;

import java.awt.*;

public class Results {
    private static Game game;
    private static int correctlyDispatched;
    private static int totalCorrectlyDispatched;
    private static int incorrectlyDispatched;
    private static int totalIncorrectlyDispatched;
    private static int correctlyDeparted;
    private static int totalCorrectlyDeparted;
    private static int incorrectlyDeparted;
    private static int totalIncorrectlyDeparted;
    private static int impatienceFilled;
    private static int interrogationItemUsage;
    private static int totalItemUsage;
    private static int oddityCount;
    private static int totalOddityCount;
    private static int dayScore;
    private static int day;
    private static int totalScore;

    public Results (Game game) {
        this.game = game;
    }
    public static int getDay () {
        return day;
    }
    public static void setDay (int i) {
        day = i;
    }

    public static void setTotalCorrectlyDispatched (int i) {totalCorrectlyDispatched = i;}
    public static void setTotalIncorrectlyDispatched (int i) {
        totalIncorrectlyDispatched = i;
    }
    public static void setTotalCorrectlyDeparted (int i) {
        totalCorrectlyDeparted = i;
    }
    public static void setTotalIncorrectlyDeparted (int i) {
        totalIncorrectlyDeparted = i;
    }
    public static void setTotalItemUsage (int i) {
        totalItemUsage = i;
    }
    public static void setTotalOddityCount (int i) {
        totalOddityCount = i;
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
            totalCorrectlyDispatched += correctlyDispatched;
        } else {
            incorrectlyDispatched++;
            totalIncorrectlyDispatched += incorrectlyDispatched;
        }
    }
    public static void addOddityDepartResults (boolean success) {
        if (success) {
            correctlyDeparted++;
            totalCorrectlyDeparted += correctlyDeparted;
        } else {
            incorrectlyDeparted++;
            totalIncorrectlyDeparted += incorrectlyDeparted;
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
        game.save.set("checkpoint_health", CheckpointHealth.getCheckpointHealth());
        game.save.set("current_day", day);
        game.save.set("total_correctly_dispatched", totalCorrectlyDispatched);
        game.save.set("total_incorrectly_dispatched", totalIncorrectlyDispatched);
        game.save.set("total_correctly_departed", totalCorrectlyDeparted);
        game.save.set("total_incorrectly_departed", totalIncorrectlyDeparted);
        game.save.set("total_score", totalScore);

        game.save.write();

        game.graphics.addText("Total Score: " + totalScore, 50, 100, 20, Color.WHITE, 25, true);
        game.graphics.addText("Daily Score: " + dayScore, 50, 150, 20, Color.WHITE, 25, true);
        game.graphics.addText("Oddities Successfully Dispatched: " + correctlyDispatched, 50, 200, 20, Color.WHITE, 25, true);
        game.graphics.addText("Oddities Successfully Departed: " + correctlyDeparted, 50, 250, 20, Color.WHITE, 25, true);
        game.graphics.addText("Oddities Incorrectly Dispatched: " + incorrectlyDispatched, 50, 300, 20, Color.WHITE, 25, true);
        game.graphics.addText("Oddities Incorrectly Departed: " + incorrectlyDeparted, 50, 350, 20, Color.WHITE, 25, true);
        game.graphics.addText("Impatience filled: " + impatienceFilled, 50, 400, 20, Color.WHITE, 25, true);
        game.graphics.addText("Item Usage: " + interrogationItemUsage, 50, 450, 20, Color.WHITE, 25, true);
        game.graphics.addText("Day " + day, 300, 50, 100, new Color(140, 20, 11), 25, true);
    }

    public static void totalReset () {
        System.out.println("All your progress has been lost, cry about it.");
    }

    public static void gameOver() {
        game.graphics.addText("Total Score: " + totalScore, 200, 100, 20, Color.RED, 25, true);
        game.graphics.addText("Total Oddities Successfully Dispatched: " + totalCorrectlyDispatched, 200, 150, 20, Color.RED, 25, true);
        game.graphics.addText("Total Oddities Incorrectly Dispatched: " + totalIncorrectlyDispatched, 200, 200, 20, Color.RED, 25, true);
        game.graphics.addText("Total Oddities Correctly Departed: " + totalCorrectlyDeparted, 200, 250, 20, Color.RED, 25, true);
        game.graphics.addText("Total Oddities Incorrectly Departed: " + totalIncorrectlyDeparted, 200, 300, 20, Color.RED, 25, true);
        game.graphics.addText("Day Reached: " + day, 200, 350, 20, Color.RED, 25, true);
        game.save.resetSave();
    }
}