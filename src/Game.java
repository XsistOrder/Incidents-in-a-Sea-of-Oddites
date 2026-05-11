import javax.swing.*;
import java.awt.*;

public class Game extends JPanel{
    public Game() {

    }
    public void move() {

    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("City Scape Assigment");
        Game game = new Game();

        frame.add(game);
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true)
        {

        }
    }
}
