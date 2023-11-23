package org.jala.university.domain;

import java.util.List;

/**
 * The {@code ScheduledTransferModule} interface defines operations related to scheduled transfers.
 * It provides methods for creating, retrieving, and managing scheduled transfer entities.
 * This interface serves as a contract for modules dealing with scheduled transfers in a banking application.
 */
public interface ScheduledTransferModule {
    void createScheduledTransfer(ScheduledTransferModel scheduledTransferModel);

    List<ScheduledTransferModel> findByRootAccount(String rootAccount);

    List<ScheduledTransferModel> findDailyTransfers(String accountFrom);
    List<ScheduledTransferModel> findMonthlyTransfers(String accountFrom);

    List<ScheduledTransferModel> findBiweeklyTransfers(String accountFrom);

    List<ScheduledTransferModel> findWeeklyTransfers(String accountFrom);

    List<ScheduledTransferModel> findAllScheduledTransfers();

    }
