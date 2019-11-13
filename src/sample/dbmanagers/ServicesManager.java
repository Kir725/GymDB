package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicesManager {
    private static final String SELECT
            = "SELECT service_id,Наименование,\"Длительность(мин)\",Стоимость,Описание FROM services";
    private static final String SELECT_ONE
            = "SELECT service_id, first_name, last_name, phone, email FROM jc_contact WHERE service_id=?";
    private static final String INSERT
            = "INSERT INTO services (Наименование,\"Длительность(мин)\",Стоимость,Описание)" +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE
            = "UPDATE services SET Наименование=?, \"Длительность(мин)\"=?, Стоимость=?, Описание=? WHERE service_id=?";
    private static final String DELETE
            = "DELETE FROM services WHERE service_id=?";


    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<Service> findItems()  {
        ObservableList<Service> list = FXCollections.observableArrayList();
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

    private Service fillItems(ResultSet rs) throws SQLException {
        Service patient = new Service();
        patient.setService_id(rs.getInt("service_id"));
        patient.setServiceName(rs.getString("Наименование"));
        patient.setValidity(rs.getInt("Длительность(мин)"));
        patient.setPrice(rs.getFloat("Стоимость"));
        patient.setDescription(rs.getString("Описание"));
        return patient;
    }

    public void addItem(Service patient) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT)) {
            try {
                pst.setString(1, patient.getServiceName());
                pst.setInt(2, patient.getValidity());
                pst.setFloat(3, patient.getPrice());
                pst.setString(4, patient.getDescription());
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateItem (Service patient,int service_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            try {
                pst.setString(1, patient.getServiceName());
                pst.setInt(2, patient.getValidity());
                pst.setFloat(3, patient.getPrice());
                pst.setString(4, patient.getDescription());
                pst.setInt(5,service_id);
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
