package org.jala.university.presentation;

import org.jala.university.model.Account;
import org.jala.university.model.Currency;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionModuleTest {

  private TransactionSummaryView transactionSummaryView;

  @BeforeEach
  public void setUp() {
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

    transactionSummaryView = new TransactionSummaryView(values);
    transactionSummaryView.setVisible(true);
  }

  @Test
  public void testMainWhenExecutedThenTransactionSummaryViewIsCreatedAndDisplayed() {
    TransactionModule.main(new String[]{});

    assertTrue(transactionSummaryView.isVisible());
  }
}