package sample.mvc;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.start.StartMeUp;

/**
 * View is responsible for providing the user interface.
 * Every single items in the primary stage is shown by View.
 * When some action events occur,
 * View will call the methods in the Model through the Controller.
 * The View class listens to the Model class,
 * and some elements in View will change with changes in Model.
 *
 * @author Shiqi XIN
 */
public class View {

    private Controller controller;
    private Model model;
    private Stage primaryStage;
    private Scene sceneStart;
    private Scene sceneGame;
    private GridPane viewStart;
    private GridPane viewGame;
    private Button buttonStart;
    private String nickName;
    private Label nameLabel;
    private TextField field;
    private Label colourLabel;
    private Label wallColour;
    private String[] choiceColour;
    private String[] textColour;
    private ChoiceBox<Object> choiceBox;
    private GridPane gameGrid;
    private MenuBar menuGame;
    private MenuItem menuItemSaveGame;
    private MenuItem menuItemLoadGame;
    private MenuItem menuItemExit;
    private Menu menuFile;
    private MenuItem menuItemUndo;
    private RadioMenuItem radioMenuItemMusic;
    private RadioMenuItem radioMenuItemDebug;
    private MenuItem menuItemResetLevel;
    private Menu menuLevel;
    private MenuItem menuItemGame;
    private Menu menuAbout;
    private Label movesCountLabel;
    private Label timeLabel;
    private Button buttonPause;

    private final int BUTTONTOP = 40;
    private final int BUTTONRIGHT = 45;
    private final int BUTTONBOTTOM = 40;
    private final int BUTTONLEFT = 45;
    private final int BUTTONFONT = 30;
    private final int BUTTONRADI = 30;
    private final String BUTTONCOLOUR = "#FFA54F";
    private final int HBBTNTOP = 50;
    private final int HBBTNRIGHT = 90;
    private final int HBBTNBOTTOM = 50;
    private final int HBBTNLEFT = 90;
    private final String GREETFONT = "-fx-font-size: 16;";
    private final String GREETCOLOUR = "-fx-background-color: #FFC1C1;";
    private final int HBGREETTOP = 0;
    private final int HBGREETRIGHT = 0;
    private final int HBGREETBOTTOM = 20;
    private final int HBGREETLEFT = 100;
    private final int FIELDWIDTH = 200;
    private final int FIELDHEIGHT = 45;
    private final String FIELDFONT = "-fx-font-size: 16;";
    private final String FIELDRADI = "-fx-background-radius: 10;";
    private final int FIELDCOUNT = 11;
    private final int HBFIELDTOP = 0;
    private final int HBFIELDRIGHT = 0;
    private final int HBFIELDBOTTOM = 35;
    private final int HBFIELDLEFT = 100;
    private final String COLOURFONT = "-fx-font-size: 26;";
    private final String CHOICEFONT = "-fx-font-size: 16;";
    private final String CHOICERADI = "-fx-background-radius: 10;";
    private final int HBCHOICESPACE = 30;
    private final int HBCHOICETOP = 0;
    private final int HBCHOICERIGHT = 0;
    private final int HBCHOICEBOTTOM = 35;
    private final int HBCHOICELEFT = 100;
    private final String COUNTERFONT = "-fx-font-size: 14;";
    private final String PAUSEFONT = "-fx-font-size: 14;";
    private final String PAUSERADI = "-fx-background-radius: 8;";
    private final int HBPAUSETOP = 0;
    private final int HBPAUSERIGHT = 250;
    private final int HBPAUSEBOTTOM = 5;
    private final int HBPAUSELEFT = 5;
    private final int HBCOUNTERSPACE = 50;
    private final int HBCOUNTERTOP = 5;
    private final int HBCOUNTERRIGHT = 0;
    private final int HBCOUNTERBOTTOM = 0;
    private final int HBCOUNTERLEFT = 20;

    /**
     * This is the only constructor of the View class.
     * It initializes Controller object and Model object.
     * It also sets the stage get from Main class to be the primaryStage.
     * @param controller {@code Controller} object.
     * @param model {@code Model} object.
     * @param primaryStage {@code Stage} object to show the scenes.
     */
    public View(Controller controller, Model model, Stage primaryStage) {
        this.controller = controller;
        this.model = model;
        this.primaryStage = primaryStage;
        createAndConfigurePane();
        createAndLayoutControlsStart();
        createAndLayoutControlsGame();
        updateControllerFromListener();
        createAndLayoutPrimaryStage();
        observeModelAndUpdateControls();
    }

