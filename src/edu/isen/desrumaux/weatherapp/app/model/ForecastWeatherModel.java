package edu.isen.desrumaux.weatherapp.app.model;

/**
 * Model that stores data from Forecast preview, less data than WeatherModel
 */
public class ForecastWeatherModel {
    /**
     * City name
     */
    private String city;
    /**
     * Weather date
     */
    private String date;
    /**
     * Weather string
     */
    private String weather;
    /**
     * Icon day
     */
    private String var;
    /**
     * Wind speed
     */
    private float windSpeed;
    /**
     * Temperature
     */
    private float temp;

    public ForecastWeatherModel() {
        this.city = "";
        this.date = "";
        this.weather = "";
        this.var = "";
        this.city = "";
        this.windSpeed = 0.f;
        this.temp = 0.f;
    }

    public String toString() {
        return ("nom : " + this.city + " date : " + this.date + " meteo : " + this.weather + " vitesse du vent : "
                + this.windSpeed + " température : " + this.temp + " var : " + this.var);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
