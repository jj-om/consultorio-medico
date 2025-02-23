USE consultoriomedico;

-- TRIGGER PARA AUDITORIAS AL REGISTRAR UN PACIENTE
DELIMITER &&

CREATE TRIGGER aud_registro_paciente
AFTER INSERT ON Pacientes
FOR EACH ROW
BEGIN
    INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita)
    VALUES ('registrapaciente', NEW.id_paciente, NULL);
END &&

DELIMITER ;

-- TRIGGER PARA LA AUDITORIA AL REGISTRAR UNA CITA
DELIMITER &&

CREATE TRIGGER aud_registrar_cita
AFTER INSERT ON Citas
FOR EACH ROW
BEGIN
    INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita)
    VALUES ('registracita', NEW.id_paciente, NEW.id_cita);
END &&

DELIMITER ;


-- TRIGGER PARA LA AUDITORIA AL CANCELAR UNA CITA
DELIMITER &&

CREATE TRIGGER aud_cancelar_cita
AFTER UPDATE ON Citas
FOR EACH ROW
BEGIN
    IF OLD.estado != 'cancelada' AND NEW.estado = 'cancelada' THEN
        INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita)
        VALUES ('cancelacita', NEW.id_paciente, NEW.id_cita);
    END IF;
END &&

DELIMITER ;

-- TRIGGER PARA AUDITORIA AL INICIAR UNA CITA
DELIMITER &&

CREATE TRIGGER aud_iniciar_cita
AFTER UPDATE ON Citas
FOR EACH ROW
BEGIN
    IF OLD.estado != 'atendida' AND NEW.estado = 'atendida' THEN
        INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita)
        VALUES ('iniciacita', NEW.id_paciente, NEW.id_cita);
    END IF;
END &&

DELIMITER ;

-- TRIGGER PARA AUDITORIA AL TERMINAR UNA CITA
DELIMITER &&

CREATE TRIGGER aud_terminar_cita
AFTER INSERT ON Consultas
FOR EACH ROW
BEGIN
    INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita)
    VALUES ('terminacita', (SELECT id_paciente FROM Citas WHERE id_cita = NEW.id_cita), NEW.id_cita);
END &&

DELIMITER ;


-- PROCEDIMIENTO ALMACENADO PARA REGISTRAR A UN PACIENTE
DELIMITER &&
CREATE PROCEDURE registrarPaciente(
	-- SELECCIONA LOS ATRIBUTOS
    IN paciente_nombres VARCHAR(40),
    IN paciente_apellidoPaterno VARCHAR(20),
    IN paciente_apellidoMaterno VARCHAR(20),
    IN paciente_correoElectronico VARCHAR(70),
    IN paciente_usuario VARCHAR(40),
    IN paciente_contraseña VARCHAR(40),
    IN paciente_telefono VARCHAR(15),
    IN paciente_calle VARCHAR(40),
    IN paciente_numero INT,
    IN paciente_colonia VARCHAR(40)
)
BEGIN

	-- DECLARAMOS LAS VARIABLES DE VERIFICACION
    DECLARE verificar_id_direccion INT;
    DECLARE verificar_existePaciente INT;
    DECLARE ultimo_usuario INT;

    -- AUNQUE SEA UNIQUE EL CORREO ELECTRONICO, LO VERIFICAMOS POR SEGURIDAD, EVITAR DUPLICADOS Y MANDAR MENSAJE PERSONALIZADO
    SELECT COUNT(*) INTO verificar_existePaciente
    FROM Pacientes
    WHERE correoElectronico = paciente_correoElectronico;

	-- SI NO ENCUENTRA COINCIDENCIAS SIGUE EL PROCESO
    IF verificar_existePaciente = 0 THEN
        -- INSERTAMOS LA DIRECCION ANTES DEL CLIENTE
        INSERT INTO Direcciones (calle, numero, colonia)
        VALUES (paciente_calle, paciente_numero, paciente_colonia);

        -- VERIFICAMOS QUE LA DIRECCION HAYA SIDO AGRAGADA CORRECTAMENTE
        SET verificar_id_direccion = LAST_INSERT_ID();

		-- REGISTRAMOS AL PACIENTE EN LA TABLA USUARIOS
        INSERT INTO Usuarios (usuario, contraseña, tipo)
        VALUES (paciente_usuario, paciente_contraseña, 'Paciente');
        SET ultimo_usuario = LAST_INSERT_ID();

        -- INSERTAMOS AHORA SI AL CLIENTE
        INSERT INTO Pacientes (id_paciente, nombres, apellidoPaterno, apellidoMaterno, correoElectronico, telefono, id_direccion)
        VALUES (ultimo_usuario, paciente_nombres, paciente_apellidoPaterno, paciente_apellidoMaterno, paciente_correoElectronico, paciente_telefono, verificar_id_direccion);
    
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El correo electrónico ya está registrado.';
    END IF;
END &&

DELIMITER ;

-- PROCEDIMIENTO PARA REGISTRAR UN MEDICO -- USO DE NOSOTROS, NO ES PARA EL PROGRAMA
DELIMITER ##

