//package com.bwmx.tool.Units.Data;
//
//import android.text.TextUtils;
//
//import com.bwmx.tool.Units.FileUnits;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicReference;
//
//@SuppressWarnings("unchecked")
//public class Signatureo {
//    public static HashMap<String, String> UserSignature = new HashMap<>();
//
//    static {
//        Object hashmap = FileUnits.ReadObjectFromFile("UserSignature.data");
//        if (hashmap instanceof HashMap) UserSignature = (HashMap<String, String>) hashmap;
////        FileUnits.writelog("[萌块]SignatureData " + UserSignature);
//    }
//
//    @androidx.annotation.Nullable
//    public static String GetLocalSignature(String pkgname) {
//        if (TextUtils.isEmpty(pkgname)) return null;
//        String qm;
//        switch (pkgname) {
//            case "com.tencent.sample":
//                qm = "1e9a7461d4dc2c85151a486c2b42aede";
//                break;//qqsdk
//            case "com.tencent.qqmusic":
//                qm = "cbd27cd7c861227d013a25b2d10f0799";
//                break;//1QQ音乐
//            case "com.netease.cloudmusic":
//                qm = "da6b069da1e2982db3e386233f68d76d";
//                break;//2网易云
//            case "com.kugou.android":
//                qm = "fe4a24d80fcf253a00676a808f62c2c6";
//                break;//3酷狗音乐
//            case "cn.kuwo.player":
//                qm = "bf9ff4ffb4c558a34ee3fd52c223ebf5";
//                break;//4酷我音乐
//            case "tv.danmaku.bili":
//                qm = "7194d531cbe7960a22007b9f6bdaa38b";
//                break;//5哔哩哔哩
//            case "com.android.mediacenter":
//                qm = "bb7bce1b1090fc3a6b67ebc88701acdd";
//                break;//6华为音乐
//            case "cn.wenyu.bodian":
//                qm = "5380ca99d2bea3173b0b6e52cff44b45";
//                break;//7波点音乐
//            case "cmccwm.mobilemusic":
//                qm = "6cdc72a439cef99a3418d2a78aa28c73";
//                break;//8咪咕音乐
//            case "com.ximalaya.ting.android":
//                qm = "22a001357629de32518a24508149689f";
//                break;//9喜马拉雅
//            case "com.ting.mp3.android":
//                qm = "586742e88a2e6a19e996598ec336b61";
//                break;//10千千音乐
//            case "com.android.bbkmusic":
//                qm = "cb3817d94474ee58ab37d0825bd25f69";
//                break;//11vivo音乐
//            case "com.shoujiduoduo.ringtone":
//                qm = "81b9fd90792b25c1899bb19827bdc335";
//                break;//12铃声多多
//            case "com.shoujiduoduo.dj":
//                qm = "2515a068a902e6c069d967c31c02fb02";
//                break;//12DJ多多
//            case "com.oppo.music":
//                qm = "f2da164040f0845dc5a614ead1f8bd7e";
//                break;//14oppo音乐
//            case "com.baidu.searchbox":
//                qm = "c2b0b497d0389e6de1505e7fd8f4d539";
//                break;//15百度APP
//            case "com.tencent.mm":
//                qm = "4b980868cac83f6bb4fb529954cc020";
//                break;//16微信
//            case "swin.com.iapp":
//                qm = "26215e153c2abf783485c28e07e75867";
//                break;//17千变语音
//            case "com.sina.weibo":
//                qm = "18da2bf10352443a00a5e046d9fca6bd";
//                break;//18新浪微博
//            case "com.baidu.tieba":
//                qm = "673004cf2f6efdec2385c8116c1e8c14";
//                break;//19百度贴吧
//            case "com.baidu.netdisk":
//                qm = "ae5821440fab5e1a61a025f014bd8972";
//                break;//20百度网盘
//            case "com.lanjingren.ivwen":
//                qm = "43070800dbad9cc337595a3c7b5b4996";
//                break;//21美篇
//            case "com.tencent.tmgp.sgame":
//                qm = "e9b518b0fa85c7b7d5c2c5bfba79217d";
//                break;//22王者荣耀
//            case "com.sankuai.meituan.takeoutnew":
//                qm = "638c81261479c2104ede3f2518e91725";
//                break;//23美团外卖
//            case "com.smile.gifmaker":
//                qm = "f938c4f0995a83c9bf31f0c64322589";
//                break;//24快手
//            case "com.shizhuang.duapp":
//                qm = "ba464b87b06c958b6307a03074c49f2b";
//                break;//25得物
//            case "com.tencent.tmgp.pubgmhd":
//                qm = "38b26479e4b500e8161598315c4ad35c";
//                break;//26和平精英
//            case "com.jingdong.app.mall":
//                qm = "e0d1a70367c58d5d41c4678dfd05f84f";
//                break;//27京东
//            case "com.tencent.qqlive":
//                qm = "106409a8f91a970d58beb2263ce7550f";
//                break;//28腾讯视频
//            case "me.ele":
//                qm = "3a407fd39704d8e4b21af0ef69c3f43f";
//                break;//29饿了么
//            case "com.duowan.kiwi":
//                qm = "47f4637cfb4fee7e91debefab92c5e31\n";
//                break;//30虎牙直播
//            case "com.baidu.homework":
//                qm = "13a0a8019be4015ed20e075d824f1696";
//                break;//31作业帮
//            case "com.xunmeng.pinduoduo":
//                qm = "6e26e5a980e0ba33fe2e4ef23607dc54";
//                break;//32拼多多
//            case "com.xmcy.hykb":
//                qm = "808df4ce6d259327b607a256a03091a9";
//                break;//33好游快爆
//            case "com.vmall.client":
//                qm = "ff83ec7e9cfbc2eae4923aa852ae4a20";
//                break;//34华为商城
//            case "com.tencent.tmgp.cf":
//                qm = "aeabc9e282f62432d2be8e728da6210c";
//                break;//35穿越火线
//            case "com.tencent.mf.uam":
//                qm = "95a4cd03967bf4a4f5a639f6ee4ebee9";
//                break;//36暗区突围
//            case "com.maohouzi.voice":
//                qm = "dd93db5dcf4cdd02c714fd20a6bcb319";
//                break;//37一秒语音包变声器
//            case "com.xingin.xhs":
//                qm = "6cfca61d9d1eca56844806706ba18cf7";
//                break;//38小红书
//            case "com.youdao.dict":
//                qm = "2a9dfdbe5a6aa87487cae962f04772d8";
//                break;//39有道词典
//            case "com.coolapk.market":
//                qm = "03722d493a5a6f991b9bb8a8f2006a17";
//                break;//40酷安
//            case "com.autonavi.minimap":
//                qm = "3f9eaea4f2d4285c2ddbbda739136479";
//                break;//41高德地图
//            case "com.tmri.app.main":
//                qm = "8b7553481074143ff45f257f186cc740";
//                break;//42交管12123
//            case "com.yingyonghui.market":
//                qm = "f45a780b1f1cd64534d04a08b1e7cafc";
//                break;//43应用汇
//            case "com.tencent.lolm":
//                qm = "a8df121f79960593b23a558e2154ffba";
//                break;//44英雄联盟手游
//            case "com.sinovatech.unicom.ui":
//                qm = "dacd0a6815659df3565408cdfd80deea";
//                break;//45中国联通
//            case "com.ct.client":
//                qm = "97fd087836784134b502cd438cb24b2d";
//                break;//46电信营业厅
//            case "com.greenpoint.android.mc10086.activity":
//                qm = "e0bcb95cdaf4777715e4fa5989a4446c";
//                break;//47中国移动
//            case "com.huawei.fans":
//                qm = "f66394486453141e6502f436eb072054";
//                break;//48花粉俱乐部
//            case "com.taptap":
//                qm = "586206d6cb934b69e28ad70461c3224e";
//                break;//49taptap
//            case "com.ataaw.tianyi":
//                qm = "d0bfbdf1a825fa9f4de3f81b78159e07";
//                break;//50天翼生活
//            case "com.jxedt":
//                qm = "4afbd87ecb0140f46c394ef236ddb214";
//                break;//51驾校一点通
//            case "com.tencent.karaoke":
//                qm = "c7df05ab9bf28e06613ae61a5115985d";
//                break;//52全民K歌
//            case "com.miui.player":
//                qm = "701478a1e3b4b7e3978ea69469410f13";
//                break;//53MIUI音乐
//            case "com.ztgame.bob":
//                qm = "aec54c485f33645f7c7e9b51d4aba5d0";
//                break;//54球球大作战
//            case "com.mihoyo.hyperion":
//                qm = "abdcfbc2380da2413c1e0be7a118dd9e";
//                break;//55米游社
//            case "com.sinovatech.gxmobile.ui":
//                qm = "dacd0a6815659df3565408cdfd80deea";
//                break;//56广西移动
//            case "com.xwtec.sd.mobileclient":
//                qm = "7318834fd9f106a3532593fb5d94cfcd";
//                break;//57山东移动
//            case "com.sankuai.meituan":
//                qm = "638c81261479c2104ede3f2518e91725";
//                break;//58美团
//            case "com.huawei.appmarket":
//                qm = "1542c7528baff88abda1c233ba482f68";
//                break;//59华为应用市场
//            case "com.iflytek.inputmethod":
//                qm = "452af6b11a606c6674871b84510acb70";
//                break;//60讯飞输入法
//            case "com.haier.uhome.uplus":
//                qm = "d2b9deaa9b23b84ca3025dd1ff52d924";
//                break;//61海尔智家
//            case "com.xtuone.android.syllabus":
//                qm = "2267de29e05d8710ca0c7cd4c1e001c";
//                break;//62超级课程表
//            case "cn.wps.moffice_eng":
//                qm = "552ebae6b47eace30258649adb8287b6";
//                break;//63WPS Office
//            case "com.dragon.read":
//                qm = "a4a27c2633195374c15651ffc3c4a497";
//                break;//64番茄免费小说
//            case "com.kmxs.reader":
//                qm = "c45a9e4b154696cc4ee39bb6d060b8c2";
//                break;//65七猫免费小说
//            case "com.bly.dkplat":
//                qm = "3c8e2bf7509880e7db58d6ccab5e6c50";
//                break;//66小X分身
//            case "com.huawei.browser":
//                qm = "745a9c4d499d675c28400fa0c22f5de7";
//                break;//67华为浏览器
//            case "com.ZWSoft.ZWCAD":
//                qm = "6c481f3e671d26c489ca0923a5c6b89a";
//                break;//68CAD看图大师
//            case "com.zhihu.android":
//                qm = "5c4f618536eaf9ae0e2628c5af1693bc";
//                break;//69知乎
//            case "com.tplink.smbcloud":
//                qm = "449fa6c78f3111dc2c6c28be9025473";
//                break;//70TP-LINK商云
//            case "com.cashtoutiao":
//                qm = "1c5901f7b2520f784fcd2b29af285d39";
//                break;//71惠头条
//            case "cn.xiaochuankeji.tieba":
//                qm = "2e4f5e0b717b87005af9030cbd4ecada";
//                break;//72最右
//            case "com.kuaishou.nebula":
//                qm = "f938c4f0995a83c9bf31f0c64322589";
//                break;//73快手极速版
//            case "com.honor.club":
//                qm = "b0f7623bf7613fd43eda48a3a5fec573";
//                break;//74荣耀俱乐部
//            case "com.huawei.gamebox":
//                qm = "17ecbf0e15537df9fcc995331be6f3dd";
//                break;//75华为游戏中心
//            case "com.tencent.qqlivehuawei":
//                qm = "7e640f37e6c612c368bd0b53f4a23759";
//                break;//76华为视频腾讯组件
//            case "com.oppo.store":
//                qm = "7815c5174154b7ce33f023602106ecc6";
//                break;//77oppo商城
//            case "com.oppo.community":
//                qm = "7815c5174154b7ce33f023602106ecc6";
//                break;//78oppo社区
//            case "com.coloros.videoeditor":
//                qm = "d5f2d71470028a27f040234dbe7b62b0";
//                break;//79Soloop即录剪辑
//            case "com.nearme.gamecenter":
//                qm = "7815c5174154b7ce33f023602106ecc6";
//                break;//80oppo游戏中心
//            case "com.heytap.browser":
//                qm = "e99f45e3f833dafb7552542ccd3abb80";
//                break;//81oppo浏览器
//            case "com.tencent.map":
//                qm = "9869a436a0eb352dc59c5f66b2ce7d46";
//                break;//82腾讯地图
//            case "com.UCMobile":
//                qm = "51a5eb6e85033f42271535aad119a2f4";
//                break;//83UC浏览器
//            case "com.hicorenational.antifraud":
//                qm = "1034c7fd488bb6eff47d8b77c32eb256";
//                break;//84国家反诈
//            case "com.gotokeep.keep":
//                qm = "3d45453a9fc2a8994d35aea5fc66741";
//                break;//85Keep
//            case "com.iflytek.oshall.ahzwfw":
//                qm = "745f0fe765cabffd0809d0902914b729";
//                break;//86皖事通
//            case "az.studio.gkztc":
//                qm = "2fbd8959480fd20f9cecca44042e37f4";
//                break;//87高考直通车
//            case "com.wuba.zhuanzhuan":
//                qm = "ae88b4b0fb55ba346c9e65e3f373d413";
//                break;//88转转
//            case "cn.xiaochuankeji.zuiyouLite":
//                qm = "2e4f5e0b717b87005af9030cbd4ecada";
//                break;//89皮皮搞笑
//            case "com.tencent.gamehelper.smoba":
//                qm = "f8cdcfe8b8e226916e466b454cc72d24";
//                break;//90王者营地
//            case "com.cat.readall":
//                qm = "aea615ab910015038f73c47e45d21466";
//                break;//91悟空浏览器
//            case "com.c2vl.kgamebox":
//                qm = "11c0bd06c1630aa67fb43fc2593f165a";
//                break;//92狼人杀
//            case "com.tencent.KiHan":
//                qm = "68ce8a967463a2c34b38adc48d0d373d";
//                break;//93火影忍者
//            case "com.tencent.tmgp.speedmobile":
//                qm = "9bcbafe32ae8382cc224f5aab0ee8383";
//                break;//94QQ飞车手游
//            case "com.njh.biubiu":
//                qm = "d01532211e2089de93cdf9ff19ac7ece";
//                break;//95BiuBiu加速器
//            case "cn.wsds.gamemaster":
//                qm = "6d4dcf22f1edf02c9d415b1b2e31d409";
//                break;//96迅游手游加速器
//            case "cn.ccspeed":
//                qm = "ed1b9267223df9de0dcadfb42404b620";
//                break;//97CC加速器
//            case "com.mt.mtxx.mtxx":
//                qm = "8e1d5ad9ea79e1b3068afa19c8e07ebe";
//                break;//98美图秀秀
//            case "com.duitang.main":
//                qm = "b1391442c0cc69da32e5a2661b28d546";
//                break;//99堆糖
//            case "com.android.browser":
//                qm = "701478a1e3b4b7e3978ea69469410f13";
//                break;//100小米浏览器
//            case "com.wepie.snake.huawei":
//                qm = "678a930b9829b54a44f92a840916f7d1";
//                break;//101贪吃蛇大作战
//            case "com.hlg.daydaytobusiness":
//                qm = "498a30376acdfd4d540ab1df066fb422";
//                break;//102稿定设计
//            case "com.alicloud.databox":
//                qm = "8f5cca115b852c2528d518651ca93d03";
//                break;//103阿里云盘
//            case "com.qqgame.hlddz":
//                qm = "5c101bee9d0f4cc50ae05f3736a44c84";
//                break;//104欢乐斗地主
//            case "com.tencent.jgm":
//                qm = "46fb386ce8587d0a5940c9e121f54323";
//                break;//105家国梦
//            case "com.tencent.weishi":
//                qm = "2a281593d71df33374e6124e9106df08";
//                break;//106微视
//            case "com.wajijiwa.club":
//                qm = "baaef3344913466a99697dd42642d92b";
//                break;//107weecho
//            case "info.muge.appshare":
//                qm = "8d4e43d2b97463bc23f80bcd665b83ac";
//                break;//109APP分享
////                                    case "":
////                                        qm = "";
////                                        break;//
////com.unionpay f866bf76d5423c5de1b53b93a789f652 中国银联
//            default:
//                qm = null;
//        }
//        return qm;
//    }
//
//    @androidx.annotation.Nullable
//    public static String GutUserSignature(String pkgname)
//    {
////        FileUnits.writelog("[萌块]GutUserSignature " + pkgname);
//        if (TextUtils.isEmpty(pkgname)) return null;
//        boolean ContainsKey= UserSignature.containsKey(pkgname);
//        if(ContainsKey) return UserSignature.get(pkgname);
//        else return null;
//    }
//
////    public static boolean PutUserSignature(String pkgname,String signature)
////    {
////         boolean ContainsKey= UserSignature.containsKey(pkgname);
////         if(!ContainsKey) UserSignature.put(pkgname, signature);
////         return !ContainsKey;
////    }
//
//    public static boolean PutUserSignature(Object hashmap)
//    {
//        if (hashmap instanceof HashMap) {
//            UserSignature.putAll((Map<? extends String, ? extends String>) hashmap);
//            return FileUnits.WriteObjectToFile("UserSignature.data", UserSignature);
//        }
//        return false;
//    }
//
//    public static String GetSignature(String pkgname) {
//        AtomicReference<String> md5 = new AtomicReference<>(GutUserSignature(pkgname));
//        if (md5.get() == null) md5.set(GetLocalSignature(pkgname));
////        if (md5.length() == 31) md5 = "0" + md5;
//        md5.set(String.format("%32s", md5.get()));// 00000abc
//        return md5.get();
//    }
//
//}
