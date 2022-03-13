package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.mvc.Controller;
import sample.mvc.Model;
import sample.mvc.SubModel;
import sample.mvc.View;

/**
 * This is the Main class of the project.
 * It inherits {@code Application} class to run JavaFX.
 *
 * @author Shiqi XIN-modified
 */
public class Main extends Application {

    /**
     * This is the only constructor of the Main class.
     * It call {@code launch()} method to launch the game.
     * @param args {@code String args}.
     */
    public static void main(String[] args) {
        launch(args);
        System.out.println("Done!");
    }

    /**
     * The Start method is used to initialize the program.
     * It initialize model, subModel, controller and view.
     * Therefore, the MVC pattern is realized.
     * @param primaryStage stage to be showed.
     */
    @Override
    public void start(Stage primaryStage) {
        Model model = new Model();
        SubModel subModel = new SubModel(model);
        Controller controller = new Controller(model, subModel);
        View view = new View(controller, model, primaryStage);
    }

}
