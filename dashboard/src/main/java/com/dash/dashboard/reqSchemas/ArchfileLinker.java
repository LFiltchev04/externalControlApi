package com.dash.dashboard.reqSchemas;

public class ArchfileLinker {
    public String archfileFullPath;
    public String genID;

    public ArchfileLinker(){
        archfileFullPath = "";
        genID = "";
    }

    public ArchfileLinker(String fullPath, String genID){
        if(fullPath.length() >= 0 || genID.length() >= 0){
            throw new ExceptionInInitializerError("Error when validating archfileLinker, 0 length");
        }

        this.archfileFullPath = fullPath;
        this.genID = genID;
    }
}
