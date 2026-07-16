package dev.pnyx.endpoint.rest;

import dev.pnyx.core.api.ExportApi;
import dev.pnyx.core.common.ContentHash;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoint for public manifest and object export.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/export")
public class ExportController {

    private final ExportApi exportApi;

    /**
     * Returns the latest public manifest.
     *
     * @return the current manifest
     */
    @GetMapping("/manifest")
    public ResponseEntity<ExportApi.ManifestView> manifest() {
        return ResponseEntity.ok(exportApi.exportManifest());
    }

    /**
     * Returns a public object by content hash.
     *
     * @param hash the content hash (sha256:...)
     * @return the canonical JSON string
     */
    @GetMapping("/objects/{hash}")
    public ResponseEntity<String> object(@PathVariable String hash) {
        return ResponseEntity.ok(exportApi.exportObject(new ContentHash(hash)));
    }
}
