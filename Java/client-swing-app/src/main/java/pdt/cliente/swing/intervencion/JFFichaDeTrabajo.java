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
 * Clase que representa la ventana de ficha de trabajo.
 */
public class JFFichaDeTrabajo extends javax.swing.JFrame {

    // ======================== Atributos ========================
    /**
     * Intervención pasada por parámetro. (Para modificar)
     */
    private IntervencionDTO intervencionPasada;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    public JFFichaDeTrabajo(IntervencionDTO intervencion) {
        intervencionPasada = intervencion;
        initComponents();
        rellenar();
        cargarComboBox();
    }

    // =============================== Métodos ===============================

    /**
     * Método para rellenar los campos de la ficha de trabajo.
     */
    public void rellenar() {
        EquipoDTO equipo = Conexion.rec_equipo.obtenerPorID(intervencionPasada.getIdEquipoIntervenido());
        txtIDEquipo.setText(String.valueOf(equipo.getIdInterna()));
    }

    /**
     * Método para cargar los ComboBox.
     */
    public void cargarComboBox() {
        // Limpia los ComboBox
        cbxTipoIntervencion.removeAllItems();
        cbxMotivo.removeAllItems();

        // Agrega los items al ComboBox de Tipo de Intervención
        cbxTipoIntervencion.addItem("NINGUNO");
        List<TipoIntervencionDTO> tipos = Conexion.rec_tipoIntervencion.obtenerTodo();
        for (TipoIntervencionDTO tip : tipos) {
            if (tip.getEstado() != EstadosEnum.ELIMINADO){
                cbxTipoIntervencion.addItem(tip.getNombre());
            }
        }

        // Agrega los items al ComboBox de Motivo
        cbxMotivo.addItem("NINGUNO");
        for (MotivosEnum motivo : MotivosEnum.values()) {
            cbxMotivo.addItem(motivo.name());
        }
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón "Cancelar".<br>
     * Cierra el frame actual y abre el dashboard.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Trabajar".<br>
     * Modifica la intervención en la base de datos.
     *
     * @param evt Evento de acción.
     */
    private void btnTrabajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrabajarActionPerformed
        JOptionPane opcion = new JOptionPane();
        opcion.setMessage("¿Seguro que quieres trabajar esta intervención?");
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar trabajo?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                //Traemos el tipo de intervención seleccionada de la combobox
                TipoIntervencionDTO tipo = Conexion.rec_tipoIntervencion.obtenerPorNombre(cbxTipoIntervencion.getSelectedItem().toString());
                //Creamos nueva intervenciónDTO y rellenamos con los datos ingresados.
                IntervencionDTO intervencionNueva = new IntervencionDTO();
                LocalDateTime fechaYHora = this.dcFecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                int hora = (int) this.spinnerHora.getValue();
                int minuto = (int) this.spinnerMin.getValue();
                fechaYHora = fechaYHora.withHour(hora);
                fechaYHora = fechaYHora.withMinute(minuto);
                intervencionNueva.setFechaYHora(fechaYHora);
                intervencionNueva.setIdEquipoIntervenido(intervencionPasada.getIdEquipoIntervenido());
                intervencionNueva.setIdTipoIntervencion(tipo.getId());
                intervencionNueva.setMotivo(MotivosEnum.valueOf((String) cbxMotivo.getSelectedItem()));
                intervencionNueva.setObservacion(txtObservacion.getText());
                //Registramos el DTO
                Conexion.rec_intervencion.registrar(intervencionNueva);
                JOptionPane.showMessageDialog(this, "Intervención trabajada exitosamente");
            } catch (EntityException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se ha cancelado el trabajo.");
        }

    }//GEN-LAST:event_btnTrabajarActionPerformed


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
        javax.swing.JLabel lblFecha = new javax.swing.JLabel();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JButton btnTrabajar = new javax.swing.JButton();
        javax.swing.JLabel lblMotivo = new javax.swing.JLabel();
        javax.swing.JLabel lblTipoIntervencion = new javax.swing.JLabel();
        javax.swing.JLabel lblObservaciones = new javax.swing.JLabel();
        cbxTipoIntervencion = new javax.swing.JComboBox<>();
        cbxMotivo = new javax.swing.JComboBox<>();
        javax.swing.JScrollPane scrollPanelObservaciones = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        spinnerHora = new javax.swing.JSpinner();
        javax.swing.JLabel lblHora = new javax.swing.JLabel();
        txtIDEquipo = new javax.swing.JTextField();
        javax.swing.JLabel lblIdInternaEquipo = new javax.swing.JLabel();
        spinnerMin = new javax.swing.JSpinner();
        javax.swing.JLabel lblMin = new javax.swing.JLabel();
        dcFecha = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        lblFichaUsuario.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblFichaUsuario.setText("Ficha de Trabajo");

        lblFecha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFecha.setText("Fecha");

        btnCancelar.setBackground(new java.awt.Color(132, 132, 132));
        btnCancelar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnTrabajar.setBackground(new java.awt.Color(38, 34, 249));
        btnTrabajar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnTrabajar.setForeground(new java.awt.Color(255, 255, 255));
        btnTrabajar.setText("Trabajar");
        btnTrabajar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrabajarActionPerformed(evt);
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
        scrollPanelObservaciones.setViewportView(txtObservacion);

        lblHora.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHora.setText("Hora");

        txtIDEquipo.setEditable(false);
        txtIDEquipo.setText("0");

        lblIdInternaEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblIdInternaEquipo.setText("ID Interna Equipo");

        lblMin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMin.setText("Min");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTrabajar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(lblFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblObservaciones)
                                .addGap(136, 136, 136))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIdInternaEquipo)
                                    .addComponent(lblTipoIntervencion)
                                    .addComponent(lblMotivo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(scrollPanelObservaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxTipoIntervencion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIDEquipo)
                                .addComponent(cbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblHora)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(lblMin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(14, 14, 14))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(lblFichaUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblFichaUsuario)
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFecha)
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
                    .addComponent(lblIdInternaEquipo)
                    .addComponent(txtIDEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblObservaciones)
                    .addComponent(scrollPanelObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTrabajar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxMotivo;
    private javax.swing.JComboBox<String> cbxTipoIntervencion;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JSpinner spinnerHora;
    private javax.swing.JSpinner spinnerMin;
    private javax.swing.JTextField txtIDEquipo;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
