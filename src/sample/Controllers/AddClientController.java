package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbmanagers.ChoiBoxManager;
import sample.dbmanagers.ClientManager;
import sample.entity.Client;

import java.time.LocalDate;

public class AddClientController {

    private boolean save = false;
    ChoiBoxManager choiBoxManager = new ChoiBoxManager();

    @FXML
    private TextField tfSecondName;

    @FXML
    private ChoiceBox<String> choibxAbType;

    @FXML
    private TextField tfBornDate;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPatronymic;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfEmail;

    @FXML
    private ChoiceBox<String> chbSex;

    @FXML
    private Button btnСancel;

    @FXML
    private Button btnSave;

    @FXML
    void initialize(){
        choibxAbType.getItems().addAll(choiBoxManager.findAbTypes());
        btnСancel.setOnAction(event->{
            Stage stage = (Stage) btnСancel.getScene().getWindow();
            stage.close();
        });
        btnSave.setOnAction(event->{
            save = true;
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });
    }
    boolean isSave(){
        return save;
    }
    Client getClient(){
        return new Client(tfName.getText(),tfSecondName.getText(),tfPatronymic.getText(),
                tfEmail.getText(),tfPhone.getText(),chbSex.getValue(),LocalDate.parse(tfBornDate.getText()),choibxAbType.getValue());
    }
    void initData(Client client) {
        tfName.setText(client.getFirstname());
        tfSecondName.setText(client.getSecondname());
        tfPatronymic.setText(client.getPatronymic());
        tfEmail.setText(client.getEmail());
        tfPhone.setText(client.getPhone());
        chbSex.setValue(client.getSex());
        tfBornDate.setText(client.getBornDate().toString());
        choibxAbType.setValue(client.getAbonementType());
    }

}
