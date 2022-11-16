package com.bwmx.tool.Hook;


import androidx.annotation.NonNull;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;


public class PttRedPacketFlagHook extends BaseHook{
    protected static String HookName = "PttRedPacketFlagHook";
    protected static Boolean Switch;

    protected static XC_MethodHook MethodHook1;
    protected static XC_MethodHook.Unhook Unhook1;

    static  {
        MethodHook1 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object msg = param.args[0];
                Class<?> msgClass = msg.getClass();
                if (msgClass.getName().contains("MessageForPtt")) {
                    XposedHelpers.setBooleanField(msg, "isResend" , true);
                    XposedHelpers.setIntField(msg, "voiceRedPacketFlag", 2);
                    XposedHelpers.callMethod(msg, "saveExtInfoToExtStr", "voice_score_id", "sss");
                }
            }
        };

        Switch = Main.HookSwitches.GetSwitch(HookName,true);
    }

    public static void Init() {
        Log("Hook " + ChangeSwitch(Switch));
    }

    private static void Log(String log)
    {
        Log(HookName, log);
    }

    private static Boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("BaseQQMessageFacade", "AddToMsgList");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
        return !HasNull(Unhook1);
    }

    @NonNull
    private static Boolean UnHook() {
        Unhook1 = UnHook(Unhook1);
        return HasNull(Unhook1);
    }

    public static Boolean ChangeSwitch(Boolean newSwitch)
    {
        Switch = ChangeSwitch(newSwitch,Switch);
        if (Switch) return Hook();
        else return !UnHook();
    }


}
