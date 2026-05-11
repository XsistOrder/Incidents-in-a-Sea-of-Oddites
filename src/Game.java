import javax.swing.*;

public class Game extends JPanel{
    public Game(){

    }
    public void move() {

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
