//package com.bwmx.tool;
//
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//
//import de.robv.android.xposed.XC_MethodHook;
//import de.robv.android.xposed.XposedBridge;
//import de.robv.android.xposed.XposedHelpers;
//
//public class QToolHook {
//
//    public static void AddMethod(Class clazz) throws NoSuchMethodException, IOException
//    {
////        FileUnits.writelog("[萌块]Load QT from " + clazz.getPackage().getName());
//        ClassLoader classLoader = clazz.getClassLoader();
//        AddMethod(classLoader);
//    }
//    public static void AddMethod(ClassLoader classLoader) throws NoSuchMethodException, IOException
//        {
//            try {
////        if (ifhook) return;
//                String Name = classLoader.toString();
//                if (Name.contains("cc.hicore.qtool")) {
////                ifhook=true;
////                    FileUnits.writelog("[萌块]AddMethod\n" +classLoader);
//                    XposedHelpers.findAndHookMethod("cc.hicore.qtool.JavaPlugin.Controller.a",classLoader, "e", XposedHelpers.findClassIfExists("cc.hicore.qtool.JavaPlugin.Controller.PluginInfo", classLoader), new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                            Object pluginInfo_object=param.args[0];
//                            Class<?> pluginInfo = pluginInfo_object.getClass();
//                            String id = (String) pluginInfo.getDeclaredField("a").get(pluginInfo_object);
////                            FileUnits.writelog("[萌块]AddMethod\n" + id);
//                            if(id.contains("QQMUSIC")){
////                                FileUnits.writelog("[萌块]AddMethod\n" + pluginInfo);
//                                Object i = pluginInfo.getDeclaredField("i").get(pluginInfo_object);
//                                Object getNameSpace = i.getClass().getDeclaredMethod("getNameSpace").invoke(i);
//                                Class<?> BshMethod = XposedHelpers.findClassIfExists("bsh.BshMethod", classLoader);
//                                Constructor<?> constructor = BshMethod.getConstructor(Method.class, Object.class);
//                                Method setMethod = getNameSpace.getClass().getDeclaredMethod("setMethod", String.class, BshMethod);
//                                Method i1 = SignatureData.class.getDeclaredMethod("PutUserSignature", String.class, String.class);
//                                setMethod.invoke(getNameSpace,"SetSignature",constructor.newInstance(i1,SignatureData.getInstance()));
////                                FileUnits.writelog("[萌块]AddMethod\n" + i1);
//                            }
//                        }
//                    });
//
//                    /*
//                    String className = "cc.hicore.qtool.JavaPlugin.Controller.a";
//                    Class<?> classIfExists = XposedHelpers.findClassIfExists(className, classLoader);
//                    String className2 = "cc.hicore.qtool.JavaPlugin.Controller.PluginInfo";
////            String className2 = "bsh.Interpreter";
//                    Class<?> classIfExists2 = XposedHelpers.findClassIfExists(className2, classLoader);
//                    String className3 = "bsh.BshMethod";
//                    Class<?> classIfExists3 = XposedHelpers.findClassIfExists(className3, classLoader);
////                        String className4 = "bsh.NameSpace";
////                        Class<?> classIfExists4 = XposedHelpers.findClass(className4, classLoader);
//                    if (classIfExists != null && classIfExists2 != null && classIfExists3 != null) {
//                        String methodName = "e";
////                String methodName = "eval";
//                        Method MethodIfExists = XposedHelpers.findMethodExactIfExists(classIfExists, methodName, classIfExists2);
////                Method MethodIfExists = XposedHelpers.findMethodExactIfExists(classIfExists2, methodName,String.class, String.class);
//                        if (MethodIfExists != null) {
//                            ArrayList<Object> list = new ArrayList();
////                    MiniAppLogin mini = new MiniAppLogin();
////                    mini.Hook();
////                    Object bsh = XposedHelpers.newInstance(classIfExists3, MiniAppLogin.class.getMethod("GetMiniAppCode", String.class), mini);
//                            Object bsh2 = XposedHelpers.newInstance(classIfExists3, SignatureData.class.getMethod("PutUserSignature", String.class, String.class), SignatureData.getInstance());
//                            FileUnits.writelog("[萌块]AddMethod1 "+ MethodIfExists);
////                    XposedHelpers.findAndHookMethod("cc.hicore.qtool.JavaPlugin.Controller.a", classLoader, "f",String.class,String.class,String.class, new XC_MethodHook() {
//                            XposedBridge.hookMethod(MethodIfExists, new XC_MethodHook() {
//                                @Override
//                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                                    try {
//                                        Object pluginInfo = param.args[0];
////                                writelog("[萌块]AddMethod2 " + pluginInfo);
//                                        if (!list.contains(pluginInfo)) {
//                                            list.add(pluginInfo);
//                                            FileUnits.writelog("[萌块]AddMethod\n[From]\n" + pluginInfo);
//                                            Field field = XposedHelpers.findField(pluginInfo.getClass(), "i");
//                                            Object object = field.get(pluginInfo);
////                                Object object=param.thisObject;
//                                            FileUnits.writelog("[萌块]AddMethod\n[From]\n"  + object );
//                                            Field field2 = XposedHelpers.findField(object.getClass(), "globalNameSpace");
//                                            Object space = field2.get(object);
//                                            FileUnits.writelog("[萌块]AddMethod\n[To]\n" + space + "\n[What]\n" + "\n" + bsh2);
////                                XposedHelpers.callMethod(space, "setMethod", "GetMini", bsh);
//                                            XposedHelpers.callMethod(space, "setMethod", "SetSignature", bsh2);
////                                writelog("[萌块]" + list);
//                                        }
//                                    } catch (Throwable e) {
//                                        FileUnits.writelog("[萌块]AddMethod Error\n" + e);
//                                    }
//                                }
//                            });
//                        } else
//                            FileUnits.writelog("[萌块]Method " + classIfExists + "→" + methodName + " found error!");
//                    } else FileUnits.writelog("[萌块]Class Controller found error!");*/
//                }
//            } catch (Throwable e) {
//                FileUnits.writelog("[萌块]AddMethod Error\n" + e);
//            }
//        }
//}
