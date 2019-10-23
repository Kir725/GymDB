package sample.entity;

public class Client {
    private int clientid;
    private String name;
    private String email;
    private String phone;
    private String sex;
    private String bornDate;
    private String regDate;

    public Client() {
    }

    public Client(int id, String name, String email, String phone, String bornDate, String regDate) {
        this.clientid = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bornDate = bornDate;
        this.regDate = regDate;
    }

    public Client(int id, String name, String email, String phone, String sex, String bornDate, String regDate) {
        this.clientid = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.bornDate = bornDate;
        this.regDate = regDate;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
}
