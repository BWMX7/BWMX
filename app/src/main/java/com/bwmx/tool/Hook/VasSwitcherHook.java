package com.bwmx.tool.Hook;


import com.bwmx.tool.Main;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;


public class VasSwitcherHook extends BaseHook
{
    public static String HookName = "VasSwitcherHook";
    public static Boolean Switch1;
    public static Boolean Switch2;

    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;
    private static XC_MethodHook.Unhook Unhook3;

    static
    {
        Switch1 = Main.HookSwitches.GetSwitch("ThemeSwitcher");
        Switch2 = Main.HookSwitches.GetSwitch("BubbleSwitcher");
    }

    public static void Init1()
    {
        if (Switch1) Log("ThemeSwitcher Hook " + Hook1());
    }

    public static void Init2()
    {
        if (Switch2) Log("BubbleSwitcher Hook " +Hook2());
    }

    private static void Log(String log)
    {
        Log(HookName, log);
    }

    private static Boolean Hook1()
    {
        Method MethodIfExists1 = MethodFinder.GetMethod("ThemeHandler", "SwitchTheme");
        Unhook1 = Hook(MethodIfExists1, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                Log("ThemeHandler Stop Change Theme To " + param.args[0]);
                return null;
            }
        }, Unhook1);

        Method MethodIfExists2 = MethodFinder.GetMethod("NormalNightModeHandler", "SwitchTheme");
        Unhook2 = Hook(MethodIfExists2, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                Log("NormalNightModeHandler Stop Change ThemeMode To " + param.args[0]);
                return null;
            }
        }, Unhook2);

        return !HasNull(Unhook1, Unhook2);
    }

    private static Boolean Hook2()
    {
        Method MethodIfExists3 = MethodFinder.GetMethod("GetStrangerVasInfoHandler", "SwitchBubble");
        Unhook3 = Hook(MethodIfExists3, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                Object object = param.args[0];
                Field field1 = XposedHelpers.findField(object.getClass(), "uint32_bubble_id");
                try {
                    Object bubble_id = field1.get(object);
                    boolean has = (boolean) XposedHelpers.callMethod(bubble_id, "has");
                    if (has) {
                        int id = (int) XposedHelpers.callMethod(bubble_id, "get");
                        Log("GetStrangerVasInfoHandler Stop Change Bubble To " + id);
                    }
                } catch (IllegalAccessException e) {
                    Log("GetStrangerVasInfoHandler " +e);
                }
                return null;
            }
        }, Unhook3);
        return !HasNull(Unhook3);
    }

    private static Boolean UnHook1() {
        Unhook1 = UnHook(Unhook1);
        Unhook2 = UnHook(Unhook2);
        return HasNull(Unhook1, Unhook2);
    }

    private static Boolean UnHook2() {
        Unhook3 = UnHook(Unhook3);
        return HasNull(Unhook3);
    }

    public static boolean ChangeSwitch1(Boolean newSwitch)
    {
        boolean change = ChangeSwitch(newSwitch, Switch1);
        boolean ok = PutSwitch("ThemeSwitcher", change);
        if (ok) Switch1 = change;
        if (Switch1) return Hook1();
        else return !UnHook1();
    }

    public static boolean ChangeSwitch2(Boolean newSwitch)
    {
        boolean change = ChangeSwitch(newSwitch, Switch2);
        boolean ok = PutSwitch("BubbleSwitcher", change);
        if (ok) Switch2 = change;
        if (Switch2) return Hook2();
        else return !UnHook2();
    }

}
