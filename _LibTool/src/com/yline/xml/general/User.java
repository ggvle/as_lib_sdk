package com.yline.xml.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午12:13:31
 * @version 
 */
public class User
{
    public static final String TAG_GENERAL_XML = "general_xml";
    
    public void test(Context context)
    {
        List<Info> infos = new ArrayList<Info>();
        Random random = new Random();
        for (int i = 0; i < 20; i++)
        {
            infos.add(new Info(System.currentTimeMillis(), random.nextInt(2) + 1, "body", "address",
                (random.nextInt(100)) + 15958145457L));
        }
        
        GeneralTool.generalWithAppend(context, infos);
        GeneralTool.generalWithSerializer(context, infos);
    }
}
