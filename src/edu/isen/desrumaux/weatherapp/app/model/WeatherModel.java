package edu.isen.desrumaux.weatherapp.app.model;

import edu.isen.desrumaux.weatherapp.app.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;

/**
 * Model that contains weather data from regular info
 */
public class WeatherModel extends Observable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherModel.class);

    /**
     * City namle
     */
    private String city;
    /**
     * City ID
     */
    private int city_id;
    /**
     * Weather string
     */
    private String weather_code;
    /**
     * Current temperature
     */
    private float temperature;
    private float temperature_min;
    private float temperature_max;
    private float humidity;
    private float pressure;
    private float wind_speed;
    private float wind_direction;
    private String wind_direction_name;
    private float clouds;
    private String clouds_name;
    private float visibility;
    private float precipitation;
    private String precipitation_mode;
    /**
     * Geocoordinates for this place
     */
    private Coordinates coordinates;
    /**
     * Small string to explain weather
     */
    private String weatherConditions;

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditions) {
        this.weatherConditions = weatherConditions;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(String weather_code) {
        this.weather_code = weather_code;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(float temperature_min) {
        this.temperature_min = temperature_min;
    }

    public float getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(float temperature_max) {
        this.temperature_max = temperature_max;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public float getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(float wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_direction_name() {
        return wind_direction_name;
    }

    public void setWind_direction_name(String wind_direction_name) {
        this.wind_direction_name = wind_direction_name;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public String getClouds_name() {
        return clouds_name;
    }

    public void setClouds_name(String clouds_name) {
        this.clouds_name = clouds_name;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public String getPrecipitation_mode() {
        return precipitation_mode;
    }

    public void setPrecipitation_mode(String precipitation_mode) {
        this.precipitation_mode = precipitation_mode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Météo pour " + city + " (" + city_id + ") : \n" +
                " Température : " + temperature + " Min : " + temperature_min + " Max : " + temperature_max + "\n" +
                " Humidité : " + humidity + " %\n" +
                " Pression atmosphérique : " + pressure + " hPa\n" +
                " Vent : " + wind_speed + " mps " + wind_direction_name + " " + wind_direction + "\n" +
                " Couverture : " + clouds_name + " " + clouds + " %\n" +
                " Visibilité  : " + visibility + " %\n" +
                " Précipations : " + precipitation_mode + precipitation;
    }
}
