package com.hackgov.api.model;

/** Posto de atendimento presencial simulado, para quem não consegue resolver online. */
public class AttendancePoint {

    private String name;
    private String address;
    private String hours;
    private float distanceKm;
    private boolean needsAppointment;
    private String[] services;

    public AttendancePoint() {
    }

    public AttendancePoint(String name, String address, String hours, float distanceKm,
                            boolean needsAppointment, String[] services) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.distanceKm = distanceKm;
        this.needsAppointment = needsAppointment;
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getHours() {
        return hours;
    }

    public float getDistanceKm() {
        return distanceKm;
    }

    public boolean isNeedsAppointment() {
        return needsAppointment;
    }

    public String[] getServices() {
        return services;
    }
}
