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
