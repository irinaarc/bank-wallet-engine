CREATE TABLE transaction (
id UUID PRIMARY KEY,
wallet_id UUID NOT NULL,
operation_type VARCHAR(50) NOT NULL,
amount DECIMAL(19, 2) NOT NULL,
created_at TIMESTAMP NOT NULL
);