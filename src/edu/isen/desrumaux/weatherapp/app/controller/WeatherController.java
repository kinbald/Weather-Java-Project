package edu.isen.desrumaux.weatherapp.app.controller;

import edu.isen.desrumaux.weatherapp.app.model.Model;
import edu.isen.desrumaux.weatherapp.app.view.MyCardLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static edu.isen.desrumaux.weatherapp.Main.*;

public class WeatherController {

    private Model model = null;
    private JFrame mainFrame;
    private MyCardLayout cardLayout;
    private Container container;
    private ArrayList<JPanel> myviews;
    private boolean proxy;

    /**
     * Default constructor
     */
    public WeatherController(Model model) {
        this.model = model;
        mainFrame = new JFrame("Votre météo");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        myviews = new ArrayList<JPanel>();
        container = mainFrame.getContentPane();
        cardLayout = new MyCardLayout();
        container.setLayout(cardLayout);
    }

    public void addView(JPanel v, String vName) {
        this.container.add(v, vName);
        this.myviews.add(v);
    }

    public void displayView(String vName) {
        cardLayout.show(container, vName);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void displayError() {
        this.displayView(HOMEVIEW);
    }

    public void displayHome() {
        displayView(HOMEVIEW);
    }

    public void notifyWeatherChoice(String city, boolean proxy) {
        if (city.length() > 2) {
            Thread getWeather = new Thread(() -> {
                this.proxy = proxy;
                this.model.getForecast(city, proxy);
                displayView(WEATHERVIEW);
            });
            getWeather.start();
            displayView(LOADINGVIEW);
        }
    }

    public void notifyRefresh(String city) {
        if (city.length() > 2) {
            Thread getWeather = new Thread(() -> {
                this.model.getForecast(city, this.proxy);
                this.displayView(WEATHERVIEW);
            });
            getWeather.start();
            displayView(LOADINGVIEW);
        }
    }
}
