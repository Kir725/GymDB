package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

import sample.Controllers.AddClientController;
import sample.dbmanagers.ClientManager;
import sample.entity.Client;
import sample.instruments.SearchManager;

public class ClientsPaneController {
    private ClientManager clientManager = new ClientManager();
    private SearchManager searchManager = new SearchManager();

    @FXML
    private Button btnAddClient;
    @FXML
    private TableView<Client> tableClients;
    @FXML
    private TableColumn<Client, Integer> colNum;
    @FXML
    private TableColumn<Client, String> colName;
    @FXML
    private TableColumn<Client, LocalDate> colBornDate;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colPhone;
    @FXML
    private TableColumn<Client, String> colRegDate;
    @FXML
    private TableColumn<Client, Void> colActions;
    @FXML
    private Button btnSearchClients;
    @FXML
    private Button btnClear;
    @FXML
    private ChoiceBox<String> chbSexSearch;
    @FXML
    private TextField tfPhoneSearch;
    @FXML
    private TextField tfNameSearch;

    @FXML
    void initialize() throws IOException {
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableClients.getItems().indexOf(param.getValue())+1));
        colName.setCellValueFactory(new PropertyValueFactory<Client, String>("FullName"));
        colBornDate.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("BornDate"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("Phone"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<Client, String>("RegDate"));
        colActions.setCellFactory(param -> new TableCell<Client, Void>() {
            private ImageView imageUpdate = new ImageView(new Image("sample/sourse/pencil.png"));
            private ImageView imageDelete = new ImageView(new Image("sample/sourse/button_cancel.png"));
            {
                imageUpdate.setCursor(Cursor.HAND);
                imageDelete.setCursor(Cursor.HAND);
            }
            private final HBox pane = new HBox(10,imageUpdate, imageDelete);

            {
                imageUpdate.setOnMouseClicked(event -> {
                    Client patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/addClientPanel.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        AddClientController controller = fxmlLoader.getController();
                        controller.initData(patient);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Редактировать данные клиента");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        stage.setOnHidden(windowEvent ->{
                            if(controller.isSave())
                            {
                                clientManager.updateClient(controller.getClient(),patient.getClientId());
                            }
                            loadTable(clientManager.findClients());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                imageDelete.setOnMouseClicked(event -> {
                    Client patient = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Удалить клиента?", ButtonType.YES, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        clientManager.deleteClient(patient.getClientId());
                    }
                    loadTable(clientManager.findClients());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });

        loadTable(clientManager.findClients());

        btnAddClient.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/addClientPanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                AddClientController controller = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить клиента");
                stage.setScene(new Scene(root1));
                stage.show();
                stage.setOnHidden(windowEvent ->{
                    if(controller.isSave())
                    {
                        clientManager.addClient(controller.getClient());
                    }
                    loadTable(clientManager.findClients());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearchClients.setOnAction(event->{
            searchManager.searchClients(tfNameSearch.getText(),tfPhoneSearch.getText(),chbSexSearch.getValue()
                    ,tableClients,clientManager.findClients());
        });
        btnClear.setOnAction(event->{
            tfNameSearch.setText("");
            tfPhoneSearch.setText("");
            loadTable(clientManager.findClients());
        });
    }
    private void loadTable(ObservableList<Client> list){
        tableClients.setItems(list);
    }

}
