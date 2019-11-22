package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.entity.AbTypeReport;
import java.time.LocalDate;

import sample.entity.Client;
import sample.dbmanagers.ClientManager;
import sample.dbmanagers.ReportAbTypeManager;


public class ReportAbTypeController {
    private ReportAbTypeManager reportAbTypeManager = new ReportAbTypeManager();
    private ClientManager clientManager = new ClientManager();
    @FXML
    private Button btnPrint;

    @FXML
    private TableView<AbTypeReport> tableReport;

    @FXML
    private TableColumn<AbTypeReport, Integer> colNumAbType;

    @FXML
    private TableColumn<AbTypeReport, String> colAbonementType;

    @FXML
    private TableColumn<AbTypeReport, Integer> colSoldNumber;

    @FXML
    private TableColumn<AbTypeReport, Integer> colSumm;

    @FXML
    private Button btnToFormReport;

    @FXML
    private Button btnClear;

    @FXML
    private Label lbAllAbenementSold;

    @FXML
    private Label lbOverallSum;

    @FXML
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, Integer> colNumClients;

    @FXML
    private TableColumn<Client, String> colClientSecName;

    @FXML
    private TableColumn<Client, String> colEmail;

    @FXML
    private TableColumn<Client, String> colPhone;

    @FXML
    private TableColumn<Client, LocalDate> colBornDate;

    @FXML
    void initialize() {
        colNumAbType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(param.getValue()) + 1));
        colAbonementType.setCellValueFactory(new PropertyValueFactory<AbTypeReport, String>("AbonementType"));
        colSoldNumber.setCellValueFactory(new PropertyValueFactory<AbTypeReport, Integer>("AbSellCount"));
        colSumm.setCellValueFactory(new PropertyValueFactory<AbTypeReport, Integer>("Summ"));
        btnToFormReport.setOnAction(e ->{
            tableReport.setItems(reportAbTypeManager.findItems());
            lbAllAbenementSold.setText(reportAbTypeManager.getAllSales().toString());
            lbOverallSum.setText(reportAbTypeManager.getOverallSumm().toString());
        });
        colNumClients.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableClients.getItems().indexOf(param.getValue()) + 1));
        colClientSecName.setCellValueFactory(new PropertyValueFactory<Client, String>("Secondname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("Phone"));
        colBornDate.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("BornDate"));
        tableReport.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                AbTypeReport patient = tableReport.getSelectionModel().getSelectedItem();
                tableClients.setItems(clientManager.findClientsByAbType(patient.getAbonementType()));
            }
        });
        btnPrint.setOnAction(e->{
            PrinterJob printerJob = PrinterJob.createPrinterJob();
            if(printerJob.showPrintDialog((Stage)btnPrint.getScene().getWindow()) && printerJob.printPage(tableReport))
                printerJob.endJob();
        });
        btnClear.setOnAction(event ->{
            tableReport.getItems().clear();
            tableClients.getItems().clear();
        });
    }
}
