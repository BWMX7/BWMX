package com.bwmx.tool.Hook;


import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class QQCustomMenuItemHook extends BaseHook{

    public static String HookName = "QQCustomMenuItemHook";

    private static final XC_MethodHook MethodHook1;
    private static final XC_MethodHook MethodHook2;

    private static final HashMap<String, XC_MethodHook.Unhook> UnhookMap1 = new HashMap<>();
    private static final HashMap<String, XC_MethodHook.Unhook> UnhookMap2 = new HashMap<>();

    private static final HashMap<Class<?>, Object[]> ItemMap = new HashMap<>();
    private static final HashMap<Class<?>, Click[]> ClickMap = new HashMap<>();

    private static final Class<?> QQCustomMenuItemClass = MethodFinder.GetClass("QQCustomMenuItem");

    static
    {
        MethodHook1 = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                Class<?> aClass = param.thisObject.getClass();
//                Log(aClass.toString());

                if (ItemMap.containsKey(aClass)) {
                Object[] arr = (Object[]) param.getResult();
                Object[] add = ItemMap.get(aClass);
                Object[] ret = (Object[]) Array.newInstance(arr.getClass().getComponentType(),Array.getLength(arr) + Array.getLength(add));
                System.arraycopy(arr, 0, ret, 0, Array.getLength(arr));
                System.arraycopy(add, 0, ret, Array.getLength(arr), Array.getLength(add));
//                Log(Arrays.toString(ret));
                param.setResult(ret);
                }
            }
        };

        MethodHook2 = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

//                Object[] objects = param.args;
//                Log(Arrays.toString(objects));

                Class<?> aClass = param.thisObject.getClass();
//                Log(aClass.toString());

                if (ClickMap.containsKey(aClass)) {
                    int id = (int) param.args[0];
                    for (Click click : ClickMap.get(aClass))
                    {
                        if (click.ItemID == id)
                        {
                            click.run(param);
                            return;
                        }
                    }
                }
            }
        };
    }

    public static void Init()
    {
        Log("HookInit");
    }

    private static void Log(String log)
    {
        Log(HookName, log);
    }

    private static boolean Hook(String ClassName) {
        if (ClassName == null) return false;
        if (ClassName.equals("ReplyTextItemBuilder")) return Hook("TextItemBuilder");

        if (!UnhookMap1.containsKey(ClassName))
        {
            Method MethodIfExists1 = MethodFinder.GetMethod(ClassName, "Dialog");
            XC_MethodHook.Unhook Unhook1 = Hook(MethodIfExists1, MethodHook1, null);
            if (HasNull(Unhook1)) return false;
            UnhookMap1.put(ClassName, Unhook1);
        }

        if (!UnhookMap2.containsKey(ClassName))
        {
            Method MethodIfExists2 = MethodFinder.GetMethod(ClassName, "Click");
            XC_MethodHook.Unhook Unhook2 = Hook(MethodIfExists2, MethodHook2, null);
            if (HasNull(Unhook2)) return false;
            UnhookMap2.put(ClassName, Unhook2);
        }
        return true;
    }

    private static boolean UnHook(String ClassName) {
        if (UnhookMap1.containsKey(ClassName))
        {
            XC_MethodHook.Unhook Unhook1 = UnhookMap1.get(ClassName);
            if (!HasNull(UnHook(Unhook1))) return false;
            UnhookMap1.remove(ClassName);
        }
        if (UnhookMap2.containsKey(ClassName))
        {
            XC_MethodHook.Unhook Unhook2 = UnhookMap2.get(ClassName);
            if (!HasNull(UnHook(Unhook2))) return false;
            UnhookMap2.remove(ClassName);
        }
        return true;
    }

    public static boolean addItem(String ClassName, Click MyClick)
    {
        if (ClassName == null || MyClick == null) return false;
        if (Hook(ClassName))
        {
            Class<?> aClass = MethodFinder.GetClass(ClassName);
//            Log(aClass + " " + MyClick);
            Object[] NewItems;
            Object MenuItem = XposedHelpers.newInstance(QQCustomMenuItemClass, MyClick.ItemID, MyClick.ItemName, Integer.MAX_VALUE - 1);
//            Log(MenuItem.toString());
            if (ItemMap.containsKey(aClass)) {
                Object[] items = ItemMap.get(aClass);
                for (Object item : items) {
                    if (QQCustomMenuItemEquals(item, MenuItem)) return true;
                }
                NewItems = new Object[items.length + 1];
                System.arraycopy(items, 0, NewItems, 0, items.length);
            }
            else NewItems = new Object[1];
            NewItems[NewItems.length - 1] = MenuItem;
            ItemMap.put(aClass, NewItems);
//            Log(ItemMap.toString());

            Click[] NewClicks;
            if (ClickMap.containsKey(aClass)) {
                Click[] Clicks = ClickMap.get(aClass);
                for (Click click : Clicks) {
                    if (click.equals(MyClick)) return true;
                }
                NewClicks = new Click[Clicks.length + 1];
                System.arraycopy(Clicks, 0, NewClicks, 0, Clicks.length);
            }
            else NewClicks = new Click[1];
            NewClicks[NewClicks.length - 1] = MyClick;
            ClickMap.put(aClass, NewClicks);
//            Log(ClickMap.toString());
        }
        return false;
    }

    public static boolean QQCustomMenuItemEquals(Object obj1, Object obj2)
    {
        if (obj1 == null || obj2 == null) return false;
        try {
            if (obj1.getClass().equals(QQCustomMenuItemClass) && obj2.getClass().equals(QQCustomMenuItemClass))
            {
//                Field field1 = XposedHelpers.findField(QQCustomMenuItemClass, "a");
//                String name1 = (String) field1.get(obj1);
//                String name2 = (String) field1.get(obj2);
                String name1 = (String) XposedHelpers.getObjectField(obj1, "a");
                String name2 = (String) XposedHelpers.getObjectField(obj2, "a");
                if (name1.equals(name2)) {
                    Field field2 = XposedHelpers.findField(QQCustomMenuItemClass, "b");
                    int id1 = (int) field2.get(obj1);
                    int id2 = (int) field2.get(obj2);
                    if (id1 == id2) return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class Click
    {
        public String ItemName;
        public int ItemID;

        Click(String name,int id)
        {
            ItemName = name;
            ItemID = id;
            Log("New Click -> " + ItemName + ":" + ItemID);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Click click = (Click) o;
            return ItemID == click.ItemID && ItemName.equals(click.ItemName);
        }

        public void run(XC_MethodHook.MethodHookParam param)
        {
            Log( ItemName + ":" + ItemID + " -> Run");
//            LogStackTrace(ItemName);
        }

    }

}
