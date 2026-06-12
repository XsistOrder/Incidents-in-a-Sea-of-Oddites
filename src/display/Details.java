package display;

import interactive.Item;
import main.Game;
import interactive.Oddity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

//call details.update every frame in move

public class Details {

    private final Game game;
    private final int labelId;

    private final int questionLine1Id;
    private final int questionLine2Id;

    private final int answerLineId;

    private static final int OFFSET_X = 12;
    private static final int OFFSET_Y = -22;
    private static final int LINE_HEIGHT = 18;

    private int mouseX = 0;
    private int mouseY = 0;
    private boolean showingMenu = false;
    private String activeItem = "";

    private static final String[][] MG_QUESTIONS = {
            { "1. Examine skin",   null },
            { "2. Examine teeth",  null },
    };

    private String mgAnswer(int q) {
        switch (q) {
            case 0: return "Skin color: " + nullSafe(Oddity.getSkinColor());
            case 1: return "Teeth color: " + nullSafe(Oddity.getTeethColor());
            default: return "";
        }
    }


    public Details(Game game) {
        this.game = game;
        int priority = 100;
        labelId = game.graphics.addText("", 0, 0, 14, new Color(255, 240, 180), priority, false);
        questionLine1Id = game.graphics.addText("", 0, 0, 13, new Color(255, 240, 180), priority+1, false);
        questionLine2Id = game.graphics.addText("", 0, 0, 13, new Color(255, 240, 180), priority+1, false);
        answerLineId = game.graphics.addText("", 0, 0, 13, new Color(255, 240, 180), priority+2, false);
    }



