package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import org.kordamp.ikonli.javafx.FontIcon;
import sample.dbmanagers.ChoiBoxManager;
import sample.dbmanagers.EmployeeManager;
import sample.entity.Employee;
import sample.instruments.SearchManager;


public class EmployeesPaneController {
    private EmployeeManager employeeManager = new EmployeeManager();
    private SearchManager searchManager = new SearchManager();
    ChoiBoxManager choiBoxManager = new ChoiBoxManager();

    @FXML
    private Button btnAddEmployee;

    @FXML
    private TableView<Employee> tableEmployees;

    @FXML
    private TableColumn<Employee, Integer> colNum;

    @FXML
    private TableColumn<Employee, String> colName;

    @FXML
    private TableColumn<Employee, String> colPosition;

    @FXML
    private TableColumn<Employee, String> colEmail;

    @FXML
    private TableColumn<Employee, String> colPhone;

    @FXML
    private TableColumn<Employee, Float> colSalary;

    @FXML
    private TableColumn<Employee, Void> colActions;

    @FXML
    private Button btnSearchEmployees;

    @FXML
    private Button btnClear;

    @FXML
    private TextField tfNameSearch;

    @FXML
    private ChoiceBox<String> chbSexSearch;

    @FXML
    private TextField tfPhoneSearch;

    @FXML
    private ChoiceBox<String> choibxPositionSearch;

    @FXML
    void initialize(){
        choibxPositionSearch.getItems().addAll(choiBoxManager.findEmployeePositions());
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableEmployees.getItems().indexOf(param.getValue())+1));
        colName.setCellValueFactory(new PropertyValueFactory<Employee, String>("FullName"));
        colPosition.setCellValueFactory(new PropertyValueFactory<Employee, String>("Position"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Employee, String>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Employee, String>("Phone"));
        colSalary.setCellValueFactory(new PropertyValueFactory<Employee, Float>("Salary"));
        colActions.setCellFactory(param -> new TableCell<Employee, Void>() {
            private FontIcon iconUpdate = new FontIcon("fa-pencil");
            private FontIcon iconDelete = new FontIcon("fth-cross");
            {
                iconUpdate.setCursor(Cursor.HAND);
                iconUpdate.setIconColor(Paint.valueOf("#5fa6e6"));
                iconUpdate.setIconSize(25);
                iconDelete.setCursor(Cursor.HAND);
                iconDelete.setIconColor(Paint.valueOf("#eb2e34"));
                iconDelete.setIconSize(25);
            }
            private final HBox pane = new HBox(5,iconUpdate, iconDelete);
            {
                pane.setPadding(new Insets(0,0, 0,20));
            }

            {
                iconUpdate.setOnMouseClicked(event -> {
                    Employee patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addEmployeePanel.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        AddEmployeeController controller = fxmlLoader.getController();
                        controller.initData(patient);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Редактировать данные сотрудника");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        stage.setOnHidden(windowEvent ->{
                            if(controller.isSave())
                            {
                                employeeManager.updateEmployee(controller.getEmployee(),patient.getEmployeeId());
                            }
                            loadTable(employeeManager.findEmployees());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                iconDelete.setOnMouseClicked(event -> {
                    Employee patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить сотрудника?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        employeeManager.deleteEmployee(patient.getEmployeeId());
                    }
                    loadTable(employeeManager.findEmployees());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        loadTable(employeeManager.findEmployees());

        btnAddEmployee.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addEmployeePanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                AddEmployeeController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить сотрудника");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setOnHidden(windowEvent ->{
                    if(controller.isSave())
                    {
                        employeeManager.addEmployee(controller.getEmployee());
                    }
                    loadTable(employeeManager.findEmployees());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnClear.setOnAction(event->{
            tfNameSearch.setText("");
            tfPhoneSearch.setText("");
            choibxPositionSearch.setValue("");
            loadTable(employeeManager.findEmployees());
        });
        btnSearchEmployees.setOnAction(event->{
            searchManager.searchEmployees(tfNameSearch.getText(),tfPhoneSearch.getText(),chbSexSearch.getValue(),choibxPositionSearch.getValue(),
                    tableEmployees,employeeManager.findEmployees());
        });

    }
        private void loadTable(ObservableList<Employee> list){
            tableEmployees.setItems(list);
        }
}