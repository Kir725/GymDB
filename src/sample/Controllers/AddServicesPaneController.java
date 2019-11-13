package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entity.Service;

public class AddServicesPaneController {
    private boolean save = false;
    @FXML
    private TextField tfValidity;

    @FXML
    private TextField tfServiceName;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextArea txarDescription;

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
    Service getItem(){
        return new Service(tfServiceName.getText(),Integer.parseInt(tfValidity.getText()),Float.parseFloat(tfPrice.getText()),
                txarDescription.getText());
    }
    void initData(Service patient) {
        tfServiceName.setText(patient.getServiceName());
        tfValidity.setText(patient.getValidity().toString());
        tfPrice.setText(patient.getPrice().toString());
        txarDescription.setText(patient.getDescription());
    }


}
