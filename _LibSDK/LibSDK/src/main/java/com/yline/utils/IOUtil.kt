package com.yline.utils

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.CharArrayWriter
import java.io.Closeable
import java.io.Flushable
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.UnsupportedEncodingException
import java.io.Writer
import java.net.HttpURLConnection
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * IO流
 *
 * @author yline 2017/4/20 -- 13:58
 * @version 1.0.0
 */
object IOUtil {

    @Throws(IOException::class)
    fun close(closeable: Closeable?) {
        closeable?.close()
    }

    fun close(httpUrlConnection: HttpURLConnection?) {
        httpUrlConnection?.disconnect()
    }

    @Throws(IOException::class)
    fun flush(flushable: Flushable?) {
        flushable?.flush()
    }

    fun toBufferedInputStream(inputStream: InputStream): BufferedInputStream {
        return if (inputStream is BufferedInputStream)
            inputStream
        else
            BufferedInputStream(inputStream)
    }

    fun toBufferedOutputStream(outputStream: OutputStream): BufferedOutputStream {
        return if (outputStream is BufferedOutputStream)
            outputStream
        else
            BufferedOutputStream(outputStream)
    }

    fun toBufferedReader(reader: Reader): BufferedReader {
        return if (reader is BufferedReader) reader else BufferedReader(reader)
    }

    fun toBufferedWriter(writer: Writer): BufferedWriter {
        return if (writer is BufferedWriter) writer else BufferedWriter(writer)
    }

    fun toInputStream(input: CharSequence): InputStream {
        return ByteArrayInputStream(input.toString().toByteArray())
    }

    @Throws(UnsupportedEncodingException::class)
    fun toInputStream(input: CharSequence, encoding: String): InputStream {
        val bytes = input.toString().toByteArray(charset(encoding))
        return ByteArrayInputStream(bytes)
    }

    @Throws(IOException::class)
    fun toString(input: InputStream): String {
        return String(toByteArray(input))
    }

    @Throws(IOException::class)
    fun toString(input: InputStream, encoding: String): String {
        return String(toByteArray(input), Charset.forName(encoding))
    }

    @Throws(IOException::class)
    fun toString(input: Reader): String {
        return String(toByteArray(input))
    }

    @Throws(IOException::class)
    fun toString(input: Reader, encoding: String): String {
        return String(toByteArray(input), Charset.forName(encoding))
    }

    fun toString(byteArray: ByteArray): String {
        return String(byteArray)
    }

    fun toString(byteArray: ByteArray, encoding: String): String {
        try {
            return String(byteArray, Charset.forName(encoding))
        } catch (e: UnsupportedEncodingException) {
            return String(byteArray)
        }
    }

    fun toByteArray(input: CharSequence?): ByteArray {
        return input?.toString()?.toByteArray() ?: ByteArray(0)
    }

    @Throws(UnsupportedEncodingException::class)
    fun toByteArray(input: CharSequence?, encoding: String): ByteArray {
        return input?.toString()?.toByteArray(charset(encoding)) ?: ByteArray(0)
    }

    @Throws(IOException::class)
    fun toByteArray(input: InputStream): ByteArray {
        val output = ByteArrayOutputStream()
        write(input, output)
        output.close()
        return output.toByteArray()
    }

    @Throws(IOException::class)
    fun toByteArray(input: InputStream, size: Int): ByteArray {
        require(size >= 0) { "Size must be equal or greater than zero: $size" }

        if (size == 0) {
            return ByteArray(0)
        }

        val data = ByteArray(size)
        var offset = 0
        var byteCount: Int
        while (offset < size) {
            byteCount = input.read(data, offset, size - offset)
            if (byteCount == -1) {
                break
            }

            offset += byteCount
        }

        if (offset != size) {
            throw IOException("Unexpected byte count size. current: $offset, excepted: $size")
        }
        return data
    }

    @Throws(IOException::class)
    fun toByteArray(input: Reader): ByteArray {
        val output = ByteArrayOutputStream()
        write(input, output)
        output.close()
        return output.toByteArray()
    }

    @Throws(IOException::class)
    fun toByteArray(input: Reader, encoding: String): ByteArray {
        val output = ByteArrayOutputStream()
        write(input, output, encoding)
        output.close()
        return output.toByteArray()
    }

    @Throws(IOException::class)
    fun toCharArray(input: CharSequence): CharArray {
        val output = CharArrayWriter()
        write(input, output)
        return output.toCharArray()
    }

    @Throws(IOException::class)
    fun toCharArray(input: InputStream): CharArray {
        val output = CharArrayWriter()
        write(input, output)
        return output.toCharArray()
    }

    @Throws(IOException::class)
    fun toStringList(input: InputStream, encoding: String): List<String> {
        val reader = InputStreamReader(input, encoding)
        return toStringList(reader)
    }

    @Throws(IOException::class)
    fun toStringList(input: InputStream): List<String> {
        val reader = InputStreamReader(input)
        return toStringList(reader)
    }

