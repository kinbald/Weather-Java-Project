package edu.isen.desrumaux.weatherapp.app.model;

import edu.isen.desrumaux.weatherapp.app.Coordinates;
import edu.isen.desrumaux.weatherapp.net.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Model.class);

    private Coordinates mySearch;
    private List<ForecastWeatherModel> myWeatherWeek;
    private List<WeatherModel> myWeatherDay;

    public Model() {
        LOGGER.debug("Model created");
        this.myWeatherDay = new ArrayList<WeatherModel>();
        this.myWeatherWeek = new ArrayList<ForecastWeatherModel>();
    }

    public void runSearch(String query, boolean proxy) {
        LOGGER.debug("Request coordinates for \"" + query + "\" | Proxy set to " + proxy);
        String GOOGLE_API_KEY = "AIzaSyBkfoPVii6YLNux8fcYqltqvaaHxulZuBw";
        Connection googleApiCon = new Connection(
                "https://maps.googleapis.com/maps/api/geocode/xml?address=" +
                        query.replaceAll("\\s", "+") +
                        "&key=" +
                        GOOGLE_API_KEY,
                proxy);
        try {
            mySearch = googleApiCon.getCoordinates();
        } catch (IllegalArgumentException e) {
            LOGGER.error(query + "n'existe pas");
            mySearch = null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            mySearch = null;
        }
        LOGGER.debug("API found : " + mySearch);
    }

    public void getForecast(String query, boolean proxy) {

        runSearch(query, proxy);

        if (mySearch != null) {
            if (myWeatherDay.size() != 0)
                if (mySearch.equals(myWeatherDay.get(0).getCoordinates()))
                    return;
            // parse current day
            String API_KEY = "89dc391a5578c55ed529ff5a6c2c7c04";
            Connection apiCon = new Connection(
                    "http://api.openweathermap.org/data/2.5/weather?" +
                            mySearch +
                            "&lang=fr&units=metric&mode=xml&appid=" +
                            API_KEY,
                    proxy);

            myWeatherDay.add(0, apiCon.getWeather(mySearch).get(0));

            // keep 5 previous search + current
            if (myWeatherDay.size() > 6)
                myWeatherDay.subList(6, myWeatherDay.size()).clear();

            setChanged();
            notifyObservers(1);

            Connection forecastApiCon = new Connection(
                    "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                            mySearch +
                            "&lang=fr&units=metric&mode=xml&appid=" +
                            API_KEY,
                    proxy);

            // parse week days
            myWeatherWeek = forecastApiCon.getForecastWeather(myWeatherDay.get(0));
            setChanged();
            notifyObservers(2);
        } else {
            setChanged();
            notifyObservers(3);
        }
    }

    public List<ForecastWeatherModel> getMyWeatherWeek() {
        return myWeatherWeek;
    }

    public List<WeatherModel> getMyWeatherDay() {
        return myWeatherDay;
    }
}
