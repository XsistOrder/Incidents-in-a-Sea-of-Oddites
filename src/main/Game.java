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
    public String pickup = "";
    public GraphicsManager graphics = new GraphicsManager();
    public AudioManager audio = new AudioManager();
    public Oddity interrogatedOddity = new Oddity(this, 80, 0, 300, 400, 2);
    public Item syringe = new Item(this, 0, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item magnifyingGlass = new Item(this, 50, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item laserPointer = new Item(this, 100, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item questioner = new Item(this, 150, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item acid = new Item(this, 350, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item crucifix = new Item(this, 400, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item flashlight = new Item(this, 450, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
    public Item woodenStake = new Item(this, 500, 500, 50, 50, "res\\textures\\interactive\\button.jpg", 2, "gulp");
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

                Popup.closeButton.keyPressed(e, "close_popup");
                Background.pauseButton.keyPressed(e, "open_pause_popup");
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Background.playButton.mousePressed(e,"open_difficulty_popup");
                Background.resumePlayButton.mousePressed(e, "resume_checkpoint");
                Background.quitButton.mousePressed(e, "close_game");
                Background.settingsButton.mousePressed(e, "open_settings_popup");
                Background.infomationButton.mousePressed(e, "open_information_popup");
                Background.encyclopediaButton.mousePressed(e, "open_encyclopedia_popup");
                Background.documentationButton.mousePressed(e, "open_documentation_popup");
                Background.departButton.mousePressed(e, "depart_oddity");
                Background.pauseButton.mousePressed(e, "open_pause_popup");
                Background.newDayButton.mousePressed(e, "open_checkpoint_menu");
                Background.newGameButton.mousePressed(e, "open_checkpoint_menu");
                Background.returnMenuButton.mousePressed(e, "");
                Popup.diffculty1Button.mousePressed(e, "to_checkpoint_menu_difficulty_1");
                Popup.diffculty2Button.mousePressed(e, "to_checkpoint_menu_difficulty_2");
                Popup.diffculty3Button.mousePressed(e, "to_checkpoint_menu_difficulty_3");
                Popup.pagerNextButton.mousePressed(e, "popup_pager_next");
                Popup.pagerPreviousButton.mousePressed(e, "popup_pager_previous");
                Popup.pauseSettingsButton.mousePressed(e, "open_settings_popup");
                Popup.pauseInformationButton.mousePressed(e, "open_information_popup");
                Popup.quitButton.mousePressed(e, "close_game");
                Popup.closeButton.mousePressed(e, "close_popup");
                interrogatedOddity.mousePressed(e, "oddity_clicked");
                syringe.mousePressed(e, "pickup_syringe");
                magnifyingGlass.mousePressed(e, "pickup_magnifying_glass");
                laserPointer.mousePressed(e, "pickup_laser_pointer");
                questioner.mousePressed(e, "pickup_questioner");
                acid.mousePressed(e, "pickup_acid");
                crucifix.mousePressed(e, "pickup_crucifix");
                flashlight.mousePressed(e, "pickup_flashlight");
                woodenStake.mousePressed(e, "pickup_wooden_stake");

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


        if (Background.getBackground().equals("checkpoint_menu") ) {
            Oddity.askAnimation(tick, false);
            Oddity.loopAnimation("idle", tick);

            syringe.trackToMouse();
            magnifyingGlass.trackToMouse();
            laserPointer.trackToMouse();
            questioner.trackToMouse();
            acid.trackToMouse();
            crucifix.trackToMouse();
            flashlight.trackToMouse();
            woodenStake.trackToMouse();


            Impatience.fill(tick);
            CheckpointHealth.checkCheckpointHealth();
            Background.clock.tick(tick, 20);

            if (Background.clock.isFinished() && Oddity.getTicked()==0) {
                Background.changeBackground("results_menu");
            }
        }



        if (tick >= 40) {
            tick = 0;
        }
        //System.out.println(CheckpointHealth.getCheckpointHealth());
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