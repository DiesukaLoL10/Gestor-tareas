ALTER TABLE tareas 
ADD COLUMN fecha_inicio DATE;

CREATE INDEX idx_tareas_fecha_inicio ON tareas (fecha_inicio);