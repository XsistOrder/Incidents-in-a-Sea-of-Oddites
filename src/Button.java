import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Button {
    private int id;
    private String hoverText;
    private static Game game;
    //private Rectangle bounds;
    private boolean isHovered = false;

    public Button(Game game, int x, int y, int width, int height,String directory, int priority,String hoverText) {
        this.game = game;
        this.hoverText = hoverText;
        //this.bounds = new Rectangle(x, y, width, height);
        id = game.graphics.addObject(x,y,width,height, directory, priority, true);
    }

    public int getId() {
        return id;
    }
    public String getHoverText() {
        return hoverText;
    }
    public boolean isHoveredOver() {
        System.out.println("1");
        Point m = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(m, game);
        if (m.x >= game.graphics.getX(id) && m.y >= game.graphics.getY(id) && m.x <= game.graphics.getX(id)+game.graphics.getWidth(id) && m.y <= game.graphics.getY(id)+game.graphics.getHeight(id)) {
            isHovered = true;
            System.out.println("2");
        } else {
            isHovered = false;
            System.out.println("3");
        }
        return isHovered;
    }
    // the action string signifies what method within other classes is triggered
    public void keyPressed (KeyEvent e, String action) {
        if (isHoveredOver()) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (action.equals("show_diffculty_popup")) {

                }
                if (action.equals("main_menu_to_checkpoint_menu") && Objects.equals(Background.getBackground(), "main_menu")) {
                    Background.changeBackground("checkpoint_menu");
                    //add checkpoint difficulty changer
                }
                if (action.equals("show_settings_popup")) {

                }
                if (action.equals("show_controls_popup")) {

                }

            }
        }
    }
    public void mousePressed (MouseEvent e, String action) {

        game.audio.playSFX("res\\music\\click.wav");
        if (isHoveredOver()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (action.equals("show_diffculty_popup")) {

                }
                if (action.equals("main_menu_to_checkpoint_menu") && Background.getBackground().equals("main_menu")) {
                    Background.changeBackground("checkpoint_menu");
                }
                if (action.equals("show_settings_popup")) {

                }
                if (action.equals("show_controls_popup")) {

                }


            }
        }
    }
}
