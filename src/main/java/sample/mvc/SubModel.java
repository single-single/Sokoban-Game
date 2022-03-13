package sample.mvc;

import sample.start.StartMeUp;

/**
 * This class contains methods needed by some non-core functions in View.
 * It is split from the Model in order to make the responsibilities of classes clearer.
 *
 * @author Shiqi XIN
 */
public class SubModel {

    private Model model;
    private static int m_wall;

    /**
     * This is the only constructor of the SubModel class.
     * It initializes the model object.
     * @param model {@code Model} object
     */
    public SubModel(Model model) {
        this.model = model;
    }

    /**
     * This method close the game by calling {@code System.exit()}.
     */
    public void CloseGame() {
        System.exit(0);
    }

    /**
     * This method is used to undo the step.
     * It undo the step by calling {@code gameEngine.Undo()}.
     * The prompt message will be displayed if the keeper can not undo.
     */
    public void Undo() {
        StartMeUp gameEngine = model.GetGameEngine();
        if (gameEngine.Undo() == 0) {
            String dialogTitle = "Undo Failed!";
            String dialogMessage = "This is the initial step.";
            model.NewDialog(dialogTitle, dialogMessage, null);
        }
        model.reloadGrid();
    }

    /**
     * This method is used to play the music.
     * If the music is not playing,
     * it will call {@code gameEngine.PlayMusic()} to play the music,
     * Otherwise, it will call {@code gameEngine.StopMusic()} to stop the music,
     */
    public void ToggleMusic() {
        StartMeUp gameEngine = model.GetGameEngine();
        try {
            if (!gameEngine.IsPlayingMusic()) {
                gameEngine.CreatePlayer();
                gameEngine.PlayMusic();
            } else {
                gameEngine.StopMusic();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method calls {@code gameEngine.ToggleDebug()} to open the debug function.
     */
    public void ToggleDebug() {
        StartMeUp gameEngine = model.GetGameEngine();
        gameEngine.ToggleDebug();
        model.reloadGrid();
    }

    /**
     * This method is used to receive the wall colour passed by Controller.
     * It set the {@code int m_wall} to the parameter index.
     * @param index {@code int} received from the Controller, each index represents a color.
     */
    public void SetWallColour(int index) {
        m_wall = index;
    }

    /**
     * This method is used to get the wall colour.
     * It passes the colour index to {@code GraphicObject} to set the wall colour.
     * @return {@code int m_wall} representing the wall colour.
     */
    public static int GetWallColour() {
        return m_wall;
    }

    /**
     * This method is used to reset the level.
     * It reset the level by calling {@code gameEngine.ResetLevel()}.
     */
    public void ResetLevel() {
        StartMeUp gameEngine = model.GetGameEngine();
        gameEngine.ResetLevel();
        model.reloadGrid();
    }

    /**
     * This method is used to show the game information.
     * It shows the message by calling {@code model.NewDialog()}.
     */
    public void ShowAbout() {
        String title = "About This Game";
        String message = "Enjoy the Game!\n";
        model.NewDialog(title, message, null);
    }

}
