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

    public ArrayList<Object> ReadData()
    {
        ArrayList<Object> arrayList = new ArrayList<>();

        for (Iterator<String> it = Data.keys(); it.hasNext(); ) {
            String Uin = it.next();
            try {
                JSONObject jsonObject = Data.getJSONObject(Uin);
//                String Uin = jsonObject.optString("Uin");
                int UinType = jsonObject.optInt("UinType");
                Object RecentUser = NewUser(Uin, UinType);
                arrayList.add(RecentUser);
            } catch (JSONException ignored) {
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
}
