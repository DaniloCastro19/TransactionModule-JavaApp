package org.jala.university.presentation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.jala.university.model.Account;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class TransactionModuleValues {
    private UUID id;
    private Date date;
    private TransactionType transactionType;
    private Long amount;
    private Currency currency;
    private Account accountFrom;
    private Account accountTo;
    private TransactionStatus transactionStatus;
    private String description;
    private String printableId;
    private String printableDate;
    private String printableTransactionType;
    private String printableAmount;
    private String printableCurrency;
    private String printableAccountFrom;
    private String printableAccountTo;
    private String printableTransactionStatus;
    private String printableDescription;

}
