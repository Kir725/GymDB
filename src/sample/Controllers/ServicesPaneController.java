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
import org.kordamp.ikonli.javafx.FontIcon;
import sample.dbmanagers.ServicesManager;
import sample.entity.Service;
import sample.instruments.SearchManager;

import java.io.IOException;

public class ServicesPaneController {
    private ServicesManager servicesManager = new ServicesManager();
    private SearchManager searchManager = new SearchManager();

    @FXML
    private Button btnAddService;

    @FXML
    private TableView<Service> tableBD;

    @FXML
    private TableColumn<Service, Integer> colNum;

    @FXML
    private TableColumn<Service, String> colName;

    @FXML
    private TableColumn<Service, Integer> colValidating;

    @FXML
    private TableColumn<Service, Float> colPrice;

    @FXML
    private TableColumn<Service, String> colDescription;

    @FXML
    private TableColumn<Service, Void> colActions;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private TextField tfServiceNameSearch;
    @FXML
    void initialize(){
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableBD.getItems().indexOf(param.getValue())+1));
        colName.setCellValueFactory(new PropertyValueFactory<Service, String>("ServiceName"));
        colValidating.setCellValueFactory(new PropertyValueFactory<Service, Integer>("Validity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Service, Float>("Price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Service, String>("Description"));
        colActions.setCellFactory(param -> new TableCell<Service, Void>() {
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
                    Service patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/AddServicePanel.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        AddServicesPaneController controller = fxmlLoader.getController();
                        controller.initData(patient);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Изменить услугу");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        stage.setOnHidden(windowEvent ->{
                            if(controller.isSave())
                            {
                                servicesManager.updateItem(controller.getItem(),patient.getService_id());
                            }
                            loadTable(servicesManager.findItems());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                iconDelete.setOnMouseClicked(event -> {
                    Service patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить абонемент?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        servicesManager.deleteItem(patient.getService_id());
                    }
                    loadTable(servicesManager.findItems());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        loadTable(servicesManager.findItems());

        btnAddService.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/AddServicePanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                AddServicesPaneController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить услугу");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setOnHidden(windowEvent ->{
                    if(controller.isSave())
                    {
                        servicesManager.addItem(controller.getItem());
                    }
                    loadTable(servicesManager.findItems());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearch.setOnAction(event->{
            searchManager.searchServices(tfServiceNameSearch.getText(),tableBD,servicesManager.findItems());
        });
        btnClear.setOnAction(event->{

            loadTable(servicesManager.findItems());
        });
    }
    private void loadTable(ObservableList<Service> list){
        tableBD.setItems(list);
    }
}
