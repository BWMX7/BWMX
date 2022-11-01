package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;


public class VasSwitcherHook extends BaseHook
{
    public static String HookName = "VasSwitcherHook";
    public static Boolean Switch = false;

    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;
    private static XC_MethodHook.Unhook Unhook3;


    public static void Init1()
    {
        Log("ThemeSwitcher Hook " + Hook1());
    }

    public static void Init2()
    {
        Log("BubbleSwitcher Hook " +Hook2());
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
                Log("[ThemeSwitcher]ThemeHandler Stop Change To " + param.args[0]);
                return null;
            }
        }, Unhook1);

        Method MethodIfExists2 = MethodFinder.GetMethod("NormalNightModeHandler", "SwitchTheme");
        Unhook2 = Hook(MethodIfExists2, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                Log("[ThemeSwitcher]NormalNightModeHandler Stop Change To " + param.args[0]);
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
                        Log("[BubbleSwitcher]GetStrangerVasInfoHandler Stop Change To " + id);
                    }
                } catch (IllegalAccessException e) {
                    Log("[BubbleSwitcher]GetStrangerVasInfoHandler " +e);
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

}
