public class Popup {
    private static String setting = "main_menu";
    private static Game game;

    public Popup(Game game) {
        this.game = game;
    }
    public static void changeAndShowPopup(String background, boolean visibility) {

        setting = background;
        switch (setting) {

            case "settings_menu":
                game.graphics.setImageDir(0,"res\\textures\\menus\\main_menu.jpg");
                break;
            case "checkpoint_menu":
                game.graphics.setImageDir(0,"res\\textures\\menus\\checkpoint_menu.jpg");
                break;
            case "results_menu":
                game.graphics.setImageDir(0,"res\\textures\\menus\\results_menu.jpg");
                break;
            case "game_over_menu":
                game.graphics.setImageDir(0,"res\\textures\\menus\\game_over_menu.jpg");
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
