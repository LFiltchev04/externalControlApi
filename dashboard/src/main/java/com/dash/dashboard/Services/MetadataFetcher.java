package com.dash.dashboard.Services;

import org.springframework.stereotype.Service;
import java.util.List;

import com.dash.dashboard.blobOwnership.ManifestOwnership;
import com.dash.dashboard.blobOwnership.ManifestOwnershipRepo;

import com.dash.dashboard.blobOwnership.BlobOwnershipRepository;
import com.dash.dashboard.blobOwnership.UserRootTableRepository;


@Service
public class MetadataFetcher {
    private final BlobOwnershipRepository blobOwnerTable;
    private final UserRootTableRepository userRootTable;
    private final ManifestOwnershipRepo manifestOwnershipRepo; 

    public MetadataFetcher(BlobOwnershipRepository ownTable, UserRootTableRepository rootUserTable, ManifestOwnershipRepo manifestOwnershipRepo){
        this.blobOwnerTable = ownTable;
        this.userRootTable = rootUserTable;
        this.manifestOwnershipRepo = manifestOwnershipRepo;
    }

    public List<ManifestOwnership> listManifestByUser(String userId) {
        // Implementation goes here
        return manifestOwnershipRepo.findByUserId(userId).orElse(List.of());

    }




}
