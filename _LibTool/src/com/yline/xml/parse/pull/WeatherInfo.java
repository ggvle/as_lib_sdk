package com.yline.xml.parse.pull;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 上午9:21:10
 * @version 
 */
public class WeatherInfo
{
    private int    id;
    
    private String name;
    
    private String wind;
    
    private String weather;
    
    private String temp;
    
    private String pm;
    
    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @return the wind
     */
    public String getWind()
    {
        return wind;
    }
    
    /**
     * @param wind the wind to set
     */
    public void setWind(String wind)
    {
        this.wind = wind;
    }
    
    /**
     * @return the weather
     */
    public String getWeather()
    {
        return weather;
    }
    
    /**
     * @param weather the weather to set
     */
    public void setWeather(String weather)
    {
        this.weather = weather;
    }
    
    /**
     * @return the temp
     */
    public String getTemp()
    {
        return temp;
    }
    
    /**
     * @param temp the temp to set
     */
    public void setTemp(String temp)
    {
        this.temp = temp;
    }
    
    /**
     * @return the pm
     */
    public String getPm()
    {
        return pm;
    }
    
    /**
     * @param pm the pm to set
     */
    public void setPm(String pm)
    {
        this.pm = pm;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "WeatherInfo [id=" + id + ", name=" + name + ", wind=" + wind + ", weather=" + weather + ", temp="
            + temp + ", pm=" + pm + "]";
    }
}
