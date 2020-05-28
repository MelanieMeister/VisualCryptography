import control.Controller;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PresentationModel;
import view.StartView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PresentationModel model = new PresentationModel();

        Parent rootPanel = new StartView( model);

        Scene scene = new Scene(rootPanel,500,150);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Visual Cryptography");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
