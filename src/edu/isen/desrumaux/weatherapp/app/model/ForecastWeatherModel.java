package edu.isen.desrumaux.weatherapp.app.model;

public class ForecastWeatherModel {
    private String city;
    private String date;
    private String weather;
    private String var;
    private float windSpeed;
    private float temp;

    public ForecastWeatherModel()
    {
        this.city = "";
        this.date = "";
        this.weather = "";
        this.var = "";
        this.city = "";
        this.windSpeed = 0.f;
        this.temp = 0.f;
    }

    // constructor for current weather + previous search
    public ForecastWeatherModel(String name, String date, String weather, String var, float windSpeed, float temp)
    {
        this.city = name;
        this.date = date;
        this.weather = weather;
        this.var = var;
        this.windSpeed = windSpeed;
        this.temp = temp;
    }

    //constructor for weather week
    public ForecastWeatherModel(String date, String var, float temp)
    {
        this();
        this.date = date;
        this.var = var;
        this.temp = temp;
    }

    public String toString()
    {
        return ("nom : " + this.city +" date : " + this.date + " meteo : "+this.weather+" vitesse du vent : "
                + this.windSpeed+" température : "+this.temp+" var : "+this.var);
    }

    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getWeather()
    {
        return weather;
    }
    public void setWeather(String weather)
    {
        this.weather = weather;
    }
    public String getVar()
    {
        return var;
    }
    public void setVar(String var)
    {
        this.var = var;
    }
    public float getWindSpeed()
    {
        return windSpeed;
    }
    public void setWindSpeed(float windSpeed)
    {
        this.windSpeed = windSpeed;
    }
    public float getTemp()
    {
        return temp;
    }
    public void setTemp(float temp)
    {
        this.temp = temp;
    }
}
