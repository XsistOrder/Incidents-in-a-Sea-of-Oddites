import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Oddity {
    private static Game game;
    private int id;
    private String[] possibleOdditySpecies = {
            "yokai",
            "yokai",
            "yokai"
    };
    private Random random = new Random();

    private boolean foundPreset = true;
    private String species = possibleOdditySpecies[random.nextInt(possibleOdditySpecies.length)];
    private int presetPick = 1;
            //random.nextInt(4) + 1;
    private String skinColor;
    private String eyeColor;
    private boolean eyeDialation;
    private String teethColor;
    private String blood;
    private int age;
    private String birthday;
    private String birthPlace;

    public Oddity (Game game, int x, int y, int width, int height, int priority) {
        this.game = game;
        id = game.graphics.addObject(x,y,width,height, "", priority, true);
    }
    public void generate () {
        try {
            FileReader fr = new FileReader("res/presets/" + species + "_Oddities_Presets.json");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line=br.readLine()) != null) {
                if (line.contains(" \"" + presetPick + "\"")) {
                    foundPreset = true;
                }
                if (line.contains("  \"skinColor\":") && foundPreset) {
                    skinColor = line.substring(line.indexOf(':'));
                    skinColor = skinColor.replace(" ", "").replace("\"", "");
                }
                if (line.contains("  \"eyeColor\":") && foundPreset) {
                    eyeColor = line.substring(line.indexOf(':'));
                    eyeColor = eyeColor.replace(" ", "").replace("\"", "");
                }
                if (line.contains("  \"eyeDilation\":") && foundPreset) {
                    String lineSub = line.substring(line.indexOf(':'));
                    lineSub = lineSub.replace(" ", "").replace("\"", "");
                    switch (lineSub) {
                        case "true" :
                            eyeDialation = true;
                            break;
                        case "false" :
                            eyeDialation = false;
                            break;
                    }
                }
                if (line.contains("  \"teethColor\":") && foundPreset) {
                    teethColor = line.substring(line.indexOf(':'));
                    teethColor = teethColor.replace(" ", "").replace("\"", "");
                }
                if (line.contains("  \"blood\":") && foundPreset) {
                    blood = line.substring(line.indexOf(':'));
                    blood = blood.replace(" ", "").replace("\"", "");
                }
                if (line.contains(" }") && foundPreset) {


                    foundPreset = false;
                }
            }
            br.close();
        }
        catch(IOException e) {
        }
        game.graphics.setVisible(1, true);
    }
    public void animation (String state, int tick) {

        switch (state) {
            case "idle" :
                game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + presetPick + "\\idle\\idle_1.png");
                if (tick <= 20) {
                    game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + presetPick + "\\idle\\idle_2.png");
                }
                break;
            case "think" :
                game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + presetPick + "\\think\\think_1.png");
                if (tick <= 20) {
                    game.graphics.setImageDir(id,"res\\textures\\oddities\\" + species +"\\" + presetPick + "\\think\\think_2.png");
                }
                break;

        }
    }

}
