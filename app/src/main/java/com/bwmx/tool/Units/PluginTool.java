package com.bwmx.tool.Units;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bwmx.tool.Main;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

public class PluginTool {

    @Nullable
    public static Bitmap RemoveColor(Bitmap bm, int removecolor, int opt)
    {
        if (bm == null) return null;
        int width = bm.getWidth();
        int height = bm.getHeight();

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        int rr = Color.red(removecolor);
        int rg = Color.green(removecolor);
        int rb = Color.blue(removecolor);
//        int ra = Color.alpha(removecolor);

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);
//        opt=Math.abs(opt);
//        Toast(removecolor+" "+width * height);
        for (int i = 0; i < width * height; i++) {
            int color = oldPx[i];
            if (color == removecolor) newPx[i] = 0;
            else {
//                newPx[num] = color;
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                // a = Color.alpha(color);
//                if (opt >= Math.abs(rr - r) && Math.abs(rb - b) <= opt && Math.abs(rg - g) <= opt)
                if (Math.abs(rr - r) <= opt && Math.abs(rb - b) <= opt && Math.abs(rg - g) <= opt)
                    newPx[i] = 0;
                else newPx[i] = color;
            }
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    public static <T> void ShowToast(T Value){
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(Main.AppContext, String.valueOf(Value), Toast.LENGTH_SHORT).show());
    }
    public static <T> void ShowLongToast(T Value){
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(Main.AppContext, String.valueOf(Value), Toast.LENGTH_LONG).show());
    }

    public static <T> void CopyString(T Value)
    {
        if (Value == null) return;
        ClipboardManager clipboardManager = (ClipboardManager)Main.AppContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, String.valueOf(Value));
        clipboardManager.setPrimaryClip(clipData);
    }

    @NonNull
    public static JSONObject Hitokoto(String word)
    {
        String tp;
        if (word == null) tp = "";
        else {
            switch (word) {
                case "动画":
                    tp = "?c=a";
                    break;
                case "漫画":
                    tp = "?c=b";
                    break;
                case "游戏":
                    tp = "?c=c";
                    break;
                case "文学":
                    tp = "?c=d";
                    break;
                case "原创":
                    tp = "?c=e";
                    break;
                case "网络":
                    tp = "?c=f";
                    break;
                case "其它":
                    tp = "?c=g";
                    break;
                case "影视":
                    tp = "?c=h";
                    break;
                case "诗词":
                    tp = "?c=i";
                    break;
                case "网易":
                    tp = "?c=j";
                    break;
                case "哲学":
                    tp = "?c=k";
                    break;
                default:
                    tp = "";
            }
        }
        String url = "https://v1.hitokoto.cn/" + tp;
        JSONObject msg;
        try
        {
            String tt = HttpGet(url);
            JSONObject json = new JSONObject(tt);
            if(!json.has("hitokoto")) return Hitokoto(word);
            else msg=json;
            return msg;
        }
        catch (JSONException e) {
            return Hitokoto(word);
        }

    }

    @NonNull
    public static String HttpGet(String url)
    {
        if (url == null) return "";

        try {
            if (Thread.currentThread().getName().equals("main")) {
                AtomicReference<String> builder = new AtomicReference<>();
                Thread thread = new Thread(() -> builder.getAndSet(HttpGet(url)));
                thread.start();
                thread.join();
                return builder.get();
            }
        }
        catch (InterruptedException e) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        InputStreamReader isr;
        try {
            URL urlObj = new URL(url);
            URLConnection uc = urlObj.openConnection();
            uc.setConnectTimeout(10000);
            uc.setReadTimeout(10000);
            isr = new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr); //缓冲
            while (true) {
                String line = reader.readLine();
                if (line != null) builder.append(line).append("\n");
                else break;
            }
            isr.close();
        } catch (IOException e) {
            return "";
        }

        if(builder.length() == 0) return builder.toString();

        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    public static long GetAudioDuration(String path){
        File file = new File(path);
        if(!file.exists()) return 0;

        long size = file.length();

        String type = GetAudioType(file);
        if (type.equals("silk")) return 0;

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        long time1 = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        long bitRate = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        mmr.release();
        //Toast(1);
        float time2 = (size * 8000f) / bitRate;
//        FileUnits.writelog(file.getName() + " 读取:" + (time1 / 1000f) + ", 计算: " + (time2 /1000f));
        if(time1 - time2 < 5000) return time1;
        return (long) time2 - 2000;
    }

    public static String GetAudioType(File file)
    {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String text = bufferedReader.readLine();
            bufferedReader.close();
            if (text.startsWith("\u0002#!SILK_V")) return "silk";
            if (text.startsWith("#!AMR")) return "amr";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "other";
    }

}
