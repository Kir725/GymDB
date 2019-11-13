package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.config.GlobalConfig;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            GlobalConfig.initGlobalConfig();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return;
        }
        Parent root = FXMLLoader.<Parent>load(getClass().getResource("/sample/MainPage.fxml"));
        primaryStage.setTitle("GymDB");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
