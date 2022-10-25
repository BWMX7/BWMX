package com.bwmx.tool.Units;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PluginTool {
    public static Bitmap RemoveColor(Bitmap bm,int removecolor,int opt)
    {
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
            int num = i;
            int color = oldPx[num];
            if (color == removecolor) newPx[num] = 0;
            else {
//                newPx[num] = color;
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                // a = Color.alpha(color);
//                if (opt >= Math.abs(rr - r) && Math.abs(rb - b) <= opt && Math.abs(rg - g) <= opt)
                if (Math.abs(rr - r) <= opt && Math.abs(rb - b) <= opt && Math.abs(rg - g) <= opt)
                    newPx[num] = 0;
                else newPx[num] = color;
            }
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }
}
