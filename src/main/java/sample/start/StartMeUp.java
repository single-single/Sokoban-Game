package sample.start;

import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import sample.logger.GameLogger;
import sample.objects.GameObject;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The startMeUp class is the game engine of this game.
 * All methods related to the game engine in Model are defined here.
 *
 * @author Shiqi XIN-modified
 */
public class StartMeUp {

    public static final String GAME_NAME = "BestSokobanEverV6";
    public static GameLogger m_logger;
    private static boolean m_debug = false;
    private Level currentLevel;
    private String mapSetName = "";
    private List<Level> levels;
    private boolean gameComplete = false;
    private boolean levelComplete = false;
    private int movesCount = 0;
    private int movesCountLevel = 0;
    private int timeLevel = 0;
    private int timeTotal = 0;
    private int levelNumber = -1;
    private boolean isLevelLoad = false;
    private MediaPlayer player;
    private final int UP = -1;
    private final int DOWN = 1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int LEVELCAPACITY = 6;
    private static int m_keeper;
    private ArrayList<String> levelNames = new ArrayList<String>(LEVELCAPACITY);
    private ArrayList<Point> keeperLastMove = new ArrayList<Point>();
    private ArrayList<Point> crateLastMove = new ArrayList<Point>();
    private ArrayList<Point> newCrate = new ArrayList<Point>();
    private ArrayList<Point> movedCrate = new ArrayList<Point>();

    /**
     * This is the only constructor of the StartMeUp class.
     * It get {@code GameLogger} instance, load the game file and get current level.
     * @param input inputStream of game levels.
     * @param production {@code boolean}
     */
    public StartMeUp(InputStream input, boolean production) {
        try {
            m_logger = GameLogger.GetGameLogger();
            levels = LoadGameFile(input);
            currentLevel = GetNextLevel();

            if (production) {
                CreatePlayer();
            }
        } catch (NoSuchElementException e) {
            m_logger.warning("Cannot load the default save file: " + e.getStackTrace());
        } catch (LineUnavailableException e) {
            m_logger.warning("Cannot load the music file: " + e.getStackTrace());
        }
    }

    /**
     * This method checks whether debug is active.
     * @return {boolean m_debug} which is true when debug is active, false otherwise.
     */
    public static boolean IsDebugActive() {
        return m_debug;
    }

    /**
     * This method is used to get the total moves count.
     * @return {@code int movesCount}.
     */
    public int GetMovesCount() {
        return movesCount;
    }

    /**
     * This method is used to get the moves count of the current level.
     * @return {@code int movesCountLevel}.
     */
    public int GetMovesCountLevel() {
        return movesCountLevel;
    }

    /**
     * This method is used to get the time of the current level.
     * @return {@code int timeLevel}.
     */
    public int GetTimeLevel() {
        return timeLevel;
    }

    /**
     * This method is used to get the total time.
     * @return {@code int timeTotal}.
     */
    public int GetTimeTotal() {
        return timeTotal;
    }

    /**
     * This method is used to set the moves count of the current level to 0.
     */
    public void ResetMovesCountLevel() {
        movesCountLevel = 0;
    }

    /**
     * This method is used to get the map set name.
     * @return {@code String} of the map set name.
     */
    public String GetMapSetName() {
        return mapSetName;
    }

