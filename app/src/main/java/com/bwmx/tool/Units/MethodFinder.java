package com.bwmx.tool.Units;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bwmx.tool.Main;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import de.robv.android.xposed.XposedHelpers;

public class MethodFinder {

    private final static HashMap<String, Class<?>> ClassMap = new HashMap<>();
    private final static HashMap<String, Method> MethodMap = new HashMap<>();

//    public Method FindMethod(String classname, Class<?>... obj)
//    {
//        Class<?> classIfExists = XposedHelpers.findClassIfExists(classname, Main.mLoader);
//        if (classIfExists != null) {
//            for (Method method : classIfExists.getDeclaredMethods())
//            {
//                Class<?>[] classes=method.getParameterTypes();
//                if(classes.length==obj.length)
//                {
//                    boolean ok=true;
//                    for(int i=0;i<classes.length;i++)
//                    {
//                        if(!classes[i].getName().equals(obj[i].getName())){
//                            ok=false;
//                            break;
//                        }
//                    }
//                    if(ok) return method;
//                }
//            }
//        }
//        return null;
//    }

    public static Class<?> FindClass(String className)
    {
//        FileUnits.writelog("FindClass " + className);
        Class<?> classIfExists = XposedHelpers.findClassIfExists(className, Main.mLoader);
        if (classIfExists != null) return classIfExists;
        else FileUnits.writelog("Class " + className +  " found error!");
        return null;
    }

    public static Method FindMethod(Class<?> classes, String methodName, Object... obj)
    {
//        FileUnits.writelog("FindMethod " + methodName + "" + Arrays.toString(obj) + " from " + classes);
        if (classes != null) {
            Method method = XposedHelpers.findMethodExactIfExists(classes, methodName, obj);
            if (method != null) FileUnits.writelog("Method " + method.toGenericString() + " found ok!");
            else {
                FileUnits.writelog("Method " + classes + "→" + methodName + " found error!");
            }
            return method;
        }
        return null;
    }

    public static Class<?> FindMyClass(String name)
    {
//        FileUnits.writelog("FindMyClass " + name);
        int QQ_version= HostInfo.getVerCode();
        switch (name) {
            case "QFixApplication": {
                String className = "com.tencent.mobileqq.qfix.QFixApplication";
                return FindClass(className);
            }
            case "QQAppInterface": {
                String className = "com.tencent.mobileqq.app.QQAppInterface";
                return FindClass(className);
            }
            case "BaseApplicationImpl": {
                String className = "com.tencent.common.app.BaseApplicationImpl";
                return FindClass(className);
            }
            case "BaseChatPie": {
                String className = "com.tencent.mobileqq.activity.aio.core.BaseChatPie";
                return FindClass(className);
            }
            case "HelperProvider": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.helper.HelperProvider");
                if (QQ_version >= 9280) {
                    className.set("com.tencent.mobileqq.activity.aio.helper.bw");
                } else if (QQ_version >= 9135) {
                    className.set("com.tencent.mobileqq.activity.aio.helper.bv");
                } else if (QQ_version >= 9000) {
                    className.set("com.tencent.mobileqq.activity.aio.helper.bu");
                } else if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.activity.aio.helper.bs");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
            case "StructMsgFactory": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.structmsg.StructMsgFactory");
                if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.structmsg.j");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
            case "ForwardShareByServerHelper": {
                String className = "com.tencent.mobileqq.forward.ForwardShareByServerHelper";
                return FindClass(className);
            }
            case "TroopMemberListAdapter": {
                String className = "com.tencent.mobileqq.activity.TroopMemberListActivity$ListAdapter";
                return FindClass(className);
            }
            case "MsgListScroller": {
                String className = "com.tencent.mobileqq.activity.aio.anim.MoveToBottomScroller";
                return FindClass(className);
            }
            case "AuthCheck": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.open.agent.auth.presenter.b$f");
                return FindClass(className.get());
            }
            case "VirtualCheck": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.open.virtual.f");
                return FindClass(className.get());
            }
            case "AuthCheckData": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.open.agent.b.b");
                return FindClass(className.get());
            }
            case "VipIconTask": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.utils.VipUtils$UpdateRecentEfficientVipIconTask");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "RecentTask": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.recent.o");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "RecentBaseData": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.recent.RecentBaseData");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "BaseActivity": {
                String className = "com.tencent.mobileqq.app.BaseActivity";
                return FindClass(className);
            }
            case "VipIconView": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.recent.o$a");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "QRoute": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.qroute.QRoute");
                return FindClass(className.get());
            }
            case "IVipColorName": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.vip.api.IVipColorName");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "VipData": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.vip.api.VipData");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "TextItemBuilder": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.item.TextItemBuilder");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "ReplyTextItemBuilder": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.item.ReplyTextItemBuilder");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "MixedMsgItemBuilder": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.item.MixedMsgItemBuilder");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "BaseBubbleBuilder$d": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.BaseBubbleBuilder$d");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "ChatMessage": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.data.ChatMessage");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "BubbleInfo": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.bubble.d");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "TextItemBuilder$h": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.item.TextItemBuilder$h");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "ThemeHandler": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.app.ThemeHandler");
                return FindClass(className.get());
            }
            case "NormalNightModeHandler": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.simpleui.NormalNightModeHandler");
                if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.simpleui.a");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
            case "RecentUser": {
                String className = "com.tencent.mobileqq.data.RecentUser";
                return FindClass(className);
            }
            case "ForwardSelectionRecentFriendGridAdapter": {
                String className = "com.tencent.mobileqq.adapter.ForwardSelectionRecentFriendGridAdapter";
                return FindClass(className);
            }
