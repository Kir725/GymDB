package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.AbTypeReport;
import sample.entity.AbonementsDeal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportAbTypeManager {
    private static final String SELECT
            = "SELECT Название, COUNT(\"Тип Абонемента\") AS countAb , COUNT(\"Тип Абонемента\")*abonements.Цена as Summ FROM clients JOIN abonements ON \"Тип Абонемента\"::int = abonements.abonement_id GROUP BY Название,abonements.Цена";
    private static final String SELECT_ALL_SALES
            = "SELECT COUNT(\"Тип Абонемента\") AS all_sales FROM clients";
    private static final String SELECT_OVERALL_SUMM
            = "SELECT SUM(countAb) as sum FROM (SELECT COUNT(\"Тип Абонемента\")*abonements.Цена as countAb  FROM clients JOIN abonements ON \"Тип Абонемента\"::int = abonements.abonement_id GROUP BY abonements.Цена)t";

    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<AbTypeReport> findItems()  {
        ObservableList<AbTypeReport> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillItems(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private AbTypeReport fillItems(ResultSet rs) throws SQLException {
        AbTypeReport patient = new AbTypeReport();
        patient.setAbonementType(rs.getString("Название"));
        patient.setAbSellCount(rs.getInt("countAb"));
        patient.setSumm(rs.getInt("Summ"));
        return patient;
    }
    public Integer getAllSales (){
        int allSales = 0;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_ALL_SALES)) {
            try {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    allSales = rs.getInt("all_sales");
                }

            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allSales;
    }
    public Integer getOverallSumm (){
        int overalSumm = 0;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_OVERALL_SUMM)) {
            try {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    overalSumm = rs.getInt("sum");
                }

            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overalSumm;
    }
}
