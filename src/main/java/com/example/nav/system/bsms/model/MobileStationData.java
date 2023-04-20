package com.example.nav.system.bsms.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class MobileStationData {
    @JsonAlias("mobile_station_id")
    private UUID mobileStationId;
    private float distance;
    private Timestamp timestamp;
}
