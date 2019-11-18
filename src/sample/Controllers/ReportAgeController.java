package sample.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dbmanagers.ReportAgeManager;
import sample.entity.AgeReport;

import java.util.ArrayList;
import java.util.List;

public class ReportAgeController {
    ReportAgeManager reportAgeManager = new ReportAgeManager();
    List<Integer[]> intervals = new ArrayList<Integer[]>();
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
    private Label lbaverageAge;

    @FXML
    private TextField tfEndAgeInterval;

    @FXML
    private BarChart<?, ?> barchartClientAge;

    @FXML
    void initialize() {
        colNum.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(tableReport.getItems().indexOf(param.getValue()) + 1));
        colAge.setCellValueFactory(new PropertyValueFactory<AgeReport, String>("Interval"));
        colClientCount.setCellValueFactory(new PropertyValueFactory<AgeReport, Integer>("ClientCount"));
        btnToFormReport.setOnAction(e -> {
            if (rb15_25.isSelected()) {
                intervals.add(new Integer[]{15, 20});
            }
            if (!(tfstAgeInterval.getText() == null || tfstAgeInterval.getText().trim().isEmpty()
                    && tfEndAgeInterval.getText() == null || tfstAgeInterval.getText().trim().isEmpty())) {
                intervals.add(new Integer[]{Integer.parseInt(tfstAgeInterval.getText()), Integer.parseInt(tfEndAgeInterval.getText())});
            }
            loadTable(reportAgeManager.findItems());
        });
    }

    private void loadTable(ObservableList<AgeReport> list) {
        tableReport.setItems(list);
    }


}
