package display;

import interactive.Button;
import main.Game;

public class Popup {
    private static String setting = "settings_popup";
    private static int id;
    private static int id2;
    private static Game game;
    public static Button closeButton;
    public static Button diffculty1Button;
    public static Button diffculty2Button;
    public static Button diffculty3Button;


    public Popup(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\settings_popup.png", 20, false);
        id2 = game.graphics.addObject(0,0, game.getWidth(), game.getHeight(),"res\\textures\\menus\\popup_overlay.png", 19, false);
        //game.graphics.setAlpha(id2, 0.5f);
        closeButton = new Button(game, 900,80,200,200, "res\\textures\\interactive\\close_button.png", 25, "helphelphelp");
        diffculty1Button = new Button(game, 200,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");
        diffculty2Button = new Button(game, 400,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");
        diffculty3Button = new Button(game, 600,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");

        game.graphics.createGroup("settings_popup_buttons");
        game.graphics.addToGroup("settings_popup_buttons", closeButton.getId());

        game.graphics.createGroup("difficulty_popup_buttons");
        game.graphics.addToGroup("difficulty_popup_buttons", closeButton.getId());
        game.graphics.addToGroup("difficulty_popup_buttons", diffculty1Button.getId());
        game.graphics.addToGroup("difficulty_popup_buttons", diffculty2Button.getId());
        game.graphics.addToGroup("difficulty_popup_buttons", diffculty3Button.getId());

        game.graphics.createGroup("pause_popup_buttons");
        game.graphics.addToGroup("pause_popup_buttons", closeButton.getId());

        game.graphics.createGroup("information_popup_buttons");
        game.graphics.addToGroup("information_popup_buttons", closeButton.getId());

        game.graphics.createGroup("documentation_popup_buttons");
        game.graphics.addToGroup("documentation_popup_buttons", closeButton.getId());

        game.graphics.createGroup("encyclopedia_popup_buttons");
        game.graphics.addToGroup("encyclopedia_popup_buttons", closeButton.getId());

        game.graphics.createGroup("all_popup_buttons");
        game.graphics.mergeIntoGroup("settings_popup_buttons", "all_popup_buttons");
        game.graphics.mergeIntoGroup("difficulty_popup_buttons", "all_popup_buttons");
        game.graphics.mergeIntoGroup("information_popup_buttons", "all_popup_buttons");
        game.graphics.mergeIntoGroup("documentation_popup_buttons", "all_popup_buttons");
        game.graphics.mergeIntoGroup("encyclopedia_popup_buttons", "all_popup_buttons");
    }

    public static void changeAndShowPopup(String popup, boolean visibility) {

        setting = popup;
        game.graphics.setVisible(id, visibility);
        game.graphics.setVisible(id2, visibility);
        switch (setting) {

            case "settings_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\settings_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("settings_popup_buttons", visibility);
                game.graphics.setGroupVisible("settings_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            case "difficulty_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\difficulty_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("difficulty_popup_buttons", visibility);
                game.graphics.setGroupVisible("difficulty_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            case "pause_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\pause_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("pause_popup_buttons", visibility);
                game.graphics.setGroupVisible("pause_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            case "information_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\difficulty_menu.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("information_popup_buttons", visibility);
                game.graphics.setGroupVisible("information_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            case "documentation_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\difficulty_menu.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("documentation_popup_buttons", visibility);
                game.graphics.setGroupVisible("documentation_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(false,true);
                break;
            case "encyclopedia_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\difficulty_menu.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("encyclopedia_popup_buttons", visibility);
                game.graphics.setGroupVisible("encyclopedia_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(false,true);
                break;
            default:
                game.graphics.setGroupClickable("" + Background.getBackground() + "_buttons", true);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                //Impatience.pauseFillAndSetVisibility(false,true);
                break;
        }
    }

    public static String getPopup() {

        return setting;
    }
}
