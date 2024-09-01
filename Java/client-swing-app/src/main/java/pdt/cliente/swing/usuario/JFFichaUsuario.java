package pdt.cliente.swing.usuario;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa la ventana de Ficha de Usuario.
 */
public class JFFichaUsuario extends javax.swing.JFrame {

    // ======================== Atributos ========================

    private final UsuarioDTO USUARIO_PASADO;


    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    public JFFichaUsuario(UsuarioDTO usuario) {
        USUARIO_PASADO = usuario;
        initComponents();
        cargarComboBoxes();
        rellenarCampos();
    }

    // =============================== Métodos ===============================

    /**
     * Método que rellena los campos con la información del usuario pasado.
     */
    public void rellenarCampos() {
        PerfilDTO perfil = Conexion.rec_perfil.obtenerPorID(this.USUARIO_PASADO.getIdPerfil());
        this.txtNombre.setText(this.USUARIO_PASADO.getNombre());
        this.txtApellido.setText(this.USUARIO_PASADO.getApellido());
        this.txtUsuario.setText(this.USUARIO_PASADO.getUsername());
        this.txtEmail.setText(this.USUARIO_PASADO.getEmail());
        String fechamal = this.USUARIO_PASADO.getFechaNacimiento().toString();
        this.cbxPerfil.setSelectedItem(perfil.getNombre());
        this.cbxEstado.setSelectedItem(this.USUARIO_PASADO.getEstado().toString());

        SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Convertir la fecha de texto a un objeto Date
            Date fecha = formatoOriginal.parse(fechamal);

            // Crear un nuevo objeto SimpleDateFormat para el formato de salida (DD/MM/YYYY)
            SimpleDateFormat formatoNuevo = new SimpleDateFormat("dd/MM/yyyy");

            // Convertir la fecha al nuevo formato (DD/MM/YYYY)
            String fechaConvertida = formatoNuevo.format(fecha);

            this.txtFecNac.setText(fechaConvertida);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Método que carga los ComboBoxes con la información necesaria.
     */
    public void cargarComboBoxes() {
        // Limpiar los ComboBoxes
        this.cbxPerfil.removeAllItems();
        this.cbxEstado.removeAllItems();

        // Agrega los perfiles al ComboBox
        this.cbxPerfil.addItem("NINGUNO");
        List<PerfilDTO> perfiles = Conexion.rec_perfil.obtenerTodo();
        for (PerfilDTO perfil : perfiles) {
            if(perfil.getEstado() != EstadosEnum.ELIMINADO){
                this.cbxPerfil.addItem(perfil.getNombre());
            }
        }

        // Agrega los estados al ComboBox
        this.cbxEstado.addItem("NINGUNO");
        this.cbxEstado.addItem(String.valueOf(EstadosEnum.ELIMINADO));
        this.cbxEstado.addItem(String.valueOf(EstadosEnum.VALIDADO));
        this.cbxEstado.addItem(String.valueOf(EstadosEnum.SIN_VALIDAR));
    }

//    private List<Permiso> leerChbx(){
//        //Mapa de enum junto con la asignación de Checkboxes y opciones del Enum
//        Map<JCheckBox, Permiso> checkBoxToEnum = new EnumMap<>(JCheckBox.class);
//        checkBoxToEnum.put(cbxUsuarios, Permiso.USUARIOS);
//        checkBoxToEnum.put(cbxEquipos, Permiso.EQUIPOS);
//        checkBoxToEnum.put(cbxPerfiles, Permiso.PERFILES);
//        checkBoxToEnum.put(cbxIntervenciones, Permiso.INTERVENCIONES);
//        checkBoxToEnum.put(cbxUbicaciones, Permiso.UBICACIONES);
//        checkBoxToEnum.put(cbxTiposDeEquipos, Permiso.TIPOS_EQUIPOS);
//        checkBoxToEnum.put(cbxTiposDeIntervenciones, Permiso.TIPOS_INTERVENCIONES);
//        List<Permiso> permisosList = new ArrayList<>();
//
//        // Iterar sobre los checkboxes para verificar cuáles están seleccionados
//        for (Map.Entry<JCheckBox, Permiso> entry : checkBoxToEnum.entrySet()) {
//            JCheckBox checkBox = entry.getKey();
//            Permiso opcionEnum = entry.getValue();
//            // Verificar si el checkbox está seleccionado
//            if (checkBox.isSelected()) {
//                // Añadir cada permiso a la lista de Permisos.
//                permisosList.add(opcionEnum);
//            }
//        }
//        return permisosList;
//    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón de cancelar.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed


    /**
     * Método que se ejecuta al presionar el botón de Actualizar.
     *
     * @param evt Evento de acción.
     */
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try{
            PerfilDTO perfil = Conexion.rec_perfil.obtenerPorNombre(cbxPerfil.getSelectedItem().toString());
            UsuarioDTO dto = Conexion.rec_usuario.obtenerPorID(USUARIO_PASADO.getId());
            JOptionPane opcion = new JOptionPane();
            opcion.setMessage("¿Seguro que quieres modificar este usuario?");
            opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
            int respuesta = opcion.showConfirmDialog(this, "Confirmar modificación?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                dto.setIdPerfil(perfil.getId());
                dto.setEstado(EstadosEnum.valueOf((String) cbxEstado.getSelectedItem()));
                Conexion.rec_usuario.actualizarAdmin(dto);
            } else {
                JOptionPane.showMessageDialog(this, "Se ha cancelado la acción");
            }
        }catch (EntityException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error al actualizar el usuario");
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
        txtEmail = new javax.swing.JTextField();
        txtFecNac = new javax.swing.JTextField();
        javax.swing.JLabel lblUsuario = new javax.swing.JLabel();
        javax.swing.JLabel lblEmail = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        javax.swing.JLabel lblFecNac = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblPerfil = new javax.swing.JLabel();
        javax.swing.JLabel lblApellido = new javax.swing.JLabel();
        javax.swing.JLabel lblEstado = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        cbxPerfil = new javax.swing.JComboBox<>();
        txtUsuario = new javax.swing.JTextField();
        cbxEstado = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(217, 217, 217));

        lblCartel.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblCartel.setText("Ficha Usuario");

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

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtFecNac.setEditable(false);
        txtFecNac.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuario.setText("Usuario");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmail.setText("Email");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre");

        lblFecNac.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFecNac.setText("Fecha de Nacimiento");

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPerfil.setText("Perfil");

        lblApellido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblApellido.setText("Apellido");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEstado.setText("Estado");

        txtApellido.setEditable(false);
        txtApellido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cbxPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtUsuario.setEditable(false);
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cbxEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecNac)
                            .addComponent(lblPerfil)
                            .addComponent(lblEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecNac, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(39, 39, 39))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(lblCartel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNombre)
                            .addComponent(lblApellido)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.LEADING))
                        .addComponent(lblEmail))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtEmail)
                        .addComponent(txtApellido)
                        .addComponent(txtNombre)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                    .addGap(38, 38, 38)))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblCartel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecNac)
                    .addComponent(txtFecNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPerfil)
                    .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNombre))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblApellido))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUsuario))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmail))
                    .addContainerGap(196, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxPerfil;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFecNac;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}