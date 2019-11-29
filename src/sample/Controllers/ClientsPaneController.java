package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

import org.kordamp.ikonli.javafx.FontIcon;
import sample.dbmanagers.ClientManager;
import sample.entity.Client;
import sample.instruments.SearchManager;

import javax.swing.*;

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
        colNum.setMaxWidth( 1f * Integer.MAX_VALUE * 3 );
        colName.setCellValueFactory(new PropertyValueFactory<Client, String>("FullName"));
        colName.setMaxWidth( 1f * Integer.MAX_VALUE * 27);
        colBornDate.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("BornDate"));
        colBornDate.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );
        colEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("Email"));
        colEmail.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        colPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("Phone"));
        colPhone.setMaxWidth( 1f * Integer.MAX_VALUE * 20 );
        colRegDate.setCellValueFactory(new PropertyValueFactory<Client, String>("RegDate"));
        colRegDate.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );
        colActions.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );
        colActions.setCellFactory(param -> new TableCell<Client, Void>() {
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
                    Client patient = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addClientPanel.fxml"));
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
                iconDelete.setOnMouseClicked(event -> {
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/addClientPanel.fxml"));
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
