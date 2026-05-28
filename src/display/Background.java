package display;

import interactive.Button;
import main.Game;

public class Background {

    private static String setting = "main_menu";
    private static int id;
    private static Game game;
    public static Button playButton;
    public static Button settingsButton;
    public static Button infomationButton;
    //private static Button

    public Background(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\main_menu.jpg", 0, true);
        playButton = new Button(game,50,50,50,50, "res\\textures\\interactive\\button.jpg", 1,"help me");
        settingsButton = new Button(game, 120,120, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");
        infomationButton = new Button(game, 120,120, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");


        game.graphics.createGroup("main_menu_buttons");
        game.graphics.addToGroup("main_menu_buttons", playButton.getId());
        game.graphics.addToGroup("main_menu_buttons", settingsButton.getId());


        game.graphics.createGroup("checkpoint_menu_buttons");


        game.graphics.createGroup("results_menu_buttons");


        game.graphics.createGroup("gameover_menu_buttons");


        game.graphics.createGroup("all_menu_buttons");
        game.graphics.mergeIntoGroup("main_menu_buttons", "all_menu_buttons");
        game.graphics.mergeIntoGroup("checkpoint_menu_buttons", "all_menu_buttons");
        game.graphics.mergeIntoGroup("results_menu_buttons", "all_menu_buttons");
        game.graphics.mergeIntoGroup("gameover_menu_buttons", "all_menu_buttons");
    }
    public static void changeBackground(String background) {

        setting = background;
        switch (setting) {

            case "main_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\main_menu.jpg");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("main_menu_buttons", true);
                game.graphics.setGroupVisible("main_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\checkpoint_menu.jpg");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("checkpoint_menu_buttons", true);
                game.graphics.setGroupVisible("checkpoint_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(false,true);
                break;
            case "results_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\results_menu.jpg");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("results_menu_buttons", true);
                game.graphics.setGroupVisible("results_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            case "game_over_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\gameover_menu.jpg");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("gameover_menu_buttons", true);
                game.graphics.setGroupVisible("gameover_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                break;
            default:
                System.err.println("da background ain't right bro");
                break;
        }
    }

    public static String getBackground() {

        return setting;
    }
}
