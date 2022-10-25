package com.bwmx.tool.Units.Data;

import androidx.annotation.Nullable;

import com.bwmx.tool.Units.FileUnits;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class BaseData {
    public String FileName = null;
    public String MethodName = null;
    public JSONObject Data;


    protected void Init(String fileName, String methodName)
    {
        try {
        FileName = fileName;
        MethodName = methodName;
        FileUnits.writelog("[萌块]" + MethodName + " Init -> " + FileName);
        String json = FileUnits.ReadStringFromFile(FileName);
        if (json != null) Data = new JSONObject(json);
        } catch (JSONException e) {
            FileUnits.writelog("[萌块]" + MethodName + " Init\n" + e);
        }
        if (Data == null) Data = new JSONObject();
//        FileUnits.writelog("[萌块]" + MethodName + " Init -> " + this);
    }

    public boolean IfHasSetData(String SetName)
    {
        return Data.has(SetName);
    }

    public boolean IfHasItemData(String SetName, String ItemName)
    {
        try {
            if (IfHasSetData(SetName))
            {
                JSONObject jsonObject = Data.getJSONObject(SetName);
//                FileUnits.writelog("[萌块]BubbleId " + BubbleId + "  " + bubble);
                if (jsonObject.has(ItemName)) return true;
            }
        } catch (JSONException e)
        {
            FileUnits.writelog("[萌块]" + MethodName + " IfHasItemData\n" + e);
        }
        return false;
    }

    @Nullable
    public Object GetItemData(String SetName, String ItemName)
    {
        try {
            if (IfHasItemData(SetName, ItemName))
            {
                return Data.getJSONObject(SetName).get(ItemName);
            }
        } catch (JSONException e)
        {
            FileUnits.writelog("[萌块]" + MethodName + " GetItemData\n" + e);
        }
        return null;
    }

    public boolean SetItemData(String SetName, String ItemName, Object data) {
        try {
            JSONObject jsonObject;
            if (IfHasSetData(SetName))
            {
                jsonObject = Data.getJSONObject(SetName);
                Data.remove(SetName);
//                if (jsonObject.has(ItemName)) jsonObject.remove(ItemName);
            }
            else jsonObject = new JSONObject();
            if (data != null) jsonObject.put(ItemName, data);
            if (jsonObject.length() > 0) Data.put(SetName, jsonObject);
            return FileUnits.WriteStringToFile(FileName, Data.toString(), false);
        } catch (JSONException e)
        {
            FileUnits.writelog("[萌块]" + MethodName + " SetItemData\n" + e);
        }
        return false;
    }
    public boolean SetItemData(String SetName, JSONObject data) {
        try {
            JSONObject jsonObject;
            if (IfHasSetData(SetName))
            {
                jsonObject = Data.getJSONObject(SetName);
                Data.remove(SetName);
                for (Iterator<String> it = data.keys(); it.hasNext(); ) {
                    String key = it.next();
                    jsonObject.put(key, data.get(key));
                }
            }
            else jsonObject = data;
            Data.remove(SetName);
            if (jsonObject != null && jsonObject.length() > 0) Data.put(SetName, jsonObject);
            return FileUnits.WriteStringToFile(FileName, Data.toString(), false);
        } catch (JSONException e)
        {
            FileUnits.writelog("[萌块]" + MethodName + " SetItemData\n" + e);
        }
        return false;
    }

    public boolean SetItemData(JSONObject data) {
        try {
            for (Iterator<String> it = data.keys(); it.hasNext(); ) {
                String key = it.next();
                Data.put(key, data.get(key));
            }
            return FileUnits.WriteStringToFile(FileName, Data.toString(), false);
        } catch (JSONException e)
        {
            FileUnits.writelog("[萌块]" + MethodName + " SetItemData\n" + e);
        }
        return false;
    }

}
