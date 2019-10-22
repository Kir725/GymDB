package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddClientController {

    @FXML
    private TextField tfSecondName;

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
    private ChoiceBox<?> chbSex;

    @FXML
    private Button btnСancel;

    @FXML
    private Button btnSave;

}
