package com.yline.xml.general;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

import com.yline.utils.LogUtil;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午12:13:58
 * @version 
 */
public class GeneralTool
{
    
    /**
     * 这一种方法存在一些问题，例如 <> 这些东西会出现bug;而第二种会自动转义
     */
    public static void generalWithAppend(Context context, List<Info> infos)
    {
        //假设已经获取到了所有的一组短信， 
        StringBuilder sb = new StringBuilder();
        
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<smss>\n");
        
        if (null != infos)
        {
            LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, "number = " + infos.size());
            
            for (Info info : infos)
            {
                sb.append("<sms>");
                
                sb.append("<address>");
                sb.append(info.getAddress());
                sb.append("</address>\n");
                
                sb.append("<body>");
                sb.append(info.getBody());
                sb.append("</body>\n");
                
                sb.append("<data>");
                sb.append(info.getData());
                sb.append("</data>\n");
                
                sb.append("<type>");
                sb.append(info.getType());
                sb.append("</type>");
                
                sb.append("</sms>\n");
            }
            
            sb.append("</smss>\n");
            
            try
            {
                File file = new File(context.getFilesDir(), "backup.xml");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(sb.toString().getBytes());
                fos.close();
                LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, sb.toString());
                LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, "生成xml成功");
            }
            catch (Exception e)
            {
                LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, "生成xml失败");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 使用序列化器,生成xml
     */
    public static void generalWithSerializer(Context context, List<Info> infos)
    {
        if (null != infos)
        {
            LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, "number = " + infos.size());
            
            try
            {
                XmlSerializer serializer = Xml.newSerializer();
                File file = new File(context.getFilesDir(), "backupSms2.xml");
                FileOutputStream fos = new FileOutputStream(file);
                //初始化序列号器，指定写入文件的目录、以及文件的编码格式
                serializer.setOutput(fos, "utf-8");
                
                //开始写入数据
                //确定文件开头的格式，以及文件是否独立
                serializer.startDocument("utf-8", true);
                //写入命名空间、名字
                serializer.startTag(null, "smss");
                
                for (Info info : infos)
                { //SmsInfo为构成的类，info是类的名称，SmsInfos是一个类组;遇到空类则跳出
                    serializer.startTag(null, "sms");
                    // 增加id 为 <sms id> 中
                    serializer.attribute(null, "id", info.getId() + "");
                    
                    //写入一条单独的字符串 body
                    serializer.startTag(null, "body");
                    serializer.text(info.getBody());
                    serializer.endTag(null, "body");
                    
                    //写入一条单独的字符串 address
                    serializer.startTag(null, "address");
                    serializer.text(info.getAddress());
                    serializer.endTag(null, "address");
                    
                    //写入一条单独的字符串 data
                    serializer.startTag(null, "data");
                    serializer.text(info.getData() + "");
                    serializer.endTag(null, "data");
                    
                    //写入一条单独的字符串 type 
                    serializer.startTag(null, "type");
                    serializer.text(info.getType() + "");
                    serializer.endTag(null, "type");
                    
                    serializer.endTag(null, "sms");
                }
                
                serializer.endTag(null, "smss");
                serializer.endDocument();
                
                LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, fos.toString());
                fos.close();
                
                LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, "生成xml成功");
            }
            catch (Exception e)
            {
                LogUtil.v(com.yline.xml.general.User.TAG_GENERAL_XML, "生成xml失败");
                e.printStackTrace();
            }
        }
    }
}
