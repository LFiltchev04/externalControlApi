package com.dash.dashboard.Services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.dash.dashboard.blobOwnership.BlobOwnership;
import com.dash.dashboard.blobOwnership.BlobOwnershipRepository;
import com.dash.dashboard.blobOwnership.ManifestOwnership;
import com.dash.dashboard.blobOwnership.ManifestOwnershipRepo;
import com.dash.dashboard.Services.ManifestFormatter;

@Service
public class ComposeEnvService {
    private final RestClient restClient;
    private final BlobOwnershipRepository ownershipTable;
    private final ManifestOwnershipRepo manifestOwnershipRepo;

    public ComposeEnvService(RestClient restClient, BlobOwnershipRepository blobOwnershipRepository,
                            ManifestOwnershipRepo manifestOwnershipRepo
    ) {
        this.restClient = restClient;
        this.ownershipTable = blobOwnershipRepository;
        this.manifestOwnershipRepo = manifestOwnershipRepo;
    }

    public void composeEnv(String[] hashSet, String userID, String genName, int versionTag) {
        List<BlobOwnership> recordsByUser = ownershipTable.findByUserId(userID);
        Set<String> userBlobHashes = recordsByUser.stream()
                .map(BlobOwnership::getBlobHash)
                .collect(Collectors.toSet());

        //validates whether there are permissions to compose the certain env
        for (String hash : hashSet) {
            boolean existsForUser = userBlobHashes.contains(hash);
            if (!existsForUser) {
                throw new ExceptionInInitializerError("No permissions to compose certain blob or nonexistent");
            }
        }

        Set<Long> blobSizes = recordsByUser.stream()
                .filter(record -> Set.of(hashSet).contains(record.getBlobHash()))
                .map(BlobOwnership::getSize)
                .collect(Collectors.toSet());

        int[] blobSizesArray = blobSizes.stream().mapToInt(Long::intValue).toArray();
        String[] userBlobHashesArray = userBlobHashes.toArray(new String[0]);
        
        //ideally just keep it a class and implement more stuff if need be, way easier than the alternatives
        ManifestFormatter manifestFormatter = new ManifestFormatter(userBlobHashesArray, blobSizesArray, 
                                                                        userID, genName, versionTag);

        String mFest = manifestFormatter.getManifestString();


        
        String tempRepoEndpoint = "http://localhost:5000/v2/temp";
        try {
            restClient.post().uri(tempRepoEndpoint)
                    .header("Content-Type", "application/json")
                    .body(mFest)
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientResponseException ex) {
            int errorCode = ex.getStatusCode().value();
            throw new RuntimeException(String.valueOf(errorCode));
        }


        manifestOwnershipRepo.save(new ManifestOwnership(userID, genName, versionTag));

    
    }
}
