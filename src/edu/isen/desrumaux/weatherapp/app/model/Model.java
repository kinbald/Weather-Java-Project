package edu.isen.desrumaux.weatherapp.app.model;

import edu.isen.desrumaux.weatherapp.app.Coordinates;
import edu.isen.desrumaux.weatherapp.net.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Model.class);

    /**
     * Location coordinates found by google API
     */
    private Coordinates mySearch;
    /**
     * List containing forecast for 5 days
     */
    private List<ForecastWeatherModel> myWeatherWeek;
    /**
     * List containing weather for the current day and history of the requested cities
     */
    private List<WeatherModel> myWeatherDay;

    public Model() {
        LOGGER.debug("Model created");
        this.myWeatherDay = new ArrayList<WeatherModel>();
        this.myWeatherWeek = new ArrayList<ForecastWeatherModel>();
    }

    /**
     * Methode that requests to google API lattiude and longitude from an adress
     *
     * @param query Address
     * @param proxy
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private void runSearch(String query, boolean proxy) throws IOException, IllegalArgumentException, ParserConfigurationException, SAXException {
        LOGGER.debug("Request coordinates for \"" + query + "\" | Proxy set to " + proxy);
        String GOOGLE_API_KEY = "AIzaSyBkfoPVii6YLNux8fcYqltqvaaHxulZuBw";
        Connection googleApiCon = new Connection(
                "https://maps.googleapis.com/maps/api/geocode/xml?address=" +
                        query.replaceAll("\\s", "+") +
                        "&key=" +
                        GOOGLE_API_KEY,
                proxy);
        mySearch = googleApiCon.getCoordinates();
        LOGGER.debug("API found : " + mySearch);
    }

    /**
     * Method that requests forecast from OpenWeatherMap
     *
     * @param query
     * @param proxy
     */
    public void getForecast(String query, boolean proxy) {
        try {
            runSearch(query, proxy);
            if (myWeatherDay.size() != 0)
                if (mySearch.equals(myWeatherDay.get(0).getCoordinates())) {
                    setChanged();
                    LOGGER.info("Notify observers");
                    notifyObservers(2);
                    return;
                }

            LOGGER.info("Creating first request for weather");

            String API_KEY = "89dc391a5578c55ed529ff5a6c2c7c04";
            Connection apiCon = new Connection(
                    "http://api.openweathermap.org/data/2.5/weather?" +
                            mySearch +
                            "&lang=fr&units=metric&mode=xml&appid=" +
                            API_KEY,
                    proxy);

            myWeatherDay.add(0, apiCon.getWeather(mySearch).get(0));

            // Keep history of 6 elements
            if (myWeatherDay.size() > 6)
                myWeatherDay.subList(6, myWeatherDay.size()).clear();

            LOGGER.info("Succeded trying to reach day weather");

            setChanged();
            LOGGER.info("Notify observers");
            notifyObservers(1);

            LOGGER.info("Creating second request for forecast");

            String DAILY_API_KEY = "94fa78df6dcce6ddf474b4a4a9b8b838";

            Connection forecastApiCon = new Connection(
                    "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                            mySearch +
                            "&lang=fr&units=metric&mode=xml&appid=" +
                            DAILY_API_KEY,
                    proxy);

            // parse week days
            LOGGER.info("Parse forecast result");
            myWeatherWeek = forecastApiCon.getForecastWeather(myWeatherDay.get(0));
            LOGGER.info("Size : " + myWeatherWeek.size());
            setChanged();
            LOGGER.info("Notify observers");
            notifyObservers(2);
        } catch (IOException e) {
            setChanged();
            LOGGER.info("ERROR during request. Please check your connection.");
            notifyObservers(4);
        } catch (IllegalArgumentException e) {
            setChanged();
            LOGGER.info("Place is not registered in google api, be more precise");
            notifyObservers(3);
        } catch (SAXException | ParserConfigurationException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public List<ForecastWeatherModel> getMyWeatherWeek() {
        return myWeatherWeek;
    }

    public List<WeatherModel> getMyWeatherDay() {
        return myWeatherDay;
    }
}
