package org.jala.university;

import org.jala.university.dao.AccountDAOMock;
import org.jala.university.dao.CheckDAOMock;
import org.jala.university.dao.MockDataGenerator;
import org.jala.university.dao.TransactionDAOMock;
import org.jala.university.dao.UserDAOMock;
import org.jala.university.domain.CheckModule;
import org.jala.university.domain.CheckModuleImpl;
import org.jala.university.domain.TransactionModule;
import org.jala.university.domain.TransactionModuleImpl;
import org.jala.university.domain.UserModule;
import org.jala.university.domain.UserModuleImpl;
import org.jala.university.domain.AccountModule;
import org.jala.university.domain.AccountModuleImpl;
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
        TransactionModuleView transactionModuleView = new TransactionModuleView(accountModule, userModule, transactionModule, checkModule);
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
