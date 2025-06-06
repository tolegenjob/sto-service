CREATE TABLE status_histories (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    from_status VARCHAR(255) NOT NULL,
    to_status VARCHAR(255) NOT NULL,
    reason TEXT,
    request_id BIGINT REFERENCES requests(id) ON DELETE CASCADE,
    changed_by_id BIGINT NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE
);