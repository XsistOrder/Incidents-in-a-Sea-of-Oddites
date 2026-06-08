package display;

import interactive.Button;
import interactive.Oddity;
import main.Game;

public class Background {

    private static String setting = "main_menu";
    private static int id;
    private static Game game;
    public static Button playButton;
    public static Button settingsButton;
    public static Button infomationButton;
    public static Button encyclopediaButton;
    public static Button documentationButton;
    public static Button departButton;
    public static Clock clock;
    //private static Button

    public Background(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\main_menu.jpg", 0, true);
        playButton = new Button(game,50,50,50,50, "res\\textures\\interactive\\button.jpg", 1,"help me");
        settingsButton = new Button(game, 120,120, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");
        infomationButton = new Button(game, 200,200, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");
        encyclopediaButton = new Button(game, 800,600, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");
        documentationButton = new Button(game, 500,600, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");


        clock = new Clock(game, 420, 120, 50, 15);
        clock.setTime(7, 0);
        clock.attachHoverListener();
        clock.setVisible(true);

        game.graphics.createGroup("main_menu_buttons");
        game.graphics.addToGroup("main_menu_buttons", playButton.getId());
        game.graphics.addToGroup("main_menu_buttons", settingsButton.getId());
        game.graphics.addToGroup("main_menu_buttons", infomationButton.getId());


        game.graphics.createGroup("checkpoint_menu_buttons");
        game.graphics.addToGroup("checkpoint_menu_buttons", game.syringe.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.magnifyingGlass.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.laserPointer.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.questioner.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.acid.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.crucifix.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.flashlight.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", game.woodenStake.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", encyclopediaButton.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", documentationButton.getId());
        clock.addToGroup("checkpoint_menu_buttons");

        game.graphics.createGroup("results_menu_buttons");


        game.graphics.createGroup("gameover_menu_buttons");


        game.graphics.createGroup("all_menu_buttons");
        game.graphics.mergeIntoGroup("main_menu_buttons", "all_menu_buttons");
        game.graphics.mergeIntoGroup("checkpoint_menu_buttons", "all_menu_buttons");
        game.graphics.mergeIntoGroup("results_menu_buttons", "all_menu_buttons");
        game.graphics.mergeIntoGroup("gameover_menu_buttons", "all_menu_buttons");
        game.graphics.addToGroup("all_menu_buttons", game.interrogatedOddity.getId());

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
                game.graphics.setVisible(CheckpointHealth.getId(), false);
                clock.setPaused(true);
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\checkpoint_menu.jpg");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("checkpoint_menu_buttons", true);
                game.graphics.setGroupVisible("checkpoint_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(false,true);
                game.graphics.setVisible(CheckpointHealth.getId(), true);
                clock.setPaused(false);
                break;
            case "results_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\results_menu.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("results_menu_buttons", true);
                game.graphics.setGroupVisible("results_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                game.graphics.setVisible(CheckpointHealth.getId(), false);
                clock.setPaused(true);
                Results.dayReset();
                break;
            case "game_over_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\gameover_menu.jpg");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("gameover_menu_buttons", true);
                game.graphics.setGroupVisible("gameover_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                game.graphics.setVisible(CheckpointHealth.getId(), false);
                clock.setPaused(true);
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
