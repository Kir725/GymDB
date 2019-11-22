package sample.entity;

public class AbTypeReport {
    private String abonementType;
    private Integer abSellCount;
    private Integer summ;

    public AbTypeReport() {
    }

    public AbTypeReport(String abonementType, Integer abSellCount, Integer summ) {
        this.abonementType = abonementType;
        this.abSellCount = abSellCount;
        this.summ = summ;
    }

    public String getAbonementType() {
        return abonementType;
    }

    public void setAbonementType(String abonementType) {
        this.abonementType = abonementType;
    }

    public Integer getAbSellCount() {
        return abSellCount;
    }

    public void setAbSellCount(Integer abSellCount) {
        this.abSellCount = abSellCount;
    }

    public Integer getSumm() {
        return summ;
    }

    public void setSumm(Integer summ) {
        this.summ = summ;
    }
}
