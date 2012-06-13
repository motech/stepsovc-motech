package org.wv.stepsovc.core.vo;

public class FacilityAvailability {

    private boolean isAvailable;

    private String unavailableFromDate;

    private String unavailableToDate;

    private String nextAvailableDate;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getNextAvailableDate() {
        return nextAvailableDate;
    }

    public void setNextAvailableDate(String nextAvailableDate) {
        this.nextAvailableDate = nextAvailableDate;
    }

    public String getUnavailableFromDate() {
        return unavailableFromDate;
    }

    public void setUnavailableFromDate(String unavailableFromDate) {
        this.unavailableFromDate = unavailableFromDate;
    }

    public String getUnavailableToDate() {
        return unavailableToDate;
    }

    public void setUnavailableToDate(String unavailableToDate) {
        this.unavailableToDate = unavailableToDate;
    }

    public FacilityAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;

    }

    public FacilityAvailability(boolean isAvailable, String nextAvailableDate, String unavailableFromDate, String unavailableToDate) {
        this.isAvailable = isAvailable;
        this.nextAvailableDate = nextAvailableDate;
        this.unavailableFromDate = unavailableFromDate;
        this.unavailableToDate = unavailableToDate;

    }

}
