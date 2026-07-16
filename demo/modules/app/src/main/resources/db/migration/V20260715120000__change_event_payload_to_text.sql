-- event_payload must preserve exact byte sequences for SHA-256 hash chain verification.
-- JSONB normalizes input (sorts keys, adds canonical spacing), which produces a different
-- string than what the hash was computed from. TEXT preserves the exact input.
ALTER TABLE event_stream
    ALTER COLUMN event_payload SET DATA TYPE text
    USING event_payload::text;
