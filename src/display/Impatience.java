package display;

import main.Game;

public class Impatience {
    private static int id;
    private static int id2;
    private static Game game;
    private static int impatienceFill = 0;
    private static float permanentImpatienceMultiplier = 1.0f;
    private static float temporaryImpatienceMultiplier = 1.0f;


    public Impatience (Game game, int x, int y, int width, int height) {
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\non_interactive\\impatience_meter.jpg", 30, true);
        id2 = game.graphics.addObject(x,y, width, height/100,"res\\textures\\non_interactive\\impatience_meter_fill.jpg", 31, true);
    }
    // add method to a tick within game
    public static void fill () {
        game.graphics.setHeight(id2, (int) (game.graphics.getHeight(id2) + 1 * temporaryImpatienceMultiplier * permanentImpatienceMultiplier));
        if (game.graphics.getHeight(id) <= game.graphics.getHeight(id2)) {
            // call to check oddity aggression
            filledAggressiveOddityReset();
        }
    }

    public static void addPermanentFillMultiplier (float f) {
        permanentImpatienceMultiplier = permanentImpatienceMultiplier + f;
    }

    public static void addTemporaryFillMultiplier (float f) {
        temporaryImpatienceMultiplier = temporaryImpatienceMultiplier + f;
    }

    public static void filledAggressiveOddityReset () {
        game.graphics.setHeight(id2, game.graphics.getHeight(id)/100);
        permanentImpatienceMultiplier = 1.0f;
        temporaryImpatienceMultiplier = 1.0f;
        //call to subtract checkpoint hp
    }
    public static void filledNormalOddityReset () {
        game.graphics.setHeight(id2, game.graphics.getHeight(id)/100);
        permanentImpatienceMultiplier = permanentImpatienceMultiplier + 0.5f;
        temporaryImpatienceMultiplier = 1.0f;
    }
    public static void reset () {
        game.graphics.setHeight(id2, game.graphics.getHeight(id)/100);
        permanentImpatienceMultiplier = 1.0f;
        temporaryImpatienceMultiplier = 1.0f;
    }
}
