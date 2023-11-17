package org.jala.university.dao;

import org.jala.university.model.Check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CheckDAOMock extends CheckDAO{
    private final Map<UUID, Check> checkMap = new HashMap<>();

    public CheckDAOMock() {
        super(null);
    }

    @Override
    public Check create(Check check){
        checkMap.put(check.getId(), check);
        return check;
    }
    @Override
    public List<Check> getCheckWithUUID(UUID ID){
        return checkMap.values().stream()
                .filter(check -> check.getId().equals(ID))
                .collect(Collectors.toList());
    }

}
