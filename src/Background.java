import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {

    private static String setting = "main_menu";
    private static Game game;

    public Background(Game game) {
        this.game = game;
    }
    public static void changeBackground(String background) {

        setting = background;
        switch (setting) {

            case "main_menu":
                game.graphics.setImageDir(0,"res\\textures\\backgrounds\\main_menu.jpg");
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(0,"res\\textures\\backgrounds\\checkpoint_menu.jpg");
                break;
            case "results_menu":
                game.graphics.setImageDir(0,"res\\textures\\backgrounds\\results_menu.jpg");
                break;
            case "game_over_menu":
                game.graphics.setImageDir(0,"res\\textures\\backgrounds\\game_over_menu.jpg");
                break;
            default:
                System.out.println("Opps you got an error from background: the settings field is incorrect");
                break;
        }
    }

    public static String getBackground() {

        return setting;
    }
}
