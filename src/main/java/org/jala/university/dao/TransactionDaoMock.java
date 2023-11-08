package org.jala.university.dao;

import jakarta.persistence.EntityManager;

public class TransactionDaoMock extends TransactionDAO{
    public TransactionDaoMock(EntityManager entityManager) {
        super(entityManager);
    }
}
