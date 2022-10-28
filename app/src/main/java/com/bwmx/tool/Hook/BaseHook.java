package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.FileUnits;

import java.lang.reflect.Method;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;


public class BaseHook {
    protected static String HookName = "BaseHook";
//    protected static Boolean Switch = false;
//    protected static XC_MethodHook MethodHook1;
//    protected static XC_MethodHook.Unhook Unhook1;



    private static void Log(String log)
    {
        FileUnits.writelog("[" + HookName + "]" + log);
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

    public static Boolean HasNull(XC_MethodHook.Unhook... unhooks)
    {
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
