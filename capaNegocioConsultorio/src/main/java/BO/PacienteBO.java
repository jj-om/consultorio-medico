package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.DireccionNuevaDTO;
import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import Entidades.Direccion;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.DireccionMapper;
import Mapper.PacienteMapper;
import Mapper.UsuarioMapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class PacienteBO {
    
    private static final Logger logger = Logger.getLogger(PacienteBO.class.getName());
    private final IPacienteDAO pacienteDAO;
    private final PacienteMapper PacienteMapper = new PacienteMapper();
    private final DireccionMapper DireccionMapper = new DireccionMapper();
    private final UsuarioMapper UsuarioMapper = new UsuarioMapper();
    
    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }
    
    public boolean registrarPaciente(PacienteNuevoDTO pacienteNuevo, DireccionNuevaDTO direccionNueva, UsuarioNuevoDTO usuarioNuevo) throws NegocioException, NoSuchAlgorithmException {
        
        // VALIDAR QUE EL DTO pacienteNuevo
        if (pacienteNuevo == null) {
            throw new NegocioException("El Paciente no puede ser nulo.");
        }
        
        // VALIDAR QUE EL DTO direccionNueva
        if (direccionNueva == null) {
            throw new NegocioException("La Direccion no puede ser nulo.");
        }
        
        // VALIDAR QUE EL DTO usuarioNuevo
        if (usuarioNuevo == null) {
            throw new NegocioException("El Usuario no puede ser nulo.");
        }
        
        // VERIFICAR QUE LOS CAMPOS REQUERIDOS NO ESTEN VACIOS
        if (pacienteNuevo.getNombres().isEmpty() ||
                pacienteNuevo.getApellidoPaterno().isEmpty() ||
                pacienteNuevo.getCorreoElectronico().isEmpty() ||
                direccionNueva.getCalle().isEmpty() ||
                direccionNueva.getNumero() == -1 ||
                direccionNueva.getColonia().isEmpty() ||
                usuarioNuevo.getUsuario().isEmpty() ||
                usuarioNuevo.getContraseña().isEmpty() ||
                usuarioNuevo.getTipo().isEmpty()
                ) {
            throw new NegocioException("Todos los campos son obligatorios.");
        }
        
        try {
            // ENCRIPTAR LA contraseña DE usuarioNuevo
            String contraseñaEncriptada = hash(usuarioNuevo.getContraseña());
            usuarioNuevo.setContraseña(contraseñaEncriptada);
            
        } catch(NoSuchAlgorithmException ex) {
            throw new NegocioException("Hubo un error al registrar al Paciente.");
        }
        
        // CONVERTIR EL DTO direccionNueva EN UNA ENTIDAD Direccion
        Direccion direccion = DireccionMapper.toEntity(direccionNueva);
        
        // CONVERTIR EL DTO direccionNueva EN UNA ENTIDAD Direccion
        Usuario usuario = UsuarioMapper.toEntity(usuarioNuevo);
        
        // CONVERTIR EL DTO pacienteNuevo Y LAS ENTIDADES direccion Y usurio A UNA ENTIDAD Paciente
        Paciente paciente = PacienteMapper.toEntity(pacienteNuevo, direccion, usuario);
        
        try {
            // INTENTAR REGISTRAR A paciente EN LA BASE DE DATOS
            Paciente pacienteRegistrado = pacienteDAO.registrarPaciente(paciente);
            
            // SI EL PACIENTE FUE REGISTRADO CON EXITO, DEVUELVE true SI NO, DEVUELVE false
            return pacienteRegistrado != null;
            
        } catch (PersistenciaException ex) {
            
            // REGISTRAR EL ERROR EN LOS logs
            logger.log(Level.SEVERE, "Error al reistrar al Paciente en la BD", ex);
            
            // LANZAR UNA EXCEPCION DE negocio
            throw new NegocioException("Hubo un error al registrar al Paciente.", ex);
        }
    }
    
    // METODO PRIVADO PARA ENCRIPTAR LA contraseña DEL Usuario 
    private String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}