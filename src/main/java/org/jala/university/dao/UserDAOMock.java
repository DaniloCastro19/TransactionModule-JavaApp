package org.jala.university.dao;

import org.jala.university.model.BankUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserDAOMock extends UserDAO {
    private final Map<UUID, BankUser> users = new HashMap<>();
    private BankUsersMockDataGenerator dataGenerator = new BankUsersMockDataGenerator();

    private List<BankUser> bankUsers = dataGenerator.bankUsersModels();

    public UserDAOMock() {
        super(null);
    }

    @Override
    public BankUser create(BankUser user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<BankUser> findUsersByNameOrLastName(String searchTerm) {
        return users.values().stream()
                .filter(user -> user.getFirstName().contains(searchTerm) || user.getLastName().contains(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<BankUser> findUsersByAccountNumber(String accountNumber) {
        return users.values().stream()
                .filter(user -> user.getAccount() != null && user.getAccount().getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public BankUser update(BankUser user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(BankUser user) {
        users.remove(user.getId());
    }

    @Override
    public List<BankUser> findAll() {
        return new ArrayList<>(users.values());
    }
    public List<BankUser> getMockBankUsers(){
        return bankUsers;
    }

    public List<BankUser> findMockUsersByNameOrLastName(String searchTerm){
        List<BankUser> usersResult =bankUsers.stream()
                .filter(user -> user.getFirstName().contains(searchTerm)
                        || user.getLastName().contains(searchTerm))
                .collect(Collectors.toList());

        return usersResult;
    }

    public List<BankUser> findMockUsersByAccountNumber(String searchTerm){
        List<BankUser> usersResult =bankUsers.stream()
                .filter(user -> user.getAccount().getAccountNumber().equals(searchTerm))
                .collect(Collectors.toList());

        return usersResult;
    }
    public BankUser findUser(String accountNumber){
        for (BankUser userResult : bankUsers){
            if (userResult.getAccount().getAccountNumber().equals(accountNumber)){
                return userResult;
            }
        }
        return null;
    }
}
