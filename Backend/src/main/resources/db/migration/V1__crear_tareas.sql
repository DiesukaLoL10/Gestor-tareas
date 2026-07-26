CREATE TABLE tareas (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    estatus VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    creado_el TIMESTAMP NOT NULL DEFAULT now(),
    actualizado_el TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_tareas_estatus ON tareas (estatus);