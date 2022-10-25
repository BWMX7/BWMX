package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.FileUnits;

import java.lang.reflect.Method;


import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


public class MsgListScrollerHook {
    private static boolean OPEN = false;
    public static void Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("MsgListScroller", "scrollto0");
        Method MethodIfExists2 = MethodFinder.GetMethod("MsgListScroller", "scroll");
        if (MethodIfExists1 != null && MethodIfExists2 != null) {
//                FileUnits.writelog("[萌块]StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
//                    StackTraceElement[] wodelogs = new Throwable("wodelog").getStackTrace();
//                    String log = "";
//                    // 使用for循环打印 调用栈查看调用关系
//                    for(int i = 0;i<wodelogs.length;i++){
//                        log +=  wodelogs[i].toString();
//                    }
//                    FileUnits.writelog("[萌块]TroopMsgListScroller\n" + log);
                    if (OPEN) FileUnits.writelog("[萌块]TroopMsgListScroller stop once scroll" );
                    else {
//                        Method Method = MethodFinder.GetMethod("MsgListScroller", "scroll");
                        String name = MethodIfExists2.getName();
                        XposedHelpers.callMethod(param.thisObject, name, 0L);
                    }
                    return null;
                }
            });
        }
    }

    public static boolean ChangeStopScroller(Boolean open)
    {
        if (open == null) OPEN = !OPEN;
        else OPEN = open;
        return OPEN;
    }
}
