package com.bwmx.tool.Units;

import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bwmx.tool.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import de.robv.android.xposed.XposedBridge;

public class FileUnits {
    private static final String Path;
    public static boolean ToastSwitch = true;

    static {
        File sdCardFile = Environment.getExternalStorageDirectory();
        File file = new File(sdCardFile, "/BWMX/");
        Path = file.getAbsolutePath();
        File file2 = new File(file, "log.txt");
        if (file2.canWrite() && file2.length() > 102400) {
            boolean delete = file2.delete();
            writelog("刪除日志" + (delete ? "成功" : "失败"));
        }
    }

    public static String GetNowTime(String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return simpleDateFormat.format(System.currentTimeMillis());
        } catch (Exception e) {
            return GetNowTime("yyyy-MM-dd HH:mm:ss");
        }
    }

    public synchronized static boolean writelog(Object log) {
        if (ToastSwitch && String.valueOf(log).toLowerCase(Locale.ROOT).contains("error")) PluginTool.ShowLongToast(log);
        String log2 = "[" + Main.ProcessName + "]" + log;
        XposedBridge.log("[萌块]" + log2);
        String data = "[" + GetNowTime("MM-dd HH:mm:ss.SS") + "]" + log2;
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
            XposedBridge.log("[萌块][" + Main.ProcessName + "]WriteLog Error\n" + e);
        }
        return false;
    }

    public static String ReadStringFromFile(String filepath)
    {
        File file = new File(filepath);
        if (file.canRead()) return ReadStringFromFile(file);
        else return ReadStringFromFile(new File(Path, filepath));
    }

    public synchronized static String ReadStringFromFile(File file)
    {
        if (file == null) return null;
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
            writelog("FileUnits -> ReadObjectFromFile\n" + e);
        }
        return "";
    }

    public static boolean WriteStringToFile(String filepath, String data, boolean append) {
        File file = new File(filepath);
        if (file.canWrite()) return WriteStringToFile(file, data, append);
        else return WriteStringToFile(new File(Path, filepath), data, append);
        }

    public synchronized static boolean WriteStringToFile(File file, String data, boolean append) {
        if (file == null) return false;
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
            writelog("FileUnits -> ReadObjectFromFile\n" + e);
        }
        return false;
    }

    public static Object ReadObjectFromFile(String filepath)
    {
        File file = new File(filepath);
        if (file.canRead()) return ReadObjectFromFile(file);
        else return ReadObjectFromFile(new File(Path, filepath));
    }

    @Nullable
    public synchronized static Object ReadObjectFromFile(File file)
    {
        if (file == null) return null;
        try{
            if(!file.exists()) return null;
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            return in.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            writelog("FileUnits -> ReadObjectFromFile\n" + e);
        }
        return null;
    }

    public static boolean WriteObjectToFile(String filepath, Object data) {
        File file = new File(filepath);
        if (file.canWrite()) return WriteObjectToFile(file, data);
        else return WriteObjectToFile(new File(Path, filepath), data);
    }

    public synchronized static boolean WriteObjectToFile(File file,Object WriteData)
    {
        if (file == null) return false;
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
            writelog("FileUnits -> WriteObjectToFile\n" + e);
        }
        return false;
    }

    @NonNull
    public static String FormatSize(Float size2) {
        float KB = 1024f;
        float MB = KB * KB;
        float GB = MB * KB;
        float TB = GB * KB;

        String suffix;
        float size;

        if (size2 >= TB) {
            suffix = "TB";
            size = size2 / TB;
        }
        else if (size2 >= GB) {
            suffix = "GB";
            size = size2 / GB;
        }
        else if (size2 >= MB) {
            suffix = "MB";
            size = size2 / MB;
        }
        else if (size2 >= KB) {
            suffix = "KB";
            size = size2 / KB;
        }
        else return size2 + "B";

        DecimalFormat format = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return format.format(size) + suffix;
    }


}
