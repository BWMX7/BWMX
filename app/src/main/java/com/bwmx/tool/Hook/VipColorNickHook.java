package com.bwmx.tool.Hook;

import android.view.View;

import androidx.annotation.NonNull;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class VipColorNickHook extends BaseHook {

    private static final String HookName = "VipColorNickHook";
    private static Boolean Switch;

    private static final XC_MethodHook MethodHook1;
    private static final XC_MethodHook MethodHook2;

    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;

    static  {
        MethodHook1 = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
//                Field field1 = XposedHelpers.findField(param.args[2].getClass(), "i");
//                View view = (View) field1.get(param.args[2]);
                View view = (View) XposedHelpers.getObjectField(param.args[2], "i");
                MethodFinder.QRoteApi("IVipColorName", "setTextColorGradient", view, "10001");
            }
        };

        MethodHook2 = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
//                        FileUnits.writelog("VipColorNickHook VipData " + param);
//                        int id = (int) param.getResult();
//                        if (id <= 1)
                        param.setResult(2);
                        }
        };

        Switch = Main.HookSwitches.GetSwitch(HookName);
    }

    public static void Init() {
        Log("Hook " + ChangeSwitch(Switch));
    }

    private static void Log(Object log)
    {
        Log(HookName, log);
    }

    private static Boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("RecentTask", "ColorNick");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);

        Method MethodIfExists2 = MethodFinder.GetMethod("VipData", "getColorName");
        Unhook2 = Hook(MethodIfExists2, MethodHook2, Unhook2);

        return !HasNull(Unhook1, Unhook2);
    }

    @NonNull
    private static Boolean UnHook() {
        Unhook1 = UnHook(Unhook1);
        Unhook2 = UnHook(Unhook2);
        return HasNull(Unhook1, Unhook2);
    }

    public static Boolean ChangeSwitch(Boolean newSwitch)
    {
        Switch = ChangeSwitch(newSwitch,Switch);
        if (Switch) return Hook();
        else return !UnHook();
    }
}