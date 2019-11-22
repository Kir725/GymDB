package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dbmanagers.ChoiBoxManager;
import sample.entity.Calendar;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddCalendarNotePanelController {
    private boolean save = false;
    private ChoiBoxManager choiBoxManager = new ChoiBoxManager();
    @FXML
    private TextField tfStartTime;
    @FXML
    private TextField tfDate;
    @FXML
    private Button btnСancel;
    @FXML
    private Button btnSave;
    @FXML
    private ChoiceBox<String> choibxServrce;

    @FXML
    private ChoiceBox<String> choibxPlace;

    @FXML
    private ChoiceBox<String> choibxTrainer;


    @FXML
    void initialize(){
        choibxServrce.getItems().addAll(choiBoxManager.findServices());
        choibxPlace.getItems().addAll(choiBoxManager.findPlaces());
        choibxTrainer.getItems().addAll(choiBoxManager.findTrainers());
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
        return new Calendar(LocalDate.parse(tfDate.getText()), LocalTime.parse(tfStartTime.getText()),choibxServrce.getValue(),choibxTrainer.getValue(),choibxPlace.getValue());
    }
    void initData(Calendar patient) {
        choibxServrce.setValue(patient.getServiceName());
        choibxTrainer.setValue(patient.getEmployeeName());
        choibxPlace.setValue(patient.getPlacementName());
        tfStartTime.setText(patient.getStartTime().toString());
        tfDate.setText(patient.getDate().toString());
    }

}
