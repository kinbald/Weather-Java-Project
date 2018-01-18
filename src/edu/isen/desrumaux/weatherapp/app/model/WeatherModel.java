package edu.isen.desrumaux.weatherapp.app.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.util.Observable;

public class WeatherModel extends Observable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherModel.class);

    private String city;
    private int city_id;
    private int weather_code;
    private float temperature;
    private float temperature_min;
    private float temperature_max;
    private float humidty;
    private int pressure;
    private float wind_speed;
    private int wind_direction;
    private String wind_direction_name;
    private int clouds;
    private String clouds_name;
    private int visibility;
    private int precipitation;
    private String precipitation_mode;

    @Override
    public String toString() {
        return "Météo pour " + city + " (" + city_id + ") : \n" +
                " Température : " + temperature + " Min : " + temperature_min + " Max : " + temperature_max + "\n" +
                " Humidité : " + humidty + " %\n" +
                " Pression atmosphérique : " + pressure + " hPa\n" +
                " Vent : " + wind_speed + " mps " + wind_direction_name + " " + wind_direction + "\n" +
                " Couverture : " + clouds_name + " " + clouds + " %\n" +
                " Visibilité  : " + visibility + " %\n" +
                " Précipations : " + precipitation_mode + precipitation;
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

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
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

    public float getHumidty() {
        return humidty;
    }

    public void setHumidty(float humidty) {
        this.humidty = humidty;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(int wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_direction_name() {
        return wind_direction_name;
    }

    public void setWind_direction_name(String wind_direction_name) {
        this.wind_direction_name = wind_direction_name;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public String getClouds_name() {
        return clouds_name;
    }

    public void setClouds_name(String clouds_name) {
        this.clouds_name = clouds_name;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public String getPrecipitation_mode() {
        return precipitation_mode;
    }

    public void setPrecipitation_mode(String precipitation_mode) {
        this.precipitation_mode = precipitation_mode;
    }

    public void convertElementToWeather(Element weatherNode) {
        this.setCity(weatherNode.getElementsByTagName("city").item(0).getAttributes().getNamedItem("name").getTextContent());
        this.setCity_id(Integer.parseInt(weatherNode.getElementsByTagName("city").item(0).getAttributes().getNamedItem("id").getTextContent()));

        this.setWeather_code(Integer.parseInt(weatherNode.getElementsByTagName("weather").item(0).getAttributes().getNamedItem("number").getTextContent()));

        this.setTemperature(Float.parseFloat(weatherNode.getElementsByTagName("temperature").item(0).getAttributes().item(3).getTextContent()));
        this.setTemperature_min(Float.parseFloat(weatherNode.getElementsByTagName("temperature").item(0).getAttributes().item(1).getTextContent()));
        this.setTemperature_max(Float.parseFloat(weatherNode.getElementsByTagName("temperature").item(0).getAttributes().item(0).getTextContent()));

        this.setHumidty(Float.parseFloat(weatherNode.getElementsByTagName("humidity").item(0).getAttributes().item(1).getTextContent()));

        this.setPressure(Integer.parseInt(weatherNode.getElementsByTagName("pressure").item(0).getAttributes().item(1).getTextContent()));

        this.setWind_direction(Integer.parseInt(weatherNode.getElementsByTagName("direction").item(0).getAttributes().item(2).getTextContent()));
        this.setWind_direction_name(weatherNode.getElementsByTagName("direction").item(0).getAttributes().item(1).getTextContent());
        this.setWind_speed(Float.parseFloat(weatherNode.getElementsByTagName("speed").item(0).getAttributes().item(1).getTextContent()));


        this.setClouds(Integer.parseInt(weatherNode.getElementsByTagName("clouds").item(0).getAttributes().item(1).getTextContent()));
        this.setClouds_name(weatherNode.getElementsByTagName("clouds").item(0).getAttributes().item(0).getTextContent());

        this.setVisibility(Integer.parseInt(weatherNode.getElementsByTagName("visibility").item(0).getAttributes().item(0).getTextContent()));

        this.setPrecipitation_mode(weatherNode.getElementsByTagName("precipitation").item(0).getAttributes().getNamedItem("mode").getTextContent());
        if (!this.getPrecipitation_mode().equals("no")) {
            this.setPrecipitation(Integer.parseInt(weatherNode.getElementsByTagName("precipitation").item(0).getAttributes().getNamedItem("value").getTextContent()));
        }

        setChanged();
        notifyObservers(this);
    }
}
