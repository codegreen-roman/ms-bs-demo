package com.example.nav.system.bsms.service;

import com.example.nav.system.bsms.exception.StationNotFoundException;
import com.example.nav.system.bsms.model.BaseStation;
import com.example.nav.system.bsms.model.MobileStation;
import com.example.nav.system.bsms.repository.StationDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class MobileStationService {

    private final StationDAO<MobileStation> dao;

    public MobileStationService(StationDAO<MobileStation> dao) {
        this.dao = dao;
        this.prefill();
    }

    public Collection<MobileStation> findAll() {
        return dao.findAll();
    }


    public void prefill() {
        for (int i = 0; i < 50; i++) {
            MobileStation mobileStation = MobileStation.builder()
                    .id(UUID.randomUUID())
                    .lastKnownX(0)
                    .lastKnownY(0)
                    .build();
            dao.put(mobileStation.getId(), mobileStation);
        }
    }

    public MobileStation findOne(UUID id) throws StationNotFoundException {
        return dao.findOne(id);
    }

    public MobileStation updateLocation(UUID id, BaseStation baseStation) throws StationNotFoundException {
        MobileStation mStation = dao.findOne(id);
        mStation.setLastKnownX(baseStation.getX());
        mStation.setLastKnownY(baseStation.getY());
        dao.put(id, mStation);

        return mStation;
    }
}
