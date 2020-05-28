package view;

import com.sun.glass.ui.Size;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import javafx.scene.layout.BorderPane;
import model.PresentationModel;

public class ResultView extends VBox implements ViewMixin {
    private final PresentationModel model;
    private final Stage startView;
    private ImageView[] imageViews = new ImageView[2];
    private ImageView[] resultImageViews = new ImageView[2];
    ImageViewPane imageViewPane;
    private static final Size size = new Size(700, 120);
    private Button goBackButton;

    private Label[] titles = new Label[2];
    private VBox[] partVBoxes = new VBox[2];


    public ResultView(PresentationModel pm, Stage startView){
        this.model = pm;
        this.startView = startView;
        init();
    }

    @Override
    public void initializeSelf() {
        String sheet = this.getClass().getResource("/style.css").toExternalForm();
        getStylesheets().add(sheet);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15));
    }

    @Override
    public void initializeParts() {

        goBackButton = new Button("Go back");

        goBackButton.setPrefWidth(model.getScreenWidth()-50);
        BufferedImage[] bufferedImages = model.generate(size);

        for(int i=0; i<2; i++){
            partVBoxes[i] = GuiHelper.getVBox();
            imageViews[i] = GuiHelper.getImageView();
            imageViews[i].setImage(model.convertToFxImage(bufferedImages[i]));

            resultImageViews[i] = GuiHelper.getImageView();
            resultImageViews[i].setImage(model.convertToFxImage(bufferedImages[i]));
        }
        titles[0] = GuiHelper.getTitle("Encryption");
        titles[1] = GuiHelper.getTitle("Decryption");

        titles[1].setPadding(new Insets(30,0,0,0));
        imageViewPane = new ImageViewPane(model,resultImageViews);
    }

    @Override
    public void layoutParts() {
        partVBoxes[0].getChildren().addAll(titles[0], imageViews[0], imageViews[1]);
        partVBoxes[1].getChildren().addAll(titles[1], imageViewPane);

        for(int i=0; i<2; i++){
            this.getChildren().add(partVBoxes[i]);
        }

        this.getChildren().add(goBackButton);
    }

    @Override
    public void setupEventHandlers() {

    }

    @Override
    public void setupValueChangedListeners() {
        goBackButton.setOnAction(e->{
            Stage currentStage = (Stage) getScene().getWindow();
            currentStage.close();
            startView.show();
        });
    }

    @Override
    public void setupBindings() {

    }



}

