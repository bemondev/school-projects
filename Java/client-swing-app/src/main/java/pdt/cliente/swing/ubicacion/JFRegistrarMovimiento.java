package pdt.cliente.swing.ubicacion;

import cfc.servidor.DTOs.EquipoDTO;
import cfc.servidor.DTOs.MovimientoDTO;
import cfc.servidor.DTOs.UbicacionDTO;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.entidades.Ubicacion;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.App;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que representa la ventana de registro de movimientos.
 */
public class JFRegistrarMovimiento extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFRegistrarMovimiento instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFRegistrarMovimiento() {
        this.initComponents();
        this.cargarComboBoxes();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFRegistrarMovimiento getInstancia() {
        if (instancia == null) {
            instancia = new JFRegistrarMovimiento();
        }
        instancia.cargarComboBoxes();
        return instancia;
    }

    /**
     * Método que carga los datos de los ComboBoxes (Ubicaciones y Equipos).
     */
    public void cargarComboBoxes() {
        // Limpia los ComboBoxes
        this.cbxEuqipo.removeAllItems();
        this.cbxUbicacion.removeAllItems();

        // Carga los equipos
        for (EquipoDTO equipo : Conexion.rec_equipo.obtenerTodo()) {
            if (equipo.getEstado() != EstadosEnum.ELIMINADO) {
                this.cbxEuqipo.addItem(equipo.getNombre());
            }
        }

        // Carga las ubicaciones
        for (UbicacionDTO ubicacion : Conexion.rec_ubicacion.obtenerTodo()) {
            if(ubicacion.getEstado() != EstadosEnum.ELIMINADO){
                this.cbxUbicacion.addItem(ubicacion.getNombre());
            }
        }
    }


    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón Cancelar.<br>
     * Oculta la ventana actual y muestra el Dashboard.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false); // Oculta la ventana actual
        JFDashboard.getInstancia().setVisible(true); // Muestra el Dashboard
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón Registrar.<br>
     * Registra un nuevo movimiento en la base de datos.
     *
     * @param evt Evento de acción.
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JOptionPane opcion = new JOptionPane();
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                // Obtiene el usuario actual
                UsuarioDTO usuarioActual = App.getUsuarioLogueado();

                // Obtiene la fecha y hora
                LocalDateTime fechaHoraSalida = this.jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                int hora = (int) this.spinHora.getValue();
                int minuto = (int) this.spinMinuto.getValue();
                fechaHoraSalida = fechaHoraSalida.withHour(hora);
                fechaHoraSalida = fechaHoraSalida.withMinute(minuto);
                // Crea un nuevo movimiento con los datos ingresados y el usuario actual
                MovimientoDTO movimiento = new MovimientoDTO();
                movimiento.setEstado(EstadosEnum.ACTIVO);
                movimiento.setComentario(this.txtObservaciones.getText());
                EquipoDTO equipoDTO = Conexion.rec_equipo.obtenerPorNombre((String) this.cbxEuqipo.getSelectedItem());
                movimiento.setIdEquipo(equipoDTO.getId());
                UbicacionDTO ubicacionDTO = Conexion.rec_ubicacion.obtenerPorNombre((String) this.cbxUbicacion.getSelectedItem());
                movimiento.setIdUbicacion(ubicacionDTO.getId());
                movimiento.setUsername(usuarioActual.getUsername());
                movimiento.setFechaDelRegistro(fechaHoraSalida);

                // Registra el movimiento
                Conexion.rec_movimiento.registrar(movimiento);

                // Muestra un mensaje de éxito
                JOptionPane.showMessageDialog(this, "Movimiento registrado correctamente.");
            } catch (EntityException ex) {
                // Muestra un mensaje de error en caso de que ocurra una excepción
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                // Muestra un mensaje de error en caso de que ocurra una excepción
                JOptionPane.showMessageDialog(this, "Error en el registro. \n Consulte a un administrador.");
                ex.printStackTrace(System.out);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se ha cancelado el registro.");
        }

    }//GEN-LAST:event_btnRegistrarActionPerformed

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
        javax.swing.JLabel lblFichaUbicacion = new javax.swing.JLabel();
        javax.swing.JButton btnRegistrar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblEquipo = new javax.swing.JLabel();
        javax.swing.JLabel lblUbicacion = new javax.swing.JLabel();
        cbxEuqipo = new javax.swing.JComboBox<>();
        cbxUbicacion = new javax.swing.JComboBox<>();
        javax.swing.JScrollPane scrollPanelObservaciones = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextPane();
        javax.swing.JLabel lblFechaSalida = new javax.swing.JLabel();
        javax.swing.JLabel lblObservaciones = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        javax.swing.JLabel lblHoraSalida = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollPanelUbicaciones = new javax.swing.JScrollPane();
        tblUbicacion = new javax.swing.JTable();
        spinHora = new javax.swing.JSpinner();
        spinMinuto = new javax.swing.JSpinner();
        javax.swing.JLabel lblHora = new javax.swing.JLabel();
        javax.swing.JLabel lblMinuto = new javax.swing.JLabel();
        javax.swing.JLabel lblUbicaciones = new javax.swing.JLabel();
        javax.swing.JButton btnActualizarTabla = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        background.setBackground(new java.awt.Color(217, 217, 217));

        lblFichaUbicacion.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblFichaUbicacion.setText("Registrar Movimiento");

        btnRegistrar.setBackground(new java.awt.Color(38, 34, 249));
        btnRegistrar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
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

        lblEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEquipo.setText("Equipo");

        lblUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUbicacion.setText("Ubicación");

        cbxEuqipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxEuqipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        cbxUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxUbicacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        scrollPanelObservaciones.setViewportView(txtObservaciones);

        lblFechaSalida.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFechaSalida.setText("Fecha de Salida");

        lblObservaciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblObservaciones.setText("Observaciones");

        lblHoraSalida.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHoraSalida.setText("Hora de Salida");

        tblUbicacion.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Institucion", "Sector", "Nombre", "Número", "Piso", "Cama"
                }
        ));
        scrollPanelUbicaciones.setViewportView(tblUbicacion);
        if (tblUbicacion.getColumnModel().getColumnCount() > 0) {
            tblUbicacion.getColumnModel().getColumn(0).setResizable(false);
            tblUbicacion.getColumnModel().getColumn(1).setResizable(false);
            tblUbicacion.getColumnModel().getColumn(2).setResizable(false);
            tblUbicacion.getColumnModel().getColumn(3).setResizable(false);
            tblUbicacion.getColumnModel().getColumn(4).setResizable(false);
            tblUbicacion.getColumnModel().getColumn(5).setResizable(false);
        }

        spinHora.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        spinMinuto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblHora.setText("Hora");

        lblMinuto.setText("Minuto");

        lblUbicaciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUbicaciones.setText("Info. Ubicación");

        btnActualizarTabla.setBackground(new java.awt.Color(38, 34, 249));
        btnActualizarTabla.setFont(new java.awt.Font("Unispace", 0, 14)); // NOI18N
        btnActualizarTabla.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarTabla.setText("Registrar");
        btnActualizarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(lblFichaUbicacion)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addComponent(lblUbicaciones)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnActualizarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addComponent(lblObservaciones)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblFechaSalida)
                                                                        .addComponent(lblHoraSalida))
                                                                .addGap(154, 154, 154)
                                                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                                                .addComponent(lblHora)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(spinHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(lblMinuto)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(spinMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                                                .addComponent(lblUbicacion)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(cbxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(scrollPanelObservaciones, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(scrollPanelUbicaciones, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                                                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                                .addComponent(lblEquipo)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(cbxEuqipo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(14, 14, 14))))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(lblFichaUbicacion)
                                .addGap(18, 18, 18)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbxEuqipo)
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(lblEquipo)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cbxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUbicacion))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(spinMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblHora)
                                                        .addComponent(spinHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addComponent(lblFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblHoraSalida)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblObservaciones)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPanelObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUbicaciones)
                                        .addComponent(btnActualizarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPanelUbicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
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

    private void btnActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTablaActionPerformed
        recargarFilas();
    }//GEN-LAST:event_btnActualizarTablaActionPerformed


    public void recargarFilas() {
        // Obtengo el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tblUbicacion.getModel();

        // Limpia las filas de la tabla
        modelo.setRowCount(0);

        // Obtiene la ubicación seleccionada
        UbicacionDTO ubicacion = Conexion.rec_ubicacion.obtenerPorNombre(cbxUbicacion.getSelectedItem().toString());
        // Carga las ubicaciones en la tabla
        cargarFila(ubicacion, modelo.getRowCount());

    }

    private void cargarFila(UbicacionDTO ubicacion, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblUbicacion.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1; // Selecciona la nueva fila
        }

        // Carga los datos de la ubicación en la fila
        modeloTabla.setValueAt(ubicacion.getNombre(), fila, 2);
        modeloTabla.setValueAt(ubicacion.getInstitucion(), fila, 0);
        modeloTabla.setValueAt(ubicacion.getSector(), fila, 1);
        modeloTabla.setValueAt(ubicacion.getPiso(), fila, 4);
        modeloTabla.setValueAt(ubicacion.getCama(), fila, 5);
        modeloTabla.setValueAt(ubicacion.getNumero(), fila, 3);
    }


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxEuqipo;
    private javax.swing.JComboBox<String> cbxUbicacion;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JSpinner spinHora;
    private javax.swing.JSpinner spinMinuto;
    private javax.swing.JTable tblUbicacion;
    private javax.swing.JTextPane txtObservaciones;
    // End of variables declaration//GEN-END:variables
}
