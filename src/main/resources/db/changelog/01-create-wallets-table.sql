CREATE TABLE wallet (
id UUID PRIMARY KEY,
account_number VARCHAR(255) NOT NULL,
balance DECIMAL(19, 4) NOT NULL,
created_at TIMESTAMP NOT NULL
);