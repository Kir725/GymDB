package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;

import java.sql.*;

public class ReportAgeManager {
    private static final String SELECT
            =

    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<Calendar> findItems()  {
        ObservableList<Calendar> list = FXCollections.observableArrayList();
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

    private Calendar fillItems(ResultSet rs) throws SQLException {
        Calendar patient = new Calendar();
        patient.setCalendar_id(rs.getInt("№п.п."));
        Date tempdate = rs.getDate("Дата");
        patient.setDate(tempdate.toLocalDate());
        Time startTime = rs.getTime("Время начала");
        patient.setStartTime(startTime.toLocalTime());
        Time endTime = rs.getTime("Время окончания");
        patient.setEndTime(endTime.toLocalTime());
        patient.setServiceName(rs.getString("Наименование"));
        patient.setEmployeeName(rs.getString("Фамилия"));
        patient.setPlacementName(rs.getString("Название"));
        return patient;
    }
}
