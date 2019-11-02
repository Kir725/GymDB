package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbmanagers.ClientManager;
import sample.entity.Client;

public class AddClientController {

    private ClientManager clientManager = new ClientManager();

    private boolean save = false;

    @FXML
    private TextField tfSecondName;

    @FXML
    private TextField tfAbonementsType;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPatronymic;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfEmail;

    @FXML
    private DatePicker dpBornDate;

    @FXML
    private ChoiceBox<String> chbSex;

    @FXML
    private Button btnСancel;

    @FXML
    private Button btnSave;

    @FXML
    void initialize(){
        btnСancel.setOnAction(event->{
            Stage stage = (Stage) btnСancel.getScene().getWindow();
            stage.close();
        });
        btnSave.setOnAction(event->{
            save = true;
            /*Client client = new Client(tfName.getText(),tfSecondName.getText(),tfPatronymic.getText(),
                    tfEmail.getText(),tfPhone.getText(),chbSex.getValue(),dpBornDate.getValue(),tfAbonementsType.getText());
            clientManager.addClient(client);*/
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });
    }
    public boolean isSave(){
        return save;
    }
    public Client getClient(){
        Client client = new Client(tfName.getText(),tfSecondName.getText(),tfPatronymic.getText(),
                tfEmail.getText(),tfPhone.getText(),chbSex.getValue(),dpBornDate.getValue(),tfAbonementsType.getText());
        return client;
    }
    public void initData(Client client) {
        tfName.setText(client.getFirstname());
        tfSecondName.setText(client.getSecondname());
        tfPatronymic.setText(client.getPatronymic());
        tfEmail.setText(client.getEmail());
        tfPhone.setText(client.getPhone());
        chbSex.setValue(client.getSex());
        dpBornDate.setValue(client.getBornDate());
        tfAbonementsType.setText(client.getAbonementType());
    }


}
