CREATE TABLE IF NOT EXISTS event_publication (
    id UUID PRIMARY KEY,
    eventtype VARCHAR(255) NOT NULL,
    listenerid VARCHAR(255) NOT NULL,
    publicationdate TIMESTAMP NOT NULL,
    completiondate TIMESTAMP,
    serializedevent TEXT NOT NULL
);
