package com.example.nav.system.bsms.rest;

import com.example.nav.system.bsms.exception.StationNotFoundException;
import com.example.nav.system.bsms.model.BaseStation;
import com.example.nav.system.bsms.model.MobileStation;
import com.example.nav.system.bsms.model.ReportRequest;
import com.example.nav.system.bsms.model.ReportResponse;
import com.example.nav.system.bsms.service.BaseStationService;
import com.example.nav.system.bsms.service.MobileStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class BsMsRestController {
    @Autowired
    private BaseStationService bsService;

    @Autowired
    private MobileStationService msService;

    @Value("${deploy.env}")
    private String deployEnv;

    @GetMapping("/")
    public String sayHello() {
        return "The env property = " + this.deployEnv;
    }

    @GetMapping("/mobile-stations")
    public List<MobileStation> getMobileStations() {
        return msService.findAll().stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/base-stations")
    public List<BaseStation> getBaseStations() {
        return bsService.findAll().stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/location/{uuid}")
    public MobileStation getMobileStationInfo(@PathVariable UUID uuid) {
        return msService.findOne(uuid);
    }

    @PostMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportResponse> makeReport(@RequestBody ReportRequest report) {
        ReportResponse response = new ReportResponse();
        BaseStation station = bsService.findOne(report.getBaseStationId());

        // I guess the base station detectionRadiusInMeters could be uses as error_radius of
        // the new mobile station position

        report.getReports().forEach(message -> {
            try {
                MobileStation updated = msService.updateLocation(message.getMobileStationId(), station);
                response.getUpdated().add(updated);
            } catch (StationNotFoundException e) {
                response.getErrors().add(e.getMessage());
            }
        });

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
