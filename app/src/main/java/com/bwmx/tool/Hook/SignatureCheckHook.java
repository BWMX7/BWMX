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

public class SignatureCheckHook extends BaseHook{
    public static APKData APK = new APKData();

    public static String HookName = "SignatureCheckHook";
//    protected static Boolean Switch = false;

    private static final XC_MethodHook MethodHook1;
    private static final XC_MethodHook MethodHook2;
    private static final XC_MethodHook MethodHook3;
    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;
    private static XC_MethodHook.Unhook Unhook3;

    static {
        MethodHook1 = new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) {
                String text = param.args[1] + "";
                String qm = APK.GetSignature(text);
                if (qm != null) {
                    param.setResult(qm);
                    //writelog("" + classIfExists + "has Hooked!");
                    Log("ForwardShareCard -> " + text + " :  " + qm);
                }
            }
        };
        MethodHook2 = new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) {
                String pkgname = (String) param.args[0];
                String[] strArr = (String[]) param.getResult();
                String qm = strArr[0];
//                    String md5 = strArr[1];
                String time = strArr[2];
                Log("VirtualCheck local -> " + pkgname + " : " + qm);
                String qm2 = APK.GetSignature(pkgname);
                Log("VirtualCheck replace -> " + pkgname + " : " + qm2);
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
        };
        MethodHook3 = new XC_MethodHook() {
            protected void beforeHookedMethod(MethodHookParam param) {
                Boolean check = (Boolean) param.args[0];
                int id = (int) param.args[1];
                Log("AuthCheck -> " + check + " : " + id);
                param.args[0] = true;
            }
        };
    }

    public static void Init1()
    {
        Log(" -> ForwardShareCard HookInit");
        Log("ForwardShareCard" + Hook1());
    }

    public static void Init2()
    {
        Log(" -> VirtualLogin HookInit");
        Log("VirtualLogin" +Hook2());
    }

    private static void Log(String log)
    {
        FileUnits.writelog("[" + HookName + "]" + log);
    }

    private static Boolean Hook1() {
        Method MethodIfExists1 = MethodFinder.GetMethod("ForwardShareByServerHelper", "SignatureData");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
        return !HasNull(Unhook1);
    }

    private static Boolean Hook2() {
        Method MethodIfExists2 = MethodFinder.GetMethod("VirtualCheck", "Sign");
        Method MethodIfExists3 = MethodFinder.GetMethod("AuthCheck", "check");
        Unhook2 = Hook(MethodIfExists2, MethodHook2, Unhook2);
        Unhook3 = Hook(MethodIfExists3, MethodHook3, Unhook3);
        return !HasNull(Unhook2, Unhook3);
    }

//    private static Boolean UnHook() {
//        Unhook1 = UnHook(Unhook1);
//        Unhook2 = UnHook(Unhook2);
//        Unhook3 = UnHook(Unhook3);
//        return HasNull(Unhook1, Unhook2, Unhook3);
//    }

//    public static Boolean ChangeSwitch(Boolean newSwitch)
//    {
//        Switch = ChangeSwitch(newSwitch,Switch);
//        if (Switch) return Hook();
//        else return !UnHook();
//    }
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
