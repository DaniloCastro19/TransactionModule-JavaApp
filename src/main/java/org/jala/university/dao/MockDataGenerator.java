package org.jala.university.dao;

import java.util.Date;
import java.util.UUID;
import org.jala.university.domain.Frequency;
import org.jala.university.domain.ScheduledTransferModel;
import org.jala.university.model.*;

public class MockDataGenerator {
    private final UserDAOMock userDaoMock;
    private final AccountDAOMock accountDaoMock;
    private final TransactionDAOMock transactionDaoMock;
    private final CheckDAOMock checkDAOMock;

    private final ScheduledTransferDAOMock scheduledTransferDAOMock;

    public MockDataGenerator(UserDAOMock userDaoMock, AccountDAOMock accountDaoMock, TransactionDAOMock transactionDaoMock, CheckDAOMock checkDAOMock, ScheduledTransferDAOMock scheduledTransferDAOMock) {
        this.userDaoMock = userDaoMock;
        this.accountDaoMock = accountDaoMock;
        this.transactionDaoMock = transactionDaoMock;
        this.checkDAOMock = checkDAOMock;
        this.scheduledTransferDAOMock = scheduledTransferDAOMock;
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
                    .balance(10000L * i)
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
                        .amount(10000L * j)
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
            for (int transferIndex = 1 ;transferIndex  < 6; transferIndex ++){
                ScheduledTransferModel scheduledTransfer = ScheduledTransferModel.builder()
                        .id(UUID.randomUUID())
                        .amount(100L * transferIndex)
                        .accountFrom(account)
                        .accountTo(account)
                        .currency(Currency.USD)
                        .date(new Date("11/30/2023"))
                        .status(TransactionStatus.PENDING)
                        .frequency(Frequency.EACH_MONTH)
                        .numOccurrences(2)
                        .details("Scheduled Transfer " + transferIndex + " for User " + transferIndex)
                        .build();

                scheduledTransferDAOMock.create(scheduledTransfer);
            }
        }
    }
}
