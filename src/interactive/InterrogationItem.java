package interactive;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class InterrogationItem extends Button {
    private static Game game;
    private static int x;
    private static int y;
    private static boolean isPickedUp = false;
    public InterrogationItem (Game game, int x, int y, int width, int height, String directory, int priority, String hoverText) {
        super(game, x, y, width, height, "", 2, "");
        this.game = game;
        this.x = x;
        this.y = y;
        game.graphics.setVisible(super.getId(), true);
    }
    public void trackToMouse () {
        Point m = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(m, game);
        //System.out.println(isPickedUp);
        System.out.println(game.graphics.getY(id));
        if (isPickedUp) {
            //game.graphics.setX(id, m.x);
            //game.graphics.setY(id, m.y);
        }
    }
    @Override
    public void mousePressed (MouseEvent e, String action) {

        if (isHoveredOver()) {
            if (game.graphics.clickAllowed(id)) {
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
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.pickup = null;
                    isPickedUp = false;
                    game.graphics.setX(id, x);
                    game.graphics.setY(id, y);
                }
            }
        }
    }
}
