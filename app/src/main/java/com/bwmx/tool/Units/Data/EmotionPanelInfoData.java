package com.bwmx.tool.Units.Data;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bwmx.tool.Units.FileUnits;
import com.bwmx.tool.Units.MethodFinder;

import java.util.ArrayList;
import java.util.Iterator;

import de.robv.android.xposed.XposedHelpers;

public class EmotionPanelInfoData extends BaseData
{
    public Class<?> EmotionPanelInfoClass;

    private ArrayList<Object> EmoticonTabList;

    {
        EmotionPanelInfoClass = MethodFinder.GetClass("EmotionPanelInfo");
        FileUnits.writelog("EmotionPanelInfoData " + EmotionPanelInfoClass);
        
        if (EmotionPanelInfoClass != null) {
//            SelfUser = NewRecentUser(Main.MyUin, 0);
            Init("/Data/EmotionPanelInfo.json", "EmotionPanelInfoData");
        }
    }

    public Object NewEmotionPanelInfo(Integer ColumnNum, String epId, Integer Type)
    {
//        FileUnits.writelog(ColumnNum + " " + epId + " " + Type);
        Object emoticonPackage = XposedHelpers.newInstance(MethodFinder.GetClass("EmoticonPackage"));
        if (emoticonPackage != null) {
            XposedHelpers.setObjectField(emoticonPackage, "epId", epId);
            XposedHelpers.setIntField(emoticonPackage, "type", 6);
            XposedHelpers.setIntField(emoticonPackage, "jobType", 4);
//            FileUnits.writelog("2" + emoticonPackage);
//                MethodFinder.RuntimeService("IEmoticonManagerService", "saveEmoticonPackage", emoticonPackage2);
            MethodFinder.RuntimeService("IEmojiManagerService", "pullEmoticonPackage", emoticonPackage, true);
            return XposedHelpers.newInstance(EmotionPanelInfoClass, Type == null ? 0 : Type, ColumnNum == null ? 0 : ColumnNum, emoticonPackage);
        }
        return null;
    }
    

    @NonNull
    private ArrayList<Object> ReadData()
    {
        ArrayList<Object> arrayList = new ArrayList<>();

        for (Iterator<String> it = Data.keys(); it.hasNext(); ) {
            String name = it.next();
            String epId = (String) GetItemData(name, "EpId");
            if (TextUtils.isEmpty(epId)) continue;
            Object emotionPanelInfo = NewEmotionPanelInfo((Integer) GetItemData(name, "ColumnNum"), epId, (Integer) GetItemData(name, "Type"));
//            FileUnits.writelog(emotionPanelInfo);
            if (emotionPanelInfo != null) arrayList.add(emotionPanelInfo);
        }
        return arrayList;
    }

    public ArrayList<Object> GetAllData()
    {
        if (EmoticonTabList == null)
        {
            EmoticonTabList = ReadData();
        }
//        FileUnits.writelog("RecentUserData " + RecentUserList);
        return new ArrayList<>(EmoticonTabList);
    }

    public boolean AddEmotionPanelInfo(Integer ColumnNum, String EpId, Integer Type)
    {
        if (Type == null) return false;
        String name = ColumnNum + (EpId == null ? "" : EpId) + Type;
        boolean ok1 = SetItemData(name, "ColumnNum", ColumnNum);
        boolean ok2 = SetItemData(name, "EpId", EpId);
        boolean ok3 = SetItemData(name, "Type", Type);
        if (ok1 && ok2 && ok3) {
            EmoticonTabList = null;
            return true;
        }
        else return false;
    }

    public boolean RemoveEmotionPanelInfo(Integer ColumnNum, String EpId, Integer Type)
    {
        if (TextUtils.isEmpty(EpId)) return false;
        String name = ColumnNum + (EpId == null ? "" : EpId) + Type;
        boolean ok = SetJSONData(name, null, true);
        if (ok) EmoticonTabList = null;
        return ok;
    }

    public String ChangeEmotionPanelInfo(Integer ColumnNum, String EpId, Integer Type)
    {
        String name = ColumnNum + (EpId == null ? "" : EpId) + Type;
        if (IfHasItemData(name, "Type")) return RemoveEmotionPanelInfo(ColumnNum, EpId, Type) ? "已从表情列表删除" : "从表情列表删除失败";
        else  return AddEmotionPanelInfo(ColumnNum, EpId, Type)  ? "已添加到表情列表" : "添加到表情列表失败";
    }
}
