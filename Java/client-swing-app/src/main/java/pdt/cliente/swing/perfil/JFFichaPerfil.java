package pdt.cliente.swing.perfil;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.enumerados.PermisosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la ventana de ficha de perfil.
 */
public class JFFichaPerfil extends javax.swing.JFrame {

    // ======================== Atributos ========================
    /**
     * Perfil pasado por parámetro. (Para modificar)
     */
    private final PerfilDTO perfilPasado;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    public JFFichaPerfil(PerfilDTO perfil) {
        perfilPasado = perfil;
        initComponents();
        rellenarCampos();
    }

    // =============================== Métodos ===============================

    /**
     * Método que rellena los campos con los datos del perfil pasado por parámetro.
     */
    private void rellenarCampos() {
        txtNombre.setText(perfilPasado.getNombre());
        List<PermisosEnum> permisos = perfilPasado.getPermisos();
        if (permisos.contains(PermisosEnum.EQUIPOS)) cbxEquipos.setSelected(true);
        if (permisos.contains(PermisosEnum.PERFILES)) cbxPerfiles.setSelected(true);
        if (permisos.contains(PermisosEnum.INTERVENCIONES)) cbxIntervenciones.setSelected(true);
        if (permisos.contains(PermisosEnum.UBICACIONES)) cbxUbicaciones.setSelected(true);
        if (permisos.contains(PermisosEnum.TIPOS_EQUIPOS)) cbxTiposDeEquipos.setSelected(true);
        if (permisos.contains(PermisosEnum.USUARIOS)) cbxUsuarios.setSelected(true);
        if (permisos.contains(PermisosEnum.TIPOS_INTERVENCIONES)) cbxTiposDeIntervenciones.setSelected(true);
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón "Cancelar".<br>
     * Oculta la ventana actual y muestra el Dashboard.
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Actualizar".<br>
     * Actualiza el perfil con los datos ingresados.
     * @param evt Evento de acción.
     */
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        List<PermisosEnum> permisos = new ArrayList<>();
        if (cbxEquipos.isSelected())
            permisos.add(PermisosEnum.EQUIPOS);
        if (cbxPerfiles.isSelected())
            permisos.add(PermisosEnum.PERFILES);
        if (cbxIntervenciones.isSelected())
            permisos.add(PermisosEnum.INTERVENCIONES);
        if (cbxUbicaciones.isSelected())
            permisos.add(PermisosEnum.UBICACIONES);
        if (cbxUsuarios.isSelected())
            permisos.add(PermisosEnum.USUARIOS);
        if (cbxTiposDeEquipos.isSelected())
            permisos.add(PermisosEnum.TIPOS_EQUIPOS);
        if (cbxTiposDeIntervenciones.isSelected())
            permisos.add(PermisosEnum.TIPOS_INTERVENCIONES);
        if (cbxMarcas.isSelected())
            permisos.add(PermisosEnum.MARCAS);
        if (cbxModelos.isSelected())
            permisos.add(PermisosEnum.MODELOS);
        if (cbxPaises.isSelected())
            permisos.add(PermisosEnum.PAISES);

        if (permisos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error en la modificación. \n Ingrese mínimo un permiso.");
        } else {

            try {
                PerfilDTO perfilDTO = new PerfilDTO();
                perfilDTO.setId(perfilPasado.getId());
                perfilDTO.setEstado(perfilPasado.getEstado());
                perfilDTO.setNombre(txtNombre.getText());
                perfilDTO.setPermisos(permisos);

                Conexion.rec_perfil.actualizar(perfilDTO);
                JOptionPane.showMessageDialog(this, "Perfil modificado.");
            } catch (EntityException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(this, "Error en la modificación.");
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

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
        javax.swing.JLabel lblCartel = new javax.swing.JLabel();
        javax.swing.JButton btnActualizar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JPanel panelPermisos = new javax.swing.JPanel();
        cbxUsuarios = new javax.swing.JCheckBox();
        cbxPerfiles = new javax.swing.JCheckBox();
        cbxEquipos = new javax.swing.JCheckBox();
        cbxUbicaciones = new javax.swing.JCheckBox();
        cbxIntervenciones = new javax.swing.JCheckBox();
        cbxTiposDeEquipos = new javax.swing.JCheckBox();
        cbxTiposDeIntervenciones = new javax.swing.JCheckBox();
        cbxMarcas = new javax.swing.JCheckBox();
        cbxModelos = new javax.swing.JCheckBox();
        cbxPaises = new javax.swing.JCheckBox();
        javax.swing.JLabel lblPermisos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(217, 217, 217));

        lblCartel.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblCartel.setText("Ficha Perfil");

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

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre Perfil");

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cbxUsuarios.setText("Usuarios");

        cbxPerfiles.setText("Perfiles");

        cbxEquipos.setText("Equipos");

        cbxUbicaciones.setText("Ubicaciones");

        cbxIntervenciones.setText("Intervenciones");

        cbxTiposDeEquipos.setText("Tipos de Equipos");

        cbxTiposDeIntervenciones.setText("Tipos de Interv.");

        cbxMarcas.setText("Marcas");

        cbxModelos.setText("Modelos");

        cbxPaises.setText("Paises");

        javax.swing.GroupLayout panelPermisosLayout = new javax.swing.GroupLayout(panelPermisos);
        panelPermisos.setLayout(panelPermisosLayout);
        panelPermisosLayout.setHorizontalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPermisosLayout.createSequentialGroup()
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxUsuarios)
                            .addComponent(cbxPerfiles)
                            .addComponent(cbxModelos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxIntervenciones)
                            .addComponent(cbxTiposDeIntervenciones)
                            .addComponent(cbxMarcas))
                        .addGap(82, 82, 82)
                        .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxUbicaciones)
                            .addComponent(cbxTiposDeEquipos)
                            .addComponent(cbxEquipos)))
                    .addGroup(panelPermisosLayout.createSequentialGroup()
                        .addComponent(cbxPaises)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPermisosLayout.setVerticalGroup(
            panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPermisosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxUsuarios)
                    .addComponent(cbxEquipos)
                    .addComponent(cbxIntervenciones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTiposDeEquipos)
                    .addComponent(cbxPerfiles)
                    .addComponent(cbxTiposDeIntervenciones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPermisosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxUbicaciones)
                    .addComponent(cbxMarcas)
                    .addComponent(cbxModelos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPaises)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblPermisos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPermisos.setText("Permisos");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(lblPermisos)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelPermisos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(lblCartel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(lblCartel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPermisos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbxEquipos;
    private javax.swing.JCheckBox cbxIntervenciones;
    private javax.swing.JCheckBox cbxMarcas;
    private javax.swing.JCheckBox cbxModelos;
    private javax.swing.JCheckBox cbxPaises;
    private javax.swing.JCheckBox cbxPerfiles;
    private javax.swing.JCheckBox cbxTiposDeEquipos;
    private javax.swing.JCheckBox cbxTiposDeIntervenciones;
    private javax.swing.JCheckBox cbxUbicaciones;
    private javax.swing.JCheckBox cbxUsuarios;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}