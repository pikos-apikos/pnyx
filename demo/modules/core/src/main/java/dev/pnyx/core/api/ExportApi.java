package dev.pnyx.core.api;

import dev.pnyx.core.common.ContentHash;
import java.util.List;
import java.util.Map;

/**
 * Driving port for exporting public content-addressed artifacts.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
public interface ExportApi {

    /**
     * Latest public manifest with object index.
     *
     * @param networkName network identifier
     * @param epoch       manifest epoch number
     * @param objects     list of object references (each a map of metadata)
     * @param createdAt   ISO-8601 timestamp of manifest creation
     */
    record ManifestView(String networkName, int epoch,
                        List<Map<String, String>> objects, String createdAt) { }

    /**
     * Returns the latest public manifest.
     *
     * @return the current manifest view
     */
    ManifestView exportManifest();

    /**
     * Returns the canonical JSON object identified by its content hash.
     *
     * @param hash content hash of the object
     * @return canonical JSON string
     */
    String exportObject(ContentHash hash);
}
