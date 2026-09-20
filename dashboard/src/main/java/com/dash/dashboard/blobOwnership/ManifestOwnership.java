package com.dash.dashboard.blobOwnership;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ManifestOwnership")
public class ManifestOwnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String manifestHash;
    public String userId;
    public String manifestName;
    public int versionTag;

    public ManifestOwnership() {

    }

    public ManifestOwnership(String userId, String manifestName, int versionTag) {
        this.userId = userId;
        this.manifestName = manifestName;
        this.versionTag = versionTag;
    }


}
