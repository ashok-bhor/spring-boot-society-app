--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-22 13:52:05

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
-- TOC entry 224 (class 1259 OID 16579)
-- Name: users; Type: TABLE; Schema: society; Owner: postgres
--

CREATE TABLE society.users (
    user_id integer NOT NULL,
    username character varying(45) NOT NULL,
    password character varying(64) NOT NULL,
    enabled smallint
);


ALTER TABLE society.users OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16578)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: society; Owner: postgres
--

CREATE SEQUENCE society.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE society.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: society; Owner: postgres
--

ALTER SEQUENCE society.users_user_id_seq OWNED BY society.users.user_id;


--
-- TOC entry 3242 (class 2604 OID 16582)
-- Name: users user_id; Type: DEFAULT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users ALTER COLUMN user_id SET DEFAULT nextval('society.users_user_id_seq'::regclass);


--
-- TOC entry 3387 (class 0 OID 16579)
-- Dependencies: 224
-- Data for Name: users; Type: TABLE DATA; Schema: society; Owner: postgres
--

COPY society.users (user_id, username, password, enabled) FROM stdin;
1	admin	$2b$12$fzFPKMEiDGduQZplzBelaOrKKOxN0IrDvXnKPId0ZCmdgWYjEQkrq	1
2	member	$2a$12$O2LewonIRTGj0oGeQzoCnuNYJhZEHYgSSRRw1TsCIxvPwaNbKCAji	1
\.


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: society; Owner: postgres
--

SELECT pg_catalog.setval('society.users_user_id_seq', 2, true);


--
-- TOC entry 3244 (class 2606 OID 16584)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3246 (class 2606 OID 16586)
-- Name: users users_username_key; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


-- Completed on 2024-08-22 13:52:05

--
-- PostgreSQL database dump complete
--

