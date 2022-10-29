package com.bwmx.tool.Units.Data;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;


public class APKData extends BaseData{

    public static class APP
    {
        public String AppPkgName;
        public String AppName;
        public int AppID;

        APP(String appPkgName, String appName, int appID)
        {
            AppPkgName = appPkgName;
            AppName = appName;
            AppID = appID;
        }
    }

    public ArrayList<APP> APPList;
    private int num = -1;

    {
        Init("/Data/UserAPK.json", "APKData");
        APPList = GetAllAPP();
        Collections.shuffle(APPList);
    }

    public String GetLocalSignature(String pkgname) {
        if (TextUtils.isEmpty(pkgname)) return null;
        String qm;
        switch (pkgname) {
            case "com.tencent.qqmusic":
                qm = "CBD27CD7C861227D013A25B2D10F0799";
                break;//1QQ音乐
            case "com.netease.cloudmusic":
                qm = "DA6B069DA1E2982DB3E386233F68D76D";
                break;//2网易云音乐
            case "com.kugou.android":
                qm = "FE4A24D80FCF253A00676A808F62C2C6";
                break;//3酷狗音乐
            case "cn.kuwo.player":
                qm = "BF9FF4FFB4C558A34EE3FD52C223EBF5";
                break;//4酷我音乐
            default:
                qm = null;
        }
        return qm != null ? qm.toUpperCase(Locale.ROOT) : null;
    }
//
//    public boolean PutUserSignature(int appId,String appName, String name) {
////        FileUnits.writelog("APKData \n" + jsonObject);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("Name", name);
//            jsonObject.put("AppID", appId);
//            String md5 = GetLocalSignature(appName);
//            jsonObject.put("SignatureMD5", md5);
//            FileUnits.writelog("APKData \n" + jsonObject);
//        } catch (JSONException e) {
//            FileUnits.writelog("APKData \n" + e);
//        }
//        return SetItemData(appName, (JSONObject) jsonObject);
//    }

    public boolean PutUserSignature(Object jsonObject)
    {
//        FileUnits.writelog("APKData \n" + jsonObject);
        if (jsonObject instanceof JSONObject) {
            return SetItemData((JSONObject) jsonObject);
        }
        return false;
    }

    public String GetSignature(String pkgname) {
        String md5 = (String) GetItemData(pkgname, "SignatureMD5");
        if (md5 == null || md5.equals("")) return GetLocalSignature(pkgname);
        if (md5.length() == 32) return md5;
        String sign = "00000000000000000000000000000000" + md5;
//        md5.set(String.format("%32s", md5.get()));// 00000abc
//        return md5.get();
        return sign.substring(sign.length() - 32);
    }

    public ArrayList<APP> GetAllAPP()
    {
        ArrayList<APP> arrayList = new ArrayList<>();
        arrayList.add(new APP("com.tencent.qqmusic", "QQ音乐", 100497308));
        arrayList.add(new APP("com.netease.cloudmusic", "网易云音乐", 100495085));
        arrayList.add(new APP("com.kugou.android", "酷狗音乐", 205141));
        arrayList.add(new APP("cn.kuwo.player", "酷我音乐", 100243533));

        for (Iterator<String> it = Data.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                JSONObject jsonObject = Data.getJSONObject(key);
                APP app = new APP(key, jsonObject.optString("Name"), jsonObject.optInt("AppID"));
                arrayList.add(app);
            } catch (JSONException ignored) {
            }
        }
        return arrayList;
    }

    public APP RandomApp(int choice)
    {
        int size = APPList.size();
        if (size == 0) return null;
        if (choice >= 0 && choice <= size) return APPList.get(choice);
        if (num >= size) {
            Collections.shuffle(APPList);
            num = 0;
        }
        else num++;
        return APPList.get(num);
    }

}
