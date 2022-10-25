package com.bwmx.tool.Hook;

import com.bwmx.tool.Main;
import com.bwmx.tool.Units.FileUnits;

import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class MiniAppLogin{
    public static HashMap<String,Object> Code = new HashMap<>();

    public static void Hook(){
    XposedHelpers.findAndHookMethod("mqq.app.MainService", Main.mLoader,
            "receiveMessageFromMSF", "com.tencent.qphone.base.remote.ToServiceMsg",
            "com.tencent.qphone.base.remote.FromServiceMsg",new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) {
                        Object fromMsg = param.args[1];
                        String serviceCmd = (String) XposedHelpers.callMethod(fromMsg, "getServiceCmd");
                        if(serviceCmd.equals("LightAppSvc.mini_program_auth.GetCode")) {
                            FileUnits.writelog(fromMsg.toString());
                            Code.put(serviceCmd, fromMsg);
                        }
                }
            });
    }
    public static Object GetMiniAppCode(String app){
        return Code.get(app);
    }
}
