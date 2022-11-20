package com.bwmx.tool.Hook;


import androidx.annotation.NonNull;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;


public class PttRedPacketFlagHook extends BaseHook{
    private static final String HookName = "PttRedPacketFlagHook";
    private static Boolean Switch;

    private static final XC_MethodHook MethodHook1;
    private static final XC_MethodHook MethodHook2;

    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;

    private static final ArrayList<Object> MsgList = new ArrayList<>();

    private static final String[] Code = new String[]{"sss", "ss", "s", "a", "b", "c"};

    static  {
        MethodHook1 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object msg = param.args[0];
//                Log(msg);
//                LogStackTrace(HookName);
                Class<?> msgClass = msg.getClass();
                if (msgClass.equals(MethodFinder.GetClass("MessageForPtt"))) {

                    XposedHelpers.setIntField(msg, "voiceRedPacketFlag", 2);
                    XposedHelpers.callMethod(msg, "saveExtInfoToExtStr", "voice_score_id", Code[new Random().nextInt(Code.length)]);
                    XposedHelpers.setBooleanField(msg, "isResend" , true);

                    try {
                        JSONObject json = PluginTool.Hitokoto("");
//                        Log(json);
                        String say = json.getString("hitokoto");
                        String name = json.getString("from");
                        String text2;
                        if (!json.isNull("from_who"))
                        {
                        String whi = json.getString("from_who");
                        if (name.equals(whi)) text2 = name;
                        else text2 = "《" + name + "》- " + whi;
                        }
                        else text2 = name;
//                        String text = "Text:\n"+say+"\nFrom:\n"+text2;
                        XposedHelpers.setObjectField(msg, "sttText", say + " —— " + text2);
                        XposedHelpers.setIntField(msg, "sttAbility", 2);
                    }
                    catch (Exception exception) {
                        Log(exception);
                    }

                    ReSetTime(msg);
                }
            }
        };

        MethodHook2 = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object msg = param.args[1];


                if (MsgList.contains(msg)) return;
//                Log(msg);
//                LogStackTrace(HookName);


                if (!(Boolean) XposedHelpers.callMethod(msg, "isSendFromLocal")) {
                    new java.lang.Thread(() -> {
                        XposedHelpers.setIntField(msg, "voiceRedPacketFlag", 0);
                        XposedHelpers.callMethod(msg, "saveExtInfoToExtStr", "voice_score_id", "");
//                        XposedHelpers.setBooleanField(msg, "isResend", false);

//                        XposedHelpers.setObjectField(msg, "sttText", null);
//                        XposedHelpers.setIntField(msg, "sttAbility", 2);

                        XposedHelpers.setIntField(msg, "autoToText", 1);
                        XposedHelpers.setIntField(msg, "sttAbility", 3);
                        XposedHelpers.setBooleanField(msg, "expandStt", true);
                        ReSetTime(msg);
                    }).start();
                }
            }
            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object msg = param.args[1];
                if (MsgList.contains(msg)) return;
                XposedHelpers.setIntField(msg, "sttAbility", 3);
                MsgList.add(msg);
            }
        };

        Switch = Main.HookSwitches.GetSwitch(HookName,true);
    }

    public static void Init() {
        Log("Hook " + ChangeSwitch(Switch));
    }

    private static void Log(Object log)
    {
        Log(HookName, log);
    }

    private static Boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("BaseQQMessageFacade", "AddToMsgList");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);

        Method MethodIfExists2 = MethodFinder.GetMethod("PttItemBuilder", "Time");
        Unhook2 = Hook(MethodIfExists2, MethodHook2, Unhook2);
        return !HasNull(Unhook1, Unhook2);
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


    private static void ReSetTime(Object msg)
    {
        String path = (String) XposedHelpers.callMethod(msg, "getLocalFilePath");
        int duration = (int) (PluginTool.GetAudioDuration(path) / 1000);
        XposedHelpers.setIntField(msg, "voiceLength", duration);
        XposedHelpers.setObjectField(msg, "timeStr", TimeToStr(duration));
        XposedHelpers.callMethod(msg, "prewrite");
    }

    @NonNull
    private static String TimeToStr(Integer i) {
        if (i == null) return "0\"";
        String str;
        int i2 = i / 60;
        int i3 = i % 60;
        if (i2 > 0) {
            str = i2 + "'";
        } else {
            str = "";
        }
        return str + i3 + "\"";
    }


}
