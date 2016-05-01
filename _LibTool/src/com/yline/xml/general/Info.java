package com.yline.xml.general;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午12:14:22
 * @version 
 */
public class Info
{
    private long   data;
    
    private int    type;
    
    private String body;
    
    private String address;
    
    private long   id;
    
    /**
     * @param data
     * @param type
     * @param body
     * @param address
     * @param id
     */
    public Info(long data, int type, String body, String address, long id)
    {
        super();
        this.data = data;
        this.type = type;
        this.body = body;
        this.address = address;
        this.id = id;
    }
    
    /**
     * @return the data
     */
    public long getData()
    {
        return data;
    }
    
    /**
     * @param data the data to set
     */
    public void setData(long data)
    {
        this.data = data;
    }
    
    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }
    
    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }
    
    /**
     * @return the body
     */
    public String getBody()
    {
        return body;
    }
    
    /**
     * @param body the body to set
     */
    public void setBody(String body)
    {
        this.body = body;
    }
    
    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    /**
     * @return the id
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(long id)
    {
        this.id = id;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Info [data=" + data + ", type=" + type + ", body=" + body + ", address=" + address + ", id=" + id + "]";
    }
    
}
