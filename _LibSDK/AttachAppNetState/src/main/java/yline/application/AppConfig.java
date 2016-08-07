package yline.application;

/**
 * 在Application中配置父类所需要的配置选项
 * simple introduction
 *
 * @author YLine 2016-5-27 -> 上午7:28:17
 * @version
 */
public class AppConfig
{
    private boolean isNetStateListenerOpen = false;
    
    public boolean isNetStateListenerOpen()
    {
        return isNetStateListenerOpen;
    }
    
    /**
     * 需要在注册表MAinfest中注册广播
     * @param isNetStateListenerOpen  网络状态监听器是否打开,default is false
     */
    public void setNetStateListenerOpen(boolean isNetStateListenerOpen)
    {
        this.isNetStateListenerOpen = isNetStateListenerOpen;
    }
    
    @Override
    public String toString()
    {
        return "AppConfig [isNetStateListenerOpen=" + isNetStateListenerOpen + "]";
    }
    
}
