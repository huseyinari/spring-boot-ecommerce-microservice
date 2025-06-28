DROP TABLE IF EXISTS storage_objects;
DROP SEQUENCE IF EXISTS storage_objects_id_sequence;

CREATE SEQUENCE storage_objects_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE storage_objects (
    id BIGINT,
    file_name VARCHAR(255) NOT NULL,
    storage_name VARCHAR(255) NOT NULL,
    file_size INTEGER NOT NULL,
    file_extension VARCHAR(255) NOT NULL,
    is_private_access BOOLEAN NOT NULL DEFAULT FALSE,
    owner_id VARCHAR(255),
    storage_type VARCHAR(255) NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE storage_objects ADD CONSTRAINT pk_storage_objects_id PRIMARY KEY (id);
ALTER TABLE storage_objects ADD CONSTRAINT un_storage_objects_file_name UNIQUE (file_name);