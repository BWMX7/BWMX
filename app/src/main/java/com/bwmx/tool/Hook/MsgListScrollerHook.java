package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;


public class MsgListScrollerHook extends BaseHook{
    protected static String HookName = "MsgListScrollerHook";
    protected static Boolean Switch = false;

    protected static XC_MethodHook MethodHook1;
    protected static XC_MethodHook.Unhook Unhook1;

    static  {
        MethodHook1 = new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                Log("Stop Once Scroll" );
//                    FileUnits.writelog("TroopMsgListScroller\n" + log);
//                    if (OPEN) FileUnits.writelog("TroopMsgListScroller stop once scroll" );
//                    else {
////                        Method Method = MethodFinder.GetMethod("MsgListScroller", "scroll");
//                        String name = MethodIfExists2.getName();
//                        XposedHelpers.callMethod(param.thisObject, name, 0L);
//                    }
                return null;
            }
        };
    }

    public static void Init() {
        Log(" -> HookInit");
//        ChangeSwitch(true);
    }

    private static void Log(String log)
    {
        Log(HookName, log);
    }

    private static Boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("MsgListScroller", "ScrollTo0");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
//        Log("Hook " + Unhook1);
        return !HasNull(Unhook1);
    }

    private static Boolean UnHook() {
        Unhook1 = UnHook(Unhook1);
//        Log(" UnHook " +Unhook1);
        return HasNull(Unhook1);
    }

    public static Boolean ChangeSwitch(Boolean newSwitch)
    {
        Switch = ChangeSwitch(newSwitch,Switch);
        if (Switch) return Hook();
        else return !UnHook();
    }


}
