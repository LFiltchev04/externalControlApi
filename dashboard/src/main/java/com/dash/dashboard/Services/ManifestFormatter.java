package com.dash.dashboard.Services;

import com.dash.dashboard.blobOwnership.ManifestOwnership;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import com.google.cloud.tools.jib.*;
import com.google.cloud.tools.jib.api.Jib;

public class ManifestFormatter {
  
    private String[] digests;
    private String[] diffIds;
    private int[] sizes;
    private String userId;
    private String manifestType;
    private int version;

    private class configuration {
        public String contentType;
        public int size;
        public String digest;
    }

    private class layer {
        public String mediaType;
        public int size;
        public String digest;
    }


    public ManifestFormatter(String[] digests, int[] sizes, String userId, String manifestType, int version){
        this.digests = digests;
        this.sizes = sizes;
        this.userId = userId;
        this.manifestType = manifestType;
        this.version = version;
    }

    public ManifestFormatter(String[] digests, int[] sizes, String[] diffIds, String userId, String manifestType, int version) {
        this.diffIds = diffIds;
        this.digests = digests;
        this.sizes = sizes;
        this.userId = userId;
        this.manifestType = manifestType;
        this.version = version;
    }


    public String getManifestString(){
        JsonObject manifest = new JsonObject();
        manifest.addProperty("schemaVersion", 2);
        manifest.addProperty("mediaType", "application/vnd.oci.image.manifest.v1+json");
        
        configuration config = new configuration();
        config.contentType = "application/vnd.oci.image.config.v1+json";
        config.size = 8838;
        config.digest = "sha256:47dccc76b32761bc57462b8753144cdbb73a16b123b1d13d3eedb92bb7952b11";
        manifest.addProperty("config", new Gson().toJson(config));
        
        List<layer> layers = new ArrayList<>();
        for (int i = 0; i < digests.length; i++) {
            layer l = new layer();

            l.mediaType = "application/vnd.oci.image.layer.v1.tar";
            l.size = sizes[i];
            l.digest = digests[i];

            layers.add(l);
    }
    
        manifest.add("layers", new Gson().toJsonTree(layers));
        return manifest.toString();
    }

}

