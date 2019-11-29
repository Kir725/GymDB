package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.print.PrinterJob;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.dbmanagers.ReportAgeManager;
import sample.entity.AgeReport;

import java.util.ArrayList;
import java.util.List;

public class ReportAgeController {
    private ReportAgeManager reportAgeManager = new ReportAgeManager();

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
    private RadioButton rb15_25;
    @FXML
    private RadioButton rb25_35;

    @FXML
    private RadioButton rb35_40;

    @FXML
    private RadioButton rb40_50;

    @FXML
    private RadioButton rb50_60;

    @FXML
    private RadioButton rb60_70;
    @FXML
    private Label lbaverageAge;

    @FXML
    private TextField tfEndAgeInterval;

    @FXML
    private BarChart<String, Number> barchartClientAge;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void initialize() {
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(param.getValue()) + 1));
        colAge.setCellValueFactory(new PropertyValueFactory<AgeReport, String>("Interval"));
        colClientCount.setCellValueFactory(new PropertyValueFactory<AgeReport, Integer>("ClientCount"));

        btnPrint.setOnAction(e->{
            PrinterJob printerJob = PrinterJob.createPrinterJob();
            if(printerJob.showPrintDialog((Stage)btnPrint.getScene().getWindow()) && printerJob.printPage(tableReport))
                printerJob.endJob();
        });

        btnToFormReport.setOnAction(e -> {
            tableReport.getItems().clear();
            barchartClientAge.getData().clear();

            List<Integer[]> intervals = new ArrayList<Integer[]>();
            if (rb15_25.isSelected()) intervals.add(new Integer[]{15, 20});
            if (rb25_35.isSelected()) intervals.add(new Integer[]{25, 35});
            if (rb35_40.isSelected()) intervals.add(new Integer[]{35, 40});
            if (rb40_50.isSelected()) intervals.add(new Integer[]{40, 50});
            if (rb50_60.isSelected()) intervals.add(new Integer[]{50, 60});
            if (rb60_70.isSelected()) intervals.add(new Integer[]{60, 70});

            if (!(tfstAgeInterval.getText() == null || tfstAgeInterval.getText().trim().isEmpty()
                    && tfEndAgeInterval.getText() == null || tfstAgeInterval.getText().trim().isEmpty())) {
                intervals.add(new Integer[]{Integer.parseInt(tfstAgeInterval.getText()), Integer.parseInt(tfEndAgeInterval.getText())});
            }
            loadTable(reportAgeManager.findItems(intervals));
            lbaverageAge.setText(reportAgeManager.getAverageAge().toString());
            XYChart.Series bChartData = new XYChart.Series();
            for(AgeReport item : reportAgeManager.findItems(intervals)){
                bChartData.getData().add(new XYChart.Data(item.getInterval(), item.getClientCount()));
            }

            barchartClientAge.getData().add(bChartData);
        });
        btnClear.setOnAction(event ->{
            tableReport.getItems().clear();
            barchartClientAge.getData().clear();
            rb15_25.setSelected(false);
            rb25_35.setSelected(false);
            rb35_40.setSelected(false);
            rb40_50.setSelected(false);
            rb50_60.setSelected(false);
            rb60_70.setSelected(false);
            tfstAgeInterval.setText("");
            tfEndAgeInterval.setText("");
        });
    }

    private void loadTable(ObservableList<AgeReport> list) {
        tableReport.setItems(list);
    }


}
