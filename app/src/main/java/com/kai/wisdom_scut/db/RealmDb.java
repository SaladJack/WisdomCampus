package com.kai.wisdom_scut.db;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetManager;


import com.kai.wisdom_scut.model.Collection;
import com.kai.wisdom_scut.model.MachineMsg;
import com.kai.wisdom_scut.model.ServiceMsg;
import com.kai.wisdom_scut.model.User;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.kai.wisdom_scut.db.Constants.config;


/**
 * Created by tusm on 16/8/3.
 */

public class RealmDb {

    public static Realm realm = null;

    //========================================================User=================================================================================
    private static User sUser = null;

    /**
     *
     * @return 返回单例sUser
     */
    public static User getUserInstance(){
        return sUser;
    }

    /**
     * 用于登录成功保存数据
     * @param response
     * @param passWord
     */
    public static void saveUser(final String response,final String passWord){

        if (sUser == null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createObjectFromJson(User.class, response);

                    sUser = realm.where(User.class)
                            .findFirst();
                    sUser.setPassWord(passWord);




//                    User user = realm.createObject(User.class);
//                    User user1 = realm.createObject(User.class);
//                    RealmList<User> users = new RealmList<User>();
//                    users.add(user);
//                    users.add(user1);
//                    sUser.setCollectionArray(users);


                    sUser.setCollection(realm.createObjectFromJson(Collection.class, Constants.sCollection));

                }
            });
        }
    }


    public static boolean haveUser(){
        realm.beginTransaction();;
        boolean haveUser = realm.where(User.class).findFirst() != null ? true:false;
        realm.commitTransaction();
        return haveUser;
    }



    /**
     * 删除User所有数据
     */
    public static void deleteUser(){
        realm.beginTransaction();
        realm.delete(User.class);
        realm.commitTransaction();
        sUser = null;
    }

    //========================================================Collection=================================================================================

    /**
     *
     * @param jsonArray
     * @return 按收藏时间降序返回List<Collection>
     */
    public static List<Collection> getCollections(final String jsonArray){

        if (realm.where(Collection.class).findAll().size() == 0) {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    realm.createAllFromJson(Collection.class,jsonArray);
                }
            });
        }
        return realm.where(Collection.class).findAll().sort("collectTime",Sort.DESCENDING);
    }

    /**
     * 增加收藏
     * @param collection
     */
    public static void addCollection(final Collection collection){
        realm.beginTransaction();
        realm.copyToRealm(collection);
        realm.commitTransaction();
    }

    /**
     * 按收藏时间筛选删除相应的collection
     * @param collectTime
     */
    public static void deleteCollection(long collectTime){
        realm.beginTransaction();
        realm.where(Collection.class).equalTo("collectTime",collectTime).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }
    //========================================================serviceMsgData=================================================================================

    /**
     *
     * @param jsonArray
     * 将jsonArray的数据解析到ServiceMsg表上
     */
    public static void saveMsgs(String jsonArray){
        realm.beginTransaction();
        realm.createAllFromJson(ServiceMsg.class,jsonArray);
        realm.commitTransaction();
    }

    /**
     *
     * @param msg
     * 添加msg到表中
     */
    public static void addMsg(ServiceMsg msg){
        realm.beginTransaction();
        realm.copyToRealm(msg);
        realm.commitTransaction();
    }

    /**
     *
     * @return 返回各服务的第一条msg
     */
    public static List<ServiceMsg> getMsgsByName() {

        List<ServiceMsg> msgList = new ArrayList<>();
        RealmResults<ServiceMsg> tempList;
        realm.beginTransaction();
        for (String serviceName : Constants.Service.serviceNames) {
            tempList = realm.where(ServiceMsg.class).equalTo("serviceName", serviceName).findAllSorted("serviceTime", Sort.DESCENDING);
            if (tempList.size()>0){
                msgList.add(tempList.first());
            }

        }
        realm.commitTransaction();
        Collections.sort(msgList);
        return msgList;

    }

    /**
     *
     * @return 按消息时间升序返回List<Collection>
     */
    public static RealmResults<ServiceMsg> getMsgs(String serviceName) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<ServiceMsg> msgs = realm.where(ServiceMsg.class).equalTo("serviceName",serviceName).findAll().sort("serviceTime", Sort.ASCENDING);
            realm.commitTransaction();
            realm.close();
            return msgs;
        }


    //========================================================MachineMsg=================================================================================
    public static void saveMachineMsg(Context context,String filename)
    {
        Realm realm = Realm.getDefaultInstance();
        String jsonArray = getJson(context,filename);
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createAllFromJson(MachineMsg.class,jsonArray);
            }
        });
        realm.close();
    }

    public static String findResponse(String ask){
        String response = "你在说什么?";
        Realm realm = Realm.getDefaultInstance();
        MachineMsg msg;
        realm.beginTransaction();
        msg = realm.where(MachineMsg.class).equalTo("ask",ask).findFirst();
        if (msg != null){
            response = realm.where(MachineMsg.class).equalTo("ask",ask).findFirst().getResponse();
        }
        realm.commitTransaction();
        realm.close();
        return response;
    }
    //========================================================All=================================================================================
    /**
     * 清除所有数据
     */
    public static void deleteAllData(){
        // Start with a clean slate every time
        Realm.deleteRealm(config);
        sUser = null;
    }

    public static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }
}
