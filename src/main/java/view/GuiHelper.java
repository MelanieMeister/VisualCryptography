package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class GuiHelper {
    public static ImageView getImageView(){
        ImageView b = new ImageView();
         GridPane.setHgrow(b, Priority.ALWAYS);
        GridPane.setHalignment(b, HPos.CENTER);
         return b;
    }

    public static Button getButton(String txt){
        Button b = new Button(txt);
        b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(b, Priority.ALWAYS);
        GridPane.setHalignment(b, HPos.CENTER);
        b.getStyleClass().add("button");

        b.setPadding(new Insets(10));
        return b;
    }


    public static Label getTitle(String title){
        Label l = new Label(title);
        l.setAlignment(Pos.CENTER);
        l.getStyleClass().add("title");
        l.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(l, Priority.ALWAYS);
        GridPane.setHalignment(l, HPos.CENTER);
        return l;
    }

    public static VBox getVBox(){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(vBox, Priority.ALWAYS);
        GridPane.setHalignment(vBox, HPos.CENTER);
        return vBox;
    }
}
