package com.example.myapplication.http;


import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public  class BuildPath {

    private static final String mp3 = ".mp3";
    private static final String usvoicepath = "http://media.shanbay.com/audio/us/";
    //做加载时的页面  可以
    private static final String todayEnglish= "https://apiv3.shanbay.com/weapps/dailyquote/quote/?date=";


    public static String getUsvoicepath(String word){
        return usvoicepath+word+mp3;
    }
    public static String getTodayEnglish(){
        return todayEnglish+getDateString();
    }


    public static String getDateString(){
        Date d = new Date(System.currentTimeMillis());
        String date = (String) DateFormat.format("yyyy-MM-dd", d);
        return date;
    }
    public static String getDateStringCalendar(){
        Date d = new Date(System.currentTimeMillis());
        String date_=getDateString();
        String date = date_.replace("-",".");
        return date;
    }

    /***
     * 获取日期的指定格式的Date对象
     * @return
     */
    public static Date getDate(){
        Date d = new Date(System.currentTimeMillis());
        Date date = (Date)DateFormat.format("yy-MM-dd/HH:mm:ss",d);
        return date;
    }
    /**
     * 生成加密字段
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getDigestType(String string, String type) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance(type);
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    /**
     *
     * @param result 音频字节流
     * @param file 存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        }catch (Exception e){
            Log.i("error",e.toString());
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}
