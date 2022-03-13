package sample.mvc;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller is responsible for linking View layer and Model layer.
 * The Controller receives the request from the View and calls the method in the Model.
 *
 * @author Shiqi XIN
 */
public class Controller {

    private Model model;
    private SubModel subModel;

    /**
     * This is the only constructor of the Controller class.
     * It initializes the model and subModel objects.
     * @param model {@code Model} object
     * @param subModel {@code SubModel} object
     */
    public Controller(Model model, SubModel subModel) {
        this.model = model;
        this.subModel = subModel;
    }

    /**
     * This is the method used to call the SaveGame function in the Model.
     * It receives the request from the View to save the game
     * and calls the corresponding method in the Model to save the game.
     */
    public void SaveGame() { model.SaveGame(); }

    /**
     * This is the method used to call the LoadGame function in the Model.
     * It receives the request from the View to load the game
     * and calls the corresponding method in the Model to load the game.
     */
    public void LoadGame() { model.LoadGame(); }

    /**
     * This is the method used to call the CloseGame function in the SubModel.
     * It receives the request from the View to close the game
     * and calls the corresponding method in the SubModel to close the game.
     */
    public void CloseGame() { subModel.CloseGame(); }

    /**
     * This is the method used to call the Undo function in the SubModel.
     * It receives the request from the View to undo the step
     * and calls the corresponding method in the SubModel to undo the step.
     */
    public void Undo() { subModel.Undo(); }

    /**
     * This is the method used to call the ToggleMusic function in the SubModel.
     * It receives the request from the View to play music
     * and calls the corresponding method in the SubModel to play music.
     */
    public void ToggleMusic() { subModel.ToggleMusic(); }

    /**
     * This is the method used to call the ToggleDebug function in the SubModel.
     * It receives the request from the View to debug
     * and calls the corresponding method in the SubModel to debug.
     */
    public void ToggleDebug() { subModel.ToggleDebug(); }

    /**
     * This is the method used to call the SetWallColour function in the SubModel.
     * It receives the request from the View to set the wall colour
     * and calls the corresponding method in the SubModel to set the wall colour.
     * @param index {@code int} received from the View, each index represents a color.
     */
    public void SetWallColour(int index) { subModel.SetWallColour(index); }

    /**
     * This is the method used to call the ResetLevel function in the SubModel.
     * It receives the request from the View to reset the level
     * and calls the corresponding method in the SubModel to reset the level.
     */
    public void ResetLevel() { subModel.ResetLevel(); }

    /**
     * This is the method used to call the ShowAbout function in the SubModel.
     * It receives the request from the View to show about message
     * and calls the corresponding method in the SubModel to show about message.
     */
    public void ShowAbout() { subModel.ShowAbout(); }

    /**
     * This is the method used to call the PauseGame function in the Model.
     * It receives the request from the View to pause the game
     * and calls the corresponding method in the Model to pause the game.
     */
    public void PauseGame() { model.PauseGame(); }

    /**
     * This is the method used to call the Clock function in the Model.
     * It receives the request from the View to start the timer
     * and calls the corresponding method in the Model to start the timer.
     */
    public void Clock() { model.Clock(); }

    /**
     * This is the method used to call the GetGameGrid function in the Model.
     * It receives the request from the View to get the gameGrid
     * and calls the corresponding method in the Model to
     * get the same gameGrid as the Model to ensure that the game runs normally.
     * @return {@code GameGrid gameGrid}
     */
    public GridPane GetGameGrid() { return model.GetGameGrid(); }

    /**
     * This is the method used to call the loadDefaultSaveFile function in the Model.
     * It receives the request from the View to start game
     * and calls the corresponding method in the Model to load the default game.
     * @param primaryStage {@code Stage} passed in from Main.
     * @param nickName {@code String} representing the player's nickname obtained from View.
     */
    public void LoadDefaultSaveFile(Stage primaryStage, String nickName) { model.loadDefaultSaveFile(primaryStage, nickName); }

}