    /**
     * This method creates 2 views,
     * which are used to place all the elements of
     * the start interface and the game interface respectively.
     */
    private void createAndConfigurePane() {
        viewStart = new GridPane();
        viewStart.setAlignment(Pos.CENTER);
        viewGame = new GridPane();
        viewGame.setAlignment(Pos.CENTER);
    }

    /**
     * This method adds all the elements of the start screen to {@code sceneStart} and styles them.
     */
    private void createAndLayoutControlsStart() {
        buttonStart = new Button("Start Game!");
        buttonStart.setPadding(new Insets(BUTTONTOP, BUTTONRIGHT, BUTTONBOTTOM, BUTTONLEFT));
        buttonStart.setFont(Font.font(BUTTONFONT));
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf(BUTTONCOLOUR), new CornerRadii(BUTTONRADI), Insets.EMPTY);
        Background background = new Background(backgroundFill);
        buttonStart.setBackground(background);
        HBox hbButton = new HBox();
        hbButton.getChildren().add(buttonStart);
        hbButton.setPadding(new Insets(HBBTNTOP, HBBTNRIGHT, HBBTNBOTTOM, HBBTNLEFT));

        nameLabel = new Label();
        nameLabel.setText("Please enter your nickname");
        nameLabel.setStyle(GREETFONT);
        HBox hbNameLabel = new HBox();
        hbNameLabel.getChildren().add(nameLabel);
        hbNameLabel.setPadding(new Insets(HBGREETTOP, HBGREETRIGHT, HBGREETBOTTOM, HBGREETLEFT));

        field = new TextField();
        field.setPrefSize(FIELDWIDTH, FIELDHEIGHT);
        field.setStyle(FIELDFONT + FIELDRADI);
        field.setEditable(true);
        field.setPromptText("Nickname Here");
        field.setAlignment(Pos.CENTER_LEFT);
        field.setPrefColumnCount(FIELDCOUNT);
        HBox hbField = new HBox();
        hbField.getChildren().add(field);
        hbField.setPadding(new Insets(HBFIELDTOP, HBFIELDRIGHT, HBFIELDBOTTOM, HBFIELDLEFT));

        colourLabel = new Label();
        colourLabel.setText("Please select the wall colour");
        colourLabel.setStyle(GREETFONT);
        HBox hbColourLabel = new HBox();
        hbColourLabel.getChildren().add(colourLabel);
        hbColourLabel.setPadding(new Insets(HBGREETTOP, HBGREETRIGHT, HBGREETBOTTOM, HBGREETLEFT));

        wallColour = new Label();
        choiceColour = new String[] {"Default", "Blue", "Brown", "gray", "green", "red", "orange", "yellow"};
        textColour = new String[] {"-fx-background-color: #9C9C9C;",
                "-fx-background-color: #00BFFF;", "-fx-background-color: #CD853F;",
                "-fx-background-color: #BEBEBE;", "-fx-background-color: #00EE76;",
                "-fx-background-color: #EE6363;", "-fx-background-color: #FFA54F;",
                "-fx-background-color: #FFE7BA;"};
        wallColour.setText(choiceColour[0]);
        wallColour.setStyle(COLOURFONT + textColour[0]);
        choiceBox = new ChoiceBox<>();
        choiceBox.setStyle(CHOICEFONT + CHOICERADI);
        choiceBox.setTooltip(new Tooltip("Select the wall colour"));
        choiceBox.setItems(FXCollections.observableArrayList(
                "Default", "Blue", "Brown", "gray", "green", "red", "orange", "yellow"));
        HBox hbChoice = new HBox();
        hbChoice.getChildren().addAll(choiceBox, wallColour);
        hbChoice.setSpacing(HBCHOICESPACE);
        hbChoice.setPadding(new Insets(HBCHOICETOP, HBCHOICERIGHT, HBCHOICEBOTTOM, HBCHOICELEFT));

        viewStart.add(hbButton, 0, 0);
        viewStart.add(hbNameLabel, 0, 1);
        viewStart.add(hbField, 0, 2);
        viewStart.add(hbColourLabel, 0, 3);
        viewStart.add(hbChoice, 0, 4);

