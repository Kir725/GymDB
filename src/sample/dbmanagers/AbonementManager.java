package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.Abonement;

import java.sql.*;

public class AbonementManager {
    private static final String SELECT
            = "SELECT abonement_id,Название,Цена,\"Часы посещения\",\"Срок действия\",Заморозка FROM abonements";
    private static final String SELECT_ONE
            = "SELECT client_id, first_name, last_name, phone, email FROM jc_contact WHERE contact_id=?";
    private static final String INSERT
            = "INSERT INTO abonements (Название,Цена,\"Часы посещения\",\"Срок действия\",Заморозка)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE
            = "UPDATE abonements SET Название=?, Цена=?, \"Часы посещения\"=?, \"Срок действия\"=?, Заморозка=? WHERE abonement_id=?";
    private static final String DELETE
            = "DELETE FROM abonements WHERE abonement_id=?";


    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<Abonement> findAbonements()  {
        ObservableList<Abonement> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillContact(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Abonement fillContact(ResultSet rs) throws SQLException {
        Abonement abonement = new Abonement();
        abonement.setAbonement_id(rs.getInt("abonement_id"));
        abonement.setTitleAbonement(rs.getString("Название"));
        abonement.setPrice(rs.getInt("Цена"));
        abonement.setVisiting_hours(rs.getString("Часы посещения"));
        abonement.setValidity(rs.getString("Срок действия"));
        abonement.setFreezing_time(rs.getString("Заморозка"));
        return abonement;
    }

    public void addAbonement(Abonement abonement) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT)) {
            try {
                pst.setString(1, abonement.getTitleAbonement());
                pst.setInt(2, abonement.getPrice());
                pst.setString(3, abonement.getVisiting_hours());
                pst.setString(4, abonement.getValidity());
                pst.setString(5, abonement.getFreezing_time());
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateAbonement (Abonement abonement,int abonement_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            try {
                pst.setString(1, abonement.getTitleAbonement());
                pst.setInt(2, abonement.getPrice());
                pst.setString(3, abonement.getVisiting_hours());
                pst.setString(4, abonement.getValidity());
                pst.setString(5, abonement.getFreezing_time());
                pst.setInt(6,abonement_id);
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteAbonement(int abonement_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, abonement_id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
