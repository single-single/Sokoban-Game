package sample.start;

import sample.objects.GameObject;

import java.awt.*;
import java.util.Iterator;

/**
 * GameGrid defines a map of gameObjects.
 * It defines several methods related to the location of gameObjects.
 *
 * @author Shiqi XIN-modified
 */
public class GameGrid implements Iterable {

    final int COLUMNS;
    final int ROWS;
    private GameObject[][] gameObjects;

    /**
     * This is the only constructor of the GameGrid class.
     * @param columns {@code int columns} of the grid.
     * @param rows {@code int rows} of the grid.
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;

        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     * This method moves the point by delta from the source location.
     * @param sourceLocation {@code Point sourceLocation}.
     * @param delta {@code Point delta} to be moved.
     * @return new point after move by delta.
     */
    static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * This method generates a new {@code Dimension} object.
     * @return {@code Dimension} object.
     */
    public Dimension GetDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * This method gets the object at the target point,
     * which the point at source point moves by point delta.
     * @param source {@code Point source}
     * @param delta {@code Point delta}
     * @return gameObjects
     */
    GameObject getTargetFromSource(Point source, Point delta) {
        return GetGameObjectAt(translatePoint(source, delta));
    }

    /**
     * This method gets the {@code GameObject} at a certain column and row.
     * @param col {@code int column}.
     * @param row {@code int row}.
     * @return {@code GameObject} at the input location.
     * @throws ArrayIndexOutOfBoundsException if the point is out of bound.
     */
    public GameObject GetGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (StartMeUp.IsDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }

        return gameObjects[col][row];
    }

    /**
     * This method gets the {@code GameObject} at a certain point.
     * @param p {@code Point p}.
     * @return {@code GameObject} at the input location.
     */
    public GameObject GetGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }

        return GetGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * This method puts the {@code GameObject} at a certain column and row.
     * @param gameObject {@code GameObject}.
     * @param x {@code int column}.
     * @param y {@code int row}.
     * @return {@code boolean} representing whether the put is success.
     */
    public boolean PutGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }

        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * This method puts the {@code GameObject} at a certain point.
     * @param gameObject {@code GameObject}.
     * @param p {@code Point} to put at.
     * @return {@code boolean} representing whether the put is success.
     */
    public boolean PutGameObjectAt(GameObject gameObject, Point p) {
        return p != null && PutGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * This method checks if the point is out of bounds.
     * @param x {@code int column}.
     * @param y {@code int row}.
     * @return {@code boolean} representing whether the input location is out of bound.
     */
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);
    }

    /**
     * Print objects in the {@code gameGrid} as String.
     * @return {@code String} converted from {@code objectsGrid}.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.GetCharSymbol());
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * this method obtains a new iterator.
     * @return {@code Iterator<GameObject>}
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new GridIterator();
    }

    /**
     * This is an inner class to generate new iterator.
     */
    public class GridIterator implements Iterator<GameObject> {
        int row = 0;
        int column = 0;

        /**
         * This method checks whether it has next object in the gameGrid.
         * @return {@code true} if it has next object, else {@code false}.
         */
        @Override
        public boolean hasNext() {
            return !(row == ROWS && column == COLUMNS);
        }

        /**
         * This method gets the next {@code GameObject} in the gameGrids.
         * @return {@code GameObject}.
         */
        @Override
        public GameObject next() {
            if (column >= COLUMNS) {
                column = 0;
                row++;
            }
            return GetGameObjectAt(column++, row);
        }
    }
}