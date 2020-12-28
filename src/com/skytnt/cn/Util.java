package com.skytnt.cn;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String getDate()
    {
        Date d=new Date();
        return new SimpleDateFormat("yyyy-MM-dd").format(d)+"T"+new SimpleDateFormat("HH:mm:ss").format(d);
    }

    public static boolean fileExists(String path)
    {
        return new File(path).exists();
    }

    public static void writeFile(String path,String context)
    {
        path=pathRepair(path);
        try {
            File file=new File(path);
            if (!file.exists())
            {
                //System.out.println("create file:"+path);
                File p=file.getParentFile();
                if (p!=null)
                p.mkdirs();
                file.createNewFile();

            }
            FileOutputStream fileOutputStream=new FileOutputStream(path);
            fileOutputStream.write(context.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static String readFile(String path)
    {
        path=pathRepair(path);
        try {
            FileInputStream fileInputStream=new FileInputStream(path);
            byte[] lsy=new byte[fileInputStream.available()];
            fileInputStream.read(lsy);
            fileInputStream.close();
            return new String(lsy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] readFileBytes(String path)
    {
        path=pathRepair(path);
        try {
            FileInputStream fileInputStream=new FileInputStream(path);
            byte[] lsy=new byte[fileInputStream.available()];
            fileInputStream.read(lsy);
            fileInputStream.close();
            return lsy;
        } catch (Exception e) {
            writeFile(path,"");
        }
        return null;
    }

    public static String pathRepair(String path)
    {
        return path.replaceAll("[\n:*\"?<>|]|( (?=/))","");
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");

            instance.update(str.getBytes());
            return md5(instance);
        }catch(NoSuchAlgorithmException e)
        {

        }
        return null;
    }

    private static String md5(MessageDigest messageDigest) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : messageDigest.digest()) {
            stringBuilder.append(Integer.toHexString((b >> 4) & 15));
            stringBuilder.append(Integer.toHexString(b & 15));
        }
        return stringBuilder.toString();
    }

    public static void printStack()
    {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for(int i=0;i<stack.length;i++){
            System.out.println(stack[i].getClassName()+"."+stack[i].getMethodName());
        }
    }

}
