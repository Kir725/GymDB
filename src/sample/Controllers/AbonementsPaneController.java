package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.dbmanagers.AbonementManager;
import sample.entity.Abonement;
import sample.instruments.SearchManager;

import java.io.IOException;

public class AbonementsPaneController {
    AbonementManager abonementManager = new AbonementManager();
    SearchManager searchManager = new SearchManager();
    @FXML
    private Button btnAddAbonement;

    @FXML
    private TableView<Abonement> tableAbonements;

    @FXML
    private TableColumn<Abonement, Integer> colNum;

    @FXML
    private TableColumn<Abonement, String> colType;

    @FXML
    private TableColumn<Abonement, Integer> colPrice;

    @FXML
    private TableColumn<Abonement, String> colVisiting_hours;

    @FXML
    private TableColumn<Abonement, String> colValidity;

    @FXML
    private TableColumn<Abonement, String> colFreezing_time;

    @FXML
    private TableColumn<Abonement, Void> colActions;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private TextField tfTypeAbonementSearch;

    @FXML
    void initialize(){
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableAbonements.getItems().indexOf(param.getValue())+1));
        colType.setCellValueFactory(new PropertyValueFactory<Abonement, String>("TitleAbonement"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Abonement, Integer>("Price"));
        colVisiting_hours.setCellValueFactory(new PropertyValueFactory<Abonement, String>("Visiting_hours"));
        colValidity.setCellValueFactory(new PropertyValueFactory<Abonement, String>("Validity"));
        colFreezing_time.setCellValueFactory(new PropertyValueFactory<Abonement, String>("Freezing_time"));
        colActions.setCellFactory(param -> new TableCell<Abonement, Void>() {
            /*private  Button btnUpdate = new Button("Изменить");
            private  Button btnDelete = new Button("Удалить");*/
            private ImageView imageUpdate = new ImageView(new Image("sample/sourse/pencil.png"));
            private ImageView imageDelete = new ImageView(new Image("sample/sourse/button_cancel.png"));
            {
                imageUpdate.setCursor(Cursor.HAND);
                imageDelete.setCursor(Cursor.HAND);
            }
            private final HBox pane = new HBox(10,imageUpdate, imageDelete);

            {
                imageUpdate.setOnMouseClicked(event -> {
                    Abonement patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addAbonementPanel.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        AddAbonementController controller = fxmlLoader.getController();
                        controller.initData(patient);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Изменить абонемент");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        stage.setOnHidden(windowEvent ->{
                            if(controller.isSave())
                            {
                                abonementManager.updateAbonement(controller.getAbonement(),patient.getAbonement_id());
                            }
                            loadTable(abonementManager.findAbonements());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                imageDelete.setOnMouseClicked(event -> {
                    Abonement patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить абонемент?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        abonementManager.deleteAbonement(patient.getAbonement_id());
                    }
                    loadTable(abonementManager.findAbonements());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        loadTable(abonementManager.findAbonements());

        btnAddAbonement.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addAbonementPanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                AddAbonementController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить клиента");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setOnHidden(windowEvent ->{
                    if(controller.isSave())
                    {
                        abonementManager.addAbonement(controller.getAbonement());
                    }
                    loadTable(abonementManager.findAbonements());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearch.setOnAction(event->{
            searchManager.searchAbonements(tfTypeAbonementSearch.getText(),tableAbonements,abonementManager.findAbonements());
        });
        btnClear.setOnAction(event->{

            loadTable(abonementManager.findAbonements());
        });
    }
    private void loadTable(ObservableList<Abonement> list){
        tableAbonements.setItems(list);
    }
}
