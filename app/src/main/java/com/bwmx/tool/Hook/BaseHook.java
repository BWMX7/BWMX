package com.bwmx.tool.Hook;


import android.text.TextUtils;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;


public class BaseHook {
    protected static String HookName = "BaseHook";

    public static void Log(String Name,Object log)
    {
        if (TextUtils.isEmpty(Name)) FileUnits.writelog("[" + HookName + "]" + log);
        else FileUnits.writelog("[" + Name + "]" + log);
    }

    public static void LogStackTrace(String Name)
    {
        StackTraceElement[] stackTraceElements = new Throwable(Name).getStackTrace();
        StringBuilder log = new StringBuilder();
        log.append("堆栈");
        // 使用for循环打印 调用栈查看调用关系
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            log.append("\n");
            log.append(stackTraceElement.toString());
        }
        Log(Name, log.toString());
    }



    protected static XC_MethodHook.Unhook Hook(Method method,XC_MethodHook methodHook, XC_MethodHook.Unhook unhook)
    {
        if (unhook != null) return unhook;
        else if (method != null) return XposedBridge.hookMethod(method, methodHook);
        else return null;
    }

    protected static XC_MethodHook.Unhook UnHook(XC_MethodHook.Unhook unhook) {
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

    protected static boolean ChangeSwitch(Boolean newSwitch, Boolean oldSwitch)
    {
        boolean change;
        if (newSwitch != null) change = newSwitch;
        else if (oldSwitch != null) change = !oldSwitch;
        else change = true;
        if (change != Boolean.TRUE.equals(oldSwitch)) Log("", "ChangeSwitch To " + change);
        return change;
    }

    public static boolean PutSwitch(String Name, boolean Switch)
    {
        return Main.HookSwitches.PutSwitch(Name, Switch);
    }

}
