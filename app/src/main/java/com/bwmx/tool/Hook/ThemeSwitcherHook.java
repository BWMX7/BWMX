package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;


public class ThemeSwitcherHook {

    public static void Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("ThemeHandler", "startSwitch");
        if (MethodIfExists1 != null) {
//                FileUnits.writelog("[萌块]StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    FileUnits.writelog("[萌块]ThemeSwitcherHook ThemeHandler Stop Change To " + param.args[0]);
                    return null;
                }
            });
        }

        Method MethodIfExists2 = MethodFinder.GetMethod("NormalNightModeHandler", "startSwitch");
        if (MethodIfExists2 != null) {
//                FileUnits.writelog("[萌块]StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    FileUnits.writelog("[萌块]ThemeSwitcherHook NormalNightModeHandler Stop Change To " + param.args[0]);
                    return null;
                }
            });
        }
    }

}
