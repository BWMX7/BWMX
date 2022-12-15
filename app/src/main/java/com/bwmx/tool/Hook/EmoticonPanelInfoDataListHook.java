package com.bwmx.tool.Hook;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.Data.EmotionPanelInfoData;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

//在表情列表里强制添加小表情。
public class EmoticonPanelInfoDataListHook extends BaseHook{
    public static EmotionPanelInfoData EmotionPanelInfoData = new EmotionPanelInfoData();

    public static String HookName = "EmoticonPanelInfoDataListHook";
    public static Boolean Switch;

    private static XC_MethodHook.Unhook Unhook;

    public static void Init()
    {
        Switch = Main.HookSwitches.GetSwitch(HookName);
        if (Switch) Log("Hook " + Hook());
    }


    private static void Log(Object log) {
        Log(HookName, log);
    }

    private static boolean Hook() {
//        Method MethodIfExists1 = MethodFinder.GetMethod("EmoticonPanelController", "getPanelDataList");
        Method MethodIfExists1 = MethodFinder.GetMethod("EmoticonPanelTabSortHelper", "getSortEmotionPanelInfoList");
        Unhook = Hook(MethodIfExists1, new XC_MethodHook() {
            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                param.setResult(AddEmoticonTabItem(param.getResult()));
//                XposedHelpers.callMethod(param.thisObject, "updateTabSortLastSelectedSecondTabIndex");
//            }
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
//                LogStackTrace(HookName);
                param.args[0] = (AddEmoticonTabItem(param.args[0]));
            }
        }, Unhook);
        return !HasNull(Unhook);
    }


    private static boolean UnHook() {
        Unhook = UnHook(Unhook);
        return HasNull(Unhook);
    }


    public static boolean ChangeSwitch(Boolean newSwitch)
    {
        boolean change = ChangeSwitch(newSwitch, Switch);
        boolean ok = PutSwitch(HookName, change);
        if (ok) Switch = change;
        if (Switch) return Hook();
        else return !UnHook();
    }

    @NonNull
    private static Object AddEmoticonTabItem(Object object) {
        ArrayList<Object> emotionPanelInfoList = EmotionPanelInfoData.GetAllData();

        if (object instanceof List<?>) {
            emotionPanelInfoList.addAll(0, (List<?>) object);
        }

//        Log("EmotionPanelInfoList " + emotionPanelInfoList);


        HashMap<String, Integer> hashMap = new HashMap<>();
        Iterator<?> it = emotionPanelInfoList.iterator();
        Class<?> classes = EmotionPanelInfoData.EmotionPanelInfoClass;
        while (it.hasNext()) {
            Object emotionPanelInfo = it.next();
            if (emotionPanelInfo == null) it.remove();
            else if (emotionPanelInfo.getClass().equals(classes))
            {
                Object emoticonPackage = XposedHelpers.getObjectField(emotionPanelInfo, "emotionPkg");
                if (emoticonPackage == null) continue;

                String epId = (String) XposedHelpers.getObjectField(emoticonPackage, "epId");
                if (TextUtils.isEmpty(epId)) continue;

                int type = XposedHelpers.getIntField(emotionPanelInfo, "type");

                if (hashMap.containsKey(epId) && hashMap.get(epId) == type)
                {
                    it.remove();
                    continue;
                }
                hashMap.put(epId, type);
                if (type == 10) {
                    XposedHelpers.setIntField(emoticonPackage, "jobType", 4);
                    XposedHelpers.setBooleanField(emoticonPackage, "valid", true);
                }
//                Log(emoticonPackage);
            }
        }
        return emotionPanelInfoList;
    }
}
