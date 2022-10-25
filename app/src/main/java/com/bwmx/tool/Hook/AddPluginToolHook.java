package com.bwmx.tool.Hook;

import android.graphics.Bitmap;

import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import java.lang.reflect.Method;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class AddPluginToolHook {
    public static void Hook() {
//        Method MethodIfExists1 = MethodFinder.GetMethod("PatchRedirectCenter", "addRedirector");
//        if (MethodIfExists1 != null) {
//            try {
//                Object o = XposedHelpers.callStaticMethod(MethodIfExists1.getDeclaringClass(), MethodIfExists1.getName(), 779412117,  SignatureData.getInstance());
//                FileUnits.writelog("[萌块]AddClassHook " + o);
//            } catch (Throwable e) {
//                FileUnits.writelog("[萌块]AddClassHook Error\n" + e);
//            }
//        }
        Method MethodIfExists2 = MethodFinder.GetMethod("QQAppInterface", "unitTestLog");
        if (MethodIfExists2 != null) {
//                FileUnits.writelog("[萌块]StructMsgFactory OK");
            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    String name = (String) param.args[0];
                    Object[] objArr = (Object[]) param.args[1];
                    Object[] obj = new Object[2];
//                    FileUnits.writelog("[萌块]AddClassHook \n" + name);
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
                            if (BubbleTextColorHook.Switch == null || !BubbleTextColorHook.Switch) {
                                BubbleTextColorHook.Switch = true;
                                obj[1] = BubbleTextColorHook.BubbleData.SetItemData((String) objArr[0], "Switch", true);
                                BubbleTextColorHook.Hook();
                            }
                            break;
                        case "ChangeStopScroller":
                            obj[0] = MsgListScrollerHook.ChangeStopScroller((Boolean) objArr[0]);
                            break;
                        default:
                            return;
                    }
                    FileUnits.writelog("[萌块]AddClassHook " + name + "\n" + Arrays.toString(objArr));
//                    FileUnits.writelog("[萌块]AddClassHook \n" + objs[0]);
                    param.setResult(obj);
                }
            });
        }
    }
}
