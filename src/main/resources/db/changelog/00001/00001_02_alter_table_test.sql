--liquibase formatted sql
--changeset gideon_mutai:00001-02


alter table test_1 add column message_id VARCHAR(50);
alter table test_1 add column sender VARCHAR(100);

-- rollback alter table test_1 drop column message_id;
-- rollback alter table test_1 drop column sender;