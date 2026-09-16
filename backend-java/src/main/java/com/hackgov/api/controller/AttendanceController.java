package com.hackgov.api.controller;

import com.hackgov.api.data.FictitiousDataStore;
import com.hackgov.api.model.AttendancePoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoint de postos de atendimento presencial simulados. */
@RestController
@RequestMapping("/api/attendance-points")
public class AttendanceController {

    private final FictitiousDataStore store;

    public AttendanceController(FictitiousDataStore store) {
        this.store = store;
    }

    @GetMapping
    public List<AttendancePoint> listAttendancePoints() {
        return store.attendancePoints();
    }
}
