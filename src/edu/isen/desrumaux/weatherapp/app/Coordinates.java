package edu.isen.desrumaux.weatherapp.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinates {

    private static final Logger LOGGER = LoggerFactory.getLogger(Coordinates.class);

    private float latitude;
    private float longitude;

    public Coordinates(float latitude, float longitude) {
        LOGGER.info("Coordinates object created for values  lat : " + latitude + " | long : "+ longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "lat=" + latitude + "&lon=" + longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinates)
        {
            return this.longitude == ((Coordinates) obj).longitude
                    && this.latitude == ((Coordinates) obj).latitude;
        }
        return false;
    }
}
