package com.bwmx.tool.Units.Data;

import android.text.TextUtils;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;

public class SwitchData extends BaseData
{

    {
        Init("/Data/Switch.json", "SwitchData");
        FileUnits.ToastSwitch = GetSwitch("ToastError", true);
    }

    public boolean GetSwitch(String Name)
    {
        return GetSwitch(Main.MyUin, Name);
    }

    public boolean GetSwitch(String Name, boolean Default)
    {
        return GetSwitch(Main.MyUin, Name, Default);
    }

    public boolean PutSwitch(String Name, boolean Switch)
    {
        return PutSwitch(Main.MyUin, Name, Switch);
    }

    public boolean GetSwitch(String Uin, String Name)
    {
        if (TextUtils.isEmpty(Name)) return false;
        if (TextUtils.isEmpty(Uin)) return GetSwitch("Globe", Name);

        Boolean Switch = (Boolean) GetItemData(Uin, Name);
        if (Switch == null)
        {
            PutSwitch(Uin, Name, false);
            return false;
        }
        else return Switch;
    }

    public boolean GetSwitch(String Uin, String Name, boolean Default)
    {
        if (TextUtils.isEmpty(Name)) return false;
        if (TextUtils.isEmpty(Uin)) return GetSwitch("Globe", Name, Default);

        Boolean Switch = (Boolean) GetItemData(Uin, Name);
        if (Switch == null)
        {
            if (Default) PutSwitch(Uin, Name, true);
            return Default;
        }
        else return Switch;
    }

    public boolean PutSwitch(String Uin, String Name, boolean Switch)
    {
        if (TextUtils.isEmpty(Name)) return false;
        if (TextUtils.isEmpty(Uin)) return PutSwitch("Globe", Name, Switch);

        boolean ok = SetItemData(Uin, Name, Switch);
        FileUnits.writelog("修改 " + Uin + ":" + Name + " 开关 -> " + Switch + " " + (ok ? "成功" : "失败"));
        return ok;
    }
}