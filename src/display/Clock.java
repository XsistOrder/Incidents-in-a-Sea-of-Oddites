package display;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/*
    call "clock.tick(tick, ticksPerSecond)l if game loops every 10ms, and tick resets at 100, ticksPerSecond = 100;

  check whether the countdown has finished
  if (clock.isFinished()) {
  [CODE]
  }

  read remaining time
  int secsLeft = clock.getTotalSecondsRemaining();

  Show / hide the clock
  clock.setVisible(true)
  clock.setVisible(false)

  add all parts to a group

  clock.addToGroup("name")

  example setup:

  Clock clock = new Clock(game, 420, 50, 120, 15);
  clock.setTime(3, 0) //3 minutes
  clock.attachHoverListener();  //MUST CALL ON SETUP FOR HOVER TO WORK

  //in game.move():
   clock.tick (tick, 100) //100 ticks per second (loop every 10ms, reset at 100)
 */

public class Clock {

    private final Game game;

    private final int faceId;
    private final int minuteHandId;
    private final int secondHandId;
    private final int hoverLabelId;

    private final int faceX;
    private final int faceY;
    private final int faceSize;
    private final int centerX;
    private final int centerY;
    private final int radius;

    private int totalSeconds = 0;
    private boolean paused = false;
    private boolean finished = false;

    public Clock(Game game, int x, int y, int size, int priority) {
        this.game = game;
        this.faceX = x;
        this.faceY = y;
        this.faceSize = size;
        this.centerX = x + size / 2;
        this.centerY = y + size / 2;
        this.radius = (int) (size * 0.38f);

        faceId = this.game.graphics.addObject(x, y, size, size, "res\\textures\\interactive\\clock.png", priority, true);
        minuteHandId = this.game.graphics.addShape("line", centerX, centerY, 0, -radius, new Color(30, 30, 30), priority + 1, true);
        int secondRadius = (int) (size * 0.43f);
        secondHandId = this.game.graphics.addShape("line", centerX, centerY, 0, -secondRadius, new Color(200, 50, 50), priority + 2, true);
        int labelFontSize = Math.max(12, size / 8);
        hoverLabelId = this.game.graphics.addText("", x + size / 2 - 20, y - 8, labelFontSize, Color.WHITE, priority + 3, false);
        updateHands();

    }
    public void setTime(int minutes, int seconds) {
        setTimeSeconds(minutes * 60 + seconds);
    }

    public void setTimeSeconds(int seconds) {
        totalSeconds = Math.max(0, seconds);
        finished = (totalSeconds == 0);
        paused = false;
        updateHands();
        updateHoverLabel();
    }

    public void tick(int currentTick, int ticksPerSecond) {
        if (paused || finished) return;
        if (currentTick % ticksPerSecond != 0) return;

        if (totalSeconds > 0) {
            totalSeconds--;
            updateHands();
            updateHoverLabel();
        }
        if (totalSeconds == 0) {
            finished = true;
            updateHands();
        }
    }

    public void attachHoverListener() {
        game.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseDragged(MouseEvent e) {}
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean over = e.getX() >= faceX && e.getX() <= faceX + faceSize && e.getY() >= faceY && e.getY() <= faceY + faceSize;
                boolean clockVisible = game.graphics.getVisibility(faceId);
                game.graphics.setVisible(hoverLabelId, over && clockVisible && !finished);
            }
        });
    }

    public boolean isFinished() { return finished; }

    public int getTotalSecondsRemaining() { return totalSeconds; }

    public int getMinutesRemaining() { return totalSeconds / 60; }

    public int getSecondsRemaining() { return totalSeconds % 60; }

    public void setPaused(boolean paused) { this.paused = paused; }
    public boolean isPaused() { return paused; }

    public void setVisible(boolean visible) {
        game.graphics.setVisible(faceId, visible);
        game.graphics.setVisible(minuteHandId, visible);
        game.graphics.setVisible(secondHandId, visible);
        if (!visible) game.graphics.setVisible(hoverLabelId, false);
    }

    public void addToGroup(String groupName) {
        game.graphics.addToGroup(groupName, faceId);
        game.graphics.addToGroup(groupName, minuteHandId);
        game.graphics.addToGroup(groupName, secondHandId);
        game.graphics.addToGroup(groupName, hoverLabelId);
    }

    public void removeFromGroup(String groupName) {
        game.graphics.removeFromGroup(groupName, faceId);
        game.graphics.removeFromGroup(groupName, minuteHandId);
        game.graphics.removeFromGroup(groupName, secondHandId);
        game.graphics.removeFromGroup(groupName, hoverLabelId);
    }

    public int getFaceId() { return faceId; }
    public int getMinuteHandId() { return minuteHandId; }
    public int getSecondHandId() { return secondHandId; }
    public int getHoverLabelId() { return hoverLabelId; }

    private void updateHands() {
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;

        //Minute hand angle:
        //0 min = 12 o'clock (270 degrees in standard math, or -90 degrees)
        //each minute = 6 degrees clockwise
        //also moves slightly based on seconds

        double minuteAngleDeg = (mins % 60) * 6.0 + (secs / 60.0) * 6.0 - 90.0;
        double minuteRad = Math.toRadians(minuteAngleDeg);

        int minuteHandRadius = (int) (faceSize * 0.33f);
        int mDx = (int) (Math.cos(minuteRad) * minuteHandRadius);
        int mDy = (int) (Math.sin(minuteRad) * minuteHandRadius);

        //second hand angle
        //0 sec = 12 o'clock, each second = 6 degrees clickwise;

        double secondAngleDeg = secs * 6.0 - 90.0;
        double secondRad = Math.toRadians(secondAngleDeg);

        int secondHandRadius = (int) (faceSize * 0.38f);
        int sDx = (int) (Math.cos(secondRad) * secondHandRadius);
        int sDy = (int) (Math.sin(secondRad) * secondHandRadius);

        game.graphics.setX(minuteHandId, centerX);
        game.graphics.setY(minuteHandId, centerY);
        game.graphics.setWidth(minuteHandId, mDx);
        game.graphics.setHeight(minuteHandId, mDy);

        game.graphics.setX(secondHandId, centerX);
        game.graphics.setY(secondHandId, centerY);
        game.graphics.setWidth(secondHandId, sDx);
        game.graphics.setHeight(secondHandId, sDy);
    }

    private void updateHoverLabel() {
        int m = totalSeconds / 60;
        int s = totalSeconds % 60;
        game.graphics.setText(hoverLabelId, String.format("%d:%02d", m, s));

        int approxLabelWidth = String.format("%d:%02d", m, s).length() * (faceSize / 10);
        game.graphics.setX(hoverLabelId, faceX + faceSize / 2 - approxLabelWidth / 2);
    }
}