//            case "BaseAuthorityPresenter": {
//                AtomicReference<String> className = new AtomicReference<>("com.tencent.open.agent.auth.presenter.BaseAuthorityPresenter");
//                return FindClass(className.get());
//            }

        }
        return null;
    }

    public static Method FindMyMethod(String classname, String methodname)
    {
        Class<?> classes = GetClass(classname);
        if (classes == null) return null;
//        FileUnits.writelog("FindMyMethod " + methodname + " from " + classes);
        int QQ_version=HostInfo.getVerCode();
        switch (classname + "." + methodname) {
            case "QFixApplication.attachBaseContext": {
                String methodName = "attachBaseContext";
                return FindMethod(classes, methodName, Context.class);
            }
            case "QQAppInterface.onCreate": {
                return FindMethod(classes, "onCreate", Bundle.class);
            }
            case "QQAppInterface.unitTestLog": {
                return FindMethod(classes, "unitTestLog", String.class, Object[].class);
            }
//            case "AddMyClassloader": {
//                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.qfix.AndroidNClassLoader");
//                AtomicReference<String> methodName = new AtomicReference<>("inject");
//                if (QQ_version < 8845) return null;
//                return FindMethod(className.get(), methodName.get(), PathClassLoader.class, Application.class);
//            }
            case "HelperProvider.init": {
                AtomicReference<String> methodName = new AtomicReference<>("b");
                if (QQ_version >= 8845) methodName.set("M");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), GetClass("BaseChatPie"));
            }
            case "StructMsgFactory.init": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("f");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), Bundle.class);
            }
            case "ForwardShareByServerHelper.SignatureData": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("F");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), Context.class, String.class);
            }
            case "TroopMemberListAdapter.init":
            case "MsgListScroller.scrollto0": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("g");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get());
            }
            case "MsgListScroller.scroll": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("h");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), long.class);
            }
            case "VirtualCheck.Sign": {
                AtomicReference<String> methodName = new AtomicReference<>("j");
                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(),String.class);
            }
            case "AuthCheck.check": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(),boolean.class, int.class, GetClass("AuthCheckData"));
            }
            case "VipIconTask.colornick": {
                AtomicReference<String> methodName = new AtomicReference<>("c");
                if (QQ_version < 9280) return null;
                return FindMethod(classes, methodName.get(),Context.class, String.class, GetClass("VipIconView"));
            }
            case "RecentTask.colornick": {
                AtomicReference<String> methodName = new AtomicReference<>("m");
                if (QQ_version < 9280) return null;
                return FindMethod(classes, methodName.get(), GetClass("RecentBaseData"), GetClass("BaseActivity"), GetClass("VipIconView"), float.class);
            }
            case "VipData.getColorName": {
                AtomicReference<String> methodName = new AtomicReference<>("getColorName");
//                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get());
            }
//            case "BaseAuthorityPresenter.Parse": {
//                AtomicReference<String> methodName = new AtomicReference<>("R");
////                if (QQ_version < 8845) return null;
//                return FindMethod(classes, methodName.get(), GetClass("AuthCheckData"));
//            }
            case "TextItemBuilder.Color":
            case "ReplyTextItemBuilder.Color":
            case "MixedMsgItemBuilder.Color": {
                AtomicReference<String> methodName = new AtomicReference<>("n0");
                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(), GetClass("BaseBubbleBuilder$d"), View.class, GetClass("ChatMessage"), GetClass("BubbleInfo"));
            }
            case "ThemeHandler.startSwitch": {
                AtomicReference<String> methodName = new AtomicReference<>("b");
                if (QQ_version >= 9425) methodName.set("d5");
                else if (QQ_version >= 9280) methodName.set("g5");
                else if (QQ_version >= 8845) methodName.set("i5");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), String.class, String.class);
            }
            case "NormalNightModeHandler.startSwitch": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 9425) methodName.set("S4");
                else if (QQ_version >= 9280) methodName.set("V4");
                else if (QQ_version >= 8845) methodName.set("X4");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), int.class);
            }
            case "ForwardSelectionRecentFriendGridAdapter.DisplayData": {
                AtomicReference<String> methodName = new AtomicReference<>("b");
                if (QQ_version >= 8845) methodName.set("f");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), List.class);
            }
        }
        return null;
    }

    public static Class<?> GetClass(String classname)
    {
        boolean ContainsKey= ClassMap.containsKey(classname);
        if(ContainsKey) return ClassMap.get(classname);
        else {
            Class<?> classes = FindMyClass(classname);
            if (classes != null) ClassMap.put(classname,classes);
            return classes;
        }
    }

    public static Method GetMethod(String classname, String methodname)
    {
        String name = classname + "." + methodname;
//        FileUnits.writelog("MethodFinder " + MethodMap);
        boolean ContainsClassKey= MethodMap.containsKey(name);
        if (ContainsClassKey) return MethodMap.get(name);
        else {
            Method method = FindMyMethod(classname, methodname);
            if (method != null) MethodMap.put(name, method);
            return method;
        }
    }

    public static Object QRoteApi(String classname)
    {
        Class<?> classIfExists = GetClass("QRoute");
        if (classIfExists == null) return null;
        return XposedHelpers.callStaticMethod(classIfExists, "api", GetClass(classname));
    }

}
