package org.jala.university.domain;

import org.jala.university.dao.CheckDAO;
import org.jala.university.model.Check;

import java.util.List;
import java.util.UUID;

public class CheckModuleImpl implements CheckModule{
    private CheckDAO checkDAO;

    public CheckModuleImpl(CheckDAO checkDAO) {
        this.checkDAO = checkDAO;
    }

    @Override
    public void createCheck(Check check) {
        checkDAO.create(check);
    }
    @Override
    public List<Check> findCheckWithUUID(UUID checkId) {
        return checkDAO.getCheckWithUUID(checkId);
    }
}
