package org.jala.university.dao;

import org.jala.university.model.Account;
import org.jala.university.model.AccountStatus;
import org.jala.university.model.BankUser;
import org.jala.university.model.Check;
import org.jala.university.model.CheckStatus;
import org.jala.university.model.Currency;
import org.jala.university.model.Transaction;
import org.jala.university.model.TransactionStatus;
import org.jala.university.model.TransactionType;

import java.util.Date;
import java.util.UUID;

public class MockDataGenerator {
    private final UserDAOMock userDaoMock;
    private final AccountDAOMock accountDaoMock;
    private final TransactionDAOMock transactionDaoMock;
    private final CheckDAOMock checkDAOMock;

    public MockDataGenerator(UserDAOMock userDaoMock, AccountDAOMock accountDaoMock, TransactionDAOMock transactionDaoMock, CheckDAOMock checkDAOMock) {
        this.userDaoMock = userDaoMock;
        this.accountDaoMock = accountDaoMock;
        this.transactionDaoMock = transactionDaoMock;
        this.checkDAOMock = checkDAOMock;
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
            user.setAccount(account);

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
            for (int checkIndex = 1; checkIndex <=3; checkIndex++){
                Check check = Check.builder()
                        .id(UUID.randomUUID())
                        .beneficiaryName(account.getName())
                        .date(new Date())
                        .amount(100L *checkIndex)
                        .reason("Check " + checkIndex + " for user" + checkIndex)
                        .currency(Currency.BOB)
                        .status(CheckStatus.ACTIVE)
                        .accountFrom(account)
                        .build();
                checkDAOMock.create(check);
            }
        }
    }
}
