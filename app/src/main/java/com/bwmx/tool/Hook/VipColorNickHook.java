package com.bwmx.tool.Hook;

import android.view.View;

import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class VipColorNickHook {
    public static void Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("RecentTask", "colornick");
        if (MethodIfExists1 != null) {
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Field field1 = XposedHelpers.findField(param.args[2].getClass(), "i");
                    View view = (View) field1.get(param.args[2]);
                    Object method = MethodFinder.QRoteApi("IVipColorName");
                    XposedHelpers.callMethod(method, "setTextColorGradient", view, "779412117");
                }
            });
        }
        Method MethodIfExists2 = MethodFinder.GetMethod("VipData", "getColorName");
        if (MethodIfExists2 != null) {
            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
//                        FileUnits.writelog("[萌块]VipColorNickHook VipData " + param);
//                        int id = (int) param.getResult();
//                        if (id <= 1)
                    param.setResult(2);
                }
            });
        }
    }
}