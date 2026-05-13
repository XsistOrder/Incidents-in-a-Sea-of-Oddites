import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {

    private static String setting = "main_menu";
    private BufferedImage mainMenu = null;
    private BufferedImage checkpointMenu = null;
    private BufferedImage resultsMenu = null;
    private BufferedImage gameOverMenu = null;
    private static Game game;

    public Background(Game game) {
        try {
            mainMenu = ImageIO.read(new File("res\\textures\\backgrounds\\main_menu.jpg"));
            checkpointMenu = ImageIO.read(new File("res\\textures\\backgrounds\\checkpoint_menu.jpg"));
        } catch (IOException e) {

        }
        this.game = game;
    }
    public static void changeBackground(String background) {

        setting = background;
        switch (setting) {

            case "main_menu":
                //g2d.drawImage(mainMenu, 0, 0, 1000, 700, null);
                //graphics.addObject(0,0,1000,700, "res\\textures\\backgrounds\\main_menu.jpg", 5);
                game.graphics.setImageDir(0,"res\\textures\\backgrounds\\main_menu.jpg");
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(0,"res\\textures\\backgrounds\\checkpoint_menu.jpg");
                break;
            case "results_menu":
                break;
            case "game_over_menu":
                break;
            default:
                break;
        }
    }

    public static String getBackground() {

        return setting;
    }
}
