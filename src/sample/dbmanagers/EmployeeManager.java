package sample.dbmanagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.contactDB.PostegresConnectionBuilder;
import sample.entity.Employee;

import java.sql.*;

public class EmployeeManager {
    private static final String SELECT
            = "SELECT employee_id,Имя,Фамилия,Отчество,Пол,Телефон,\"Email\",Должность,Оклад FROM employees";
    private static final String SELECT_ONE
            = "SELECT client_id, first_name, last_name, phone, email FROM jc_contact WHERE contact_id=?";
    private static final String INSERT
            = "INSERT INTO employees (Имя,Фамилия,Отчество,Пол,Телефон,\"Email\",Должность, Оклад) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE
            = "UPDATE employees SET Имя=?, Фамилия=?, Отчество=?, Пол=?, Телефон=?, \"Email\"=?, Должность=?,Оклад=? WHERE employee_id=?";
    private static final String DELETE
            = "DELETE FROM employees WHERE employee_id=?";


    private PostegresConnectionBuilder connectionBuilder = new PostegresConnectionBuilder();
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public ObservableList<Employee> findEmployees()  {
        ObservableList<Employee> list = FXCollections.observableArrayList();
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

    private Employee fillContact(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setFirstname(rs.getString("Имя"));
        employee.setSecondname(rs.getString("Фамилия"));
        employee.setPatronymic(rs.getString("Отчество"));
        employee.setSex(rs.getString("Пол"));
        employee.setPhone(rs.getString("Телефон"));
        employee.setEmail(rs.getString("Email"));
        employee.setPosition(rs.getString("Должность"));
        employee.setSalary(rs.getFloat("Оклад"));
        return employee;
    }

    public void addEmployee(Employee employee) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT)) {
            try {
                pst.setString(1, employee.getFirstname());
                pst.setString(2, employee.getSecondname());
                pst.setString(3, employee.getPatronymic());
                pst.setString(4, employee.getSex());
                pst.setString(5, employee.getPhone());
                pst.setString(6, employee.getEmail());
                pst.setString(7, employee.getPosition());
                pst.setFloat(8, employee.getSalary());
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateEmployee (Employee employee,int employee_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            try {
                pst.setString(1, employee.getFirstname());
                pst.setString(2, employee.getSecondname());
                pst.setString(3, employee.getPatronymic());
                pst.setString(4, employee.getSex());
                pst.setString(5, employee.getPhone());
                pst.setString(6, employee.getEmail());
                pst.setString(7, employee.getPosition());
                pst.setFloat(8, employee.getSalary());
                pst.setInt(9,employee_id);
                pst.executeUpdate();
            }
            finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteEmployee(int employee_id){
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, employee_id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
