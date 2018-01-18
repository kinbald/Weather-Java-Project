package edu.isen.desrumaux.weatherapp.net;

import edu.isen.desrumaux.weatherapp.app.Coordonates;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.*;

public class Connection {

    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);

    private URL url;
    private boolean useProxy;
    private Element element;

    public Connection(String url, boolean proxy) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.useProxy = proxy;
    }

    public void getWeather(WeatherModel model) {
        try {
            HttpURLConnection con;
            if (useProxy) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("isen.isen.fr", 3128));
                con = (HttpURLConnection) url.openConnection(proxy);
            } else {
                con = (HttpURLConnection) url.openConnection();
            }
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document document = db.parse(con.getInputStream());

                element = document.getDocumentElement();

                model.convertElementToWeather(element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Coordonates getCoordonates() {
        try {
            HttpURLConnection con;
            if (useProxy) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("isen.isen.fr", 3128));
                con = (HttpURLConnection) url.openConnection(proxy);
            } else {
                con = (HttpURLConnection) url.openConnection();
            }
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document document = db.parse(con.getInputStream());

                element = document.getDocumentElement();

                if (element.getElementsByTagName("status").item(0).getTextContent().equals("OK")) {
                    return new Coordonates(
                            Float.parseFloat(element.getElementsByTagName("lat").item(0).getTextContent()),
                            Float.parseFloat(element.getElementsByTagName("lng").item(0).getTextContent()
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

