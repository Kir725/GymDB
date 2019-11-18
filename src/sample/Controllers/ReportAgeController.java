package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.entity.AgeReport;

public class ReportAgeController {
    @FXML
    private Button btnPrint;

    @FXML
    private TableView<AgeReport> tableReport;

    @FXML
    private TableColumn<AgeReport, Integer> colNum;

    @FXML
    private TableColumn<AgeReport, String> colAge;

    @FXML
    private TableColumn<AgeReport, Integer> colClientCount;

    @FXML
    private Button btnToFormReport;

    @FXML
    private Button btnClear;

    @FXML
    private TextField tfstAgeInterval;

    @FXML
    private TextField tfAgeStep;

    @FXML
    private Label lbaverageAge;

    @FXML
    private TextField tfEndAgeInterval;

    @FXML
    private BarChart<?, ?> barchartClientAge;
    @FXML
    void initialize(){
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(param.getValue())+1));
        colAge.setCellValueFactory(new PropertyValueFactory<AgeReport, String>("Interval"));
        colClientCount.setCellValueFactory(new PropertyValueFactory<AgeReport, Integer>("ClientCount"));

    }

}
