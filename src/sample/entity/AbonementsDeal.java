package sample.entity;

import java.time.LocalDate;

public class AbonementsDeal {
    private int cardNumber;
    private String clientName;
    private String abonementTitle;
    private LocalDate startDate;
    private LocalDate endDate;

    public AbonementsDeal() {
    }
    public AbonementsDeal(String clientName, String abonementTitle, LocalDate startDate, LocalDate endDate) {
        this.clientName = clientName;
        this.abonementTitle = abonementTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAbonementTitle() {
        return abonementTitle;
    }

    public void setAbonementTitle(String abonementTitle) {
        this.abonementTitle = abonementTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
