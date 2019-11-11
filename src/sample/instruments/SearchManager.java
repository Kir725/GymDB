package sample.instruments;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import sample.entity.Abonement;
import sample.entity.Client;
import sample.entity.Employee;

public class SearchManager {
    public void searchClients(String enterName, String enterPhone,String sex, TableView<Client> table, ObservableList<Client> originalList){
        FilteredList<Client> filterList = new FilteredList<>(originalList);
        filterList.setPredicate(client -> client.getFullName().toLowerCase().contains(enterName.toLowerCase()) &&
                client.getPhone().toLowerCase().contains(enterPhone.toLowerCase()) && client.getSex().equals(sex));
        table.setItems(filterList);
    }
    public void searchEmployees(String enterName, String enterPhone, String sex, String position, TableView<Employee> table, ObservableList<Employee> originalList){
        FilteredList<Employee> filterList = new FilteredList<>(originalList);
        filterList.setPredicate(employee -> employee.getFullName().toLowerCase().contains(enterName.toLowerCase()) &&
                employee.getPhone().toLowerCase().contains(enterPhone.toLowerCase()) && employee.getSex().equals(sex)
        && employee.getPosition().toLowerCase().contains(position.toLowerCase()));
        table.setItems(filterList);
    }
    public void searchAbonements(String abonementType, TableView<Abonement> table, ObservableList<Abonement> originalList){
        FilteredList<Abonement> filterList = new FilteredList<>(originalList);
        filterList.setPredicate(abonement -> abonement.getTitleAbonement().toLowerCase().contains(abonementType.toLowerCase()));
        table.setItems(filterList);
    }
}
