package com.dash.dashboard.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dash.dashboard.Services.MetadataFetcher;

@RestController
@RequestMapping("/meta")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
public class GenerationsMetadata {

    private final MetadataFetcher metadataFetcher;

    public GenerationsMetadata(MetadataFetcher metadataFetcher) {
        this.metadataFetcher = metadataFetcher;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/manifests")
    public String visibleEndpoints(@AuthenticationPrincipal Jwt jwt){

        System.out.println("JWT: " + jwt);
        String userId = jwt.getSubject();
        String aiDi = jwt.getClaimAsString("profile");
        System.out.println("User ID: " + userId);
        System.out.println("AI Di: " + aiDi);
        
        return metadataFetcher.listManifestByUser(userId).toString();
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
