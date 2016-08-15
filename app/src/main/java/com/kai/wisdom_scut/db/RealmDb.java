package com.kai.wisdom_scut.db;

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
import java.util.List;

import io.realm.Realm;
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
     * @return 按消息时间降序返回List<Collection>
     */
    public static List<ServiceMsg> getMsgs(String jsonArray){
        realm.beginTransaction();
        realm.createAllFromJson(ServiceMsg.class,jsonArray);
        realm.commitTransaction();
        return realm.where(ServiceMsg.class).findAll().sort("serviceTime", Sort.DESCENDING);
    }
    //========================================================MachineMsg=================================================================================
    public static void saveMachineMsg(final String jsonArray){
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createAllFromJson(MachineMsg.class,jsonArray);
            }
        });
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
