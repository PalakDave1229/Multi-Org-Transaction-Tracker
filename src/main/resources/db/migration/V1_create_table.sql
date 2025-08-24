CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE organizations (
    org_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    owner_id UUID NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
    tx_id UUID PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    item_description TEXT,
    date DATE,
    party VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    org_id UUID NOT NULL,
    created_by UUID NOT NULL,
    FOREIGN KEY (org_id) REFERENCES organizations(org_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);