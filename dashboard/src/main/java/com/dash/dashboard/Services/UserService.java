package com.dash.dashboard.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import com.dash.dashboard.blobOwnership.UserRootTable;
import com.dash.dashboard.blobOwnership.UserRootTableRepository;

import com.dash.dashboard.Services.ManifestFormatter;

@Service
public class UserService {

    private final RestClient restClient;

    private String[] baseManifests(String manifestType){
        switch(manifestType){
            case "ubuntu":
                //its actually kafka
                String[] retArr = {
                    "sha256:42516208460b63f47d0044aef98b70571ce7765cc9108b0a3d14ae7fa16d7566",
                    "sha256:2d1a277377d10ae29a58277bdefa13f3a1cfffb295a5b7bce5ad0b0d82661629",
                    "sha256:4f4fb700ef54461cfa02571ae0db9a0dc1e0cdb5577484a6d75e68dc38e8acc1",
                    "sha256:da9d0fb3345c8fa944451bc81ffc8d4852a9d9ce9b9ab5e6e08d634414c4bcfc",
                    "sha256:9ee28cb5e924c3c9b6ef4de6ced1d8c0de5b6fd24f9bac201bcd32b9bbc32f38",
                    "sha256:f9f362c352e24fffe4dd54534c07f22a7211da3112615b479474f1de063adb49",
                    "sha256:625afcf9df2fd8c5633bb80b24b8417a6950f9dfc4d3ac2e7616d66f9fb57c8c",
                    "sha256:cb74ead52f1038ce8e2259f455558a20ebae5f06cefdcb83c399f159be4364ac",
                    "sha256:c62d3fe6c5864a4a9dcbfede720c0fb8b3f07bcd0aa46248a161287a7ec9d4e6",
                };
                return retArr;
            default:
                return new String[]{};
        }
    }
    
    private final UserRootTableRepository userRootTableRepository;

    public UserService(UserRootTableRepository userRootTableRepository, RestClient restClient) {
        this.userRootTableRepository = userRootTableRepository;
        this.restClient = restClient;
    }

    public void createUser(String userId){
        
        UserRootTable newUser = new UserRootTable();
        newUser.setUserId(userId);
        userRootTableRepository.save(newUser);


        //Just writes a simple base manifest
        String[] manifestBase = baseManifests("ubuntu");
        int[] manifestSize = {
            3864189,
            16857117,
            53301871,
            128,
            2281,
            32,
            143161855,
            3001,
            14535493,
            7138040,
            2277,
            1573
        };

        ManifestFormatter manifestFormatter = new ManifestFormatter(manifestBase,manifestSize, userId,"ubuntu",0);
        String manifest = manifestFormatter.getManifestString();
        System.out.println("Manifest: " + manifest);
    
        
        try{
            restClient.put()
            .uri("http://127.0.0.1:5000/v2/" + userId + "/manifests/ubuntu")
            .header("Content-Type", "application/vnd.docker.distribution.manifest.v2+json")
            .body(manifest)
            .retrieve()
            .body(String.class)
            .wait();

        }catch(Exception excp){
            System.err.println("Error creating manifest: " + excp.toString());
        }




        
    }


}









