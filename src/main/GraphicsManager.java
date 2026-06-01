package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import javax.imageio.ImageIO;

/*
    Graphics Manager - handles all object rendering for the game

    Each object is stored across parallel ArrayLists (one per property).
    Objects are drawn in ascending priority order, so higher priority objects
    appear on top of lower priority ones.

    examples:

                                     X  Y  Width Height  Directory Priority Visibility
                                     |  |    |    |          |         |     |
            int bgId = gfx.addObject(0, 0, 1000, 700, "assets/bg.png", 0, true)

            gfx.translate(playerID, 10, 0);   move player right 10px
            gfx.setAlpha(bgId, 0.5f);         make image semi-transparent


            push/restore stack examples:

            gfx.push(id);  saves a full copy of the objects current state
            gfx.restore(id); restores the most recently pushed copy (in reverse order aka like a normal stack)
            gfx.clearStack(id); discards all saved copies for a object
            gfx.stackSize(id); returns how many copies of an object are currently saved

            example:

            object starts at x = 10

            gfx.push(id);  saves x = 10
            gfx.setX(id, 50);  object is now at x = 50
            gfx.push(id);  saves x = 50
            gfx.setX(id, 99); object is now at x = 99
            gfx.restore(id);  restores x = 50 (most recent push)
            gfx.restore(id);  restores x = 10 (next in line)

            visibility group examples:

            gfx.createGroup("main_menu_buttons");

            gfx.addToGroup("main_menu_buttons", playButtonId);
            gfx.addToGroup("main_menu_buttons", settingsButtonId);
            gfx.addToGroup("main_menu_buttons", creditsButtonId);

            gfx.showGroup("main_menu_butons");
            gfx.hideGroup("main_menu_buttons");
            gfx.setGroupVisible("main_menu_buttons", true);
            gfx.setGroupVisible("main_menu_butons", false);

            gfx.removeFromGroup("main_menu_buttons", creditsButtonId);

            gfx.deleteGroup("main_menu_buttons");

            gfx.groupExists("main_menu_buttons");

            gfx.getGroupIds("main_menu_buttons");

            gfx.isInGroup("main_menu_buttons", playButtonId);

            An object can be in multiple groups at the same time

            gfx.addToGroup("all_buttons", playButtonId);
            gfx.addToGroup("main_menu_buttons", playButtonId);

        Shapes use the same id system, priority, visibility, groups, push/restore, etc.
        as image objects. The only difference is they are drawn with Graphics2D instead
        of a BufferedImage, so no image file is needed.

        Supported shape types (pass as the type String):
            "rect"        filled rectangle
            "rect_outline"  rectangle outline only (no fill)
            "oval"        filled oval / circle
            "oval_outline"  oval outline only
            "line"        straight line from (x,y) to (x+width, y+height)

        int trackId = gfx.addShape("rect", 100, 200, 300, 10, Color.GRAY, 5, true);
        int thumbId = gfx.addShape("oval", 180, 195, 20, 20, Color.WHITE, 6, true);

        gfx.setShapeColor(trackId, Color.DARK_GRAY);
        gfx.setX(thumbId, 250);          // all normal setters work on shapes too
        gfx.setVisible(trackId, false);  // visibility, groups, push/restore all work

        Text object examples

        Text objects render a string at a given position with a given font and color.
        They obey priority, visibility, groups, push/restore like everything else.
        Width/height are used as the font size (width) — height is ignored for text.

        int labelId = gfx.addText("100", 50, 200, 18, Color.WHITE, 7, true);

        gfx.setText(labelId, "75");            // update the displayed string
        gfx.setTextColor(labelId, Color.RED);  // change the color
        gfx.setX(labelId, 60);                 // reposition


 */

public class GraphicsManager {

    //ArrayLists - one entry per managed object
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_SHAPE = 1;
    private static final int TYPE_TEXT = 2;

