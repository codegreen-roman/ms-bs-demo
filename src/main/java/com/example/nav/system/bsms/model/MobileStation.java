package com.example.nav.system.bsms.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MobileStation {
    private UUID id;
    private float lastKnownX;
    private float lastKnownY;
}
