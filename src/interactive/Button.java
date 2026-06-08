package interactive;

import display.CheckpointHealth;
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
    private boolean isHovered = false;

    public Button(Game game, int x, int y, int width, int height, String directory, int priority, String hoverText) {
        this.game = game;
        this.hoverText = hoverText;
        id = game.graphics.addObject(x,y,width,height, directory, priority, false);
    }

    public int getId() {
        return id;
    }
    public String getHoverText() {
        return hoverText;
    }
    public boolean isHoveredOver() {
        //System.out.println("click");
        Point m = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(m, game);
        if (m.x >= game.graphics.getX(id) && m.y >= game.graphics.getY(id) && m.x <= game.graphics.getX(id)+game.graphics.getWidth(id) && m.y <= game.graphics.getY(id)+game.graphics.getHeight(id)) {
            isHovered = true;
            //System.out.println("click hit");
        } else {
            isHovered = false;
            //System.out.println("click miss");
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
                    if (action.equals("open_difficulty_popup")) {
                        Popup.changeAndShowPopup("difficulty_popup", true);
                    }
                    if (action.equals("close_difficulty_popup")) {
                        Popup.changeAndShowPopup("", false);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }
                    if (action.equals("to_checkpoint_menu_difficulty_1")) {
                        Background.changeBackground("checkpoint_menu");
                        CheckpointHealth.setCheckpointHealth(5);
                        Popup.changeAndShowPopup("", false);
                    }
                    if (action.equals("to_checkpoint_menu_difficulty_2")) {
                        Background.changeBackground("checkpoint_menu");
                        CheckpointHealth.setCheckpointHealth(3);
                        Popup.changeAndShowPopup("", false);
                    }
                    if (action.equals("to_checkpoint_menu_difficulty_3")) {
                        Background.changeBackground("checkpoint_menu");
                        CheckpointHealth.setCheckpointHealth(1);
                        Popup.changeAndShowPopup("", false);
                    }

                    if (action.equals("open_settings_popup")) {
                        Popup.changeAndShowPopup("settings_popup", true);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }
                    if (action.equals("close_settings_popup")) {
                        Popup.changeAndShowPopup("", false);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }

                    if (action.equals("open_information_popup")) {
                        Popup.changeAndShowPopup("information_popup", true);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }
                    if (action.equals("close_information_popup")) {
                        Popup.changeAndShowPopup("", false);
                        //game.audio.playSFX("res\\music\\click.wav");
                    }
                    if (action.equals("popup_pager_next")) {
                        Popup.popupNextPage();
                    }
                    if (action.equals("popup_pager_previous")) {
                        Popup.popupPreviousPage();
                    }

                }

            }
        }
    }
}
