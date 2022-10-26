package com.bwmx.tool.Hook;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

//在转发选择好友/群聊界面里的最近转发列表里第强制添加自己QQ。
public class ForwardRecentDisplayHook {

    public static void Hook()
    {
        Class ClassIfExists1 = MethodFinder.GetClass("RecentUser");
        FileUnits.writelog("[萌块]ForwardRecentDisplayHook " + ClassIfExists1);
        Method MethodIfExists1 = MethodFinder.GetMethod("ForwardSelectionRecentFriendGridAdapter", "DisplayData");
        FileUnits.writelog("[萌块]ForwardRecentDisplayHook " + MethodIfExists1);
        if (ClassIfExists1 != null && MethodIfExists1 != null) {
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws IllegalAccessException {
                    Object object = param.args[0];
                    ArrayList<Object> RecentUserList = new ArrayList<>();
                    if (object instanceof List<?>) {
                        List obj = (List) object;
                        RecentUserList.addAll(obj);
                    }
                    FileUnits.writelog("[萌块]ForwardRecentDisplayHook RecentUserList " + RecentUserList);
                    Iterator it = RecentUserList.iterator();
                    while (it.hasNext()) {
                        Object RecentUser = it.next();
                        if (RecentUser.getClass().equals(ClassIfExists1))
                        {
                            Field field1 = XposedHelpers.findField(ClassIfExists1, "type");
                            int type = (int) field1.get(RecentUser);
                            Field field2 = XposedHelpers.findField(ClassIfExists1, "uin");
                            String uin = (String) field2.get(RecentUser);
                            if (type == 0 && uin.equals(Main.MyUin))
                            {
                                it.remove();
                            }
                        }
                    }
//                    FileUnits.writelog("[萌块]ForwardRecentDisplayHook RecentUserList2 " + RecentUserList);
                    Object SelfUser = XposedHelpers.newInstance(ClassIfExists1, Main.MyUin, 0);
//                    Field field3 = XposedHelpers.findField(ClassIfExists1, "displayName");
//                    field3.set(SelfUser, "[萌块]强制置前当前QQ");
//                    FileUnits.writelog("[萌块]ForwardRecentDisplayHook SelfUser " + SelfUser);
                    RecentUserList.add(0,SelfUser);
//                    FileUnits.writelog("[萌块]ForwardRecentDisplayHook RecentUserList3 " + RecentUserList);
                    param.args[0] = RecentUserList;
                }
            });
        }
    }
}
