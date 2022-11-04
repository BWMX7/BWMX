package com.bwmx.tool.Units.Data;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import de.robv.android.xposed.XposedHelpers;

public class RecentUserData extends BaseData
{
    public Class<?> RecentUserClass;
    private Object SelfUser;
    private ArrayList<Object> RecentUserList;

    {
        RecentUserClass = MethodFinder.GetClass("RecentUser");
        FileUnits.writelog("RecentUserData " + RecentUserClass);
        if (RecentUserClass != null) {
//            SelfUser = NewRecentUser(Main.MyUin, 0);
            Init("/Data/RecentUserData.json", "RecentUserData");
        }
    }

    public Object NewUser(String Uin, int UinType)
    {
        return XposedHelpers.newInstance(RecentUserClass, Uin, UinType);
    }

    public Object GetSelfData()
    {
        if (SelfUser == null && Main.MyUin != null)
        {
            SelfUser = NewUser(Main.MyUin, 0);
        }
        return SelfUser;
    }

    private ArrayList<Object> ReadData()
    {
        ArrayList<Object> arrayList = new ArrayList<>();

        for (Iterator<String> it = Data.keys(); it.hasNext(); ) {
            try {
            String name = it.next();
            Integer UinType = (Integer) GetItemData(name, "UinType");
            if (UinType == null) continue;
            if (UinType == 0) arrayList.add(NewUser((String) GetItemData(name, "QQ"), 0));
            else if (UinType == 1) arrayList.add(NewUser((String) GetItemData(name, "TroopUin"), 1));
            else if (UinType == 1000) {
                String TroopUin = (String) GetItemData(name, "TroopUin");
                String QQ = (String) GetItemData(name, "QQ");
                Object RecentUser = NewUser(QQ, 1000);
                Field field = XposedHelpers.findField(RecentUserClass, "troopUin");
                field.set(RecentUser, TroopUin);
                arrayList.add(RecentUser);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        arrayList.add(SelfUser);
        return arrayList;
    }

    public ArrayList<Object> GetAllData()
    {
        if (RecentUserList == null)
        {
            RecentUserList = ReadData();
        }
//        FileUnits.writelog("RecentUserData " + RecentUserList);
        return new ArrayList<>(RecentUserList);
    }

    public boolean AddNRecentUser(String TroopUin, String QQ, Integer UinType)
    {
        if (UinType == null) return false;
        String name = String.valueOf(((TroopUin == null ? "" : TroopUin) + (QQ == null ? "" : QQ)).hashCode() + UinType);
        boolean ok1 = SetItemData(name, "TroopUin", TroopUin);
        boolean ok2 = SetItemData(name, "QQ", QQ);
        boolean ok3 = SetItemData(name, "UinType", UinType);
        if (ok1 && ok2 && ok3) {
            RecentUserList = null;
            return true;
        }
        else return false;
    }

    public boolean RemoveRecentUser(String TroopUin, String QQ, Integer UinType)
    {
        if (UinType == null) return false;
        String name = String.valueOf(((TroopUin == null ? "" : TroopUin) + (QQ == null ? "" : QQ)).hashCode() + UinType);
        boolean ok = SetJSONData(name, null, true);
        if (ok) RecentUserList = null;
        return ok;
    }

    public String ChangeRecentUser(String TroopUin, String QQ, Integer UinType)
    {
        String name = String.valueOf(((TroopUin == null ? "" : TroopUin) + (QQ == null ? "" : QQ)).hashCode() + UinType);
        if (IfHasSetData(name)) return RemoveRecentUser(TroopUin, QQ, UinType) ? "已从最近转发列表删除" : "从最近转发列表删除失败";
        else  return AddNRecentUser(TroopUin, QQ, UinType)  ? "已添加到最近转发列表" : "添加到最近转发列表失败";
    }
}
