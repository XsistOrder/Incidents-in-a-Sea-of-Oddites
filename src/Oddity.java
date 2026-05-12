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
    private String species = possibleOdditySpecies[random.nextInt(possibleOdditySpecies.length)];
    int presetPick = random.nextInt(4) + 1;
    private String skinColor = "";

    public Oddity () {
        switch (species) {
            case "Yokai":
                System.out.println(presetPick);
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

                    //System.out.print(line.indexOf("\"" + presetPick + "\""));
                    if (line.contains("\t\"" + presetPick + "\":")) {
                        System.out.print(line.charAt(i));
                    }
                    if (line.contains("\"" + presetPick + "\"")) {
                        System.out.print(line.charAt(i));
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
