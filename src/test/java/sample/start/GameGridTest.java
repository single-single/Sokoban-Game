package sample.start;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sample.objects.GameObject;

import java.awt.*;

import static org.junit.Assert.*;

public class GameGridTest {

    private static int COLUMNS;
    private static int ROWS;

    @BeforeAll
    static void initAll() {
        COLUMNS = 3;
        ROWS = 3;
    }

    @Test
    void testTranslatePoint() {
        Point result = GameGrid.translatePoint(new Point(COLUMNS-1,ROWS-1), new Point(0,-1));
        assertEquals(new Point(COLUMNS-1,ROWS-2), result);
    }

    @Test
    void testGetDimension() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        assertEquals(new Dimension(COLUMNS, ROWS), gameGrid.GetDimension());
    }

    @Test
    void testGetGameObjectAt1() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS-1, ROWS-1);
        assertEquals(GameObject.WALL, gameGrid.GetGameObjectAt(COLUMNS-1, ROWS-1));
    }

    @Test
    void testGetGameObjectAt2() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS-1, ROWS-1);
        assertEquals(GameObject.WALL, gameGrid.GetGameObjectAt(new Point(COLUMNS-1, ROWS-1)));
    }

    @Test
    void testPutGameObjectAt1() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        assertTrue(gameGrid.PutGameObjectAt(GameObject.WALL, new Point(COLUMNS-1, ROWS-1)));
    }

    @Test
    void testPutGameObjectAt2() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        assertFalse(gameGrid.PutGameObjectAt(GameObject.WALL, new Point(COLUMNS, ROWS)));
    }

    @Test
    void testPutGameObjectAt3() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        assertTrue(gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS-1, ROWS-1));
    }

    @Test
    void testPutGameObjectAt4() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        assertFalse(gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS, ROWS));
    }

    @Test
    void testToString() {
        GameGrid gameGrid = new GameGrid(COLUMNS, ROWS);
        gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS-3, ROWS-3);
        gameGrid.PutGameObjectAt(GameObject.FLOOR, COLUMNS-3, ROWS-2);
        gameGrid.PutGameObjectAt(GameObject.DIAMOND, COLUMNS-3, ROWS-1);
        gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS-2, ROWS-3);
        gameGrid.PutGameObjectAt(GameObject.FLOOR, COLUMNS-2, ROWS-2);
        gameGrid.PutGameObjectAt(GameObject.DIAMOND, COLUMNS-2, ROWS-1);
        gameGrid.PutGameObjectAt(GameObject.WALL, COLUMNS-1, ROWS-3);
        gameGrid.PutGameObjectAt(GameObject.KEEPER, COLUMNS-1, ROWS-2);
        gameGrid.PutGameObjectAt(GameObject.DIAMOND, COLUMNS-1, ROWS-1);
        String result = "W D\nW D\nWSD\n";
        assertEquals(result, gameGrid.toString());
    }

}
