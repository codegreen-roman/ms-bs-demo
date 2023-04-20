package com.example.nav.system.bsms.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Data
public class ReportResponse {
    private Collection<MobileStation> updated;
    private Collection<String> errors;

    public ReportResponse(){
        this.updated = new HashSet<>();
        this.errors = new ArrayList<>();
    }

}
