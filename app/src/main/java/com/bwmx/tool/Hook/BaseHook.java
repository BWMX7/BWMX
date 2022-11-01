package com.bwmx.tool.Hook;


import android.text.TextUtils;

import com.bwmx.tool.Units.FileUnits;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;


public class BaseHook {
    protected static String HookName = "BaseHook";
//    protected static Boolean Switch = false;
//    protected static XC_MethodHook MethodHook1;
//    protected static XC_MethodHook.Unhook Unhook1;


    public static void Log(String Name,String log)
    {
        if (TextUtils.isEmpty(Name)) FileUnits.writelog("[" + HookName + "] " + log);
        else FileUnits.writelog("[" + Name + "] " + log);
    }

    public static XC_MethodHook.Unhook Hook(Method method,XC_MethodHook methodHook, XC_MethodHook.Unhook unhook)
    {
        if (unhook != null) return unhook;
        else if (method != null) return XposedBridge.hookMethod(method, methodHook);
        else return null;
    }

    public static XC_MethodHook.Unhook UnHook(XC_MethodHook.Unhook unhook) {
        if (unhook == null) return null;
        try {
            unhook.unhook();
            return null;
        } catch (Exception e) {
           return unhook;
        }
    }

    public static boolean HasNull(XC_MethodHook.Unhook... unhooks)
    {
        if (unhooks == null) return true;
        for (XC_MethodHook.Unhook unhook : unhooks)
        {
            if (unhook == null) return true;
        }
//        Log(Arrays.toString(unhooks) + "NoNull");
        return false;
    }

    public static Boolean ChangeSwitch(Boolean newSwitch, Boolean oldSwitch)
    {
        if (newSwitch != null) return newSwitch;
        else if (oldSwitch != null) return !oldSwitch;
        else return null;
    }

}
