package interactive;

import display.Popup;
import main.Game;
import display.Background;

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

    public Button(Game game, int x, int y, int width, int height, String directory, int priority, String hoverText) {
        this.game = game;
        this.hoverText = hoverText;
        //this.bounds = new Rectangle(x, y, width, height);
        id = game.graphics.addObject(x,y,width,height, directory, priority, false);
    }

    public int getId() {
        return id;
    }
    public String getHoverText() {
        return hoverText;
    }
    public boolean isHoveredOver() {
        System.out.println("click");
        Point m = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(m, game);
        if (m.x >= game.graphics.getX(id) && m.y >= game.graphics.getY(id) && m.x <= game.graphics.getX(id)+game.graphics.getWidth(id) && m.y <= game.graphics.getY(id)+game.graphics.getHeight(id)) {
            isHovered = true;
            System.out.println("click hit");
        } else {
            isHovered = false;
            System.out.println("click miss");
        }
        return isHovered;
    }
    // the action string signifies what method within other classes is triggered
    public void keyPressed (KeyEvent e, String action) {
        if (game.graphics.clickAllowed(id)) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                if (action.equals("close_settings_popup")) {
                    Popup.changeAndShowPopup("settings_popup", false);
                }
                if (action.equals("close_difficulty_popup")) {
                    Popup.changeAndShowPopup("difficulty_popup", false);
                }

            }
        }
    }
    public void mousePressed (MouseEvent e, String action) {

        if (isHoveredOver()) {
            if (game.graphics.clickAllowed(id)) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (action.equals("open_diffculty_popup")) {
                        Popup.changeAndShowPopup("difficulty_popup", true);
                    }
                    if (action.equals("main_menu_to_checkpoint_menu") && Background.getBackground().equals("main_menu")) {
                        Background.changeBackground("checkpoint_menu");
                    }
                    if (action.equals("open_settings_popup")) {
                        Popup.changeAndShowPopup("settings_popup", true);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }
                    if (action.equals("close_settings_popup")) {
                        Popup.changeAndShowPopup("settings_popup", false);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }
                }

            }
        }
    }
}
