package com.dash.dashboard.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dash.dashboard.Services.EnvironmentCreateService;
import com.dash.dashboard.Services.ComposeEnvService;
import com.dash.dashboard.reqSchemas.CreateEnvironment;
import com.dash.dashboard.reqSchemas.EnvCompose;
import com.dash.dashboard.reqSchemas.CompressPV;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
public class GenerationsManager {

    private final EnvironmentCreateService svc;
    private final ComposeEnvService composeSvc;

    public GenerationsManager(EnvironmentCreateService svc, ComposeEnvService composeSvc) {
        this.svc = svc;
        this.composeSvc = composeSvc;
    }

    @PostMapping("/saveEnv")
    @PreAuthorize("isAuthenticated()")
    public String saveController(@Valid @RequestBody CreateEnvironment jsn, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        svc.cyclePV(userId, jsn.getImageName());
        return "Environment created for image " + jsn.getImageName();
    }

    @PostMapping("/linkEnv")
    @PreAuthorize("isAuthenticated()")
    public String linkerController(@Valid @RequestBody CompressPV pv, @AuthenticationPrincipal Jwt jwt){
        //String placeholderAuth = "f-off";
        String userId = jwt.getSubject();

        svc.linkHash(userId, pv.VolumePath);
        return "Completed the hashing and hardlinker stages";
    }


    @PostMapping("/composeEnv")
    @PreAuthorize("isAuthenticated()")
    public String composeController(@Valid @RequestBody EnvCompose composeReq, @AuthenticationPrincipal Jwt jwt){
        String userId = jwt.getSubject();
        try{
            composeSvc.composeEnv(composeReq.blobSet, userId, composeReq.manifestName, composeReq.generationTag);
        } catch (Exception e) {
            return "Failed to compose environment: " + e.getMessage();
        }
        
        return "Compose request succeeded";

    }

    
}
