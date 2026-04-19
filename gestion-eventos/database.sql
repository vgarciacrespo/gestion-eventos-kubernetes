DROP SEQUENCE IF EXISTS seq_recintos;
DROP SEQUENCE IF EXISTS seq_eventos;
DROP SEQUENCE IF EXISTS seq_reservas;


DROP TABLE IF EXISTS recintos CASCADE;
DROP TABLE IF EXISTS eventos CASCADE;
DROP TABLE IF EXISTS reservas CASCADE;

CREATE SEQUENCE seq_recintos MINVALUE 1 MAXVALUE 999999999 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_eventos MINVALUE 1 MAXVALUE 999999999 START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_reservas MINVALUE 1 MAXVALUE 999999999 START WITH 1 INCREMENT BY 1;

CREATE TABLE recintos
(
    id_recinto      VARCHAR(9),
    nombre          VARCHAR(100),
    ubicacion       VARCHAR(255),
    capacidad_total INT,

    CONSTRAINT "PK_RECINTOS" PRIMARY KEY (id_recinto),
    CONSTRAINT "NN_RECINTOS_NOMBRE" CHECK (nombre IS NOT NULL),
    CONSTRAINT "NN_RECINTOS_UBICACION" CHECK (ubicacion IS NOT NULL),
    CONSTRAINT "NN_RECINTOS_CAPACIDAD" CHECK (capacidad_total IS NOT NULL)
);

CREATE TABLE eventos
(
    id_evento     VARCHAR(9),
    nombre        VARCHAR(100),
    descripcion   VARCHAR(500),
    disciplina    VARCHAR(50),
    id_recinto    VARCHAR(9),
    fecha_inicio  TIMESTAMP,
    fecha_fin     TIMESTAMP,
    precio        NUMERIC(10, 2),
    entradas_disponibles INT,
    estado        VARCHAR(20),

    CONSTRAINT "PK_EVENTOS" PRIMARY KEY (id_evento),
    CONSTRAINT "NN_EVENTOS_NOMBRE" CHECK (nombre IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_DISCIPLINA" CHECK (disciplina IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_RECINTO" CHECK (id_recinto IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_FECHA_INICIO" CHECK (fecha_inicio IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_FECHA_FIN" CHECK (fecha_fin IS NOT NULL),
    CONSTRAINT "NN_EVENTOS_ENTRADAS_DISPONIBLES" CHECK (entradas_disponibles IS NOT NULL),
    CONSTRAINT "CK_EVENTOS_ESTADO" CHECK (estado IN ('ABIERTO', 'LLENO', 'CANCELADO', 'FINALIZADO')),
    CONSTRAINT "FK_EVENTOS_RECINTO" FOREIGN KEY (id_recinto) REFERENCES recintos (id_recinto)
);

INSERT INTO recintos (id_recinto, nombre, ubicacion, capacidad_total)
VALUES (nextval('seq_recintos'), 'Polideportivo Municipal Rosa Colorado', 'Av. de los Cipreses, s/n, 37004 Salamanca', 500),
       (nextval('seq_recintos'), 'Club de Raqueta Helmántico', 'C. de la Raqueta, 1, 37005 Salamanca', 200),
       (nextval('seq_recintos'), 'Pistas de Atletismo Helmántico', 'Ctra. Zamora, km 1, 37005 Salamanca', 1500);

INSERT INTO eventos (id_evento, nombre, descripcion, disciplina, id_recinto, fecha_inicio, fecha_fin, precio,
                     entradas_disponibles, estado)
VALUES (nextval('seq_eventos'), 'Torneo de Pádel Primavera', 'Torneo por parejas nivel amateur.', 'Pádel', '2', '2026-05-10 09:00:00',
        '2026-05-10 20:00:00', 15.00, 32, 'ABIERTO'),
       (nextval('seq_eventos'), 'Liga Local Fútbol 7', 'Jornada 1 de la liga municipal.', 'Fútbol 7', '1', '2026-05-12 18:00:00',
        '2026-05-12 22:00:00', 0.00, 140, 'ABIERTO'),
       (nextval('seq_eventos'), 'Masterclass de Tenis', 'Clase magistral con profesionales del sector.', 'Tenis', '2',
        '2026-06-01 10:00:00', '2026-06-01 14:00:00', 50.00, 20, 'LLENO'),
       (nextval('seq_eventos'), 'Campeonato Regional de Atletismo', 'Pruebas de pista y campo para todas las categorías.', 'Atletismo',
        '3', '2026-06-15 08:00:00', '2026-06-16 20:00:00', 5.00, 300, 'ABIERTO'),
       (nextval('seq_eventos'), 'Torneo de Baloncesto 3x3', 'Competición urbana de baloncesto 3x3 en pista cubierta.', 'Baloncesto', '1',
        '2026-05-20 16:00:00', '2026-05-20 21:00:00', 10.00, 48, 'CANCELADO');


CREATE TABLE reservas
(
    id_reserva        VARCHAR(9),
    id_evento         VARCHAR(9),
    nombre_cliente        VARCHAR(100),
    fecha_reserva     TIMESTAMP,
    cantidad_entradas INT,
    precio_total      NUMERIC(10, 2),
    estado            VARCHAR(20),

    CONSTRAINT "PK_RESERVAS" PRIMARY KEY (id_reserva),
    CONSTRAINT "NN_RESERVAS_EVENTO" CHECK (id_evento IS NOT NULL),
    CONSTRAINT "NN_RESERVAS_NOMBRE_CLIENTE" CHECK (nombre_cliente IS NOT NULL),
    CONSTRAINT "NN_RESERVAS_FECHA" CHECK (fecha_reserva IS NOT NULL),
    CONSTRAINT "NN_RESERVAS_CANTIDAD" CHECK (cantidad_entradas IS NOT NULL),
    CONSTRAINT "CK_RESERVAS_CANTIDAD" CHECK (cantidad_entradas > 0),
    CONSTRAINT "NN_RESERVAS_PRECIO" CHECK (precio_total IS NOT NULL),
    CONSTRAINT "CK_RESERVAS_ESTADO" CHECK (estado IN ('PENDIENTE', 'CONFIRMADA', 'CANCELADA'))
);

INSERT INTO reservas (id_reserva, id_evento, nombre_cliente, fecha_reserva, cantidad_entradas, precio_total, estado)
VALUES
(nextval('seq_reservas'), '1', 'Carlos Martínez Ruiz', '2026-04-15 10:30:00', 2, 30.00, 'CONFIRMADA'),
(nextval('seq_reservas'), '2', 'Ana María Gómez Fernández', '2026-04-16 12:15:00', 5, 0.00, 'CONFIRMADA'),
(nextval('seq_reservas'), '4', 'Luis Miguel Rey González', '2026-04-17 09:45:00', 3, 15.00, 'PENDIENTE'),
(nextval('seq_reservas'), '5', 'Eva de la Fuente Domínguez', '2026-04-17 18:00:00', 2, 20.00, 'CANCELADA'),
(nextval('seq_reservas'), '1', 'Juan Pablo Silva', '2026-04-18 11:20:00', 1, 15.00, 'CONFIRMADA');