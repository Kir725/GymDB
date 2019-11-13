package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.AbonementsDeal;

import java.sql.*;

public class DealManager {
    private static final String SELECT
            = "SELECT card_number,clients.Фамилия,abonements.Название,date_start,date_end\n" +
            "FROM abonements_sale AS ABS JOIN clients ON ABS.client_id = clients.client_id JOIN abonements ON ABS.abonement_id = abonements.abonement_id";
    private static final String SELECT_ONE
            = "SELECT service_id, first_name, last_name, phone, email FROM jc_contact WHERE service_id=?";
    private static final String INSERT
            = "INSERT INTO abonements_sale(client_id,abonement_id,date_start,date_end)" +
            "VALUES ((SELECT client_id FROM clients WHERE Фамилия = ?)," +
            "(SELECT abonement_id FROM abonements WHERE Название = ?),?,?)";
    private static final String UPDATE
            = "UPDATE abonements_sale SET client_id=(SELECT client_id FROM clients WHERE Фамилия = ?)," +
            "abonement_id=(SELECT abonement_id FROM abonements WHERE Название = ?), date_start=?, date_end=? WHERE card_number=?";
    private static final String DELETE
            = "DELETE FROM abonements_sale WHERE card_number=?";


    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<AbonementsDeal> findItems()  {
        ObservableList<AbonementsDeal> list = FXCollections.observableArrayList();
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

    private AbonementsDeal fillItems(ResultSet rs) throws SQLException {
        AbonementsDeal patient = new AbonementsDeal();
        patient.setCardNumber(rs.getInt("card_number"));
        patient.setClientName(rs.getString("Фамилия"));
        patient.setAbonementTitle(rs.getString("Название"));
        Date startDate = rs.getDate("date_start");
        patient.setStartDate(startDate.toLocalDate());
        Date endDate = rs.getDate("date_end");
        patient.setEndDate(endDate.toLocalDate());
        return patient;
    }

    public void addItem(AbonementsDeal patient) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT)) {
            try {
                pst.setString(1, patient.getClientName());
                pst.setString(2, patient.getAbonementTitle());
                java.sql.Date startSqlDate = Date.valueOf(patient.getStartDate());
                pst.setDate(3, startSqlDate);
                java.sql.Date endSqlDate = Date.valueOf(patient.getEndDate());
                pst.setDate(4, endSqlDate);
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateItem (AbonementsDeal patient,int card_number){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            try {
                pst.setString(1, patient.getClientName());
                pst.setString(2, patient.getAbonementTitle());
                java.sql.Date startSqlDate = Date.valueOf(patient.getStartDate());
                pst.setDate(3, startSqlDate);
                java.sql.Date endSqlDate = Date.valueOf(patient.getEndDate());
                pst.setDate(4, endSqlDate);
                pst.setInt(5,card_number);
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
