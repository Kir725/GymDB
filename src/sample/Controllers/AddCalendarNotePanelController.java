package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entity.Calendar;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddCalendarNotePanelController {
    private boolean save = false;

    @FXML
    private TextField tfEmployee;

    @FXML
    private TextField tfPlacement;

    @FXML
    private TextField tfStartTime;

    @FXML
    private TextField tfService;

    @FXML
    private TextField tfDate;

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
    Calendar getItem(){
        return new Calendar(LocalDate.parse(tfDate.getText()), LocalTime.parse(tfStartTime.getText()),tfService.getText(),tfEmployee.getText(),tfPlacement.getText());
    }
    void initData(Calendar patient) {
        tfService.setText(patient.getServiceName());
        tfEmployee.setText(patient.getEmployeeName());
        tfPlacement.setText(patient.getPlacementName());
        tfStartTime.setText(patient.getStartTime().toString());
        tfDate.setText(patient.getDate().toString());
    }

}
