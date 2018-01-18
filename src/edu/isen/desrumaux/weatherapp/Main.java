package edu.isen.desrumaux.weatherapp;

import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;
import edu.isen.desrumaux.weatherapp.app.view.HomeView;
import edu.isen.desrumaux.weatherapp.app.view.WeatherView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Hello");

        WeatherModel model = new WeatherModel();
        WeatherController controller = new WeatherController(model);
        HomeView homeView = new HomeView(controller);
        model.addObserver(homeView);

        controller.displayHomeView();
    }
}