package edu.isen.desrumaux.weatherapp.app.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class MyCardLayout extends CardLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCardLayout.class);

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        LOGGER.info("Ask for cardlayout resize");
        Component current = findCurrentComponent(parent);
        if (current != null) {
            Insets insets = parent.getInsets();
            Dimension pref = current.getPreferredSize();
            pref.width += insets.left + insets.right;
            pref.height += insets.top + insets.bottom;
            return pref;
        }
        return super.preferredLayoutSize(parent);
    }

    private Component findCurrentComponent(Container parent) {
        for (Component comp : parent.getComponents()) {
            if (comp.isVisible()) {
                return comp;
            }
        }
        return null;
    }
}
