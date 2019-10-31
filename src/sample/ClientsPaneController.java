package sample;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;
import sample.dbmanagers.ClientManager;
import sample.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ClientsPaneController {
    private ClientManager clientManager = new ClientManager();

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
    private TextField tfNameSearch;

    @FXML
    void initialize() throws IOException {
        colNum.setCellValueFactory(new PropertyValueFactory<Client, Integer>("ClientId"));
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
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addClientPanel.fxml"));
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
                            loadclients(clientManager.findClients());
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
                    loadclients(clientManager.findClients());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });

        loadclients(clientManager.findClients());

        btnAddClient.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addClientPanel.fxml"));
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
                    loadclients(clientManager.findClients());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnSearchClients.setOnAction(event->{
            search();
        });
        btnClear.setOnAction(event->{
            tfNameSearch.setText("");
            loadclients(clientManager.findClients());
        });
    }
    private void loadclients(ObservableList<Client> list){
        tableClients.setItems(list);
    }
    private void search(){
        String nameSample = tfNameSearch.getText();
        ObservableList<Client> originalList = clientManager.findClients();
        ObservableList<Client> finalList = FXCollections.observableArrayList();
        for(Client client : originalList){
            if(client.getFirstname().equals(nameSample)){
                finalList.add(client);
            }
        }
        tableClients.setItems(finalList);
    }

}
