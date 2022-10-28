package com.bwmx.tool.Hook;

import android.os.Bundle;

import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.io.IOException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class StructMsgHook {
    public static void Hook() throws IOException {
        Method MethodIfExists1 = MethodFinder.GetMethod("StructMsgFactory", "init");
        if (MethodIfExists1 != null) {
//                FileUnits.writelog("StructMsgFactory OK");
                XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Bundle bundle = (Bundle) param.args[0];
                        int i = bundle.getInt("req_type", 146);
                        if (i == 1) {
                            bundle.remove("req_share_id");
                            bundle.remove("req_pkg_name");
                            bundle.remove("desc");
                            bundle.remove("image_url_remote");
                            bundle.putLong("req_share_id", 101934507);
                            bundle.putString("res_pkg_name", "com.hicorenational.antifraud");
                            bundle.putString("desc", "国家反诈中心");
                            bundle.putString("image_url_remote", "https://p.qpic.cn/qqconnect/0/app_101934507_1614341161/100?max-age=2592000&t=0");
//                                                        bundle.putLong("req_share_id",100495085);
//                                                        bundle.putString("res_pkg_name","com.netease.cloudmusic");
//                                                        bundle.putString("desc","深夜网抑云");
//                                                        bundle.putString("image_url_remote","http://gchat.qpic.cn/gchatpic_new/0/0-0-BAC7122744423ADBDD48C688600F17B3/0?term=2");
                            FileUnits.writelog("StructMsgFactory " + bundle);
                            param.args[0] = bundle;
                        }
                    }
                });
            }
    }
}