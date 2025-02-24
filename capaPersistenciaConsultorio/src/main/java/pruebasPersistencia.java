import Conexion.IConexionBD;
import Conexion.ConexionBD; 
import DAO.CitaDAO;
import DAO.PacienteDAO;
import Exception.PersistenciaException;
import java.time.LocalDateTime;
import DAO.CitaDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import Entidades.Direccion;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jesús Osuna 240549
 */
public class pruebasPersistencia {
    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        PacienteDAO pacienteDAO = new PacienteDAO(conexion);
        Scanner scanner = new Scanner (System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
        MedicoDAO medicoDAO = new MedicoDAO(conexion);
        System.out.print("Ingrese el ID de la cita a verificar: ");
        int idCita = scanner.nextInt();

        try {
            pacienteDAO.verificarAsistenciaCita(idCita);
        } catch (PersistenciaException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
      /*  try {
            String tipoUsuario = usuarioDAO.validarCredenciales(usuario, contraseña);

            if ("Paciente".equals(tipoUsuario)) {
                System.out.println("Bienvenido, paciente.");
            } else if ("Medico".equals(tipoUsuario)) {
                System.out.println("Bienvenido, médico.");
            } else {
                System.out.println("Tipo de usuario desconocido.");
            }

        } catch (PersistenciaException e) {
            System.out.println(e.getMessage());
        }
    
        try {
            List<String> historial = pacienteDAO.consultarHistorialConsultas(idPaciente, especialidad, fechaInicio, fechaFin);
            if (historial.isEmpty()) {
                System.out.println("No se encontraron consultas médicas en el historial.");
            } else {
                System.out.println("Historial de Consultas Médicas:");
                historial.forEach(System.out::println);
            }
        } catch (PersistenciaException e) {
            System.out.println("Error al consultar historial de consultas: " + e.getMessage());
        }
        try {
            // Intentar agendar la cita usando el procedimiento almacenado
            CitaDAO nuevaCita = new CitaDAO(idPaciente, idMedico, fechaHora, "Agendada");
            pacienteDAO.agendarCita(nuevaCita); 

            System.out.println(" Cita agendada con éxito para el paciente " + idPaciente + " con el médico " + idMedico +
                               " el día " + fechaHora.toLocalDate() + " a las " + fechaHora.toLocalTime());
        } catch (PersistenciaException e) {
            System.out.println("Error al agendar la cita: " + e.getMessage());
        }
        // Crear conexión a la base de datos
        // Crear usuarios de prueba
        Usuario usuario1 = new Usuario("1333", "password123", "Paciente");
      
        Usuario usuario2 = new Usuario("1666", "password456", "Paciente");

        // Crear direcciones de prueba
        Direccion direccion1 = new Direccion("Calle 1", 123, "Colonia Centro");
        Direccion direccion2 = new Direccion("Avenida Principal", 456, "Colonia Norte");

        // Crear pacientes de prueba
        Paciente paciente1 = new Paciente("pepe", "Pérez", "López", "asd@gmail.com", "12354", direccion1, usuario1);
        Paciente paciente2 = new Paciente("asdaksnd", "Gómez", "Martínez", "aAAAAA@mail.com", "9876", direccion2, usuario2);

        try {
            // Registrar los pacientes en la base de datos
            Paciente registrado1 = pacienteDAO.registrarPaciente(paciente1);
            Paciente registrado2 = pacienteDAO.registrarPaciente(paciente2);

            // Mostrar los pacientes registrados
            System.out.println("Paciente registrado: " + paciente1);
            System.out.println("Paciente registrado: " + paciente2);
        } catch (PersistenciaException e) {
            System.err.println("Error en la persistencia: " + e.getMessage());
        }
    }
       
       //INTENTAMOS CANCELAR UNA CITA CON ESTE BLOQUE DE TRY
               try {
            pacienteDAO.cancelarCita(idCita, idPaciente);
            System.out.println("La cita " + idCita + " ha sido cancelada correctamente.");

            // Verificar en la base de datos (manual)

        } catch (PersistenciaException e) {
            System.out.println("Error al cancelar la cita: " + e.getMessage());
        }
    }
    try {
            int folio = pacienteDAO.generarCitaEmergencia(idPaciente);
            System.out.println("Cita de emergencia registrada con folio: " + folio);

        } catch (PersistenciaException e) {
            System.out.println("Error al generar la cita de emergencia: " + e.getMessage());
        }
    }   */
    }
    



