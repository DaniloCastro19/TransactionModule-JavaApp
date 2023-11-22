package org.jala.university.domain;

import org.jala.university.dao.*;
import org.jala.university.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TransactionServiceTest {
    private AccountDAOMock accountDAOMock = new AccountDAOMock();
    private UserDAOMock userDAOMock = new UserDAOMock();
    private ScheduledTransferDAOMock scheduledTransferDAOMock = new ScheduledTransferDAOMock();
    private ScheduledTransferModule scheduledTransferModule = new ScheduledTransferModuleImpl(scheduledTransferDAOMock);
    private TransactionDAOMock transactionDAOMock = new TransactionDAOMock();
    private UserModule userModule = new UserModuleImpl(accountDAOMock, userDAOMock);
    private TransactionModule transactionModule = new TransactionModuleImpl(transactionDAOMock);
    private TransactionService transactionService = new TransactionService(transactionModule,userModule,scheduledTransferModule);
    private CheckDAOMock checkDAOMock = new CheckDAOMock();
    private MockDataGenerator dataGenerator = new MockDataGenerator(userDAOMock, accountDAOMock,transactionDAOMock, checkDAOMock, scheduledTransferDAOMock);
    private List<BankUser> accountToUserResults;
    private List<BankUser> accountFromUserResults;
    private Account accountTo;
    private Account accountFrom;
    private TransactionStatus initialTransactionStatus = TransactionStatus.PENDING;
    Long initialAccountFromBalance;
    Long initialAccountToBalance;
    @BeforeEach
    public void setup(){
        dataGenerator.generateMockData();
        accountToUserResults = userModule.findUsersByAccountNumber("2");
        accountFromUserResults = userModule.findUsersByAccountNumber("1");
        accountTo= accountToUserResults.get(0).getAccount();
        accountFrom = accountFromUserResults.get(0).getAccount();
        initialAccountToBalance = accountTo.getBalance();
        initialAccountFromBalance = accountFrom.getBalance();
    }

    @Test
    void makeTransfer_transferCompleted() {
        //Test the response of the transaction execution in the Completed case.
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(new Date())
                .type(TransactionType.TRANSFER)
                .amount(1000l)
                .currency(Currency.USD)
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .status(initialTransactionStatus)
                .description("Transferencia.")
                .build();
        TransactionStatus actualTransactionStatus = transactionService.transfer(transaction);
        Long accountFromNewBalance = accountFrom.getBalance();
        Long accountToNewBalance = accountTo.getBalance();

        assertNotEquals(actualTransactionStatus, initialTransactionStatus);
        assertNotEquals(initialAccountFromBalance, accountFromNewBalance);
        assertNotEquals(initialAccountToBalance, accountToNewBalance);
        assertEquals(actualTransactionStatus, TransactionStatus.COMPLETED);
    }
    @Test
    void makeTransfer_transferFailed(){
        //Test the failed transfer case.
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .date(new Date())
                .type(TransactionType.TRANSFER)
                .amount(100000l)
                .currency(Currency.USD)
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .status(initialTransactionStatus)
                .description("Transferencia.")
                .build();

        TransactionStatus actualTransactionStatus = transactionService.transfer(transaction);
        Long accountFromNewBalance = accountFrom.getBalance();
        Long accountToNewBalance = accountTo.getBalance();

        assertEquals(initialAccountFromBalance, accountFromNewBalance);
        assertEquals(initialAccountToBalance, accountToNewBalance);
        assertEquals(actualTransactionStatus, TransactionStatus.FAILED);

    }
}