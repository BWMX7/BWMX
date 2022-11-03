package com.bwmx.tool.Hook;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.Data.RecentUserData;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

//在转发选择好友/群聊界面里的最近转发列表里强制添加自己QQ。
public class ForwardRecentDisplayHook extends BaseHook{
    public static RecentUserData recentUserData = new RecentUserData();

    public static String HookName = "ForwardRecentDisplayHook";
    public static Boolean Switch1;
    public static Boolean Switch2;

    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;

    static {
        Switch1 = Main.HookSwitches.GetSwitch("WithoutShowUp", true);
        Switch2 = Main.HookSwitches.GetSwitch("FriendGridAdapter", true);
    }

    public static void Init() {
        Init1();
        Init2();
    }

    private static void Init1()
    {
        if (Switch1) Log("WithoutShowUp Hook " + Hook1());
    }

    private static void Init2()
    {
        if (Switch2) Log("FriendGridAdapter Hook " +Hook2());
    }

    private static void Log(String log) {
        Log(HookName, log);
    }

    private static boolean Hook1() {
        Method MethodIfExists1 = MethodFinder.GetMethod("ForwardRecentActivity", "getRecentForwardListWithoutShowUp");
        Unhook1 = Hook(MethodIfExists1, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object object = param.getResult();
                param.setResult(AddRecentUser(object));
            }
        }, Unhook1);
        return !HasNull(Unhook1);
    }

    private static boolean Hook2() {
        Method MethodIfExists2 = MethodFinder.GetMethod("ForwardSelectionRecentFriendGridAdapter", "DisplayData");
        Unhook2 = Hook(MethodIfExists2, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object object = param.args[0];
                param.args[0] = AddRecentUser(object);
            }
        }, Unhook2);
        return !HasNull(Unhook2);
    }

    private static Boolean UnHook1() {
        Unhook1 = UnHook(Unhook1);
        return HasNull(Unhook1);
    }

    private static Boolean UnHook2() {
        Unhook2 = UnHook(Unhook2);
        return HasNull(Unhook2);
    }

    public static boolean ChangeSwitch1(Boolean newSwitch)
    {
        boolean change = ChangeSwitch(newSwitch, Switch1);
        boolean ok = PutSwitch("WithoutShowUp", change);
        if (ok) Switch1 = change;
        if (Switch1) return Hook1();
        else return !UnHook1();
    }

    public static boolean ChangeSwitch2(Boolean newSwitch)
    {
        boolean change = ChangeSwitch(newSwitch, Switch2);
        boolean ok = PutSwitch("FriendGridAdapter", change);
        if (ok) Switch2 = change;
        if (Switch2) return Hook2();
        else return !UnHook2();
    }

    private static Object AddRecentUser(Object object) throws IllegalAccessException {
        ArrayList<Object> RecentUserList = recentUserData.GetAllData();
        if (RecentUserList == null) return object;

        if (object instanceof List<?>) {
            RecentUserList.addAll((List<?>) object);
        }

//        Log("RecentUserList " + RecentUserList);

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("1",0);
        Iterator<?> it = RecentUserList.iterator();
        while (it.hasNext()) {
            Object RecentUser = it.next();
            Class<?> classes = recentUserData.RecentUserClass;
            if (RecentUser.getClass().equals(classes))
            {
                Field field1 = XposedHelpers.findField(classes, "type");
                int type = (int) field1.get(RecentUser);
                Field field2 = XposedHelpers.findField(classes, "uin");
                String uin = (String) field2.get(RecentUser);
                if (hashMap.containsKey(uin))
                {
                    if (hashMap.get(uin) == type ) it.remove();
                }
                else hashMap.put(uin, type);
            }
        }
        if (!hashMap.containsKey(Main.MyUin)) RecentUserList.add(0, recentUserData.GetSelfData());
//                    FileUnits.writelog("ForwardRecentDisplayHook RecentUserList2 " + RecentUserList);
//                    for (int i = 0; i < recentUserData.RecentUserList.size(); i++)
//                    {
//                    Field field3 = XposedHelpers.findField(ClassIfExists1, "displayName");
//                    field3.set(SelfUser, "强制置前当前QQ");
//                    FileUnits.writelog("ForwardRecentDisplayHook SelfUser " + SelfUser);
//                        RecentUserList.add(i, recentUserData.RecentUserList.get(i));
//                    FileUnits.writelog("ForwardRecentDisplayHook RecentUserList3 " + RecentUserList);
//                    }
//                    RecentUserList.add(0, recentUserData.GetSelfData());
        return RecentUserList;
    }
}
