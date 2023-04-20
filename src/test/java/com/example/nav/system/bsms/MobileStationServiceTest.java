package com.example.nav.system.bsms;

import com.example.nav.system.bsms.exception.StationNotFoundException;
import com.example.nav.system.bsms.model.BaseStation;
import com.example.nav.system.bsms.model.MobileStation;
import com.example.nav.system.bsms.repository.StationDAO;
import com.example.nav.system.bsms.service.BaseStationService;
import com.example.nav.system.bsms.service.MobileStationService;
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
public class MobileStationServiceTest {
    MobileStationService msService;
    @Mock
    StationDAO<MobileStation> msDao;
    BaseStationService bsService;
    @Mock
    StationDAO<BaseStation> bsDao;

    Map<UUID, MobileStation> values;
    Map<UUID, BaseStation> bsValues;

    @Before
    public void init() {
        msService = new MobileStationService(msDao);
        bsService = new BaseStationService(bsDao);
        values = new HashMap<>();
        bsValues = new HashMap<>();

        for (int i = 0; i < 50; i++) {
            MobileStation mobileStation = MobileStation.builder()
                    .id(UUID.randomUUID())
                    .lastKnownX(0)
                    .lastKnownY(0)
                    .build();
            values.put(mobileStation.getId(), mobileStation);

            BaseStation baseStation = BaseStation.builder()
                    .id(UUID.randomUUID())
                    .x(ThreadLocalRandom.current().nextInt(0, 100))
                    .y(ThreadLocalRandom.current().nextInt(0, 100))
                    .detectionRadiusInMeters(2)
                    .name(format("Base Station %d", i))
                    .build();
            bsValues.put(baseStation.getId(), baseStation);
        }

        Mockito.when(msDao.findAll()).thenReturn(values.values());
        Mockito.when(bsDao.findAll()).thenReturn(bsValues.values());
    }

    @Test
    public void thatSizeOfPrefilledStationsIsCorrect() {
        assertEquals(50, msService.findAll().size());
    }

    @Test
    public void thatAllInitialCoordinatesAreCorrect() {

        MobileStation station = msService.findAll().stream().findAny().get();
        assertEquals(0, station.getLastKnownX(), 0.0);
        assertEquals(0, station.getLastKnownY(), 0.0);

    }

    @Test(expected = StationNotFoundException.class)
    public void thatFindOneWillThrow() {
        UUID id = UUID.fromString("f19fa326-664f-4bf7-b484-4ba1f056d076");
        Mockito.when(msDao.findOne(id)).thenThrow(new StationNotFoundException("Error message"));
        msService.findOne(id);
    }

    @Test
    public void thatNewStationCoordinatesAreUp() {
        MobileStation station = msService.findAll().stream().findAny().get();
        BaseStation bsStation = bsService.findAll().stream().findAny().get();

        Mockito.when(msDao.findOne(station.getId())).thenReturn(station);

        msService.updateLocation(station.getId(), bsStation);

        assertEquals(bsStation.getX(), msService.findOne(station.getId()).getLastKnownX() , 0.0);
        assertEquals(bsStation.getY(), msService.findOne(station.getId()).getLastKnownY() , 0.0);

    }
}
