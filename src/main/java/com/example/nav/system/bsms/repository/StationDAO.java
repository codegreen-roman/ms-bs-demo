package com.example.nav.system.bsms.repository;

import com.example.nav.system.bsms.exception.StationNotFoundException;

import java.util.Collection;
import java.util.UUID;

public interface StationDAO<T> {
    Collection<T> findAll();
    T findOne(UUID id) throws StationNotFoundException;
    void put(UUID id, T item);
}
