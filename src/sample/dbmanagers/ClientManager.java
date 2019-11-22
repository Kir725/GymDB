package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.Client;

import java.sql.*;


public class ClientManager {
    private static final String SELECT
            = "SELECT client_id,Имя,Фамилия,Отчество,Пол,Телефон,\"Email\",\"Дата рождения\"," +
            "\"Дата регистрации\", \"Тип Абонемента\" FROM clients";
    private static final String SELECT_BY_AB_TYPE
            = "SELECT Фамилия,\"Email\",Телефон,\"Дата рождения\" FROM clients WHERE \"Тип Абонемента\"::int = (SELECT abonement_id FROM abonements WHERE \"Название\" = ?)";
    private static final String INSERT
            = "INSERT INTO clients (Имя,Фамилия,Отчество,Пол,Телефон,\"Email\",\"Дата рождения\",\"Тип Абонемента\") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, (SELECT abonement_id FROM abonements WHERE Название = ?))";
    private static final String UPDATE
            = "UPDATE clients SET Имя=?, Фамилия=?, Отчество=?, Пол=?, Телефон=?, \"Email\"=?, \"Дата рождения\"=?,\"Тип Абонемента\"=(SELECT abonement_id FROM abonements WHERE Название = ?) WHERE client_id=?";
    private static final String DELETE
            = "DELETE FROM clients WHERE client_id=?";


    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<Client> findClients()  {
        ObservableList<Client> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillClient(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<Client> findClientsByAbType(String abType)  {
        ObservableList<Client> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_BY_AB_TYPE)) {
            try {
                pst.setString(1,abType);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    list.add(fillItems(rs));
                }
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    private Client fillItems(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setSecondname(rs.getString("Фамилия"));
        client.setEmail(rs.getString("Email"));
        client.setPhone(rs.getString("Телефон"));
        Date tempdate = rs.getDate("Дата рождения");
        client.setBornDate(tempdate.toLocalDate());
        return client;
    }
    private Client fillClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setFirstname(rs.getString("Имя"));
        client.setSecondname(rs.getString("Фамилия"));
        client.setPatronymic(rs.getString("Отчество"));
        client.setSex(rs.getString("Пол"));
        Date tempdate = rs.getDate("Дата рождения");
        client.setBornDate(tempdate.toLocalDate());
        client.setRegDate(rs.getString("Дата регистрации"));
        client.setPhone(rs.getString("Телефон"));
        client.setEmail(rs.getString("Email"));
        client.setAbonementType(rs.getString("Тип Абонемента"));
        return client;
    }

    public void addClient(Client client) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT)) {
            try {
                pst.setString(1, client.getFirstname());
                pst.setString(2, client.getSecondname());
                pst.setString(3, client.getPatronymic());
                pst.setString(4, client.getSex());
                pst.setString(5, client.getPhone());
                pst.setString(6, client.getEmail());
                java.sql.Date tempSqlDate = Date.valueOf(client.getBornDate());
                pst.setDate(7, tempSqlDate);
                pst.setString(8, client.getAbonementType());
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateClient (Client client,int client_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            try {
                pst.setString(1, client.getFirstname());
                pst.setString(2, client.getSecondname());
                pst.setString(3, client.getPatronymic());
                pst.setString(4, client.getSex());
                pst.setString(5, client.getPhone());
                pst.setString(6, client.getEmail());
                java.sql.Date tempSqlDate = Date.valueOf(client.getBornDate());
                pst.setDate(7, tempSqlDate);
                pst.setString(8, client.getAbonementType());
                pst.setInt(9,client_id);
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteClient(int client_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, client_id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
