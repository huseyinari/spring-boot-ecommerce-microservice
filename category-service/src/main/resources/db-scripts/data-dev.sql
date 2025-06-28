INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Elektronik', '/category/pantolon.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Moda', '/category/gomlek.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Ev, Yaşam, Kırtasiye, Ofis', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Oto, Bahçe, Yapı Market', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Anne, Bebek, Oyuncak', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Spor, Outdoor', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Kozmetik, Kişisel Bakım', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Süpermarket, Pet Shop', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Kitap, Müzik, Film, Hobi', '/category/tshirt.jpg', 0, null, 'system', '2025-04-12', NULL, NULL);


---------- sub categories ----------
INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Bilgisayar/Tablet', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Yazıcılar & Projeksiyon', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Telefon & Telefon Aksesuarları', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, image_url, total_product_count, parent_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Elektrikli Ev Aletleri', '/category/tshirt.jpg', 0, 100000, 'system', '2025-04-12', NULL, NULL);