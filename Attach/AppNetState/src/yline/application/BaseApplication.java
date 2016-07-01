package yline.application;

import android.app.Application;
import yline.application.netstate.NetStateManager;
import yline.application.netstate.NetStateManager.INetStateListener;

/**
 * 网络状态监听器
 */
public abstract class BaseApplication extends Application
{
    private static AppConfig mBaseConfig = new AppConfig(); // 先选用默认配置
    
    /**
     * @return 当前Application的配置信息
     */
    public static AppConfig getBaseConfig()
    {
        return mBaseConfig;
    }
    
    /**
     * 配置当前Application的配置信息
     * 返回null,则按默认配置
     * @param mBaseConfig 配置对象
     */
    private void setBaseConfig(AppConfig mBaseConfig)
    {
        if (null != mBaseConfig)
        {
            BaseApplication.mBaseConfig = mBaseConfig;
        }
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
        // 配置,定制基础信息
        setBaseConfig(initConfig());
        
        AppService.initAppService(this); // 伴生服务
    }
    
    /**
     * 注册网络监听器 <目前只支持WIfi>
     * 相同的tag,会注册失败
     * @param netStateListener
     * @return true(success)
     */
    public static boolean registerNetStateListener(String tag, INetStateListener netStateListener)
    {
        return NetStateManager.getInstance().registerListener(tag, netStateListener);
    }
    
    /**
     * 移除网络监听器 <目前只支持WIfi>
     * @param netStateListener
     * @return true(success)
     */
    public static boolean removeNetStateListener(String tag, INetStateListener netStateListener)
    {
        return NetStateManager.getInstance().removeListener(tag, netStateListener);
    }
    
    /**
     * 清除所有的网络监听器 <目前只支持WIfi>
     */
    public static void removeAllNetStateListener()
    {
        NetStateManager.getInstance().clearListener();
    }
    
    /**
     * 进行一些基础配置,要求上级必须配置的信息
     * @return
     */
    protected abstract AppConfig initConfig();
    
}
