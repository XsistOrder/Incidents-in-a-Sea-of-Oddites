package display;

import interactive.Button;
import interactive.Item;
import interactive.Oddity;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

//call details.update every frame in move

public class Details {

    private final Game game;
    private final int labelId;

    private static final int OFFSET_X = 12;
    private static final int OFFSET_Y = -22;

    private int mouseX = 0;
    private int mouseY = 0;

    public Details(Game game) {
        this.game = game;

        labelId = game.graphics.addText("", 0, 0, 14, new Color(255, 240, 180), 100, false);
    }



    public void attachListener() {
        game.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) { update(e.getX(), e.getY()); }
            @Override
            public void mouseMoved(MouseEvent e) { update(e.getX(), e.getY()); }
        });
    }

    public void update() {
        update(mouseX, mouseY);
    }

    private void update(int mx, int my) {
        mouseX = mx;
        mouseY = my;

        if (!Background.getBackground().equals("checkpoint_menu") || !Popup.getPopup().isEmpty()) {
            hide();
            return;
        }

        String heldItem = game.pickup;

        if (isOverObject(game.interrogatedOddity.getId(), mx, my)) {
            if (heldItem.isEmpty()) {
                show("Oddity", mx, my);
            } else {
                show(itemOnOddityText(heldItem), mx, my);
            }
            return;
        }

        if (isOverItemAtRest(game.syringe, mx, my)) {
            show("Syringe", mx, my); return;
        }
        if (isOverItemAtRest(game.magnifyingGlass, mx, my)) {
            show("Magnifying Glass", mx, my); return;
        }
        if (isOverItemAtRest(game.laserPointer, mx, my)) {
            show("Laser Pointer", mx, my); return;
        }
        if (isOverItemAtRest(game.questioner, mx, my)) {
            show("Questioner", mx, my); return;
        }
        if (isOverItemAtRest(game.acid, mx, my)) {
            show("Acid", mx, my); return;
        }
        if (isOverItemAtRest(game.crucifix, mx, my)) {
            show("Crucifix", mx, my); return;
        }
        if (isOverItemAtRest(game.flashlight, mx, my)) {
            show("Flashlight", mx, my); return;
        }
        if (isOverItemAtRest(game.woodenStake, mx, my)) {
            show("Wooden Stake", mx, my); return;
        }

        if (isOverObject(Background.documentationButton.getId(), mx, my)) {
            show("View Documentation", mx, my); return;
        }
        if (isOverObject(Background.encyclopediaButton.getId(), mx, my)) {
            show("Open Encyclopedia", mx, my); return;
        }
        if (isOverObject(Background.departButton.getId(), mx, my)) {
            show("Depart Oddity", mx, my); return;
        }
        if (isOverObject(Background.pauseButton.getId(), mx, my)) {
            show("Pause", mx, my); return;
        }

        hide();
    }

    private String itemOnOddityText(String item) {
        switch (item) {
            case "syringe": return "Draw a blood sample";
            case "magnifying_glass": return "Examine closely";
            case "laser_pointer": return "Test reaction";
            case "questioner": return "Ask a question";
            case "acid": return "Dispatch";
            case "crucifix": return "Dispatch";
            case "flashlight": return "Dispatch";
            case "wooden_stake": return "Dispatch";
            default: return "Use " + item + " on Oddity";
        }
    }

    private void show(String text, int mx, int my) {
        game.graphics.setText(labelId, text);
        game.graphics.setX(labelId, mx + OFFSET_X);
        game.graphics.setY(labelId, my + OFFSET_Y);
        game.graphics.setVisible(labelId, true);
    }

    private void hide() {
        game.graphics.setVisible(labelId, false);
    }

    private boolean isOverObject(int id, int mx, int my) {

    }
}
