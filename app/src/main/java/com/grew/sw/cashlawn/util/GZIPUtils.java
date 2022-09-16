package com.grew.sw.cashlawn.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import okhttp3.Headers;

public class GZIPUtils {public static final String ENCODE_UTF_8 = "UTF-8";

    public static final String ENCODE_ISO_8859_1 = "ISO-8859-1";

//    public static byte[] compress(String str) throws IOException {
//        if (str == null || str.length() == 0) {
//            return null;
//        }
//        // 创建一个新的 byte 数组输出流
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        // 使用默认缓冲区大小创建新的输出流
//        GZIPOutputStream gzip = new GZIPOutputStream(out);
//        // 将 b.length 个字节写入此输出流
//        gzip.write(str.getBytes());
//        gzip.finish();
//        gzip.close();
//        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
//        return out.toByteArray();
//    }
//
    /**
     * String 压缩至gzip 字节数据
     */
    public static byte[] compress(String str) {
        return compress(str, ENCODE_UTF_8);
    }

    /**
     * String 压缩至gzip 字节数组，可选择encoding配置
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipInputStream;
        byte[] bytes = null;
        try {
            gzipInputStream = new GZIPOutputStream(out);
            gzipInputStream.write(str.getBytes(encoding));
            gzipInputStream.close();
            bytes = out.toByteArray();
            out.close();
        } catch (IOException e) {
            System.out.println("gzip compress error");
        }
        return bytes;
    }

    /**
     * 字节数组解压
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        byte[] bytes1 = new byte[0];
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            bytes1 = out.toByteArray();
            gzipInputStream.close();
        } catch (IOException e) {
            System.out.println("gzip uncompress error.");
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes1;
    }

    /**
     * 字节数组解压至string
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, ENCODE_UTF_8);
    }

    /**
     * 字节数组解压至string，可选择encoding配置
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            ungzip.close();
            return out.toString(encoding);
        } catch (IOException e) {
            System.out.println("gzip uncompress to string error");
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 判断请求头是否存在gzip
     */
    public static boolean isGzip(Headers headers) {
        boolean gzip = false;
        for (String key : headers.names()) {
            if (key.equalsIgnoreCase("Accept-Encoding") && headers.get(key).contains("gzip") || key.equalsIgnoreCase("Content-Encoding") && headers.get(key).contains("gzip")) {
                gzip = true;
                break;
            }
        }
        return gzip;
    }
}

