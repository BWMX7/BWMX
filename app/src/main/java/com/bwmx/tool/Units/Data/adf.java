//package com.bwmx.tool.Units.Data;
//
//import androidx.annotation.Nullable;
//
//import com.bwmx.tool.Units.FileUnits;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class adf {
//    package com.bwmx.tool.Units.Data;
//
//import androidx.annotation.Nullable;
//
//import com.bwmx.tool.Units.FileUnits;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//    public class BubbleId extends BaseData{
//        private static String FileName = "/Data/BubbleId.json";
//        private static String MethodName = com.bwmx.tool.Units.Data.BubbleId.class.getName();
////    private static JSONObject Data;
//
//
//        public static boolean IfHasBackground(String ChatBackgroundId)
//        {
//            if (ChatBackground.has(ChatBackgroundId))  return true;
//            else return false;
//        }
//
//        public static boolean IfHasNewColor(String ChatBackgroundId, String BubbleId)
//        {
//            try {
//                if (IfHasBackground(ChatBackgroundId))
//                {
//                    JSONObject bubble = ChatBackground.getJSONObject(ChatBackgroundId);
////                FileUnits.writelog("[萌块]BubbleId " + BubbleId + "  " + bubble);
//                    if (bubble.has(BubbleId)) return true;
//                }
//            } catch (JSONException e)
//            {
//                FileUnits.writelog("[萌块]BubbleId IfHasNewColor\n" + e);
//            }
//            return false;
//        }
//
//        @Nullable
//        public static Integer GetNewColor(String ChatBackgroundId, String BubbleId)
//        {
//            try {
//                if (IfHasNewColor(ChatBackgroundId, BubbleId))
//                {
//                    Integer color = ChatBackground.getJSONObject(ChatBackgroundId).getInt(BubbleId);
//                    return color;
//                }
//            } catch (JSONException e)
//            {
//                FileUnits.writelog("[萌块]BubbleId GetNewColor\n" + e);
//            }
//            return null;
//        }
//
//        public static boolean SetNewColor(String ChatBackgroundId, String BubbleId, Integer color) {
//            try {
//                JSONObject bubble;
//                if (IfHasBackground(ChatBackgroundId))
//                {
//                    bubble = ChatBackground.getJSONObject(ChatBackgroundId);
//                    ChatBackground.remove(ChatBackgroundId);
//                    if (bubble.has(BubbleId)) bubble.remove(BubbleId);
//                }
//                else bubble = new JSONObject();
//                if (color != null) bubble.put(BubbleId, color);
//                if (bubble != null && bubble.length() > 0) ChatBackground.put(ChatBackgroundId, bubble);
//                return FileUnits.WriteStringToFile("BubbleId.json", ChatBackground.toString(), false);
//            } catch (JSONException e)
//            {
//                FileUnits.writelog("[萌块]BubbleId SetNewColor\n" + e);
//            }
//            return false;
//        }
//    }
//
//}
