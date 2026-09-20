package com.dash.dashboard.blobOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ManifestOwnershipRepo extends JpaRepository<ManifestOwnership, Long> {
    public Optional<List<ManifestOwnership>> findByUserId(String userId);

}
