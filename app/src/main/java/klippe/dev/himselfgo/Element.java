package klippe.dev.himselfgo;

/**
 * Created by user on 17.01.2017.
 */

public class Element {

    public String path;
    public int step;
    public double latitude;
    public double longtitude;

    public Element(String path, int step, double latitude, double longtitude) {
        this.path = path;
        this.step = step;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getPath() {
        return path;
    }

    public int getStep() {
        return step;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
