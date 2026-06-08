package display;

import interactive.Button;
import interactive.Slider;
import main.Game;

import java.awt.*;

public class Popup {
    private static String setting = "settings_popup";
    private static int id;
    private static int id2;
    private static int maxPages;
    private static int currentPage;
    private static Game game;
    public static Button closeButton;
    public static Button diffculty1Button;
    public static Button diffculty2Button;
    public static Button diffculty3Button;
    public static Button pagerNext;
    public static Button pagerPrevious;
    private static int musicText;
    private static int SFXText;

    public Popup(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\settings_popup.png", 20, false);
        id2 = game.graphics.addObject(0,0, 1000, 700,"res\\textures\\menus\\popup_overlay.png", 19, false);
        game.graphics.setAlpha(id2, 0.5f);
        closeButton = new Button(game, 900,80,200,200, "res\\textures\\interactive\\close_button.png", 25, "helphelphelp");
        diffculty1Button = new Button(game, 200,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");
        diffculty2Button = new Button(game, 400,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");
        diffculty3Button = new Button(game, 600,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");

        pagerNext = new Button(game, 600,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");
        pagerPrevious = new Button(game, 400,200,200,200, "res\\textures\\interactive\\difficulty_1_button.jpg", 25, "helphelphelp");

        musicText = game.graphics.addText("Music Volume", 175, 225, 25, Color.WHITE, 25, false);
        SFXText = game.graphics.addText("SFX Volume", 175, 325, 25, Color.WHITE, 25, false);
        Slider musicVolume = new Slider(game, 150, 250, 250, 10, 0, 100, 25, true, true);
        Slider SFXVolume = new Slider(game, 150, 350, 250, 10, 0, 100, 25, true, true);

        game.graphics.createGroup("settings_popup_buttons");
        game.graphics.addToGroup("settings_popup_buttons", closeButton.getId());
        game.graphics.addToGroup("settings_popup_buttons", musicVolume.getFillId());
        game.graphics.addToGroup("settings_popup_buttons", musicVolume.getThumbId());
        game.graphics.addToGroup("settings_popup_buttons", musicVolume.getLabelId());
        game.graphics.addToGroup("settings_popup_buttons", musicVolume.getTrackId());
        game.graphics.addToGroup("settings_popup_buttons", musicText);
        game.graphics.addToGroup("settings_popup_buttons", SFXText);
        game.graphics.addToGroup("settings_popup_buttons", SFXVolume.getFillId());
        game.graphics.addToGroup("settings_popup_buttons", SFXVolume.getThumbId());
        game.graphics.addToGroup("settings_popup_buttons", SFXVolume.getLabelId());
        game.graphics.addToGroup("settings_popup_buttons", SFXVolume.getTrackId());
        musicVolume.setVisible(false);
        musicVolume.setDraggable(true);
        musicVolume.attachListeners();
        SFXVolume.setVisible(false);
        SFXVolume.setDraggable(true);
        SFXVolume.attachListeners();

        musicVolume.setOnChangeListener(newValue -> {
            float volumePercentage = newValue / 100f;
            game.audio.setVolume(volumePercentage);
        });

        SFXVolume.setOnChangeListener(newValue -> {
            float SFXPercentage = newValue / 100f;
            game.audio.setSFXVolume(SFXPercentage);
        });


        game.graphics.createGroup("difficulty_popup_buttons");
        game.graphics.addToGroup("difficulty_popup_buttons", closeButton.getId());
        game.graphics.addToGroup("difficulty_popup_buttons", diffculty1Button.getId());
        game.graphics.addToGroup("difficulty_popup_buttons", diffculty2Button.getId());
        game.graphics.addToGroup("difficulty_popup_buttons", diffculty3Button.getId());

        game.graphics.createGroup("pause_popup_buttons");
        game.graphics.addToGroup("pause_popup_buttons", closeButton.getId());

        game.graphics.createGroup("information_popup_buttons");
        game.graphics.addToGroup("information_popup_buttons", closeButton.getId());
        game.graphics.addToGroup("information_popup_buttons", pagerNext.getId());
        game.graphics.addToGroup("information_popup_buttons", pagerPrevious.getId());

        game.graphics.createGroup("documentation_popup_buttons");
        game.graphics.addToGroup("documentation_popup_buttons", closeButton.getId());

        game.graphics.createGroup("encyclopedia_popup_buttons");
        game.graphics.addToGroup("encyclopedia_popup_buttons", closeButton.getId());
        game.graphics.addToGroup("encyclopedia_popup_buttons", pagerNext.getId());
        game.graphics.addToGroup("encyclopedia_popup_buttons", pagerPrevious.getId());

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
                maxPages=0;
                game.graphics.setImageDir(id, "res\\textures\\menus\\settings_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("settings_popup_buttons", visibility);
                game.graphics.setGroupVisible("settings_popup_buttons", visibility);



                Impatience.pauseFillAndSetVisibility(true,false);

                break;
            case "difficulty_popup":
                maxPages=0;
                game.graphics.setImageDir(id, "res\\textures\\menus\\difficulty_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("difficulty_popup_buttons", visibility);
                game.graphics.setGroupVisible("difficulty_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);

                break;
            case "pause_popup":
                maxPages=0;
                game.graphics.setImageDir(id, "res\\textures\\menus\\pause_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("pause_popup_buttons", visibility);
                game.graphics.setGroupVisible("pause_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);

                break;
            case "information_popup":
                maxPages=4;
                currentPage=1;
                game.graphics.setImageDir(id, "res\\textures\\menus\\information_popup_"+ currentPage +".png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("information_popup_buttons", visibility);
                game.graphics.setGroupVisible("information_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(true,false);

                break;
            case "documentation_popup":
                maxPages=0;
                game.graphics.setImageDir(id, "res\\textures\\menus\\documentation_popup.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("documentation_popup_buttons", visibility);
                game.graphics.setGroupVisible("documentation_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(false,true);
                break;
            case "encyclopedia_popup":
                maxPages=10;
                currentPage=1;
                game.graphics.setImageDir(id, "res\\textures\\menus\\encyclopedia_popup_"+ currentPage + ".png");

                game.graphics.setGroupClickable("all_menu_buttons", false);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                game.graphics.setGroupClickable("encyclopedia_popup_buttons", visibility);
                game.graphics.setGroupVisible("encyclopedia_popup_buttons", visibility);

                Impatience.pauseFillAndSetVisibility(false,true);

                break;
            default:
                maxPages=0;
                currentPage=1;
                game.graphics.setGroupClickable("" + Background.getBackground() + "_buttons", true);

                game.graphics.setGroupClickable("all_popup_buttons", false);
                game.graphics.setGroupVisible("all_popup_buttons", false);

                //Impatience.pauseFillAndSetVisibility(false,true);
                break;
        }
    }
    public static void popupNextPage () {
        if (currentPage < maxPages) {
            currentPage++;
            game.graphics.setImageDir(id, "res\\textures\\menus\\" + setting +"_"+ currentPage + ".png");
        }
    }
    public static void popupPreviousPage () {
        if (currentPage > 1) {
            currentPage--;
            game.graphics.setImageDir(id, "res\\textures\\menus\\" + setting +"_"+ currentPage + ".png");
            //hi
        }
    }
    public static String getPopup() {

        return setting;
    }


}