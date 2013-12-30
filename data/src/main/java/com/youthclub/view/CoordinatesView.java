package com.youthclub.view;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
public class CoordinatesView extends ViewBase {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "latitude: " + latitude + ", longitude: " + longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CoordinatesView) {
            CoordinatesView that = (CoordinatesView) obj;
            return that.latitude == latitude && that.longitude == longitude;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

