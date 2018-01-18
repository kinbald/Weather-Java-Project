package edu.isen.desrumaux.weatherapp.app.controller;

import edu.isen.desrumaux.weatherapp.app.Coordonates;
import edu.isen.desrumaux.weatherapp.app.IWeatherView;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;
import edu.isen.desrumaux.weatherapp.app.view.WeatherView;
import edu.isen.desrumaux.weatherapp.net.Connection;

import java.util.ArrayList;
import java.util.List;

public class WeatherController {

    private WeatherModel model = null;
    private List<IWeatherView> myviews;

    /**
     * Default constructor
     */
    public WeatherController(WeatherModel model) {
        this.model = model;
        myviews = new ArrayList<IWeatherView>();
    }

    public void addView(IWeatherView v) {
        this.myviews.add(v);
    }

    public void displayHomeView() {
        myviews.get(0).display();
    }

    public void displayWeatherView() {
        myviews.get(1).display();
    }

    public void closeViews() {
        for (IWeatherView wv : myviews) {
            wv.close();
        }
    }

    public void notifyWeatherChoice(String city) {

        String GOOGLE_API_KEY = "AIzaSyBkfoPVii6YLNux8fcYqltqvaaHxulZuBw";
        Connection googleApiCon = new Connection("https://maps.googleapis.com/maps/api/geocode/xml?address="+ city.replaceAll("\\s","+") + "&key=" + GOOGLE_API_KEY, false);
        Coordonates coordonates = googleApiCon.getCoordonates();

        String API_KEY = "89dc391a5578c55ed529ff5a6c2c7c04";
        Connection apiCon = new Connection("http://api.openweathermap.org/data/2.5/weather?" + coordonates +"&lang=fr&units=metric&mode=xml&appid=" + API_KEY, false);
        apiCon.getWeather(model);
    }

    public void notifyWeatherDisplay() {
        closeViews();
        model.addObserver(new WeatherView(this, model));
        displayWeatherView();
    }
}
