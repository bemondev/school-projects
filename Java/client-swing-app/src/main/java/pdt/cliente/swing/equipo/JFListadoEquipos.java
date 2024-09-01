package pdt.cliente.swing.equipo;

import cfc.servidor.DTOs.*;
import cfc.servidor.enumerados.EstadosEnum;
import pdt.cliente.Conexion;
import pdt.cliente.ExcelManager;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que representa la ventana de listado de equipos.
 */
public class JFListadoEquipos extends javax.swing.JFrame {

    // ======================== Atributos ========================
    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFListadoEquipos instancia = null;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFListadoEquipos() {
        initComponents();
        cargarComboBoxes();
        recargarFilas();
        cargarEventoSeleccion();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFListadoEquipos getInstancia() {
        if (instancia == null) {
            instancia = new JFListadoEquipos();
        }
        return instancia;
    }

    /**
     * Método que limpia los filtros de búsqueda.
     */
    public void limpiarFiltros() {
        txtID.setText("");
        txtNombre.setText("");
        cbxTipoEquipo.setSelectedItem("NINGUNO");
        cbxMarca.setSelectedItem("NINGUNO");
        cbxModelo.setSelectedItem("NINGUNO");
        txtNSerie.setText("");
        txtGarantia.setText("");
        cbxPais.setSelectedItem("NINGUNO");
        txtProveedor.setText("");
        dcFecDesde.setDate(null);
        dcFecHasta.setDate(null);
        txtIDInterna.setText("");
        cbxUbicacion.setSelectedItem("NINGUNO");
        cbxEstado.setSelectedItem("NINGUNO");
        recargarFilas();
        aplicarFiltros();
    }

    /**
     * Método que recarga las filas de la tabla con los equipos de la base de datos.
     */
    public void recargarFilas() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblEquipos.getModel();

        // Limpia las filas de la tabla
        modeloTabla.setRowCount(0);

        // Obtiene la lista completa de equipos
        List<EquipoDTO> equipos = Conexion.rec_equipo.obtenerTodo();

        // Agrega cada equipo a la tabla
        for (EquipoDTO equipo : equipos) {
            this.cargarFila(equipo, modeloTabla.getRowCount());
        }
    }

    /**
     * Método que aplica los filtros de búsqueda a la tabla.
     */
    public void aplicarFiltros() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblEquipos.getModel();

