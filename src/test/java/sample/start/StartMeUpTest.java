package sample.start;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class StartMeUpTest {

    private static boolean production;
    private static InputStream input;

    static void initAll() {
        production = false;
        try {
            input = new FileInputStream(new File("src/main/resources/SampleGame.skb"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetMapSetName() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertEquals("Example Game!", startMeUp.GetMapSetName());
    }

    @Test
    void testIsDebugActive() {
        initAll();
        assertFalse(StartMeUp.IsDebugActive());
    }

    @Test
    void testResetMovesCountLevel() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertEquals(0, startMeUp.GetMovesCountLevel());
    }

    @Test
    void testHandleKey() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        startMeUp.HandleKey(KeyCode.UP);
        assertEquals(1, StartMeUp.GetKeeper());
    }

    @Test
    void testIsGameComplete() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertFalse(startMeUp.IsGameComplete());
    }

    @Test
    void testIsLevelComplete() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertFalse(startMeUp.IsLevelComplete());
    }

    @Test
    void testGetLevelIndex() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertEquals(0, startMeUp.GetLevelIndex());
    }

    @Test
    void testToggleDebug() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        startMeUp.ToggleDebug();
        assertTrue(StartMeUp.IsDebugActive());
    }

    @Test
    void testSaveGame() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertTrue(startMeUp.SaveGame().contains("LevelName: "));
    }

    @Test
    void testUndo() {
        initAll();
        StartMeUp startMeUp = new StartMeUp(input, production);
        assertEquals(0, startMeUp.Undo());
    }

}
