package org.wv.stepsovc.web.vo;

public class FacilityAvailability {

    private boolean isAvailable;

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

    public FacilityAvailability(boolean available, String nextAvailableDate) {
        isAvailable = available;
        this.nextAvailableDate = nextAvailableDate;
    }

}
