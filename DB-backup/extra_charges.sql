--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-11 10:50:58

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3386 (class 0 OID 16440)
-- Dependencies: 220
-- Data for Name: extrachargesentry; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.extrachargesentry VALUES (54, 10465, 'Outstanding of 2016-2017', 704, 'Prior Outstanding');
INSERT INTO society.extrachargesentry VALUES (55, 3426, 'Outstanding of 2016-2017', 102, 'Prior Outstanding');
INSERT INTO society.extrachargesentry VALUES (56, 2590, 'Outstanding of 2016-2017', 404, 'Prior Outstanding');
INSERT INTO society.extrachargesentry VALUES (60, 2590, 'Outstanding of 2016-2017', 301, 'Prior Outstanding');
INSERT INTO society.extrachargesentry VALUES (62, 2590, 'Outstanding of 2016-2017', 503, 'Prior Outstanding');
INSERT INTO society.extrachargesentry VALUES (63, 2590, 'Outstanding of 2016-2017', 204, 'Prior Outstanding');


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 219
-- Name: extrachargesentry_id_seq; Type: SEQUENCE SET; Schema: society; Owner: admin
--

SELECT pg_catalog.setval('society.extrachargesentry_id_seq', 77, true);


-- Completed on 2024-08-11 10:50:59

--
-- PostgreSQL database dump complete
--

