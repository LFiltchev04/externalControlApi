package com.dash.dashboard.blobOwnership;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserRootTable")
public class UserRootTable {
    @Id
    public String UserID;
    public String VolumePath;

    public UserRootTable(){

    }

    public UserRootTable(String userID, String volumePath){
        if(userID.length() != 6){
            throw new ExceptionInInitializerError("Username is wrong format");
        }

        if(volumePath.isEmpty()){
            throw new ExceptionInInitializerError("Volume path is null");
        }
    }

    public String getUserId(){
        return this.UserID;
    }
    public void setUserId(String userId){
        this.UserID = userId;
    }

    public String getVolumePath(){
        return this.VolumePath;
    }

    public void setVolumePath(String volumePath){
        this.VolumePath = volumePath;
    }

}
