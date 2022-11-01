package com.bwmx.tool.Units.Data;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import org.json.JSONException;
import org.json.JSONObject;

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
            String Uin = it.next();
            Integer UinType = (Integer) GetItemData(Uin, "UinType");
            if (UinType != null) {
                Object RecentUser = NewUser(Uin, UinType);
                arrayList.add(RecentUser);
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

    public boolean AddNRecentUser(String Uin, Integer UinType)
    {
        boolean ok = SetItemData(Uin, "UinType", UinType);
        if (ok) RecentUserList = null;
        return ok;
    }

    public boolean RemoveRecentUser(String Uin, Integer UinType)
    {
        boolean ok = SetJSONData(Uin, null, true);
        if (ok) RecentUserList = null;
        return ok;
    }

    public String ChangeRecentUser(String Uin, Integer UinType)
    {
        if (IfHasItemData(Uin, "UinType")) return RemoveRecentUser(Uin, UinType) ? "已从最近转发列表删除" : "从最近转发列表删除失败";
        else  return AddNRecentUser(Uin, UinType)  ? "已添加到最近转发列表" : "添加到最近转发列表失败";
    }
}
