--liquibase formatted sql
--changeset gideon_mutai:00001-03


alter table test_1 add column status VARCHAR(20);

-- rollback alter table test_1 drop column status;