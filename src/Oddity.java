import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Oddity {
    private String[] possibleOdditySpecies = {
            "Yokai",
            "Yokai",
            "Yokai"
    };
    private Random random = new Random();
    private boolean foundPreset = true;
    private String species = possibleOdditySpecies[random.nextInt(possibleOdditySpecies.length)];
    private int presetPick = random.nextInt(4) + 1;
    private String skinColor = "";
    private String eyeColor = "";
    private boolean eyeDialation;
    private String teethColor = "";
    private String blood = "";

    public Oddity () {
        switch (species) {
            case "Yokai":
                //System.out.println(presetPick);
                break;
            case "Still_Life":

                break;
        }
        try {
            FileReader fr = new FileReader("res/presets/" + species + "_Oddities_Presets.json");
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line=br.readLine()) != null) {
                for (int i = 0;i < line.length();i++) {

                    if (line.contains(" \"" + presetPick + "\"")) {
                        foundPreset = true;
                    }
                    if (line.contains("  \"skinColor\":") && foundPreset) {
                        if (i > line.indexOf(':')) {
                            skinColor += line.charAt(i);
                        }
                    }
                    if (line.contains("  \"eyeColor\":") && foundPreset) {
                        if (i > line.indexOf(':')) {
                            eyeColor += line.charAt(i);
                        }
                    }
                    if (line.contains("  \"eyeDilation\":") && foundPreset) {
                        if (i > line.indexOf(':')) {
                            String lineSub = line.substring(line.indexOf(':'));
                            switch
                            eyeColor += line.charAt(i);
                        }
                    }
                    if (line.contains(" }") && foundPreset) {
                        skinColor = skinColor.replace(" ", "").replace("\"", "");
                        eyeColor = eyeColor.replace(" ", "").replace("\"", "");
                        foundPreset = false;
                        System.out.print(skinColor);
                    }

                }
                //System.out.println();
            }
            br.close();
        }
        catch(IOException e) {
        }
    }
}
