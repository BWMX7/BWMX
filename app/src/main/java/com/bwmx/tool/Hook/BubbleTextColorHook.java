package com.bwmx.tool.Hook;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwmx.tool.Units.Data.ChangeBubbleData;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class BubbleTextColorHook extends BaseHook{
    public static ChangeBubbleData BubbleData = new ChangeBubbleData();

    public static String HookName = "BubbleTextColorHook";
    public static Boolean Switch;

    private static final XC_MethodHook MethodHook1;
    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;
    private static XC_MethodHook.Unhook Unhook3;

    static {
        Boolean data = (Boolean) BubbleData.GetItemData("ThemeId_2028656", "Switch");
        if (data != null) Switch = data;
        else Switch = false;

        MethodHook1 = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Object builder = param.args[0];
//                FileUnits.writelog("BubbleColorHook " + builder);

                Object bubble = param.args[3];
//                        FileUnits.writelog("BubbleColorHook " + bubble);

                Field field1 = XposedHelpers.findField(bubble.getClass(), "a");
//                        FileUnits.writelog("BubbleColorHook " + field1);
                int id = (int) field1.get(bubble);
//                FileUnits.writelog("BubbleColorHook " + id);
                Field field2 = XposedHelpers.findField(bubble.getClass(), "j");
//                       FileUnits.writelog("BubbleColorHook " + field2);
                Integer oldColor = (Integer) field2.get(bubble);
                Integer newColor = (Integer) BubbleData.GetItemData("ThemeId_2028656", String.valueOf(id));
//                      FileUnits.writelog("BubbleColorHook " + id + ":" + Color.valueOf(colorInt));

                if (newColor != null) {
//                       FileUnits.writelog("BubbleColorHook " + id  + " -> " + color);
                    Class<?> aClass = param.thisObject.getClass();
//                    FileUnits.writelog("BubbleColorHook " + aClass);
//                    FileUnits.writelog("BubbleColorHook " + aClass + " " + id + " : " + Color.valueOf(oldColor) + " -> " + Color.valueOf(newColor));

                    Class<?> TextClass = MethodFinder.GetClass("TextItemBuilder");
                    Class<?> ReplyTextClass = MethodFinder.GetClass("ReplyTextItemBuilder");
                    Class<?> MixedMsgClass = MethodFinder.GetClass("MixedMsgItemBuilder");

//                    FileUnits.writelog("BubbleColorHook builder " + builder);
                    if (MixedMsgClass.equals(aClass)) {
                        Field field3 = XposedHelpers.findField(builder.getClass(), "D");
                        LinearLayout linearLayout = (LinearLayout) field3.get(builder);
//                        FileUnits.writelog("BubbleColorHook " + builder + " LinearLayout " + linearLayout);
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
//                        FileUnits.writelog("BubbleColorHook " + builder + " View " + view);
                        if (view != null) view.setTextColor(newColor);
//                        Object method2 = MethodFinder.QRoteApi("IVipColorName");
//                        XposedHelpers.callMethod(method2, "setTextColorGradient", view, "1179726705");
                    }
                }
            }
        };
        if (Switch) Hook();
    }

    public static void Init()
    {
        Log(" -> HookInit");
    }

    private static void Log(String log)
    {
        FileUnits.writelog("[" + HookName + "]" + log);
    }

    public static Boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("TextItemBuilder", "Color");
        Method MethodIfExists2 = MethodFinder.GetMethod("ReplyTextItemBuilder", "Color");
        Method MethodIfExists3 = MethodFinder.GetMethod("MixedMsgItemBuilder", "Color");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
        Unhook2 = Hook(MethodIfExists2, MethodHook1, Unhook2);
        Unhook3 = Hook(MethodIfExists3, MethodHook1, Unhook3);
        return !HasNull(Unhook1, Unhook2, Unhook3);
    }

    public static Boolean UnHook() {
        Unhook1 = UnHook(Unhook1);
        Unhook2 = UnHook(Unhook2);
        Unhook3 = UnHook(Unhook3);
        return HasNull(Unhook1, Unhook2, Unhook3);
    }

    public static Boolean ChangeSwitch(Boolean newSwitch)
    {
        Switch = ChangeSwitch(newSwitch,Switch);
        if (Switch) return Hook();
        else return !UnHook();
    }

//        Method MethodIfExists2 = MethodFinder.GetMethod("VipData", "getColorName");
//        if (MethodIfExists2 != null) {
//            XposedBridge.hookMethod(MethodIfExists2, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
////                        FileUnits.writelog("VipColorNickHook VipData " + param);
////                        int id = (int) param.getResult();
////                        if (id <= 1)
//                    param.setResult(2);
//                }
//            });
//        }

}