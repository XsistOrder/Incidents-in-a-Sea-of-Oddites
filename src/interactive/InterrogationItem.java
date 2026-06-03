package interactive;

import main.Game;

import java.awt.event.MouseEvent;

public class InterrogationItem extends Button {
    private static Game game;
    private static int id;
    public InterrogationItem (Game game, int x, int y, int width, int height, String directory, int priority, String hoverText) {
        super(game, x, y, width, height, directory, priority, hoverText);
        this.game = game;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public void mousePressed (MouseEvent e, String action) {

        if (isHoveredOver()) {
            if (game.graphics.clickAllowed(id)) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (action.equals("pickup_syringe")) {
                        System.out.println("syringe");
                        game.pickup = "syringe";
                    }
                    if (action.equals("pickup_magnifying_glass")) {
                        System.out.println("magnifying_glass");
                        game.pickup = "magnifying_glass";
                    }
                    if (action.equals("pickup_laser_pointer")) {
                        System.out.println("laser_pointer");
                        game.pickup = "laser_pointer";
                    }
                    if (action.equals("pickup_questioner")) {
                        System.out.println("questioner");
                        game.pickup = "questioner";
                    }
                }

            }
        }
    }
}