    private final ArrayList<Integer> ids;               //unique id per object
    private final ArrayList<Integer> xPositions;        //top-left x
    private final ArrayList<Integer> yPositions;        //top-left y
    private final ArrayList<Integer> widths;            //draw widths
    private final ArrayList<Integer> heights;           // draw heights
    private final ArrayList<String> imageDirs;          //file paths to images
    private final ArrayList<Integer> priorities;        //draw order
    private final ArrayList<Boolean> visibilities;      //whether objects are visible or not
    private final ArrayList<Float> rotations;           // rotation in degrees
    private final ArrayList<Float> alphas;              //opacity 0 - 1
    private final ArrayList<BufferedImage> imageCache;  //pre-loaded images
    private final ArrayList<Boolean> clickables;

    //shape / text extras (null for image objects)
    private final ArrayList<Integer> objectTypes;
    private final ArrayList<String> shapeTypes;
    private final ArrayList<Color> shapeColors;
    private final ArrayList<String> textStrings;

    //Push / restore stacks

    //Each object ID maps to its own stack

    private final HashMap<Integer, ArrayDeque<ObjectState>> stacks;

    //visibility groups

    private final HashMap<String, HashSet<Integer>> groups;
    //Auto-incrementing id counter
    public int nextId = 0;


    //inner class - saves copies of a single object's state

    private static class ObjectState {
        final int          x;
        final int          y;
        final int          width;
        final int          height;
        final String       imageDir;
        final int          priority;
        final boolean      visibility;
        final float        rotation;
        final float        alpha;
        final Color color;
        final String textOrShapeType;

        ObjectState(int x, int y, int width, int height, String imageDir,
                    int priority, boolean visibility, float rotation, float alpha, Color color, String textOrShapeType) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.imageDir = imageDir;
            this.priority = priority;
            this.visibility = visibility;
            this.rotation = rotation;
            this.alpha = alpha;
            this.color = color;
            this.textOrShapeType = textOrShapeType;
        }
    }

    public GraphicsManager() {

        ids = new ArrayList<>();
        xPositions = new ArrayList<>();
        yPositions = new ArrayList<>();
        widths = new ArrayList<>();
        heights = new ArrayList<>();
        imageDirs = new ArrayList<>();
        priorities = new ArrayList<>();
        visibilities = new ArrayList<>();
        rotations = new ArrayList<>();
        alphas = new ArrayList<>();
        imageCache = new ArrayList<>();
        stacks = new HashMap<>();
        groups = new HashMap<>();
        clickables = new ArrayList<>();
        objectTypes = new ArrayList<>();
        shapeTypes = new ArrayList<>();
        shapeColors = new ArrayList<>();
        textStrings = new ArrayList<>();
    }

    //Adding / Removing Objects

    //Adds an object and returns it's unique ID

    public int addObject(int x, int y, int width, int height, String imageDir, int priority, boolean visibility) {

        int id = nextId++;
        ids.add(id);
        xPositions.add(x);
        yPositions.add(y);
        widths.add(width);
        heights.add(height);
        imageDirs.add(imageDir);
        priorities.add(priority);
        visibilities.add(visibility);
        rotations.add(0f);
        alphas.add(1f);
        clickables.add(true);
        imageCache.add(loadImage(imageDir));
        objectTypes.add(TYPE_IMAGE);
        shapeTypes.add(null);
        shapeColors.add(null);
        textStrings.add(null);
        stacks.put(id, new ArrayDeque<>()); //create an empty stack for this object
        return id;
    }

