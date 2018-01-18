package edu.isen.desrumaux.weatherapp.app;

public class Coordonates {
    private float latitude;
    private float longitude;

    public Coordonates(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "lat=" + latitude + "&lon=" + longitude;
    }
}
