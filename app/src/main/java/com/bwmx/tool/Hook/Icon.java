//package com.bwmx.tool;
//
//
//import android.content.res.XModuleResources;
//import android.content.res.XResources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//
//import de.robv.android.xposed.IXposedHookInitPackageResources;
//import de.robv.android.xposed.IXposedHookLoadPackage;
//import de.robv.android.xposed.XposedBridge;
//import de.robv.android.xposed.callbacks.XC_InitPackageResources;
//
//public class Icon implements IXposedHookInitPackageResources {
//    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
//        //只替换systemui应用的资源
//        XposedBridge.log("[萌块]Icon"+resparam);
//        if (!resparam.packageName.equals("com.tencent.mobileqq"))
//            return;
//        XposedBridge.log("[萌块]Icon start");
//        // 替换资源的不同方式
////        resparam.res.setReplacement(0x7f080083, "YEAH!"); // WLAN toggle text. You should not do this because the id is not fixed. Only for framework resources, you could use android.R.string.something
////        resparam.res.setReplacement("com.android.systemui:string/quickpanel_bluetooth_text", "WOO!");
////        resparam.res.setReplacement("com.android.systemui", "string", "quickpanel_gps_text", "HOO!");
////        resparam.res.setReplacement("com.android.systemui", "integer", "config_maxLevelOfSignalStrengthIndicator", 6);
//        resparam.res.setReplacement("com.tencent.mobileqq", "drawable", "icon", new XResources.DrawableLoader() {
//            @Override
//            public Drawable newDrawable(XResources res, int id) throws Throwable {
//
//                Bitmap newBmp = BitmapFactory.decodeFile("../src/main/assets/icon.png");
//                XposedBridge.log("[萌块]Icon"+newBmp);
//                Drawable drawable = new BitmapDrawable(res, newBmp);
//                XposedBridge.log("[萌块]Icon"+drawable);
//                return drawable;
//            }
//        });//你不能直接使用Drawble类进行替换，因为Drawble类可以影响其他引用Ddrawble类实例的ImageView ,最好使用一个包装器。
//    }
//
//}
