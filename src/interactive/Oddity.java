package interactive;

import display.Background;
import display.Impatience;
import display.Popup;
import display.Results;
import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Oddity extends Button {
    private static Game game;
    private static int ticked = 0;
    private static boolean isOnscreen = false;
    private static int id;
    private static boolean paused = true;
    private static String[] possibleOdditySpecies = {
            "yokai",
            "yokai",
            "yokai"
    };
    private static Random random = new Random();

    private static boolean foundPreset = false;
    private static String species = possibleOdditySpecies[random.nextInt(possibleOdditySpecies.length)];
    private static int presetPick = random.nextInt(1,4);
    //random.nextInt(4) + 1;
    private static String skinColor;
    private static String eyeColor;
    private static boolean eyeDialation;
    private static String teethColor;
    private static String blood;
    private static int age;
    private static String birthPlace;

    private static String portrait;

    private static boolean aggression;

    private static String weakness;

    public Oddity (Game game, int x, int y, int width, int height, int priority) {
        super(game, x, y, width, height, "", priority, "");
        this.game = game;
        id = game.graphics.addObject(x,y,width,height, "", priority, false);
    }
    public int getId () {
        return id;
    }
    public static int getTicked () {return ticked; }
    public static String getSkinColor () {
        return skinColor;
    }
    public static String getEyeColor () {
        return skinColor;
    }
    public static boolean getEyeDilation () {
        return eyeDialation;
    }
    public static String getTeethColor () {
        return teethColor;
    }
    public static String getBlood () {
        return blood;
    }
    public static int getAge () {
        return age;
    }
    public static String getBirthPlace () {
        return birthPlace;
    }
    public static boolean getAggression () {
        return aggression;
    }

    public static void setPaused (boolean b) {
        paused = b;
    }

    public static void generate () {

        try {
            FileReader fr = new FileReader("res\\presets\\" + species + "_Oddities_Presets.json");
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
            age = random.nextInt(20, 400);
            System.out.println(age);
        }
        catch(IOException e) {
            System.err.println("oddity preset did not read the bible");
        }
    }
    public static void loopAnimation (String state, int tick) {
        if (!paused) {
            switch (state) {
                case "idle":
                    game.graphics.setImageDir(id, "res\\textures\\oddities\\" + species + "\\" + portrait + "\\idle\\idle_1.png");
                    if (tick <= 20) {
                        game.graphics.setImageDir(id, "res\\textures\\oddities\\" + species + "\\" + portrait + "\\idle\\idle_2.png");
                    }
                    break;
                case "think":
                    game.graphics.setImageDir(id, "res\\textures\\oddities\\" + species + "\\" + portrait + "\\think\\think_1.png");
                    if (tick <= 20) {
                        game.graphics.setImageDir(id, "res\\textures\\oddities\\" + species + "\\" + portrait + "\\think\\think_2.png");
                    }
                    break;

            }
        }
    }
    public static void askAnimation (int tick, boolean reset) {
        if (!isOnscreen && !paused) {
            //display prompt to let in
            Impatience.pauseFillAndSetVisibility(true, true);
            game.graphics.setClickable(Background.documentationButton.getId(), false);
            game.graphics.setVisible(Background.documentationButton.getId(), false);
            game.graphics.setClickable(Background.departButton.getId(), false);
            game.graphics.setVisible(Background.departButton.getId(), false);
            game.graphics.setVisible(id, false);
            game.graphics.setClickable(id, false);
            if (tick == 20) {
                ticked++;
            }
            if (ticked == 5) {
                isOnscreen = true;
                Oddity.generate();
                game.graphics.setClickable(Background.documentationButton.getId(), true);
                game.graphics.setVisible(Background.documentationButton.getId(), true);
                game.graphics.setClickable(Background.departButton.getId(), true);
                game.graphics.setVisible(Background.departButton.getId(), true);
                Impatience.pauseFillAndSetVisibility(false, true);
                Results.addToOddityCount();
                game.graphics.setVisible(id, true);
                game.graphics.setClickable(id, true);
            }
        }
        if (reset) {
            ticked = 0;
            isOnscreen = false;
        }

    }
    public static void walkover () {

    }
    @Override
    public void keyPressed (KeyEvent e, String action) {
        if (game.graphics.clickAllowed(id)) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                if (action.equals("close_settings_popup")) {
                    Popup.changeAndShowPopup("settings_popup", false);
                }
                if (action.equals("close_difficulty_popup")) {
                    Popup.changeAndShowPopup("difficulty_popup", false);
                }

            }
        }
    }
    @Override
    public void mousePressed (MouseEvent e, String action) {

        if (isHoveredOver()) {
            if (game.graphics.clickAllowed(id)) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (action.equals("oddity_clicked") && game.pickup.equals("syringe")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with syringe");
                    }
                    if (action.equals("oddity_clicked") && game.pickup.equals("magnifying_glass")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with magnifying_glass");
                    }
                    if (action.equals("oddity_clicked") && game.pickup.equals("laser_pointer")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with laser_pointer");
                    }
                    if (action.equals("oddity_clicked") && game.pickup.equals("questioner")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with questioner");
                    }

                    if (action.equals("oddity_clicked") && game.pickup.equals("acid")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with acid");
                        if (game.pickup.equals(weakness) && aggression) {
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(true);
                        } else if (!aggression){
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        } else {
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        }
                    }
                    if (action.equals("oddity_clicked") && game.pickup.equals("crucifix")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with crucifix");
                        if (game.pickup.equals(weakness) && aggression) {
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(true);
                        } else if (!aggression){
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        } else {
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        }
                    }
                    if (action.equals("oddity_clicked") && game.pickup.equals("flashlight")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with flashlight");
                        if (game.pickup.equals(weakness) && aggression) {
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(true);
                        } else if (!aggression){
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        } else {
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        }
                    }
                    if (action.equals("oddity_clicked") && game.pickup.equals("wooden_stake")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity with wooden_stake");
                        if (game.pickup.equals(weakness) && aggression) {
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(true);
                        } else if (!aggression){
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        } else {
                            Impatience.addPermanentFillMultiplier(0.25f);
                            Impatience.reset();
                            Oddity.askAnimation(0,true);
                            Results.addOddityDispatchResults(false);
                        }
                    }
                }

            }
        }
    }

}