    public void attachListener() {
        game.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) { update(e.getX(), e.getY()); }
            @Override
            public void mouseMoved(MouseEvent e) { update(e.getX(), e.getY()); }
        });
    }

    public void attachKeyListener() {
        game.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (!showingMenu) return;
                if (e.getKeyCode() == KeyEvent.VK_1) showAnswer(0);
                if (e.getKeyCode() == KeyEvent.VK_2) showAnswer(1);
            }
        });
    }

    public void update() {
        update(mouseX, mouseY);
    }

    private void update(int mx, int my) {
        mouseX = mx;
        mouseY = my;

        if (!Background.getBackground().equals("checkpoint_menu") || !Popup.getPopup().isEmpty()) {
            hideAll();
            return;
        }

        String heldItem = game.pickup;

        if (isOverObject(game.interrogatedOddity.getId(), mx, my)) {
            if (heldItem.isEmpty()) {
                showSingle("Oddity", mx, my);
            } else if (heldItem.equals("magnifying_glass")) {
                showQuestionMenu(MG_MENU_TITLE, MG_QUESTIONS, "magnifying_glass", mx, my);
            } else if (heldItem.equals("questioner")) {
                showQuestionMenu(QR_MENU_TITLE, QR_QUESTIONS, "questioner", mx, my);
            } else {
                showSingle(itemOnOddityText(heldItem), mx, my);
            }
            return;
        }

        if (showingMenu) {
            showingMenu = false;
            activeItem  = "";
            hideAnswerLine();
        }

        if (isOverItemAtRest(game.syringe, mx, my)) {
            showSingle("Syringe", mx, my); return;
        }
        if (isOverItemAtRest(game.magnifyingGlass, mx, my)) {
            showSingle("Magnifying Glass", mx, my); return;
        }
        if (isOverItemAtRest(game.laserPointer, mx, my)) {
            showSingle("Laser Pointer", mx, my); return;
        }
        if (isOverItemAtRest(game.questioner, mx, my)) {
            showSingle("Questioner", mx, my); return;
        }
        if (isOverItemAtRest(game.acid, mx, my)) {
            showSingle("Acid", mx, my); return;
        }
        if (isOverItemAtRest(game.crucifix, mx, my)) {
            showSingle("Crucifix", mx, my); return;
        }
        if (isOverItemAtRest(game.flashlight, mx, my)) {
            showSingle("Flashlight", mx, my); return;
        }
        if (isOverItemAtRest(game.woodenStake, mx, my)) {
            showSingle("Wooden Stake", mx, my); return;
        }

        if (isOverObject(Background.documentationButton.getId(), mx, my)) {
            showSingle("View Documentation", mx, my); return;
        }
        if (isOverObject(Background.encyclopediaButton.getId(), mx, my)) {
            showSingle("Open Encyclopedia", mx, my); return;
        }
        if (isOverObject(Background.departButton.getId(), mx, my)) {
            showSingle("Depart Oddity", mx, my); return;
        }
        if (isOverObject(Background.pauseButton.getId(), mx, my)) {
            showSingle("Pause", mx, my); return;
        }

        if (showingMenu) {
            showingMenu = false;
            activeItem  = "";
            hideAnswerLine();
        }

    }

    private String itemOnOddityText(String item) {
        switch (item) {
            case "syringe": return "Draw Blood from Oddity";
            case "magnifying_glass": return "Examine Oddity";
            case "laser_pointer": return "Test Oddity Dialation";
            case "questioner": return "Ask Oddity a question";
            case "acid": return "Dispatch Oddity";
            case "crucifix": return "Dispatch Oddity";
            case "flashlight": return "Dispatch Oddity";
            case "wooden_stake": return "Dispatch Oddity";
            default: return "Use " + item + " on Oddity";
        }

    }

    private void showSingle(String text, int mx, int my) {
        showingMenu = false;
        activeItem  = "";
        positionAndShow(labelId, text, mx, my, 0);
        hideQuestionLines();
        hideAnswerLine();
    }

    private void showQuestionMenu(String title, String[][] questions,
                                  String item, int mx, int my) {
        if (!item.equals(activeItem)) {
            activeItem = item;
            hideAnswerLine();
        }
        showingMenu = true;

        positionAndShow(labelId,        title,            mx, my, 0);
        positionAndShow(questionLine1Id, questions[0][0], mx, my, 1);
        positionAndShow(questionLine2Id, questions[1][0], mx, my, 2);

        if (game.graphics.getVisibility(answerLineId)) {
            positionOnly(answerLineId, mx, my, 3);
        }
    }

    private void showAnswer(int questionIndex) {
        String answer = "";
        if (activeItem.equals("magnifying_glass")) answer = mgAnswer(questionIndex);
        if (activeItem.equals("questioner"))       answer = qrAnswer(questionIndex);
        if (answer.isEmpty()) return;

        positionAndShow(answerLineId, answer, mouseX, mouseY, 3);
    }

    private void positionAndShow(int id, String text, int mx, int my, int lineOffset) {
        game.graphics.setText   (id, text);
        game.graphics.setX      (id, mx + OFFSET_X);
        game.graphics.setY      (id, my + OFFSET_Y - lineOffset * LINE_HEIGHT);
        game.graphics.setVisible(id, true);
    }

    private void positionOnly(int id, int mx, int my, int lineOffset) {
        game.graphics.setX(id, mx + OFFSET_X);
        game.graphics.setY(id, my + OFFSET_Y - lineOffset * LINE_HEIGHT);
    }

    private void hideQuestionLines() {
        game.graphics.setVisible(questionLine1Id, false);
        game.graphics.setVisible(questionLine2Id, false);
    }

    private void hideAnswerLine() {
        game.graphics.setVisible(answerLineId, false);
    }

    private void hideAll() {
        game.graphics.setVisible(labelId,        false);
        game.graphics.setVisible(questionLine1Id, false);
        game.graphics.setVisible(questionLine2Id, false);
        game.graphics.setVisible(answerLineId,    false);
        showingMenu = false;
        activeItem  = "";
    }

    private boolean isOverObject(int id, int mx, int my) {
        if (!game.graphics.getVisibility(id)) return false;
        int ox = game.graphics.getX(id);
        int oy = game.graphics.getY(id);
        int ow = game.graphics.getWidth(id);
        int oh = game.graphics.getHeight(id);
        return mx >= ox && mx <= ox + ow && my >= oy && my <= oy + oh;
    }

    private boolean isOverItemAtRest(Item item, int mx, int my) {
        if (!game.pickup.isEmpty()) return false;
        return isOverObject(item.getId(), mx, my);
    }

    public int getLabelId()        { return labelId;        }
    public int getQuestionLine1Id(){ return questionLine1Id; }
    public int getQuestionLine2Id(){ return questionLine2Id; }
    public int getAnswerLineId()   { return answerLineId;    }
}
