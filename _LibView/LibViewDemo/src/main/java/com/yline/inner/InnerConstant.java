package com.yline.inner;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InnerConstant
{
	private static Random random = new Random();

	public static List<String> getMvList()
	{
		return Arrays.asList("绅士", "春风十里不如你", "骄傲的少年", "美人鱼", "当我找到了你", "超人不会飞", "原点", "Mine Mine", "Faded", "从前的我",
				"刚刚好", "可惜没如果", "渺小", "摄影艺术", "不完美女孩", "You Belong With Me", "Begin again", "Blank Space", "一个人", "修炼爱情",
				"Try Everything", "大雨将至", "大雨将至", "白日梦想家", "小幸运", "山河故人");
	}

	public static List<String> getSingerList()
	{
		return Arrays.asList("Avril", "Taylor Swift", "周杰伦", "周笔畅", "孙燕姿", "张杰", "张靓颖", "徐良", "曾轶可", "本兮", "李宇春", "林俊杰", "梁静茹", "汪苏泷"
				, "王力宏", "许嵩", "阿悄");
	}

	public static String getRandom()
	{
		return "random-" + random.nextInt(100);
	}

	public static String getRandom(int max)
	{
		return "random-" + random.nextInt(max);
	}
}
