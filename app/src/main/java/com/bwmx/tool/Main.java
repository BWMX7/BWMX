package com.bwmx.tool;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.bwmx.tool.Hook.AddPluginToolHook;
import com.bwmx.tool.Hook.BubbleTextColorHook;
import com.bwmx.tool.Hook.ForwardRecentDisplayHook;
import com.bwmx.tool.Hook.MiniAppLogin;
import com.bwmx.tool.Hook.MsgListScrollerHook;
import com.bwmx.tool.Hook.SignatureCheckHook;
import com.bwmx.tool.Hook.StructMsgHook;
import com.bwmx.tool.Hook.ThemeSwitcherHook;
import com.bwmx.tool.Hook.TroopMemberListHook;
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


public class Main implements IXposedHookLoadPackage {
    public static Context AppContext;
    public static Application Applications;
    public static ClassLoader mLoader;
    public static Object Runtime;
    public static String MyUin;

    public void handleLoadPackage(@NonNull XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam.packageName.equals("com.tencent.mobileqq")) {
            FileUnits.writelog("[萌块]Load QQ from " + loadPackageParam.classLoader);
            if (mLoader == null) mLoader = loadPackageParam.classLoader;

            Method MethodIfExists1 = MethodFinder.GetMethod("QFixApplication", "attachBaseContext");
            if (MethodIfExists1 != null) {
                XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        Applications = (Application) param.thisObject;
                        AppContext = (Context) param.args[0];
                        mLoader = Applications.getClass().getClassLoader();

                        HostInfo.Init();
                        FileUnits.writelog("[萌块]HostInfo：" + HostInfo.getVersion() + "_" + HostInfo.getVerCode());

                        AddPluginToolHook.Hook();
                        SignatureCheckHook.Hook2();
                        ThemeSwitcherHook.Hook();
//                        VipColorNickHook.Hook();

                        Method MethodIfExists2 = MethodFinder.GetMethod("HelperProvider", "init");
                        if (MethodIfExists2 != null) {
                            // writelog("[萌块]" + MethodIfExists);
                            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    StructMsgHook.Hook();
                                    SignatureCheckHook.Hook1();
                                    MiniAppLogin.Hook();
                                    TroopMemberListHook.Hook();
                                    MsgListScrollerHook.Hook();
                                    ForwardRecentDisplayHook.Hook();
                                    if (BubbleTextColorHook.Switch) BubbleTextColorHook.Hook();
                                }
                            });
                        }
                    }
                });
            }

            Method MethodIfExists3 = MethodFinder.GetMethod("QQAppInterface", "onCreate");
            if (MethodIfExists3 != null) {
                    XposedBridge.hookMethod(MethodIfExists3, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Object runtime = param.thisObject;
//                        FileUnits.writelog("[萌块]runtime：" + runtime);
                        if (runtime != null && !Objects.equals(runtime, Runtime)) {
                            Runtime = runtime;
                            FileUnits.writelog("[萌块]Runtime：" + Runtime);
                            String uin = (String) XposedHelpers.callMethod(Runtime, "getCurrentAccountUin");
//                        FileUnits.writelog("[萌块]uin：" + uin);
                            if (uin != null && uin.length() > 4) MyUin = uin;
                            FileUnits.writelog("[萌块]MyUin：" + MyUin);
                        }
                    }
                });
            }
        }
    }
}

