package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;


public class MainController {
    @FXML
    private Button btnClients;

    @FXML
    private Button btnEmplyoees;

    @FXML
    private Button btnServices;
    @FXML
    private Button btnAbonements;
    @FXML
    private Button btnAbonementsSale;
    @FXML
    private Button btnCalendar;
    @FXML
    private SplitMenuButton spMenuReports;
    @FXML
    private MenuItem miClientAgeReport;

    @FXML
    private MenuItem miAbonementTypeReport;

    @FXML
    private AnchorPane wrapperPane;
    @FXML
    private AnchorPane abonementsPane;
    @FXML
    private AnchorPane clientsPane;
    @FXML
    private AnchorPane employeesPane;
    @FXML
    private AnchorPane servicesPane;
    @FXML
    private AnchorPane abonementsSalePane;
    @FXML
    private AnchorPane reportClientAgePane;
    @FXML
    private AnchorPane reportAbTypePane;
    @FXML
    private AnchorPane calendarPane;
    @FXML
    void initialize() throws IOException {
        //FXMLLoader loader =  new FXMLLoader(getClass().getResource("/sample/calendarPane.fxml"));
        clientsPane = load(getClass().getResource("/sample/view/clientsPane.fxml"));
        employeesPane = load(getClass().getResource("/sample/view/employeesPane.fxml"));
        abonementsPane = load(getClass().getResource("/sample/view/abonementsPane.fxml"));
        servicesPane = FXMLLoader.<AnchorPane>load(getClass().getResource("/sample/view/servicesPane.fxml"));
        abonementsSalePane = load(getClass().getResource("/sample/view/abonementsSalePane.fxml"));
        calendarPane = load(getClass().getResource("/sample/view/calendarPane.fxml"));
        reportClientAgePane = load(getClass().getResource("/sample/view/reportClientAgePane.fxml"));
        reportAbTypePane = load(getClass().getResource("/sample/view/reportAbonementsTypePane.fxml"));
        btnClients.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(clientsPane);
            AnchorPane.setBottomAnchor(clientsPane, 0.0);
            AnchorPane.setLeftAnchor(clientsPane, 0.0);
            AnchorPane.setRightAnchor(clientsPane, 0.0);
            AnchorPane.setTopAnchor(clientsPane, 0.0);
        });
        btnEmplyoees.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(employeesPane);
        });
        btnAbonements.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(abonementsPane);
        });
        btnServices.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(servicesPane);
        });
        btnAbonementsSale.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(abonementsSalePane);
        });
        btnCalendar.setOnAction(event ->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(calendarPane);
        });
        miClientAgeReport.setOnAction(event->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(reportClientAgePane);
        });
        miAbonementTypeReport.setOnAction(event->{
            wrapperPane.getChildren().clear();
            wrapperPane.getChildren().add(reportAbTypePane);
        });
    }
}
