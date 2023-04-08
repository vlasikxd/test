CREATE SCHEMA IF NOT EXISTS ${spring.liquibase.liquibase-schema:profile};

CREATE SEQUENCE IF NOT EXISTS account_details_id_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS actual_registration_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS audit_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS passport_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS profile_id_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS registration_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE account_details_id
(
    id BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,
    CONSTRAINT pk_account_details_id PRIMARY KEY (id, account_id)
);

CREATE TABLE actual_registration
(
    id     BIGINT NOT NULL,
    country   VARCHAR(255),
    region VARCHAR(255),
    city VARCHAR(255),
    district VARCHAR(255),
    locality VARCHAR(255),
    street VARCHAR(255),
    house_number VARCHAR(255),
    house_block VARCHAR(255),
    flat_number VARCHAR(255),
    index BIGINT NOT NULL ,
    CONSTRAINT pk_actual_registration PRIMARY KEY (id)
);

CREATE TABLE audit
(
    id     BIGINT NOT NULL,
    entity_type   VARCHAR(255),
    operation_type VARCHAR(255),
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    new_entity_json VARCHAR(255),
    entity_json VARCHAR(255),
    CONSTRAINT pk_audit PRIMARY KEY (id)
);

CREATE TABLE profile
(
    id BIGINT NOT NULL,
    phone_number BIGINT NOT NULL ,
    email VARCHAR(255),
    name_on_card VARCHAR(255),
    inn BIGINT,
    snils BIGINT,
    passport_id BIGINT NOT NULL,
    actual_registration_id BIGINT NOT NULL,
    CONSTRAINT pk_profile PRIMARY KEY (id, passport_id, actual_registration_id)
);

CREATE TABLE registration
(
    id     BIGINT NOT NULL,
    country   VARCHAR(255),
    region VARCHAR(255),
    city VARCHAR(255),
    district VARCHAR(255),
    locality VARCHAR(255),
    street VARCHAR(255),
    house_number VARCHAR(255),
    house_block VARCHAR(255),
    flat_number VARCHAR(255),
    index BIGINT NOT NULL ,
    CONSTRAINT pk_registration PRIMARY KEY (id)
);

