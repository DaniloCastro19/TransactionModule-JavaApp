package org.jala.university.presentation;

import org.jala.university.model.Account;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;

import java.util.Date;
import java.util.UUID;

public class TransactionModule {
    public static void main(String[] args) {
        TransactionModuleValues values = TransactionModuleValues.builder()
                .id(UUID.randomUUID())
                .date(new Date())
                .transactionType(TransactionType.TRANSFER)
                .amount(1000L)
                .currency(Currency.EUR)
                .accountFrom(Account.builder().build())
                .accountTo(Account.builder().build())
                .transactionStatus(TransactionStatus.PENDING)
                .description("Esta transacción es solo una prueba.")
                .build();
        TransactionSummaryView window = new TransactionSummaryView(values);
        window.setVisible(true);
    }
}
