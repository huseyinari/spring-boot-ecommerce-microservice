INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Elektronik', 35, null, 100004, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Moda', 28, null, 100009, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Ev, Yaşam, Kırtasiye, Ofis', 22, null, 100007, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Oto, Bahçe, Yapı Market', 15, null, 100006, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Anne, Bebek, Oyuncak', 8, null, 100003, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Spor, Outdoor', 17, null, 100008, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Kozmetik, Kişisel Bakım', 3, null, 100003, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Süpermarket, Pet Shop', 6, null, 100003, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Kitap, Müzik, Film, Hobi', 11, null, 100005, 'system', '2025-04-12', NULL, NULL);


---------- sub categories ----------
INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Bilgisayar/Tablet', 14, 100000, 100003, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Yazıcılar & Projeksiyon', 3, 100000, 100003, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Telefon & Telefon Aksesuarları', 11, 100000, 100003, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.category
(id, category_name, total_product_count, parent_id, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('category_id_sequence'), 'Elektrikli Ev Aletleri', 13, 100000, 100003, 'system', '2025-04-12', NULL, NULL);


---------- carousel items ----------

INSERT INTO public.carousel_item
(id, title, subtitle, link, link_title, carousel_name, carousel_list_order, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('carousel_item_id_sequence'), 'Kaira klimalarda yaza özel', 'Sepette %10 net indirim', '/test', 'Fırsatları Kaçırma', 'homepage-carousel', 1, 100000, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.carousel_item
(id, title, subtitle, link, link_title, carousel_name, carousel_list_order, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('carousel_item_id_sequence'), 'Samsung Galaxy S25 Edge Kampanya', 'Watch7 ve Buds3 Kulaklık 7.999 TL', '/test', 'Satın Al', 'homepage-carousel', 2, 100001, 'system', '2025-04-12', NULL, NULL);

INSERT INTO public.carousel_item
(id, title, subtitle, link, link_title, carousel_name, carousel_list_order, image_storage_object_id, created_by, created_date, updated_by, updated_date)
VALUES(nextval('carousel_item_id_sequence'), 'Kitaplarda Sepette %50 İndirim', NULL, '/test', 'Keşfet', 'homepage-carousel', 3, 100002, 'system', '2025-04-12', NULL, NULL);

----------- category products filter options -----------
