package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


public class VasSwitcherHook {

    public static void Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("ThemeHandler", "SwitchTheme");
        if (MethodIfExists1 != null) {
//                FileUnits.writelog("StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    FileUnits.writelog("ThemeSwitcherHook ThemeHandler Stop Change To " + param.args[0]);
                    return null;
                }
            });
        }

        Method MethodIfExists2 = MethodFinder.GetMethod("NormalNightModeHandler", "SwitchTheme");
        if (MethodIfExists2 != null) {
//                FileUnits.writelog("StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    FileUnits.writelog("ThemeSwitcherHook NormalNightModeHandler Stop Change To " + param.args[0]);
                    return null;
                }
            });
        }

        Method MethodIfExists3 = MethodFinder.GetMethod("GetStrangerVasInfoHandler", "SwitchBubble");
        if (MethodIfExists3 != null) {
//                FileUnits.writelog("StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists3, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    Object object = param.args[0];
                    Field field1 = XposedHelpers.findField(object.getClass(), "uint32_bubble_id");
                    try {
                        Object bubble_id = field1.get(object);
                        boolean has = (boolean) XposedHelpers.callMethod(bubble_id, "has");
                        if (has) {
                            int id = (int) XposedHelpers.callMethod(bubble_id, "get");
                            FileUnits.writelog("BubbleSwitcherHook GetStrangerVasInfoHandler Stop Change To " + id);
                        }
                    } catch (IllegalAccessException e) {
                        FileUnits.writelog("BubbleSwitcherHook GetStrangerVasInfoHandler " +e);
                    }
//                    FileUnits.writelog("BubbleSwitcherHook GetStrangerVasInfoHandler Stop Change To " + param.args[0]);
                    return null;
                }
            });
        }
    }

}
