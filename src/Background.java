import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {

    private static String setting;
    GraphicsManager graphics = new GraphicsManager();
    private BufferedImage mainMenu = null;
    private BufferedImage checkpointMenu = null;
    private BufferedImage resultsMenu = null;
    private BufferedImage gameOverMenu = null;

    public Background () {
        try {
            mainMenu = ImageIO.read(new File("res\\textures\\backgrounds\\main_menu.jpg"));
        } catch (IOException e) {

        }
    }
    public static void changeBackground(String background) {

        setting = background;
    }

    public String getBackground() {

        return setting;
    }
    public void paint(Graphics2D g2d) {
        switch (setting) {
            case "Main_Menu"
        }
        g2d.drawImage(mainMenu, 0, 0, 1000, 700, null);
        //graphics.addObject(0,0,1000,700, "res\\textures\\backgrounds\\main_menu.jpg", 5);
    }

}
