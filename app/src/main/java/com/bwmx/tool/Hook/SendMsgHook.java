package com.bwmx.tool.Hook;

import androidx.annotation.NonNull;

import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import de.robv.android.xposed.XC_MethodHook;

public class SendMsgHook extends BaseHook{

    public static String HookName = "SendMsgHook";

    private static final XC_MethodHook MethodHook1;
    private static XC_MethodHook.Unhook Unhook1;

    private static final ArrayList<CallBack> CallBack = new ArrayList<>();

    static {
        MethodHook1 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object msg = param.args[0];
//                LogMsg(msg);

                for (Iterator<CallBack> it = CallBack.iterator(); it.hasNext(); ) {
                    CallBack callBack = it.next();

//                       LogStackTrace(HookName);
//                       Log(Arrays.toString(param.args));



                    String back = callBack.onMsg(msg);

                    Log(callBack + " Return " + back);
//                    callBack.onMsg(msg);
                    if (back.equals(callBack.Remove)) {
                        Log(callBack + " Remove " + removeCallBack(it));
                    }

                    else if (back.equals(callBack.StopToSend)) {
                        PluginTool.ShowToast("Stop To Send Msg!\n at " + callBack);
                        Log(callBack + " Stop To Send " + msg);
                        param.setResult(null);
                        break;
                    }

                    else if (!back.equals(callBack.Null)) break;
//                      Log(back.getClass());

//                    if (back != msg) {
//                        PluginTool.ShowToast("You Can't Replace Msg To Other!\n at " + callBack);
//                        Log(callBack + " Can't Replace Msg To " + back);
//                    }
//                    else return;
                }
//                LogMsg(msg);
            }
        };

    }

    private static void Log(Object log) {
        Log(HookName, log);
    }

    private static boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("BaseQQMessageFacade", "SendMessage");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
        return !HasNull(Unhook1);
    }

    private static boolean UnHook() {
        Unhook1 = UnHook(Unhook1);
        return HasNull(Unhook1);
    }

    public static boolean addCallBack(CallBack pluginCallBack) {
        CallBack.add(pluginCallBack);
        if (HasNull(Unhook1)) return Hook();
        return true;
    }


    public static boolean removeCallBack(Iterator<CallBack> it) {
        if (it == null) return false;
        it.remove();
        if (CallBack.size() == 0) return UnHook();
        return true;
    }

    public static void LogMsg(@NonNull Object msg)
    {
        Field[] fields = msg.getClass().getFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            try {
//                if (!field.toGenericString().contains("ForPic")) break;
                 stringBuilder.append(field.toGenericString().substring(field.toGenericString().lastIndexOf(".")+1)).append(" = ").append(field.get(msg)).append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Log(msg + "\n" + stringBuilder);
    }

    public static class CallBack {
        String Name;

        String Null = "null";
        String Remove = "Remove";
        String StopToSend = "StopToSend";

        CallBack(String name) {
            Name = name;
        }

        public String onMsg(Object msg) {
//            Log(this + " onMsg " + msg);
            return "";
        }

        @NonNull
        @Override
        public String toString() {
            return "CallBack(" + Name + ")";
        }
    }

}