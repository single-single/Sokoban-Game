package sample.start;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sample.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LevelTest {

    private static String levelName;
    private static int levelIndex;
    private static List<String> raw_level;

    @BeforeAll
    static void initAll() {
        levelName = "level name";
        levelIndex = 1;
        raw_level = new ArrayList<String>(4);
        raw_level.add(0, "WWWW\n");
        raw_level.add(1, "WCDW\n");
        raw_level.add(2, "W SW\n");
        raw_level.add(3, "WWWW\n");
    }

    @Test
    void testSaveLevel() {
        Level level = new Level(levelName, levelIndex, raw_level);
        String result = "WWWW\nWCDW\nW SW\nWWWW\n";
        assertEquals(result, level.SaveLevel());
    }

    @Test
    void testGetIndex() {
        Level level = new Level(levelName, levelIndex, raw_level);
        assertEquals(levelIndex, level.getIndex());
    }

    @Test
    void testGetKeeperPosition() {
        Level level = new Level(levelName, levelIndex, raw_level);
        assertEquals(new Point(2, 2), level.getKeeperPosition());
    }

    @Test
    void testGetKeeperInitialPosition() {
        Level level = new Level(levelName, levelIndex, raw_level);
        level.moveGameObjectBy(GameObject.KEEPER, new Point(2, 2), new Point(-1, 0));
        assertEquals(new Point(2, 2), level.getKeeperInitialPosition());
    }

    @Test
    void testGetTargetObject() {
        Level level = new Level(levelName, levelIndex, raw_level);
        GameObject result = level.getTargetObject(new Point(2, 2), new Point(-1, 0));
        assertEquals(GameObject.FLOOR, result);
    }

    @Test
    void testGetObjectAt() {
        Level level = new Level(levelName, levelIndex, raw_level);
        assertEquals(GameObject.KEEPER, level.getObjectAt(new Point(2, 2)));
    }

    @Test
    void testMoveGameObjectBy() {
        Level level = new Level(levelName, levelIndex, raw_level);
        level.moveGameObjectBy(GameObject.KEEPER, new Point(2, 2), new Point(-1, 0));
        assertEquals(GameObject.KEEPER, level.getObjectAt(new Point(1, 2)));
    }

    @Test
    void testMoveGameObjectTo() {
        Level level = new Level(levelName, levelIndex, raw_level);
        level.MoveGameObjectTo(GameObject.KEEPER, new Point(2, 2), new Point(1, 2));
        assertEquals(GameObject.FLOOR, level.getObjectAt(new Point(2, 2)));
        assertEquals(GameObject.KEEPER, level.getObjectAt(new Point(1, 2)));
    }

    @Test
    void testMovePointToInitial() {
        Level level = new Level(levelName, levelIndex, raw_level);
        level.MoveGameObjectTo(GameObject.KEEPER, new Point(2, 2), new Point(1, 2));
        level.MovePointToInitial();
        assertEquals(new Point(2, 2), level.getKeeperPosition());
    }

    @Test
    void testToString() {
        Level level = new Level(levelName, levelIndex, raw_level);
        String result = "WWWW\nWC W\nW SW\nWWWW\n";
        assertEquals(result, level.toString());
    }

}
