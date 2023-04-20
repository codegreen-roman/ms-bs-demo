package com.example.nav.system.bsms;

import com.example.nav.system.bsms.exception.StationNotFoundException;
import com.example.nav.system.bsms.model.BaseStation;
import com.example.nav.system.bsms.repository.StationDAO;
import com.example.nav.system.bsms.service.BaseStationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BaseStationServiceTest {

    @Mock
    StationDAO<BaseStation> bsDao;
    BaseStationService bsService;

    Map<UUID, BaseStation> values;

    @Before
    public void init() {
        bsService = new BaseStationService(bsDao);
        values = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            BaseStation baseStation = BaseStation.builder()
                    .id(UUID.randomUUID())
                    .x(ThreadLocalRandom.current().nextInt(0, 100))
                    .y(ThreadLocalRandom.current().nextInt(0, 100))
                    .detectionRadiusInMeters(2)
                    .name(format("Base Station %d", i))
                    .build();
            values.put(baseStation.getId(), baseStation);
        }

        Mockito.when(bsDao.findAll()).thenReturn(values.values());
    }

    @Test
    public void thatSizeOfPrefilledStationsIsCorrect() {
        assertEquals(50, bsService.findAll().size());
    }

    @Test(expected = StationNotFoundException.class)
    public void thatFindOneWillThrow() {
        UUID id = UUID.fromString("f19fa326-664f-4bf7-b484-4ba1f056d076");
        Mockito.when(bsDao.findOne(id)).thenThrow(new StationNotFoundException("Error"));
        bsService.findOne(id);
    }

}
