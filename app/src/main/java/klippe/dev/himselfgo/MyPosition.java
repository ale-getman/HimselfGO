package klippe.dev.himselfgo;

public class MyPosition {

    private static MyPosition mInstance = null;
    private double latitude, longtitude;

    public static MyPosition getInstance() {
        if(mInstance == null) {
            mInstance = new MyPosition();
        }
        return mInstance;
    }

    private MyPosition() {
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }
}
