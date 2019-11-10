package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class MainController {
    @FXML
    private Button btnClients;

    @FXML
    private Button btnEmplyoees;

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
    private AnchorPane employeesPane;
    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        clientsPane = FXMLLoader.load(getClass().getResource("clientsPane.fxml"));
        employeesPane = FXMLLoader.load(getClass().getResource("employeesPane.fxml"));
        abonementsPane = new AnchorPane();
        abonementsPane.getChildren().add(new Label("Абонементы"));
        btnClients.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(clientsPane);
        });
        btnEmplyoees.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(employeesPane);
        });
        btnAbonements.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(abonementsPane);
        });
    }
}
