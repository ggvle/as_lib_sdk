/**
 * Data:2016-5-1上午9:16:06
 */
package com.yline.xml.parse.pull;

import java.io.InputStream;
import java.util.List;

import com.yline.lib.utils.LogUtil;

import android.content.Context;

/**
 * simple introduction
 * 用法示例
 *
 * @author YLine 2016-5-1
 * @version 
 */
public class User
{
    public static final String TAG_XML_PULL_PARSE = "xml_pull_parse";
    
    public void test(Context context)
    {
        InputStream inputStream = context.getClassLoader().getResourceAsStream("weather.xml");
        try
        {
            List<WeatherInfo> infos = ParseUtil.getWeatherInfo(inputStream);
            
            StringBuffer sBuffer = new StringBuffer();
            for (WeatherInfo weatherInfo : infos)
            {
                sBuffer.append(weatherInfo.toString());
                sBuffer.append("\n");
            }
            String content = sBuffer.toString();
            LogUtil.v(TAG_XML_PULL_PARSE, content);
        }
        catch (Exception e)
        {
            LogUtil.e(TAG_XML_PULL_PARSE, "解析出错");
            e.printStackTrace();
        }
    }
}
