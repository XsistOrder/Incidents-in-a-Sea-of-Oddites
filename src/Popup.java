public class Popup {
    private static String setting = "main_menu";
    private static int id;
    private static Game game;

    public Popup(Game game, int x, int y, int width, int height) {
        this.game = game;
        id = game.graphics.addObject(x,y,width, height,"res\\textures\\menus\\settings_menu.jpg", 20, false);
    }
    public void changeAndShowPopup(String popup, boolean visibility) {

        setting = popup;
        switch (setting) {

            case "settings_popup":
                game.graphics.setImageDir(id, "res\\textures\\menus\\settings_menu.jpg");
                game.playButton.enabledAndHide(false, true);
                game.settingsButton.enabledAndHide(false, true);
                generateButtons();
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
        game.graphics.setVisible(id, visibility);
    }
    public void generateButtons(){
        Button closeButton = new Button(game, 200,200,200,200, "res\\textures\\interactive\\button.jpg", 25, "helphelphelp");
        closeButton.enabledAndHide(true,true);
    }

    public static String getPopup() {

        return setting;
    }
}
