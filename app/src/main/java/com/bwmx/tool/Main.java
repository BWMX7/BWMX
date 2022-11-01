package com.bwmx.tool;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.bwmx.tool.Hook.AddPluginToolHook;
import com.bwmx.tool.Hook.BaseHook;
import com.bwmx.tool.Hook.BubbleTextColorHook;
import com.bwmx.tool.Hook.ForwardRecentDisplayHook;
import com.bwmx.tool.Hook.MiniAppLogin;
import com.bwmx.tool.Hook.MsgListScrollerHook;
import com.bwmx.tool.Hook.SignatureCheckHook;
import com.bwmx.tool.Hook.StructMsgHook;
import com.bwmx.tool.Hook.TroopMemberListHook;
import com.bwmx.tool.Hook.VasSwitcherHook;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.HostInfo;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;
import java.util.Objects;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main extends BaseHook implements IXposedHookLoadPackage{
    public static Context AppContext;
    public static Application Applications;
    public static ClassLoader mLoader;
    public static Object Runtime;
    public static String MyUin;

    public static String ProcessName = "QQ:Main";
    protected static String HookName = "MainHook";
//    protected static Boolean Switch = false;
    protected static XC_MethodHook.Unhook Unhook1;
//    protected static XC_MethodHook MethodHook1;


//    static {
//        Log(" -> HookInit");
//    }

    public static void Log(String log)
    {
        Log(HookName, log);
    }

    public void handleLoadPackage(@NonNull XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam.packageName.equals("com.tencent.mobileqq")) {

            String processName = loadPackageParam.processName.replace("com.tencent.mobileqq", "QQ");
//            if (!processName.equals("")) return;
            if (!processName.equals("QQ")) ProcessName = processName;
            if (!ProcessName.contains("Main") && !ProcessName.contains("openSdk")) return;
            Log("-> Load QQ");
//            if (mLoader == null)
            mLoader = loadPackageParam.classLoader;

            Method MethodIfExists1 = MethodFinder.GetMethod("QFixApplication", "attachBaseContext");
            if (MethodIfExists1 != null && Unhook1 == null) {
                Unhook1 = XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        Applications = (Application) param.thisObject;
                        AppContext = (Context) param.args[0];
                        mLoader = Applications.getClass().getClassLoader();

                        HostInfo.Init();
                        Log("HostInfo：" + HostInfo.getVersion() + "_" + HostInfo.getVerCode());

                        Method MethodIfExists2 = MethodFinder.GetMethod("QQAppInterface", "onCreate");
                        if (MethodIfExists2 != null) {
                            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    Object runtime = param.thisObject;
//                        Log("runtime：" + runtime);
                                    if (runtime != null && !Objects.equals(runtime, Runtime)) {
                                        Runtime = runtime;
                                        Log("Runtime：" + Runtime);
                                        String uin = (String) XposedHelpers.callMethod(Runtime, "getCurrentAccountUin");
//                        Log("uin：" + uin);
                                        if (uin != null && uin.length() > 4) MyUin = uin;
                                        Log("MyUin：" + MyUin);
                                    }
                                }
                            });
                        }

                        if (ProcessName.contains("openSdk")) {
                            SignatureCheckHook.Init2();
                            return;
                        }

                        AddPluginToolHook.Init();
                        VasSwitcherHook.Init1();
                        StructMsgHook.Hook();
                        ForwardRecentDisplayHook.Hook();
//                        VipColorNickHook.Hook();

                        Method MethodIfExists3 = MethodFinder.GetMethod("HelperProvider", "init");
                        if (MethodIfExists3 != null) {
                            // writelog("" + MethodIfExists);
                            XposedBridge.hookMethod(MethodIfExists3, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);

                                    SignatureCheckHook.Init1();
                                    MiniAppLogin.Hook();
                                    TroopMemberListHook.Hook();
                                    MsgListScrollerHook.Init();
                                    VasSwitcherHook.Init2();
                                    BubbleTextColorHook.Init();

                                }
                            });
                        }
                    }
                });
            }
        }
    }
}

