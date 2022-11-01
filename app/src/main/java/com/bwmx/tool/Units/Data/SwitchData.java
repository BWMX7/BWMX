package com.bwmx.tool.Units.Data;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;

public class SwitchData extends BaseData
{

    {
        Init("/Data/Switch.json", "SwitchData");
    }

    public boolean GetSwitch(String Name)
    {
        return GetSwitch(Name, false);
    }

    public boolean GetSwitch(String Name, boolean Default)
    {
        Boolean Switch = (Boolean) GetItemData(Main.MyUin, Name);
        if (Switch == null)
        {
            if (Default) PutSwitch(Name, true);
            return Default;
        }
        else return Switch;
    }

    public boolean PutSwitch(String Name, boolean Switch)
    {
        boolean ok = SetItemData(Main.MyUin, Name, Switch);
        FileUnits.writelog("修改 " + Name + " 开关 -> " + Switch + " " + (ok ? "成功" : "失败"));
        return ok;
    }
}