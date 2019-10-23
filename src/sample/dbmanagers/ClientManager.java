package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostgesConnectionBuilder;
import sample.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientManager {
    private static final String SELECT
            = "SELECT * FROM clients";
    private static final String SELECT_ONE
            = "SELECT contact_id, first_name, last_name, phone, email FROM jc_contact WHERE contact_id=?";
    private static final String INSERT
            = "INSERT INTO jc_contact (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
    private static final String UPDATE
            = "UPDATE jc_contact SET first_name=?, last_name=?, phone=?, email=? WHERE contact_id=?";
    private static final String DELETE
            = "DELETE FROM jc_contact WHERE contact_id=?";


    private PostgesConnectionBuilder builder = new PostgesConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }
    public ObservableList<Client> findClients()  {
        ObservableList<Client> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillContact(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Client fillContact(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("№"));
        client.setName(rs.getString("Имя") + " "+ rs.getString("Фамилия") + " "+ rs.getString("Отчество"));
        client.setBornDate(rs.getString("Дата рождения"));
        client.setRegDate(rs.getString("Дата регистрации"));
        client.setPhone(rs.getString("Телефон"));
        client.setEmail(rs.getString("Email"));
        return client;
    }
}
