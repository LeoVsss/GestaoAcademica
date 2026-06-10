--
-- PostgreSQL database dump
--

\restrict dMW11yb9z8e9zhihmSYPcaz8IBgrLqSC9FzmjvO6rrzukvHCVg5LXHTuZ1IYr5S

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.3

-- Started on 2026-06-10 18:34:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- TOC entry 220 (class 1259 OID 16392)
-- Name: curso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.curso (
    codigo integer NOT NULL,
    nome_curso character varying(100) NOT NULL,
    descricao text
);


ALTER TABLE public.curso OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16402)
-- Name: disciplina; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.disciplina (
    numero integer NOT NULL,
    nome character varying(100) NOT NULL,
    data_inicio date NOT NULL,
    data_encerramento date NOT NULL,
    codigo_professor integer,
    codigo_curso integer,
    CONSTRAINT chk_data_inicio_corrente CHECK ((data_inicio >= CURRENT_DATE)),
    CONSTRAINT chk_data_inicio_encerramento CHECK ((data_inicio <= data_encerramento))
);


ALTER TABLE public.disciplina OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16401)
-- Name: disciplina_numero_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.disciplina_numero_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.disciplina_numero_seq OWNER TO postgres;

--
-- TOC entry 5032 (class 0 OID 0)
-- Dependencies: 221
-- Name: disciplina_numero_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.disciplina_numero_seq OWNED BY public.disciplina.numero;


--
-- TOC entry 219 (class 1259 OID 16385)
-- Name: professor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.professor (
    codigo_funcional integer NOT NULL,
    nome character varying(100) NOT NULL,
    data_nascimento date,
    cod_curso integer
);


ALTER TABLE public.professor OWNER TO postgres;

--
-- TOC entry 4864 (class 2604 OID 16405)
-- Name: disciplina numero; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disciplina ALTER COLUMN numero SET DEFAULT nextval('public.disciplina_numero_seq'::regclass);


--
-- TOC entry 5024 (class 0 OID 16392)
-- Dependencies: 220
-- Data for Name: curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.curso (codigo, nome_curso, descricao) FROM stdin;
1	Matemática	Curso focado no desenvolvimento lógico e resolução de problemas
2	Engenharia de Computação	Computers
3	Engenharia Mecânica	Mecas
\.


--
-- TOC entry 5026 (class 0 OID 16402)
-- Dependencies: 222
-- Data for Name: disciplina; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.disciplina (numero, nome, data_inicio, data_encerramento, codigo_professor, codigo_curso) FROM stdin;
1	Desenvolvimento web	2026-05-25	2026-12-30	2	2
2	Mecânica dos Sólidos	2026-07-03	2026-12-12	3	3
\.


--
-- TOC entry 5023 (class 0 OID 16385)
-- Dependencies: 219
-- Data for Name: professor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.professor (codigo_funcional, nome, data_nascimento, cod_curso) FROM stdin;
1	Jonathan Souza	1996-07-12	1
2	Amilton	1967-07-06	2
3	Jorge	1986-09-18	3
\.


--
-- TOC entry 5033 (class 0 OID 0)
-- Dependencies: 221
-- Name: disciplina_numero_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.disciplina_numero_seq', 4, true);


--
-- TOC entry 4870 (class 2606 OID 16400)
-- Name: curso curso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curso
    ADD CONSTRAINT curso_pkey PRIMARY KEY (codigo);


--
-- TOC entry 4872 (class 2606 OID 16413)
-- Name: disciplina disciplina_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_pkey PRIMARY KEY (numero);


--
-- TOC entry 4868 (class 2606 OID 16391)
-- Name: professor professor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_pkey PRIMARY KEY (codigo_funcional);


--
-- TOC entry 4874 (class 2606 OID 16419)
-- Name: disciplina disciplina_codigo_curso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_codigo_curso_fkey FOREIGN KEY (codigo_curso) REFERENCES public.curso(codigo);


--
-- TOC entry 4875 (class 2606 OID 16414)
-- Name: disciplina disciplina_codigo_professor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disciplina
    ADD CONSTRAINT disciplina_codigo_professor_fkey FOREIGN KEY (codigo_professor) REFERENCES public.professor(codigo_funcional);


--
-- TOC entry 4873 (class 2606 OID 16424)
-- Name: professor professor_cod_curso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.professor
    ADD CONSTRAINT professor_cod_curso_fkey FOREIGN KEY (cod_curso) REFERENCES public.curso(codigo);


-- Completed on 2026-06-10 18:34:39

--
-- PostgreSQL database dump complete
--

\unrestrict dMW11yb9z8e9zhihmSYPcaz8IBgrLqSC9FzmjvO6rrzukvHCVg5LXHTuZ1IYr5S

