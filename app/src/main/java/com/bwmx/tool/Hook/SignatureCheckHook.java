package com.bwmx.tool.Hook;

import com.bwmx.tool.Units.Data.APKData;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

public class SignatureCheckHook {
    public static APKData APK = new APKData();

    public static void Hook1() {
        Method MethodIfExists1 = MethodFinder.GetMethod("ForwardShareByServerHelper", "SignatureData");
        if (MethodIfExists1 != null) {
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) {
                    String text = param.args[1] + "";
                    String qm = APK.GetSignature(text);
                    if (qm != null) {
                        param.setResult(qm);
                        //writelog("" + classIfExists + "has Hooked!");
                        FileUnits.writelog("ForwardShareCardSignature -> " + text + " :  " + qm);
                    }
                }
            });
        }
    }

    public static void Hook2() {
        Method MethodIfExists1 = MethodFinder.GetMethod("VirtualCheck", "Sign");
        if (MethodIfExists1 != null) {
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) {
                    String pkgname = (String) param.args[0];
                    String[] strArr = (String[]) param.getResult();
                    String qm = strArr[0];
//                    String md5 = strArr[1];
                    String time = strArr[2];
                    FileUnits.writelog("VirtualCheck local -> " + pkgname + " : " + qm);
                    String qm2 = APK.GetSignature(pkgname);
                    FileUnits.writelog("VirtualCheck replace -> " + pkgname + " : " + qm2);
                    if (qm != null && !qm2.contains(qm)) {
                        StringBuilder sb = new StringBuilder();
                        try {
                            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                            messageDigest.update((pkgname + "_" + qm2 + "_" + time).getBytes());
                            byte[] b = messageDigest.digest();
                            AtomicInteger d = new AtomicInteger();
                            for (byte value : b) {
                                d.set(value);
                                if (d.get() < 0) {
                                    d.set(value & 0xff);
                                }
                                if (d.get() < 16) sb.append("0");
                                sb.append(Integer.toHexString(d.get()));
                            }
                        }
                        catch (NoSuchAlgorithmException e){
                            FileUnits.writelog("VirtualCheck Error\n" + e);
                        }
                        strArr[1] = sb.toString().toUpperCase(Locale.ROOT);
//                        FileUnits.writelog("VirtualCheck new md5 -> " + pkgname + " : " + strArr[1]);
                        param.setResult(strArr);
                    }

                }
            });
        }
        Method MethodIfExists2 = MethodFinder.GetMethod("AuthCheck", "check");
        if (MethodIfExists2 != null) {
            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) {
                    Boolean check = (Boolean) param.args[0];
                    int id = (int) param.args[1];
                    FileUnits.writelog("AuthCheck -> " + check + " : " + id);
                    param.args[0] = true;
                }
            });
        }
//        Method MethodIfExists3 = MethodFinder.GetMethod("BaseAuthorityPresenter", "Parse");
//        if (MethodIfExists2 != null) {
//            XposedBridge.hookMethod(MethodIfExists3, new XC_MethodReplacement() {
//                @Override
//                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                        XposedHelpers.callMethod(param.thisObject, "H", param.args[0]);
//                        return null;
//                }
//            });
//        }
    }

}
