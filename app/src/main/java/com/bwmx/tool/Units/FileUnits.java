package com.bwmx.tool.Units;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import de.robv.android.xposed.XposedBridge;

public class FileUnits {
    private static final String Path;

    static {
        File sdCardFile = Environment.getExternalStorageDirectory();
        File file = new File(sdCardFile, "/BWMX/");
        Path = file.getAbsolutePath();
    }

    public static boolean writelog(String data) {
        XposedBridge.log(data);
        try {
            File file = new File(Path, "log.txt");
            AtomicBoolean create = new AtomicBoolean(true);
            if(!file.getParentFile().exists()) create.set(file.getParentFile().mkdirs());
            else if (!file.exists()) create.set(file.createNewFile());
            if (create.get()) {
                FileOutputStream fos = new FileOutputStream(file, true);
                fos.write((data + "\n").getBytes());
                fos.flush();
                fos.close();
                return true;
            }
        }
        catch (IOException e){
            XposedBridge.log(e);
        }
        return false;
    }

    public static String ReadStringFromFile(String filepath)
    {
        File file = new File(filepath);
        if (file.canRead()) return ReadStringFromFile(file);
        else return ReadStringFromFile(new File(Path, filepath));
    }

    public static String ReadStringFromFile(File file)
    {
        try{
            if(!file.exists()) return null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String text;
            while((text = bufferedReader.readLine()) != null){
            sb.append(text).append("\n");
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (IOException e) {
            writelog("[萌块]FileUnits -> ReadObjectFromFile\n" + e);
        }
        return null;
    }

    public static boolean WriteStringToFile(String filepath, String data, boolean append) {
            File file = new File(filepath);
            if (file.canWrite()) return WriteStringToFile(file, data, append);
            else return WriteStringToFile(new File(Path, filepath), data, append);
        }

    public static boolean WriteStringToFile(File file, String data, boolean append) {
        try {
            AtomicBoolean create = new AtomicBoolean(true);
            if(!Objects.requireNonNull(file.getParentFile()).exists()) create.set(file.getParentFile().mkdirs());
            else if (!file.exists()) create.set(file.createNewFile());
            if (create.get()) {
                FileOutputStream fos = new FileOutputStream(file, append);
                fos.write((data + "\n").getBytes());
                fos.flush();
                fos.close();
                return true;
            }
        }
        catch (IOException e){
            writelog("[萌块]FileUnits -> ReadObjectFromFile\n" + e);
        }
        return false;
    }

    public static Object ReadObjectFromFile(String filepath)
    {
        File file = new File(filepath);
        if (file.canRead()) return ReadObjectFromFile(file);
        else return ReadObjectFromFile(new File(Path, filepath));
    }

    public static Object ReadObjectFromFile(File file)
    {
        try{
            if(!file.exists()) return null;
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            return in.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            writelog("[萌块]FileUnits -> ReadObjectFromFile\n" + e);
        }
        return null;
    }

    public static boolean WriteObjectToFile(String filepath, Object data) {
        File file = new File(filepath);
        if (file.canWrite()) return WriteObjectToFile(file, data);
        else return WriteObjectToFile(new File(Path, filepath), data);
    }

    public static boolean WriteObjectToFile(File file,Object WriteData)
    {
        try{
            AtomicBoolean create = new AtomicBoolean(true);
            if(!Objects.requireNonNull(file.getParentFile()).exists()) create.set(file.getParentFile().mkdirs());
            else if (!file.exists()) create.set(file.createNewFile());
            if (create.get()) {
                FileOutputStream out = new FileOutputStream(file, false);
                ObjectOutputStream output = new ObjectOutputStream(out);
                output.writeObject(WriteData);
                output.flush();
                output.close();
                out.close();
                return true;
            }
        }
        catch (IOException e) {
            writelog("[萌块]FileUnits -> WriteObjectToFile\n" + e);
        }
        return false;
    }

}
