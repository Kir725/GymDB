package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class MainController {
    @FXML
    private Button btnClients;

    @FXML
    private Button btnManagers;

    @FXML
    private Button btnServices;

    @FXML
    private Button btnAbonements;
    @FXML
    private AnchorPane wrapperPane;
    @FXML
    private AnchorPane abonementsPane;
    @FXML
    private AnchorPane clientsPane;
    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientsPane.fxml"));
        clientsPane = loader.load();
        abonementsPane = new AnchorPane();
        abonementsPane.getChildren().add(new Label("Абонементы"));
        btnClients.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(clientsPane);
        });
        btnAbonements.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(abonementsPane);
        });
    }
}
