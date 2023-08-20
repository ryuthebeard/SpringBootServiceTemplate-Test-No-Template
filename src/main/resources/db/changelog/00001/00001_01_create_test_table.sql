--liquibase formatted sql
--changeset gideon_mutai:00001-01

CREATE SEQUENCE test_id_1_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE test_1 (
    id INTEGER NOT NULL DEFAULT NEXTVAL('test_id_1_seq') PRIMARY KEY,
    phone_number CHARACTER VARYING(50),
    message CHARACTER VARYING(500),
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
);

-- rollback DROP TABLE test_1;
-- rollback DROP SEQUENCE test_id_1_seq;