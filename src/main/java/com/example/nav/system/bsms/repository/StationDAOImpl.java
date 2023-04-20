package com.example.nav.system.bsms.repository;


import com.example.nav.system.bsms.exception.StationNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import static java.lang.String.format;

@Repository
public class StationDAOImpl<T> implements StationDAO<T> {

    HashMap<UUID, T> stations = new HashMap<>();

    @Override
    public Collection findAll() {
        return stations.values().stream().toList();
    }

    @Override
    public T findOne(UUID id) throws StationNotFoundException {
        if (stations.containsKey(id))
            return stations.get(id);
        throw new StationNotFoundException(format("Station with id %s not found", id));
    }

    @Override
    public void put(UUID id, T item) {
        stations.put(id, item);
    }
}
