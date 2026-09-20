package com.dash.dashboard.blobOwnership;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlobOwnershipRepository extends JpaRepository<BlobOwnership, String> {
    List<BlobOwnership> findByUserId(String userId);
}
