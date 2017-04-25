package com.yline.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Comparator;

/**
 * 目前提供给 LogFileUtil准备
 * simple introduction
 *
 * @author YLine 2016-5-25 - 上午8:06:08
 */
public class FileUtil
{
	private static final String HIDDEN_PREFIX = ".";

	/**
	 * 获取内置sd卡最上层路径
	 *
	 * @return /storage/emulated/0/ or null
	 */
	public static String getPathTop()
	{
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 获取内置sd卡, 最上层路径
	 *
	 * @return /storage/emulated/0/ or null
	 */
	public static File getFileTop()
	{
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			return Environment.getExternalStorageDirectory();
		}
		else
		{
			return null;
		}
	}

	/**
	 * 获取内置sd卡, 最上层路径
	 *
	 * @param fileName
	 * @return /storage/emulated/0/ or null
	 */
	public static File getFileTop(String fileName)
	{
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			return Environment.getExternalStoragePublicDirectory(fileName);
		}
		else
		{
			return null;
		}
	}

	/**
	 * 获取内置sd卡, 最上层路径
	 *
	 * @param dirName
	 * @param fileName
	 * @return /storage/emulated/0/ or null
	 */
	public static File getFileTop(String dirName, String fileName)
	{
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
		{
			File dir = Environment.getExternalStoragePublicDirectory(dirName);
			return FileUtil.create(dir, fileName);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param context
	 * @param dirName
	 * @return /storage/emulated/0/Android/data/包名/files/ + dirName
	 */
	public static File getFileExternalDir(Context context, String dirName)
	{
		return context.getExternalFilesDir(dirName);
	}

	/**
	 * @param context
	 * @param dirName
	 * @param fileName
	 * @return /storage/emulated/0/Android/data/包名/files/ + dirName/ + fileName
	 */
	public static File getFileExternal(Context context, String dirName, String fileName)
	{
		File dir = context.getExternalFilesDir(dirName);
		return FileUtil.create(dir, fileName);
	}

	/**
	 * @param context
	 * @return /storage/emulated/0/Android/data/包名/cache
	 */
	public static File getFileExternalCacheDir(Context context)
	{
		return context.getExternalCacheDir();
	}

	/**
	 * @param context
	 * @return /data/data/包名/files
	 */
	public static File getFileInner(Context context)
	{
		return context.getFilesDir();
	}

	/**
	 * @param context
	 * @return /data/data/包名/cache
	 */
	public static File getFileInnerCache(Context context)
	{
		return context.getCacheDir();
	}

	/**
	 * 读取 Assets 中的 Stream文件
	 *
	 * @param context
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static InputStream getStreamAssets(Context context, String fileName) throws IOException
	{
		return context.getAssets().open(fileName);
	}

	/**
	 * android.permission.WRITE_EXTERNAL_STORAGE
	 * 构建一个文件,真实的创建
	 *
	 * @param dir  文件的目录
	 * @param name 文件名     such as log.txt
	 * @return file or null
	 */
	public static File create(File dir, String name)
	{
		if (null == dir || TextUtils.isEmpty(name))
		{
			return null;
		}

		File file = new File(dir, name);
		if (!file.exists())
		{
			try
			{
				if (file.createNewFile())
				{
					return file;
				}
			} catch (IOException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return file;
		}

		return null;
	}

	/**
	 * android.permission.WRITE_EXTERNAL_STORAGE
	 * 创建一个文件夹
	 *
	 * @param path such as /storage/emulated/0/Yline/Log/
	 * @return file or null
	 */
	public static File createDir(String path)
	{
		File pathFile = new File(path);

		if (!pathFile.exists())
		{
			if (!pathFile.mkdirs())
			{
				return null;
			}
		}
		return pathFile;
	}

	/**
	 * 是否存在该文件
	 *
	 * @param dir  文件目录
	 * @param name 文件名称
	 * @return false(参数错误、文件不存在)
	 */
	public static boolean isExist(File dir, String name)
	{
		if (null == dir || TextUtils.isEmpty(name))
		{
			return false;
		}

		return new File(dir, name).exists();
	}

	/**
	 * android.permission.WRITE_EXTERNAL_STORAGE
	 * 删除一个文件
	 *
	 * @param dir  文件的目录
	 * @param name 文件名  such as log.txt
	 * @return false(参数错误、不存在该文件、删除失败)
	 */
	public static boolean delete(File dir, String name)
	{
		if (null == dir || TextUtils.isEmpty(name))
		{
			return false;
		}

		File file = new File(dir, name);
		if (file.exists())
		{
			return file.delete();
		}

		return false;
	}

	/**
	 * 重命名一个文件
	 *
	 * @param dir     文件的目录
	 * @param oldName 文件名  such as log0.txt
	 * @param newName 文件名  such as log1.txt
	 * @return false(参数错误、不存在该文件、重命名失败)
	 */
	public static boolean rename(File dir, String oldName, String newName)
	{
		if (null == dir || TextUtils.isEmpty(oldName))
		{
			return false;
		}

		File oldFile = new File(dir, oldName);
		// 不存在该文件,即算作命名成功
		if (oldFile.exists())
		{
			if (TextUtils.isEmpty(newName))
			{
				return false;
			}
			File newFile = new File(dir, newName);
			return oldFile.renameTo(newFile);
		}

		return false;
	}

	/**
	 * @param file    文件
	 * @param content 内容
	 * @return false(写入失败, 写入工具关闭失败)
	 */
	public static boolean write(File file, String content)
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			IOUtil.write(content + "\n", fileOutputStream);
			IOUtil.close(fileOutputStream);
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * File and folder comparator. TODO Expose sorting option method
	 *
	 * @author paulburke
	 */
	private static Comparator<File> sComparator = new Comparator<File>()
	{
		@Override
		public int compare(File f1, File f2)
		{
			// Sort alphabetically by lower case, which is much cleaner
			return f1.getName().toLowerCase().compareTo(
					f2.getName().toLowerCase());
		}
	};

	private static FileFilter sFileFilter = new FileFilter()
	{
		public boolean accept(File file)
		{
			return file.isFile();
		}
	};

	private static FileFilter sDirFilter = new FileFilter()
	{
		public boolean accept(File file)
		{
			return file.isDirectory();
		}
	};

	/**
	 * File (not directories) filter.
	 *
	 * @author paulburke
	 */
	private static FileFilter sFilePointFilter = new FileFilter()
	{
		@Override
		public boolean accept(File file)
		{
			final String fileName = file.getName();
			// Return files only (not directories) and skip hidden files
			return file.isFile() && !fileName.startsWith(HIDDEN_PREFIX);
		}
	};

	/**
	 * Folder (directories) filter.
	 *
	 * @author paulburke
	 */
	private static FileFilter sDirPointFilter = new FileFilter()
	{
		@Override
		public boolean accept(File file)
		{
			final String fileName = file.getName();
			// Return directories only and skip hidden directories
			return file.isDirectory() && !fileName.startsWith(HIDDEN_PREFIX);
		}
	};

	public static Comparator<File> getsComparator()
	{
		return sComparator;
	}

	public static String getHiddenPrefix()
	{
		return HIDDEN_PREFIX;
	}

	public static FileFilter getsFilePointFilter()
	{
		return sFilePointFilter;
	}

	public static FileFilter getsDirPointFilter()
	{
		return sDirPointFilter;
	}

	public static FileFilter getsFileFilter()
	{
		return sFileFilter;
	}

	public static FileFilter getsDirFilter()
	{
		return sDirFilter;
	}

	/**
	 * uri 路径 转成 文件路径
	 * 测试结果: 跳转图片ok; 跳转文件管理es ok; 跳转系统缩略图 failed
	 *
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String uri2File(final Context context, final Uri uri)
	{
		if (null == uri)
		{
			return null;
		}

		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
		{
			data = uri.getPath();
		}
		else if (ContentResolver.SCHEME_FILE.equals(scheme))
		{
			data = uri.getPath();
		}
		else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
		{
			Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
			if (null != cursor)
			{
				if (cursor.moveToFirst())
				{
					int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (index > -1)
					{
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	public static URI file2Uri(File file)
	{
		return file.toURI();
	}
}
