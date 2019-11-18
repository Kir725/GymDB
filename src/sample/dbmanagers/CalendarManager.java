package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.Calendar;

import java.sql.*;

public class CalendarManager {
    private static final String SELECT
            = "SELECT \"№п.п.\", \"Дата\", \"Время начала\", \"Время окончания\", services.Наименование, employees.Фамилия,placements.Название FROM calendar JOIN services ON calendar.Услуга = services.service_id JOIN employees ON calendar.Сотрудник = employees.employee_id JOIN placements ON calendar.Помещение = placements.place_id";
    private static final String SELECT_ONE
            = "SELECT service_id, first_name, last_name, phone, email FROM jc_contact WHERE service_id=?";
    private static final String INSERT
            = "INSERT INTO calendar(\n" +
            "\"Дата\", \"Время начала\", \"Время окончания\", \"Услуга\", \"Сотрудник\", \"Помещение\")" +
            "VALUES (?, ?,(SELECT ?::time + \"Длительность(мин)\"*'1 minute'::interval FROM services WHERE service_id=(SELECT service_id FROM services WHERE Наименование = ?))," +
            "(SELECT service_id FROM services WHERE Наименование = ?), (SELECT employee_id FROM employees WHERE Фамилия = ?),(SELECT place_id FROM placements WHERE Название = ?));";
    private static final String UPDATE
            = "UPDATE calendar SET Дата=?, \"Время начала\"=?, " +
            "\"Время окончания\"=(SELECT \"Время начала\" + \"Длительность(мин)\"*'1 minute'::interval FROM services WHERE service_id=(SELECT service_id FROM services WHERE Наименование = ?))" +
            ", Услуга=(SELECT service_id FROM services WHERE Наименование = ?)," +
            "Сотрудник = (SELECT employee_id FROM employees WHERE Фамилия = ?),Помещение = (SELECT place_id FROM placements WHERE Название = ?)  WHERE \"№п.п.\"=?";
    private static final String DELETE
            = "DELETE FROM calendar WHERE \"№п.п.\"=?";


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

    public void addItem(Calendar patient) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT)) {
            try {
                java.sql.Date tempSqlDate = Date.valueOf(patient.getDate());
                pst.setDate(1, tempSqlDate);
                java.sql.Time startTime = Time.valueOf(patient.getStartTime());
                pst.setTime(2,startTime);
                pst.setTime(3,startTime);
                pst.setString(4, patient.getServiceName());
                pst.setString(5, patient.getServiceName());
                pst.setString(6, patient.getEmployeeName());
                pst.setString(7, patient.getPlacementName());
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateItem (Calendar patient,int patient_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            try {
                java.sql.Date tempSqlDate = Date.valueOf(patient.getDate());
                pst.setDate(1, tempSqlDate);
                java.sql.Time startTime = Time.valueOf(patient.getStartTime());
                pst.setTime(2,startTime);
                pst.setString(3, patient.getServiceName());
                pst.setString(4, patient.getServiceName());
                pst.setString(5, patient.getEmployeeName());
                pst.setString(6, patient.getPlacementName());
                pst.setInt(7,patient_id);
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteItem(int patient_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, patient_id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
