package interactive;

import display.Background;
import display.Popup;
import main.Game;

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
    private static String portrait;
    private static int age;
    private static String birthday;
    private static String birthPlace;

    public Oddity (Game game, int x, int y, int width, int height, int priority) {
        super(this.game = game, x, y, width, height, "", priority, "");
        id = game.graphics.addObject(x,y,width,height, "", priority, false);
    }
    public int getId () {
        return id;
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
                //System.out.println(line);
            }
            br.close();
        }
        catch(IOException e) {
            System.err.println("oddity preset did not read the bible");
        }
    }
    public static void animation (String state, int tick) {

        switch (state) {
            case "idle" :
                game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + portrait + "\\idle\\idle_1.png");
                if (tick <= 20) {
                    game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + portrait + "\\idle\\idle_2.png");
                }
                break;
            case "think" :
                game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + portrait + "\\think\\think_1.png");
                if (tick <= 20) {
                    game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + portrait + "\\think\\think_2.png");
                }
                break;

        }
    }
    public static void ask (int tick) {
        if (!isOnscreen) {
            //display prompt to let in
            if (tick == 20) {
                ticked++;
            }
            if (ticked == 10) {
                isOnscreen = true;
                game.graphics.setVisible(id, true);
                game.graphics.setClickable(id, true);
            }
        } else {

        }

    }
    public static void walkover () {

    }
    @Override
    public void mousePressed (MouseEvent e, String action) {

        if (isHoveredOver()) {
            if (game.graphics.clickAllowed(id)) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (action.equals("oddity_clicked")) {
                        //add action to determine which interrogation/dispatch item is being carried & resulting in value returns & results addtions
                        System.out.println("clicked oddity");
                    }

                }

            }
        }
    }

}
