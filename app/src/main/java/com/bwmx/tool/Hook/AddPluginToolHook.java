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

    private static void Log(String log) {
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
        Object[] obj = new Object[2];
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
                    obj[1] = BubbleTextColorHook.ChangeSwitch(true);
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
            default:
                return objArr;
        }
        Log(name + Arrays.toString(objArr) + " -> " + Arrays.toString(obj));
        return obj;
    }

}
