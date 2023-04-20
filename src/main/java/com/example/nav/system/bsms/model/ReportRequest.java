package com.example.nav.system.bsms.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
public class ReportRequest {
    @JsonAlias("base_station_id")
    private UUID baseStationId;
    private Collection<MobileStationData> reports;
}
