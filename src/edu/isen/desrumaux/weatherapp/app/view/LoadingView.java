package edu.isen.desrumaux.weatherapp.app.view;

import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class LoadingView extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadingView.class);

    public LoadingView(WeatherController controller, String vName) {
        LOGGER.info("LoadingView created with name " + vName);
        controller.addView(this, vName);
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        buildFrame();
    }

    private void buildFrame() {
        LOGGER.info("Building LoadingView Panel");
        JLabel jLabel = new JLabel("Chargement des données météo ...");
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setBackground(Color.WHITE);
        jPanel.add(jLabel);
        this.add(jPanel, BorderLayout.NORTH);
        LOGGER.info("Adding loading gif");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/loading/load.gif")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(300, 300,  Image.SCALE_DEFAULT); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        JLabel icon = new JLabel(imageIcon);
        this.add(icon, BorderLayout.SOUTH);
    }
}
