package pdt.cliente.swing.login;

import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.enumerados.LoginResultEnum;
import pdt.cliente.App;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Clase que representa la ventana de inicio de sesión.
 */
public class JFLogin extends javax.swing.JFrame {

    // ============================== Atributos ==============================
    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFLogin instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFLogin() {
        this.initComponents();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFLogin getInstancia() {
        if (instancia == null) {
            instancia = new JFLogin();
        }
        return instancia;
    }

    /**
     * Método que limpia los campos de texto de la ventana.<br>
     * Sirve para cuando se cierra sesión y se vuelve a abrir la ventana.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
    }

    // =============================== Eventos ===============================

    /**
     * Evento al presionar una tecla en el campo de texto de "Usuario".<br>
     * Pasa el foco al campo de texto de "Contraseña" si se presiona "Enter".
     *
     * @param evt Evento de teclado.
     */
    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        // Si se presiona la tecla "Enter"
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Se pasa el foco al campo de texto de "Contraseña"
            this.txtContrasenia.requestFocusInWindow();
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    /**
     * Evento al presionar una tecla en el campo de texto de "Contraseña".<br>
     * Presiona el botón "Ingresar" si se presiona "Enter".
     *
     * @param evt Evento de teclado.
     */
    private void txtContraseniaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseniaKeyPressed
        // Si se presiona la tecla "Enter"
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Se pasa el foco al botón "Ingresar" y se ejecuta su evento
            this.btnLogin.requestFocusInWindow();
            this.btnLogin.doClick();
        }

    }//GEN-LAST:event_txtContraseniaKeyPressed

    /**
     * Evento al presionar el botón "Ingresar".<br>
     * Verifica las credenciales ingresadas y muestra un mensaje correspondiente.<br>
     * Si las credenciales son correctas, se inicia la ventana de Dashboard.
     *
     * @param evt Evento de clic.
     */
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        // Se obtienen los datos ingresados en los campos de texto
        String usuario = txtUsuario.getText();
        String contrasenia = new String(txtContrasenia.getPassword());

        // Si alguno de los campos está vacío, se muestra un mensaje solicitando completar ambos campos
        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos.");
            return;
        }

        // Se verifica la credencial ingresada y se obtiene el resultado
        LoginResultEnum result = Conexion.rec_login.verificarCredenciales(usuario, contrasenia);

        // Se muestra un mensaje correspondiente al resultado obtenido
        // y se ejecuta la acción correspondiente si las credenciales son correctas
        switch (result) {
            case INICIO_CORRECTO:
                // Se obtiene el usuario y se guarda en la aplicación
                UsuarioDTO usuarioDTO = Conexion.rec_usuario.obtenerPorUsername(usuario);
                App.setUsuarioLogueado(usuarioDTO);

                // Se muestra un mensaje de bienvenida y se inicia la ventana de Dashboard
                JOptionPane.showMessageDialog(this, "Bienvenido " + usuario);
                this.setVisible(false);
                JFDashboard jfDashboard = JFDashboard.getInstancia();
                jfDashboard.setUsername();
                jfDashboard.comprobarPermisos();
                JFDashboard.getInstancia().setVisible(true);
                break;
            case CREDENCIALES_INCORRECTAS:
                JOptionPane.showMessageDialog(this, "Crédenciales incorrectas.");
                break;
            case USUARIO_NO_VALIDADO:
                JOptionPane.showMessageDialog(this, "Usuario no validado.\nEspera a la validacion de un administrador.");
                break;
            case USUARIO_BAJA:
                JOptionPane.showMessageDialog(this, "Usuario eliminado.\nPonerse en contacto con un administrador.");
                break;
            case USUARIO_INEXISTENTE:
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error: Estado fuera de rango.");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void lblRegistrarseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistrarseMouseClicked
        this.setVisible(false);
        JFRegister jfRegister = JFRegister.getInstancia();
        jfRegister.limpiarCampos();
        jfRegister.setVisible(true);
    }//GEN-LAST:event_lblRegistrarseMouseClicked

    // ================================= UI ==================================

    /*
     * Método controlado por el editor de formularios, encargado
     * de inicializar y estructurar los elementos del formulario.
     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("all")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel background = new javax.swing.JPanel();
        javax.swing.JLabel lblLogo = new javax.swing.JLabel();
        javax.swing.JPanel panelCentral = new javax.swing.JPanel();
        javax.swing.JLabel lblLogin = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        javax.swing.JLabel lblUsuario = new javax.swing.JLabel();
        javax.swing.JLabel lblContrasenia = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        javax.swing.JLabel lblNuevoUsuario = new javax.swing.JLabel();
        javax.swing.JLabel lblRegistrarse = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoMeditech.png")));

        panelCentral.setBackground(new java.awt.Color(217, 217, 217));

        lblLogin.setFont(new java.awt.Font("Unispace", 0, 30)); // NOI18N
        lblLogin.setText("Inicio de sesión de Usuario");

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblUsuario.setText("Usuario");

        lblContrasenia.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblContrasenia.setText("Contraseña");

        btnLogin.setBackground(new java.awt.Color(38, 34, 249));
        btnLogin.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Iniciar Sesión");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblNuevoUsuario.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblNuevoUsuario.setText("¿Nuevo usuario?");

        lblRegistrarse.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblRegistrarse.setForeground(new java.awt.Color(49, 131, 255));
        lblRegistrarse.setText("Regístrese aquí");
        lblRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegistrarseMouseClicked(evt);
            }
        });

        txtContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContraseniaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
                panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelCentralLayout.createSequentialGroup()
                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelCentralLayout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addComponent(lblLogin))
                                        .addGroup(panelCentralLayout.createSequentialGroup()
                                                .addGap(174, 174, 174)
                                                .addComponent(lblNuevoUsuario)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblRegistrarse))
                                        .addGroup(panelCentralLayout.createSequentialGroup()
                                                .addGap(144, 144, 144)
                                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(79, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCentralLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtContrasenia)
                                        .addComponent(lblContrasenia, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE))
                                .addGap(66, 66, 66))
        );
        panelCentralLayout.setVerticalGroup(
                panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelCentralLayout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addComponent(lblLogin)
                                .addGap(24, 24, 24)
                                .addComponent(lblUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblContrasenia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNuevoUsuario)
                                        .addComponent(lblRegistrarse))
                                .addContainerGap(152, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                                .addComponent(panelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGap(37, 37, 37)
                                                .addComponent(panelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGap(170, 170, 170)
                                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}