    @Throws(IOException::class)
    fun toStringList(input: Reader): List<String> {
        val reader = toBufferedReader(input)
        val list = ArrayList<String>()
        var line: String? = reader.readLine()
        while (line != null) {
            list.add(line)
            line = reader.readLine()
        }
        return list
    }

    @Throws(IOException::class)
    fun toCharArray(input: InputStream, encoding: String): CharArray {
        val output = CharArrayWriter()
        write(input, output, encoding)
        return output.toCharArray()
    }

    @Throws(IOException::class)
    fun toCharArray(input: Reader): CharArray {
        val output = CharArrayWriter()
        write(input, output)
        return output.toCharArray()
    }

    @Throws(IOException::class)
    fun write(data: ByteArray?, output: OutputStream) {
        if (data != null) {
            output.write(data)
        }
    }

    @Throws(IOException::class)
    fun write(data: ByteArray?, output: Writer) {
        if (data != null) {
            output.write(String(data))
        }
    }

    @Throws(IOException::class)
    fun write(data: ByteArray?, output: Writer, encoding: String) {
        if (data != null) {
            output.write(String(data, Charset.forName(encoding)))
        }
    }

    @Throws(IOException::class)
    fun write(data: CharArray?, output: Writer) {
        if (data != null) {
            output.write(data)
        }
    }

    @Throws(IOException::class)
    fun write(data: CharArray?, output: OutputStream) {
        if (data != null) {
            output.write(String(data).toByteArray())
        }
    }

    @Throws(IOException::class)
    fun write(data: CharArray?, output: OutputStream, encoding: String) {
        if (data != null) {
            output.write(String(data).toByteArray(charset(encoding)))
        }
    }

    @Throws(IOException::class)
    fun write(data: CharSequence?, output: Writer) {
        if (data != null) {
            output.write(data.toString())
        }
    }

    @Throws(IOException::class)
    fun write(data: CharSequence?, output: OutputStream) {
        if (data != null) {
            output.write(data.toString().toByteArray())
        }
    }

    @Throws(IOException::class)
    fun write(data: CharSequence?, output: OutputStream, encoding: String) {
        if (data != null) {
            output.write(data.toString().toByteArray(charset(encoding)))
        }
    }

    @Throws(IOException::class)
    fun write(inputStream: InputStream, outputStream: OutputStream) {
        var len: Int
        val buffer = ByteArray(4096)

        len = inputStream.read(buffer)
        while (len != -1) {
            outputStream.write(buffer, 0, len)

            len = inputStream.read(buffer)
        }
    }

    @Throws(IOException::class)
    fun write(input: Reader, output: OutputStream) {
        val out = OutputStreamWriter(output)
        write(input, out)
        out.flush()
    }

    @Throws(IOException::class)
    fun write(input: InputStream, output: Writer) {
        val `in` = InputStreamReader(input)
        write(`in`, output)
    }

    @Throws(IOException::class)
    fun write(input: Reader, output: OutputStream, encoding: String) {
        val out = OutputStreamWriter(output, encoding)
        write(input, out)
        out.flush()
    }

    @Throws(IOException::class)
    fun write(input: InputStream, output: OutputStream, encoding: String) {
        val `in` = InputStreamReader(input, encoding)
        write(`in`, output)
    }

    @Throws(IOException::class)
    fun write(input: InputStream, output: Writer, encoding: String) {
        val `in` = InputStreamReader(input, encoding)
        write(`in`, output)
    }

    @Throws(IOException::class)
    fun write(input: Reader, output: Writer) {
        var len: Int
        val buffer = CharArray(4096)

        len = input.read(buffer)
        while (-1 != len) {
            output.write(buffer, 0, len)

            len = input.read(buffer)
        }
    }

    @Throws(IOException::class)
    fun isEquals(input1: InputStream, input2: InputStream): Boolean {
        var input1 = input1
        var input2 = input2
        input1 = toBufferedInputStream(input1)
        input2 = toBufferedInputStream(input2)

        var ch = input1.read()
        while (-1 != ch) {
            val ch2 = input2.read()
            if (ch != ch2) {
                return false
            }
            ch = input1.read()
        }

        val ch2 = input2.read()
        return ch2 == -1
    }

    @Throws(IOException::class)
    fun isEquals(input1: Reader, input2: Reader): Boolean {
        var input1 = input1
        var input2 = input2
        input1 = toBufferedReader(input1)
        input2 = toBufferedReader(input2)

        var ch = input1.read()
        while (-1 != ch) {
            val ch2 = input2.read()
            if (ch != ch2) {
                return false
            }
            ch = input1.read()
        }

        val ch2 = input2.read()
        return ch2 == -1
    }

    @Throws(IOException::class)
    fun isEqualsIgnoreEOL(input1: Reader, input2: Reader): Boolean {
        val br1 = toBufferedReader(input1)
        val br2 = toBufferedReader(input2)

        var line1: String? = br1.readLine()
        var line2: String? = br2.readLine()
        while (line1 != null && line2 != null && line1 == line2) {
            line1 = br1.readLine()
            line2 = br2.readLine()
        }
        return line1 != null && (line2 == null || line1 == line2)
    }
}
