package pdt.cliente.swing.login;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa la ventana de registro de usuario.
 */
public class JFRegister extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFRegister instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFRegister() {
        this.initComponents();
        this.cargarComboBoxes();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     */
    public static JFRegister getInstancia() {
        if (instancia == null) {
            instancia = new JFRegister();
        }
        instancia.cargarComboBoxes();
        return instancia;
    }

    /**
     * Método que carga los perfiles en el ComboBox.
     */
    public void cargarComboBoxes() {
        // Limpiamos los items del ComboBox
        this.cbxPerfil.removeAllItems();

        List<PerfilDTO> perfiles = Conexion.rec_perfil.obtenerTodo();
        if (!perfiles.isEmpty()) {
            for (PerfilDTO perfil : perfiles) {
                if (perfil.getEstado() != EstadosEnum.ELIMINADO) {
                    this.cbxPerfil.addItem(perfil.getNombre());
                }
            }
        } else {
            this.cbxPerfil.addItem("No Items");
        }
    }

    /**
     * Limpia los campos.
     */
    public void limpiarCampos() {
        this.txtNombre.setText("");
        this.txtApellido.setText("");
        this.txtEmail.setText("");
        this.txtContrasenia.setText("");
        this.dateFecNacimiento.setDate(null);
        this.txtCedula.setText("");
        this.txtTelefono.setText("");
    }

    // =============================== Eventos ===============================

    /**
     * Evento al presionar el botón "Atrás".<br>
     * Cierra la ventana actual y abre la ventana de inicio de sesión.
     *
     * @param evt Evento de clic.
     */
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        this.dispose();
        JFLogin.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnAtrasActionPerformed

    /**
     * Evento al presionar el botón "Crear Cuenta".<br>
     * Registra un nuevo usuario en la base de datos.
     *
     * @param evt Evento de clic.
     */
    private void btnCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearCuentaActionPerformed
        // Definimos la expresión regular para validar la contraseña
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        String contra = new String(txtContrasenia.getPassword());
        if (contra.matches(regex)) {
            try {
                String email = txtEmail.getText();
                if (!email.contains("@") || !email.contains(".com")) {
                    JOptionPane.showMessageDialog(this, "El email no es válido.");
                    return;
                }

                PerfilDTO perfil = Conexion.rec_perfil.obtenerPorNombre(cbxPerfil.getSelectedItem().toString());
                if (perfil == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione un perfil válido.");
                    return;
                }

                String nombre = txtNombre.getText().trim();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                    return;
                }

                String apellido = txtApellido.getText().trim();
                if (apellido.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El apellido no puede estar vacío.");
                    return;
                }

                Date fecha = dateFecNacimiento.getDate();
                if (fecha == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione una fecha de nacimiento válida.");
                    return;
                }
                LocalDate fecNac = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                String telefono = txtTelefono.getText().trim();
                if (telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El teléfono no puede estar vacío.");
                    return;
                }

                if (!telefono.matches("\\d{9,}")) {
                    JOptionPane.showMessageDialog(this, "El teléfono debe contener al menos 9 dígitos y ser solo números.");
                    return;
                }

                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setCedula(txtCedula.getText());
                usuario.setEmail(email);
                usuario.setFechaNacimiento(fecNac);
                usuario.setIdPerfil(perfil.getId());
                usuario.setEstado(EstadosEnum.SIN_VALIDAR);
                usuario.setTelefono(telefono);
                usuario = Conexion.rec_usuario.registrar(usuario, contra);
                JOptionPane.showMessageDialog(this, "Usuario " + usuario.getUsername() + " creado correctamente.\n A la espera del alta de un administrador.");
            } catch (EntityException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                System.out.println(e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en el registro");
                System.out.println(e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "La contraseña no cumple con los requisitos mínimos: \n 8 Caracteres, un número, una letra mayuscula, una minuscula y un caracter especial(@$!%*?&)");
        }
    }//GEN-LAST:event_btnCrearCuentaActionPerformed


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
        javax.swing.JLabel lblRegister = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        javax.swing.JLabel lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        javax.swing.JLabel lblContrasenia = new javax.swing.JLabel();
        javax.swing.JLabel lblFecNacimiento = new javax.swing.JLabel();
        javax.swing.JLabel lblRol = new javax.swing.JLabel();
        cbxPerfil = new javax.swing.JComboBox<>();
        javax.swing.JButton btnCrearCuenta = new javax.swing.JButton();
        javax.swing.JButton btnAtras = new javax.swing.JButton();
        txtCedula = new javax.swing.JTextField();
        javax.swing.JLabel lblCedula = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        javax.swing.JLabel lblTelefono = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JPasswordField();
        dateFecNacimiento = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoMeditech.png")));

        panelCentral.setBackground(new java.awt.Color(217, 217, 217));

        lblRegister.setFont(new java.awt.Font("Unispace", 0, 30)); // NOI18N
        lblRegister.setText("Registro de Usuario");

        lblNombre.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblNombre.setText("Nombre/s");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblApellido.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblApellido.setText("Apellido/s");

        txtApellido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblEmail.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblEmail.setText("Email");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblContrasenia.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblContrasenia.setText("Contraseña");

        lblFecNacimiento.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblFecNacimiento.setText("Fecha de Nacimiento");

        lblRol.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblRol.setText("Rol");

        cbxPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        btnCrearCuenta.setBackground(new java.awt.Color(38, 34, 249));
        btnCrearCuenta.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnCrearCuenta.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearCuenta.setText("Crear Cuenta");
        btnCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearCuentaActionPerformed(evt);
            }
        });

        btnAtras.setBackground(new java.awt.Color(132, 132, 132));
        btnAtras.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        txtCedula.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblCedula.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblCedula.setText("Cédula");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTelefono.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        lblTelefono.setText("Teléfono");

        txtContrasenia.setMinimumSize(new java.awt.Dimension(64, 31));

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
                panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelCentralLayout.createSequentialGroup()
                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelCentralLayout.createSequentialGroup()
                                                .addGap(203, 203, 203)
                                                .addComponent(lblRegister))
                                        .addGroup(panelCentralLayout.createSequentialGroup()
                                                .addGap(70, 70, 70)
                                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblTelefono)
                                                        .addComponent(txtTelefono)
                                                        .addComponent(lblNombre)
                                                        .addComponent(txtNombre)
                                                        .addComponent(lblApellido)
                                                        .addComponent(txtApellido)
                                                        .addComponent(lblEmail)
                                                        .addComponent(txtEmail)
                                                        .addComponent(lblContrasenia)
                                                        .addComponent(lblFecNacimiento)
                                                        .addComponent(lblCedula)
                                                        .addComponent(txtCedula)
                                                        .addComponent(lblRol)
                                                        .addComponent(txtContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                                                        .addGroup(panelCentralLayout.createSequentialGroup()
                                                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(btnAtras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(cbxPerfil, 0, 225, Short.MAX_VALUE))
                                                                .addGap(49, 49, 49)
                                                                .addComponent(btnCrearCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(dateFecNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(97, Short.MAX_VALUE))
        );
        panelCentralLayout.setVerticalGroup(
                panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelCentralLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(lblRegister)
                                .addGap(50, 50, 50)
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblApellido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblContrasenia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFecNacimiento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateFecNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCedula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTelefono)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRol)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnCrearCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addContainerGap(159, Short.MAX_VALUE)
                                .addComponent(lblLogo)
                                .addGap(134, 134, 134)
                                .addComponent(panelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGap(176, 176, 176)
                                                .addComponent(lblLogo))
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(panelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxPerfil;
    private com.toedter.calendar.JDateChooser dateFecNacimiento;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}