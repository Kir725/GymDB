package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.entity.Employee;

public class AddEmployeeController {
    private boolean save = false;

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
    private ChoiceBox<String> chbSex;

    @FXML
    private TextField tfSolary;

    @FXML
    private ChoiceBox<String> choibxPosition;

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
    Employee getEmployee(){
        return new Employee(tfName.getText(),tfSecondName.getText(),tfPatronymic.getText(),
                tfEmail.getText(),tfPhone.getText(),chbSex.getValue(),choibxPosition.getValue(),Float.parseFloat(tfSolary.getText()));
    }
    void initData(Employee employee) {
        tfName.setText(employee.getFirstname());
        tfSecondName.setText(employee.getSecondname());
        tfPatronymic.setText(employee.getPatronymic());
        tfEmail.setText(employee.getEmail());
        tfPhone.setText(employee.getPhone());
        chbSex.setValue(employee.getSex());
        choibxPosition.setValue(employee.getPosition());
        tfSolary.setText(employee.getSalary().toString());
    }
}
