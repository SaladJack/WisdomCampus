package com.kai.wisdom_scut.db;

import com.kai.wisdom_scut.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmConfiguration;

/**
 * Created by tusm on 16/8/3.
 */

public class Constants {
    public static class Service{
        public static String[] mainServiceNames =
                {"一键上网","拾卡寻人","就业招聘","学生活动"};
        public static String[] subServiceNames =
                {
                        "专业信息","教学计划","学术课程表","成绩查询",
                        "奖学金","助学金","助学贷款","财务数据",
                        "信息网络","校园资讯","网路故障","新闻门户",
                        "人事服务","教师课程表","科研信息","签到",
                        "帮助咨询","重要通知","宿管","资产处",
                        "成绩分析","消费分析","签到分析","辅修分析",
                        "教学分析","更多"
                };
        public static String[] allServiceNames;
        public static HashMap<String,Integer> map = new HashMap<>();
        static{
            allServiceNames = new String[mainServiceNames.length + subServiceNames.length];
            for (int i = 0; i < allServiceNames.length; ++i){
                if (i < mainServiceNames.length)
                    allServiceNames[i] = mainServiceNames[i];
                else
                    allServiceNames[i] = subServiceNames[i - mainServiceNames.length];
            }


            //main
            map.put("一键上网",R.mipmap.yjsw);
            map.put("拾卡寻人",R.mipmap.skxr);
            map.put("就业招聘",R.mipmap.jyzp);
            map.put("学生活动",R.mipmap.xshd);
            //sub
            map.put("专业信息",R.mipmap.zyxx);
            map.put("教学计划",R.mipmap.jxjh);
            map.put("学术课程表",R.mipmap.xskcb);
            map.put("成绩查询",R.mipmap.cjcx);
            map.put("奖学金",R.mipmap.jxj);
            map.put("助学金",R.mipmap.zxj);
            map.put("助学贷款",R.mipmap.zxdk);
            map.put("财务数据",R.mipmap.cwsj);
            map.put("信息网络",R.mipmap.xxwl);
            map.put("校园资讯",R.mipmap.xyzx);
            map.put("网路故障",R.mipmap.wlgz);
            map.put("新闻门户",R.mipmap.xwmh);
            map.put("人事服务",R.mipmap.rsfw);
            map.put("教师课程表",R.mipmap.jskcb);
            map.put("科研信息",R.mipmap.kyxx);
            map.put("签到",R.mipmap.qd);
            map.put("帮助咨询",R.mipmap.bzzx);
            map.put("重要通知",R.mipmap.zytz);
            map.put("宿管",R.mipmap.sg);
            map.put("资产处",R.mipmap.zcc);
            map.put("成绩分析",R.mipmap.cjfx);
            map.put("消费分析",R.mipmap.xffx);
            map.put("签到分析",R.mipmap.qdfx);
            map.put("辅修分析",R.mipmap.fxfx);
            map.put("教学分析",R.mipmap.jxfx);
            map.put("更多",R.drawable.more);
        }
    }


    public static final String UserTestData = " {\n" +
            "  \"result\":\"success\",\n" +
            "  \"name\":\"小明\",\n" +
            "  \"number\":\"201436750419\",\n" +
            "  \"phoneNumber\":\"13800000000\",\n" +
            "  \"emailAddress\":\"666@kaigui.com\",\n" +
            "  \"avatarUrl\":\"http://i.imgur.com/UePbdph.jpg\",\n" +
            "  \"cardNumber\":\"123456\"\n" +
            "  }\n";

    public static final String CollectionTestData  = " [\n" +
            "    {\n" +
            "  \"serviceName\":\"专业信息\",\n" +
            "  \"collectTime\":1470389600000,\n" +
            "  \"collectionContent\":\"并没有什么卵专业信息\"\n" +
            "    },\n" +
            "      {\n" +
            "  \"serviceName\":\"教学计划\",\n" +
            "  \"collectTime\":1470409600000,\n" +
            "  \"collectionContent\":\"并没有什么卵教学计划\"\n" +
            "},  {\n" +
            "  \"serviceName\":\"学术课程表\",\n" +
            "  \"collectTime\":1470382600000,\n" +
            "  \"collectionContent\":\"并没有什么卵学术课程表\"\n" +
            "}]";




    public static final String serviceMsgData = "[\n" +
            "{\n" +
            "\"serviceName\":\"就业招聘\",\n" +
            "\"serviceImageUrl\":\"http://musicdata.baidu.com/data2/pic/d25fb9f6c06a362b9f5f67810edec2ad/267472925/267472925.jpg\",\n" +
            "\"serviceContent\":\"凯归招聘!!!!\",\n" +
            "\"serviceTime\":400000\n" +
            "},\n" +
            "{\n" +
            "\"serviceName\":\"校园资讯\",\n" +
            "\"serviceImageUrl\":\"http://musicdata.baidu.com/data2/pic/eede55e93e4f0353b1eea0a7627e7be1/267709262/267709262.jpg\",\n" +
            "\"serviceContent\":\"王迎军勇夺蝶泳金牌\",\n" +
            "\"serviceTime\":1470369600000\n" +
            "}\n" +
            "]";



    public static byte[] key = new byte[64];
    public static RealmConfiguration config;

    public static class Api{
        public static final String simiSimiBaseUrl = "http://www.xiaodoubi.com/";
    }

    public static class BaiduYuYin{
        public static final String EXTRA_KEY = "key";
        public static final String EXTRA_SECRET = "secret";
        public static final String EXTRA_SAMPLE = "sample";
        public static final String EXTRA_SOUND_START = "sound_start";
        public static final String EXTRA_SOUND_END = "sound_end";
        public static final String EXTRA_SOUND_SUCCESS = "sound_success";
        public static final String EXTRA_SOUND_ERROR = "sound_error";
        public static final String EXTRA_SOUND_CANCEL = "sound_cancel";
        public static final String EXTRA_INFILE = "infile";
        public static final String EXTRA_OUTFILE = "outfile";

        public static final String EXTRA_LANGUAGE = "language";
        public static final String EXTRA_NLU = "nlu";
        public static final String EXTRA_VAD = "vad";
        public static final String EXTRA_PROP = "prop";

        public static final String EXTRA_OFFLINE_ASR_BASE_FILE_PATH = "asr-base-file-path";
        public static final String EXTRA_LICENSE_FILE_PATH = "license-file-path";
        public static final String EXTRA_OFFLINE_LM_RES_FILE_PATH = "lm-res-file-path";
        public static final String EXTRA_OFFLINE_SLOT_DATA = "slot-data";
        public static final String EXTRA_OFFLINE_SLOT_NAME = "name";
        public static final String EXTRA_OFFLINE_SLOT_SONG = "song";
        public static final String EXTRA_OFFLINE_SLOT_ARTIST = "artist";
        public static final String EXTRA_OFFLINE_SLOT_APP = "app";
        public static final String EXTRA_OFFLINE_SLOT_USERCOMMAND = "usercommand";

        public static final int SAMPLE_8K = 8000;
        public static final int SAMPLE_16K = 16000;

        public static final String VAD_SEARCH = "search";
        public static final String VAD_INPUT = "input";
    }
}
