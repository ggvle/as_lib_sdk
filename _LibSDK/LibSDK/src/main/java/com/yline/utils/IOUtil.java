package com.yline.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * IO流
 *
 * @author yline 2017/4/20 -- 13:58
 * @version 1.0.0
 */
public class IOUtil
{
	public IOUtil()
	{
		/** 实例化失败 */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static void close(Closeable closeable) throws IOException
	{
		if (null != closeable)
		{
			closeable.close();
		}
	}

	public static void close(HttpURLConnection httpUrlConnection)
	{
		if (null != httpUrlConnection)
		{
			httpUrlConnection.disconnect();
		}
	}

	public static void flush(Flushable flushable) throws IOException
	{
		if (null != flushable)
		{
			flushable.flush();
		}
	}

	public static BufferedInputStream toBufferedInputStream(InputStream inputStream)
	{
		return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream :
				new BufferedInputStream(inputStream);
	}

	public static BufferedOutputStream toBufferedOutputStream(OutputStream outputStream)
	{
		return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream :
				new BufferedOutputStream(outputStream);
	}

	public static BufferedReader toBufferedReader(Reader reader)
	{
		return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
	}

	public static BufferedWriter toBufferedWriter(Writer writer)
	{
		return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
	}

	public static InputStream toInputStream(CharSequence input)
	{
		return new ByteArrayInputStream(input.toString().getBytes());
	}

	public static InputStream toInputStream(CharSequence input, String encoding) throws UnsupportedEncodingException
	{
		byte[] bytes = input.toString().getBytes(encoding);
		return new ByteArrayInputStream(bytes);
	}


	public static String toString(InputStream input) throws IOException
	{
		return new String(toByteArray(input));
	}

	public static String toString(InputStream input, String encoding) throws IOException
	{
		return new String(toByteArray(input), encoding);
	}

	public static String toString(Reader input) throws IOException
	{
		return new String(toByteArray(input));
	}

	public static String toString(Reader input, String encoding) throws IOException
	{
		return new String(toByteArray(input), encoding);
	}

	public static String toString(byte[] byteArray)
	{
		return new String(byteArray);
	}

	public static String toString(byte[] byteArray, String encoding)
	{
		try
		{
			return new String(byteArray, encoding);
		} catch (UnsupportedEncodingException e)
		{
			return new String(byteArray);
		}
	}

	public static byte[] toByteArray(CharSequence input)
	{
		if (input == null)
		{
			return new byte[0];
		}
		return input.toString().getBytes();
	}

	public static byte[] toByteArray(CharSequence input, String encoding) throws UnsupportedEncodingException
	{
		if (input == null)
		{
			return new byte[0];
		}
		else
		{
			return input.toString().getBytes(encoding);
		}
	}

	public static byte[] toByteArray(InputStream input) throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		write(input, output);
		output.close();
		return output.toByteArray();
	}

	public static byte[] toByteArray(InputStream input, int size) throws IOException
	{
		if (size < 0)
		{
			throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
		}

		if (size == 0)
		{
			return new byte[0];
		}

		byte[] data = new byte[size];
		int offset = 0;
		int byteCount;
		while ((offset < size) && (byteCount = input.read(data, offset, size - offset)) != -1)
		{
			offset += byteCount;
		}

		if (offset != size)
		{
			throw new IOException("Unexpected byte count size. current: " + offset + ", excepted: " + size);
		}
		return data;
	}

