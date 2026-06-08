package interactive;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Item extends Button {
    private static Game game;
    private int x;
    private int y;
    private boolean isPickedUp = false;
    public Item (Game game, int x, int y, int width, int height, String directory, int priority, String hoverText) {
        super(game, x, y, width, height, directory, priority, hoverText);
        this.game = game;
        this.x = x;
        this.y = y;
        game.graphics.setVisible(super.getId(), true);
    }
    public void trackToMouse () {
        Point m = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(m, game);
        //System.out.println(isPickedUp);
        if (isPickedUp) {
            game.graphics.setClickable(getId(), false);
            game.graphics.setX(getId(), m.x);
            game.graphics.setY(getId(), m.y);
        } else {
            game.graphics.setClickable(getId(), true);
        }
    }
    @Override
    public void mousePressed (MouseEvent e, String action) {

        if (game.graphics.clickAllowed(getId())){
            if (isHoveredOver() && game.pickup.equals("")) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (action.equals("pickup_syringe")) {
                        System.out.println("syringe");
                        game.pickup = "syringe";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_magnifying_glass")) {
                        System.out.println("magnifying_glass");
                        game.pickup = "magnifying_glass";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_laser_pointer")) {
                        System.out.println("laser_pointer");
                        game.pickup = "laser_pointer";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_questioner")) {
                        System.out.println("questioner");
                        game.pickup = "questioner";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_acid")) {
                        System.out.println("acid");
                        game.pickup = "acid";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_crucifix")) {
                        System.out.println("crucifix");
                        game.pickup = "crucifix";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_flashlight")) {
                        System.out.println("flashlight");
                        game.pickup = "flashlight";
                        isPickedUp = true;
                    }
                    if (action.equals("pickup_wooden_stake")) {
                        System.out.println("wooden_stake");
                        game.pickup = "wooden_stake";
                        isPickedUp = true;
                    }
                }

            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            game.pickup = "";
            System.out.println(game.pickup);
            isPickedUp = false;
            game.graphics.setX(getId(), x);
            game.graphics.setY(getId(), y);
        }
    }
}