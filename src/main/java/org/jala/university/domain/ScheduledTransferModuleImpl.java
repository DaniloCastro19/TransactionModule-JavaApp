package org.jala.university.domain;

import org.jala.university.dao.ScheduledTransferDAOMock;

import java.util.List;

/**
 * The ScheduledTransferModuleImpl class is an implementation of the {@code ScheduledTransferModule} interface.
 * It provides concrete implementations for the methods defined in the interface,
 * handling the logic and data access related to scheduled transfers.
 */
public class ScheduledTransferModuleImpl implements ScheduledTransferModule {
    private ScheduledTransferDAOMock scheduledTransferDAOMock;

    public ScheduledTransferModuleImpl(ScheduledTransferDAOMock scheduledTransferDAOMock) {
        this.scheduledTransferDAOMock = scheduledTransferDAOMock;
    }

    @Override
    public void createScheduledTransfer(ScheduledTransferModel scheduledTransferModel) {
        scheduledTransferDAOMock.create(scheduledTransferModel);
    }

    @Override
    public List<ScheduledTransferModel> findByRootAccount(String rootAccount) {
        return scheduledTransferDAOMock.findByRootAccount(rootAccount);
    }
    @Override
    public List<ScheduledTransferModel> findDailyTransfers(String accountFrom) {
        return scheduledTransferDAOMock.findDailyTransfers(accountFrom);
    }

    @Override
    public List<ScheduledTransferModel> findMonthlyTransfers(String accountFrom) {
        return scheduledTransferDAOMock.findMonthlyTransfers(accountFrom);
    }

    @Override
    public List<ScheduledTransferModel> findBiweeklyTransfers(String accountFrom) {
        return scheduledTransferDAOMock.findBiweeklyTransfers(accountFrom);

    }

    @Override
    public List<ScheduledTransferModel> findWeeklyTransfers(String accountFrom) {
        return scheduledTransferDAOMock.findWeeklyTransfers(accountFrom);

    }

    @Override
    public List<ScheduledTransferModel> findAllScheduledTransfers() {
        return scheduledTransferDAOMock.findAllScheduledTransfers();
    }
}
