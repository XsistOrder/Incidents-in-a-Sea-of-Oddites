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

        faceId = game.graphics.addObject(x, y, size, size, "res\\textures\\interactive\\clock.png", priority, true);
        minuteHandId = game.graphics.addShape("line", centerX, centerY, 0, -radius, new Color(30, 30, 30), priority + 1, true);
        int secondRadius = (int) (size * 0.43f);
        secondHandId = game.graphics.addShape("line", centerX, centerY, 0, -secondRadius, new Color(200, 50, 50), priority + 2, true);
        int labelFontSize = Math.max(12, size / 8);
        hoverLabelId = game.graphics.addText("", x + size / 2 - 20, y - 8, labelFontSize, Color.WHITE, priority + 3, false);
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

            }
        }
