package edu.isen.desrumaux.weatherapp.net;

import edu.isen.desrumaux.weatherapp.app.Coordinates;
import edu.isen.desrumaux.weatherapp.app.model.ForecastWeatherModel;
import edu.isen.desrumaux.weatherapp.app.model.WeatherModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.*;
import java.util.ArrayList;

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

    public ArrayList<WeatherModel> getWeather(Coordinates coordinates) {
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

                LOGGER.debug(element.getTagName());

                if (element.getTagName().equals("current")) {
                    ArrayList<WeatherModel> result = new ArrayList<WeatherModel>();
                    result.add(convertElementToWeather(element, coordinates));
                    return result;
                } else {
                    throw new IllegalArgumentException("L'adresse demandée ne correspond pas à une ville");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ForecastWeatherModel> getForecastWeather(WeatherModel weatherModel) {
        try {
            HttpURLConnection con;
            if (useProxy) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("isen.isen.fr", 3128));
                con = (HttpURLConnection) url.openConnection(proxy);
            } else {
                con = (HttpURLConnection) url.openConnection();
            }
            con.setRequestMethod("GET");
            LOGGER.info("Connection opened");
            int responseCode = con.getResponseCode();
            LOGGER.info("Response code " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success

                LOGGER.debug("Forecast weather request succeded");

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document document = db.parse(con.getInputStream());

                element = document.getDocumentElement();

                return convertElementToForecastWeather(element, weatherModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ForecastWeatherModel> convertElementToForecastWeather(Element weatherNode, WeatherModel weatherModel) {
        ArrayList<ForecastWeatherModel> result = new ArrayList<ForecastWeatherModel>();

        NodeList week = element.getElementsByTagName("time");

        if (week != null) {
            int length = week.getLength();
            LOGGER.debug("Length of the week : " + length);
            for (int i = 0; i < length; i++) {
                if (week.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) week.item(i);

                    ForecastWeatherModel forecastWeatherModel = new ForecastWeatherModel();

                    forecastWeatherModel.setCity(weatherModel.getCity());
                    forecastWeatherModel.setDate(el.getAttribute("day"));
                    forecastWeatherModel.setVar(el.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("var").getTextContent());
                    forecastWeatherModel.setWeather(el.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("name").getTextContent());

                    forecastWeatherModel.setTemp(Float.parseFloat(el.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("day").getTextContent()));

                    forecastWeatherModel.setWindSpeed(Float.parseFloat(el.getElementsByTagName("windSpeed").item(0).getAttributes().getNamedItem("mps").getTextContent()));

                    result.add(forecastWeatherModel);
                }
            }
        } else {
            LOGGER.debug("Week list is null");
        }
        return result;
    }

    public WeatherModel convertElementToWeather(Element weatherNode, Coordinates coordinates) {
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.setCity(weatherNode.getElementsByTagName("city").item(0).getAttributes().getNamedItem("name").getTextContent() + "," + weatherNode.getElementsByTagName("country").item(0).getTextContent());
        weatherModel.setCity_id(Integer.parseInt(weatherNode.getElementsByTagName("city").item(0).getAttributes().getNamedItem("id").getTextContent()));

        weatherModel.setWeather_code(weatherNode.getElementsByTagName("weather").item(0).getAttributes().getNamedItem("icon").getTextContent());
        weatherModel.setWeatherConditions(weatherNode.getElementsByTagName("weather").item(0).getAttributes().getNamedItem("value").getTextContent());

        weatherModel.setTemperature(Float.parseFloat(weatherNode.getElementsByTagName("temperature").item(0).getAttributes().item(3).getTextContent()));
        weatherModel.setTemperature_min(Float.parseFloat(weatherNode.getElementsByTagName("temperature").item(0).getAttributes().item(1).getTextContent()));
        weatherModel.setTemperature_max(Float.parseFloat(weatherNode.getElementsByTagName("temperature").item(0).getAttributes().item(0).getTextContent()));

        weatherModel.setHumidity(Float.parseFloat(weatherNode.getElementsByTagName("humidity").item(0).getAttributes().item(1).getTextContent()));

        weatherModel.setPressure(Float.parseFloat(weatherNode.getElementsByTagName("pressure").item(0).getAttributes().item(1).getTextContent()));

        weatherModel.setWind_direction(Float.parseFloat(weatherNode.getElementsByTagName("direction").item(0).getAttributes().item(2).getTextContent()));
        weatherModel.setWind_direction_name(weatherNode.getElementsByTagName("direction").item(0).getAttributes().getNamedItem("code").getTextContent());
        weatherModel.setWind_speed(Float.parseFloat(weatherNode.getElementsByTagName("speed").item(0).getAttributes().item(1).getTextContent()));


        weatherModel.setClouds(Float.parseFloat(weatherNode.getElementsByTagName("clouds").item(0).getAttributes().item(1).getTextContent()));
        weatherModel.setClouds_name(weatherNode.getElementsByTagName("clouds").item(0).getAttributes().item(0).getTextContent());

        if (weatherNode.getElementsByTagName("visibility").item(0).getAttributes().getLength() != 0) {
            weatherModel.setVisibility(Float.parseFloat(weatherNode.getElementsByTagName("visibility").item(0).getAttributes().item(0).getTextContent()));
        }

        weatherModel.setPrecipitation_mode(weatherNode.getElementsByTagName("precipitation").item(0).getAttributes().getNamedItem("mode").getTextContent());
        if (!weatherModel.getPrecipitation_mode().equals("no")) {
            weatherModel.setPrecipitation(Float.parseFloat(weatherNode.getElementsByTagName("precipitation").item(0).getAttributes().getNamedItem("value").getTextContent()));
        }
        weatherModel.setCoordinates(coordinates);
        return weatherModel;
    }

    public Coordinates getCoordinates() throws Exception {

        HttpURLConnection con;
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("isen.isen.fr", 3128));
            con = (HttpURLConnection) url.openConnection(proxy);
        } else {
            con = (HttpURLConnection) url.openConnection();
        }
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        LOGGER.debug("Response code : " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document document = db.parse(con.getInputStream());

            element = document.getDocumentElement();

            if (element.getElementsByTagName("status").item(0).getTextContent().equals("OK")) {
                return new Coordinates(
                        Float.parseFloat(element.getElementsByTagName("lat").item(0).getTextContent()),
                        Float.parseFloat(element.getElementsByTagName("lng").item(0).getTextContent()
                        ));
            } else {
                throw new IllegalArgumentException("Le lieu demandé n'existe pas");
            }
        } else {
            throw new Exception("La reqûete n'a pas pu aboutir");
        }
    }
}

