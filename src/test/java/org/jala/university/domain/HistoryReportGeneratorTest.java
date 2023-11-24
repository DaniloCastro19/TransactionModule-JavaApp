package org.jala.university.domain;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;
import org.jala.university.model.TransactionType;
import org.jala.university.model.BankUser;
import org.jala.university.model.Currency;
import org.jala.university.model.Account;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class HistoryReportGeneratorTest {
    @Mock
    private TransactionModule transactionModule;

    @InjectMocks
    private HistoryReportGenerator reportGenerator;

    @Before
    public void setUp() {
        reportGenerator = new HistoryReportGenerator(transactionModule);
    }
    @Test
    public void generateReport() {
        String accountNumber = "0908786651";
        Date startDate = new Date();
        Date endDate = new Date();

        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(Transaction.builder()
                .id(UUID.randomUUID())
                .date(new Date())
                .type(TransactionType.DEPOSIT)
                .amount(100L)
                .currency(Currency.USD)
                .accountFrom(Account.builder().accountNumber("0908786651").build())
                .accountTo(Account.builder().accountNumber("9078655430").build())
                .status(TransactionStatus.COMPLETED)
                .description("Prueba de Transaccion")
                .build());

        BankUser mockUser = BankUser.builder()
                .id(UUID.randomUUID())
                .identification("ID001")
                .firstName("Gabriel")
                .lastName("Mendoza")
                .build();

        when(transactionModule.findTransactionsWithAccountNumber(accountNumber)).thenReturn(mockTransactions);
        when(transactionModule.getUserInfoForAccountNumber(accountNumber)).thenReturn(mockUser);

        String report = reportGenerator.generateReport(accountNumber);

        assertNotNull(report);
        assertTrue(report.contains("Nombre del Cliente: Gabriel Mendoza"));
        assertTrue(report.contains("Cliente ID: ID001"));
        assertTrue(report.contains("Numero de cuenta: 0908786651"));
    }
}