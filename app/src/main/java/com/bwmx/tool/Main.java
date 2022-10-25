package com.bwmx.tool;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.bwmx.tool.Hook.AddPluginToolHook;
import com.bwmx.tool.Hook.BubbleTextColorHook;
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

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class Main implements IXposedHookLoadPackage {
    public static Context AppContext;
    public static Application Applications;
    public static ClassLoader mLoader;
//    public static ClassLoader newLoader;
//    private static XC_MethodHook.Unhook PassHook;

//    @Override
//    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
//        writelog("[萌块]Icon"+resparam);
//        Icon.handleInitPackageResources(resparam);
//    }
    public void handleLoadPackage(@NonNull XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (loadPackageParam.packageName.equals("com.tencent.mobileqq")) {
            FileUnits.writelog("[萌块]Load QQ from " + loadPackageParam.classLoader);
            if (mLoader==null) mLoader = loadPackageParam.classLoader;

            Method MethodIfExists1 = MethodFinder.GetMethod("QFixApplication", "attachBaseContext");
            if (MethodIfExists1 != null ) {
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
//                        super.afterHookedMethod(param);
//                        newLoader = this.getClass().getClassLoader();
//                        writelog("[萌块]myclassLoader：" + newLoader);
//
//                        try {
//                            AddClassLoader.add(mLoader,newLoader);
//                        } catch (Throwable e) {
//                            StackTraceElement[] wodelogs = e.getStackTrace();
//                            String log = "";
//                            // 使用for循环打印 调用栈查看调用关系
//                            for (int i = 0; i < wodelogs.length; i++) {
//                                log += "\n" + wodelogs[i].toString();
//                            }
//                            writelog("[萌块]myclassLoader：" + log);
//                        }
//                        writelog("[萌块]myclassLoader：" + newLoader);

//                        Method MethodIfExists2 = MethodFinder.GetMethod("ClassLoader");
//                        if (MethodIfExists2 != null) {
//                            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
//                                @Override
//                                protected void afterHookedMethod(MethodHookParam param){
//                                    writelog("[萌块]getclassLoader" +param.getResult());
//                                }
//                            });
//                        }
//
//                        Method MethodIfExists3 = MethodFinder.GetMethod("AddMyClassloader");
//                        if (MethodIfExists3 != null) {
//                            XposedBridge.hookMethod(MethodIfExists3, new XC_MethodHook() {
//                                @Override
//                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                    super.afterHookedMethod(param);
//                                    writelog("[萌块]myclassLoader：" + param.args[0]);
//                                    param.args[0] = newLoader;
//                                }
//                            });
//                        }

                        Method MethodIfExists4 = MethodFinder.GetMethod("HelperProvider", "init");
                        if (MethodIfExists4 != null) {
                            // writelog("[萌块]" + MethodIfExists);
                            XposedBridge.hookMethod(MethodIfExists4, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    StructMsgHook.Hook();
                                    SignatureCheckHook.Hook1();
                                    MiniAppLogin.Hook();
                                    TroopMemberListHook.Hook();
                                    MsgListScrollerHook.Hook();
                                    if (BubbleTextColorHook.Switch) BubbleTextColorHook.Hook();
                                }
                            });
                        }
                    }
                });

//            XposedHelpers.findAndHookConstructor(XC_MethodHook.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    Class<?> clazz = param.thisObject.getClass();
//                    QToolHook.AddMethod(clazz);
//                }
//            });

//        }
//        else {
//            XposedHelpers.findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    Context context = (Context) param.args[0];
//                    ClassLoader cl = context.getClassLoader();
//                    AddMethod(cl);
//                }
//            });
            }
        }
    }



}

