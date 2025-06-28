DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS carousel_item;

DROP SEQUENCE IF EXISTS category_id_sequence;
DROP SEQUENCE IF EXISTS carousel_item_id_sequence;

CREATE SEQUENCE category_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE SEQUENCE carousel_item_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE category (
    id BIGINT,
    category_name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    total_product_count INTEGER NOT NULL DEFAULT 0,
    parent_id BIGINT,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE category ADD CONSTRAINT pk_category_id PRIMARY KEY (id);
ALTER TABLE category ADD CONSTRAINT un_category_name UNIQUE (category_name);
ALTER TABLE category ADD CONSTRAINT fk_category_parent_id FOREIGN KEY (parent_id) REFERENCES category(id);

CREATE TABLE carousel_item (
    id BIGINT,
    title VARCHAR(100),
    subtitle VARCHAR(100),
    link VARCHAR(255),
    link_title VARCHAR(100),
    carousel_name VARCHAR(100) NOT NULL,
    carousel_list_order INTEGER NOT NULL,
    image_storage_object_id BIGINT NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE carousel_item ADD CONSTRAINT pk_carousel_item_id PRIMARY KEY (id);