package com.dash.dashboard.blobOwnership;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BlobOwnership")
public class BlobOwnership {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permListID;
    
    private String blobHash;
    private String userId;
    private Long size;
    private Long rawSize;
    private String rawHash;
    //can only be "layer", "manifest" or "config"
    private String type;

    protected BlobOwnership() {
        // JPA requires a no-arg constructor
    }

    public BlobOwnership(String blobHashIn, String userIdIn, Long sizeIn) {
        if (blobHashIn == null || blobHashIn.length() <= 7) {
            throw new IllegalArgumentException("Exception thrown in validation, blob hash was too small to be valid, faulty input: " + blobHashIn);
        }
        if (!blobHashIn.startsWith("sha256:")) {
            throw new IllegalArgumentException("Exception thrown in validation, blob hash began without sha256 prefix, faulty input: " + blobHashIn);
        }
        if (userIdIn == null || userIdIn.isEmpty()) {
            throw new IllegalArgumentException("Exception thrown in validation, userID was too small to be valid, faulty input: " + userIdIn);
        }

        this.blobHash = blobHashIn.substring(7);
        this.userId = userIdIn;
        this.size = sizeIn;
    }

    public String getBlobHash() {
        return this.blobHash;
    }

    public Long getSize() {
        return this.size;
    }

    public String getUserId() {
        return this.userId;
    }

    public Long getRawSize() {
        return this.rawSize;
    }

    public String getRawHash() {
        return this.rawHash;
    }
}
