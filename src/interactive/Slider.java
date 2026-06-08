package interactive;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Slider {

    private final Game game;
    private final int trackId;      //grey background bar
    private final int fillId;       //colored filled portion (left side)
    private final int thumbId;      //draggable circle
    private final int labelId;      //numeric label to the left

    private final int trackX;       //fixed left edge of track
    private final int trackY;
    private final int trackW;       //total width of track
    private final int trackH;

    private final int thumbSize;    //diameter of the thumb circle

    private final float minValue;
    private final float maxValue;
    private float currentValue;

    private boolean draggable;
    private boolean dragging = false;

    public interface OnChangeListener {
        void valueChanged(float newValue);
    }

    private OnChangeListener changeListener;

    public void setOnChangeListener(OnChangeListener listener) {
        this.changeListener = listener;
    }


    public Slider(Game game, int x, int y, int width, int height, float minValue, float maxValue,
                  int priority, boolean visibility, boolean draggable) {

        this.game = game;
        this.trackX = x;
        this.trackY = y;
        this.trackW = width;
        this.trackH = height;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.draggable = draggable;
        this.currentValue = Math.round((minValue + maxValue) / 2f); //start at middle

        thumbSize = Math.max(height + 8, 16);

        int thumbY = y - (thumbSize - height) / 2;

        trackId = game.graphics.addShape("rect", x, y, width, height, new Color(80, 80, 80), priority, visibility);
        fillId = game.graphics.addShape("rect", x, y, 0, height, new Color(100, 180, 255), priority + 1, visibility);

        thumbId = game.graphics.addShape("oval", x - thumbSize / 2, thumbY, thumbSize, thumbSize, Color.WHITE, priority + 2, visibility);

        int labelFontSize = Math.max(height + 4, 12);
        labelId = game.graphics.addText(formatValue(currentValue), x - 50, y + height / 2 + labelFontSize / 2 - 2, labelFontSize, Color.WHITE, priority + 2, visibility);

        game.graphics.setClickable(trackId, draggable);
        game.graphics.setClickable(fillId, draggable);
        game.graphics.setClickable(thumbId, draggable);
        game.graphics.setClickable(labelId, false); //label is never clickable
        updateVisuals();
    }
    //CALL ONCE AFTER CREATING SLIDER

    public void attachListeners() {
        game.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!draggable) return;
                if (!game.graphics.getVisibility(trackId)) return;
                Point p = e.getPoint();
                int tx = game.graphics.getX(thumbId);
                int ty = game.graphics.getY(thumbId);
                int ts = thumbSize;
                boolean onThumb = p.x >= tx && p.x <= tx + ts && p.y >= ty && p.y < ty + ts;
                boolean onTrack = p.x >= trackX && p.x <= trackX + trackW && p.y >= trackY && p.y <= trackY + trackH + 4;
                if (onThumb || onTrack) {
                    dragging = true;
                    updateFromMouseX(p.x);
                }
            }
        });

        game.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && draggable) {
                    updateFromMouseX(e.getX());
                }
            }
        });
    }

    public float getValue() {
        return currentValue;
    }

    public int getValueInt() {
        return Math.round(currentValue);
    }


    public void setValue ( float value){
        currentValue = Math.max(minValue, Math.min(maxValue, value));
        updateVisuals();
    }

    public void setVisible ( boolean visible){
        game.graphics.setVisible(trackId, visible);
        game.graphics.setVisible(fillId, visible);
        game.graphics.setVisible(thumbId, visible);
        game.graphics.setVisible(labelId, visible);
    }

    public void setDraggable ( boolean drag){
        this.draggable = drag;
        game.graphics.setClickable(trackId, drag);
        game.graphics.setClickable(fillId, drag);
        game.graphics.setClickable(thumbId, drag);
    }

    public boolean isDraggable () {
        return draggable;
    }
    public void setTrackColor(Color c) {
        game.graphics.setShapeColor(trackId, c);
    }
    public void setFillColor(Color c) {
        game.graphics.setShapeColor(fillId, c);
    }
    public void setThumbColor(Color c) {
        game.graphics.setShapeColor(thumbId, c);
    }
    public void setLabelColor(Color c) {
        game.graphics.setShapeColor(labelId, c);
    }

    public void addToGroup(String groupName) {
        game.graphics.addToGroup(groupName, trackId);
        game.graphics.addToGroup(groupName, fillId);
        game.graphics.addToGroup(groupName, thumbId);
        game.graphics.addToGroup(groupName, labelId);
    }

    public void removeFromGroup(String groupName) {
        game.graphics.removeFromGroup(groupName, trackId);
        game.graphics.removeFromGroup(groupName, fillId);
        game.graphics.removeFromGroup(groupName, thumbId);
        game.graphics.removeFromGroup(groupName, labelId);
    }

    public int getTrackId() {
        return trackId;
    }
    public int getFillId() {
        return fillId;
    }
    public int getThumbId() {
        return thumbId;
    }
    public int getLabelId() {
        return labelId;
    }

    private void updateFromMouseX(int mouseX) {
        int clampedX = Math.max(trackX, Math.min(trackX + trackW, mouseX));
        float ratio = (float) (clampedX - trackX) / trackW;
        currentValue = minValue + ratio * (maxValue - minValue);
        updateVisuals();

        if (changeListener != null) {
            changeListener.valueChanged(currentValue);
        }
    }

    private void updateVisuals() {
        float ratio = (currentValue - minValue) / (maxValue - minValue);
        int fillW = Math.round(ratio * trackW);
        int thumbX = trackX + fillW - thumbSize / 2;
        int thumbY = trackY - (thumbSize - trackH) / 2;

        game.graphics.setWidth(fillId, Math.max(0, fillW));
        game.graphics.setPosition(thumbId, thumbX, thumbY);
        game.graphics.setText(labelId, formatValue(currentValue));
    }

    private String formatValue(float value) {
        return String.valueOf((int) value);
    }
}