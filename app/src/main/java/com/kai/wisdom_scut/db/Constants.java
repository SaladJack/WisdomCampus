package com.kai.wisdom_scut.db;

import com.kai.wisdom_scut.R;

import java.util.HashMap;
import java.util.Map;

import io.realm.RealmConfiguration;

/**
 * Created by tusm on 16/8/3.
 */

public class Constants {
    public static class Service{
        public static String[] serviceNames= {"就业信息","校园资讯"};
        public static HashMap<String,Integer> map = new HashMap<>();
        static{
            map.put("就业信息",R.mipmap.jyzp);
            map.put("校园资讯",R.mipmap.xyzx);
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

    public static final String CollectionTestData  = "[\n" +
            "    {\n" +
            "  \"serviceId\":0,\n" +
            "  \"serviceName\":\"服务0\",\n" +
            "  \"collectTime\":10000000\n" +
            "    },\n" +
            "      {\n" +
            "  \"serviceId\":1,\n" +
            "  \"serviceName\":\"服务1\",\n" +
            "  \"collectTime\":20000000\n" +
            "},  {\n" +
            "  \"serviceId\":2,\n" +
            "  \"serviceName\":\"服务2\",\n" +
            "  \"collectTime\":30000000\n" +
            "}]";

    public static final String sCollection = "{\n" +
            "  \"serviceId\":1,\n" +
            "  \"serviceName\":\"服务1\",\n" +
            "  \"collectTime\":20000000\n" +
            "}";


    public static final String serviceMsgData = "[\n" +
            "{\n" +
            "\"serviceName\":\"就业信息\",\n" +
            "\"serviceImageUrl\":\"http://musicdata.baidu.com/data2/pic/d25fb9f6c06a362b9f5f67810edec2ad/267472925/267472925.jpg\",\n" +
            "\"serviceContent\":\"这是内容\",\n" +
            "\"serviceTime\":400000\n" +
            "},\n" +
            "{\n" +
            "\"serviceName\":\"校园资讯\",\n" +
            "\"serviceImageUrl\":\"http://musicdata.baidu.com/data2/pic/eede55e93e4f0353b1eea0a7627e7be1/267709262/267709262.jpg\",\n" +
            "\"serviceContent\":\"一大波建筑狗因熬夜画图狗带\",\n" +
            "\"serviceTime\":1470369600000\n" +
            "}\n" +
            "] ";



    public static byte[] key = new byte[64];
    public static RealmConfiguration config;
}
