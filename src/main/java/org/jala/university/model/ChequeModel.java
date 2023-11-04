package org.jala.university.model;

public class ChequeModel {
    private String name;
    private double amount;
    private String reason;
    private String currency;
    private String timeDateGenerationLabel;

    public ChequeModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeDateGenerationLabel() {
        return timeDateGenerationLabel;
    }

    public void setTimeDateGenerationLabel(String timeDateGenerationLabel) {
        this.timeDateGenerationLabel = timeDateGenerationLabel;
    }
}

