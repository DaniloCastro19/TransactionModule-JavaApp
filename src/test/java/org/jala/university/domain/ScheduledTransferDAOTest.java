package org.jala.university.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jala.university.dao.ScheduledTransferDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScheduledTransferDAOTest {
    private final EntityManager entityManagerMock = Mockito.mock(EntityManager.class);
    private final TypedQuery<ScheduledTransferModel> queryMock = Mockito.mock(TypedQuery.class);
    private final ScheduledTransferDAO scheduledTransferDAO = new ScheduledTransferDAO(entityManagerMock);

    @Test
    void findByRootAccount_SuccessfulFind() {
        Mockito.when(entityManagerMock.createQuery(Mockito.anyString(), Mockito.eq(ScheduledTransferModel.class)))
                .thenReturn(queryMock);

        List<ScheduledTransferModel> expectedResults = createScheduledTransferList();
        Mockito.when(queryMock.getResultList()).thenReturn(expectedResults);
        List<ScheduledTransferModel> result = scheduledTransferDAO.findByRootAccount("rootAccount");
        assertEquals(expectedResults, result);
    }

    @Test
    void findByDestinationAccount_SuccessfulFind() {
        Mockito.when(entityManagerMock.createQuery(Mockito.anyString(), Mockito.eq(ScheduledTransferModel.class)))
                .thenReturn(queryMock);

        List<ScheduledTransferModel> expectedResults = createScheduledTransferList();
        Mockito.when(queryMock.getResultList()).thenReturn(expectedResults);
        List<ScheduledTransferModel> result = scheduledTransferDAO.findByDestinationAccount("destinationAccount");
        assertEquals(expectedResults, result);
    }

    @Test
    void findAllScheduledTransfers_SuccessfulFind() {
        Mockito.when(entityManagerMock.createQuery(Mockito.anyString(), Mockito.eq(ScheduledTransferModel.class)))
                .thenReturn(queryMock);

        List<ScheduledTransferModel> expectedResults = createScheduledTransferList();
        Mockito.when(queryMock.getResultList()).thenReturn(expectedResults);
        List<ScheduledTransferModel> result = scheduledTransferDAO.findAllScheduledTransfers();
        assertEquals(expectedResults, result);
    }

    private List<ScheduledTransferModel> createScheduledTransferList() {
        List<ScheduledTransferModel> list = new ArrayList<>();
        list.add(createScheduledTransferModel());
        return list;
    }

    private ScheduledTransferModel createScheduledTransferModel() {
        return ScheduledTransferModel.builder()
                .id(UUID.randomUUID())
                .amount(100L)
                .build();
    }
}