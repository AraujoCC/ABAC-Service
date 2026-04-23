CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE subjects
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         VARCHAR(255) NOT NULL UNIQUE,
    department      VARCHAR(100) NOT NULL,
    role            VARCHAR(100) NOT NULL,
    clearance_level INT,
    contract_type   VARCHAR(50),
    location        VARCHAR(50),
    active          BOOLEAN          DEFAULT TRUE,
    created_at      TIMESTAMPTZ  NOT NULL,
    updated_at      TIMESTAMPTZ  NOT NULL,
    created_by      VARCHAR(255)
);

CREATE TABLE resources
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    resource_id      VARCHAR(255) NOT NULL UNIQUE,
    resource_type    VARCHAR(50)  NOT NULL,
    owner_id         VARCHAR(255),
    owner_department VARCHAR(100),
    classification   VARCHAR(50)  NOT NULL,
    archived         BOOLEAN          DEFAULT FALSE,
    created_at       TIMESTAMPTZ  NOT NULL,
    updated_at       TIMESTAMPTZ  NOT NULL,
    created_by       VARCHAR(255)
);

CREATE TABLE policies
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    effect      VARCHAR(10)  NOT NULL CHECK (effect IN ('PERMIT', 'DENY')),
    priority    INT,
    active      BOOLEAN          DEFAULT TRUE,
    created_at  TIMESTAMPTZ  NOT NULL,
    updated_at  TIMESTAMPTZ  NOT NULL,
    created_by  VARCHAR(255)
);

CREATE TABLE policy_target_actions
(
    policy_id      UUID REFERENCES policies (id) ON DELETE CASCADE,
    target_actions VARCHAR(50) NOT NULL
);

CREATE TABLE policy_target_resource_types
(
    policy_id             UUID REFERENCES policies (id) ON DELETE CASCADE,
    target_resource_types VARCHAR(50) NOT NULL
);

CREATE TABLE policy_decisions
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    subject_id     VARCHAR(255) NOT NULL,
    resource_id    VARCHAR(255) NOT NULL,
    action         VARCHAR(50)  NOT NULL,
    decision       VARCHAR(10)  NOT NULL,
    policy_applied VARCHAR(255),
    reason         TEXT,
    evaluated_at   TIMESTAMPTZ  NOT NULL,
    ip_address     VARCHAR(45),
    request_id     VARCHAR(255)
);

CREATE INDEX idx_decisions_subject ON policy_decisions (subject_id);
CREATE INDEX idx_decisions_evaluated_at ON policy_decisions (evaluated_at);