import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel{
    private int tick = 0;
    private Oddity interrogatedOddity = new Oddity(this);
    public GraphicsManager graphics = new GraphicsManager();
    public AudioManager audio = new AudioManager();
    private Background background = new Background(this);
    private Button button = new Button(1,50,50,50,50,"help me");

    public Game() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {

                button.keyPressed(e, "main_menu_to_checkpoint_menu");
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.mousePressed(e,"main_menu_to_checkpoint_menu");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setFocusable(true);
    }
    public void move() {
        tick++;
        interrogatedOddity.animation("idle", tick);
        if (tick >= 40) {
            tick = 0;
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        graphics.drawAll(g2d);
        g2d.drawRect(50,50,50,50);
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Incidents in a Sea of Oddities");
        Game game = new Game();
        frame.add(game);
        frame.setSize(1000, 700);
        game.graphics.addObject(0,0,1000,700,"res/textures/backgrounds/main_menu.jpg", 0, true);
        game.graphics.addObject(80,0,400,300,"", 0, true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        game.audio.play("res/music/test.wav");
        while (true)
        {
            game.move();
            game.repaint();
            Thread.sleep(10);
        }
    }
}
