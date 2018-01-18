package edu.isen.desrumaux.weatherapp.app.view;

import edu.isen.desrumaux.weatherapp.app.IWeatherView;
import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class HomeView implements IWeatherView, ActionListener, Observer {
    private WeatherController controller = null;
    private JFrame frame = null;
    private JTextField field = null;

    public HomeView(WeatherController controller) {
        this.controller = controller;
        this.controller.addView(this);
        buildFrame();
    }

    private void buildFrame() {
        frame = new JFrame("Votre météo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();

        GroupLayout groupLayout = new GroupLayout(contentPane);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        contentPane.setLayout(groupLayout);
        field = new JTextField();
        JButton button = new JButton("Rechercher la météo");
        button.addActionListener(this);
        GroupLayout.SequentialGroup leftToRight = groupLayout.createSequentialGroup();

        GroupLayout.ParallelGroup columnMiddle = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        columnMiddle.addComponent(field);
        columnMiddle.addComponent(button);
        leftToRight.addGroup(columnMiddle);

        GroupLayout.SequentialGroup topToBottom = groupLayout.createSequentialGroup();
        topToBottom.addComponent(field);
        topToBottom.addComponent(button);

        groupLayout.setHorizontalGroup(leftToRight);
        groupLayout.setVerticalGroup(topToBottom);

        frame.pack();
        frame.setVisible(true);
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
        this.controller.notifyWeatherChoice(this.field.getText());
    }

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        this.controller.notifyWeatherDisplay();
    }
}
