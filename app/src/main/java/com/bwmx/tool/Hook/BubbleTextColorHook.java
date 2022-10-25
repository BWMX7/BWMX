package com.bwmx.tool.Hook;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwmx.tool.Units.Data.ChangeBubbleData;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class BubbleTextColorHook {
    public static ChangeBubbleData BubbleData = new ChangeBubbleData();
    public static Boolean Switch = false;

    static {
        Boolean data = (Boolean) BubbleData.GetItemData("ThemeId_2028656", "Switch");
        if (data != null) Switch = data;
    }

    public static void Hook() {
        XC_MethodHook MethodHook = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Object builder = param.args[0];
//                FileUnits.writelog("[萌块]BubbleColorHook " + builder);

                Object bubble = param.args[3];
//                        FileUnits.writelog("[萌块]BubbleColorHook " + bubble);

                Field field1 = XposedHelpers.findField(bubble.getClass(), "a");
//                        FileUnits.writelog("[萌块]BubbleColorHook " + field1);
                int id = (int) field1.get(bubble);
//                FileUnits.writelog("[萌块]BubbleColorHook " + id);
                Field field2 = XposedHelpers.findField(bubble.getClass(), "j");
//                       FileUnits.writelog("[萌块]BubbleColorHook " + field2);
                Integer oldColor = (Integer) field2.get(bubble);
                Integer newColor = (Integer) BubbleData.GetItemData("ThemeId_2028656", String.valueOf(id));
//                      FileUnits.writelog("[萌块]BubbleColorHook " + id + ":" + Color.valueOf(colorInt));

                if (newColor != null) {
//                       FileUnits.writelog("[萌块]BubbleColorHook " + id  + " -> " + color);
                    Class<?> aClass = param.thisObject.getClass();
//                    FileUnits.writelog("[萌块]BubbleColorHook " + aClass);
//                    FileUnits.writelog("[萌块]BubbleColorHook " + aClass + " " + id + " : " + Color.valueOf(oldColor) + " -> " + Color.valueOf(newColor));

                    Class<?> TextClass = MethodFinder.GetClass("TextItemBuilder");
                    Class<?> ReplyTextClass = MethodFinder.GetClass("ReplyTextItemBuilder");
                    Class<?> MixedMsgClass = MethodFinder.GetClass("MixedMsgItemBuilder");

//                    FileUnits.writelog("[萌块]BubbleColorHook builder " + builder);
                    if (MixedMsgClass.equals(aClass)) {
                        Field field3 = XposedHelpers.findField(builder.getClass(), "D");
                        LinearLayout linearLayout = (LinearLayout) field3.get(builder);
//                        FileUnits.writelog("[萌块]BubbleColorHook " + builder + " LinearLayout " + linearLayout);
                        int childCount = linearLayout.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            Object childAt = linearLayout.getChildAt(i);
                            if (childAt instanceof TextView) {
                                TextView view = (TextView) childAt;
                                view.setTextColor(newColor);
//                                childAt.setLinkTextColor(colorStateList2);
//                                Object method2 = MethodFinder.QRoteApi("IVipColorName");
//                                XposedHelpers.callMethod(method2, "setTextColorGradient", view, "1179726705");
                            }
                        }
                    }
                    String[] strings;
                    if (TextClass.equals(aClass)) strings = new String[]{"D"};
                    else if (ReplyTextClass.equals(aClass)) strings = new String[]{"D", "M", "P", "T"};
                    else if (MixedMsgClass.equals(aClass)) strings = new String[]{"L", "H", "I", "J"};
                    else return;
                    for (String method : strings) {
                        Field field3 = XposedHelpers.findField(builder.getClass(), method);
                        TextView view = (TextView) field3.get(builder);
//                        FileUnits.writelog("[萌块]BubbleColorHook " + builder + " View " + view);
                        if (view != null) view.setTextColor(newColor);
//                        Object method2 = MethodFinder.QRoteApi("IVipColorName");
//                        XposedHelpers.callMethod(method2, "setTextColorGradient", view, "1179726705");
                    }
                }
            }
        };



        if (Switch) {
            Method MethodIfExists1 = MethodFinder.GetMethod("TextItemBuilder", "Color");
            if (MethodIfExists1 != null) {
                XposedBridge.hookMethod(MethodIfExists1, MethodHook);
            }

            Method MethodIfExists2 = MethodFinder.GetMethod("ReplyTextItemBuilder", "Color");
            if (MethodIfExists2 != null) {
                XposedBridge.hookMethod(MethodIfExists2, MethodHook);
            }

            Method MethodIfExists3 = MethodFinder.GetMethod("MixedMsgItemBuilder", "Color");
            if (MethodIfExists3 != null) {
                XposedBridge.hookMethod(MethodIfExists3, MethodHook);
            }
        }

//        Method MethodIfExists2 = MethodFinder.GetMethod("VipData", "getColorName");
//        if (MethodIfExists2 != null) {
//            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
////                        FileUnits.writelog("[萌块]VipColorNickHook VipData " + param);
////                        int id = (int) param.getResult();
////                        if (id <= 1)
//                    param.setResult(2);
//                }
//            });
//        }
    }
}