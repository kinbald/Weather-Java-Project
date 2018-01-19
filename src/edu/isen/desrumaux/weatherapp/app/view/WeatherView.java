package edu.isen.desrumaux.weatherapp.app.view;

import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import edu.isen.desrumaux.weatherapp.app.model.ForecastWeatherModel;
import edu.isen.desrumaux.weatherapp.app.model.Model;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class WeatherView extends JPanel implements ActionListener, Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherView.class);

    private List<ForecastWeatherModel> myWeatherWeek;
    private List<WeatherModel> myWeatherDay;
    private WeatherController controller = null;
    private JLabel cityLabel = null;
    private JButton buttonRefresh = null;
    private JButton buttonGoBack = null;

    private JLabel temp;
    private JLabel humidity;
    private JLabel pressure;
    private JLabel precipitation;
    private JLabel wind_speed;
    private JLabel wind_dir;

    public WeatherView(WeatherController controller, String vName) {
        this.controller = controller;
        this.controller.addView(this, vName);
        this.setPreferredSize(new Dimension(800, 800));
        buildFrame();
    }

    private void buildFrame() {
        this.setLayout(new BorderLayout());

        cityLabel = new JLabel();
        this.add(cityLabel, BorderLayout.NORTH);

        JPanel dayInfo = new JPanel(new GridLayout(3, 2));

        temp = new JLabel();
        humidity = new JLabel();
        pressure = new JLabel();
        precipitation = new JLabel();
        wind_speed = new JLabel();
        wind_dir = new JLabel();

        dayInfo.add(temp);
        dayInfo.add(humidity);
        dayInfo.add(pressure);
        dayInfo.add(precipitation);
        dayInfo.add(wind_dir);
        dayInfo.add(wind_speed);

        this.add(dayInfo, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 2));

        buttonRefresh = new JButton("Rafraîchir");
        buttonRefresh.addActionListener(this);
        buttons.add(buttonRefresh);
        buttonGoBack = new JButton("Revenir à la recherche");
        buttonGoBack.addActionListener(this);
        buttons.add(buttonGoBack);
        this.add(buttons, BorderLayout.SOUTH);
    }

    private void displayWeather() {
        cityLabel.setText(myWeatherDay.get(0).getCity());
        cityLabel.setIcon(new ImageIcon(this.getClass().getResource("/icons/" + this.myWeatherDay.get(0).getWeather_code() + ".png")));
        temp.setText(myWeatherDay.get(0).getTemperature_max() + " ° / " + myWeatherDay.get(0).getTemperature_min() + " °");
        humidity.setText(myWeatherDay.get(0).getHumidity() + " %");
        pressure.setText(myWeatherDay.get(0).getPressure() + " hPa");
        if (myWeatherDay.get(0).getPrecipitation_mode().equals("no")) {
            precipitation.setText("Pas de précipitations");
        } else {
            precipitation.setText(myWeatherDay.get(0).getPrecipitation_mode() + " " + myWeatherDay.get(0).getPrecipitation() + " %");
        }
        if (!Objects.equals(this.myWeatherDay.get(0).getWind_direction_name(), "")) {
            wind_dir.setIcon(new ImageIcon(this.getClass().getResource("/wind/" + this.myWeatherDay.get(0).getWind_direction_name() + ".png")));
        }

        wind_speed.setText(String.valueOf(myWeatherDay.get(0).getWind_speed()));
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
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

        if (obj instanceof Integer) {
            int k = (Integer) obj;

            switch (k) {
                case 1:
                    myWeatherDay = ((Model) obs).getMyWeatherDay();
                    break;
                case 2:
                    myWeatherWeek = ((Model) obs).getMyWeatherWeek();
                    displayWeather();
                    break;
            }
        }
    }
}