        sceneStart = new Scene(viewStart);
    }

    /**
     * This method adds all the elements of the game screen to {@code sceneGame} and styles them.
     */
    private void createAndLayoutControlsGame() {
        menuGame = new MenuBar();
        gameGrid = new GridPane();

        menuItemSaveGame = new MenuItem("Save Game");
        menuItemLoadGame = new MenuItem("Load Game");
        menuItemExit = new MenuItem("Exit");
        menuFile = new Menu("File");
        menuFile.getItems().addAll(menuItemSaveGame, menuItemLoadGame, new SeparatorMenuItem(), menuItemExit);

        menuItemUndo = new MenuItem("Undo");
        radioMenuItemMusic = new RadioMenuItem("Toggle Music");
        radioMenuItemDebug = new RadioMenuItem("Toggle Debug");
        menuItemResetLevel = new MenuItem("Reset Level");
        menuLevel = new Menu("Level");
        menuLevel.getItems().addAll(menuItemUndo, radioMenuItemMusic, radioMenuItemDebug,
        new SeparatorMenuItem(), menuItemResetLevel);

        menuItemGame = new MenuItem("About This Game");
        menuAbout = new Menu("About");
        menuAbout.getItems().addAll(menuItemGame);

        menuGame.getMenus().addAll(menuFile, menuLevel, menuAbout);

        buttonPause = new Button("Pause");
        buttonPause.setStyle(PAUSEFONT + PAUSERADI);
        HBox hbPause = new HBox();
        hbPause.getChildren().add(buttonPause);
        hbPause.setPadding(new Insets(HBPAUSETOP, HBPAUSERIGHT, HBPAUSEBOTTOM, HBPAUSELEFT));

        movesCountLabel = new Label();
        timeLabel = new Label();
        movesCountLabel.setText("Moves: 0");
        timeLabel.setText("Time: 0");
        movesCountLabel.setStyle(COUNTERFONT);
        timeLabel.setStyle(COUNTERFONT);

        HBox hbCounter = new HBox();
        hbCounter.getChildren().addAll(hbPause, movesCountLabel, timeLabel);
        hbCounter.setSpacing(HBCOUNTERSPACE);
        hbCounter.setPadding(new Insets(HBCOUNTERTOP, HBCOUNTERRIGHT, HBCOUNTERBOTTOM, HBCOUNTERLEFT));

        gameGrid = controller.GetGameGrid();

        viewGame.add(menuGame, 0, 0);
        viewGame.add(hbCounter, 0, 1);
        viewGame.add(gameGrid, 0, 2);

        sceneGame = new Scene(viewGame);
    }

    /**
     * This method sets action event for elements in 2 scenes
     * and calls methods in the Model through the Controller.
     */
    private void updateControllerFromListener() {
        buttonStart.setOnAction(actionEvent -> {
            if (!field.getText().trim().isEmpty()) {
                nickName = field.getText();
                controller.LoadDefaultSaveFile(primaryStage, nickName);
                primaryStage.setScene(sceneGame);
                controller.Clock();
            } else {
                nameLabel.setText("You have not enter your nickname");
                nameLabel.setStyle(GREETFONT + GREETCOLOUR);
            }
        });

        menuItemSaveGame.setOnAction(actionEvent -> {controller.SaveGame();});
        menuItemLoadGame.setOnAction(actionEvent -> {controller.LoadGame();});
        menuItemExit.setOnAction(actionEvent -> {controller.CloseGame();});

        menuItemUndo.setOnAction(actionEvent -> {controller.Undo();});
        radioMenuItemMusic.setOnAction(actionEvent -> {controller.ToggleMusic();});
        radioMenuItemDebug.setOnAction(actionEvent -> {controller.ToggleDebug();});
        menuItemResetLevel.setOnAction(actionEvent -> {controller.ResetLevel();});

        menuAbout.setOnAction(actionEvent -> {controller.ShowAbout();});

        buttonPause.setOnAction(actionEvent -> {
            if (buttonPause.getText().equals("Pause")) {
                buttonPause.setText("Continue");
                menuFile.setDisable(true);
                menuLevel.setDisable(true);
            } else {
                buttonPause.setText("Pause");
                menuFile.setDisable(false);
                menuLevel.setDisable(false);
            }
            controller.PauseGame();
        });
    }

    /**
     * This method sets up the listener of
     * the choice box in View and the number of steps and time in Model.
     * The elements in the View will change as these things change.
     */
    private void observeModelAndUpdateControls() {
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldV, newV)->{
            wallColour.setText(choiceColour[newV.intValue()]);
            wallColour.setStyle(COLOURFONT + textColour[newV.intValue()]);
            controller.SetWallColour(newV.intValue());
        });
        model.MovesCountProperty().addListener((obs,oldX,newX)->{
            movesCountLabel.setText("Moves: " + model.GetMovesCount());
        });
        model.LevelTimeProperty().addListener((obs,oldX,newX)->{
            timeLabel.setText("Time: " + model.GetLevelTime());
        });
    }

    /**
     * This method sets the scene in primaryStage as {@code sceneStart} and displays it.
     */
    private void createAndLayoutPrimaryStage() {
        primaryStage.setTitle(StartMeUp.GAME_NAME);
        primaryStage.setScene(sceneStart);
        primaryStage.show();
    }

}