//Adds a new object with a default priority of 0

    public int addObject(int x, int y, int width, int height, String imageDir, int priority) {
        return addObject(x, y, width, height, imageDir, priority, true);
    }

    //add shape object and return it's unique id

    public int addShape(String type, int x, int y, int width, int height, Color color, int priority, boolean visibility) {
        int id = nextId++;
        ids.add(id);
        xPositions.add(x);
        yPositions.add(y);
        widths.add(width);
        heights.add(height);
        imageDirs.add("");
        priorities.add(priority);
        visibilities.add(visibility);
        rotations.add(0f);
        alphas.add(1f);
        clickables.add(true);
        imageCache.add(null);
        objectTypes.add(TYPE_SHAPE);
        shapeTypes.add(type);
        shapeColors.add(color);
        textStrings.add(null);
        stacks.put(id, new ArrayDeque<>());
        return id;
    }

    public int addShape(String type, int x, int y, int width, int height, Color color, int priority) {
        return addShape(type, x, y, width, height, color, priority, true);
    }

    public int addText(String text, int x, int y, int fontSize, Color color, int priority, boolean visibility) {
        int id = nextId++;
        ids.add(id);
        xPositions.add(x);
        yPositions.add(y);
        widths.add(fontSize); //fontSize stored here
        heights.add(0);
        imageDirs.add("");
        priorities.add(priority);
        visibilities.add(visibility);
        rotations.add(0f);
        alphas.add(1f);
        clickables.add(true);
        imageCache.add(null);
        objectTypes.add(TYPE_TEXT);
        shapeTypes.add(null);
        shapeColors.add(color);
        textStrings.add(text);
        stacks.put(id, new ArrayDeque<>());
        return id;
    }

    public int addText(String text, int x, int y, int fontSize, Color color, int priority) {
        return addText(text, x, y, fontSize, color, priority, true);
    }

    //shape / text setters

    public void setShapeColor(int id, Color color) {
        shapeColors.set(indexOf(id), color);
    }

    public void setText(int id, String text) {
        textStrings.set(indexOf(id), text);
    }

    public String getText(int id) {
        return textStrings.get(indexOf(id));
    }

    public Color getShapeColor(int id) {
        return shapeColors.get(indexOf(id));
    }

    public void setShapeType(int id, String type) {
        shapeTypes.set(indexOf(id), type);
    }

    public void setFontSize(int id, int fontSize) {
        widths.set(indexOf(id), fontSize);
    }

//removes an object with the given id
//returns true if the object existed and was removed successfully
//otherwise, returns false

    public boolean removeObject(int id) {
        int idx = indexOf(id);
        if (idx == -1) return false;
        ids.remove(idx);
        xPositions.remove(idx);
        yPositions.remove(idx);
        widths.remove(idx);
        heights.remove(idx);
        imageDirs.remove(idx);
        priorities.remove(idx);
        visibilities.remove(idx);
        rotations.remove(idx);
        alphas.remove(idx);
        imageCache.remove(idx);
        clickables.remove(idx);
        objectTypes.remove(idx);
        shapeTypes.remove(idx);
        shapeColors.remove(idx);
        textStrings.remove(idx);
        stacks.remove(id);
        //remove from all groups automatically
        for (HashSet<Integer> members : groups.values()) members.remove(id);
        return true;
    }

