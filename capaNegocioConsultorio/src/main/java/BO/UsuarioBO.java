/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.UsuarioDAO;
import Exception.NegocioException;
import Exception.PersistenciaException;

/**
 *
 * @author Gael
 */

public class UsuarioBO {
    private final UsuarioDAO usuarioDAO;

    public UsuarioBO(IConexionBD conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
    }

    public String validarCredenciales(String usuario, String contraseña) throws NegocioException, PersistenciaException {
        if (usuario == null || usuario.isEmpty() || contraseña == null || contraseña.isEmpty()) {
            throw new NegocioException("Usuario y contraseña son obligatorios.");
        }

        try {
            return usuarioDAO.validarCredenciales(usuario, contraseña);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error en la autenticación: " + ex.getMessage(), ex);
        }
    }
}

