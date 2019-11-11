package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entity.Abonement;

public class AddAbonementController {
    private boolean save = false;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfAbonementsType;

    @FXML
    private ChoiceBox<String> choibxVisiting_hours;

    @FXML
    private ChoiceBox<String> choibxValidity;

    @FXML
    private ChoiceBox<String> choibxFreezing_time;

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
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });
    }
    boolean isSave(){
        return save;
    }
    Abonement getAbonement(){
        return new Abonement(tfAbonementsType.getText(),Integer.parseInt(tfPrice.getText()),choibxVisiting_hours.getValue(),
                choibxValidity.getValue(),choibxFreezing_time.getValue());
    }
    void initData(Abonement abonement) {
        tfAbonementsType.setText(abonement.getTitleAbonement());
        tfPrice.setText(abonement.getPrice().toString());
        choibxVisiting_hours.setValue(abonement.getVisiting_hours());
        choibxValidity.setValue(abonement.getValidity());
        choibxFreezing_time.setValue(abonement.getFreezing_time());

    }

}
