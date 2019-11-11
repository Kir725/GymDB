package sample.entity;

public class Abonement {
    private int abonement_id;
    private String titleAbonement;
    private Integer price;
    private String visiting_hours;
    private String validity;
    private String freezing_time;

    public Abonement() {
    }

    public Abonement(String titleAbonement, Integer price, String visiting_hours, String validity, String freezing_time) {
        this.titleAbonement = titleAbonement;
        this.price = price;
        this.visiting_hours = visiting_hours;
        this.validity = validity;
        this.freezing_time = freezing_time;
    }

    public int getAbonement_id() {
        return abonement_id;
    }

    public void setAbonement_id(int abonement_id) {
        this.abonement_id = abonement_id;
    }

    public String getTitleAbonement() {
        return titleAbonement;
    }

    public void setTitleAbonement(String titleAbonement) {
        this.titleAbonement = titleAbonement;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getVisiting_hours() {
        return visiting_hours;
    }

    public void setVisiting_hours(String visiting_hours) {
        this.visiting_hours = visiting_hours;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getFreezing_time() {
        return freezing_time;
    }

    public void setFreezing_time(String freezing_time) {
        this.freezing_time = freezing_time;
    }
}
