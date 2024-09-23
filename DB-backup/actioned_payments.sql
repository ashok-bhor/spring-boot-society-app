--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-11 11:07:02

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
-- TOC entry 3379 (class 0 OID 24902)
-- Dependencies: 235
-- Data for Name: actionedpaymentsdetails; Type: TABLE DATA; Schema: society; Owner: admin
--

INSERT INTO society.actionedpaymentsdetails VALUES (21, 'Approved', 'admin', '04-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2019-06-10', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (35, 'Approved', 'admin', '04-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 3426, '2017-05-06', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (37, 'Approved', 'admin', '04-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2021-11-07', 101, 'IMPS', '', 'IMPS');
INSERT INTO society.actionedpaymentsdetails VALUES (36, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 11000, '2018-01-03', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (22, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2020-08-01', 503, 'IMPS', '', 'IMPS');
INSERT INTO society.actionedpaymentsdetails VALUES (23, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2020-08-27', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (24, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2022-09-17', 503, 'IMPS', '', 'IMPS');
INSERT INTO society.actionedpaymentsdetails VALUES (38, 'Rejected', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 7800, '2018-01-03', 503, 'Cheque', 'Duplicate entry', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (25, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 2590, '2017-11-20', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (26, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 7800, '2018-01-03', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (27, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 9420, '2018-06-19', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (28, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2021-05-07', 503, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (29, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 2590, '2017-12-01', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (30, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 9420, '2018-06-19', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (31, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 7800, '2018-01-09', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (32, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2019-06-27', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (33, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2020-06-11', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (34, 'Approved', 'admin', '06-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 17220, '2022-07-06', 204, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (39, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 3426, '2017-05-06', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (40, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 11000, '2018-01-03', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (41, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 11440, '2018-06-19', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (42, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 22440, '2019-11-15', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (43, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 22440, '2021-05-01', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (44, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 22440, '2022-05-24', 102, 'Cheque', '', 'Cheque');
INSERT INTO society.actionedpaymentsdetails VALUES (45, 'Approved', 'admin', '11-08-2024', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36', 22440, '2022-07-13', 102, 'Cheque', '', 'Cheque');


-- Completed on 2024-08-11 11:07:02

--
-- PostgreSQL database dump complete
--

