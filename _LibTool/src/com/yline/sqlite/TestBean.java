package com.yline.sqlite;

/**
 * simple introduction
 *
 * @author YLine 2016-5-2 -> 上午7:29:34
 * @version 
 */
public class TestBean
{
    private int    id;
    
    private String name;
    
    private String number;

    public TestBean()
    {
    }
    
    /**
     * @param id
     * @param name
     * @param number
     */
    public TestBean(int id, String name, String number)
    {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
    }

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
     * @return the number
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "TestBean [id=" + id + ", name=" + name + ", number=" + number + "]";
    }
}
