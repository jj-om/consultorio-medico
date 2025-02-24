package GUI;

import BO.UsuarioBO;
import DTO.SesionDTO;
import javax.swing.JOptionPane;

import Exception.NegocioException;
import Exception.PersistenciaException;
import configuracion.DependencyInjector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class SesionForm extends javax.swing.JFrame {

    private UsuarioBO usuarioBO = DependencyInjector.crearUsuarioBO();
    private String tipoUsuario;
    private boolean usuarioValido;

    public SesionForm() {
        initComponents();
    }

    private void iniciarSesion() throws PersistenciaException {
        String usuario = textUser.getText().trim();
        char[] caracteres = textPass.getPassword();

        if (usuario.isEmpty() || caracteres.length == 0) {
            JOptionPane.showMessageDialog(this, "No puede dejar ningún espacio en blanco.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String contraseña = new String(caracteres);

        try {
            SesionDTO sesion = new SesionDTO(usuario, contraseña);

            boolean validacion = usuarioBO.validarCredenciales(sesion);

            if (validacion) {
                this.usuarioValido = true;
                this.tipoUsuario = usuarioBO.recuperarUsuario(sesion.getUsuario(), sesion.getContraseña()).getTipo();
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                abrirVentanaPrincipal();
            } else {
                this.usuarioValido = false;
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.usuarioValido = false;
        }
    }

    private void abrirVentanaPrincipal() {
        if (tipoUsuario.equalsIgnoreCase("Paciente")) {
            PrinPacienteForm principal = new PrinPacienteForm();
            principal.setVisible(true);
        } else if (tipoUsuario.equalsIgnoreCase("Medico")) {
            //pantallas del medico
        } else {
            JOptionPane.showMessageDialog(this, "Tipo de usuario no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textUser = new javax.swing.JTextField();
        btnInicioSesion = new javax.swing.JButton();
        btnInicioSesion1 = new javax.swing.JButton();
        textPass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Consultorio Medico");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("INICIO SESION");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setText("Contraseña:");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setText("Usuario:");

        textUser.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        textUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textUserActionPerformed(evt);
            }
        });

        btnInicioSesion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnInicioSesion.setText("Iniciar Sesion");
        btnInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioSesionActionPerformed(evt);
            }
        });

        btnInicioSesion1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnInicioSesion1.setText("Registrarse");
        btnInicioSesion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioSesion1ActionPerformed(evt);
            }
        });

        textPass.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(246, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(226, 226, 226))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textUser, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(textPass)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(btnInicioSesion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnInicioSesion1)))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInicioSesion)
                .addGap(26, 26, 26)
                .addComponent(btnInicioSesion1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textUserActionPerformed

    private void btnInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioSesionActionPerformed
        try {
            iniciarSesion();
        } catch (PersistenciaException ex) {
            Logger.getLogger(SesionForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnInicioSesionActionPerformed

    private void btnInicioSesion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioSesion1ActionPerformed
        RegistrarPacienteForm registrar = new RegistrarPacienteForm();
        registrar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInicioSesion1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SesionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SesionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SesionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SesionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SesionForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInicioSesion;
    private javax.swing.JButton btnInicioSesion1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField textPass;
    private javax.swing.JTextField textUser;
    // End of variables declaration//GEN-END:variables
}
