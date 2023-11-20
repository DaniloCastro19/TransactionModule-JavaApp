package org.jala.university.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.jala.university.dao.*;
import org.jala.university.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionModuleImplTest {

    private UserDAOMock userDAOMock;
    private AccountDAOMock accountDAOMock;
    private TransactionDAOMock transactionDAOMock;
    private TransactionModule transactionModule;
    private CheckDAOMock checkDAOMock;
    @BeforeEach
    public void setup() {
        userDAOMock = new UserDAOMock();
        accountDAOMock = new AccountDAOMock();
        transactionDAOMock = new TransactionDAOMock();
        checkDAOMock = new CheckDAOMock();

        MockDataGenerator generator = new MockDataGenerator(userDAOMock, accountDAOMock, transactionDAOMock,checkDAOMock);
        generator.generateMockData();
         transactionModule = new TransactionModuleImpl(transactionDAOMock);
    }

    @Test
    public void findTransactionsWithAccountNumber_SuccessfulFind() {
        int expectedNumberOfTransactions = 3;
        List<Transaction> allTransactions = transactionModule.findTransactionsWithAccountNumber("1");
        assertEquals(expectedNumberOfTransactions, allTransactions.size());
        for (Transaction transaction : allTransactions) {
            assertEquals(TransactionType.DEPOSIT, transaction.getType());
            assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
            assertNotNull(transaction.getAccountFrom());
            assertNotNull(transaction.getAccountTo());
        }
    }
    @Test
    public void findTransactionsWithNameOrLastNameTest_SuccessfulFind() {
        int expectedNumberOfTransactions = 15;
        List<Transaction> allTransactions = transactionModule.findTransactionsWithNameOrLastName("tes");
        assertEquals(expectedNumberOfTransactions, allTransactions.size());
        for (Transaction transaction : allTransactions) {
            assertEquals(TransactionType.DEPOSIT, transaction.getType());
            assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
            assertNotNull(transaction.getAccountFrom());
            assertNotNull(transaction.getAccountTo());
        }
    }
    @Test
    public void finTransactionsWithTransactionAmount_SuccessfulFind() {
        int expectedNumberOfTransactions = 15;
        List<Transaction> allTransactions = transactionModule.finTransactionsWithTransactionAmount(true);
        assertEquals(expectedNumberOfTransactions, allTransactions.size());
        for (Transaction transaction : allTransactions) {
            assertEquals(TransactionType.DEPOSIT, transaction.getType());
            assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
            assertNotNull(transaction.getAccountFrom());
            assertNotNull(transaction.getAccountTo());
        }
    }
    @Test
    public void findTransactionWithDateAmountTest_SuccessfulFind() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.DECEMBER, 13);
        Date specificDate = calendar.getTime();
        calendar.set(2021,Calendar.DECEMBER, 10);
        Date startDate = calendar.getTime();
        int expectedNumberOfTransactions = 15;
        List<Transaction> allTransactions = transactionModule.findTransactionWithDate(startDate, specificDate);
        assertEquals(expectedNumberOfTransactions, allTransactions.size());
        for (Transaction transaction : allTransactions) {
            assertEquals(TransactionType.DEPOSIT, transaction.getType());
            assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
            assertNotNull(transaction.getAccountFrom());
            assertNotNull(transaction.getAccountTo());
        }
    }
    @Test
    public void findTransactionWithTypeTest_SuccessfulFind() {
        int expectedNumberOfTransactions = 15;
        List<Transaction> allTransactions = transactionModule.findTransactionWithType(TransactionType.DEPOSIT);
        assertEquals(expectedNumberOfTransactions, allTransactions.size());
        for (Transaction transaction : allTransactions) {
            assertEquals(TransactionType.DEPOSIT, transaction.getType());
            assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
            assertNotNull(transaction.getAccountFrom());
            assertNotNull(transaction.getAccountTo());
        }
    }

}
