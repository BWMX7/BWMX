package com.bwmx.tool.Hook;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.Data.RecentUserData;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

//在转发选择好友/群聊界面里的最近转发列表里强制添加自己QQ。
public class ForwardRecentDisplayHook {
    public static RecentUserData recentUserData = new RecentUserData();

    public static void Hook()
    {

        Method MethodIfExists1 = MethodFinder.GetMethod("ForwardSelectionRecentFriendGridAdapter", "DisplayData");
        FileUnits.writelog("ForwardRecentDisplayHook " + MethodIfExists1);
        if (recentUserData.RecentUserClass != null && MethodIfExists1 != null) {
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws IllegalAccessException {
                    ArrayList<Object> RecentUserList = recentUserData.GetAllData();
                    if (RecentUserList == null) return;

                    Object object = param.args[0];
                    if (object instanceof List<?>) {
                        RecentUserList.addAll((List<?>) object);
                    }
//                    FileUnits.writelog("ForwardRecentDisplayHook RecentUserList " + RecentUserList);

                    HashMap<String, Integer> hashMap = new HashMap<>();
                    hashMap.put("1",0);
                    Iterator<?> it = RecentUserList.iterator();
                    while (it.hasNext()) {
                        Object RecentUser = it.next();
                        if (RecentUser.getClass().equals(recentUserData.RecentUserClass))
                        {
                            Field field1 = XposedHelpers.findField(recentUserData.RecentUserClass, "type");
                            int type = (int) field1.get(RecentUser);
                            Field field2 = XposedHelpers.findField(recentUserData.RecentUserClass, "uin");
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
                    param.args[0] = RecentUserList;
                }
            });
        }
    }
}
