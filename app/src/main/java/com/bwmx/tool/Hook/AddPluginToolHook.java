package com.bwmx.tool.Hook;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import java.lang.reflect.Method;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class AddPluginToolHook extends BaseHook{
    protected static String HookName = "AddPluginToolHook";

    protected static XC_MethodHook MethodHook1;
    protected static XC_MethodHook.Unhook Unhook1;

    static
    {
        MethodHook1 = new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                String name = (String) param.args[0];
                Object[] objArr = (Object[]) param.args[1];
                return AddPlugin(name, objArr);
            }
        };
    }

    public static void Init() {
        Log("Hook " + Hook());
    }

    private static void Log(Object log) {
        Log(HookName, log);
    }

    private static Boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("QQAppInterface", "unitTestLog");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
//        Log("Hook " + Unhook1);
        return !HasNull(Unhook1);
    }

    @Nullable
    private static Object[] AddPlugin(@NonNull String name, Object[] objArr)
    {
        Object[] obj = new Object[1];
        switch (name) {
            case "CheckLoad":
                obj[0] = true;
                break;
            case "GetMiniAppCode":
                obj[0] = MiniAppLogin.Code;
                break;
            case "RemoveColor":
                obj[0] = PluginTool.RemoveColor((Bitmap) objArr[0], (int) objArr[1], (int) objArr[2]);
                break;
            case "SetNewColor":
                obj[0] = BubbleTextColorHook.BubbleData.SetItemData((String) objArr[0], (String) objArr[1], objArr[2]);
                if (!BubbleTextColorHook.Switch) {
                    obj[0] = BubbleTextColorHook.ChangeSwitch(true);
                }
                break;
            case "ChangeStopScroller":
                obj[0] = MsgListScrollerHook.ChangeSwitch((Boolean) objArr[0]);
                break;
            case "SetAPP":
                obj[0] = SignatureCheckHook.APK.PutUserAPK(objArr[0]);
                break;
            case "ChangeRecentUser":
                obj[0] = ForwardRecentDisplayHook.recentUserData.ChangeRecentUser((String) objArr[0], (String) objArr[1], (Integer) objArr[2]);
                break;
            case "ThemeHook":
                Boolean change = (Boolean) objArr[0];
                if (change == null) obj[0] = VasSwitcherHook.ChangeSwitch1(null);
                else if (change && !VasSwitcherHook.Switch1) obj[0] = VasSwitcherHook.ChangeSwitch1(true);
                else if (!change && VasSwitcherHook.Switch1) obj[0] = VasSwitcherHook.ChangeSwitch1(false);
                break;
            case "BubbleHook":
                change = (Boolean) objArr[0];
                if (change == null) obj[0] = VasSwitcherHook.ChangeSwitch2(null);
                else if (change && !VasSwitcherHook.Switch2) obj[0] = VasSwitcherHook.ChangeSwitch2(true);
                else if (!change && VasSwitcherHook.Switch2) obj[0] = VasSwitcherHook.ChangeSwitch2(false);
                break;
//            case "Tool" :
//                try {
//                    Field field1 = XposedHelpers.findField(objArr[0].getClass(), "javaObject");
//                    Log(field1.toString());
//                    Object obj2 = field1.get(objArr[0]);
//                    XposedHelpers.callMethod(obj2, "Toast", "萌块便捷设置与萌块通讯成功");
//                    obj[0] = "成功";
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case "RestartLoad" :
//                try {
//                    Field field1 = XposedHelpers.findField(objArr[0].getClass(), "javaObject");
//                    Log(field1.toString());
//                    Object obj2 = field1.get(objArr[0]);
//                    Log(obj2);
//                    Field field2 = XposedHelpers.findField(obj2.getClass(), "info_");
//                    Log(field2.toString());
//                    Object PluginInfo = field2.get(obj2);
//                    Log(PluginInfo);
//                    Field field3 = XposedHelpers.findField(PluginInfo.getClass(), "a");
//                    Log(field3.toString());
//                    String PluginID = (String) field3.get(PluginInfo);
//                    Log(PluginID);
//                    Class<?> classIfExists = XposedHelpers.findClassIfExists("cc.hicore.qtool.JavaPlugin.Controller.a", PluginInfo.getClass().getClassLoader());
//                    if (classIfExists != null)
//                    {
//                        Log(classIfExists);
//                        XposedHelpers.callStaticMethod(classIfExists, "j", PluginID);
//                        XposedHelpers.callStaticMethod(classIfExists, "f", PluginInfo);
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                break;
            case "AddMenuItem" :
                Tool tool = new Tool(objArr[0], (String) objArr[1], (String) objArr[2], (Integer) objArr[3]);
                QQCustomMenuItemHook.addItem((String[]) objArr[4], tool);
                obj[0] = "成功";
                break;
            default:
                return objArr;
        }
        Log(name + Arrays.toString(objArr) + " -> " + Arrays.toString(obj));
        return obj;
    }

    private static class Tool extends QQCustomMenuItemHook.Click
    {
        Object Plugin;
        Object BshMethod;
        Object Interpreter;

        Tool(Object plugin, String bshMethodName, String name, int id) {
            super(name, id);
            Plugin = plugin;
            Object NameSpace = XposedHelpers.getObjectField(Plugin, "namespace");
            BshMethod = XposedHelpers.callMethod(NameSpace, "getMethod", bshMethodName, new Class[]{Object.class});
            Interpreter = XposedHelpers.getObjectField(Plugin, "declaringInterpreter");
            Log(BshMethod + " -> " + Interpreter);
        }

        @Override
        public void run(XC_MethodHook.MethodHookParam param) {
            if (BshMethod == null || Interpreter == null || param == null) return;
            Object msg = param.args[2];
            Log(this + " -> " + msg);
            XposedHelpers.callMethod(BshMethod, "invoke", new Object[]{msg}, Interpreter);
        }
    }
}