    /**
     * This method checks the keyboard input and calls {@code move()} method accordingly.
     * It also set the direction of the keeper when moving,
     * which use a static int variable to represent the direction.
     * @param code {@code keyCode code}.
     */
    public void HandleKey(KeyCode code) {
        try {
            switch (code) {
                case UP:
                    m_keeper = 1;
                    move(new Point(UP, 0));
                    break;

                case RIGHT:
                    m_keeper = 2;
                    move(new Point(0, RIGHT));
                    break;

                case DOWN:
                    m_keeper = 3;
                    move(new Point(DOWN, 0));
                    break;

                case LEFT:
                    m_keeper = 4;
                    move(new Point(0, LEFT));
                    break;

                default:
                    // TODO: implement something funny.
                    m_keeper = 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (IsDebugActive()) {
            System.out.println(code);
        }
    }

    /**
     * This method gets the index stand for the direction of the keeper.
     * @return {@code int} set when move, which stand for the direction.
     */
    public static int GetKeeper() {
        return m_keeper;
    }

    /**
     * This method input a Point delta and output the opposite point of this point.
     * @param delta {@code Point delta} which stands for up, right, down and left.
     * @return antiPoint which is the the opposite point of input.
     */
    private Point antiPoint(Point delta) {
        Point antiDelta;
        if (delta.equals(new Point(UP, 0))) {
            antiDelta = new Point(DOWN, 0);
        } else if (delta.equals(new Point(0, RIGHT))) {
            antiDelta = new Point(0, LEFT);
        } else if (delta.equals(new Point(DOWN, 0))) {
            antiDelta = new Point(UP, 0);
        } else if (delta.equals(new Point(0, LEFT))) {
            antiDelta = new Point(0, RIGHT);
        } else {
            antiDelta = new Point(0, 0);
        }
        return antiDelta;
    }

    /**
     * This method first checks whether the game is complete, if it is, return.
     * Otherwise, it moves the objects according to the target object of the keeper.
     * @param delta The direction to move to.
     */
    private void move(Point delta) {
        if (IsGameComplete()) {
            return;
        }

        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.getObjectAt(keeperPosition);
        Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.getObjectAt(targetObjectPoint);
        Point antiDelta = antiPoint(delta);
        boolean keeperMoved = false;

        if (StartMeUp.IsDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(currentLevel.toString());
            System.out.println("Keeper pos: " + keeperPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
        }

        switch (keeperTarget) {

            case WALL:
                break;

            case CRATE:
                GameObject crateTarget = currentLevel.getTargetObject(targetObjectPoint, delta);
                if (crateTarget != GameObject.FLOOR) {
                    break;
                }

                Point nextPoint = GameGrid.translatePoint(targetObjectPoint, delta);
                if (!movedCrate.contains(targetObjectPoint)) {
                    newCrate.add(targetObjectPoint);
                    movedCrate.add(nextPoint);
                } else {
                    movedCrate.set(movedCrate.indexOf(targetObjectPoint), nextPoint);
                }

                keeperLastMove.add(antiDelta);
                crateLastMove.add(antiDelta);
                currentLevel.moveGameObjectBy(keeperTarget, targetObjectPoint, delta);
                currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);
                keeperMoved = true;
                break;

            case FLOOR:
                keeperLastMove.add(antiDelta);
                crateLastMove.add(new Point(0, 0));

                currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);
                keeperMoved = true;
                break;

            default:
                m_logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }

        if (keeperMoved) {
            keeperPosition.translate((int) delta.getX(), (int) delta.getY());
            movesCount++;
            movesCountLevel++;
            if (currentLevel.isComplete()) {
                if (IsDebugActive()) {
                    System.out.println("Level complete!");
                }
                levelComplete = true;
                levelNumber++;
                currentLevel = GetNextLevel();
            }
        }
    }

    /**
     * This method reads the game from the input file.
     * and save levels in a arrayList.
     * It also initialize {@code movesCount}, {@code timeLoad},
     * {@code mapSetName} and {@code levelName}.
     * @param input the file's inputStream.
     * @return an arrayList contains levels.
     */
    public List<Level> LoadGameFile(InputStream input) {
        List<Level> levels = new ArrayList<>(LEVELCAPACITY);
        int levelIndex = 0;

        InputStreamReader inputStreamReader = new InputStreamReader(input);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";

            while (true) {
                String line = reader.readLine();

                // Break the loop if EOF is reached
                if (line == null) {
                    if (rawLevel.size() != 0) {
                        Level parsedLevel = new Level(levelName, levelIndex++, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                }
                if (line.contains("total moves: ")) {
                    movesCount = Integer.parseInt(line.replace("total moves: ", ""));
                    continue;
                }
                if (line.contains("moves: ")) {
                    movesCountLevel = Integer.parseInt(line.replace("moves: ", ""));
                    continue;
                }
                if (line.contains("total time: ")) {
                    timeTotal = Integer.parseInt(line.replace("total time: ", ""));
                    continue;
                }
                if (line.contains("time: ")) {
                    timeLevel = Integer.parseInt(line.replace("time: ", ""));
                    continue;
                }
                if (line.contains("level index: ")) {
                    isLevelLoad = true;
                    levelNumber = Integer.parseInt(line.replace("level index: ", ""));
                    continue;
                }
                if (line.contains("MapSetName")) {
                    mapSetName = line.replace("MapSetName: ", "");
                    continue;
                }
                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(levelName, levelIndex++, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }

                    levelName = line.replace("LevelName: ", "");
                    levelNames.add(levelName);
                    continue;
                }

                line = line.trim();
                line = line.toUpperCase();
                // If the line contains at least 2 WALLS, add it to the list
                if (line.matches(".*W.*W.*")) {
                    rawLevel.add(line);
                }
            }
            inputStreamReader.close();
        } catch (IOException e) {
            m_logger.severe("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            m_logger.severe("Cannot open the requested file: " + e);
        }
        return levels;
    }

    /**
     * This method checks whether the game is completed.
     * @return {@code boolean gameComplete} which is true
     * when game is completed, false otherwise.
     */
    public boolean IsGameComplete() {
        return gameComplete;
    }

    /**
     * This method checks whether the current level is completed.
     * @return {@code boolean gameComplete} which is true
     * when current level is completed, false otherwise.
     */
    public boolean IsLevelComplete() {
        return levelComplete;
    }

    /**
     * This method sets the {@code levelComplete} to false.
     */
    public void SetLevelComplete() {
        levelComplete = false;
    }

    /**
     * This method creates a player to play the game music.
     * @throws LineUnavailableException when music play failed
     */
    public void CreatePlayer() throws LineUnavailableException {
        File filePath = new File("src/main/resources/music/puzzle_theme.wav");
        Media music = new Media(filePath.toURI().toString());
        player = new MediaPlayer(music);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
    }

    /**
     * This method makes the player play.
     */
    public void PlayMusic() {
        player.play();
    }

    /**
     * This method makes the player stop.
     */
    public void StopMusic() {
        player.stop();
    }

    /**
     * This method check if the music is playing.
     * @return {@code boolean} which is true when
     * the music is playing, otherwise false.
     */
    public boolean IsPlayingMusic() {
        return player.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * This method gets the next level of the current level.
     * If the current level is null, it returns the first level,
     * else if the current level is the last level, it returns null.
     * Otherwise, it returns the next level.
     * @return next level if it has, otherwise {@code null}.
     */
    public Level GetNextLevel() {
        if (currentLevel == null) {
            return levels.get(0);
        }

        int currentLevelIndex = currentLevel.getIndex();
        if (currentLevelIndex < (levels.size() - 1)) {
            newCrate.clear();
            movedCrate.clear();
            keeperLastMove.clear();
            crateLastMove.clear();
            return levels.get(currentLevelIndex + 1);
        }

        gameComplete = true;
        return null;
    }

    /**
     * This methods gets the current level.
     * @return {@code Level currentLevel}.
     */
    public Level GetCurrentLevel() {
        return currentLevel;
    }

    /**
     * This method gets the index of the current level.
     * When the current level is completed, it returns
     * current level index - 1 since the current level
     * is actually next level now.
     * If the saved game is loaded, it returns the saved level index.
     * @return the index of the current level.
     */
    public int GetLevelIndex() {
        if (!isLevelLoad) {
            if (currentLevel == null) {
                return 0;
            } else if (levelComplete){
                return (currentLevel.getIndex() - 1);
            } else {
                return currentLevel.getIndex();
            }
        } else {
            if (levelComplete){
                return (levelNumber - 1);
            } else {
                return levelNumber;
            }
        }
    }

    /**
     * This method set the debug state to the opposite state.
     */
    public void ToggleDebug() {
        m_debug = !m_debug;
    }

    /**
     * This method get the current level state and convert it to String.
     * @return {@code String} representing levels.
     */
    public String SaveGame() {
        StringBuilder gameBuilder = new StringBuilder();
        for (int lev = currentLevel.getIndex(); lev < levels.size(); lev++) {
            gameBuilder.append("LevelName: ").append(levelNames.get(lev)).append("\n");
            gameBuilder.append(levels.get(lev).SaveLevel()).append("\n");
        }
        return gameBuilder.toString();
    }

    /**
     * This method is used to undo the step.
     * It moves the keeper and the crate to the previous point.
     * If undo failed, it returns 0 then the prompt will be displayed by {@code SubModel.Undo}.
     * @return {@code int} representing undo success or not.
     */
    public int Undo() {
        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.getObjectAt(keeperPosition);
        try {
            m_keeper = 3;
            if (!keeperLastMove.isEmpty()) {
                Point keeperAntiDelta = keeperLastMove.get(keeperLastMove.size() - 1);
                Point keeperDelta = antiPoint(keeperAntiDelta);
                Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, keeperDelta);
                GameObject keeperTarget = currentLevel.getObjectAt(targetObjectPoint);
                currentLevel.moveGameObjectBy(keeper, keeperPosition, keeperAntiDelta);
                keeperPosition.translate((int) keeperAntiDelta.getX(), (int) keeperAntiDelta.getY());
                if (keeperTarget == GameObject.CRATE) {
                    Point crateAntiDelta = crateLastMove.get(crateLastMove.size() - 1);
                    Point nextPoint = GameGrid.translatePoint(targetObjectPoint, crateAntiDelta);
                    movedCrate.set(movedCrate.indexOf(targetObjectPoint), nextPoint);
                    currentLevel.moveGameObjectBy(keeperTarget, targetObjectPoint, crateAntiDelta);
                    targetObjectPoint.translate((int) crateAntiDelta.getX(), (int) crateAntiDelta.getY());
                }
                keeperLastMove.remove(keeperLastMove.size() - 1);
                crateLastMove.remove(crateLastMove.size() - 1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        movesCount--;
        movesCountLevel--;
        return 1;
    }

    /**
     * This method is used to reset the level.
     * It moves the keeper and crates to their initial point.
     */
    public void ResetLevel() {
        try {
            Point keeperPosition = currentLevel.getKeeperPosition();
            Point keeperInitialPosition = currentLevel.getKeeperInitialPosition();
            GameObject keeper = currentLevel.getObjectAt(keeperPosition);
            currentLevel.MoveGameObjectTo(keeper, keeperPosition, keeperInitialPosition);
            currentLevel.MovePointToInitial();
            m_keeper = 3;
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Point point : movedCrate) {
            int index = movedCrate.indexOf(point);
            GameObject go = currentLevel.getObjectAt(point);
            currentLevel.MoveGameObjectTo(go, movedCrate.get(index), newCrate.get(index));
        }
        newCrate.clear();
        movedCrate.clear();
        keeperLastMove.clear();
        crateLastMove.clear();
        movesCount -= movesCountLevel;
        movesCountLevel = 0;
    }

}