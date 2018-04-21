package org.amd.aqua.model;

/**
 * Created by Akira on 2018/04/21.
 */

public class UserManager {
    private static UserManager instance = null;

    public static UserManager getInstance(){
        if( instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager(){

    }

    public String getUserName( int userId){
        if( userId == 0){
            return "オクシン";
        }
        if( userId == 1){
            return "デグチェリ";
        }
        if( userId == 2){
            return "アキラ";
        }
        return "";
    }
}
