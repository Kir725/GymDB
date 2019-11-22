package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.AgeReport;

import java.sql.*;
import java.util.List;

public class ReportAgeManager {
    private static final String SELECT
            = "SELECT COUNT(AGE) AS count FROM(SELECT date_part(\'year\',age(\"Дата рождения\")) AS AGE FROM clients)t WHERE AGE BETWEEN ? AND ?";
    private static final String SELECT_AVG_AGE
            = "SELECT AVG(date_part(\'year\',age(\"Дата рождения\"))) AS avg_age FROM clients";

    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<AgeReport> findItems(List<Integer[]> intervals)  {
        ObservableList<AgeReport> list = FXCollections.observableArrayList();
        for(Integer interval[] : intervals) {
            try (Connection con = getConnection();
                 PreparedStatement pst = con.prepareStatement(SELECT)) {
                try {
                    pst.setInt(1, interval[0]);
                    pst.setInt(2, interval[1]);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        list.add(fillItems(rs,interval));
                    }
                } finally {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    private AgeReport fillItems(ResultSet rs, Integer interval[]) throws SQLException {
        AgeReport patient = new AgeReport();
        patient.setStartAgeInterval(interval[0]);
        patient.setEndAgeInterval(interval[1]);

        patient.setClientCount(rs.getInt("count"));
        return patient;
    }
    public Integer getAverageAge (){
        int age = 0;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_AVG_AGE)) {
            try {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    age = rs.getInt("avg_age");
                }

            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }
}
