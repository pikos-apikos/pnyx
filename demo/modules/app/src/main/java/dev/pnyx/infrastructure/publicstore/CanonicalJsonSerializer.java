package dev.pnyx.infrastructure.publicstore;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Produces canonical (deterministic, key-sorted) JSON for public artifact serialization.
 * <p>
 * Per {@code ../docs/90_Information/PUBLIC_STORAGE_MODEL.md §3}, canonical JSON ensures that
 * the same data always produces the same byte sequence, enabling content-addressed storage
 * and reproducible hash computation. Keys are sorted alphabetically, whitespace is stripped,
 * and field ordering is deterministic.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
@Component
public class CanonicalJsonSerializer {

  private final ObjectMapper mapper;

  public CanonicalJsonSerializer() {
    this.mapper = new ObjectMapper();
    this.mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    this.mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
  }

  /**
   * Rewrites JSON with deterministic ordering and compact formatting.
   *
   * @param json input JSON string
   * @return canonicalized JSON string
   * @throws IllegalArgumentException if the input is not valid JSON
   */
  public String canonicalize(String json) {
    try {
      Object obj = mapper.readValue(json, Object.class);
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to canonicalize JSON", e);
    }
  }

  /**
   * Serializes a value as deterministic, compact canonical JSON.
   *
   * @param value object to serialize
   * @return canonicalized JSON string
   * @throws IllegalArgumentException if serialization fails
   */
  public String canonicalize(Object value) {
    try {
      return mapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to canonicalize object", e);
    }
  }
}
