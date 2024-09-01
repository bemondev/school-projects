package pdt.cliente.swing.intervencion;

import cfc.servidor.DTOs.EquipoDTO;
import cfc.servidor.DTOs.IntervencionDTO;
import cfc.servidor.DTOs.TipoIntervencionDTO;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.enumerados.MotivosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Clase que representa la ventana de ingreso de intervención.
 */
public class JFIngresoIntervencion extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFIngresoIntervencion instancia;

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    public JFIngresoIntervencion() {
        initComponents();
        cargarComboBoxes();
    }

    // =============================== Métodos ===============================

    /**
     * Método que obtiene la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFIngresoIntervencion getInstancia() {
        if (instancia == null) {
            instancia = new JFIngresoIntervencion();
        }
        instancia.cargarComboBoxes();
        return instancia;
    }

    /**
     * Método que carga los items a los ComboBoxes.
     */
    public void cargarComboBoxes() {
        // Limpia los ComboBoxes
        cbxTipoIntervencion.removeAllItems();
        cbxMotivo.removeAllItems();
        cbxIDInterna.removeAllItems();

        // Carga los items a los ComboBoxes
        List<TipoIntervencionDTO> tiposIntervencion = Conexion.rec_tipoIntervencion.obtenerTodo();
        for (TipoIntervencionDTO tipoIntervencion : tiposIntervencion) {
            if (tipoIntervencion.getEstado() != EstadosEnum.ELIMINADO){
                cbxTipoIntervencion.addItem(tipoIntervencion.getNombre());
            }
        }

        MotivosEnum[] motivos = MotivosEnum.values();
        for (MotivosEnum motivo : motivos) {
            cbxMotivo.addItem(motivo.toString());
        }

        List<EquipoDTO> equipos = Conexion.rec_equipo.obtenerTodo();
        for (EquipoDTO equipo : equipos) {
            if(equipo.getEstado() != EstadosEnum.ELIMINADO){
                cbxIDInterna.addItem(String.valueOf(equipo.getIdInterna()));
            }
        }
    }

    /**
     * Método que limpia los campos de la ventana.
     */
    public void limpiarCampos() {
        dcFecha.setDate(null);
        spinnerHora.setValue(0);
        spinnerMin.setValue(0);
        cbxTipoIntervencion.setSelectedItem("NINGUNO");
        cbxMotivo.setSelectedItem("NINGUNO");
        cbxIDInterna.setSelectedItem("NINGUNO");
        txtObservacion.setText("");
    }

    // =============================== Eventos ===============================


    /**
     * Método que se ejecuta al presionar el botón "Registrar".<br>
     * Registra una nueva intervención en la base de datos.
     *
     * @param evt Evento de acción.
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JOptionPane opcion = new JOptionPane();
        opcion.setMessage("¿Seguro que quieres realizar esta intervención?");
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                //Traemos el tipo de intervención seleccionada de la combobox
                String tipoIntervencionNombre = (String) cbxTipoIntervencion.getSelectedItem();
                if (tipoIntervencionNombre == null) return;

                TipoIntervencionDTO tipo = Conexion.rec_tipoIntervencion.obtenerPorNombre(tipoIntervencionNombre);

                String equipoIdInternaString = (String) cbxIDInterna.getSelectedItem();
                if (equipoIdInternaString == null) return;
                Integer equipoIdInterna = Integer.parseInt(equipoIdInternaString);
                EquipoDTO equipoDTO = Conexion.rec_equipo.obtenerPorIDInterna(equipoIdInterna);

                //Creamos nueva intervenciónDTO y rellenamos con los datos ingresados.
                IntervencionDTO intervencion = new IntervencionDTO();
                LocalDateTime fechaYHora = this.dcFecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                int hora = (int) this.spinnerHora.getValue();
                int minuto = (int) this.spinnerMin.getValue();
                fechaYHora = fechaYHora.withHour(hora);
                fechaYHora = fechaYHora.withMinute(minuto);
                intervencion.setFechaYHora(fechaYHora);
                intervencion.setIdEquipoIntervenido(equipoDTO.getId());
                intervencion.setIdTipoIntervencion(tipo.getId());
                intervencion.setMotivo(MotivosEnum.valueOf((String) cbxMotivo.getSelectedItem()));
                intervencion.setObservacion(txtObservacion.getText());
                //Registramos el DTO
                Conexion.rec_intervencion.registrar(intervencion);
                JOptionPane.showMessageDialog(this, "Intervencion registrada.");
                limpiarCampos();
            } catch (EntityException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en el registro intervención.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se ha cancelado el ingreso.");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Cancelar".<br>
     * Cierra el frame actual, instancia y hace visible un nuevo dashboard con el Usuario.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
        javax.swing.JLabel lblFichaUsuario = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JButton btnRegistrar = new javax.swing.JButton();
        javax.swing.JLabel lblMotivo = new javax.swing.JLabel();
        javax.swing.JLabel lblTipoIntervencion = new javax.swing.JLabel();
        javax.swing.JLabel lblObservaciones = new javax.swing.JLabel();
        cbxTipoIntervencion = new javax.swing.JComboBox<>();
        cbxMotivo = new javax.swing.JComboBox<>();
        javax.swing.JScrollPane scrollPanelObservacioens = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        spinnerHora = new javax.swing.JSpinner();
        javax.swing.JLabel lblHora = new javax.swing.JLabel();
        javax.swing.JLabel lblIdInterna = new javax.swing.JLabel();
        spinnerMin = new javax.swing.JSpinner();
        javax.swing.JLabel lblMin = new javax.swing.JLabel();
        dcFecha = new com.toedter.calendar.JDateChooser();
        cbxIDInterna = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblFichaUsuario.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblFichaUsuario.setText("Ingreso de Intervención");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Fecha");

        btnCancelar.setBackground(new java.awt.Color(132, 132, 132));
        btnCancelar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnRegistrar.setBackground(new java.awt.Color(38, 34, 249));
        btnRegistrar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        lblMotivo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMotivo.setText("Motivo");

        lblTipoIntervencion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTipoIntervencion.setText("Tipo Intervencion");

        lblObservaciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblObservaciones.setText("Observaciones");

        cbxTipoIntervencion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        scrollPanelObservacioens.setViewportView(txtObservacion);

        lblHora.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHora.setText("Hora");

        lblIdInterna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblIdInterna.setText("ID Interna Equipo");

        lblMin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMin.setText("Min");

        cbxIDInterna.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(backgroundLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(lblObservaciones)
                                    .addGap(136, 136, 136))
                                .addGroup(backgroundLayout.createSequentialGroup()
                                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblIdInterna)
                                        .addComponent(lblTipoIntervencion)
                                        .addComponent(lblMotivo))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)))
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxTipoIntervencion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxMotivo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollPanelObservacioens, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblHora)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(lblMin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbxIDInterna, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(14, 14, 14))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(lblFichaUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblFichaUsuario)
                .addGap(22, 22, 22)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(spinnerMin, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMin)
                                    .addComponent(spinnerHora, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHora))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxTipoIntervencion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTipoIntervencion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMotivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdInterna)
                    .addComponent(cbxIDInterna, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblObservaciones)
                    .addComponent(scrollPanelObservacioens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxIDInterna;
    private javax.swing.JComboBox<String> cbxMotivo;
    private javax.swing.JComboBox<String> cbxTipoIntervencion;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JSpinner spinnerHora;
    private javax.swing.JSpinner spinnerMin;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
