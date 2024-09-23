--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-22 13:52:24

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 226 (class 1259 OID 16588)
-- Name: roles; Type: TABLE; Schema: society; Owner: postgres
--

CREATE TABLE society.roles (
    role_id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE society.roles OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16587)
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: society; Owner: postgres
--

CREATE SEQUENCE society.roles_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.roles_role_id_seq OWNER TO postgres;

--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 225
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: postgres
--

ALTER SEQUENCE society.roles_role_id_seq OWNED BY society.roles.role_id;


--
-- TOC entry 3242 (class 2604 OID 16591)
-- Name: roles role_id; Type: DEFAULT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.roles ALTER COLUMN role_id SET DEFAULT nextval('society.roles_role_id_seq'::regclass);


--
-- TOC entry 3387 (class 0 OID 16588)
-- Dependencies: 226
-- Data for Name: roles; Type: TABLE DATA; Schema: society; Owner: postgres
--

COPY society.roles (role_id, name) FROM stdin;
1	ADMIN
2	USER
\.


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 225
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: society; Owner: postgres
--

SELECT pg_catalog.setval('society.roles_role_id_seq', 1, false);


--
-- TOC entry 3244 (class 2606 OID 16595)
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- TOC entry 3246 (class 2606 OID 16593)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


-- Completed on 2024-08-22 13:52:25

--
-- PostgreSQL database dump complete
--

