package pdt.cliente.swing.ubicacion;

import cfc.servidor.DTOs.UbicacionDTO;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;
import pdt.cliente.ExcelManager;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que representa la ventana de listado de ubicaciones.
 */
public class JFListadoUbicacion extends javax.swing.JFrame {

    // ======================== Atributos ========================
    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFListadoUbicacion instancia;


    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFListadoUbicacion() {
        initComponents();
        aplicarFiltros();
        cargarComboBox();
        cargarEventoSeleccion();
    }
    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFListadoUbicacion getInstancia() {
        if (instancia == null) {
            instancia = new JFListadoUbicacion();
        }
        return instancia;
    }

    /**
     * Método que limpia los campos de filtro.
     */
    public void limpiarFiltros() {
        this.txtID.setText("");
        this.txtNombre.setText("");
        this.cbxEstado.setSelectedItem("NINGUNO");
        this.recargarFilas();
        this.aplicarFiltros();
    }

    private void cargarFila(UbicacionDTO ubicacion, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblUbicaciones.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1; // Selecciona la nueva fila
        }

        // Carga los datos de la ubicación en la fila
        modeloTabla.setValueAt(ubicacion.getId(), fila, 0);
        modeloTabla.setValueAt(ubicacion.getNombre(), fila, 1);
        modeloTabla.setValueAt(ubicacion.getInstitucion(), fila, 2);
        modeloTabla.setValueAt(ubicacion.getSector(), fila, 3);
        modeloTabla.setValueAt(ubicacion.getPiso(), fila, 4);
        modeloTabla.setValueAt(ubicacion.getCama(), fila, 5);
        modeloTabla.setValueAt(ubicacion.getNumero(), fila, 6);
        modeloTabla.setValueAt(ubicacion.getEstado().name(), fila, 7);
    }

    /**
     * Método que recarga las filas de la tabla con las ubicaciones en la base de datos.
     */
    public void recargarFilas() {
        // Obtengo el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tblUbicaciones.getModel();

        // Limpia las filas de la tabla
        modelo.setRowCount(0);

        // Obtengo las ubicaciones de la base de datos
        List<UbicacionDTO> ubicaciones = Conexion.rec_ubicacion.obtenerTodo();

        // Agrego las ubicaciones a la tabla
        for (UbicacionDTO ubicacion : ubicaciones) {
            cargarFila(ubicacion, modelo.getRowCount());
        }
    }

    /**
     * Método que aplica los filtros de búsqueda a la tabla.
     */
    public void aplicarFiltros() {
        // Obtengo el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) tblUbicaciones.getModel();

        // Crea un ordenador de filas para el modelo
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);

        // Asigna el ordenador de filas al modelo de la tabla
        this.tblUbicaciones.setRowSorter(sorter);

        //  Crea un mapa de filtros que se aplicarán al ordenador de filas
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Agrega los filtros basados en los campos de texto a la lista de filtros
        filters.add(RowFilter.regexFilter(txtID.getText(), 0)); // Filtrar por id
        filters.add(RowFilter.regexFilter(txtNombre.getText(), 1)); // Filtrar por nombre

        String estado = (String) this.cbxEstado.getSelectedItem();
        if (estado != null && !estado.equalsIgnoreCase("Ninguno")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(estado.toLowerCase()), 7)); // Filtrar por estado
        }

        // Crea un filtro que combina todos los filtros de la lista
        RowFilter<DefaultTableModel, Object> rf = RowFilter.andFilter(filters);

        // Aplica el filtro al ordenador de filas
        sorter.setRowFilter(rf);
    }

    /**
     * Método que carga los items del ComboBox (Estado).
     */
    public void cargarComboBox() {
        // Limpio el ComboBox
        cbxEstado.removeAllItems();

        // Agrego los items
        cbxEstado.addItem("NINGUNO");
        cbxEstado.addItem(EstadosEnum.ACTIVO.name());
        cbxEstado.addItem(EstadosEnum.ELIMINADO.name());
    }


    /**
     * Método que carga el evento de selección de la tabla.<br>
     * Si se selecciona una fila, cambia el mensaje del botón de baja/reactivación.
     */
    private void cargarEventoSeleccion() {
        ListSelectionModel model = this.tblUbicaciones.getSelectionModel();
        model.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Evita que se ejecute más de una vez
                if (this.tblUbicaciones.getSelectedRow() != -1) {
                    int filaSeleccionada = this.tblUbicaciones.getSelectedRow();
                    int id = (int) this.tblUbicaciones.getModel().getValueAt(this.tblUbicaciones.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
                    UbicacionDTO ubicacionDTO = Conexion.rec_ubicacion.obtenerPorID(id);
                    if (ubicacionDTO.getEstado().equals(EstadosEnum.ELIMINADO)) {
                        this.btnBajaReactivar.setText("Reactivar");
                    } else {
                        this.btnBajaReactivar.setText("Baja");
                    }
                }
            }
        });
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón de Modificar.<br>
     * Muestra la ventana de Ficha de Ubicación con los datos de la ubicación
     * seleccionada para modificar.
     *
     * @param evt Evento de acción.
     */
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = this.tblUbicaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una ubicación");
            return;
        }

        int id = (int) this.tblUbicaciones.getModel().getValueAt(this.tblUbicaciones.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
        UbicacionDTO ubicacion = Conexion.rec_ubicacion.obtenerPorID(id);
        JFFichaDeUbicacion fichaDeUbicacion = new JFFichaDeUbicacion(ubicacion);
        fichaDeUbicacion.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnModificarActionPerformed

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
        this.recargarFilas();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de Consultar.<br>
     * Aplica los filtros de búsqueda y recarga las filas de la tabla.
     *
     * @param evt Evento de acción.
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        this.recargarFilas();
        this.aplicarFiltros();
    }//GEN-LAST:event_btnConsultarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de Baja/Reactivar.<br>
     * Desactiva o activa una ubicación seleccionada.
     *
     * @param evt Evento de acción.
     */
    private void btnBajaReactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaReactivarActionPerformed

        // Obtiene la posición seleccionada en la tabla
        int filaSeleccionada = this.tblUbicaciones.getSelectedRow();

        // Si no hay ninguna fila seleccionada, muestra un mensaje de error
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una ubicación");
            return;
        }
        JOptionPane opcion = new JOptionPane();
        opcion.setOptions(new Object[] {"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {


            // Obtiene el ID de la ubicación seleccionada
            int id = (int) this.tblUbicaciones.getModel().getValueAt(this.tblUbicaciones.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);

            // Obtiene la ubicación correspondiente al ID
            UbicacionDTO ubicacion = Conexion.rec_ubicacion.obtenerPorID(id);

            // Sí la ubicación no existe, muestra un mensaje de error
            if (ubicacion == null) {
                JOptionPane.showMessageDialog(this, "La ubicación seleccionada no se encuentra en la base de datos.");
                return;
            }
try{
    // Sí la ubicación está activa, la desactiva. Sí está desactivada, la activa.
    if (ubicacion.getEstado() == EstadosEnum.ACTIVO) {
        ubicacion.setEstado(EstadosEnum.ELIMINADO); // Desactiva la ubicación
        Conexion.rec_ubicacion.actualizar(ubicacion); // Actualiza la ubicación en la base de datos

        // Muestra un mensaje de confirmación
        JOptionPane.showMessageDialog(this, "Ubicación " + ubicacion.getNombre() + " desactivada.");

        // Actualiza la fila de la tabla con el nuevo estado la ubicación
        this.cargarFila(ubicacion, filaSeleccionada);

        // Actualiza el texto del botón
        this.btnBajaReactivar.setText("Reactivar");
    } else if (ubicacion.getEstado() == EstadosEnum.ELIMINADO) {
        ubicacion.setEstado(EstadosEnum.ACTIVO); // Activa la ubicación
        Conexion.rec_ubicacion.actualizar(ubicacion); // Actualiza la ubicación en la base de datos

        // Muestra un mensaje de confirmación
        JOptionPane.showMessageDialog(this, "Ubicación " + ubicacion.getNombre() + " reactivada.");

        // Actualiza la fila de la tabla con el nuevo estado la ubicación
        this.cargarFila(ubicacion, filaSeleccionada);

        // Actualiza el texto del botón
        this.btnBajaReactivar.setText("Baja");
    }
}   catch (EntityException e){
    JOptionPane.showMessageDialog(this, e.getMessage());
}   catch (Exception e){
    JOptionPane.showMessageDialog(this, "Error al actualizar");
}




        }
    }//GEN-LAST:event_btnBajaReactivarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Exportar a Excel".<br>
     * Exporta los datos de la tabla a un archivo de Excel.
     *
     * @param evt Evento de acción.
     */
    private void btnExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExcelActionPerformed
        try {
            ExcelManager.exportarExcel(tblUbicaciones);
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
        tblUbicaciones = new javax.swing.JTable();
        txtID = new javax.swing.JTextField();
        javax.swing.JLabel lblListadoDeUbicaciones = new javax.swing.JLabel();
        javax.swing.JLabel lblID = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JButton btnConsultar = new javax.swing.JButton();
        javax.swing.JButton btnLimpiar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblEstado = new javax.swing.JLabel();
        javax.swing.JButton btnModificar = new javax.swing.JButton();
        cbxEstado = new javax.swing.JComboBox<>();
        btnBajaReactivar = new javax.swing.JButton();
        javax.swing.JButton btnExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblUbicaciones.setAutoCreateRowSorter(true);
        tblUbicaciones.setBackground(new java.awt.Color(217, 217, 217));
        tblUbicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Instituto", "Sector", "Piso", "Cama", "Numero", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUbicaciones.setColumnSelectionAllowed(true);
        tblUbicaciones.setMinimumSize(new java.awt.Dimension(1007, 558));
        tblUbicaciones.setPreferredSize(new java.awt.Dimension(1007, 558));
        scrollPanelTabla.setViewportView(tblUbicaciones);
        tblUbicaciones.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        txtID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblListadoDeUbicaciones.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        lblListadoDeUbicaciones.setText("Listado de Ubicaciones");

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblID.setText("ID");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

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
        lblEstado.setText("Estado");

        btnModificar.setBackground(new java.awt.Color(255, 204, 0));
        btnModificar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        cbxEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnBajaReactivar.setBackground(new java.awt.Color(255, 102, 102));
        btnBajaReactivar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnBajaReactivar.setForeground(new java.awt.Color(255, 255, 255));
        btnBajaReactivar.setText("Baja");
        btnBajaReactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaReactivarActionPerformed(evt);
            }
        });

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
                .addGap(417, 417, 417)
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
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblID))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnBajaReactivar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblListadoDeUbicaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(173, 173, 173)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBajaReactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExportarExcel)
                        .addGap(12, 12, 12))))
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
    private javax.swing.JButton btnBajaReactivar;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JTable tblUbicaciones;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}