package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ClientsPaneController {

    @FXML
    private AnchorPane clientsPane;

    @FXML
    private Button btnAddClient;

    @FXML
    private Button btnFindClient;

    @FXML
    private Button btnClear;

    @FXML
    void initialize() throws IOException {
        btnAddClient.setOnAction(event ->{

        });
    }

}
