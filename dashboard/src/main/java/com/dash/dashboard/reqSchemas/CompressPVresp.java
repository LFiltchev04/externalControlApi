package com.dash.dashboard.reqSchemas;

public class CompressPVresp{
    public String err;
    public Long blobSize;
    public String blobHash;

    public CompressPVresp(){
        err = "";
    }

    public String getErrType(){
        return err;
    }
} 