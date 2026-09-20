package com.dash.dashboard.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dash.dashboard.blobOwnership.BlobOwnership;
import com.dash.dashboard.blobOwnership.BlobOwnershipRepository;
import com.dash.dashboard.blobOwnership.UserRootTable;
import com.dash.dashboard.blobOwnership.UserRootTableRepository;
import com.dash.dashboard.reqSchemas.ArchfileLinker;
import com.dash.dashboard.reqSchemas.ArchfileLinkerResp;
import com.dash.dashboard.reqSchemas.CompressPV;
import com.dash.dashboard.reqSchemas.CompressPVresp;

import com.google.gson.Gson;
import org.springframework.web.client.RestClient;


@Service
public class EnvironmentCreateService {


    private RestClient restClient;
    private BlobOwnershipRepository blobOwnershipRepository;
    private UserRootTableRepository userRootTableRepository;
    private Gson gson;

    public EnvironmentCreateService(BlobOwnershipRepository blobOwnershipRepository, UserRootTableRepository userRootTableRepository, RestClient restClient) {
        this.blobOwnershipRepository = blobOwnershipRepository;
        this.userRootTableRepository = userRootTableRepository;
        this.restClient = restClient;
        this.gson = new Gson();
    }

    public boolean validateBlobs(String[] blobs, String userId) {
        if (blobs == null || blobs.length == 0) {
            return false;
        }
        return true;
   
    }



    public void cyclePV(String userID, String genName){
    
        //this thing basically talks to the utility microservice to carry out the ceph stuff. 
        //not even sure i need this

        Optional<UserRootTable> rootEntry = userRootTableRepository.findById(userID);

        UserRootTable userRootEntry;
        try{
            userRootEntry = rootEntry.get();
        }catch(Exception excp){
            System.err.println("DB error: " + excp.toString());
            return;
        }
      
        
        CompressPV reqBdy = new CompressPV(userRootEntry.VolumePath + "/arch/" + genName, userID);

        
        CompressPVresp response = restClient.post()
            .uri("127.0.0.1:4000")
            .body(gson.toJson(reqBdy))
            .retrieve()
            .body(CompressPVresp.class);

        if (response == null) {
            return;
        }

        BlobOwnership blob = new BlobOwnership(response.blobHash, userID, response.blobSize);
        blobOwnershipRepository.save(blob);

        linkHash(userID, userRootEntry.VolumePath + "/arch/" + genName);

    }

    public void linkHash(String userID, String archfilePath){


        ArchfileLinker bodyReq = new ArchfileLinker(archfilePath,userID);
        ArchfileLinkerResp rsp = restClient.post()
        .uri("127.0.0.1:4000")
        .body(gson.toJson(bodyReq))
        .retrieve()
        .body(ArchfileLinkerResp.class);

        //gotta figure out what to do with the metadata, frontends should handle the forkover to cut down on calls, plus a separate metadata service for pulling it in 
    }

}
