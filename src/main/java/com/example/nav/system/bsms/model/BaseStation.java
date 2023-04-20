package com.example.nav.system.bsms.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BaseStation {
    private UUID id;
    private String name;
    private float x;
    private float y;
    private float detectionRadiusInMeters;
}
