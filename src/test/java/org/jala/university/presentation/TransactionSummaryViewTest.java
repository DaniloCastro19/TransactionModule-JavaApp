package org.jala.university.presentation;

import org.jala.university.model.Account;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class TransactionSummaryViewTest {
    @Test
    public void initTransactionSummaryViewTest() {
        TransactionModuleValues values = new TransactionModuleValues();

        values.setId(UUID.randomUUID());
        values.setDate(new Date());
        values.setTransactionType(TransactionType.TRANSFER);
        values.setAmount(1000);
        values.setCurrency(Currency.EUR);
        values.setAccountFrom(Account.builder().build());
        values.setAccountTo(Account.builder().build());
        values.setTransactionStatus(TransactionStatus.PENDING);
        values.setDescription("Esta transacción es solo una prueba.");

        TransactionSummaryView window = new TransactionSummaryView(values);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
