package com.wja.myapplication.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.wja.myapplication.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;

/**
 * Created by hanke on 2018/1/23.
 */

public class FileUtils {
    public static final String APP_STORAGE_ROOT = "MyTouTiao";
    /**
     * 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录
     */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getAppExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }
    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 获取SD下当前APP的目录
     */
    public static String getAppExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(APP_STORAGE_ROOT);
        sb.append(File.separator);
        return sb.toString();
    }


    /**
     * 获取应用的cache目录
     */
    public static String getCachePath() {
        File f = MyApplication.getContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }
    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }


    public static void copy(File src,File dst){
        FileInputStream fileInputStream=null;
        FileOutputStream fileOutputStream=null;
        FileChannel inChannel=null;
        FileChannel outChannel=null;
        try {
            fileInputStream=new FileInputStream(src);
            fileOutputStream=new FileOutputStream(dst);
            inChannel=fileInputStream.getChannel();
            outChannel=fileOutputStream.getChannel();
            inChannel.transferTo(0,inChannel.size(),outChannel);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
                inChannel.close();
                outChannel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public  static String getImageFileExt(String filePath){
        HashMap<String,String> mFileTypes=new HashMap<>();
        mFileTypes.put("FFD8FF", ".jpg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("474946", ".gif");
        mFileTypes.put("49492A00", ".tif");
        mFileTypes.put("424D", ".bmp");
        String value=mFileTypes.get(getFileHeader(filePath));
        String ext= TextUtils.isEmpty(value)? ".jpg":value;
        return ext;
    }

    public static String getFileHeader(String filePath){
        FileInputStream fileInputStream=null;
        String value=null;
        try {
            fileInputStream=new FileInputStream(filePath);
            byte[] bytes=new byte[3];
            fileInputStream.read(bytes,0,bytes.length);
            value=bytesToHexString(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fileInputStream!=null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder builder=new StringBuilder();
        if (src==null || src.length==0){
            return null;
        }
        for (int i=0;i<src.length;i++){
            int value=src[i]&0XFF;
            String hex=Integer.toHexString(value);
            if (hex.length()<2){
                builder.append(0);
            }
            builder.append(hex);
        }
        return builder.toString();
    }
}
