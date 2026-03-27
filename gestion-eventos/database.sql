DROP SEQUENCE IF EXISTS seq_recintos;
DROP SEQUENCE IF EXISTS seq_eventos;

DROP TABLE IF EXISTS recintos CASCADE;
DROP TABLE IF EXISTS eventos CASCADE;

CREATE SEQUENCE seq_recintos MINVALUE 1 MAXVALUE 999999999 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_eventos MINVALUE 1 MAXVALUE 999999999 START WITH 1 INCREMENT BY 1;

CREATE TABLE recintos
(
    id_recinto        VARCHAR(9),
    nombre            VARCHAR(100),
    ubicacion         VARCHAR(255),
    capacidad_total   INT,

    CONSTRAINT "PK_RECINTOS" PRIMARY KEY (id_recinto),
    CONSTRAINT "NN_RECINTOS_NOMBRE" CHECK (nombre IS NOT NULL),
    CONSTRAINT "NN_RECINTOS_UBICACION" CHECK (ubicacion IS NOT NULL),
    CONSTRAINT "NN_RECINTOS_CAPACIDAD" CHECK (capacidad_total IS NOT NULL)
);

CREATE TABLE eventos
(
    id_evento         VARCHAR(9),
    nombre            VARCHAR(100),
    descripcion       VARCHAR(500),
    disciplina        VARCHAR(50),
    id_recinto        VARCHAR(9),
    fecha_inicio      TIMESTAMP,
    fecha_fin         TIMESTAMP,
    precio            NUMERIC(10, 2),
    capacidad_max     INT,
    estado            VARCHAR(20),

    CONSTRAINT "PK_EVENTOS" PRIMARY KEY (id_evento),
    CONSTRAINT "NN_EVENTOS_NOMBRE" CHECK (nombre IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_DISCIPLINA" CHECK (disciplina IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_RECINTO" CHECK (id_recinto IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_FECHA_INICIO" CHECK (fecha_inicio IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_FECHA_FIN" CHECK (fecha_fin IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_CAPACIDAD" CHECK (capacidad_max IS NOT NULL),
    CONSTRAINT "CK_EVENTOS_ESTADO" CHECK (estado IN ('ABIERTO', 'LLENO', 'CANCELADO', 'FINALIZADO')),
    CONSTRAINT "FK_EVENTOS_RECINTO" FOREIGN KEY (id_recinto) REFERENCES recintos(id_recinto)
);