package edu.isen.desrumaux.weatherapp.app.view;

import javax.swing.*;

public class CenteredJLabel extends JLabel {

    public CenteredJLabel() {
        super();
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public CenteredJLabel(String text) {
        super(text);
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public CenteredJLabel(Icon image) {
        super(image);
        this.setHorizontalAlignment(JLabel.CENTER);
    }
}
