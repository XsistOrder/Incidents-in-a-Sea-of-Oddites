import javax.swing.*;
import java.awt.*;

public class Game extends JPanel{
    Oddity interrogatedOddity = new Oddity();
    GraphicsManager graphics = new GraphicsManager();
    Background background = new Background();

    public Game() {
        Background.changeBackground("Main_Menu");
    }
    public void move() {

    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        background.paint(g2d);
        graphics.drawAll(g2d);
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("City Scape Assigment");
        Game game = new Game();
        frame.add(game);
        frame.setSize(1000, 700);
        game.graphics.addObject(0,0,1000,700,"res/textures/backgrounds/main_menu.jpg");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        while (true)
        {
            game.move();
            game.repaint();
            Thread.sleep(10);
        }
    }
}
