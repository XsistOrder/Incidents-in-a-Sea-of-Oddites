package display;

import interactive.Oddity;
import main.Game;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    private static String falseSkinColor;
    private static String falseEyeColor;
    private static boolean falseEyeDilation;
    private static String falseTeethColor;
    private static String falseBlood;
    private static String falseAge;
    private static String falseBirthPlace;
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

        //String trueSpecies = "Species: " + ; //species isn't a public getter
        String trueSkinColor = "Skin Color: " + nullSafe(Oddity.getSkinColor());
        String trueEyeColor = "Eye Color: " + nullSafe(Oddity.getEyeColor());
        String trueEyeDilation = "Eye Dilation: " + (Oddity.getEyeDilation() ? "dilated" : "normal");
        String trueTeethColor = "Teeth Color: " + nullSafe(Oddity.getTeethColor());
        String trueBlood = "Blood: " + nullSafe(Oddity.getBlood());
        String trueAge = "Age: " + Oddity.getAge();
        String trueBirthPlace = "Birth Place: " + nullSafe(Oddity.getBirthPlace());

        String[] lines = {
                //trueSpecies,
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

    private String pickDifferent(String[] alternatives, String trueValue) {
        ArrayList<String> pool = new ArrayList<>();
        for (String s : alternatives) {
            if (!s.equalsIgnoreCase(nullSafe(trueValue))) pool.add(s);
        }
        if (pool.isEmpty()) return alternatives[0];
        return pool.get(random.nextInt(pool.size()));
    }

    private String corruptPlace(String place) {
        if (place == null || place.isEmpty()) return "unknown";
        String[] suffixes = {" Valley", " Peaks", " Bay", " Hollow", " Ridge", " Coast", " Wastes"};

        for (String s : suffixes) {
            if (place.endsWith(s)) {
                String base = place.substring(0, place.length() - s.length());
                String newSuffix = suffixes[random.nextInt(suffixes.length)];
                while (newSuffix.equals(s)) newSuffix = suffixes[random.nextInt(suffixes.length)];
                return base + newSuffix;
            }
        }

        return place + suffixes[random.nextInt(suffixes.length)];
    }

    private String nullSafe(String s) {
        return (s == null || s.isEmpty()) ? "unknown" : s;
    }

    private void setFalse () {

        try {
            FileReader fr = new FileReader("res\\presets\\false_Oddity_Stats.json");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line=br.readLine()) != null) {

                if (line.contains("\"" + presetPick + "\"")) {
                    foundPreset = true;
                }
                if (line.contains("\"skinColor\" :") && foundPreset) {
                    skinColor = line.substring(line.indexOf(':'));
                    skinColor = skinColor.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    //System.out.println(skinColor);
                }
                if (line.contains("\"eyeColor\" :") && foundPreset) {
                    eyeColor = line.substring(line.indexOf(':'));
                    eyeColor = eyeColor.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    //System.out.println(eyeColor);
                }
                if (line.contains("\"eyeDilation\" :") && foundPreset) {
                    String lineSub = line.substring(line.indexOf(':'));
                    lineSub = lineSub.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    switch (lineSub) {
                        case "true" :
                            eyeDialation = true;
                            break;
                        case "false" :
                            eyeDialation = false;
                            break;

                    }
                    //System.out.println(eyeDialation);
                }
                if (line.contains("\"teethColor\" :") && foundPreset) {
                    teethColor = line.substring(line.indexOf(':'));
                    teethColor = teethColor.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    //System.out.println(teethColor);
                }
                if (line.contains("\"blood\" :") && foundPreset) {
                    blood = line.substring(line.indexOf(':'));
                    blood = blood.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    //System.out.println(blood);
                }
                if (line.contains("\"portrait\" :") && foundPreset) {
                    portrait = line.substring(line.indexOf(':'));
                    portrait = portrait.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    game.graphics.setImageDir(id, "res\\textures\\oddities\\" + species + "\\" + portrait + "\\idle\\idle_1.png");
                    //System.out.println(game.graphics.getImageDir(id));
                }
                if (line.contains(" }") && foundPreset) {

                    //System.out.println("stop");
                    foundPreset = false;
                }
                if (line.contains("\"age\" :")) {
                    String subline = line.substring(line.indexOf(':'),line.indexOf('-'));
                    subline = subline.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    minAge = Integer.parseInt(subline);
                    String subline2 = line.substring(line.indexOf('-'));
                    subline2 = subline2.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                    maxAge = Integer.parseInt(subline);
                }
                if (line.contains("\"weakness\" :")) {
                    weakness = line.substring(line.indexOf(':'));
                    weakness = weakness.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                }
                if (line.contains("\"birthPlace\" :")) {
                    birthPlace = line.substring(line.indexOf(':'));
                    birthPlace = birthPlace.replace(" ", "").replace("\"", "").replace(":", "").replace(",", "");
                }

            }
            br.close();
            aggression = random.nextBoolean();
            System.out.println(aggression);
            age = random.nextInt(minAge, maxAge);
            System.out.println(age);
        }
        catch(IOException e) {
            System.err.println("oddity preset did not read the bible");
        }
    }
}