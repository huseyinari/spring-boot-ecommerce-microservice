DROP SEQUENCE IF EXISTS category_products_filter_options_id_sequence;
DROP SEQUENCE IF EXISTS category_id_sequence;
DROP SEQUENCE IF EXISTS carousel_item_id_sequence;

DROP TABLE IF EXISTS category_products_filter_options;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS carousel_item;

CREATE SEQUENCE category_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE category (
    id BIGINT,
    category_name VARCHAR(255) NOT NULL,
    -- image_url VARCHAR(255) NOT NULL,
    total_product_count INTEGER NOT NULL DEFAULT 0,
    parent_id BIGINT,
    image_storage_object_id BIGINT NOT NULL,
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE category ADD CONSTRAINT pk_category_id PRIMARY KEY (id);
ALTER TABLE category ADD CONSTRAINT un_category_name UNIQUE (category_name);
ALTER TABLE category ADD CONSTRAINT fk_category_parent_id FOREIGN KEY (parent_id) REFERENCES category(id);

CREATE SEQUENCE carousel_item_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

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

CREATE SEQUENCE category_products_filter_options_id_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 2147483647
START 100000
NO CYCLE;

CREATE TABLE category_products_filter_options (
    id BIGINT,
    category_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    query_name VARCHAR(255) NOT NULL UNIQUE,
    filter_type VARCHAR(50) NOT NULL DEFAULT 'ALL_OPTIONS',     --- ALL_OPTIONS / RANGE
    ui_component VARCHAR(50) NOT NULL,
    max_filter_option INTEGER,  --- En fazla kaç adet seçenek olabilir ?
    --
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(100),
    updated_date TIMESTAMP
);

ALTER TABLE category_products_filter_options ADD CONSTRAINT pk_category_products_filter_options_id PRIMARY KEY (id);
ALTER TABLE category_products_filter_options ADD CONSTRAINT fk_category_products_filter_options_category_id FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE category_products_filter_options ADD CONSTRAINT un_category_query_name UNIQUE (query_name);
CREATE UNIQUE INDEX un_ind_category_products_filter_options_category_id_name ON category_products_filter_options (category_id, query_name);