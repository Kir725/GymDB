package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.dbmanagers.CalendarManager;
import sample.entity.Calendar;
import sample.instruments.SearchManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarPaneController {
    CalendarManager calendarManager = new CalendarManager();
    SearchManager searchManager = new SearchManager();

    @FXML
    private Button btnAddItem;

    @FXML
    private TableView<Calendar> tableBD;

    @FXML
    private TableColumn<Calendar, Integer> colNum;

    @FXML
    private TableColumn<Calendar, String> colService;

    @FXML
    private TableColumn<Calendar, String> colEmployee;

    @FXML
    private TableColumn<Calendar, String> colPlacement;

    @FXML
    private TableColumn<Calendar, LocalTime> colStartTime;

    @FXML
    private TableColumn<Calendar, LocalTime> colEndTime;

    @FXML
    private TableColumn<Calendar, LocalDate> colDate;

    @FXML
    private TableColumn<Calendar, Void> colActions;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private TextField tfServicesNameSearch;

    @FXML
    private TextField tfEmployeeSearch;

    @FXML
    private TextField tfPlacementSearch;

    @FXML
    void initialize(){
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableBD.getItems().indexOf(param.getValue())+1));
        colService.setCellValueFactory(new PropertyValueFactory<Calendar, String>("ServiceName"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<Calendar, String>("EmployeeName"));
        colPlacement.setCellValueFactory(new PropertyValueFactory<Calendar, String>("PlacementName"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<Calendar, LocalTime>("StartTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<Calendar, LocalTime>("EndTime"));
        colDate.setCellValueFactory(new PropertyValueFactory<Calendar, LocalDate>("Date"));
        colActions.setCellFactory(param -> new TableCell<Calendar, Void>() {
            private  Button btnUpdate = new Button("Изменить");
            private  Button btnDelete = new Button("Удалить");
            /*private ImageView imageUpdate = new ImageView(new Image("sample/sourse/pencil.png"));
            private ImageView imageDelete = new ImageView(new Image("sample/sourse/button_cancel.png"));
            {
                imageUpdate.setCursor(Cursor.HAND);
                imageDelete.setCursor(Cursor.HAND);
            }*/
            private final HBox pane = new HBox(10,btnUpdate, btnDelete);

            {
                btnUpdate.setOnMouseClicked(event -> {
                    Calendar patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/addCalendarNotePanel.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        AddCalendarNotePanelController controller = fxmlLoader.getController();
                        controller.initData(patient);
                        Stage stage = new Stage();
                        //stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Изменить данные");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        stage.setOnHidden(windowEvent ->{
                            if(controller.isSave())
                            {
                                calendarManager.updateItem(controller.getItem(),patient.getCalendar_id());
                            }
                            loadTable(calendarManager.findItems());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                btnDelete.setOnMouseClicked(event -> {
                    Calendar patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить данные?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        calendarManager.deleteItem(patient.getCalendar_id());
                    }
                    loadTable(calendarManager.findItems());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        loadTable(calendarManager.findItems());

        btnAddItem.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/addCalendarNotePanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                AddCalendarNotePanelController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                //stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить данные");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setOnHidden(windowEvent ->{
                    if(controller.isSave())
                    {
                        calendarManager.addItem(controller.getItem());
                    }
                    loadTable(calendarManager.findItems());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearch.setOnAction(event->{
            searchManager.searchCalendar(tfServicesNameSearch.getText(),tfEmployeeSearch.getText(),tfServicesNameSearch.getText(),tableBD,calendarManager.findItems());
        });
        btnClear.setOnAction(event->{
            loadTable(calendarManager.findItems());
        });
    }
    private void loadTable(ObservableList<Calendar> list){
        tableBD.setItems(list);
    }
}
