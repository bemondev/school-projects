/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pdt.cliente.swing.usuario;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.App;
import pdt.cliente.Conexion;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class JFUsuarioPropio extends javax.swing.JFrame {


    public JFUsuarioPropio() {
        initComponents();
        setCBX();
        setTextos();
    }

    private void setTextos() {
        txtCedula.setText(App.getUsuarioLogueado().getCedula());
        txtEmail.setText(App.getUsuarioLogueado().getEmail());
        txtTelefono.setText(App.getUsuarioLogueado().getTelefono());
        cbxPerfil.setSelectedItem(Conexion.rec_perfil.obtenerPorID(App.getUsuarioLogueado().getIdPerfil()).getNombre());
        dcFecNac.setDate(Date.from(App.getUsuarioLogueado().getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lblUsuario.setText(App.getUsuarioLogueado().getUsername());
    }

    public void setCBX() {

        List<PerfilDTO> perfiles = Conexion.rec_perfil.obtenerTodo();
        if (perfiles.size() != 0) {
            cbxPerfil.removeAllItems();
            for (PerfilDTO per : perfiles) {
                if(per.getEstado() != EstadosEnum.ELIMINADO){
                    cbxPerfil.addItem(per.getNombre());
                }
            }
        } else {
            cbxPerfil.removeAllItems();
            cbxPerfil.addItem("No Items");
        }

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbxPerfil = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        dcFecNac = new com.toedter.calendar.JDateChooser();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtContraseniaActual = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblUsuario.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblUsuario.setText("Usuario.Usuario");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel6.setText("Email");

        jLabel7.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel7.setText("Contraseña");

        txtContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtContrasenia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseniaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel10.setText("Cédula");

        txtCedula.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel11.setText("Teléfono");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel9.setText("Rol");

        cbxPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxPerfil.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel8.setText("Fecha de Nacimiento");

        btnActualizar.setBackground(new java.awt.Color(38, 34, 249));
        btnActualizar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(132, 132, 132));
        btnCancelar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtContraseniaActual.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtContraseniaActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseniaActualActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Unispace", 1, 14)); // NOI18N
        jLabel12.setText("Contraseña Actual");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(dcFecNac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addComponent(txtTelefono)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(txtCedula)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbxPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(283, 283, 283))
                    .addComponent(txtContrasenia)
                    .addComponent(txtEmail)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                    .addComponent(txtContraseniaActual))
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addGap(220, 220, 220))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblUsuario)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcFecNac, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraseniaActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtContraseniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseniaActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        UsuarioDTO usuarioDTO = App.getUsuarioLogueado();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        String contra = txtContrasenia.getText();


        if (contra.matches(regex)) {
            try {
                String email = txtEmail.getText();
                if (!email.contains("@") || !email.contains(".com")) {
                    JOptionPane.showMessageDialog(null, "El email no es válido.");
                    return;
                }

                PerfilDTO perfil = Conexion.rec_perfil.obtenerPorNombre(cbxPerfil.getSelectedItem().toString());
                if (perfil == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione un perfil válido.");
                    return;
                }


                Date fecha = dcFecNac.getDate();
                if (fecha == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fecha de nacimiento válida.");
                    return;
                }
                LocalDate fecNac = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                String telefono = txtTelefono.getText().trim();
                if (telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El teléfono no puede estar vacío.");
                    return;
                }

                if (!telefono.matches("\\d{9,}")) {
                    JOptionPane.showMessageDialog(null, "El teléfono debe contener al menos 9 dígitos y ser solo números.");
                    return;
                }

                usuarioDTO.setCedula(txtCedula.getText());
                usuarioDTO.setEmail(txtEmail.getText());
                usuarioDTO.setFechaNacimiento(fecNac);
                usuarioDTO.setIdPerfil(perfil.getId());
                usuarioDTO.setTelefono(txtTelefono.getText());
                Conexion.rec_usuario.actualizar(usuarioDTO, txtContraseniaActual.getText(), txtContrasenia.getText());
                JOptionPane.showMessageDialog(null, "Usuario " + usuarioDTO.getUsername() + " actualizado correctamente.");
                setVisible(false);
                JFUsuario.getInstancia().setVisible(true);
            } catch (EntityException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en el registro");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "La contraseña no cumple con los requisitos mínimos: \n 8 Caracteres, 1 número, 1 letra");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //BOTÓN CANCELAR - Cierra el frame actual, instancia y hace visible un nuevo dashboard con el Usuario
        setVisible(false);
        JFUsuario.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtContraseniaActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseniaActualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseniaActualActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cbxPerfil;
    private com.toedter.calendar.JDateChooser dcFecNac;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtContrasenia;
    private javax.swing.JTextField txtContraseniaActual;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
