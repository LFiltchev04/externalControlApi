package com.dash.dashboard.reqSchemas;

public class CompressPV {

    public CompressPV(){

    };
    public CompressPV(String snapsht, String volPth){
        if(snapsht.length() <= 0 || volPth.length() <= 0){
            throw new ExceptionInInitializerError("Failed to create a valid request body for compressPV api call");
        }

        this.SnapshotID = snapsht;
        this.VolumePath = volPth;
    }

    public Long blobSize;
    public String SnapshotID;
    public String VolumePath;
}


