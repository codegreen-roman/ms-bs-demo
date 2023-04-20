package com.example.nav.system.bsms.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationErrorResponse {
    private int status;
    private String message;
}
