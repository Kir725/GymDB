package sample.Controllers;

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
import sample.dbmanagers.ChoiBoxManager;
import sample.dbmanagers.DealManager;
import sample.entity.AbonementsDeal;
import sample.instruments.SearchManager;

import java.io.IOException;
import java.time.LocalDate;

public class AbonementsSalePaneController {
    private DealManager dealManager = new DealManager();
    private SearchManager searchManager = new SearchManager();
    private ChoiBoxManager choiBoxManager = new ChoiBoxManager();
    @FXML
    private Button btnAddDeal;

    @FXML
    private TableView<AbonementsDeal> tableBD;

    @FXML
    private TableColumn<AbonementsDeal, Integer> colCardNumber;

    @FXML
    private TableColumn<AbonementsDeal, String> colClientName;

    @FXML
    private TableColumn<AbonementsDeal, String> colAbonementType;

    @FXML
    private TableColumn<AbonementsDeal, LocalDate> colDateStart;

    @FXML
    private TableColumn<AbonementsDeal, LocalDate> colDateEnd;

    @FXML
    private TableColumn<AbonementsDeal, Void> colActions;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private TextField tfCardNumberSearch;

    @FXML
    private TextField tfClientNameSearch;

    @FXML
    private ChoiceBox<String> choibxAbonementType;
    @FXML
    void initialize(){
        choibxAbonementType.getItems().addAll(choiBoxManager.findAbTypes());
        colCardNumber.setCellValueFactory(new PropertyValueFactory<AbonementsDeal, Integer>("CardNumber"));
        colClientName.setCellValueFactory(new PropertyValueFactory<AbonementsDeal, String>("ClientName"));
        colAbonementType.setCellValueFactory(new PropertyValueFactory<AbonementsDeal, String>("AbonementTitle"));
        colDateStart.setCellValueFactory(new PropertyValueFactory<AbonementsDeal, LocalDate>("StartDate"));
        colDateEnd.setCellValueFactory(new PropertyValueFactory<AbonementsDeal, LocalDate>("EndDate"));
        colActions.setCellFactory(param -> new TableCell<AbonementsDeal, Void>() {
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
                    AbonementsDeal patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addAbonementsDealPanel.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        AddAbonementsDealController controller = fxmlLoader.getController();
                        controller.initData(patient);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Изменить данные");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        stage.setOnHidden(windowEvent ->{
                            if(controller.isSave())
                            {
                                dealManager.updateItem(controller.getItem(),patient.getCardNumber());
                            }
                            loadTable(dealManager.findItems());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                iconDelete.setOnMouseClicked(event -> {
                    AbonementsDeal patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить данные?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        dealManager.deleteItem(patient.getCardNumber());
                    }
                    loadTable(dealManager.findItems());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        loadTable(dealManager.findItems());

        btnAddDeal.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addAbonementsDealPanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                AddAbonementsDealController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить данные");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setOnHidden(windowEvent ->{
                    if(controller.isSave())
                    {
                        dealManager.addItem(controller.getItem());
                    }
                    loadTable(dealManager.findItems());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearch.setOnAction(event->{
            searchManager.searchDeals(tfCardNumberSearch.getText(),tfClientNameSearch.getText(),choibxAbonementType.getValue(),tableBD,dealManager.findItems());
        });
        btnClear.setOnAction(event->{
            loadTable(dealManager.findItems());
        });
    }
    private void loadTable(ObservableList<AbonementsDeal> list){
        tableBD.setItems(list);
    }
}
