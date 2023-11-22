package org.jala.university.domain;

import org.jala.university.model.Check;

import java.util.List;
import java.util.UUID;

public interface CheckModule {
    void createCheck(Check check);
    List<Check> findCheckWithUUID(UUID checkId);
    boolean hasSufficientFunds(Check check);

}
