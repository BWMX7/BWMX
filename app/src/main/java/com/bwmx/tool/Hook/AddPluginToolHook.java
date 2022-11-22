package com.bwmx.tool.Hook;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class AddPluginToolHook extends BaseHook{
    protected static String HookName = "AddPluginToolHook";

    protected static XC_MethodHook MethodHook1;
    protected static XC_MethodHook.Unhook Unhook1;

    static {
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
    private static Object[] AddPlugin(@NonNull String name, Object[] objArr) {
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
            case "Tool" :
                ClassLoader classLoader = objArr[0].getClass().getClassLoader();
                Class<?> aClass = XposedHelpers.findClassIfExists("bsh.classpath.BshLoaderManager",classLoader);
                XposedHelpers.callStaticMethod(aClass,"addClassLoader", classLoader);
                XposedHelpers.callStaticMethod(aClass,"addClassLoader", Main.HookSwitches.getClass().getClassLoader());
                obj[0] = "成功";
                Log(XposedHelpers.getStaticObjectField(aClass, "loaders"));
                break;
            case "RestartLoad" :
                try {
                    Field field1 = XposedHelpers.findField(objArr[0].getClass(), "javaObject");
                    Log(field1.toString());
                    Object obj2 = field1.get(objArr[0]);
                    Log(obj2);
                    Field field2 = XposedHelpers.findField(obj2.getClass(), "info_");
                    Log(field2.toString());
                    Object PluginInfo = field2.get(obj2);
                    Log(PluginInfo);
                    Field field3 = XposedHelpers.findField(PluginInfo.getClass(), "a");
                    Log(field3.toString());
                    String PluginID = (String) field3.get(PluginInfo);
                    Log(PluginID);
                    Class<?> classIfExists = XposedHelpers.findClassIfExists("cc.hicore.qtool.JavaPlugin.Controller.a", PluginInfo.getClass().getClassLoader());
                    if (classIfExists != null)
                    {
                        Log(classIfExists);
                        XposedHelpers.callStaticMethod(classIfExists, "j", PluginID);
                        XposedHelpers.callStaticMethod(classIfExists, "f", PluginInfo);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case "AddMenuItem" :
                Tool tool = new Tool(objArr[0], (String) objArr[1], (String) objArr[2], (Integer) objArr[3]);
                QQCustomMenuItemHook.addItem((String[]) objArr[4], tool);
                obj[0] = "成功";
                break;
            case "OnSend" :
                PluginCallBack pluginCallBack = new PluginCallBack(objArr[0], (String) objArr[1]);
                obj[0] = SendMsgHook.addCallBack(pluginCallBack);
                break;
            default:
                return objArr;
        }
        Log(name + Arrays.toString(objArr) + " -> " + Arrays.toString(obj));
        return obj;
    }

     private static class Tool extends QQCustomMenuItemHook.Click {
        PluginCallBack pluginCallBack;

        Tool(Object plugin, String bshMethodName, String name, int id) {
            super(name, id);
            pluginCallBack = new PluginCallBack(plugin, bshMethodName);
        }

        @Override
        public void run(XC_MethodHook.MethodHookParam param) {
            if (param == null) return;
            Object msg = param.args[2];
            Log(msg);
            Log(pluginCallBack.onMsg(msg));
        }
    }

    private static class PluginCallBack extends SendMsgHook.CallBack {
        Object Plugin;
        Object NameSpace;
        Object BshMethod;
        Object Interpreter;

        PluginCallBack(Object plugin, String bshMethodName) {
            super("Plugin_" + bshMethodName);
            Plugin = plugin;
            NameSpace = XposedHelpers.getObjectField(Plugin, "namespace");
            BshMethod = XposedHelpers.callMethod(NameSpace, "getMethod", bshMethodName, new Class[]{Object.class});
            Interpreter = XposedHelpers.getObjectField(Plugin, "declaringInterpreter");
            Log(this + " -> " + BshMethod);
        }

        @Override
        public String onMsg(Object msg) {
            if (NameSpace == null || BshMethod == null || Interpreter == null) return Remove;
            Object methods = XposedHelpers.getObjectField(NameSpace, "methods");
//            Log(methods);

            if (methods == null) return Remove;
            if (!(methods instanceof Hashtable)) return Remove;
//            if (!((Hashtable<?, ?>)methods).contains(BshMethod)) return Remove;

            super.onMsg(msg);
            if (msg == null) return Null;
            return String.valueOf(XposedHelpers.callMethod(BshMethod, "invoke", new Object[]{msg}, Interpreter));
        }
    }

}
