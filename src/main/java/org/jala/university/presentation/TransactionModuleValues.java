package org.jala.university.presentation;

import org.jala.university.model.Account;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;

import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

public class TransactionModuleValues {
    private UUID id;
    private Date date;
    private TransactionType type;
    private Long amount;
    private Currency currency;
    private Account accountFrom;
    private Account accountTo;
    private TransactionStatus status;
    private String description;

    public TransactionModuleValues() {

    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getPrintableId(){
        return this.id.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getPrintableDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(this.date);
    }

    public void setTransactionType(TransactionType type) {
        this.type = type;
    }
    public String getPrintableTransactionType() {
        return this.type.name();
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
    public String getPrintableAmount() {
        return String.valueOf(this.amount) + " " + currency.getSymbol();
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public String getPrintableCurrency(){
        return currency.getName();
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }
    public String getPrintableAccountFrom(){
        return this.accountFrom.getAccountNumber();
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }
    public String getPrintableAccountTo() {
        return this.accountTo.getAccountNumber();
    }

    public void setTransactionStatus(TransactionStatus status) {
        this.status = status;
    }
    public String getPrintableTransactionStatus() {
        return this.status.name();
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrintableDescription() {
        return this.description;
    }
}
