package org.jala.university.domain;

import org.jala.university.dao.CheckDAO;
import org.jala.university.model.Check;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckModuleImpl implements CheckModule {
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

  @Override
  public List<Check> findCheckWithAccountNumber(String parameter) {
    return checkDAO.getChecksWithAccountNumber(parameter);
  }

  @Override
  public List<Check> findCheckWithName(String parameter) {
    return checkDAO.getChecksWithName(parameter);
  }

  @Override
  public List<Check> findCheckWithDate(Date startDate, Date endDate) {
    return checkDAO.getChecksWithDate(startDate, endDate);
  }

  @Override
  public List<Check> findChecksWithAmount(boolean b) {
    return checkDAO.getChecksWithAmount(b);
  }

  @Override
  public void delete(Check check) {
    checkDAO.delete(check);
  }
}
