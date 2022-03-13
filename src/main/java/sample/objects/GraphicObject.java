package sample.objects;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.mvc.SubModel;
import sample.start.StartMeUp;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 * GraphicObject class fill each grid with pictures.
 * It also contains methods to change the wall color and keeper pictures.
 *
 * @author Shiqi XIN-modified
 */
public class GraphicObject extends Rectangle {

    private final int DURATION = 1000;
    private final double FROMVALUE = 1.0;
    private final double TOVALUE = 0.2;
    private final int IMGHEIGHT = 35;
    private final int IMGWIDTH = 35;
    private final int HEIGHT = 30;
    private final int WIDTH = 30;
    private final double STROKEWIDTH = 0.25;

    /**
     * This is the only constructor of the GraphicObject class.
     * It judges the input object and fills it with the corresponding picture.
     * @param obj {@code GameObject obj} to be filled with picture.
     */
    public GraphicObject(GameObject obj) {
        Image image;
        InputStream inputStream = null;
        try {
            switch (obj) {
                case WALL:
                    if (setWallColour() == null) {
                        inputStream = new FileInputStream(new File("src/main/resources/sprites/wall/wall.png"));
                    } else {
                        inputStream = setWallColour();
                    }
                    break;

                case CRATE:
                    inputStream = new FileInputStream(new File("src/main/resources/sprites/crate/crate.png"));
                    break;

                case DIAMOND:
                    inputStream = new FileInputStream(new File("src/main/resources/sprites/diamond/diamond.png"));

                    // TODO: fix memory leak.
                    if (StartMeUp.IsDebugActive()) {
                        FadeTransition ft = new FadeTransition(Duration.millis(DURATION), this);
                        ft.setFromValue(FROMVALUE);
                        ft.setToValue(TOVALUE);
                        ft.setCycleCount(Timeline.INDEFINITE);
                        ft.setAutoReverse(true);
                        ft.play();
                    }

                    break;

                case KEEPER:
                    if (setKeeper() == null) {
                        inputStream = new FileInputStream(new File("src/main/resources/sprites/keeper/keeper.png"));
                    } else {
                        inputStream = setKeeper();
                    }
                    break;

                case FLOOR:
                    inputStream = new FileInputStream(new File("src/main/resources/sprites/floor/floor.png"));
                    break;

                case CRATE_ON_DIAMOND:
                    inputStream = new FileInputStream(new File("src/main/resources/sprites/cod/cod.png"));
                    break;

                default:
                    String message = "Error in Level constructor. Object not recognized.";
                    StartMeUp.m_logger.severe(message);
                    throw new AssertionError(message);
            }

            assert inputStream != null;
            image = new Image(inputStream, IMGWIDTH, IMGHEIGHT, false, true);
            ImagePattern imagePattern = new ImagePattern(image);

            this.setFill(imagePattern);
            this.setHeight(HEIGHT);
            this.setWidth(WIDTH);

        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StartMeUp.IsDebugActive()) {
            this.setStroke(Color.RED);
            this.setStrokeWidth(STROKEWIDTH);
        }
    }

    /**
     * This method selects the picture to be filled in WALL object.
     * It determines which picture to choose by
     * obtaining the index from {@code SubModel.GetWallColour()}.
     * @return {@code FileInputStream} inputStream.
     */
    private FileInputStream setWallColour() {
        FileInputStream wallColour = null;
        try {
            switch (SubModel.GetWallColour()) {
                case 0:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall.png"));
                    break;

                case 1:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_blue.png"));
                    break;

                case 2:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_brown.png"));
                    break;

                case 3:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_gray.png"));
                    break;

                case 4:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_green.png"));
                    break;

                case 5:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_red.png"));
                    break;

                case 6:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_orange.png"));
                    break;

                case 7:
                    wallColour = new FileInputStream(new File("src/main/resources/sprites/wall/wall_yellow.png"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wallColour;
    }

    /**
     * This method selects the picture to be filled in KEEPER object.
     * It determines which picture to choose by
     * obtaining the index from {@code StartMeUp.GetKeeper()}.
     * @return {@code FileInputStream} inputStream.
     */
    private FileInputStream setKeeper() {
        FileInputStream keeper = null;
        try {
            switch (StartMeUp.GetKeeper()) {
                case 1:
                    keeper = new FileInputStream(new File("src/main/resources/sprites/keeper/keeper_up.png"));
                    break;

                case 2:
                    keeper = new FileInputStream(new File("src/main/resources/sprites/keeper/keeper_right.png"));
                    break;

                case 3:
                    keeper = new FileInputStream(new File("src/main/resources/sprites/keeper/keeper.png"));
                    break;

                case 4:
                    keeper = new FileInputStream(new File("src/main/resources/sprites/keeper/keeper_left.png"));
                    break;

                default:
                    keeper = new FileInputStream(new File("src/main/resources/sprites/keeper/keeper.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keeper;
    }

}