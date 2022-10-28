package com.bwmx.tool.Hook;

import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.HostInfo;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class TroopMemberListHook {
    public static void Hook() {
        Method MethodIfExists1 = MethodFinder.GetMethod("TroopMemberListAdapter", "init");
        if (MethodIfExists1 != null) {
            FileUnits.writelog("TroopMemberListAdapter OK");
            AtomicReference<String> methodName = new AtomicReference<>("m");
            if (HostInfo.getVerCode() >= 8845) methodName.set("J");
            else if (HostInfo.getVerCode() >= 8000) return;
            XposedBridge.hookMethod(MethodIfExists1, new XC_MethodHook() {
                @Override
                @SuppressWarnings("unchecked")
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object[] objArr = (Object[]) param.getResult();
                    LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) objArr[0];
//                 FileUnits.writelog("TroopMemberListAdapter "+ linkedHashMap.toString());

                    Object value = null;
                    for (Iterator<?> it = linkedHashMap.entrySet().iterator(); it.hasNext(); ) {
                        Entry<String,Object> item = (Entry<String,Object>) it.next();
                        if (item.getKey().equals(("已屏蔽"))) {
                            value = item.getValue();
                            it.remove();
                        }
                    }
                    if (value != null) linkedHashMap.put("[萌块 - 移至最低]已屏蔽群内消息用户", value);

    //                 FileUnits.writelog("TroopMemberListAdapter\n" + linkedHashMap.toString());

                    Iterator<?> it2 = linkedHashMap.keySet().iterator();
                    int size4 = linkedHashMap.keySet().size();
                    if (size4 == 0) {
                        param.setResult(new Object[0]);
                        return;
                    }

    //                 FileUnits.writelog("TroopMemberListAdapter\n g2" );

                    int[] iArr = new int[size4];
                    String[] strArr = new String[size4];
                    iArr[0] = 0;
                    for (int i16 = 1; i16 <= size4; i16++) {
                        String key = (String) it2.next();
                        if (i16 < size4)  iArr[i16] = iArr[i16] + iArr[i16 - 1] + ((List<?>) linkedHashMap.get(key)).size() + 1;
                        strArr[i16-1] = key;
                    }

    //                 FileUnits.writelog("TroopMemberListAdapter\n" + Arrays.toString(iArr));
    //                 FileUnits.writelog("TroopMemberListAdapter\n" + Arrays.toString(strArr));

                    Object ListAdapter = param.thisObject;
    //                 FileUnits.writelog("TroopMemberListAdapter\n" + ListAdapter);

                    Field field = XposedHelpers.findField(ListAdapter.getClass(), methodName.get());
    //                 FileUnits.writelog("TroopMemberListAdapter\n" + field);
                    Object troopMemberListActivity = field.get(ListAdapter);

    //                FileUnits.writelog("TroopMemberListAdapter\n" + troopMemberListActivity);
                    Field field2 = XposedHelpers.findField(troopMemberListActivity.getClass(), "mFrom");
    //                FileUnits.writelog("TroopMemberListAdapter\n" + field2);
                    int mFrom = (int) field2.get(troopMemberListActivity);
    //                FileUnits.writelog("TroopMemberListAdapter\n" + mFrom);

                    if (mFrom == 0) {
                        XposedHelpers.callMethod(troopMemberListActivity, "updateMaxItemCount", new Object[]{iArr});
                    }

    //                FileUnits.writelog("TroopMemberListAdapter\n g5" );

                    Object[] objArr2 = new Object[3];
                    objArr2[0] = linkedHashMap;
                    objArr2[1] = iArr;
                    objArr2[2] = strArr;
                    param.setResult(objArr2);
                }
            });
        }
    }
}
