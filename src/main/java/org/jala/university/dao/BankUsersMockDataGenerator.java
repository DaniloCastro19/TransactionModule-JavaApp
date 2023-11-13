package org.jala.university.dao;

import org.jala.university.model.Account;
import org.jala.university.model.AccountStatus;
import org.jala.university.model.BankUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BankUsersMockDataGenerator {

    public List<BankUser> bankUsersModels(){
        List<BankUser> bankUsers = new ArrayList<>();
        BankUser userFrom = BankUser.builder()
                .id(UUID.randomUUID())
                .identification("111")
                .firstName("Dan")
                .lastName("Boneless")
                .build();
        BankUser userTo = BankUser.builder()
                .id(UUID.randomUUID())
                .identification("222")
                .firstName("Lu")
                .lastName("Ash")
                .build();

        List<Account> accountsList=  accountModels(userFrom, userTo);

        userFrom.setAccount(accountsList.get(0));
        userTo.setAccount(accountsList.get(1));
        bankUsers.add(userFrom);
        bankUsers.add(userTo);
        return bankUsers;
    }
    public List<Account> accountModels(BankUser user1, BankUser user2){
        List<Account>accounts = new ArrayList<>();
        Account accountFrom= Account.builder()
                .id(UUID.randomUUID())
                .accountNumber("11")
                .name("1Account")
                .balance(1000L)
                .status(AccountStatus.ACTIVE)
                .created(new Date())
                .updated(new Date())
                .owner(user1)
                .build();

        Account accountTo= Account.builder()
                .id(UUID.randomUUID())
                .accountNumber("22")
                .name("2Account")
                .balance(1000L)
                .status(AccountStatus.ACTIVE)
                .created(new Date())
                .updated(new Date())
                .owner(user2)
                .build();
        accounts.add(accountFrom);
        accounts.add(accountTo);
        return accounts;
    }

    public Account getAccount(String accountNumber){
        List<BankUser> userList = bankUsersModels();

        for (int i = 0; i < userList.size(); i++){
            if(accountNumber.equals(userList.get(i).getAccount().getAccountNumber())){
                return userList.get(i).getAccount();
            }
        }
        return null;
    }



}