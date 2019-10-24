package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.dbmanagers.ClientManager;
import sample.entity.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    private TableColumn<Client, String> colBornDate;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colPhone;
    @FXML
    private TableColumn<Client, String> colRegDate;
    @FXML
    private TableColumn<Client, Void> colActions;
    @FXML
    private Button btnFindClient;
    @FXML
    private Button btnClear;
    @FXML
    private TextField tfNameSearch;

    @FXML
    void initialize() throws IOException {

        loadclients();

        btnAddClient.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addClientPanel.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Добавить клиента");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void loadclients(){
        ObservableList<Client> list = clientManager.findClients();
        colNum.setCellValueFactory(new PropertyValueFactory<Client, Integer>("ClientId"));
        colName.setCellValueFactory(new PropertyValueFactory<Client, String>("Name"));
        colBornDate.setCellValueFactory(new PropertyValueFactory<Client, String>("BornDate"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("Phone"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<Client, String>("RegDate"));
        colActions.setCellFactory(param -> new TableCell<Client, Void>() {
            private ImageView imageUpdate = new ImageView(new Image("sample/sourse/pencil.png"));
            private ImageView imageDelete = new ImageView(new Image("sample/sourse/button_cancel.png"));
            private final Button editButton = new Button("edit");
            private final Button deleteButton = new Button("delete");
            private final HBox pane = new HBox(10,imageUpdate, imageDelete);

            {
                imageUpdate.setOnMouseClicked(event -> {
                    Client getPatient = getTableView().getItems().get(getIndex());
                    System.out.println(getPatient.getClientId() + "   " + getPatient.getName());
                });

                imageDelete.setOnMouseClicked(event -> {
                    Client getPatient = getTableView().getItems().get(getIndex());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });
        tableClients.setItems(list);
    }

}
