package edu.isen.desrumaux.weatherapp.app.view;

import edu.isen.desrumaux.weatherapp.app.IWeatherView;
import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

public class WeatherView implements IWeatherView, ActionListener, Observer {
    private WeatherController controller = null;
    private JFrame frame = null;
    private JPanel contentPane = null;
    private JFormattedTextField field = null;
    private JButton button = null;
    private NumberFormat format = null;

    public WeatherView(WeatherController controller, WeatherModel model) {
        this.controller = controller;
        this.controller.addView(this);
        buildFrame(model);
    }

    private void buildFrame(WeatherModel model) {
        frame = new JFrame("Votre météo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.add(new JTextField(model.getCity()));
        button = new JButton("Mettre à jour");
        button.addActionListener(this);
        contentPane.add(button);
        frame.setContentPane(contentPane);
        frame.pack();
    }

    public void close() {
        frame.dispose();
    }

    public void display() {
        frame.setVisible(true);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Integer) field.setValue((Integer) arg);
        System.out.println("[FieldVolumeView] : update");
    }
}
