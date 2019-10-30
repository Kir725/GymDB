package sample.entity;

import java.time.LocalDate;
import java.util.Date;

public class Client {
    private int clientid;
    private String firstname;
    private String secondname;
    private String patronymic;
    private String email;
    private String phone;
    private String sex;
    private LocalDate bornDate;
    private String regDate;
    private String abonementType;



    public Client() {
    }

    public Client(String firstname, String secondname, String patronymic, String email, String phone, LocalDate bornDate) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.bornDate = bornDate;
    }

    public Client(String firstname, String secondname, String patronymic, String email, String phone, String sex, LocalDate bornDate, String abonementType) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.bornDate = bornDate;
        this.regDate = regDate;
        this.abonementType = abonementType;
    }

    public String getFullName() {
        return secondname +" "+ firstname +" "+ patronymic;
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

    public String getAbonementType() {
        return abonementType;
    }

    public void setAbonementType(String abonementType) {
        this.abonementType = abonementType;
    }




    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    public int getClientId() {
        return clientid;
    }
    public void setId(int id) {
        this.clientid = id;
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
    public LocalDate getBornDate() {
        return bornDate;
    }
    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }
}