//removes all objects

    public void clearAll() {
        ids.clear();
        xPositions.clear();
        yPositions.clear();
        widths.clear();
        heights.clear();
        imageDirs.clear();
        priorities.clear();
        visibilities.clear();
        rotations.clear();
        alphas.clear();
        imageCache.clear();
        clickables.clear();
        objectTypes.clear();
        shapeTypes.clear();
        shapeColors.clear();
        textStrings.clear();
        stacks.clear();
        groups.clear();
    }


    //visibility groups

    public void createGroup(String groupName) {
        if (!groups.containsKey(groupName)) {
            groups.put(groupName, new HashSet<>());
        } else {
            System.err.println("[GraphicsManager] createGroup: group \"" + groupName + "\" already exists");
        }
    }

    //Adds an object to a group.

    public void addToGroup(String groupName, int id) {
        if (indexOf(id) == -1) {
            System.err.println("[GraphicsManager] addToGroup: unknown id " + id);
            return;
        }
        groups.computeIfAbsent(groupName, k -> new HashSet<>()).add(id);
    }

    //removes one object from a group

    public void removeFromGroup(String groupName, int id) {
        HashSet<Integer> members = groups.get(groupName);
        if (members == null) {
            System.err.println("[GraphicsManager] removeFromGroup: group \"" + groupName + "\" not found");
            return;
        }
        members.remove(id);
    }

    //deletes an entire group

    public void deleteGroup(String groupName) {
        if (groups.remove(groupName) == null) {
            System.err.println("[GraphicsManager] deleteGroup: group \"" + groupName + "\" not found");
        }
    }

    //show all objects in group

    public void showGroup(String groupName) {
        setGroupVisible(groupName, true);
    }

    //hides all objects in group

    public void hideGroup(String groupName) {
        setGroupVisible(groupName, false);
    }

    //sets visibility for every object in group

    public void setGroupVisible(String groupName, boolean visible) {
        HashSet<Integer> members = groups.get(groupName);
        if (members == null) {
            System.err.println("[GraphicsManager] setGroupVisible: group \"" + groupName + "\" not found");
            return;
        }
        for (int id : members) {
            int idx = indexOf(id);
            if (idx != -1) visibilities.set(idx, visible);
        }
    }

    public void enableGroupClicks(String groupName) {
        setGroupClickable(groupName, true);
    }

    public void disableGroupClicks(String groupName) {
        setGroupClickable(groupName, false);
    }

    public void setGroupClickable(String groupName, boolean clickable) {
        HashSet<Integer> members = groups.get(groupName);
        if (members == null) {
            System.err.println("[GraphicsManager] setGroupClickable: group \"" + groupName + "\" not found");
            return;
        }
        for (int id : members) {
            int idx = indexOf(id);
            if (idx != -1) clickables.set(idx, clickable);
        }
    }

    //returns true if the named group exists

    public boolean groupExists(String groupName) {
        return groups.containsKey(groupName);
    }

    //returns a copy of all object ids currently in the group

    public ArrayList<Integer> getGroupIds(String groupName) {
        HashSet<Integer> members = groups.get(groupName);
        if (members == null) return new ArrayList<>();
        return new ArrayList<>(members);
    }

    //returns true if the given object is a member of the named group

    public boolean isInGroup(String groupName, int id) {
        HashSet<Integer> members = groups.get(groupName);
        return members != null && members.contains(id);
    }

    //returns the names of all currently existing groups

    public ArrayList<String> getAllGroupNames() {
        return new ArrayList<>(groups.keySet());
    }

    //returns how many objects are in the given group

    public int getGroupSize(String groupName) {
        HashSet<Integer> members = groups.get(groupName);
        return (members == null) ? 0 : members.size();
    }

    public void mergeIntoGroup(String sourceGroup, String targetGroup) {
        HashSet<Integer> source = groups.get(sourceGroup);
        if (source == null) {
            System.err.println("GraphicsManager] mergeIntoGroup: source group \"" + sourceGroup + "\" not found");
            return;
        }
        groups.computeIfAbsent(targetGroup, k -> new HashSet<>()).addAll(source);
    }

    //push / restore stack

    public void push(int id) {
        int idx = indexOf(id);
        if (idx == -1) {
            System.err.println("[main.GraphicsManager] push: unknown id " + id);
            return;
        }
        stacks.get(id).push(new ObjectState(
                xPositions.get(idx), yPositions.get(idx),
                widths.get(idx), heights.get(idx),
                imageDirs.get(idx), priorities.get(idx),
                visibilities.get(idx), rotations.get(idx), alphas.get(idx),
                shapeColors.get(idx),
                objectTypes.get(idx) == TYPE_TEXT ? textStrings.get(idx) : shapeTypes.get(idx)
        ));
    }

    //restores the most recently pushed copy for the object

    public void restore(int id) {
        ArrayDeque<ObjectState> stack = stacks.get(id);
        if (stack == null || stack.isEmpty()) {
            System.err.println("[main.GraphicsManager] restore: no saved state for id " + id);
            return;
        }
        ObjectState s = stack.pop();
        int idx = indexOf(id);

        xPositions.set(idx, s.x);
        yPositions.set(idx, s.y);
        widths.set(idx, s.width);
        heights.set(idx, s.height);
        priorities.set(idx, s.priority);
        visibilities.set(idx, s.visibility);
        rotations.set(idx, s.rotation);
        alphas.set(idx, s.alpha);
        if (s.color != null) shapeColors.set(idx, s.color);
        int type = objectTypes.get(idx);
        if (type == TYPE_TEXT && s.textOrShapeType != null) textStrings.set(idx, s.textOrShapeType);
        if (type == TYPE_SHAPE && s.textOrShapeType != null) shapeTypes.set(idx, s.textOrShapeType);
        if (type == TYPE_IMAGE && !imageDirs.get(idx).equals(s.imageDir)) {
            imageDirs.set(idx, s.imageDir);
            imageCache.set(idx, loadImage(s.imageDir));
        }
    }

    //discard all copies for the object without restoring

    public void clearStack(int id) {
        ArrayDeque<ObjectState> stack = stacks.get(id);
        if (stack != null) stack.clear();
    }
    //returns how many copies are currently saved for the given object
    public int stackSize(int id) {
        ArrayDeque<ObjectState> stack = stacks.get(id);
        return (stack == null) ? 0 : stack.size();
    }

