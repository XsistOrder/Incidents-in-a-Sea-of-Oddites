package display;

import interactive.Button;
import interactive.Oddity;
import main.Game;

public class Background {

    private static String setting = "main_menu";
    private static int id;
    private static int id2;
    private static Game game;
    public static Button playButton;
    public static Button resumePlayButton;
    public static Button settingsButton;
    public static Button infomationButton;
    public static Button quitButton;
    public static Button encyclopediaButton;
    public static Button documentationButton;
    public static Button departButton;
    public static Button pauseButton;
    public static Button newDayButton;
    public static Button newGameButton;
    public static Button returnMenuButton;
    public static Clock clock;
    //private static Button

    public Background(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\main_menu_1.png", 0, true);
        id2 = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\main_menu_2.png", 0, true);
        playButton = new Button(game,50,100,50,50, "res\\textures\\interactive\\play_button.png", 1,"help me");
        resumePlayButton = new Button(game,300,50,50,50, "res\\textures\\interactive\\resume_button.png", 1,"help me");
        quitButton = new Button(game,400,50,50,50, "res\\textures\\interactive\\quit_button.png", 1,"help me");
        settingsButton = new Button(game, 120,120, 50,50, "res\\textures\\interactive\\settings_button.png", 1,"placeholder");
        infomationButton = new Button(game, 200,200, 50,50, "res\\textures\\interactive\\informtation_button.png", 1,"placeholder");
        encyclopediaButton = new Button(game, 800,600, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");
        documentationButton = new Button(game, 500,600, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");
        departButton = new Button(game,950,50,50,50, "res\\textures\\interactive\\button.jpg", 1,"help me");
        pauseButton = new Button(game,50,50,50,50, "res\\textures\\interactive\\button.jpg", 1,"help me");
        newDayButton = new Button(game, 500,600, 50,50, "res\\textures\\interactive\\new_day_button.png", 1,"placeholder");
        newGameButton = new Button(game, 500,600, 50,50, "res\\textures\\interactive\\new_game_button.png", 1,"placeholder");
        returnMenuButton = new Button(game, 500,700, 50,50, "res\\textures\\interactive\\return_main_menu_button.png", 1,"placeholder");

        clock = new Clock(game, 420, 120, 50, 15);
        clock.setTime(7, 0);
        clock.attachHoverListener();
        clock.setVisible(true);

        game.graphics.createGroup("main_menu_buttons");
        game.graphics.addToGroup("main_menu_buttons", playButton.getId());
        game.graphics.addToGroup("main_menu_buttons", resumePlayButton.getId());
        game.graphics.addToGroup("main_menu_buttons", quitButton.getId());
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
        game.graphics.addToGroup("checkpoint_menu_buttons", departButton.getId());
        game.graphics.addToGroup("checkpoint_menu_buttons", pauseButton.getId());
        clock.addToGroup("checkpoint_menu_buttons");

        game.graphics.createGroup("results_menu_buttons");
        game.graphics.addToGroup("results_menu_buttons", newDayButton.getId());
        game.graphics.addToGroup("results_menu_buttons", returnMenuButton.getId());


        game.graphics.createGroup("gameover_menu_buttons");
        game.graphics.addToGroup("gameover_menu_buttons", newGameButton.getId());
        game.graphics.addToGroup("gameover_menu_buttons", returnMenuButton.getId());

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
                game.graphics.setImageDir(id,"res\\textures\\menus\\main_menu_1.png");
                game.graphics.setImageDir(id2,"res\\textures\\menus\\main_menu_2.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("main_menu_buttons", true);
                game.graphics.setGroupVisible("main_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                game.graphics.setVisible(CheckpointHealth.getId(), false);
                Oddity.setPaused(true);
                clock.setPaused(true);
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\checkpoint_menu_1.png");
                game.graphics.setImageDir(id2,"res\\textures\\menus\\checkpoint_menu_2.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("checkpoint_menu_buttons", true);
                game.graphics.setGroupVisible("checkpoint_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(false,true);
                game.graphics.setVisible(CheckpointHealth.getId(), true);
                Oddity.setPaused(false);
                clock.setPaused(false);
                break;
            case "results_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\results_menu_1.png");
                game.graphics.setImageDir(id2,"res\\textures\\menus\\results_menu_2.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("results_menu_buttons", true);
                game.graphics.setGroupVisible("results_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                game.graphics.setVisible(CheckpointHealth.getId(), false);
                Oddity.setPaused(true);
                clock.setPaused(true);
                Results.dayReset();
                break;
            case "game_over_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\gameover_menu_1.png");
                game.graphics.setImageDir(id2,"res\\textures\\menus\\gameover_menu_2.png");

                game.graphics.setGroupClickable("all_menu_buttons", false);
                game.graphics.setGroupVisible("all_menu_buttons", false);

                game.graphics.setGroupClickable("gameover_menu_buttons", true);
                game.graphics.setGroupVisible("gameover_menu_buttons", true);

                Impatience.pauseFillAndSetVisibility(true,false);
                game.graphics.setVisible(CheckpointHealth.getId(), false);
                Oddity.setPaused(true);
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