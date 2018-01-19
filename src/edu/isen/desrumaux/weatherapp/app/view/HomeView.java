package edu.isen.desrumaux.weatherapp.app.view;

import edu.isen.desrumaux.weatherapp.app.controller.WeatherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class HomeView extends JPanel implements ActionListener, Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeView.class);

    private WeatherController controller = null;
    /**
     * Query field
     */
    private JTextField field = null;
    /**
     * Query button
     */
    private JButton button = null;
    /**
     * Proxy selected
     */
    private boolean proxy = false;

    public HomeView(WeatherController controller, String vName) {
        LOGGER.info("HomeView created with name:" + vName);
        this.controller = controller;
        this.controller.addView(this, vName);
        buildFrame();
    }

    private void buildFrame() {
        LOGGER.info("Building frame");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        this.setLayout(groupLayout);
        JLabel jLabel = new JLabel("Veuillez entrer une ville :");
        field = new JTextField(20);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(field);

        JCheckBox proxyCheck = new JCheckBox("Utiliser le proxy isen", proxy);
        proxyCheck.addActionListener(this);

        button = new JButton("Rechercher la météo");
        button.addActionListener(this);

        //Act as button when ENTER key is pressed
        field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });

        LOGGER.info("Add ISEN logo");
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/logo/logoISEN.png")); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(200, 88, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        LOGGER.info("Add OpenWeatherMap logo");
        ImageIcon imageIcon2 = new ImageIcon(this.getClass().getResource("/logo/logoOWM.png")); // load the image to a imageIcon
        image = imageIcon2.getImage(); // transform it
        newimg = image.getScaledInstance(291, 28, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon2 = new ImageIcon(newimg);  // transform it back

        JLabel logo1 = new JLabel(imageIcon);
        JLabel logo2 = new JLabel(imageIcon2);

        LOGGER.info("Create GroupLayout");
        GroupLayout.SequentialGroup leftToRight = groupLayout.createSequentialGroup();

        GroupLayout.ParallelGroup columnMiddle = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        columnMiddle.addComponent(jLabel);
        columnMiddle.addComponent(panel);
        columnMiddle.addComponent(proxyCheck);
        columnMiddle.addComponent(button);
        columnMiddle.addComponent(logo1);
        columnMiddle.addComponent(logo2);
        leftToRight.addGroup(columnMiddle);

        GroupLayout.SequentialGroup topToBottom = groupLayout.createSequentialGroup();
        topToBottom.addComponent(jLabel);
        topToBottom.addComponent(panel);
        topToBottom.addComponent(proxyCheck);
        topToBottom.addComponent(button);
        topToBottom.addComponent(logo1);
        topToBottom.addComponent(logo2);

        groupLayout.setHorizontalGroup(leftToRight);
        groupLayout.setVerticalGroup(topToBottom);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JCheckBox) {
            proxy = ((JCheckBox) e.getSource()).isSelected();
        } else {
            this.controller.notifyWeatherChoice(this.field.getText(), proxy);
        }
    }

    @Override
    public void update(Observable obs, Object obj) {
        LOGGER.info("Recieved observable update");
        if (obj instanceof Integer) {
            int k = (Integer) obj;
            switch (k) {
                case 3:
                    LOGGER.info("Code means that request is not possible");
                    String e = this.field.getText();
                    this.field.setText("");
                    this.controller.displayError();
                    LOGGER.info("Display error dialog");
                    JOptionPane.showMessageDialog(this,
                            "Le lieu \"" + e + "\" n'a pas été trouvé\n" +
                                    "Merci de préciser votre recherche.",
                            "Lieu non trouvé",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case 4:
                    LOGGER.info("Code means that request is not possible due to connection error");
                    this.controller.displayError();
                    LOGGER.info("Display error dialog");
                    JOptionPane.showMessageDialog(this,
                            "La requête n'a pas pu aboutir.\n" +
                                    "Merci de vérifier votre connexion.",
                            "Requête non aboutie",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
}
