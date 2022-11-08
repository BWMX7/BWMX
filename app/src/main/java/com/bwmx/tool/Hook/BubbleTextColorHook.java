package com.bwmx.tool.Hook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.Data.ChangeBubbleData;
import com.bwmx.tool.Units.MethodFinder;
import com.bwmx.tool.Units.PluginTool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class BubbleTextColorHook extends BaseHook{
    public static ChangeBubbleData BubbleData = new ChangeBubbleData();

    public static String HookName = "BubbleTextColorHook";
    public static boolean Switch;

    private static final XC_MethodHook MethodHook1;
    private static XC_MethodHook.Unhook Unhook1;
    private static XC_MethodHook.Unhook Unhook2;
    private static XC_MethodHook.Unhook Unhook3;

    static
    {
        Switch = Main.HookSwitches.GetSwitch(HookName);
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
        SetColor setColor = new SetColor("复制ID", 3200);
        QQCustomMenuItemHook.addItem("TextItemBuilder" , setColor);
        QQCustomMenuItemHook.addItem("ReplyTextItemBuilder", setColor);
        QQCustomMenuItemHook.addItem("MixedMsgItemBuilder", setColor);
    }

    public static void Init()
    {
        Log("HookInit");
        if (Switch) Log("Hook " + Hook());
    }

    private static void Log(Object log)
    {
        Log(HookName, log);
    }

    public static boolean Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("TextItemBuilder", "Color");
        Method MethodIfExists2 = MethodFinder.GetMethod("ReplyTextItemBuilder", "Color");
        Method MethodIfExists3 = MethodFinder.GetMethod("MixedMsgItemBuilder", "Color");
        Unhook1 = Hook(MethodIfExists1, MethodHook1, Unhook1);
        Unhook2 = Hook(MethodIfExists2, MethodHook1, Unhook2);
        Unhook3 = Hook(MethodIfExists3, MethodHook1, Unhook3);
        return !HasNull(Unhook1, Unhook2, Unhook3);
    }

    public static boolean UnHook() {
        Unhook1 = UnHook(Unhook1);
        Unhook2 = UnHook(Unhook2);
        Unhook3 = UnHook(Unhook3);
        return HasNull(Unhook1, Unhook2, Unhook3);
    }

    public static boolean ChangeSwitch(Boolean newSwitch)
    {
        boolean change = ChangeSwitch(newSwitch, Switch);
        boolean ok = PutSwitch(HookName, change);
        if (ok) Switch = change;
        if (Switch) return Hook();
        else return !UnHook();
    }

    private static class SetColor extends QQCustomMenuItemHook.Click
    {

        SetColor(String name, int id) {
            super(name, id);
        }

        @Override
        public void run(XC_MethodHook.MethodHookParam param) {
            super.run(param);
//            Log("测试2");
//            LogStackTrace(ItemName);
            Integer bubbleID = (Integer) MethodFinder.BusinessHandler("SVIP_HANDLER", "getBubbleIdFromMessageRecord", param.args[2]);
//            Log("BubbleID:" + bubbleID);
            ClipboardManager clipboardManager = (ClipboardManager)Main.AppContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, bubbleID != null ? bubbleID.toString() : null);
            clipboardManager.setPrimaryClip(clipData);
            PluginTool.ShowToast("气泡ID" + bubbleID + "已复制");
        }
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