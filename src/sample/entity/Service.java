package sample.entity;

public class Service {
    private int service_id;
    private String serviceName;
    private Integer validity;
    private Float price;
    private String description;

    public Service() {
    }

    public Service(String serviceName, Integer validity, Float price, String description) {
        this.serviceName = serviceName;
        this.validity = validity;
        this.price = price;
        this.description = description;
    }


    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
