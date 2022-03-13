package sample.start;

import sample.objects.GameObject;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static sample.start.GameGrid.translatePoint;

/**
 * Level saved a level using 3 different GameGrid objects
 * and provide several methods related to position.
 *
 * @author Shiqi XIN-modified
 */
public final class Level implements Iterable<GameObject> {

    private final String NAME;
    private final GameGrid objectsGrid;
    private final GameGrid diamondsGrid;
    private final GameGrid allObjectsGrid;
    private final int INDEX;
    private int numberOfDiamonds = 0;
    private Point keeperPosition = new Point(0, 0);
    private Point keeperInitialPosition = new Point(0, 0);

    /**
     * This is the only constructor of the Level class.
     * It saves a level using 3 different GameGrid objects.
     * It saves a objectsGrid which saves everything except diamonds.
     * It saves a diamondsGrid which only saves diamonds.
     * It also saves a allObjectsGrid which is same as objectsGrid for further use.
     * @param levelName {@code String} representing the level name.
     * @param levelIndex {@code int} representing the level index.
     * @param raw_level {@code List<String>} contains String of {@code GameObject}.
     */
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        if (StartMeUp.IsDebugActive()) {
            System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);
        }

        NAME = levelName;
        INDEX = levelIndex;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        objectsGrid = new GameGrid(rows, columns);
        diamondsGrid = new GameGrid(rows, columns);
        allObjectsGrid = new GameGrid(rows, columns);

        for (int row = 0; row < raw_level.size(); row++) {

            // Loop over the string one char at a time because it should be the fastest way:
            // http://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
            for (int col = 0; col < raw_level.get(row).length(); col++) {
                // The game object is null when the we're adding a floor or a diamond
                GameObject curTile = GameObject.FromChar(raw_level.get(row).charAt(col));

                if (curTile == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.PutGameObjectAt(curTile, row, col);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.KEEPER) {
                    keeperPosition = new Point(row, col);
                    keeperInitialPosition = new Point(row, col);
                } else if (curTile == GameObject.CRATE_ON_DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.PutGameObjectAt(GameObject.DIAMOND, row, col);
                    curTile = GameObject.CRATE;
                } else if (curTile == GameObject.KEEPER_ON_DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.PutGameObjectAt(GameObject.DIAMOND, row, col);
                    keeperPosition = new Point(row, col);
                    keeperInitialPosition = new Point(row, col);
                    curTile = GameObject.KEEPER;
                } else {
                    // Do Nothing
                }

                objectsGrid.PutGameObjectAt(curTile, row, col);
                allObjectsGrid.PutGameObjectAt(curTile, row, col);
                curTile = null;
            }
        }
    }

    /**
     * This method splices objectsGrid and diamondGrid together
     * and converts them into a string to return.
     * @return {@code String} converted from the allObjectsGrid
     */
    public String SaveLevel() {
        for (int row = 0; row < allObjectsGrid.ROWS; row++) {
            for (int col = 0; col < allObjectsGrid.COLUMNS; col++) {
                if (diamondsGrid.GetGameObjectAt(col, row) == GameObject.DIAMOND) {
                    if (allObjectsGrid.GetGameObjectAt(col, row) == GameObject.CRATE) {
                        allObjectsGrid.PutGameObjectAt(GameObject.CRATE_ON_DIAMOND, col, row);
                    } else if (allObjectsGrid.GetGameObjectAt(col, row) == GameObject.KEEPER) {
                        allObjectsGrid.PutGameObjectAt(GameObject.KEEPER_ON_DIAMOND, col, row);
                    } else {
                        allObjectsGrid.PutGameObjectAt(GameObject.DIAMOND, col, row);
                    }
                }
            }
        }
        return allObjectsGrid.toString();
    }

    /**
     * This method checks whether the level is complete.
     * @return {@code boolean} which is true if it is completed, otherwise false.
     */
    boolean isComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                if (objectsGrid.GetGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.GetGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }

        return cratedDiamondsCount >= numberOfDiamonds;
    }

    /**
     * This method get the current level index.
     * @return {@code int} level index.
     */
    int getIndex() {
        return INDEX;
    }

    /**
     * This method get the current keeper position.
     * @return {@code int} keeper position.
     */
    Point getKeeperPosition() {
        return keeperPosition;
    }

    /**
     * This method get the initial keeper position.
     * @return {@code int} initial keeper position.
     */
    Point getKeeperInitialPosition() {
        return keeperInitialPosition;
    }

    /**
     * This method get the object at the target point.
     * @param source source {@code point}.
     * @param delta delta {@code point}.
     * @return {@code GameObject} at the target point.
     */
    GameObject getTargetObject(Point source, Point delta) {
        return objectsGrid.getTargetFromSource(source, delta);
    }

    /**
     * This method get the object at a certain point.
     * @param p {@code Point p}.
     * @return {@code GameObject} at {@code Point p}.
     */
    GameObject getObjectAt(Point p) {
        return objectsGrid.GetGameObjectAt(p);
    }

    /**
     * This method moves the {@code GameObject} from source point by delta point.
     * @param object {@code GameObject} to be moved.
     * @param source source point.
     * @param delta delta point.
     */
    void moveGameObjectBy(GameObject object, Point source, Point delta) {
        MoveGameObjectTo(object, source, translatePoint(source, delta));
    }

    /**
     * This method moves the {@code GameObject} from source point to destination point
     * in both allObjectsGrid and objectsGrid.
     * @param object {@code GameObject} to be moved.
     * @param source source point.
     * @param destination destination point.
     */
    public void MoveGameObjectTo(GameObject object, Point source, Point destination) {
        allObjectsGrid.PutGameObjectAt(getObjectAt(destination), source);
        allObjectsGrid.PutGameObjectAt(object, destination);
        objectsGrid.PutGameObjectAt(getObjectAt(destination), source);
        objectsGrid.PutGameObjectAt(object, destination);
    }

    /**
     * This method moves the keeper to it's initial position.
     */
    public void MovePointToInitial() {
        keeperPosition = new Point((int)keeperInitialPosition.getX(), (int)keeperInitialPosition.getY());
    }

    /**
     * Print {@code objectsGrid} in the current level as String.
     * @return {@code String} converted from {@code objectsGrid}.
     */
    @Override
    public String toString() {
        return objectsGrid.toString();
    }

    /**
     * this method obtains a new iterator.
     * @return {@code Iterator<GameObject>}
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }

    /**
     * This is an inner class to generate new iterator.
     */
    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        /**
         * This method checks whether it has next object in this level.
         * @return {@code true} if it has next object, else {@code false}.
         */
        @Override
        public boolean hasNext() {
            return !(row == objectsGrid.ROWS - 1 && column == objectsGrid.COLUMNS);
        }

        /**
         * This method gets the next {@code GameObject} in the Grids.
         * It returns {@code GameObject.CRATE_ON_DIAMOND} when crate and diamond
         * are on the same point of objectGrid and diamondGrid respectively,
         * {@code GameObject.DIAMOND} when floor and diamond
         * are on the same point of objectGrid and diamondGrid respectively,
         * else return {@code objectGrid}.
         * @return {@code GameObject}.
         */
        @Override
        public GameObject next() {
            if (column >= objectsGrid.COLUMNS) {
                column = 0;
                row++;
            }

            GameObject object = objectsGrid.GetGameObjectAt(column, row);
            GameObject diamond = diamondsGrid.GetGameObjectAt(column, row);
            GameObject retObj = object;

            column++;

            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                } else {
                    retObj = object;
                }
            }

            return retObj;
        }

        /**
         * This method gets the current position.
         * @return Point at current position.
         */
        public Point GetCurrentPosition() {
            return new Point(column, row);
        }
    }
}