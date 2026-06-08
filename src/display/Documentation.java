package display;

import interactive.Oddity;
import main.Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*

    When the oddity is NOT aggressive, every stat shown is truthful
    When the oddity IS aggressive, a random number of stats (1 to half the total)
    are replaced with alternative values.

    Setup:

    documentation = new Documentation(game, 100, 150, 22, 30);

    Call this each time a new Oddity is generated:

    documentation.refresh();
 */

public class Documentation {

    private final Game game;
    private final int startX;
    private final int startY;
    private final int fontSize;
    private final int lineSpacing;
    private final int priority;
    private final Random random = new Random();

    private int idSpecies;
    private int idSkinColor;
    private int idEyeColor;
    private int idEyeDilation;
    private int idTeethColor;
    private int idBlood;
    private int idAge;
    private int idBirthPlace;

    private final ArrayList<Integer> allIds = new ArrayList<>();

    private static final String[] ALT_SKIN_COLORS = {"pale", "grey", "green", "blue", "tan", "charcoal", "ivory", "rust"};
    private static final String[] ALT_EYE_COLORS = {"red", "yellow", "white", "violet", "black", "silver", "amber", "cyan"};
    private static final String[] ALT_TEETH_COLORS = {"white", "black", "yellow", "grey", "crimson", "brown", "ivory", "silver"};
    private static final String[] ALT_BLOOD_TYPES = {"red", "black", "blue", "green", "silver", "gold", "clear", "purple"};

    public Documentation(Game game, int startX, int startY, int fontSize, int lineSpacing, int priority) {
        this.game = game;
        this.startX = startX;
        this.startY = startY;
        this.fontSize = fontSize;
        this.lineSpacing = lineSpacing;
        this.priority = priority;

        idSpecies = addLine(0);
        idSkinColor = addLine(1);
        idEyeColor = addLine(2);
        idEyeDilation = addLine(3);
        idTeethColor = addLine(4);
        idBlood = addLine(5);
        idAge = addLine(6);
        idBirthPlace = addLine(7);

        allIds.add(idSpecies);
        allIds.add(idSkinColor);
        allIds.add(idEyeColor);
        allIds.add(idEyeDilation);
        allIds.add(idTeethColor);
        allIds.add(idBlood);
        allIds.add(idAge);
        allIds.add(idBirthPlace);
    }

    public void refresh() {

        String trueSpecies = "Species: yokai"; //species isn't a public getter
        String trueSkinColor = "Skin: " + nullSafe(Oddity.getSkinColor());
        String trueEyeColor = "Eyes: " + nullSafe(Oddity.getEyeColor());
        String trueEyeDilation = "Dilation: " + (Oddity.getEyeDilation() ? "dilated" : "normal");
        String trueTeethColor = "Teeth: " + nullSafe(Oddity.getTeethColor());
        String trueBlood = "Blood: " + nullSafe(Oddity.getBlood());
        String trueAge = "Age: " + Oddity.getAge();
        String trueBirthPlace = "Origin: " + nullSafe(Oddity.getBirthPlace());

        String[] lines = {
                trueSpecies,
                trueSkinColor,
                trueEyeColor,
                trueEyeDilation,
                trueTeethColor,
                trueBlood,
                trueAge,
                trueBirthPlace
        };

        if (Oddity.getAggression()) {
            corruptLines(lines);
        }

        game.graphics.setText(idSpecies, lines[0]);
        game.graphics.setText(idSkinColor, lines[1]);
        game.graphics.setText(idSkinColor, lines[2]);
        game.graphics.setText(idEyeDilation, lines[3]);
        game.graphics.setText(idTeethColor, lines[4]);
        game.graphics.setText(idBlood, lines[5]);
        game.graphics.setText(idAge, lines[6]);
        game.graphics.setText(idBirthPlace, lines[7]);
    }

    public void setVisible(boolean visible) {
        for (int id : allIds) {
            game.graphics.setVisible(id, visible);
        }
    }

    public void addToGroup(String groupName) {
        for (int id : allIds) {
            game.graphics.addToGroup(groupName, id);
        }
    }

    public void removeFromGroup(String groupName) {
        for (int id : allIds) {
            game.graphics.removeFromGroup(groupName, id);
        }
    }

    public ArrayList<Integer> getAllIds() { return new ArrayList<>(allIds); }
    public int getIdSpecies() { return idSpecies; }
    public int getIdSkinColor() { return idSkinColor; }
    public int getIdEyeColor() { return idEyeColor; }
    public int getIdEyeDilation() { return idEyeDilation; }
    public int getIdTeethColor() { return idTeethColor; }
    public int getIdBlood() { return idBlood; }
    public int getIdAge() { return idAge; }
    public int getIdBirthPlace() { return idBirthPlace; }


    private int addLine(int lineIndex) {
        return game.graphics.addText(
                "",
                startX,
                startY + lineIndex * lineSpacing,
                fontSize,
                Color.WHITE,
                priority,
                false
        );
    }

    private void corruptLines(String[] lines) {
        ArrayList<Integer> eligible = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) eligible.add(i);
        Collections.shuffle(eligible, random);

        int corruptCount = 1 + random.nextInt(eligible.size() / 2 + 1);

        for (int i = 0; i < corruptCount; i++) {
            int idx = eligible.get(i);
            lines[idx] = corruptLine(idx, lines[idx]);
        }
    }

    private String corruptLine(int index, String originalLine) {
        int colonPos = originalLine.indexOf(':');
        String label = (colonPos >= 0) ? originalLine.substring(0, colonPos + 1) : "";

        switch (index) {
            case 1:
                return label + " " + pickDifferent(ALT_SKIN_COLORS, Oddity.getSkinColor());
            case 2:
                return label + " " + pickDifferent(ALT_EYE_COLORS, Oddity.getEyeColor());
            case 3:
                return label + " " + (Oddity.getEyeDilation() ? "normal" : "dilated");
            case 4:
                return label + " " + pickDifferent(ALT_TEETH_COLORS, Oddity.getTeethColor());
            case 5:
                return label + " " + pickDifferent(ALT_BLOOD_TYPES, Oddity.getBlood());
            case 6:
                int fakeAge = Oddity.getAge() + (random.nextBoolean() ? 1 : -1) * (10 + random.nextInt(50));
                fakeAge = Math.max(1, fakeAge);
                return label + " " + fakeAge;
            case 7:
                return label + " " + corruptPlace(Oddity.getBirthPlace());
            default:
                return originalLine;
        }
    }

    
}