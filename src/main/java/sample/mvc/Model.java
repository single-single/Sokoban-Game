package sample.mvc;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import sample.objects.GameObject;
import sample.objects.GraphicObject;
import sample.start.Level;
import sample.start.StartMeUp;

import java.awt.*;
import java.io.*;

/**
 * Model encapsulates various methods that need to be used in View.
 * It cooperates with the {@code StartMeUp} object gameEngine to run the game.
 * In addition to satisfying the basic operations of the game,
 * it also provides some other functions (save the game, load the game, etc.)
 *
 * @author Shiqi XIN
 */
public class Model {

    private Stage primaryStage;
    private String nickName;
    private StartMeUp gameEngine;
    private GridPane gameGrid;
    private File saveFile;
    private File fileToSave;
    private String levelFile;
    private boolean isButtonClicked = false;
    private Timeline clock;
    private boolean isClockRun = true;
    private boolean isGamePause = false;
    private int levelTime = 0;
    private int totalTime = 0;
    private IntegerProperty movesCountProperty = new SimpleIntegerProperty();
    private IntegerProperty levelTimeProperty = new SimpleIntegerProperty();
    private final int TEXT1FONT = 14;
    private final int VBOXSPACING = 20;
    private final int DIALOGTOP = 30;
    private final int DIALOGRIGHT = 30;
    private final int DIALOGBOTTOM = 30;
    private final int DIALOGLEFT = 30;

    /**
     * This is the only constructor of the Model class.
     * It initializes the {@code GridPane gameGrid}.
     */
    public Model() {
        gameGrid = new GridPane();
    }

    /**
     * This is the method used to get the {@code GridPane gameGrid}.
     * @return a {@code GridPane gameGrid} to View.
     */
    public GridPane GetGameGrid() {
        return gameGrid;
    }

    /**
     * This is the method used to load the default game.
     * It load the game file, initialize game and set event filter.
     * @param primaryStage {@code Stage} passed in from Main.
     * @param nickName {@code String} representing the player's nickname obtained from View.
     */
    void loadDefaultSaveFile(Stage primaryStage, String nickName) {
        this.primaryStage = primaryStage;
        this.nickName = nickName;
        System.out.println("Hi");
        InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
        System.out.println(in);
        InitializeGame(in);
        System.out.println("Hi");
        SetEventFilter();
        System.out.println("Hi");
    }

