public class Background {

    private static String setting = "main_menu";
    private static int id;
    private static Game game;

    public Background(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\main_menu.jpg", 0, true);
    }
    public static void changeBackground(String background) {

        setting = background;
        switch (setting) {

            case "main_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\main_menu.jpg");
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\checkpoint_menu.jpg");
                break;
            case "results_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\results_menu.jpg");
                break;
            case "game_over_menu":
                game.graphics.setImageDir(id,"res\\textures\\menus\\game_over_menu.jpg");
                break;
            default:
                System.out.println("Opps you got an error from background: the settings field is incorrect");
                break;
        }
    }

    public static String getBackground() {

        return setting;
    }
}
