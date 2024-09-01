package pdt.cliente.swing.intervencion;

import cfc.servidor.DTOs.IntervencionDTO;
import cfc.servidor.DTOs.TipoIntervencionDTO;
import pdt.cliente.Conexion;
import pdt.cliente.ExcelManager;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa la ventana de reporte de intervenciones por fechas.
 */
public class JFIntervencionPorFecha extends javax.swing.JFrame {

    // ======================== Atributos ========================
    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFIntervencionPorFecha instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    public JFIntervencionPorFecha() {
        initComponents();
        limpiarFiltros();
        recargarFilas();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFIntervencionPorFecha getInstancia() {
        if (instancia == null) {
            instancia = new JFIntervencionPorFecha();
        }
        return instancia;
    }

    /**
     * Método que limpia los filtros de búsqueda.
     */
    public void limpiarFiltros() {
        txtId.setText("");
        dcDesde.setDate(null);
        dcHasta.setDate(null);
        recargarFilas();
        aplicarFiltros();
    }

    /**
     * Método que aplica los filtros de búsqueda a la tabla.
     */
    public void aplicarFiltros() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblIntervenciones.getModel();

        // Crea un ordenador de filas para el modelo
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);

        // Asigna el ordenador de filas al modelo de la tabla
        this.tblIntervenciones.setRowSorter(sorter);

        // Crea un mapa de filtros que se aplicarán al ordenador de filas
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Agrega los filtros basados en los campos de texto a la lista de filtros
        filters.add(RowFilter.regexFilter("(?i)" + txtId.getText().toLowerCase(), 0)); // Filtrar por id

        Date fechaDesde = dcDesde.getDate();
        // resta un dia a la fecha para que el filtro sea inclusivo
        fechaDesde = fechaDesde != null ? Date.from(dcDesde.getDate().toInstant().minusSeconds(86400)) : null;

        Date fechaHasta = dcHasta.getDate();

