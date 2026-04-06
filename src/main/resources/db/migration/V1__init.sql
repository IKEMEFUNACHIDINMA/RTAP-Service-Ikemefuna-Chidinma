CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE class_sessions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE attendance_records (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT REFERENCES class_sessions(id),
    user_id BIGINT REFERENCES users(id),
    joined_at TIMESTAMP NOT NULL,
    left_at TIMESTAMP
);

CREATE TABLE session_events (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT REFERENCES class_sessions(id),
    user_id BIGINT REFERENCES users(id),
    event_type VARCHAR(50) NOT NULL,
    payload TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);