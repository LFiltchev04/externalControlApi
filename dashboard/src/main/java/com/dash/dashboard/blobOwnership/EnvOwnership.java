package com.dash.dashboard.blobOwnership;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EnvOwnership")
public class EnvOwnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String envName;
    public String userId;
    public String envDesc;
    public String baseHash;

    public EnvOwnership(String envName, String userId, String envDesc, String baseHash){
        this.envName = envName;
        this.userId = userId;
        this.envDesc = envDesc;
        this.baseHash = baseHash;
    }
}
