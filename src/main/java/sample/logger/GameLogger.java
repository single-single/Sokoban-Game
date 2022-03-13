package sample.logger;

import sample.start.StartMeUp;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * GameLogger records log when bugs or exception happends.
 * Singleton Pattern is used since it only needs one instance by StartMeUp.
 *
 * @author Shiqi XIN-modified
 */
public class GameLogger extends Logger {

    private static Logger m_logger = Logger.getLogger("GameLogger");
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Calendar calendar = Calendar.getInstance();

    private static GameLogger instance;

    /**
     * This is the only constructor of the GameLogger class.
     * It creates a new file to save game log.
     * @throws IOException when read file failed.
     */
    private GameLogger() throws IOException {
        super("GameLogger", null);

        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        directory.mkdirs();

        FileHandler fh = new FileHandler(directory + "/" + StartMeUp.GAME_NAME + ".log");
        m_logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
     * This method gets an instance of GameLogger
     * @return instance of GameLogger
     */
    public static GameLogger GetGameLogger() {
        if (instance == null) {
            try {
                instance = new GameLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * This method creates a message with time.
     * @param message {@code String} message to be showed.
     * @return a {@code String} with calender and message.
     */
    private String createFormattedMessage(String message) {
        return dateFormat.format(calendar.getTime()) + " -- " + message;
    }

    /**
     * Log an INFO message.
     * <p>
     * If the logger is currently enabled for the INFO message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     *
     * @param message The string message (or a key in the message catalog)
     */
    public void info(String message) {
        m_logger.info(createFormattedMessage(message));
    }

    /**
     * Log a WARNING message.
     * <p>
     * If the logger is currently enabled for the WARNING message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     *
     * @param message The string message (or a key in the message catalog)
     */
    public void warning(String message) {
        m_logger.warning(createFormattedMessage(message));
    }

    /**
     * Log a SEVERE message.
     * <p>
     * If the logger is currently enabled for the SEVERE message
     * level then the given message is forwarded to all the
     * registered output Handler objects.
     *
     * @param message The string message (or a key in the message catalog)
     */
    public void severe(String message) {
        m_logger.severe(createFormattedMessage(message));
    }
    
}