CREATE PROCEDURE registrarMedico(
	-- SELECCIONA LOS ATRIBUTOS
	IN medico_cedulaProfesional VARCHAR(50),
	IN medico_nombres VARCHAR(40),
    IN medico_apellidoPaterno VARCHAR(20),
    IN medico_apellidoMaterno VARCHAR(20),
    IN medico_usuario VARCHAR(40),
    IN medico_contraseña VARCHAR(40),
    IN medico_especialidad VARCHAR(50),
    IN medico_estado ENUM('Activo', 'Inactivo'),
    IN medico_horario INT
) 
BEGIN

	-- DECLARAMOS LAS VARIABLES DE VERIFICACION
	DECLARE verificar_cedula INT;
    DECLARE ultimo_usuario INT;
    -- AUNQUE SEA UNIQUE REVISAMOS QUE NO EXISTA LA CEDULA PROFESIONAL
    SELECT COUNT(*) INTO verificar_cedula
    FROM Medicos
    WHERE cedulaProfesional = medico_cedulaProfesional;
    -- VERIFICAMOS LA CONSULTA
    IF verificar_cedula = 0 THEN
		-- REGISTRAMOS AL MEDICO EN LA TABLA USUARIOS
        INSERT INTO Usuarios (usuario, contraseña, tipo)
        VALUES (medico_usuario, medico_contraseña, 'Medico');
        SET ultimo_usuario = LAST_INSERT_ID();
        
        -- INSERTAMOS AHORA SI AL MEDICO
		INSERT INTO medicos(id_medico, cedulaProfesional, nombres, apellidoPaterno, apellidoMaterno, especialidad, estado)
        VALUES (ultimo_usuario, medico_cedulaProfesional, medico_nombres, medico_apellidoPaterno, medico_apellidoMaterno, medico_especialidad, medico_estado);
        -- INSERTAMOS EL HORARIO DEL MEDICO
        INSERT INTO horarios_medicos(id_medico, id_horario)
        VALUES (ultimo_usuario, medico_horario);
	ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La Cedula Profesional ya está registrada.';
    END IF ;
END ##

DELIMITER ;

-- PROCEDIMIENTO PARA AGENTAR UNA CITA
DELIMITER //
CREATE PROCEDURE AgendarCita(
    IN paciente_id INT,
    IN medico_id INT,
    IN fecha_hora DATETIME
)
BEGIN
    DECLARE contar_citas INT;
    DECLARE horario_valido INT;

    -- Verificar si el paciente ya tiene una cita con el mismo médico en la misma fecha
    SELECT COUNT(*) INTO contar_citas 
    FROM Citas 
    WHERE id_paciente = paciente_id 
      AND id_medico = medico_id 
      AND DATE(fechaHoraCita) = DATE(fecha_hora);

    IF contar_citas > 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'El paciente ya tiene una cita con este médico en la misma fecha';
    END IF;

    -- Verificar si el médico está disponible en ese horario
    SELECT COUNT(*) INTO horario_valido 
    FROM Horarios_Medicos hm
    JOIN Horarios h ON hm.id_horario = h.id_horario
    WHERE hm.id_medico = medico_id 
      AND TIME(fecha_hora) >= h.horaInicio 
      AND TIME(fecha_hora) <= h.horaFinal
      AND LOWER(h.diaSemana) = LOWER(DAYNAME(fecha_hora));

    IF horario_valido = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'El médico no está disponible en este horario';
    END IF;

    -- Insertar la cita si todo está correcto
    INSERT INTO Citas (fechaHoraCita, estado, id_paciente, id_medico) 
    VALUES (fecha_hora, 'Agendada', paciente_id, medico_id);

    INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita) 
    VALUES ('Cita Agendada', paciente_id, LAST_INSERT_ID());
END //
DELIMITER ;

-- PROCEDIMIENTO ALMACENADO PARA CANCELAR UNA CITA
DELIMITER //

CREATE PROCEDURE CancelarCita(
    IN cita_id INT,
    IN paciente_id INT
)
BEGIN
    DECLARE cita_existente INT;
    DECLARE estado_actual VARCHAR(20);

    -- Verificar si la cita existe y pertenece al paciente
    SELECT COUNT(*) INTO cita_existente 
    FROM Citas 
    WHERE id_cita = cita_id AND id_paciente = paciente_id;

    IF cita_existente = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'La cita no existe o no pertenece a este paciente';
    END IF;

    -- Verificar el estado actual de la cita
    SELECT estado INTO estado_actual 
    FROM Citas 
    WHERE id_cita = cita_id;

    -- No permitir cancelar citas ya atendidas
    IF estado_actual = 'atendida' THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'No se puede cancelar una cita ya atendida';
    END IF;

    -- Actualizar estado de la cita a 'cancelada'
    UPDATE Citas 
    SET estado = 'cancelada' 
    WHERE id_cita = cita_id;

    -- Registrar la cancelación en la auditoría
    INSERT INTO Auditorias (tipoMovimiento, id_usuario, id_cita) 
    VALUES ('Cita Cancelada', paciente_id, cita_id);
END //
DELIMITER ;

