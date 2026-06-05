package main;

import display.*;
import display.Popup;
import interactive.Button;
import interactive.Item;
import interactive.Oddity;
import interactive.Slider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel{
    private int tick = 0;
    public String pickup;
    public GraphicsManager graphics = new GraphicsManager();
    public AudioManager audio = new AudioManager();
    public Oddity interrogatedOddity = new Oddity(this, 80, 0, 300, 400, 2);
    public Item syringe = new Item(this, 0, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item magnifyingGlass = new Item(this, 50, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item laserPointer = new Item(this, 100, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item questioner = new Item(this, 150, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Background background = new Background(this, 0, 0, 1000, 700);
    private Popup popup = new Popup(this, 80, 80, 820, 540);
    private Details details = new Details();
    private Impatience impatienceMeter = new Impatience(this,700,350, 50,100);
    public static int gameWidth = 1000;
    public static int gameHeight = 700;
    public Game() {
        //controls menu you start on
        Background.changeBackground("main_menu");

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {

                Popup.closeButton.keyPressed(e, "close_" + Popup.getPopup() + "");
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Background.playButton.mousePressed(e,"open_difficulty_popup");
                Background.settingsButton.mousePressed(e, "open_settings_popup");
                Background.infomationButton.mousePressed(e, "open_information_popup");
                Popup.diffculty1Button.mousePressed(e, "main_menu_to_checkpoint_menu");
                //Popup.diffculty2Button.mousePressed(e, "main_menu_to_checkpoint_menu");
                //Popup.diffculty3Button.mousePressed(e, "main_menu_to_checkpoint_menu");
                Popup.closeButton.mousePressed(e, "close_" + Popup.getPopup() + "");
                interrogatedOddity.mousePressed(e, "oddity_clicked");
                syringe.mousePressed(e, "pickup_syringe");
                magnifyingGlass.mousePressed(e, "pickup_magnifying_glass");
                laserPointer.mousePressed(e, "pickup_laser_pointer");
                questioner.mousePressed(e, "pickup_questioner");

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


        if (Background.getBackground().equals("checkpoint_menu")) {
            Oddity.askAnimation(tick);
            Oddity.animation("idle", tick);
            syringe.trackToMouse();
            magnifyingGlass.trackToMouse();
            laserPointer.trackToMouse();
            questioner.trackToMouse();
            Impatience.fill(tick);
            CheckpointHealth.checkCheckpointHealth();
            Background.clock.tick(tick, 20);
        }
        if (Background.clock.isFinished()) {
            Background.changeBackground("results_menu");
        }


        if (tick >= 40) {
            tick = 0;
        }
        //System.out.println(graphics.nextId);
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        graphics.drawAll(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Incidents in a Sea of Oddities");
        Game game = new Game();
        frame.add(game);
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //game.audio.play("res\\music\\test.wav");
        gameWidth = game.getWidth();
        gameHeight = game.getHeight();


        while (true)
        {
            game.move();
            game.repaint();
            Thread.sleep(10);
        }
    }
}
