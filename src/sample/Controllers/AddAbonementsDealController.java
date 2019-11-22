package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbmanagers.ChoiBoxManager;
import sample.entity.Abonement;
import sample.entity.AbonementsDeal;

public class AddAbonementsDealController {
    private boolean save = false;
    private ChoiBoxManager choiBoxManager = new ChoiBoxManager();
    @FXML
    private TextField tfClientName;

    @FXML
    private ChoiceBox<String> choibxAbonementType;

    @FXML
    private DatePicker dpDateStart;

    @FXML
    private DatePicker dpDateEnd;

    @FXML
    private Button btnСancel;

    @FXML
    private Button btnSave;
    @FXML
    void initialize(){
        choibxAbonementType.getItems().addAll(choiBoxManager.findAbTypes());
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
    AbonementsDeal getItem(){
        return new AbonementsDeal(tfClientName.getText(),choibxAbonementType.getValue(),dpDateStart.getValue(),dpDateEnd.getValue());
    }
    void initData(AbonementsDeal patient) {
        tfClientName.setText(patient.getClientName());
        choibxAbonementType.setValue(patient.getAbonementTitle());
        dpDateStart.setValue(patient.getStartDate());
        dpDateEnd.setValue(patient.getEndDate());
    }
}