    /**
     * This is the method used to initialize the game.
     * It set the game by instancing a {@code StartMeUp} object gameEngine.
     * @param input {@code InputStream} representing the game file.
     */
    public void InitializeGame(InputStream input) {
        gameEngine = new StartMeUp(input, true);
        reloadGrid();
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the method used to set the event filter.
     * It gets the event passed in from the keyboard
     * and calls the HandleKey method as a parameter.
     */
    public void SetEventFilter() {
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (isGamePause) {
                return;
            }
            gameEngine.HandleKey(event.getCode());
            reloadGrid();
        });
    }

    /**
     * This is the method used to get the game engine in the Model.
     * It passes the {@code gameEngine} in the Model to SubModel.
     * @return {@code StartMeUp gameEngine}.
     */
    public StartMeUp GetGameEngine() {
        return gameEngine;
    }

    /**
     * This is the method used to set the time of level in the game.
     * It set the current {@code levelTime} to the saved {@code int time}.
     * @param time {@code int} representing the time of level in the saved game.
     */
    public void SetTimeLevel(int time) {
        levelTime = time;
    }

    /**
     * This is the method used to set the total time in the game.
     * It set the current {@code totalTime} to the saved {@code int time}.
     * @param time {@code int} representing the total time in the saved game.
     */
    public void SetTimeTotal(int time) {
        totalTime = time;
    }

    /**
     * This is the method used to get the IntegerProperty of moves count.
     * It passes the {@code IntegerProperty movesCountProperty} to View.
     * @return {@code IntegerProperty movesCountProperty}
     * which is the IntegerProperty of {@code int movesCount}.
     */
    public IntegerProperty MovesCountProperty() {
        return movesCountProperty;
    }

    /**
     * This is the method used to set the {@code IntegerProperty movesCountProperty}.
     * It set the content of {@code IntegerProperty movesCountProperty} to {@code int movesCount}.
     */
    public void SetMovesCount() {
        movesCountProperty.set(gameEngine.GetMovesCountLevel());
    }

    /**
     * This is the method used to get the moves count.
     * It passes the {@code int movesCount} to View.
     * @return {@code int movesCount} which is the current moves count.
     */
    public int GetMovesCount() {
        return movesCountProperty.get();
    }

    /**
     * This is the method used to get the IntegerProperty of time of level.
     * It passes the {@code IntegerProperty levelTimeProperty} to View.
     * @return {@code IntegerProperty levelTimeProperty}
     * which is the IntegerProperty of {@code int levelTime}.
     */
    public IntegerProperty LevelTimeProperty(){
        return levelTimeProperty;
    }

    /**
     * This is the method used to set the {@code IntegerProperty levelTimeProperty}.
     * It set the content of {@code IntegerProperty levelTimeProperty} to {@code int levelTime}.
     */
    public void SetLevelTime() {
        levelTimeProperty.set(levelTime);
    }

    /**
     * This is the method used to get the time of level.
     * It passes the {@code int levelTime} to View.
     * @return {@code int levelTime} which is the current time of level.
     */
    public int GetLevelTime() {
        return levelTimeProperty.get();
    }

    /**
     * This is the method used to time each level.
     * It set up an infinite timeline, and adds 1 to {@code int levelTime} every second
     */
    public void Clock() {
        clock = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            levelTime++;
            SetLevelTime();
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    /**
     * This is the method to reload the game grid.
     * It displays the information and returns after each level is over,
     * and reloads all objects in the current level at other times.
     */
    void reloadGrid() {
        SetMovesCount();

        if (gameEngine.IsGameComplete()) {
            ShowVictoryMessage();
            return;
        }

        if (gameEngine.IsLevelComplete() && !gameEngine.IsGameComplete()) {
            if (!isButtonClicked) {
                ShowLevelMessage();
                return;
            }
            gameEngine.SetLevelComplete();
        }

        Level currentLevel = gameEngine.GetCurrentLevel();
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();
        gameGrid.getChildren().clear();
        while (levelGridIterator.hasNext()) {
            AddObjectToGrid(levelGridIterator.next(), levelGridIterator.GetCurrentPosition());
        }
        gameGrid.autosize();
        primaryStage.sizeToScene();
    }

    /**
     * This is the method used to get the file path corresponding to the current level.
     * @return {@code String levelFile} which is the file path.
     */
    public String GetLevelFilePath() {
        int currentLevelIndex = gameEngine.GetLevelIndex();
        switch (currentLevelIndex) {
            case 0:
                levelFile = "src/main/resources/highscore_lists/level1.txt";
                break;

            case 1:
                levelFile = "src/main/resources/highscore_lists/level2.txt";
                break;

            case 2:
                levelFile = "src/main/resources/highscore_lists/level3.txt";
                break;

            case 3:
                levelFile = "src/main/resources/highscore_lists/level4.txt";
                break;

            case 4:
                levelFile = "src/main/resources/highscore_lists/level5.txt";
                break;
        }

        return levelFile;
    }

    /**
     * This method save and update the high score list of current level to a file.
     * It passes the high score list of current level to {@code ShowLevelMessage()}.
     * @return {@code String list} which is the high score list of current level.
     */
    public String ShowHighscoreListLevel() {
        String list = "";
        try {
            String name = "";
            String nameTemp = nickName;
            String line = "";
            boolean isMe = true;
            int rank = 0;
            int lineCount = 0;
            int movesCount = 0;
            int movesCountTemp = gameEngine.GetMovesCountLevel();
            long timeInterval = 0;
            long timeIntervalTemp = levelTime;
            totalTime += timeIntervalTemp;
            StringBuilder inputBuffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(GetLevelFilePath()));
            while (true) {
                lineCount++;
                line = reader.readLine();
                if (line == null) {
                    if (lineCount/3 < 10) {
                        inputBuffer.append("Name: ").append(nameTemp).append('\n');
                        inputBuffer.append("Time: ").append(timeIntervalTemp).append('\n');
                        inputBuffer.append("Moves: ").append(movesCountTemp).append('\n');
                        if (isMe) {
                            rank = lineCount/3 + 1;
                            isMe = false;
                        }
                    }
                    break;
                }
                if (line.contains("Name: ")) {
                    name = line.replaceFirst("Name: ", "");
                }
                if (line.contains("Time: ")) {
                    timeInterval = Integer.parseInt(line.replaceFirst("Time: ", ""));
                }
                if (line.contains("Moves: ")) {
                    movesCount = Integer.parseInt(line.replaceFirst("Moves: ", ""));
                }
                if (lineCount%3 == 0) {
                    if (timeIntervalTemp < timeInterval || (timeIntervalTemp == timeInterval && movesCountTemp < movesCount)) {
                        inputBuffer.append("Name: ").append(nameTemp).append('\n');
                        inputBuffer.append("Time: ").append(timeIntervalTemp).append('\n');
                        inputBuffer.append("Moves: ").append(movesCountTemp).append('\n');
                        nameTemp = name;
                        timeIntervalTemp = timeInterval;
                        movesCountTemp = movesCount;
                        if (isMe) {
                            rank = lineCount/3;
                            isMe = false;
                        }
                    } else {
                        inputBuffer.append("Name: ").append(name).append('\n');
                        inputBuffer.append("Time: ").append(timeInterval).append('\n');
                        inputBuffer.append("Moves: ").append(movesCount).append('\n');
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(GetLevelFilePath()));
            writer.write(inputBuffer.toString());
            writer.flush();
            writer.close();
            reader.close();

            if (!isMe) {
                inputBuffer.append("\n").append("You are in rank ").append(rank).append(".");
            } else {
                inputBuffer.append("\n").append("Unfortunately, you are not on the list.");
            }
            list = inputBuffer.toString();
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    /**
     * This method save and update the high score list of the game to a file.
     * It passes the high score list of the game to {@code ShowVictoryMessage()}.
     * @return {@code String list} which is the high score list of the game.
     */
    public String ShowHighscoreList() {
        String list = "";
        try {
            String name = "";
            String nameTemp = nickName;
            String line = "";
            boolean isMe = true;
            int rank = 0;
            int lineCount = 0;
            int movesCount = 0;
            int movesCountTemp = gameEngine.GetMovesCount();
            long timeTotal = 0;
            long timeTotalTemp = totalTime + levelTime;
            StringBuilder inputBuffer = new StringBuilder();
            String filePath = "src/main/resources/highscore_lists/game.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (true) {
                lineCount++;
                line = reader.readLine();
                if (line == null) {
                    if (lineCount/3 < 10) {
                        inputBuffer.append("Name: ").append(nameTemp).append('\n');
                        inputBuffer.append("Time: ").append(timeTotalTemp).append('\n');
                        inputBuffer.append("Moves: ").append(movesCountTemp).append('\n');
                        if (isMe) {
                            rank = lineCount/3 + 1;
                            isMe = false;
                        }
                    }
                    break;
                }
                if (line.contains("Name: ")) {
                    name = line.replaceFirst("Name: ", "");
                }
                if (line.contains("Time: ")) {
                    timeTotal = Integer.parseInt(line.replaceFirst("Time: ", ""));
                }
                if (line.contains("Moves: ")) {
                    movesCount = Integer.parseInt(line.replaceFirst("Moves: ", ""));
                }
                if (lineCount%3 == 0) {
                    if (timeTotalTemp < timeTotal || (timeTotalTemp == timeTotal && movesCountTemp < movesCount)) {
                        inputBuffer.append("Name: ").append(nameTemp).append('\n');
                        inputBuffer.append("Time: ").append(timeTotalTemp).append('\n');
                        inputBuffer.append("Moves: ").append(movesCountTemp).append('\n');
                        nameTemp = name;
                        timeTotalTemp = timeTotal;
                        movesCountTemp = movesCount;
                        if (isMe) {
                            rank = lineCount/3;
                            isMe = false;
                        }
                    } else {
                        inputBuffer.append("Name: ").append(name).append('\n');
                        inputBuffer.append("Time: ").append(timeTotal).append('\n');
                        inputBuffer.append("Moves: ").append(movesCount).append('\n');
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(inputBuffer.toString());
            writer.flush();
            writer.close();
            reader.close();

            if (!isMe) {
                inputBuffer.append("\n").append("You are in rank ").append(rank).append(".");
            } else {
                inputBuffer.append("\n").append("Unfortunately, you are not on the list.");
            }
            list = inputBuffer.toString();
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    /**
     * This method shows the victory message of current level.
     * It get the high score list from {@code ShowHighscoreListLevel()}
     * and append some extra information and shows.
     */
    public void ShowLevelMessage() {
        String dialogTitle = "Game Over!";
        String dialogMessage = "You completed level " + (gameEngine.GetLevelIndex() + 1)
                + " in " + gameEngine.GetMovesCountLevel() + " moves!\n"
                + "The following is the high score list of this level: \n\n" + ShowHighscoreListLevel();

        NewDialog(dialogTitle, dialogMessage, null);
    }

    /**
     * This method shows the victory message of the game.
     * It get the high score list from {@code ShowHighscoreList()}
     * and append some extra information and shows.
     */
    public void ShowVictoryMessage() {
        String dialogTitle = "Game Over!";
        String dialogMessage = "You completed " + gameEngine.GetMapSetName()
                + " in " + gameEngine.GetMovesCount() + " moves!\n"
                + "The following is the high score list: \n\n" + ShowHighscoreList();

        NewDialog(dialogTitle, dialogMessage, null);
    }

    /**
     * This method provides several dialog options.
     * If the level is completed, it shows a dialog with a button to move to next level.
     * Else if the game is completed, it shows a dialog with a button to exit.
     * Otherwise, it shows a dialog without button.
     * @param dialogTitle {@code String dialogTitle} which is the title of the dialog.
     * @param dialogMessage {@code String dialogMessage} which is the message of the dialog.
     * @param dialogMessageEffect {@code Effect dialogMessageEffect} which is the effect of the dialog message.
     */
    public void NewDialog(String dialogTitle, String dialogMessage, Effect dialogMessageEffect) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle(dialogTitle);

        Text text1 = new Text(dialogMessage);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(javafx.scene.text.Font.font(TEXT1FONT));

        if (dialogMessageEffect != null) {
            text1.setEffect(dialogMessageEffect);
        }

        VBox dialogVbox = new VBox(VBOXSPACING);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setBackground(Background.EMPTY);
        dialogVbox.setPadding(new Insets(DIALOGTOP, DIALOGRIGHT, DIALOGBOTTOM, DIALOGLEFT));

        Button buttonNext = new Button("Next Level");
        Button buttonExit = new Button("Exit");
        buttonNext.setOnAction(actionEvent -> {
            levelTime = 0;
            gameEngine.ResetMovesCountLevel();
            isButtonClicked = true;
            dialog.close();
            reloadGrid();
            isButtonClicked = false;
        });
        buttonExit.setOnAction(actionEvent -> {System.exit(0);});

        if (gameEngine.IsGameComplete()) {
            dialogVbox.getChildren().addAll(text1, buttonExit);
        } else if (gameEngine.IsLevelComplete()) {
            dialogVbox.getChildren().addAll(text1, buttonNext);
        } else {
            dialogVbox.getChildren().add(text1);
        }

        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (gameEngine.IsGameComplete()) {
                    System.exit(0);
                } else if (gameEngine.IsLevelComplete()) {
                    levelTime = 0;
                    gameEngine.ResetMovesCountLevel();
                    isButtonClicked = true;
                    reloadGrid();
                    isButtonClicked = false;
                } else {
                    // Do Nothing;
                }
            }
        });
    }

    /**
     * This method add an object to a specific location.
     * @param gameObject {@code GameObject} which is the object to be added.
     * @param location {@code Point} which is the point to be added.
     */
    public void AddObjectToGrid(GameObject gameObject, Point location) {
        GraphicObject graphicObject = new GraphicObject(gameObject);
        gameGrid.add(graphicObject, location.y, location.x);
    }

    /**
     * This method append some extra information to the saved game.
     * @return {@code String} which is the information of the saved game.
     */
    public String GameFile() {
        StringBuilder gameBuilder = new StringBuilder();
        gameBuilder.append("moves: ").append(gameEngine.GetMovesCountLevel()).append("\n");
        gameBuilder.append("total moves: ").append(gameEngine.GetMovesCount()).append("\n");
        gameBuilder.append("time: ").append(levelTime).append("\n");
        gameBuilder.append("total time: ").append(totalTime + levelTime).append("\n");
        gameBuilder.append("level index: ").append(gameEngine.GetLevelIndex()).append("\n\n");
        gameBuilder.append(gameEngine.SaveGame());
        return gameBuilder.toString();
    }

    /**
     * This method is used to save the game.
     * It write the String get from {@code GameFile()} to a file.
     */
    public void SaveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        fileToSave = fileChooser.showSaveDialog(primaryStage);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
            writer.write(GameFile());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to load a saved game file.
     * It calls {@code InitializeGame()} method to initialize the saved game.
     * And then set the time of level to the saved one.
     * @throws FileNotFoundException when the game file is not founded.
     */
    public void LoadGameFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        saveFile = fileChooser.showOpenDialog(primaryStage);

        if (saveFile != null) {
            if (StartMeUp.IsDebugActive()) {
                StartMeUp.m_logger.info("Loading save file: " + saveFile.getName());
            }
            InitializeGame(new FileInputStream(saveFile));
            SetTimeLevel(gameEngine.GetTimeLevel());
            SetTimeTotal(gameEngine.GetTimeTotal());
        }
    }

    /**
     * This method load the game by calling {@code LoadGameFile()} method.
     */
    public void LoadGame() {
        boolean isPlaying = false;
        try {
            if (gameEngine.IsPlayingMusic()) {
                isPlaying = true;
                gameEngine.StopMusic();
            }
            LoadGameFile();
            if (isPlaying) {
                gameEngine.PlayMusic();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to pause the game.
     * If the game is paused, it continue timing
     * and make the keeper movable by set {@code isGamePause} to false.
     * Otherwise, it pause timing
     * and make the keeper immovable by set {@code isGamePause} to true.
     */
    public void PauseGame() {
        if (isClockRun) {
            clock.pause();
            isClockRun = false;
            isGamePause = true;
        } else {
            clock.play();
            isClockRun = true;
            isGamePause = false;
        }
    }

}