package org.jala.university.dao;

import org.jala.university.model.*;

import java.util.Date;
import java.util.UUID;

public class MockDataGenerator {
    private final UserDAOMock userDaoMock;
    private final AccountDAOMock accountDaoMock;
    private final TransactionDAOMock transactionDaoMock;

    public MockDataGenerator(UserDAOMock userDaoMock, AccountDAOMock accountDaoMock, TransactionDAOMock transactionDaoMock) {
        this.userDaoMock = userDaoMock;
        this.accountDaoMock = accountDaoMock;
        this.transactionDaoMock = transactionDaoMock;
    }
    public void generateMockData() {
        for (int i = 1; i <= 5; i++) {
            BankUser user = BankUser.builder()
                    .id(UUID.randomUUID())
                    .identification(String.valueOf(i))
                    .firstName("User" + i)
                    .lastName("Test" + i)
                    .build();
            userDaoMock.create(user);

            Account account = Account.builder()
                    .id(UUID.randomUUID())
                    .accountNumber(String.valueOf(i))
                    .name(user.getFirstName() + " " + user.getLastName())
                    .balance(1000L * i)
                    .status(AccountStatus.ACTIVE)
                    .currency(Currency.USD)
                    .created(new Date())
                    .updated(new Date())
                    .owner(user)
                    .build();
            accountDaoMock.create(account);

            for (int j = 1; j <= 3; j++) {
                Transaction transaction = Transaction.builder()
                        .id(UUID.randomUUID())
                        .date(new Date())
                        .type(TransactionType.DEPOSIT)
                        .amount(100L * j)
                        .currency(Currency.USD)
                        .accountFrom(account)
                        .accountTo(account)
                        .status(TransactionStatus.COMPLETED)
                        .description("Transaction " + j + " for User " + i)
                        .build();
                transactionDaoMock.create(transaction);
            }
        }
    }
}
