package org.jala.university;

import org.jala.university.dao.*;
import org.jala.university.domain.*;
import org.jala.university.presentation.TransactionModuleView;

import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {
        AccountDAOMock accountDAOMock = new AccountDAOMock();
        UserDAOMock userDAOMock = new UserDAOMock();
        TransactionDAOMock transactionDAOMock = new TransactionDAOMock();
        CheckDAOMock checkDAOMock = new CheckDAOMock();
        generateMockData(accountDAOMock, userDAOMock, transactionDAOMock, checkDAOMock);
        AccountModule accountModule = new AccountModuleImpl(accountDAOMock);
        UserModule userModule = new UserModuleImpl(accountDAOMock, userDAOMock);
        CheckModule checkModule = new CheckModuleImpl(checkDAOMock);
        TransactionModule transactionModule = new TransactionModuleImpl(transactionDAOMock);
        ScheduledTransferDAOMock scheduledTransferDAOMock = new ScheduledTransferDAOMock();
        ScheduledTransferModule scheduledTransferModule = new ScheduledTransferModuleImpl(scheduledTransferDAOMock);
        TransactionModuleView transactionModuleView = new TransactionModuleView(accountModule, userModule, transactionModule, checkModule, scheduledTransferModule);
        initForm(transactionModuleView);
    }

    
    private static void initForm(JFrame transactionModuleView) {
        transactionModuleView.setVisible(true);
    }

    private static void generateMockData(AccountDAOMock accountDAOMock, UserDAOMock userDAOMock, TransactionDAOMock transactionDAOMock, CheckDAOMock checkDAOMock){
        MockDataGenerator mockDataGenerator = new MockDataGenerator(userDAOMock, accountDAOMock, transactionDAOMock, checkDAOMock);
        mockDataGenerator.generateMockData();
    }
}
