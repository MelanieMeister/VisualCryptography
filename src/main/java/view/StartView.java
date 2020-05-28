package view;

import com.sun.glass.ui.Size;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import javafx.scene.layout.BorderPane;
import model.PresentationModel;

public class StartView extends VBox implements ViewMixin {
    private final PresentationModel model;

    private Label titleLabel;

    private TextField inputField;
    private Button startButton;


    public StartView(PresentationModel pm){
        this.model = pm;
        init();
    }

    @Override
    public void initializeSelf() {
        String sheet = this.getClass().getResource("/style.css").toExternalForm();
        getStylesheets().add(sheet);

        this.setPadding(new Insets(15));
        this.getStyleClass().add("root");
    }

    @Override
    public void initializeParts() {
        inputField = new TextField();
        inputField.setId("input");
        startButton = GuiHelper.getButton("Let's encrypt");
        titleLabel = GuiHelper.getTitle("What do you want to encrypt?");
    }

    @Override
    public void layoutParts() {
        this.getChildren().addAll(titleLabel, inputField, startButton);
        setSpacing(5);

    }

    @Override
    public void setupEventHandlers() {
        startButton.setOnAction(e->{
            Stage stage = new Stage();

            ResultView newStage = new ResultView(
                    model,(Stage) getScene().getWindow());
            Scene scene = new Scene(newStage,model.getScreenWidth(),600);
            stage.setTitle("Encryption / Decryption");
            newStage.setStyle("-fx-background-color:white");

            getStyleClass().add("root");

            Stage currentStage = (Stage) getScene().getWindow();
            currentStage.close();

            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void setupValueChangedListeners() {
        startButton.setOnMouseEntered(e-> getScene().setCursor(Cursor.HAND));

    }

    @Override
    public void setupBindings() {
        inputField.textProperty().bindBidirectional(model.plainTextProperty());
    }
}

