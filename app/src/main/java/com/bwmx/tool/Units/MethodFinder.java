package com.bwmx.tool.Units;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

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



    private static Class<?> FindClass(String className) {
        if (className == null) return null;
//        FileUnits.writelog("FindClass " + className);
        Class<?> classIfExists = XposedHelpers.findClassIfExists(className, Main.mLoader);
        if (classIfExists != null) return classIfExists;
        else {
            FileUnits.writelog("Class " + className + " found error!");
            return null;
        }
    }

    private static Method FindMethod(Class<?> classes, String methodName, Object... obj)
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


    private static Class<?> FindMyClass(String name)
    {
        if (name == null) return null;
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
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.helper.HelperProvider");//8.8.*
                if (QQ_version >= 9570) {//8.9.18
                    className.set("com.tencent.mobileqq.activity.aio.helper.bx");
                }else if (QQ_version >= 9280) {//8.9.13-8.9.15(9425)
                    className.set("com.tencent.mobileqq.activity.aio.helper.bw");
                } else if (QQ_version >= 9135) {//8.9.10
                    className.set("com.tencent.mobileqq.activity.aio.helper.bv");
                } else if (QQ_version >= 8995) {//8.9.8
                    className.set("com.tencent.mobileqq.activity.aio.helper.bu");
                } else if (QQ_version >= 8845) {//8.9.5
                    className.set("com.tencent.mobileqq.activity.aio.helper.bs");
                } else if (QQ_version >= 8120) return null;//8.8.90
                return FindClass(className.get());
            }
            case "StructMsgForGeneralShare": {
                String className = "com.tencent.mobileqq.structmsg.StructMsgForGeneralShare";
                return FindClass(className);
            }
            case "StructMsgForAudioShare": {
                String className = "com.tencent.mobileqq.structmsg.StructMsgForAudioShare";
                return FindClass(className);
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
                String className = "com.tencent.mobileqq.qroute.QRoute";
                return FindClass(className);
            }
            case "IVipColorName": {
                String className = "com.tencent.mobileqq.vip.api.IVipColorName";
                if (QQ_version < 9280) return null;
                return FindClass(className);
            }
            case "VipData": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.vip.api.VipData");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "TextItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.item.TextItemBuilder";
                return FindClass(className);
            }
            case "ReplyTextItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.item.ReplyTextItemBuilder";
                return FindClass(className);
            }
            case "MixedMsgItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.item.MixedMsgItemBuilder";
                return FindClass(className);
            }
            case "BaseBubbleBuilder$d": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.BaseBubbleBuilder$d");
                if (QQ_version < 9280) return null;
                return FindClass(className.get());
            }
            case "ChatMessage": {
                String className = "com.tencent.mobileqq.data.ChatMessage";
                return FindClass(className);
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
            case "ForwardRecentActivity": {
                String className = "com.tencent.mobileqq.activity.ForwardRecentActivity";
                return FindClass(className);
            }
            case "ForwardSelectionRecentFriendGridAdapter": {
                String className = "com.tencent.mobileqq.adapter.ForwardSelectionRecentFriendGridAdapter";
                return FindClass(className);
            }
            case "GetStrangerVasInfoHandler": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.vas.handler.GetStrangerVasInfoHandler");
                if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.vas.e.a");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
            case "oidb_0x5eb$UdcUinData": {
                String className = "tencent.im.oidb.cmd0x5eb.oidb_0x5eb$UdcUinData";
                return FindClass(className);
            }
            case "SVIPHandler": {
                String className = "com.tencent.mobileqq.app.SVIPHandler";
                return FindClass(className);
            }
            case "QQCustomMenuItem": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.utils.dialogutils.QQCustomMenuItem");
                if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.utils.dialogutils.b");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
//            case "BaseAuthorityPresenter": {
//                AtomicReference<String> className = new AtomicReference<>("com.tencent.open.agent.auth.presenter.BaseAuthorityPresenter");
//                return FindClass(className.get());
//            }
            case "BusinessHandlerFactory": {
                String className = "com.tencent.mobileqq.app.BusinessHandlerFactory";
                return FindClass(className);
            }
            case "BasePicItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.item.BasePicItemBuilder";
                return FindClass(className);
            }
            case "PicItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.item.PicItemBuilder";
                return FindClass(className);
            }
            case "BaseBubbleItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.BaseBubbleBuilder";
                return FindClass(className);
            }
            case "QFileItemBuilder": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.item.QFileItemBuilder");
                if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.activity.aio.item.aw");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
            case "PttItemBuilder": {
                String className = "com.tencent.mobileqq.activity.aio.item.PttItemBuilder";
                return FindClass(className);
            }
            case "BaseQQMessageFacade": {
                String className = "com.tencent.imcore.message.BaseQQMessageFacade";
                return FindClass(className);
            }
            case "MessageRecord": {
                String className = "com.tencent.mobileqq.data.MessageRecord";
                return FindClass(className);
            }
            case "BusinessObserver": {
                String className = "com.tencent.mobileqq.app.BusinessObserver";
                return FindClass(className);
            }
            case "MessageForPtt": {
                String className = "com.tencent.mobileqq.data.MessageForPtt";
                return FindClass(className);
            }
            case "PttItemBuilder$Holder": {
                AtomicReference<String> className = new AtomicReference<>("com.tencent.mobileqq.activity.aio.item.PttItemBuilder$Holder");
                if (QQ_version >= 8845) {
                    className.set("com.tencent.mobileqq.activity.aio.item.PttItemBuilder$c");
                } else if (QQ_version >= 8000) return null;
                return FindClass(className.get());
            }
            default:
                return FindClass(name);
        }
    }


    @Nullable
    private static Method FindMyMethod(String classname, String methodname)
    {
        Class<?> classes = GetClass(classname);
        if (classes == null) return null;
//        FileUnits.writelog("FindMyMethod " + methodname + " from " + classes);
        int QQ_version=HostInfo.getVerCode();
        switch (classname + "." + methodname) {
            case "QFixApplication.attachBaseContext": {
                return FindMethod(classes, "attachBaseContext", Context.class);
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
//            case "StructMsgFactory.init": {
//                AtomicReference<String> methodName = new AtomicReference<>("a");
//                if (QQ_version >= 8845) methodName.set("f");
//                else if (QQ_version >= 8000) return null;
//                return FindMethod(classes, methodName.get(), Bundle.class);
//            }
            case "ForwardShareByServerHelper.SignatureData": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("F");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), Context.class, String.class);
            }
            case "TroopMemberListAdapter.init":
            case "MsgListScroller.ScrollTo0": {
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
//            case "VipIconTask.ColorNick": {
//                AtomicReference<String> methodName = new AtomicReference<>("c");
//                if (QQ_version < 9280) return null;
//                return FindMethod(classes, methodName.get(),Context.class, String.class, GetClass("VipIconView"));
//            }
            case "RecentTask.ColorNick": {
                AtomicReference<String> methodName = new AtomicReference<>("m");
                if (QQ_version < 9280) return null;
                return FindMethod(classes, methodName.get(), GetClass("RecentBaseData"), GetClass("BaseActivity"), GetClass("VipIconView"), float.class);
            }
            case "VipData.getColorName": {
                AtomicReference<String> methodName = new AtomicReference<>("getColorName");
//                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get());
            }
            case "BaseAuthorityPresenter.Parse": {
                AtomicReference<String> methodName = new AtomicReference<>("R");
//                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(), GetClass("AuthCheckData"));
            }
            case "TextItemBuilder.Color":
            case "ReplyTextItemBuilder.Color":
            case "MixedMsgItemBuilder.Color": {
                AtomicReference<String> methodName = new AtomicReference<>("n0");
                if (QQ_version >= 9570) methodName.set("m0");
                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(), GetClass("BaseBubbleBuilder$d"), View.class, GetClass("ChatMessage"), GetClass("BubbleInfo"));
            }
            case "ThemeHandler.SwitchTheme": {
                AtomicReference<String> methodName = new AtomicReference<>("b");
                if (QQ_version >= 9570) methodName.set("b5");
                else if (QQ_version >= 9425) methodName.set("d5");
                else if (QQ_version >= 9280) methodName.set("g5");
                else if (QQ_version >= 8845) methodName.set("i5");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), String.class, String.class);
            }
            case "NormalNightModeHandler.SwitchTheme": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 9570) methodName.set("Q4");
                else if (QQ_version >= 9425) methodName.set("S4");
                else if (QQ_version >= 9280) methodName.set("V4");
                else if (QQ_version >= 8845) methodName.set("X4");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), int.class);
            }
            case "ForwardRecentActivity.getRecentForwardListWithoutShowUp": {
                return FindMethod(classes, "getRecentForwardListWithoutShowUp");
            }
            case "ForwardSelectionRecentFriendGridAdapter.DisplayData": {
                AtomicReference<String> methodName = new AtomicReference<>("b");
                if (QQ_version >= 8845) methodName.set("e");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), List.class);
            }
            case "GetStrangerVasInfoHandler.SwitchBubble": {
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("d");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), GetClass("oidb_0x5eb$UdcUinData"));
            }
            case "SVIPHandler.setSelfBubbleId": {
                return FindMethod(classes, "setSelfBubbleId", int.class);
            }
            case "TextItemBuilder.Dialog":
            case "MixedMsgItemBuilder.Dialog":
            case "BasePicItemBuilder.Dialog":
            case "QFileItemBuilder.Dialog":
            case "PttItemBuilder.Dialog":{
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("o");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), View.class);
            }
            case "TextItemBuilder.Click":
            case "ReplyTextItemBuilder.Click":
            case "MixedMsgItemBuilder.Click":
            case "BasePicItemBuilder.Click":
            case "BaseBubbleItemBuilder.Click":
            case "QFileItemBuilder.Click":
            case "PttItemBuilder.Click":{
                return FindMethod(classes, "a", int.class, Context.class, GetClass("ChatMessage"));
            }
            case "BaseQQMessageFacade.SendMessage":{
                AtomicReference<String> methodName = new AtomicReference<>("n0");
                if (QQ_version >= 8845) methodName.set("W0");
                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(), GetClass("MessageRecord"), GetClass("BusinessObserver"), boolean.class);
            }
            case "BaseQQMessageFacade.AddToMsgList":{
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 8845) methodName.set("c");
                if (QQ_version < 8845) return null;
                return FindMethod(classes, methodName.get(), GetClass("MessageRecord"), String.class);
            }
            case "PttItemBuilder.Time":{
                AtomicReference<String> methodName = new AtomicReference<>("a");
                if (QQ_version >= 9570) methodName.set("S0");
                else if (QQ_version >= 9280) methodName.set("T0");
                else if (QQ_version >= 8845) methodName.set("V0");
                else if (QQ_version >= 8000) return null;
                return FindMethod(classes, methodName.get(), GetClass("PttItemBuilder$Holder"), GetClass("MessageForPtt"));
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

    @Nullable
    public static Object QRoteApi(String ClassName)
    {
        Class<?> classIfExists = GetClass("QRoute");
        if (classIfExists == null) return null;
        Class<?> classIfExists2 = GetClass(ClassName);
        if (classIfExists2 == null) return null;
        return XposedHelpers.callStaticMethod(classIfExists, "api", classIfExists2);
    }

    @Nullable
    public static Object BusinessHandler(String HandlerName, String methodName, Object... objects)
    {
        Class<?> classIfExists = GetClass("BusinessHandlerFactory");
        if (classIfExists == null) return null;
//        Field field1 = XposedHelpers.findFieldIfExists(classIfExists, HandlerName);
//        if (field1 == null) return null;
        String handlerName = (String) XposedHelpers.getStaticObjectField(classIfExists, HandlerName);
        if (TextUtils.isEmpty(handlerName)) return null;
        Object handler = XposedHelpers.callMethod(Main.Runtime, "getBusinessHandler", handlerName);
        return XposedHelpers.callMethod(handler, methodName, objects);
    }

}