//getters

    public int     getX          (int id) { return xPositions.get(indexOf(id)); }
    public int     getY          (int id) { return yPositions.get(indexOf(id)); }
    public int     getWidth      (int id) { return widths.get(indexOf(id)); }
    public int     getHeight     (int id) { return heights.get(indexOf(id)); }
    public String  getImageDir   (int id) { return imageDirs.get(indexOf(id)); }
    public int     getPriority   (int id) { return priorities.get(indexOf(id)); }
    public boolean getVisibility (int id) { return visibilities.get(indexOf(id)); }
    public float   getRotation   (int id) { return rotations.get(indexOf(id)); }
    public float   getAlpha      (int id) { return alphas.get(indexOf(id)); }


//Returns a copy of all active ids

    public ArrayList<Integer> getAllIds() {
        return new ArrayList<>(ids);
    }

//returns the number of managed objects

    public int getObjectCount() {
        return ids.size();
    }

    public boolean clickAllowed(int id) {
        return clickables.get(indexOf(id));
    }

//setters

    public void setX (int id, int x) {
        xPositions.set(indexOf(id), x);
    }

    public void setY (int id, int y) {
        yPositions.set(indexOf(id), y);
    }

    public void setWidth (int id, int w) {
        widths.set(indexOf(id), w);
    }

    public void setHeight (int id, int h) {
        heights.set(indexOf(id), h);
    }

    public void setPriority (int id, int p) {
        priorities.set(indexOf(id), p);
    }

    public void setVisible (int id, boolean v) {
        visibilities.set(indexOf(id), v);
    }

    public void setRotation (int id, float degrees) {
        rotations.set(indexOf(id), degrees);
    }

    public void setClickable(int id, boolean clickable) {
        clickables.set(indexOf(id), clickable);
    }

    public boolean getClickable(int id) {
        return clickables.get(indexOf(id));
    }

//sets opacity. Clamped to 0.0 - 1.0

    public void setAlpha(int id, float alpha) {
        alphas.set(indexOf(id), Math.max(0f, Math.min(1f, alpha)));
    }

//sets the image path and reloads the cached image

    public void setImageDir(int id, String imageDir) {
        int idx = indexOf(id);
        imageDirs.set(idx, imageDir);
        imageCache.set(idx, loadImage(imageDir));
    }
//sets both x and y position at once

    public void setPosition(int id, int x, int y) {
        setX(id, x);
        setY(id, y);
    }

//sets both width and height at once

    public void setSize(int id, int width, int height) {
        setWidth(id, width);
        setHeight(id, height);
    }

//changers

//moves the object by (dx, dy) relative to its current position

    public void translate(int id, int dx, int dy) {
        setX(id, getX(id) + dx);
        setY(id, getY(id) + dy);
    }

//scales width and height by the given factors

    public void scale(int id, float scaleX, float scaleY) {
        setWidth(id, Math.round(getWidth(id) * scaleX));
        setHeight(id, Math.round(getHeight(id) * scaleY));
    }

//adds degrees to the current rotation

    public void rotate(int id, float degrees) {
        setRotation(id, getRotation(id) + degrees);
    }

