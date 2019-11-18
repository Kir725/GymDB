package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.AgeReport;

import java.sql.*;

public class ReportAgeManager {
    private static final String SELECT
            = "SELECT COUNT(AGE) AS count FROM(SELECT date_part(\'year\',age(\"Дата рождения\")) AS AGE FROM clients)t WHERE AGE BETWEEN ? AND ?";

    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<AgeReport> findItems()  {
        ObservableList<AgeReport> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
                 PreparedStatement pst = con.prepareStatement(SELECT)){
                try {
                    pst.setInt(1,20);
                    pst.setInt(2,40);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        list.add(fillItems(rs));
                    }
                }
                finally {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return list;
    }
    private AgeReport fillItems(ResultSet rs) throws SQLException {
        AgeReport patient = new AgeReport();
        patient.setClientCount(rs.getInt("count"));
        return patient;
    }
}
