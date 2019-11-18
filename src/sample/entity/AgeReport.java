package sample.entity;

public class AgeReport {
    private Integer startAgeInterval;
    private Integer endAgeInterval;
    private Integer clientCount;



    public AgeReport() {
    }


    public AgeReport(Integer startAgeInterval, Integer endAgeInterval, Integer clientCount, Integer intervalStep) {
        this.startAgeInterval = startAgeInterval;
        this.endAgeInterval = endAgeInterval;
        this.clientCount = clientCount;
    }
    public Integer getStartAgeInterval() {
        return startAgeInterval;
    }

    public void setStartAgeInterval(Integer startAgeInterval) {
        this.startAgeInterval = startAgeInterval;
    }

    public Integer getEndAgeInterval() {
        return endAgeInterval;
    }

    public void setEndAgeInterval(Integer endAgeInterval) {
        this.endAgeInterval = endAgeInterval;
    }

    public Integer getClientCount() {
        return clientCount;
    }

    public void setClientCount(Integer clientCount) {
        this.clientCount = clientCount;
    }
    public String getInterval(){
        return startAgeInterval.toString()+ " - " + endAgeInterval.toString();
    }
    

}
