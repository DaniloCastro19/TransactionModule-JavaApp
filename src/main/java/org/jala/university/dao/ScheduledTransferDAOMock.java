package org.jala.university.dao;

import org.jala.university.domain.Frequency;
import org.jala.university.domain.ScheduledTransferModel;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Implementation of the DAOMock of scheduled transfer in simulated memory.
 */
public class ScheduledTransferDAOMock extends ScheduledTransferDAO {
    private final Map<UUID, ScheduledTransferModel> scheduledTransfers = new HashMap<>();

    public ScheduledTransferDAOMock() {
        super(null);
    }

    @Override
    public ScheduledTransferModel create(ScheduledTransferModel scheduledTransfer){
        scheduledTransfers.put(scheduledTransfer.getId(), scheduledTransfer);
        return scheduledTransfer;
    }

    @Override
    public List<ScheduledTransferModel> findByRootAccount(String rootAccount) {
        return scheduledTransfers.values().stream()
                .filter(scheduledTransferModel -> scheduledTransferModel.getAccountFrom().getAccountNumber().equals(rootAccount))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduledTransferModel> findDailyTransfers(String accountFrom) {
        return scheduledTransfers.values().stream()
                .filter(scheduledTransferModel -> scheduledTransferModel.getFrequency().equals(Frequency.EVERY_DAY)
                        && scheduledTransferModel.getAccountFrom().getAccountNumber().equals(accountFrom))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduledTransferModel> findMonthlyTransfers(String accountFrom) {
        return scheduledTransfers.values().stream()
                .filter(scheduledTransferModel -> scheduledTransferModel.getFrequency().equals(Frequency.EACH_MONTH)
                        && scheduledTransferModel.getAccountFrom().getAccountNumber().equals(accountFrom))
                .collect(Collectors.toList());
    }
    @Override
    public List<ScheduledTransferModel> findBiweeklyTransfers(String accountFrom) {
        return scheduledTransfers.values().stream()
                .filter(scheduledTransferModel -> scheduledTransferModel.getFrequency().equals(Frequency.EVERY_FIFTEEN_DAYS)
                        && scheduledTransferModel.getAccountFrom().getAccountNumber().equals(accountFrom))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduledTransferModel> findWeeklyTransfers(String accountFrom) {
        return scheduledTransfers.values().stream()
                .filter(scheduledTransferModel -> scheduledTransferModel.getFrequency().equals(Frequency.WEEKLY)
                        && scheduledTransferModel.getAccountFrom().getAccountNumber().equals(accountFrom))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduledTransferModel> findAllScheduledTransfers() {
        return new ArrayList<>(scheduledTransfers.values());
    }
}
