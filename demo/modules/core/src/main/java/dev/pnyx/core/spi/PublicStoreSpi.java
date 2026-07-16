package dev.pnyx.core.spi;

import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;

import java.util.List;

/**
 * Driven port for content-addressed public artifact storage.
 * <p>
 * Per {@code ../docs/90_Information/PUBLIC_STORAGE_MODEL.md}, public artifacts are content-addressed,
 * canonical JSON files stored on the filesystem. Artifacts are addressed by SHA-256 hash of their
 * canonical JSON representation. This store is the authoritative source of public truth — the
 * database is a derived read model.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
public interface PublicStoreSpi {

    /**
     * Writes canonical JSON as a public object and returns its content hash.
     *
     * @param objectType     type of public object
     * @param schemaVersion  schema version for validation
     * @param createdBy      actor identifier
     * @param canonicalJson  key-sorted canonical JSON string
     * @return content hash of the stored object
     */
    ContentHash write(PublicObjectType objectType, PublicSchemaVersion schemaVersion,
                      String createdBy, String canonicalJson);

    /**
     * Reads a public object by content hash.
     *
     * @param hash content hash of the object to read
     * @return canonical JSON string
     */
    String read(ContentHash hash);

    /**
     * Updates the latest public manifest to reference a stored object.
     *
     * @param networkName network identifier
     * @param objectHash  content hash of the object
     * @param objectType  type of public object
     * @param location    storage path relative to the public root
     */
    void updateManifest(String networkName, ContentHash objectHash,
                        PublicObjectType objectType, String location);

    /**
     * Lists the index entries for all objects of the given type, ordered by
     * published-at timestamp (most recent first). The index is read from the
     * {@code indexes/objects.jsonl} file.
     *
     * @param objectType type of public object to list
     * @return ordered list of index entries
     */
    List<IndexEntry> listByType(PublicObjectType objectType);

    /**
     * Lightweight reference to a stored public object. Use {@link #read(ContentHash)}
     * to fetch the full canonical JSON if needed.
     */
    record IndexEntry(ContentHash hash, PublicObjectType objectType, String location,
                      String publishedAt) { }
}
