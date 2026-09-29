-- ============================================================
-- TIPOS DE VEHÍCULO
-- ============================================================

INSERT INTO tipos_vehiculo (nombre, tarifa_por_minuto) VALUES ('OFICIAL', 0.00);
INSERT INTO tipos_vehiculo (nombre, tarifa_por_minuto) VALUES ('RESIDENTE', 0.05);
INSERT INTO tipos_vehiculo (nombre, tarifa_por_minuto) VALUES ('NO_RESIDENTE', 0.50);
INSERT INTO tipos_vehiculo (nombre, tarifa_por_minuto) VALUES ('TRANSPORTE', 0.20);

-- ============================================================
-- VEHÍCULOS OFICIALES
-- ============================================================

INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('OFI001', 'OFICIAL');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('OFI002', 'OFICIAL');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('OFI003', 'OFICIAL');

-- ============================================================
-- VEHÍCULOS RESIDENTES
-- ============================================================

INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('RES001', 'RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('RES002', 'RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('RES003', 'RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('RES004', 'RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('RES005', 'RESIDENTE');

-- ============================================================
-- VEHÍCULOS NO RESIDENTES
-- ============================================================

INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('NRE001', 'NO_RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('NRE002', 'NO_RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('NRE003', 'NO_RESIDENTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('NRE004', 'NO_RESIDENTE');

-- ============================================================
-- VEHÍCULOS DE TIPO PERSONALIZADO
-- ============================================================

INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('TRS001', 'TRANSPORTE');
INSERT INTO vehiculos (placa, tipo_vehiculo) VALUES ('TRS002', 'TRANSPORTE');

-- ============================================================
-- RESIDENTES
-- ============================================================

INSERT INTO residentes (placa, tiempo_acumulado) VALUES ('RES001', 420);
INSERT INTO residentes (placa, tiempo_acumulado) VALUES ('RES002', 235);
INSERT INTO residentes (placa, tiempo_acumulado) VALUES ('RES003', 1110);
INSERT INTO residentes (placa, tiempo_acumulado) VALUES ('RES004', 150);
INSERT INTO residentes (placa, tiempo_acumulado) VALUES ('RES005', 175);

-- ============================================================
-- ESTANCIAS COMPLETADAS
-- ============================================================

-- RESIDENTES
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES001', '2026-09-25 08:00:00', '2026-09-25 10:00:00', 120);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES001', '2026-09-25 14:00:00', '2026-09-25 15:30:00', 90);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES001', '2026-09-20 07:50:00', '2026-09-20 09:15:00', 85);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES001', '2026-09-21 12:30:00', '2026-09-21 13:15:00', 45);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES001', '2026-09-23 16:00:00', '2026-09-23 17:20:00', 80);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES002', '2026-09-25 09:30:00', '2026-09-25 10:45:00', 75);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES003', '2026-09-24 07:45:00', '2026-09-24 10:45:00', 180);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES003', '2026-09-24 13:00:00', '2026-09-24 18:00:00', 300);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES004', '2026-09-25 16:10:00', '2026-09-25 16:45:00', 35);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES002', '2026-09-20 08:10:00', '2026-09-20 08:40:00', 30);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES002', '2026-09-22 10:00:00', '2026-09-22 11:20:00', 80);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES002', '2026-09-23 17:10:00', '2026-09-23 18:00:00', 50);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES003', '2026-09-19 06:45:00', '2026-09-19 09:30:00', 165);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES003', '2026-09-20 13:30:00', '2026-09-20 16:45:00', 195);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES003', '2026-09-22 08:00:00', '2026-09-22 12:30:00', 270);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES004', '2026-09-20 11:20:00', '2026-09-20 12:00:00', 40);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES004', '2026-09-22 15:00:00', '2026-09-22 15:25:00', 25);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES004', '2026-09-24 09:15:00', '2026-09-24 10:05:00', 50);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES005', '2026-09-20 07:30:00', '2026-09-20 08:45:00', 75);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('RES005', '2026-09-22 14:00:00', '2026-09-22 15:40:00', 100);

-- NO RESIDENTES
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('NRE001', '2026-09-25 11:00:00', '2026-09-25 12:30:00', 90);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('NRE002', '2026-09-24 15:20:00', '2026-09-24 17:05:00', 105);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('NRE003', '2026-09-25 18:00:00', '2026-09-25 18:12:00', 12);

-- OFICIAL
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI001', '2026-09-25 06:30:00', '2026-09-25 09:00:00', 150);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI001', '2026-09-20 06:30:00', '2026-09-20 11:00:00', 270);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI001', '2026-09-22 07:00:00', '2026-09-22 12:15:00', 315);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI002', '2026-09-19 08:00:00', '2026-09-19 12:30:00', 270);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI002', '2026-09-21 07:45:00', '2026-09-21 10:20:00', 155);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI002', '2026-09-24 13:00:00', '2026-09-24 17:30:00', 270);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI003', '2026-09-20 09:15:00', '2026-09-20 14:00:00', 285);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('OFI003', '2026-09-23 06:50:00', '2026-09-23 11:30:00', 280);

-- TRANSPORTE
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS001', '2026-09-25 10:15:00', '2026-09-25 11:00:00', 45);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS001', '2026-09-20 08:30:00', '2026-09-20 09:30:00', 60);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS001', '2026-09-22 12:15:00', '2026-09-22 13:45:00', 90);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS001', '2026-09-24 16:20:00', '2026-09-24 17:05:00', 45);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS002', '2026-09-19 10:00:00', '2026-09-19 12:00:00', 120);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS002', '2026-09-21 09:30:00', '2026-09-21 11:15:00', 105);
INSERT INTO estancias (placa, fecha_hora_entrada, fecha_hora_salida, minutos) VALUES ('TRS002', '2026-09-23 14:00:00', '2026-09-23 16:30:00', 150);

-- ============================================================
-- ESTANCIAS ACTIVAS
-- ============================================================

-- RESIDENTES
INSERT INTO estancias (placa, fecha_hora_entrada) VALUES ('RES005', '2026-09-26 08:30:00');

-- NO RESIDENTES
INSERT INTO estancias (placa, fecha_hora_entrada) VALUES ('NRE004', '2026-09-26 09:15:00');

-- OFICIAL
INSERT INTO estancias (placa, fecha_hora_entrada) VALUES ('OFI001', '2026-09-26 07:45:00');