//increases or decreases priority by a number

    public void changePriority(int id, int value) {
        setPriority(id, getPriority(id) + value);
    }

//bulk operations

//make all objects visible

    public void showAll() {
        for (int i = 0; i < ids.size(); i++) visibilities.set(i, true);
    }

//hides all objects

    public void hideAll() {
        for (int i = 0; i < ids.size(); i++) visibilities.set(i, false);
    }

//shifts every object by (dx, dy)

    public void translateAll(int dx, int dy) {
        for (int id : ids) translate(id, dx, dy);
    }

//drawing

    public void drawAll(Graphics2D g2d) {
        if (ids.isEmpty()) return;
        for (int idx : getSortedIndices()) {
            if (!visibilities.get(idx)) continue;

            float opacity = alphas.get(idx);
            Composite original = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

            int type = objectTypes.get(idx);

            if (type == TYPE_IMAGE) {
                drawImage(g2d, idx);
            } else if (type == TYPE_SHAPE) {
                drawShape(g2d, idx);
            } else if (type == TYPE_TEXT) {
                drawText(g2d, idx);
            }

            g2d.setComposite(original);
        }
    }

//Private helpers

    private void drawImage(Graphics2D g2d, int idx) {
        BufferedImage img = imageCache.get(idx);
        if (img == null) return;
        int x = xPositions.get(idx);
        int y = yPositions.get(idx);
        int w = widths.get(idx);
        int h = heights.get(idx);
        float angle = rotations.get(idx);
        if (angle != 0f) {
            int cx = x + w / 2, cy = y + h / 2;
            g2d.rotate(-Math.toRadians(angle), cx, cy);
        } else {
            g2d.drawImage(img, x, y, w, h, null);
        }
    }

    private void drawShape(Graphics2D g2d, int idx) {
        int x = xPositions.get(idx);
        int y = yPositions.get(idx);
        int w = widths.get(idx);
        int h = heights.get(idx);
        Color color = shapeColors.get(idx);
        String type = shapeTypes.get(idx);
        float angle = rotations.get(idx);

        Color prevColor = g2d.getColor();
        g2d.setColor(color != null ? color : Color.WHITE);

        if (angle != 0f) {
            int cx = x + w / 2, cy = y + h / 2;
            g2d.rotate(Math.toRadians(angle), cx, cy);
        }

        if (type == null) {
            //nothing
        }
        else if (type.equals("rect")) g2d.fillRect(x, y, w, h);
        else if (type.equals("rect_outline")) g2d.drawRect(x, y, w, h);
        else if (type.equals("oval")) g2d.fillOval(x, y, w, h);
        else if (type.equals("oval_outline")) g2d.drawOval(x, y, w, h);
        else if (type.equals("line")) g2d.drawLine(x, y, x + w, y + h);

        if (angle != 0f) {
            int cx = x + w / 2, cy = y + h / 2;
            g2d.rotate(-Math.toRadians(angle), cx, cy);
        }

        g2d.setColor(prevColor);
    }

    private void drawText(Graphics2D g2d, int idx) {
        int x = xPositions.get(idx);
        int y = yPositions.get(idx);
        int fontSize = widths.get(idx);
        Color color = shapeColors.get(idx);
        String text = textStrings.get(idx);
        if (text == null) return;

        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        g2d.setColor(color != null ? color : Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g2d.drawString(text, x, y);

        g2d.setColor(prevColor);
        g2d.setFont(prevFont);
    }

//returns the internal index for a given ID, or -1 if not found

    private int indexOf(int id) {
        return ids.indexOf(id);
    }

//returns internal indices sorted by ascending priority
//so that the highest priority object is drawn last (on top)

    private ArrayList<Integer> getSortedIndices() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) indices.add(i);
        indices.sort((a, b) -> priorities.get(a) - priorities.get(b));
        return indices;
    }

//loads a BufferedImage from disk, prints a warning and returns null on failure

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("[main.GraphicsManager] Could not load image: " + path);
            return null;
        }
    }
}