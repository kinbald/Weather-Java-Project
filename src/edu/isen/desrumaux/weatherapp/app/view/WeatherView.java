package edu.isen.desrumaux.weatherapp.app.view;

import com.sun.xml.internal.ws.util.StringUtils;
import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import edu.isen.desrumaux.weatherapp.app.model.ForecastWeatherModel;
import edu.isen.desrumaux.weatherapp.app.model.Model;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class WeatherView extends JPanel implements ActionListener, Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherView.class);

    /**
     * Used font size in the view
     */
    private static final int fontSize = 20;

    /**
     * Forecast data
     */
    private List<ForecastWeatherModel> myWeatherWeek;
    /**
     * Weather data
     */
    private List<WeatherModel> myWeatherDay;
    private WeatherController controller = null;

    /**
     * City Label
     */
    private CenteredJLabel cityLabel = null;
    /**
     * Weather icon
     */
    private CenteredJLabel cityWeatherIcon = null;
    /**
     * Temperature
     */
    private CenteredJLabel temp = null;
    /**
     * Humidity
     */
    private CenteredJLabel humidity = null;
    /**
     * Atmospheric pressure
     */
    private CenteredJLabel pressure = null;
    /**
     * String about precipitations
     */
    private CenteredJLabel precipitation = null;
    /**
     * Wind speed
     */
    private CenteredJLabel wind_speed = null;
    /**
     * String that contains wind direction
     */
    private CenteredJLabel wind_dir = null;
    /**
     * String about global weather conditions at the place
     */
    private CenteredJLabel weatherConditions = null;

    private List<CenteredJLabel> date;
    private List<CenteredJLabel> cityWeekIcon;
    private List<CenteredJLabel> weekTemp;

    /**
     * Background image
     */
    private Image backgroundImage;

    public WeatherView(WeatherController controller, String vName) {
        LOGGER.info("WeatherView created");
        this.controller = controller;
        this.controller.addView(this, vName);

        try {
            backgroundImage = ImageIO.read(this.getClass().getResource("/icons/background.jpg"));
            LOGGER.info("Background image has been read succesfully");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        date = new ArrayList<>(5);
        cityWeekIcon = new ArrayList<>(5);
        weekTemp = new ArrayList<>(5);

        for (int i = 0; i < 5; i++) {
            date.add(i, new CenteredJLabel());
            cityWeekIcon.add(i, new CenteredJLabel());
            weekTemp.add(i, new CenteredJLabel());

            date.get(i).setFont(new Font("Serif", Font.BOLD, fontSize));
            cityWeekIcon.get(i).setFont(new Font("Serif", Font.BOLD, fontSize));
            weekTemp.get(i).setFont(new Font("Serif", Font.BOLD, fontSize));
        }

        buildFrame();
    }

    private void buildFrame() {
        LOGGER.info("Building frame");

        this.setLayout(new BorderLayout());

        LOGGER.info("First row");
        CenteredJLabel upperText = new CenteredJLabel("Voici les informations météo :");
        upperText.setFont(new Font("Serif", Font.BOLD, fontSize));
        upperText.setVerticalAlignment(JLabel.CENTER);
        upperText.setBackground(new Color(0, 0, 0, 65));
        this.add(upperText, BorderLayout.NORTH);

        LOGGER.info("Init elements");
        cityLabel = new CenteredJLabel();
        cityWeatherIcon = new CenteredJLabel();
        temp = new CenteredJLabel();
        humidity = new CenteredJLabel();
        pressure = new CenteredJLabel();
        precipitation = new CenteredJLabel();
        wind_speed = new CenteredJLabel();
        wind_dir = new CenteredJLabel();
        weatherConditions = new CenteredJLabel();

        LOGGER.info("Set font for labels");
        cityLabel.setFont(new Font("Serif", Font.BOLD, fontSize));
        temp.setFont(new Font("Serif", Font.BOLD, fontSize));
        humidity.setFont(new Font("Serif", Font.BOLD, fontSize));
        pressure.setFont(new Font("Serif", Font.BOLD, fontSize));
        precipitation.setFont(new Font("Serif", Font.BOLD, fontSize));
        wind_speed.setFont(new Font("Serif", Font.BOLD, fontSize));
        weatherConditions.setFont(new Font("Serif", Font.BOLD, fontSize));

        LOGGER.info("Create buttons panel");
        JPanel buttons = new JPanel(new GridLayout(1, 2));
        buttons.setBackground(new Color(0, 0, 0, 65));

        JButton buttonRefresh = new JButton("Rafraîchir");
        buttonRefresh.addActionListener(this);
        buttons.add(buttonRefresh);
        JButton buttonGoBack = new JButton("Revenir à la recherche");
        buttonGoBack.addActionListener(this);
        buttons.add(buttonGoBack);
        this.add(buttons, BorderLayout.SOUTH);

        JPanel weekInfo = new JPanel();
        weekInfo.setBackground(new Color(0, 0, 0, 65));

        weekInfo.setLayout(new GridBagLayout());

        JLabel text = new JLabel();
        text.setForeground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel[] panelRows = new JPanel[2];
        for (int i = 0; i < 2; i++) {
            panelRows[i] = new JPanel();
            panelRows[i].setLayout(new GridBagLayout());
            panelRows[i].setOpaque(false);
        }
        gbc.insets = new Insets(5, 5, 5, 5);


        // Ligne centrale
        LOGGER.info("central Row");
        gbc.fill = GridBagConstraints.BOTH;

        // Nom ville
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        cityLabel.setForeground(Color.white);
        panelRows[0].add(cityLabel, gbc);

        // Image 2 hauteur / largeur
        gbc.gridy++;
        panelRows[0].add(cityWeatherIcon,
                gbc);

        // weather
        gbc.gridy++;
        weatherConditions.setForeground(Color.white);
        panelRows[0].add(weatherConditions, gbc);

        // weather
        gbc.gridy++;
        precipitation.setForeground(Color.white);
        panelRows[0].add(precipitation, gbc);

        // Temp (sous weather)
        gbc.gridy++;
        temp.setForeground(Color.white);
        panelRows[0].add(temp, gbc);

        // vitesse Vent
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        wind_speed.setForeground(Color.white);
        panelRows[0].add(wind_speed, gbc);

        // img vents
        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panelRows[0].add(wind_dir,
                gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        humidity.setForeground(Color.white);
        panelRows[0].add(humidity, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        pressure.setForeground(Color.white);
        panelRows[0].add(pressure, gbc);

        LOGGER.info("Last Row");
        int k = 0;

        for (int i = 1; i < 5; i++) {
            // add separator
            gbc.gridwidth = 1;
            gbc.gridheight = GridBagConstraints.REMAINDER;// ou 3
            gbc.gridy = 0;
            gbc.gridx = k;
            JSeparator jSep = new JSeparator(SwingConstants.VERTICAL);
            panelRows[1].add(jSep, gbc);

            // add date
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.gridx = ++k;
            date.get(i).setForeground(Color.white);
            panelRows[1].add(date.get(i), gbc);

            // add img
            gbc.gridy++;
            panelRows[1].add(cityWeekIcon.get(i), gbc);

            // add temp
            gbc.gridy++;
            gbc.gridheight = GridBagConstraints.REMAINDER;
            weekTemp.get(i).setForeground(Color.white);
            panelRows[1].add(weekTemp.get(i), gbc);
            k++;
        }
        gbc.gridy = 0;
        gbc.gridx = k++;
        JSeparator jSep2 = new JSeparator(SwingConstants.VERTICAL);
        panelRows[1].add(jSep2, gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        weekInfo.add(panelRows[0], gbc);

        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridy++;

        weekInfo.add(panelRows[1], gbc);
        LOGGER.info("Validate view");
        weekInfo.revalidate();

        this.add(weekInfo, BorderLayout.CENTER);
        LOGGER.info("View created");
    }

    private void displayWeather() {
        LOGGER.info("Update all the values from the Model");
        cityLabel.setText(myWeatherDay.get(0).getCity());
        cityWeatherIcon.setIcon(new ImageIcon(this.getClass().getResource("/icons/" + this.myWeatherDay.get(0).getWeather_code() + ".png")));
        weatherConditions.setText(StringUtils.capitalize(myWeatherDay.get(0).getWeatherConditions()));
        temp.setText(myWeatherDay.get(0).getTemperature_max() + " °C / " + myWeatherDay.get(0).getTemperature_min() + " °C");
        humidity.setText("Humidité : " + myWeatherDay.get(0).getHumidity() + " %");
        pressure.setText("Baromètre : " + myWeatherDay.get(0).getPressure() + " hPa");
        if (myWeatherDay.get(0).getPrecipitation_mode().equals("no")) {
            precipitation.setText("Pas de précipitations");
        } else {
            precipitation.setText(myWeatherDay.get(0).getPrecipitation_mode() + " " + myWeatherDay.get(0).getPrecipitation() + " %");
        }
        if (!Objects.equals(this.myWeatherDay.get(0).getWind_direction_name(), "")) {
            wind_dir.setIcon(new ImageIcon(this.getClass().getResource("/wind/" + this.myWeatherDay.get(0).getWind_direction_name() + ".png")));
        }
        wind_speed.setText(String.valueOf("Vent : " + myWeatherDay.get(0).getWind_speed()) + " mps");
        for (int i = 1; i < 5; i++) {

            LOGGER.info("Size :" + myWeatherWeek.size());
            date.get(i).setText(myWeatherWeek.get(i).getDate());
            cityWeekIcon.get(i).setIcon(new ImageIcon(this.getClass().getResource("/icons/" + this.myWeatherWeek.get(i).getVar() + ".png")));
            weekTemp.get(i).setText(String.valueOf(this.myWeatherWeek.get(i).getTemp()) + " °C");
        }
        this.controller.askFramePack();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        LOGGER.info("Display background image");
        // Draw the background image.
        g.drawImage(backgroundImage, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOGGER.info("Button action asked from " + e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Revenir à la recherche":
                this.controller.displayHome();
                break;
            case "Rafraîchir":
                this.controller.notifyRefresh(cityLabel.getText());
                break;
        }
    }

    @Override
    public void update(Observable obs, Object obj) {
        LOGGER.info("Update received from observable");
        if (obj instanceof Integer) {
            int k = (Integer) obj;
            switch (k) {
                case 1:
                    LOGGER.info("Code means that dayweather has been created");
                    myWeatherDay = ((Model) obs).getMyWeatherDay();
                    break;
                case 2:
                    LOGGER.info("Code means that forecast has been created");
                    myWeatherWeek = ((Model) obs).getMyWeatherWeek();
                    LOGGER.info("View is ready to be updated");
                    this.controller.setReceivedData(true);
                    displayWeather();
                    break;
            }
        }
    }
}