        // Crea un ordenador de filas para el modelo
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);

        // Asigna el ordenador de filas al modelo de la tabla
        this.tblEquipos.setRowSorter(sorter);

        // Crea un mapa de filtros que se aplicarán al ordenador de filas
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Agrega los filtros basados en los campos de texto a la lista de filtros
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtID.getText().toLowerCase()), 0)); // Filtrar por id
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtNombre.getText().toLowerCase()), 1)); // Filtrar por nombre
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtNSerie.getText().toLowerCase()), 5)); // Filtrar por n.serie
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtGarantia.getText().toLowerCase()), 6)); // Filtrar por garantía
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtProveedor.getText().toLowerCase()), 8)); // Filtrar por proveedor
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtIDInterna.getText().toLowerCase()), 10)); // Filtrar por idInterna

        String tipoEquipo = (String) cbxTipoEquipo.getSelectedItem();
        if (tipoEquipo != null && !tipoEquipo.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(tipoEquipo.toLowerCase()), 2)); // Filtrar por tipoEquipo
        }

        String marca = (String) cbxMarca.getSelectedItem();
        if (marca != null && !marca.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(marca.toLowerCase()), 3)); // Filtrar por marca
        }

        String modelo = (String) cbxModelo.getSelectedItem();
        if (modelo != null && !modelo.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(modelo.toLowerCase()), 4)); // Filtrar por modelo
        }

        String pais = (String) cbxPais.getSelectedItem();
        if (pais != null && !pais.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(pais.toLowerCase()), 7)); // Filtrar por país
        }

        String ubicacion = (String) cbxUbicacion.getSelectedItem();
        if (ubicacion != null && !ubicacion.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(ubicacion.toLowerCase()), 11)); // Filtrar por ubicación
        }

        String estado = (String) cbxEstado.getSelectedItem();
        if (estado != null && !estado.equalsIgnoreCase("NINGUNO")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(estado.toLowerCase()), 12)); // Filtrar por estado
        }

        Date fechaDesde = dcFecDesde.getDate();
        // resta un dia a la fecha para que el filtro sea inclusivo
        fechaDesde = fechaDesde != null ? Date.from(dcFecDesde.getDate().toInstant().minusSeconds(86400)) : null;

        Date fechaHasta = dcFecHasta.getDate();

        // restar un día a la fecha
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

        // Crea un filtro que combina los filtros de la lista
        RowFilter<DefaultTableModel, Object> rf = RowFilter.andFilter(filters);

        // Aplica el filtro al ordenador de filas
        sorter.setRowFilter(rf);
    }

    /**
     * Método que carga los items al ComboBox de Estados.
     */
    public void cargarComboBoxes() {
        // Limpia los ComboBoxes
        cbxTipoEquipo.removeAllItems();
        cbxMarca.removeAllItems();
        cbxModelo.removeAllItems();
        cbxPais.removeAllItems();
        cbxUbicacion.removeAllItems();
        cbxEstado.removeAllItems();

        // Agrega los items al ComboBox de Tipo de Equipo
        cbxTipoEquipo.addItem("NINGUNO");
        List<TipoEquipoDTO> tiposEquipo = Conexion.rec_tipoEquipo.obtenerTodo();
        for (TipoEquipoDTO tipoEquipo : tiposEquipo) {
            cbxTipoEquipo.addItem(tipoEquipo.getNombre());
        }

        // Agrega los items al ComboBox de Marca
        cbxMarca.addItem("NINGUNO");
        List<MarcaDTO> marcas = Conexion.rec_marca.obtenerTodo();
        for (MarcaDTO marca : marcas) {
            cbxMarca.addItem(marca.getNombre());
        }

        // Agrega los items al ComboBox de Modelo
        cbxModelo.addItem("NINGUNO");
        List<ModeloDTO> modelos = Conexion.rec_modelo.obtenerTodo();
        for (ModeloDTO modelo : modelos) {
            cbxModelo.addItem(modelo.getNombre());
        }

        // Agrega los items al ComboBox de País
        cbxPais.addItem("NINGUNO");
        List<PaisDTO> paises = Conexion.rec_pais.obtenerTodo();
        for (PaisDTO pais : paises) {
            cbxPais.addItem(pais.getNombre());
        }

        // Agrega los items al ComboBox de Ubicación
        cbxUbicacion.addItem("NINGUNO");
        List<UbicacionDTO> ubicaciones = Conexion.rec_ubicacion.obtenerTodo();
        for (UbicacionDTO ubicacion : ubicaciones) {
            cbxUbicacion.addItem(ubicacion.getNombre());
        }

        // Agrega los items al ComboBox de Estado
        cbxEstado.addItem("NINGUNO");
        cbxEstado.addItem(EstadosEnum.ACTIVO.toString());
        cbxEstado.addItem(EstadosEnum.ELIMINADO.toString());
    }

    /**
     * Rellena una fila de la tabla con los datos de un equipo.<br>
     * Si la fila no existe, crea una nueva y la selecciona.
     *
     * @param equipo Equipo a cargar.
     * @param fila   Fila a cargar.
     */
    public void cargarFila(EquipoDTO equipo, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblEquipos.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1;
        }

        // Carga los datos del equipo en la fila

        TipoEquipoDTO tipoEquipo = Conexion.rec_tipoEquipo.obtenerPorID(equipo.getIdTipoEquipo());
        ModeloDTO modelo = Conexion.rec_modelo.obtenerPorID(equipo.getIdModeloEquipo());
        MarcaDTO marca = Conexion.rec_marca.obtenerPorID(modelo.getIdMarca());
        UbicacionDTO ubicacion = Conexion.rec_ubicacion.obtenerPorID(equipo.getIdUbicacion());
        PaisDTO pais = Conexion.rec_pais.obtenerPorID(equipo.getIdPaisDeOrigen());


        modeloTabla.setValueAt(equipo.getId(), fila, 0);
        modeloTabla.setValueAt(equipo.getNombre(), fila, 1);
        modeloTabla.setValueAt(tipoEquipo.getNombre(), fila, 2);
        modeloTabla.setValueAt(marca.getNombre(), fila, 3);
        modeloTabla.setValueAt(modelo.getNombre(), fila, 4);
        modeloTabla.setValueAt(equipo.getNumeroSerie(), fila, 5);
        modeloTabla.setValueAt(equipo.getGarantia(), fila, 6);
        modeloTabla.setValueAt(pais.getNombre(), fila, 7);
        modeloTabla.setValueAt(equipo.getProveedor(), fila, 8);
        modeloTabla.setValueAt(Date.from(equipo.getFechaAdquisicion().atStartOfDay(ZoneId.systemDefault()).toInstant()), fila, 9);
        modeloTabla.setValueAt(equipo.getIdInterna(), fila, 10);
        modeloTabla.setValueAt(ubicacion.getNombre(), fila, 11);
        modeloTabla.setValueAt(equipo.getEstado().name(), fila, 12);
    }

    /**
     * Método que carga el evento de selección de la tabla.<br>
     * Si se selecciona una fila, cambia el mensaje del botón de baja/reactivación.
     */
    private void cargarEventoSeleccion() {
        ListSelectionListener selectionTableListener = e -> {
            if (e.getValueIsAdjusting()) return; // Evita que se ejecute más de una vez
            if (tblEquipos.getSelectedRow() != -1) {
                int filaSeleccionada = tblEquipos.getSelectedRow();
                int id = (int) tblEquipos.getModel().getValueAt(tblEquipos.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
                EquipoDTO equipo = Conexion.rec_equipo.obtenerPorID(id);
                if (equipo.getEstado().equals(EstadosEnum.ELIMINADO)) {
                    btnBajaReactivar.setText("Reactivar");
                } else {
                    btnBajaReactivar.setText("Baja");
                }
            }
        };
        this.tblEquipos.getSelectionModel().addListSelectionListener(selectionTableListener);
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón "Baja"/"Reactivar".<br>
     * Da de baja o reactiva un equipo.
     *
     * @param evt Evento de acción.
     */
    private void btnBajaReactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaReactivarActionPerformed
        int filaSeleccionada = this.tblEquipos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Tipo de Equipo");
            return;
        }
        int id = (int) this.tblEquipos.getModel().getValueAt(this.tblEquipos.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);

        JOptionPane opcion = new JOptionPane();
        opcion.setMessage("¿Seguro que quieres dar de baja este Equipo?");
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmación", "Confirmar baja", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            EquipoDTO dto = Conexion.rec_equipo.obtenerPorID(id);
            if (dto.getEstado() == EstadosEnum.ACTIVO) {
                setVisible(false);
                JFBajaEquipo jfBajaEquipo = new JFBajaEquipo(dto);
                jfBajaEquipo.setVisible(true);
                dto.setEstado(EstadosEnum.ELIMINADO);
            } else if (dto.getEstado() == EstadosEnum.ELIMINADO) {
                Conexion.rec_equipo.activar(dto.getId());
                this.btnBajaReactivar.setText("Baja");
                dto.setEstado(EstadosEnum.ACTIVO);
                JOptionPane.showMessageDialog(this, "Equipo " + dto.getNombre() + " Activado.");
            }
            cargarFila(dto, filaSeleccionada);
        }



    }//GEN-LAST:event_btnBajaReactivarActionPerformed

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
     * Método que se ejecuta al presionar el botón "Modificar".<br>
     * Abre la ventana de modificación de un equipo.
     *
     * @param evt Evento de acción.
     */
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = this.tblEquipos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Tipo de Equipo");
            return;
        }
        int id = (int) this.tblEquipos.getModel().getValueAt(this.tblEquipos.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
        EquipoDTO dto = Conexion.rec_equipo.obtenerPorID(id);
        setVisible(false);
        JFFichaEquipo jfFichaEquipo = new JFFichaEquipo(dto);
        jfFichaEquipo.setVisible(true);
    }//GEN-LAST:event_btnModificarActionPerformed

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
     * Método que se ejecuta al presionar el botón "Exportar a Excel".<br>
     * Exporta los datos de la tabla a un archivo de Excel.
     *
     * @param evt Evento de acción.
     */
    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        try {
            ExcelManager.exportarExcel(tblEquipos);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(this, "Error al exportar a Excel");
        }
    }//GEN-LAST:event_btnExportarExcelActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Ver Imagen".<br>
     * Abre una ventana para ver la imagen del equipo seleccionado.
     * @param evt Evento de acción.
     */
    private void btnVerImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerImagenActionPerformed
        int filaSeleccionada = this.tblEquipos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Tipo de Equipo");
            return;
        }
        int id = (int) this.tblEquipos.getModel().getValueAt(this.tblEquipos.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
        EquipoDTO dto = Conexion.rec_equipo.obtenerPorID(id);

//        try{
////            Blob blob = BlobImageDisplay.convertToBlob(Conexion.rec_imagen.obtenerPorID(dto.getIdImagen()).getUrl());
////            new BlobImageDisplay(blob);
//        } catch (SQLException ex) {
//            System.out.println("Error al convertir el array de bytes a Blob: " + ex.getMessage());
//        }
    }//GEN-LAST:event_btnVerImagenActionPerformed


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
        tblEquipos = new javax.swing.JTable();
        txtID = new javax.swing.JTextField();
        javax.swing.JLabel lblListadoDeUsuarios = new javax.swing.JLabel();
        javax.swing.JLabel lblID = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblMarca = new javax.swing.JLabel();
        javax.swing.JButton btnConsultar = new javax.swing.JButton();
        btnBajaReactivar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        cbxTipoEquipo = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblTipoEquipo = new javax.swing.JLabel();
        javax.swing.JLabel lblModelo = new javax.swing.JLabel();
        txtNSerie = new javax.swing.JTextField();
        javax.swing.JLabel lblNSerie = new javax.swing.JLabel();
        txtGarantia = new javax.swing.JTextField();
        javax.swing.JLabel lblGarantia = new javax.swing.JLabel();
        cbxPais = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblPais = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        javax.swing.JLabel lblProveedor = new javax.swing.JLabel();
        javax.swing.JLabel lblFecDesde = new javax.swing.JLabel();
        txtIDInterna = new javax.swing.JTextField();
        javax.swing.JLabel lblIdInterna = new javax.swing.JLabel();
        cbxUbicacion = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblUbicacion = new javax.swing.JLabel();
        javax.swing.JButton btnModificar = new javax.swing.JButton();
        javax.swing.JButton btnLimpiar = new javax.swing.JButton();
        cbxModelo = new javax.swing.JComboBox<>();
        cbxMarca = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblFecHasta = new javax.swing.JLabel();
        dcFecDesde = new com.toedter.calendar.JDateChooser();
        dcFecHasta = new com.toedter.calendar.JDateChooser();
        cbxEstado = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblEstado = new javax.swing.JLabel();
        javax.swing.JButton btnExportarExcel = new javax.swing.JButton();
        javax.swing.JButton btnVerImagen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblEquipos.setAutoCreateRowSorter(true);
        tblEquipos.setBackground(new java.awt.Color(217, 217, 217));
        tblEquipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "TipoEquipo", "Marca", "Modelo", "N.Serie", "Garantía", "País", "Proveedor", "Fec.Adq.", "IDInterna", "Ubic.", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEquipos.setColumnSelectionAllowed(true);
        tblEquipos.setMinimumSize(new java.awt.Dimension(1007, 558));
        tblEquipos.setPreferredSize(new java.awt.Dimension(1007, 558));
        scrollPanelTabla.setViewportView(tblEquipos);
        tblEquipos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        tblEquipos.getColumnModel().getColumn(9).setCellRenderer(tableCellRendererDate);

        txtID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblListadoDeUsuarios.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        lblListadoDeUsuarios.setText("Listado de Equipos");

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblID.setText("ID");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMarca.setText("Marca");

        btnConsultar.setBackground(new java.awt.Color(66, 153, 255));
        btnConsultar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnBajaReactivar.setBackground(new java.awt.Color(255, 102, 102));
        btnBajaReactivar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnBajaReactivar.setForeground(new java.awt.Color(255, 255, 255));
        btnBajaReactivar.setText("Baja / Alta");
        btnBajaReactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaReactivarActionPerformed(evt);
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

        cbxTipoEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxTipoEquipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblTipoEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTipoEquipo.setText("Tipo de Equipo");

        lblModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblModelo.setText("Modelo");

        txtNSerie.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblNSerie.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNSerie.setText("N. de Serie");

        txtGarantia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblGarantia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGarantia.setText("Garantía");

        cbxPais.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblPais.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPais.setText("País");

        txtProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblProveedor.setText("Proveedor");

        lblFecDesde.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFecDesde.setText("Fec. Desde");

        txtIDInterna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblIdInterna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblIdInterna.setText("ID Interna");

        cbxUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxUbicacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUbicacion.setText("Ubicación");

        btnModificar.setBackground(new java.awt.Color(255, 255, 51));
        btnModificar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
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

        cbxModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblFecHasta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFecHasta.setText("Fec. Hasta");

        cbxEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEstado.setText("Estado");

        btnExportarExcel.setBackground(new java.awt.Color(204, 204, 204));
        btnExportarExcel.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnExportarExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarExcel.setText("Exportar Excel");
        btnExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarExcelActionPerformed(evt);
            }
        });

        btnVerImagen.setBackground(new java.awt.Color(66, 153, 255));
        btnVerImagen.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnVerImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnVerImagen.setText("Ver Imagen");
        btnVerImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBajaReactivar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblUbicacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblIdInterna)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIDInterna, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblNSerie)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addComponent(txtNSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblID))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGarantia)
                                    .addComponent(lblPais))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxPais, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMarca)
                                    .addComponent(lblTipoEquipo)
                                    .addComponent(lblModelo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxTipoEquipo, 0, 202, Short.MAX_VALUE)
                                    .addComponent(cbxMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProveedor)
                                    .addComponent(lblFecHasta)
                                    .addComponent(lblFecDesde))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dcFecDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(dcFecHasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(358, 358, 358)
                                .addComponent(lblListadoDeUsuarios))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVerImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblListadoDeUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxTipoEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipoEquipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMarca)
                            .addComponent(cbxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblModelo)
                            .addComponent(cbxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNSerie))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGarantia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPais))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProveedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(dcFecDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcFecHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblFecDesde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblFecHasta)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIDInterna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIdInterna))
                        .addGap(7, 7, 7)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUbicacion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBajaReactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPanelTabla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBajaReactivar;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxMarca;
    private javax.swing.JComboBox<String> cbxModelo;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JComboBox<String> cbxTipoEquipo;
    private javax.swing.JComboBox<String> cbxUbicacion;
    private com.toedter.calendar.JDateChooser dcFecDesde;
    private com.toedter.calendar.JDateChooser dcFecHasta;
    private javax.swing.JTable tblEquipos;
    private javax.swing.JTextField txtGarantia;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDInterna;
    private javax.swing.JTextField txtNSerie;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtProveedor;
    // End of variables declaration//GEN-END:variables
}
