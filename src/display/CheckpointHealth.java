package display;

import main.Game;

public class CheckpointHealth {
    private static int id;
    private static int id2;
    private static Game game;
    private static int checkpointHealth = 5;

    public CheckpointHealth(Game game, int x, int y, int width, int height) {
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\non_interactive\\checkpoint_health_bar.jpg", 26, false);
    }
    public static int getId() {
        return id;
    }
}
