package edu.isen.desrumaux.weatherapp.app.controller;

import edu.isen.desrumaux.weatherapp.app.model.Model;
import edu.isen.desrumaux.weatherapp.app.view.MyCardLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static edu.isen.desrumaux.weatherapp.Main.*;

public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    private Model model = null;
    private JFrame mainFrame;
    private MyCardLayout cardLayout;
    private Container container;
    private ArrayList<JPanel> myviews;
    private boolean proxy;
    private boolean receivedData;

    /**
     * Default constructor
     */
    public WeatherController(Model model) {
        LOGGER.info("Controller creation");
        this.model = model;
        mainFrame = new JFrame("Votre météo");
        LOGGER.info("Frame creation");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        myviews = new ArrayList<JPanel>();
        container = mainFrame.getContentPane();
        cardLayout = new MyCardLayout();
        container.setLayout(cardLayout);
        mainFrame.setJMenuBar(creeMenu());
    }

    private JMenuBar creeMenu() {
        LOGGER.info("Menu bar creation");
        JMenuBar m = new JMenuBar();

        JMenu menu1 = new JMenu("Fichier");
        JMenu menu2 = new JMenu("A propos");

        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.setIcon(new ImageIcon(this.getClass().getResource("/icons/quitter.png")));
        quitter.addActionListener(e -> {
            mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            System.exit(0);
        });

        JMenuItem auteurs = new JMenuItem("Auteur");
        auteurs.setIcon(new ImageIcon(this.getClass().getResource("/icons/author.png")));
        auteurs.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Réalisé par Guillaume DESRUMAUX CIR3\n2018"));

        menu1.add(quitter);
        menu2.add(auteurs);

        quitter.setAccelerator(
                KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false));

        m.add(menu1);
        m.add(menu2);
        return m;
    }

    public void addView(JPanel v, String vName) {
        LOGGER.info(vName + " added to list");
        this.container.add(v, vName);
        this.myviews.add(v);
    }

    public void displayView(String vName) {
        LOGGER.info("Display " + vName);
        cardLayout.show(container, vName);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void askFramePack()
    {
        mainFrame.pack();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void displayError() {
        LOGGER.info("Display homeview when query not possible");
        this.displayView(HOMEVIEW);
        receivedData = false;
    }

    public void displayHome() {
        LOGGER.info("Ask to display HOMEVIEW");
        displayView(HOMEVIEW);
    }

    public void notifyWeatherChoice(String city, boolean proxy) {
        LOGGER.info("User requests weather for " + city);
        if (city.length() > 2) {
            Thread getWeather = new Thread(() -> {
                LOGGER.info("New thread created to handle request");
                this.proxy = proxy;
                this.model.getForecast(city, proxy);
                if(receivedData)
                {
                    displayView(WEATHERVIEW);
                }
                receivedData = false;
                return;
            });
            getWeather.start();
            LOGGER.info("Thread started");
            displayView(LOADINGVIEW);
        }
    }

    public void notifyRefresh(String city) {
        LOGGER.info("User requests refresh weather");
        if (city.length() > 2) {
            Thread getWeather = new Thread(() -> {
                LOGGER.info("New thread created to handle request");
                this.model.getForecast(city, this.proxy);
                this.displayView(WEATHERVIEW);
                return;
            });
            getWeather.start();
            LOGGER.info("Thread started");
            displayView(LOADINGVIEW);
        }
    }

    public void setReceivedData(boolean receivedData) {
        this.receivedData = receivedData;
    }
}
