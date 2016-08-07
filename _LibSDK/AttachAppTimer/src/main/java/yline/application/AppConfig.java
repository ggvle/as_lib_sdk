package yline.application;

/**
 * 在Application中配置父类所需要的配置选项
 *
 * @author YLine 2016-5-27 -> 上午7:28:17
 */
public class AppConfig
{
	private boolean isTimerServiceOpen = false;

	public boolean isTimerServiceOpen()
	{
		return isTimerServiceOpen;
	}

	/**
	 * 需要在注册表MAinfest中注册服务
	 *
	 * @param isTimerServiceOpen 伴随服务是否开启,default is false
	 */
	public void setTimerServiceOpen(boolean isTimerServiceOpen)
	{
		this.isTimerServiceOpen = isTimerServiceOpen;
	}

	@Override
	public String toString()
	{
		return "AppConfig [isTimerServiceOpen=" + isTimerServiceOpen + "]";
	}

}
