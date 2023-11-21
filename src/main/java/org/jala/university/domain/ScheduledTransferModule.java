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

    List<ScheduledTransferModel> findByDestinationAccount(String destinationAccount);

    List<ScheduledTransferModel> findAllScheduledTransfers();

    }
