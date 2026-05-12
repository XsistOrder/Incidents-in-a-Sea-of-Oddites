import java.awt.Rectangle;

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
    public boolean isHovered() {
        return isHovered;
    }
}
