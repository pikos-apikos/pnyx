CREATE TABLE event_stream (
    stream_id       UUID        NOT NULL,
    stream_version  BIGINT      NOT NULL,
    event_type      VARCHAR(128) NOT NULL,
    event_payload   JSONB       NOT NULL,
    content_hash    VARCHAR(71) NOT NULL,
    previous_hash   VARCHAR(71),
    actor_id        VARCHAR(255) NOT NULL DEFAULT 'system:pnyx-demo',
    occurred_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (stream_id, stream_version)
);