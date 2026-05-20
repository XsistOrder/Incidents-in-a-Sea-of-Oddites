import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel{
    private int tick = 0;

    public GraphicsManager graphics = new GraphicsManager();
    public AudioManager audio = new AudioManager();
    public Oddity interrogatedOddity = new Oddity(this, 80, 0, 300, 400, 2);
    private Background background = new Background(this, 0, 0, 1000, 700);
    private Button playButton = new Button(this,50,50,50,50, "res\\textures\\interactive\\button.jpg", 1,"help me");
    private Button settingsButton = new Button(this, 120,120, 50,50, "res\\textures\\interactive\\button.jpg", 1,"placeholder");


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

                playButton.keyPressed(e, "show_diffculty_popup");
                settingsButton.keyPressed(e, "show_settings_popup");
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                playButton.mousePressed(e,"show_diffculty_popup");
                settingsButton.mousePressed(e, "show_settings_popup");
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
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Incidents in a Sea of Oddities");
        Game game = new Game();
        frame.add(game);
        frame.setSize(1000, 700);
        game.graphics.addObject(500,350, 500, 450, "res\\textures\\menus\\settings_menu.jpg", 0, false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //game.audio.play("res\\music\\test.wav");

        while (true)
        {
            game.move();
            game.repaint();
            Thread.sleep(10);
        }
    }
}
