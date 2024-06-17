package com.repository;

import com.model.entities.RubbleDumpster;
import com.model.entities.RubbleDumpsterStatus;
import com.persistence.dao.RubbleDumpsterDAO;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRubbleDumpsterDAO implements RubbleDumpsterDAO {

    private static final Map<Integer, RubbleDumpster> db = new LinkedHashMap<>();
    private static int idcounter;


    @Override
    public List<RubbleDumpster> findAll(RubbleDumpsterStatus status) {
        return db.values().stream()
                .filter(rubbleDumpster -> rubbleDumpster.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RubbleDumpster> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Integer create(RubbleDumpster rubbleDumpster) {
        idcounter ++;
        rubbleDumpster.setId(idcounter);
        db.put(idcounter, rubbleDumpster);
        return idcounter;
    }

    @Override
    public Optional<RubbleDumpster> findOne(Integer key) {
        if (db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    public Optional<RubbleDumpster> findOneBySerialNumber(Integer serialNumber) {
        return db.values().stream()
                .filter(rubbleDumpster -> rubbleDumpster.getSerialNumber().equals(serialNumber))
                .findAny();
    }

    @Override
    public List<RubbleDumpster> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(RubbleDumpster rubbleDumpster) {
        Integer id = rubbleDumpster.getId();
        if (db.containsKey(id)){
            db.replace(id, rubbleDumpster);
        return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(RubbleDumpster rubbleDumpster) {
        return deleteByKey(rubbleDumpster.getId());
    }
}