	public static byte[] toByteArray(Reader input) throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		write(input, output);
		output.close();
		return output.toByteArray();
	}

	public static byte[] toByteArray(Reader input, String encoding) throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		write(input, output, encoding);
		output.close();
		return output.toByteArray();
	}

	public static char[] toCharArray(CharSequence input) throws IOException
	{
		CharArrayWriter output = new CharArrayWriter();
		write(input, output);
		return output.toCharArray();
	}

	public static char[] toCharArray(InputStream input) throws IOException
	{
		CharArrayWriter output = new CharArrayWriter();
		write(input, output);
		return output.toCharArray();
	}

	public static List<String> toStringList(InputStream input, String encoding) throws IOException
	{
		Reader reader = new InputStreamReader(input, encoding);
		return toStringList(reader);
	}

	public static List<String> toStringList(InputStream input) throws IOException
	{
		Reader reader = new InputStreamReader(input);
		return toStringList(reader);
	}

	public static List<String> toStringList(Reader input) throws IOException
	{
		BufferedReader reader = toBufferedReader(input);
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null)
		{
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

	public static char[] toCharArray(InputStream input, String encoding) throws IOException
	{
		CharArrayWriter output = new CharArrayWriter();
		write(input, output, encoding);
		return output.toCharArray();
	}

	public static char[] toCharArray(Reader input) throws IOException
	{
		CharArrayWriter output = new CharArrayWriter();
		write(input, output);
		return output.toCharArray();
	}

	public static void write(byte[] data, OutputStream output) throws IOException
	{
		if (data != null)
		{
			output.write(data);
		}
	}

	public static void write(byte[] data, Writer output) throws IOException
	{
		if (data != null)
		{
			output.write(new String(data));
		}
	}

	public static void write(byte[] data, Writer output, String encoding) throws IOException
	{
		if (data != null)
		{
			output.write(new String(data, encoding));
		}
	}

	public static void write(char[] data, Writer output) throws IOException
	{
		if (data != null)
		{
			output.write(data);
		}
	}

	public static void write(char[] data, OutputStream output) throws IOException
	{
		if (data != null)
		{
			output.write(new String(data).getBytes());
		}
	}

	public static void write(char[] data, OutputStream output, String encoding) throws IOException
	{
		if (data != null)
		{
			output.write(new String(data).getBytes(encoding));
		}
	}

	public static void write(CharSequence data, Writer output) throws IOException
	{
		if (data != null)
		{
			output.write(data.toString());
		}
	}

	public static void write(CharSequence data, OutputStream output) throws IOException
	{
		if (data != null)
		{
			output.write(data.toString().getBytes());
		}
	}

	public static void write(CharSequence data, OutputStream output, String encoding) throws IOException
	{
		if (data != null)
		{
			output.write(data.toString().getBytes(encoding));
		}
	}

	public static void write(InputStream inputStream, OutputStream outputStream) throws IOException
	{
		int len;
		byte[] buffer = new byte[4096];
		while ((len = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, len);
		}
	}

	public static void write(Reader input, OutputStream output) throws IOException
	{
		Writer out = new OutputStreamWriter(output);
		write(input, out);
		out.flush();
	}

	public static void write(InputStream input, Writer output) throws IOException
	{
		Reader in = new InputStreamReader(input);
		write(in, output);
	}

	public static void write(Reader input, OutputStream output, String encoding) throws IOException
	{
		Writer out = new OutputStreamWriter(output, encoding);
		write(input, out);
		out.flush();
	}

	public static void write(InputStream input, OutputStream output, String encoding) throws IOException
	{
		Reader in = new InputStreamReader(input, encoding);
		write(in, output);
	}

	public static void write(InputStream input, Writer output, String encoding) throws IOException
	{
		Reader in = new InputStreamReader(input, encoding);
		write(in, output);
	}

	public static void write(Reader input, Writer output) throws IOException
	{
		int len;
		char[] buffer = new char[4096];
		while (-1 != (len = input.read(buffer)))
		{
			output.write(buffer, 0, len);
		}
	}

	public static boolean isEquals(InputStream input1, InputStream input2) throws IOException
	{
		input1 = toBufferedInputStream(input1);
		input2 = toBufferedInputStream(input2);

		int ch = input1.read();
		while (-1 != ch)
		{
			int ch2 = input2.read();
			if (ch != ch2)
			{
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return ch2 == -1;
	}

	public static boolean isEquals(Reader input1, Reader input2) throws IOException
	{
		input1 = toBufferedReader(input1);
		input2 = toBufferedReader(input2);

		int ch = input1.read();
		while (-1 != ch)
		{
			int ch2 = input2.read();
			if (ch != ch2)
			{
				return false;
			}
			ch = input1.read();
		}

		int ch2 = input2.read();
		return ch2 == -1;
	}

	public static boolean isEqualsIgnoreEOL(Reader input1, Reader input2) throws IOException
	{
		BufferedReader br1 = toBufferedReader(input1);
		BufferedReader br2 = toBufferedReader(input2);

		String line1 = br1.readLine();
		String line2 = br2.readLine();
		while ((line1 != null) && (line2 != null) && (line1.equals(line2)))
		{
			line1 = br1.readLine();
			line2 = br2.readLine();
		}
		return line1 != null && (line2 == null || line1.equals(line2));
	}
}