package sample.entity;

public class Employee {
    private int employeeId;
    private String firstname;
    private String secondname;
    private String patronymic;
    private String email;
    private String phone;
    private String sex;
    private String position;
    private Float salary;

    public Employee() {
    }

    public Employee(String firstname, String secondname, String patronymic, String email, String phone, String sex, String position, Float salary) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.position = position;
        this.salary = salary;
    }

    public String getFullName() {
        return secondname +" "+ firstname +" "+ patronymic;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
}