        // restar un día a la fecha
        if (fechaHasta != null) {
            LocalDate localDate = fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localDate = localDate.plusDays(1); // Sumar un día
            fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        if (fechaDesde != null) {
            filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, fechaDesde, 1)); // Filtrar por fecha desde
        }

        if (fechaHasta != null) {
            filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, fechaHasta, 1)); // Filtrar por fecha hasta
        }

        // Crea un filtro que combina los filtros de la lista
        RowFilter<DefaultTableModel, Object> rf = RowFilter.andFilter(filters);

        // Aplica el filtro al ordenador de filas
        sorter.setRowFilter(rf);
    }

    /**
     * Método que recarga las filas de la tabla con las intervenciones.
     */
    public void recargarFilas() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblIntervenciones.getModel();

        // Limpia las filas de la tabla
        modeloTabla.setRowCount(0);

        // Obtiene la lista completa de intervenciones
        List<IntervencionDTO> intervenciones = Conexion.rec_intervencion.obtenerTodo();

        // Agrega cada intervencion a la tabla
        for (IntervencionDTO inter : intervenciones) {
            this.cargarFila(inter, modeloTabla.getRowCount());
        }
    }

    /**
     * Método que carga los datos de una intervención en una fila de la tabla.
     * Si la fila no existe, la crea.
     *
     * @param intervencion Intervención a cargar.
     * @param fila         Fila en la que cargar la intervención.
     */
    private void cargarFila(IntervencionDTO intervencion, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblIntervenciones.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1;
        }

        // Carga los datos de la intervencion en la fila
        TipoIntervencionDTO tipoIntervencion = Conexion.rec_tipoIntervencion.obtenerPorID(intervencion.getIdTipoIntervencion());
        LocalDate fecha = intervencion.getFechaYHora().toLocalDate();
        LocalTime hora = intervencion.getFechaYHora().toLocalTime();
        modeloTabla.setValueAt(intervencion.getId(), fila, 0);
        modeloTabla.setValueAt(Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()), fila, 1);
        modeloTabla.setValueAt(tipoIntervencion.getNombre(), fila, 2);
        modeloTabla.setValueAt(intervencion.getMotivo(), fila, 3);
        modeloTabla.setValueAt(intervencion.getIdEquipoIntervenido(), fila, 4);
        modeloTabla.setValueAt(intervencion.getObservacion(), fila, 5);
        modeloTabla.setValueAt(hora, fila, 6);
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón "Consultar".<br>
     * Aplica los filtros de búsqueda a la tabla.
     *
     * @param evt Evento de acción.
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        recargarFilas();
        aplicarFiltros();
    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Limpiar".<br>
     * Limpia los filtros de búsqueda.
     *
     * @param evt Evento de acción.
     */
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFiltros();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Cancelar".<br>
     * Cierra la ventana y vuelve al Dashboard.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Trabajar".<br>
     * Abre la ventana de ficha de trabajo.
     *
     * @param evt Evento de acción.
     */
    private void btnTrabajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrabajarActionPerformed
        int filaSeleccionada = this.tblIntervenciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Intervencion");
            return;
        }
        int id = (int) this.tblIntervenciones.getModel().getValueAt(this.tblIntervenciones.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
        IntervencionDTO dto = Conexion.rec_intervencion.obtenerPorID(id);
        JFFichaDeTrabajo ficha = new JFFichaDeTrabajo(dto);
        ficha.setVisible(true);
    }//GEN-LAST:event_btnTrabajarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Exportar a Excel".<br>
     * Exporta los datos de la tabla a un archivo de Excel.
     *
     * @param evt Evento de acción.
     */
    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        try {
            ExcelManager.exportarExcel(tblIntervenciones);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(this, "Error al exportar a Excel");
        }
    }//GEN-LAST:event_btnExportarExcelActionPerformed
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
        javax.swing.JScrollPane scrollPanelTabla = new javax.swing.JScrollPane();
        tblIntervenciones = new javax.swing.JTable();
        javax.swing.JLabel lblListadoDeUbicaciones = new javax.swing.JLabel();
        javax.swing.JLabel lblDesde = new javax.swing.JLabel();
        javax.swing.JLabel lblHasta = new javax.swing.JLabel();
        javax.swing.JButton btnConsultar = new javax.swing.JButton();
        javax.swing.JButton btnLimpiar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JButton btnTrabajar = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        javax.swing.JLabel lblID = new javax.swing.JLabel();
        dcDesde = new com.toedter.calendar.JDateChooser();
        dcHasta = new com.toedter.calendar.JDateChooser();
        javax.swing.JButton btnExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblIntervenciones.setAutoCreateRowSorter(true);
        tblIntervenciones.setBackground(new java.awt.Color(217, 217, 217));
        tblIntervenciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fecha", "Tipo", "Motivo", "ID Equipo", "Observaciones", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblIntervenciones.setColumnSelectionAllowed(true);
        tblIntervenciones.setMinimumSize(new java.awt.Dimension(1007, 558));
        tblIntervenciones.setPreferredSize(new java.awt.Dimension(1007, 558));
        scrollPanelTabla.setViewportView(tblIntervenciones);
        tblIntervenciones.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        // Crear un formato de fecha
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Crear un TableCellRenderer
        TableCellRenderer tableCellRendererDate = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = dateFormat.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        tblIntervenciones.getColumnModel().getColumn(1).setCellRenderer(tableCellRendererDate);

        lblListadoDeUbicaciones.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        lblListadoDeUbicaciones.setText("Reporte de Intervenciones por Fechas");

        lblDesde.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDesde.setText("Desde");

        lblHasta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHasta.setText("Hasta");

        btnConsultar.setBackground(new java.awt.Color(66, 153, 255));
        btnConsultar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(153, 153, 153));
        btnLimpiar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnTrabajar.setBackground(new java.awt.Color(255, 255, 51));
        btnTrabajar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnTrabajar.setForeground(new java.awt.Color(255, 255, 255));
        btnTrabajar.setText("Trabajar");
        btnTrabajar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrabajarActionPerformed(evt);
            }
        });

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblID.setText("ID");

        btnExportarExcel.setBackground(new java.awt.Color(204, 204, 204));
        btnExportarExcel.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnExportarExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarExcel.setText("Exportar Excel");
        btnExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(lblListadoDeUbicaciones)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportarExcel))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnTrabajar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDesde)
                                    .addComponent(lblHasta)
                                    .addComponent(lblID))
                                .addGap(86, 86, 86)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(dcDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcHasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblListadoDeUbicaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblID)
                                .addGap(18, 18, 18)
                                .addComponent(lblDesde)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHasta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTrabajar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
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
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dcDesde;
    private com.toedter.calendar.JDateChooser dcHasta;
    private javax.swing.JTable tblIntervenciones;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
