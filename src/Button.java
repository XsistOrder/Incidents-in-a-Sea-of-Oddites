import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Button {
    private int id;
    private String hoverText;
    private Rectangle bounds;
    private boolean isHovered = false;

    public Button(int id, int x, int y, int width, int height, String hoverText) {
        this.id = id;
        this.hoverText = hoverText;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public int getId() {
        return id;
    }
    public String getHoverText() {
        return hoverText;
    }
    public boolean isHoveredOver() {
        System.out.println("1");
        Point m = MouseInfo.getPointerInfo().getLocation();
        if (m.x >= bounds.x && m.y >= bounds.y && m.x <= bounds.x+bounds.width && m.y <= bounds.y+bounds.height) {
            isHovered = true;
            System.out.println("2");
        } else {
            isHovered = false;
        }
        return isHovered;
    }
    // the action string signifies what method within other classes is triggered
    public void keyPressed (KeyEvent e, String action) {
        if (isHoveredOver()) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (action.equals("main_menu_to_checkpoint_menu") && Objects.equals(Background.getBackground(), "main_menu")) {
                    Background.changeBackground("checkpoint_menu");
                }
            }
        }
    }
    public void mousePressed (MouseEvent e, String action) {

        if (isHoveredOver()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (action.equals("main_menu_to_checkpoint_menu") && Background.getBackground().equals("main_menu")) {
                    Background.changeBackground("checkpoint_menu");
                }
            }
        }
    }
}
