-- Script DDL para la Base de Conocimientos de Le-Bon-Gout
-- PostgreSQL

CREATE TABLE IF NOT EXISTS articulo_kbs (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    solucion TEXT,
    tipo_articulo VARCHAR(20) NOT NULL CHECK (tipo_articulo IN ('OPERATIVO', 'ESTRATEGICO', 'CRISIS')),
    modulo_origen VARCHAR(20) NOT NULL CHECK (modulo_origen IN ('SOLICITUDES', 'CAMBIOS', 'INCIDENCIAS', 'CAPACIDAD', 'MONITOREO', 'CONTINUIDAD')),
    estado VARCHAR(20) NOT NULL DEFAULT 'BORRADOR' CHECK (estado IN ('BORRADOR', 'REVISION_PARES', 'PUBLICADO', 'OBSOLETO', 'EMERGENCIA_ACTIVA')),
    categoria VARCHAR(100),
    likes INTEGER DEFAULT 0,
    dislikes INTEGER DEFAULT 0,
    rating DOUBLE PRECISION DEFAULT 0.0,
    total_votos INTEGER DEFAULT 0,
    afecta_cocina BOOLEAN DEFAULT FALSE,
    afecta_salon BOOLEAN DEFAULT FALSE,
    afecta_reservas BOOLEAN DEFAULT FALSE,
    creado_por VARCHAR(100),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_articulo_estado ON articulo_kbs(estado);
CREATE INDEX idx_articulo_tipo ON articulo_kbs(tipo_articulo);
CREATE INDEX idx_articulo_modulo ON articulo_kbs(modulo_origen);
CREATE INDEX idx_articulo_categoria ON articulo_kbs(categoria);
