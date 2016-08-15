package com.kai.wisdom_scut.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tusm on 16/8/3.
 */

public class User extends RealmObject {

    /**
     * result : success
     * name : 小明
     * number : 201436750419
     * phoneNumber : 13800000000
     * emailAddress : 666@kaigui.com
     * avatarUrl : http://i.imgur.com/UePbdph.jpg
     * collectionArray : [{"serviceId":0,"serviceName":"拾卡寻人","collectTime":10000000}]
     * cardNumber : ”123456“
     */
    @PrimaryKey
    private String number;

    private String result;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String avatarUrl;
    private String passWord;

private RealmList<Collection> collectionArray;


    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    private Collection collection;








    @Override
    public String toString() {
        return "User{" +
                "number='" + number + '\'' +
                ", result='" + result + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", passWord='" + passWord + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

    private String cardNumber;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
