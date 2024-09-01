package pdt.cliente.swing.ubicacion;

import cfc.servidor.DTOs.EquipoDTO;
import cfc.servidor.DTOs.MovimientoDTO;
import cfc.servidor.DTOs.UbicacionDTO;
import cfc.servidor.enumerados.InstitucionesEnum;
import cfc.servidor.enumerados.SectoresEnum;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que representa la ventana de listado de movimientos.
 */
public class JFListadoMovimientos extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFListadoMovimientos instancia;

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFListadoMovimientos() {
        this.initComponents();
        this.cargarComboBoxes();
        this.recargarFilas();

    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFListadoMovimientos getInstancia() {
        if (instancia == null) {
            instancia = new JFListadoMovimientos();
        }
        return instancia;
    }

    /**
     * Método que limpia los filtros de búsqueda.
     */
    public void limpiarFiltros() {
        this.txtEquipo.setText("");
        this.txtIdentificador.setText("");
        this.cbxInstitucion.setSelectedItem("NINGUNA");
        this.cbxSector.setSelectedItem("NINGUNO");
        this.txtUsuario.setText("");
        this.dcDesde.setDate(null);
        this.dcHasta.setDate(null);
        this.recargarFilas();
        this.aplicarFiltros();
    }

    /**
     * Rellena una fila de la tabla con los datos de un movimiento.<br>
     * Si la fila no existe, crea una nueva y la selecciona.
     *
     * @param movimiento Movimiento a cargar.
     * @param fila       Fila a cargar.
     */
    public void cargarFila(MovimientoDTO movimiento, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblMovimientos.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1; // Selecciona la nueva fila
        }

        // Obtiene el equipo del movimiento
        EquipoDTO equipo = Conexion.rec_equipo.obtenerPorID(movimiento.getIdEquipo());

        // Obtiene la ubicación del movimiento
        UbicacionDTO ubicacion = Conexion.rec_ubicacion.obtenerPorID(movimiento.getIdUbicacion());

        // Define el formato de la hora
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

        // Obtiene la fecha
        LocalDate fecha = movimiento.getFechaDelRegistro().toLocalDate();
        // Obtiene la hora y los minutos
        LocalTime horaYMinutos = movimiento.getFechaDelRegistro().toLocalTime();


        // Cargo los datos del movimiento en la fila
        modeloTabla.setValueAt(movimiento.getId(), fila, 0);
        modeloTabla.setValueAt(equipo.getNombre(), fila, 1);
        modeloTabla.setValueAt(equipo.getId(), fila, 2);
        modeloTabla.setValueAt(ubicacion.getInstitucion(), fila, 3);
        modeloTabla.setValueAt(ubicacion.getSector(), fila, 4);
        modeloTabla.setValueAt(ubicacion.getPiso(), fila, 5);
        modeloTabla.setValueAt(ubicacion.getCama(), fila, 6);
        modeloTabla.setValueAt(ubicacion.getNumero(), fila, 7);
        modeloTabla.setValueAt(movimiento.getUsername(), fila, 8);
        modeloTabla.setValueAt(Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()), fila, 9); // Convierte la fecha a Date (java.util.Date
        modeloTabla.setValueAt(horaYMinutos.format(formatterHora), fila, 10);
    }

    /**
     * Método que recarga las filas de la tabla con los movimientos de la base de datos.
     */
    public void recargarFilas() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblMovimientos.getModel();

        // Limpia las filas de la tabla
        modeloTabla.setRowCount(0);

        // Obtiene la lista completa de movimientos
        List<MovimientoDTO> movimientosDTO = Conexion.rec_movimiento.obtenerTodo();

        // Agrega cada movimiento a la tabla
        for (MovimientoDTO movi : movimientosDTO) {
            this.cargarFila(movi, modeloTabla.getRowCount());
        }
    }

    /**
     * Método que aplica los filtros de búsqueda a la tabla.
     */
    public void aplicarFiltros() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblMovimientos.getModel();

        // Crea un ordenador de filas para el modelo
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);

        // Asigna el ordenador de filas al modelo de la tabla
        this.tblMovimientos.setRowSorter(sorter);

        //  Crea un mapa de filtros que se aplicarán al ordenador de filas
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Agrega los filtros basados en los campos de texto a la lista de filtros
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtEquipo.getText().toLowerCase()), 1)); // Filtrar por nombre del equipo
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtIdentificador.getText().toLowerCase()), 2)); // Filtrar por ID del equipo
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtUsuario.getText().toLowerCase()), 8)); // Filtrar por usuario

        String institucion = (String) cbxInstitucion.getSelectedItem();
        String sector = (String) cbxSector.getSelectedItem();

        if (institucion != null && !institucion.equalsIgnoreCase("NINGUNA")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(institucion.toLowerCase()), 3)); // Filtrar por institucion
        }

        if (sector != null && !sector.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(sector.toLowerCase()), 4)); // Filtrar por sector
        }

        Date fechaDesde = dcDesde.getDate();
        // resta un dia a la fecha para que el filtro sea inclusivo
        fechaDesde = fechaDesde != null ? Date.from(dcDesde.getDate().toInstant().minusSeconds(86400)) : null;

        Date fechaHasta = dcHasta.getDate();

        // restar un dia a la fecha para que el filtro sea inclusivo
        if (fechaHasta != null) {
            LocalDate localDate = fechaHasta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localDate = localDate.plusDays(1); // Sumar un día
            fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        if (fechaDesde != null) {
            filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, fechaDesde, 9)); // Filtrar por fecha desde
        }

        if (fechaHasta != null) {
            filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, fechaHasta, 9)); // Filtrar por fecha hasta
        }

        // Crea un filtro que combina todos los filtros de la lista
        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.andFilter(filters);

        // Aplica el filtro al ordenador de filas
        sorter.setRowFilter(rowFilter);
    }

    /**
     * Método que carga los items de los ComboBoxes (Instituciones y Sectores).
     */
    private void cargarComboBoxes() {
        // Limpia los ComboBox
        this.cbxInstitucion.removeAllItems();
        this.cbxSector.removeAllItems();

        // Agrega los items al ComboBox de Instituciones
        this.cbxInstitucion.addItem("NINGUNA");
        for (InstitucionesEnum institucion : InstitucionesEnum.values()) {
            this.cbxInstitucion.addItem(institucion.name());
        }

        // Agrega los items al ComboBox de Sectores
        this.cbxSector.addItem("NINGUNO");
        for (SectoresEnum sector : SectoresEnum.values()) {
            this.cbxSector.addItem(sector.name());
        }
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón de Nuevo Movimiento<br>
     * Oculta la ventana actual y muestra la ventana de Registro de Movimiento.
     *
     * @param evt Evento de acción.
     */
    private void btnNuevoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoMovimientoActionPerformed
        this.setVisible(false); // Oculta la ventana actual
        JFRegistrarMovimiento.getInstancia().setVisible(true); // Muestra la ventana de Registro de Movimiento
    }//GEN-LAST:event_btnNuevoMovimientoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de Cancelar.<br>
     * Oculta la ventana actual y muestra la ventana de Dashboard.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false); // Oculta la ventana actual
        JFDashboard.getInstancia().setVisible(true); // Muestra la ventana de Dashboard
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de Limpiar.<br>
     * Limpia los filtros de búsqueda y recarga las filas de la tabla.
     *
     * @param evt Evento de acción.
     */
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.limpiarFiltros();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de Consultar.<br>
     * Recarga las filas de la tabla y aplica los filtros de búsqueda.
     *
     * @param evt Evento de acción.
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        this.recargarFilas();
        this.aplicarFiltros();
    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Exportar a Excel".<br>
     * Exporta los datos de la tabla a un archivo de Excel.
     *
     * @param evt Evento de acción.
     */
    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        try {
            ExcelManager.exportarExcel(tblMovimientos);
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
        javax.swing.JScrollPane scrollPanelMovimientos = new javax.swing.JScrollPane();
        tblMovimientos = new javax.swing.JTable();
        txtEquipo = new javax.swing.JTextField();
        javax.swing.JLabel lblListadoDeUbicaciones = new javax.swing.JLabel();
        javax.swing.JLabel lblID = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtIdentificador = new javax.swing.JTextField();
        javax.swing.JButton btnConsultar = new javax.swing.JButton();
        javax.swing.JButton btnLimpiar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblEstado = new javax.swing.JLabel();
        javax.swing.JButton btnNuevoMovimiento = new javax.swing.JButton();
        cbxInstitucion = new javax.swing.JComboBox<>();
        cbxSector = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblSector = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        javax.swing.JLabel lblUsuario = new javax.swing.JLabel();
        javax.swing.JLabel lblDesde = new javax.swing.JLabel();
        javax.swing.JLabel lblHasta = new javax.swing.JLabel();
        dcDesde = new com.toedter.calendar.JDateChooser();
        dcHasta = new com.toedter.calendar.JDateChooser();
        javax.swing.JButton btnExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblMovimientos.setAutoCreateRowSorter(true);
        tblMovimientos.setBackground(new java.awt.Color(217, 217, 217));
        tblMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Equipo", "ID Equipo", "Instituto", "Sector", "Piso", "Cama", "Numero", "Usuario", "Fecha", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMovimientos.setColumnSelectionAllowed(true);
        tblMovimientos.setMinimumSize(new java.awt.Dimension(1007, 558));
        tblMovimientos.setPreferredSize(new java.awt.Dimension(1007, 558));
        scrollPanelMovimientos.setViewportView(tblMovimientos);
        tblMovimientos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        tblMovimientos.getColumnModel().getColumn(9).setCellRenderer(tableCellRendererDate);

        txtEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblListadoDeUbicaciones.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        lblListadoDeUbicaciones.setText("Listado de Movimientos");

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblID.setText("Equipo");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Identificador");

        txtIdentificador.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

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

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEstado.setText("Institucion");

        btnNuevoMovimiento.setBackground(new java.awt.Color(255, 204, 0));
        btnNuevoMovimiento.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnNuevoMovimiento.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoMovimiento.setText("Nuevo Movimiento");
        btnNuevoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoMovimientoActionPerformed(evt);
            }
        });

        cbxInstitucion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxInstitucion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxSector.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxSector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblSector.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSector.setText("Sector");

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuario.setText("Usuario");

        lblDesde.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDesde.setText("Desde");

        lblHasta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHasta.setText("Hasta");

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
                .addGap(449, 449, 449)
                .addComponent(lblListadoDeUbicaciones)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportarExcel))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblSector)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxSector, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblID))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEquipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdentificador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxInstitucion, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNuevoMovimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsuario)
                                    .addComponent(lblDesde)
                                    .addComponent(lblHasta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(dcDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcHasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addComponent(scrollPanelMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(lblListadoDeUbicaciones)
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPanelMovimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado)
                            .addComponent(cbxInstitucion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSector)
                            .addComponent(cbxSector, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDesde)
                            .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHasta)
                            .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxInstitucion;
    private javax.swing.JComboBox<String> cbxSector;
    private com.toedter.calendar.JDateChooser dcDesde;
    private com.toedter.calendar.JDateChooser dcHasta;
    private javax.swing.JTable tblMovimientos;
    private javax.swing.JTextField txtEquipo;
    private javax.swing.JTextField txtIdentificador;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
