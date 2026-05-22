public class Popup {
    private static String setting = "settings_menu";
    private static int id;
    private static int id2;
    private static Game game;
    public Button closeButton;


    public Popup(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\settings_menu.png", 20, false);
        closeButton = new Button(game, 200,200,200,200, "res\\textures\\interactive\\close_button.png", 25, "helphelphelp");
    }

    public void changeAndShowPopup(String popup, boolean visibility) {

        setting = popup;
        game.graphics.setVisible(id, visibility);
        switch (setting) {

            case "settings_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\settings_menu.png");
                game.playButton.enabledAndHide(false, true);
                game.settingsButton.enabledAndHide(false, true);
                closeButton.enabledAndHide(true,true);
                break;
            case "controls_popup":

                break;
            case "difficulty_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\difficulty_menu.png");
                game.playButton.enabledAndHide(false, true);
                game.settingsButton.enabledAndHide(false, true);
                break;
            case "pause_popup":

                break;
            case "information_popup":

                break;
            case "documentation_popup":

                break;
            case "encyclopedia_popup":

                break;
            default:
                System.out.println("Opps you got an error from background: the settings field is incorrect");
                break;
        }
        System.out.println("long");
    }
    public void generateButtons(){


    }

    public static String getPopup() {

        return setting;
    }
}
