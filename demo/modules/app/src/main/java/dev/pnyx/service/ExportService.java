package dev.pnyx.service;

import dev.pnyx.core.api.ExportApi;
import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.spi.PublicStoreSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Application service for reading public manifests and stored objects.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
@Service
@RequiredArgsConstructor
public class ExportService implements ExportApi {

    private final PublicStoreSpi publicStore;

    @Override
    public ManifestView exportManifest() {
        return new ManifestView("test-network", 1, List.of(), Instant.now().toString());
    }

    @Override
    public String exportObject(ContentHash hash) {
        return publicStore.read(hash);
    }
}
