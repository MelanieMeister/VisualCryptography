package view;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import model.PresentationModel;

public class ImageViewPane extends BorderPane implements ViewMixin {
    private final static int RECT_WIDTH = 310;
    private final static int RECT_HEIGHT = 150;
    private final static int CONSOLE_WIDTH = RECT_WIDTH;
    private final static int CONSOLE_HEIGHT = 80;
    private final static int SMALL_CIRCLE_STARTX = 50;
    private final static int SMALL_CIRCLE_STARTY = 50;
    private final static int BIG_CIRCLE_STARTX = 180;
    private final static int BIG_CIRCLE_STARTY = 50;
    private final PresentationModel model;
    private ImageView[] resultImageViews;
    private Group group;
    //variables for storing initial position before drag of circle
    private double initX;
    private double initY;
    private Point2D dragAnchor;

    public ImageViewPane(PresentationModel model, ImageView[] resultImageViews) {
        this.model = model;
        this.resultImageViews = resultImageViews;
        init();
    }


    @Override
    public void initializeSelf() {
        setPrefSize(model.getScreenWidth(), 600);
    }

    @Override
    public void initializeParts() {
        group = new Group(resultImageViews[0], resultImageViews[1]);
    }

    @Override
    public void layoutParts() {
        setCenter(group);
    }

    @Override
    public void setupEventHandlers() {

        for (ImageView img : resultImageViews) {
            initDrag(img);
        }
    }

    @Override
    public void setupValueChangedListeners() {

    }

    @Override
    public void setupBindings() {

    }

    private void initDrag(final ImageView imageView) {
        //change a cursor when it is over circle
        imageView.setCursor(Cursor.HAND);
        //add a mouse listeners
        imageView.setOnMouseClicked((MouseEvent me) -> {
            //the event will be passed only to the circle which is on front
            me.consume();
        });
        imageView.setOnMouseDragged((MouseEvent me) -> {
            double dragX = me.getSceneX() - dragAnchor.getX();
            double dragY = me.getSceneY() - dragAnchor.getY();
            //calculate new position of the circle
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;

            imageView.setTranslateX(newXPosition);
            imageView.setTranslateY(newYPosition);
        });
        imageView.setOnMouseEntered((MouseEvent me) -> {
            //change the z-coordinate of the circle
            imageView.toFront();
        });

        imageView.setOnMousePressed((MouseEvent me) -> {
            //when mouse is pressed, store initial position
            initX = imageView.getTranslateX();
            initY = imageView.getTranslateY();
            dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
        });
    }
}
