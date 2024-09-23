--
-- PostgreSQL database dump
--

-- Dumped from database version 14.12
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-22 13:51:49

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
-- TOC entry 227 (class 1259 OID 16596)
-- Name: users_roles; Type: TABLE; Schema: society; Owner: postgres
--

CREATE TABLE society.users_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE society.users_roles OWNER TO postgres;

--
-- TOC entry 3385 (class 0 OID 16596)
-- Dependencies: 227
-- Data for Name: users_roles; Type: TABLE DATA; Schema: society; Owner: postgres
--

COPY society.users_roles (user_id, role_id) FROM stdin;
1	1
2	2
\.


--
-- TOC entry 3243 (class 2606 OID 16600)
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3244 (class 2606 OID 16606)
-- Name: users_roles users_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES society.roles(role_id);


--
-- TOC entry 3245 (class 2606 OID 16601)
-- Name: users_roles users_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: society; Owner: postgres
--

ALTER TABLE ONLY society.users_roles
    ADD CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES society.users(user_id);


-- Completed on 2024-08-22 13:51:49

--
-- PostgreSQL database dump complete
--

