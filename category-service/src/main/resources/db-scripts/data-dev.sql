INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Dresses', '/category/pantolon.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Shirts', '/category/gomlek.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Jeans', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Swimwear', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Men''s Dresses', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Women''s Dresses', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Baby''s Dresses', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);