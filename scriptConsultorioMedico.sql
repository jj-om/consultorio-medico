-- CREAR BASE DE DATOS
DROP DATABASE IF EXISTS consultorioMedico;
CREATE DATABASE IF NOT EXISTS consultorioMedico;

-- SELECCIONAR LA BASE DE DATOS
USE consultorioMedico;

-- CREAR TABLA USUARIOS
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(40) UNIQUE NOT NULL,
    contraseña VARCHAR(64) NOT NULL,
    tipo ENUM('Paciente', 'Medico') NOT NULL
);

-- CREAR TABLA MEDICOS
CREATE TABLE Medicos (
    id_medico INT PRIMARY KEY,
    cedulaProfesional VARCHAR(50) UNIQUE NOT NULL,
    nombres VARCHAR(40) NOT NULL,
    apellidoPaterno VARCHAR(20) NOT NULL,
    apellidoMaterno VARCHAR(20) NULL,
    especialidad VARCHAR(50) NOT NULL,
    estado ENUM('Activo', 'Inactivo') NOT NULL,
    FOREIGN KEY (id_medico) REFERENCES Usuarios(id_usuario)
);

-- CREAR TABLA HORARIOS
CREATE TABLE Horarios (
    id_horario INT AUTO_INCREMENT PRIMARY KEY,
    horaInicio TIME NOT NULL,
    horaFinal TIME NOT NULL,
    diaSemana ENUM('domingo', 'lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado') NOT NULL
);

-- CREAR TABLA HORARIOS_MEDICOS
CREATE TABLE Horarios_Medicos (
    id_horarioMedico INT AUTO_INCREMENT PRIMARY KEY,
    id_medico INT NOT NULL,
    id_horario INT NOT NULL,
    FOREIGN KEY (id_medico) REFERENCES Medicos(id_medico),
    FOREIGN KEY (id_horario) REFERENCES Horarios(id_horario)
);

-- CREAR TABLA DIRECCIONES
CREATE TABLE Direcciones (
    id_direccion INT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(40) NOT NULL,
    numero INT NOT NULL,
    colonia VARCHAR(40) NOT NULL
);

-- CREAR TABLA PACIENTES
CREATE TABLE Pacientes (
    id_paciente INT PRIMARY KEY,
    nombres VARCHAR(40) NOT NULL,
    apellidoPaterno VARCHAR(20) NOT NULL,
    apellidoMaterno VARCHAR(20) NULL,
    fechaNacimiento DATE NOT NULL,
    edad INT NULL,
    correoElectronico VARCHAR(70) UNIQUE NOT NULL,
    telefono VARCHAR(15) UNIQUE NULL,
    id_direccion INT NOT NULL,
    FOREIGN KEY (id_direccion) REFERENCES Direcciones(id_direccion),
    FOREIGN KEY (id_paciente) REFERENCES Usuarios(id_usuario)
);

-- CREAR TABLA CITAS
CREATE TABLE Citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fechaHoraCita DATE NOT NULL,
    estado ENUM('agendada', 'atendida', 'cancelada', 'no atendida', 'no asistio paciente') NOT NULL,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES Pacientes(id_paciente),
	FOREIGN KEY (id_medico) REFERENCES Medicos(id_medico)
);

-- CREAR TABLA CITAS_EMERGENCIAS
CREATE TABLE Citas_Emergencias (
    id_citaEmergencia INT PRIMARY KEY,
    folio INT NOT NULL,
    FOREIGN KEY (id_citaEmergencia) REFERENCES Citas(id_cita) ON DELETE CASCADE
);

-- CREAR TABLA CONSULTAS
CREATE TABLE Consultas (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    fechaHoraConsulta DATETIME DEFAULT CURRENT_TIMESTAMP,
    motivo VARCHAR(100) NOT NULL,
    diagnostico VARCHAR(200) NOT NULL,
    tratamiento VARCHAR(200) NULL,
    id_cita INT NOT NULL,
	FOREIGN KEY (id_cita) REFERENCES Citas(id_cita)
);

-- CREAR TABLA AUDITORIAS
CREATE TABLE Auditorias (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    fechaHora DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipoMovimiento VARCHAR(50) NOT NULL,
    id_usuario INT NOT NULL,
    id_cita INT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_cita) REFERENCES Citas(id_cita)
);
