package com.bwmx.tool.Hook;


import androidx.annotation.NonNull;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;


public class MessageForPttHook extends BaseHook {
    private static final String HookName = "MessageForPttHook";

    private static Boolean Switch1;
    private static Boolean Switch2;

    private static final XC_MethodHook MethodHook1;
    private static final XC_MethodHook MethodHook2;

    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;

    private static final ArrayList<Object> MsgList = new ArrayList<>();
//    private static final ArrayList<Object> SttMsgList = new ArrayList<>();

    private static final ArrayList<String> Code = new ArrayList<>(Arrays.asList("sss", "ss", "s", "a", "b", "c"));
    private static final ArrayList<Integer> SpecialBubbleIdList = new ArrayList<>(Arrays.asList(2243, 2244, 2245, 2438, 2439, 2440, 2441, 2477, 2478, 2479, 2480, 2481, 2482, 2483, 2484, 2485, 2678, 2679, 2680, 2905, 2906, 2907));

    static {
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
                    XposedHelpers.callMethod(msg, "saveExtInfoToExtStr", "voice_score_id", Code.get(new Random().nextInt(Code.size())));
                    XposedHelpers.setBooleanField(msg, "isResend", true);

                    XposedHelpers.setIntField(msg, "voiceChangeFlag", 3);

                    try {
                        JSONObject json = PluginTool.Hitokoto("");
                        if (json != null) {
//                        Log(json);
                            String say = json.getString("hitokoto");
                            String name = json.getString("from");
                            String text2;
                            if (!json.isNull("from_who")) {
                                String whi = json.getString("from_who");
                                if (name.equals(whi)) text2 = name;
                                else text2 = "《" + name + "》- " + whi;
                            } else text2 = name;
//                        String text = "Text:\n"+say+"\nFrom:\n"+text2;
                            XposedHelpers.setObjectField(msg, "sttText", say + " —— " + text2);
                            XposedHelpers.setIntField(msg, "sttAbility", 2);
                        }
                    } catch (Exception exception) {
                        Log(exception);
                    }
                    ReSetTime(msg, false);
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
                        String path = (String) XposedHelpers.callMethod(msg, "getLocalFilePath");
                        String type = PluginTool.GetAudioType(path);
                        if (type.equals("silk") || type.equals("amr")) {
                            XposedHelpers.setIntField(msg, "autoToText", 2);

                            XposedHelpers.setIntField(msg, "sttAbility", 3);
                            XposedHelpers.setObjectField(msg, "sttText", null);

                            XposedHelpers.setIntField(msg, "voiceRedPacketFlag", 0);

                            XposedHelpers.setIntField(msg, "voiceChangeFlag", 0);

//                            XposedHelpers.setBooleanField(msg, "expandStt", true);

//                            if (type.equals("silk")) XposedHelpers.setIntField(msg, "voiceLength", 0);

//                            SttMsgList.add(msg);
                        } else {
                            XposedHelpers.setIntField(msg, "autoToText", 0);
                            XposedHelpers.setIntField(msg, "sttAbility", 0);
                        }

                        int bubbleID = (int) MethodFinder.BusinessHandler("SVIP_HANDLER", "getBubbleIdFromMessageRecord", msg);
                        if (SpecialBubbleIdList.contains(bubbleID)) {
                            int SelfBubbleID = (int) MethodFinder.BusinessHandler("SVIP_HANDLER", "getSelfBubbleId");
                            XposedHelpers.setLongField(msg, "vipBubbleID", SelfBubbleID);
                            Log(bubbleID + " -> " + SelfBubbleID);
                        }

                        MsgList.add(msg);

                    }).start();
                }
                ReSetTime(msg, true);
            }

//            @Override
//            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Object msg = param.args[1];
////                if (!) return;
//
//                if (SttMsgList.contains(msg) && XposedHelpers.getIntField(msg, "sttAbility") == 0) {
//                    XposedHelpers.setIntField(msg, "sttAbility", 3);
////                    XposedHelpers.callMethod(msg, "prewrite");
//                }
//                else SttMsgList.remove(msg);
//
//                if (MsgList.contains(msg)) return;
//
//                ReSetTime(msg, true);
//
//                MsgList.add(msg);
//            }
        };

        Switch1 = Main.HookSwitches.GetSwitch("SendPttHook", true);
        Switch2 = Main.HookSwitches.GetSwitch("ReceivePttHook", true);
    }

    public static void Init() {
        Log("SendPttHook " + ChangeSwitch1(Switch1));
        Log("ReceivePttHook " + ChangeSwitch2(Switch2));
    }

    private static void Log(Object log) {
        Log(HookName, log);
    }

    private static Boolean Hook1() {
        Method MethodIfExists1 = MethodFinder.GetMethod("BaseQQMessageFacade", "AddToMsgList");
//        Method MethodIfExists1 = MethodFinder.GetMethod("BaseQQMessageFacade", "SendMessage");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
        return !HasNull(Unhook1);
    }

    private static boolean Hook2() {
        Method MethodIfExists2 = MethodFinder.GetMethod("PttItemBuilder", "Time");
        Unhook2 = Hook(MethodIfExists2, MethodHook2, Unhook2);
        return !HasNull(Unhook2);
    }

    private static boolean UnHook1() {
        Unhook1 = UnHook(Unhook1);
        return HasNull(Unhook1);
    }

    private static boolean UnHook2() {
        Unhook2 = UnHook(Unhook2);
        return HasNull(Unhook2);
    }

    public static boolean ChangeSwitch1(Boolean newSwitch) {
        Switch1 = ChangeSwitch(newSwitch, Switch1);
        if (Switch1) return Hook1();
        else return !UnHook1();
    }

    public static boolean ChangeSwitch2(Boolean newSwitch) {
        Switch2 = ChangeSwitch(newSwitch, Switch1);
        if (Switch2) return Hook2();
        else return !UnHook2();
    }

    private static void ReSetTime(Object msg, boolean reset) {
        String path = (String) XposedHelpers.callMethod(msg, "getLocalFilePath");

        File file = new File(path);

        float size = (float) file.length();
//        Log(file + " " + size);
//        if (size == 0) return;
        if (size > 0) {
            String type = PluginTool.GetAudioType(file).toUpperCase(Locale.ROOT);
            int duration;
//            if (type.equals("SILK")) {
//                duration = XposedHelpers.getIntField(msg, "voiceLength");
//            } else {
            duration = (int) (PluginTool.GetAudioDuration(file) / 1000);
            XposedHelpers.setIntField(msg, "voiceLength", duration);
//            }
            XposedHelpers.setObjectField(msg, "timeStr", TimeToStr(duration) + "|" + type + "|" + FileUnits.FormatSize(size));
            MsgList.add(msg);
        }
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
