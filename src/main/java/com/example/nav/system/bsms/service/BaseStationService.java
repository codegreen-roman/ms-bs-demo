package com.example.nav.system.bsms.service;

import com.example.nav.system.bsms.exception.StationNotFoundException;
import com.example.nav.system.bsms.model.BaseStation;
import com.example.nav.system.bsms.repository.StationDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;

@Service
public class BaseStationService {

    private final StationDAO<BaseStation> dao;

    public BaseStationService(StationDAO<BaseStation> dao){
        this.dao = dao;
        this.prefill();
    }


    public Collection<BaseStation> findAll() {
        return dao.findAll();
    }

    private void prefill() {
        for (int i = 0; i < 50; i++) {
            BaseStation baseStation = BaseStation.builder()
                    .id(UUID.randomUUID())
                    .x(ThreadLocalRandom.current().nextInt(0, 100))
                    .y(ThreadLocalRandom.current().nextInt(0, 100))
                    .detectionRadiusInMeters(2)
                    .name(format("Base Station %d", i))
                    .build();
            dao.put(baseStation.getId(), baseStation);
        }
    }

    public BaseStation findOne(UUID id) throws StationNotFoundException {
        return dao.findOne(id);
    }
}
