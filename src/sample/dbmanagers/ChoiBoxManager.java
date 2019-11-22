package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChoiBoxManager {
    private static final String SELECT_AB_TYPES
            = "SELECT Название FROM abonements";
    private static final String SELECT_EMPLOYEE_POSITIONS
            = "SELECT DISTINCT Должность FROM employees";
    private static final String SELECT_TRAINERS
            = "SELECT Фамилия FROM employees WHERE Должность = 'Тренер'";
    private static final String SELECT_SERVICES
            = "SELECT Наименование FROM services";
    private static final String SELECT_PLACES
            = "SELECT Название FROM placements";

    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<String> findAbTypes ()  {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_AB_TYPES);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Название"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> findEmployeePositions ()  {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_EMPLOYEE_POSITIONS);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Должность"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> findTrainers ()  {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_TRAINERS);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Фамилия"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> findServices ()  {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_SERVICES);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Наименование"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> findPlaces ()  {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_PLACES);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("Название"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
