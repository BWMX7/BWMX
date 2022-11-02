package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.FileUnits;
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
//                    StackTraceElement[] wodelogs = new Throwable("wodelog").getStackTrace();
//                    String log = "";
//                    // 使用for循环打印 调用栈查看调用关系
//                    for(int i = 0;i<wodelogs.length;i++){
//                        log +=  wodelogs[i].toString();
//                    }
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
        //        Method MethodIfExists2 = MethodFinder.GetMethod("MsgListScroller", "scroll");
//        if (MethodIfExists1 != null && MethodIfExists2 != null) {
//                FileUnits.writelog("StructMsgFactory OK");
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
        Log("ChangeSwitch To " + Switch);
        if (Switch) return Hook();
        else return !UnHook();
    }


//    public static boolean ChangeStopScroller(Boolean open)
//    {
//        if (open == null) OPEN = !OPEN;
//        else OPEN = open;
//        return OPEN;
//    }

}
