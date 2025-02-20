import Conexion.IConexionBD;
import Conexion.ConexionBD; // Asegúrate de tener una implementación de IConexionBD
import DAO.PacienteDAO;
import Entidades.Direccion;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.PersistenciaException;

/**
 * @author Jesús Osuna 240549
 */
public class pruebasPersistencia {
    public static void main(String[] args) {
        // Crear conexión a la base de datos
        IConexionBD conexion = new ConexionBD(); // Asegúrate de implementar correctamente esta clase
        PacienteDAO pacienteDAO = new PacienteDAO(conexion);

        // Crear usuarios de prueba
        Usuario usuario1 = new Usuario("098", "password123", "Paciente");
        Usuario usuario2 = new Usuario("890", "password456", "Paciente");

        // Crear direcciones de prueba
        Direccion direccion1 = new Direccion("Calle 1", 123, "Colonia Centro");
        Direccion direccion2 = new Direccion("Avenida Principal", 456, "Colonia Norte");

        // Crear pacientes de prueba
        Paciente paciente1 = new Paciente("Juan", "Pérez", "López", "12345@gmail.com", "54101", direccion1, usuario1);
        Paciente paciente2 = new Paciente("Ana", "Gómez", "Martínez", "54321@mail.com", "10154", direccion2, usuario2);

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
}
