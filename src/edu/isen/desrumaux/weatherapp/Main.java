package edu.isen.desrumaux.weatherapp;

import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import edu.isen.desrumaux.weatherapp.app.model.Model;
import edu.isen.desrumaux.weatherapp.app.view.HomeView;
import edu.isen.desrumaux.weatherapp.app.view.LoadingView;
import edu.isen.desrumaux.weatherapp.app.view.WeatherView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    final public static String WEATHERVIEW = "Weather View";
    final public static String HOMEVIEW = "Home View";
    final public static String LOADINGVIEW = "Loading View";

    public static void main(String[] args) {

        Model model = new Model();

        javax.swing.SwingUtilities.invokeLater(() -> {
            LOGGER.debug("Create Window in different thread");
            WeatherController controller = new WeatherController(model);
            HomeView homeView = new HomeView(controller, HOMEVIEW);
            model.addObserver(homeView);
            WeatherView weatherView = new WeatherView(controller, WEATHERVIEW);
            model.addObserver(weatherView);
            LoadingView loadingView = new LoadingView(controller, LOADINGVIEW);

            controller.displayView(HOMEVIEW);
        });
    }
}