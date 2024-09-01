package pdt.cliente.swing.marca;

import cfc.servidor.DTOs.MarcaDTO;
import cfc.servidor.enumerados.EstadosEnum;
import pdt.cliente.Conexion;
import pdt.cliente.ExcelManager;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que representa la ventana de listado de marcas.
 */
public class JFListadoMarcas extends javax.swing.JFrame {
    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFListadoMarcas instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFListadoMarcas() {
        initComponents();
        limpiarFiltros();
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
    public static JFListadoMarcas getInstancia() {
        if (instancia == null) {
            instancia = new JFListadoMarcas();
        }
        return instancia;
    }

    /**
     * Método que limpia los filtros de búsqueda.
     */
    public void limpiarFiltros() {
        txtNombre.setText("");
        cbxEstado.setSelectedItem("NINGUNO");
        recargarFilas();
        aplicarFiltros();
    }

    /**
     * Rellena una fila de la tabla con los datos de una marca.<br>
     * Si la fila no existe, crea una nueva y la selecciona.
     *
     * @param marca Marca a cargar.
     * @param fila  Fila a cargar.
     */
    public void cargarFila(MarcaDTO marca, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblMarcas.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1;
        }

        // Carga los datos de la marca en la fila
        modeloTabla.setValueAt(marca.getId(), fila, 0);
        modeloTabla.setValueAt(marca.getNombre(), fila, 1);
        modeloTabla.setValueAt(marca.getEstado().name(), fila, 2);
    }

    /**
     * Método que carga los items a los ComboBoxes.
     */
    public void cargarComboBoxes() {
        // Limpia el ComboBox de Estados
        cbxEstado.removeAllItems();

        // Agrega los items al ComboBox de Estados
        cbxEstado.addItem("NINGUNO");
        cbxEstado.addItem(EstadosEnum.ACTIVO.toString());
        cbxEstado.addItem(EstadosEnum.ELIMINADO.toString());
    }

    /**
     * Método que aplica los filtros de búsqueda a la tabla.
     */
    public void aplicarFiltros() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblMarcas.getModel();

        // Crea un ordenador de filas para el modelo
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);

        // Asigna el ordenador de filas al modelo de la tabla
        this.tblMarcas.setRowSorter(sorter);

        // Crea un mapa de filtros que se aplicarán al ordenador de filas
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Agrega los filtros basados en los campos de texto a la lista de filtros
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(txtNombre.getText().toLowerCase()), 1)); // Filtrar por nombre

        String estado = (String) cbxEstado.getSelectedItem();
        if (estado != null && !estado.equalsIgnoreCase("Ninguno")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(estado.toLowerCase()), 2)); // Filtrar por estado
        }

        // Crea un filtro que combina los filtros de la lista
        RowFilter<DefaultTableModel, Object> rf = RowFilter.andFilter(filters);

        // Aplica el filtro al ordenador de filas
        sorter.setRowFilter(rf);
    }

    /**
     * Método que recarga las filas de la tabla con las marcas de la base de datos.
     */
    public void recargarFilas() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) tblMarcas.getModel();

        // Limpia las filas de la tabla
        modeloTabla.setRowCount(0);

        // Obtiene la lista completa de marcas
        List<MarcaDTO> marcas = Conexion.rec_marca.obtenerTodo();

        // Agrega cada marca a la tabla
        for (MarcaDTO marca : marcas) {
            this.cargarFila(marca, modeloTabla.getRowCount());
        }
    }

    /**
     * Método que carga el evento de selección de la tabla.<br>
     * Si se selecciona una fila, cambia el mensaje del botón de baja/reactivación.
     */
    private void cargarEventoSeleccion() {
        ListSelectionListener selectionTableListener = e -> {
            if (e.getValueIsAdjusting()) return; // Evita que se ejecute más de una vez
            if (tblMarcas.getSelectedRow() != -1) {
                int filaSeleccionada = tblMarcas.getSelectedRow();
                int id = (int) tblMarcas.getModel().getValueAt(tblMarcas.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
                MarcaDTO marca = Conexion.rec_marca.obtenerPorID(id);
                if (marca.getEstado().equals(EstadosEnum.ELIMINADO)) {
                    btnBajaReactivar.setText("Activar");
                } else {
                    btnBajaReactivar.setText("Baja");
                }
            }
        };
        this.tblMarcas.getSelectionModel().addListSelectionListener(selectionTableListener);
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón "Cancelar".<br>
     * Oculta la ventana actual y muestra el Dashboard.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Consultar".<br>
     * Recarga las filas de la tabla y aplica los filtros de búsqueda.
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
     * Método que se ejecuta al presionar el botón "Baja".<br>
     * Realiza la baja o activación de una marca.
     *
     * @param evt Evento de acción.
     */
    private void btnBajaReactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaReactivarActionPerformed
        int filaSeleccionada = this.tblMarcas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una marca.");
            return;
        }
        int id = (int) this.tblMarcas.getModel().getValueAt(this.tblMarcas.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);

        JOptionPane opcion = new JOptionPane();
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if ((respuesta == JOptionPane.YES_OPTION)) {
            MarcaDTO dto = Conexion.rec_marca.obtenerPorID(id);
            if (dto.getEstado() == EstadosEnum.ACTIVO) {
                Conexion.rec_marca.desactivar(dto.getId());
                dto.setEstado(EstadosEnum.ELIMINADO);
                JOptionPane.showMessageDialog(this, "Marca " + dto.getNombre() + " desactivada.");
            } else if (dto.getEstado() == EstadosEnum.ELIMINADO) {
                Conexion.rec_marca.activar(dto.getId());
                dto.setEstado(EstadosEnum.ACTIVO);
                JOptionPane.showMessageDialog(this, "Marca " + dto.getNombre() + " activada.");
            }
            cargarFila(dto, filaSeleccionada);
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
            ExcelManager.exportarExcel(tblMarcas);
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
        tblMarcas = new javax.swing.JTable();
        javax.swing.JLabel lblListadoDeUsuarios = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        cbxEstado = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblEstado = new javax.swing.JLabel();
        javax.swing.JButton btnConsultar = new javax.swing.JButton();
        javax.swing.JButton btnLimpiar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        btnBajaReactivar = new javax.swing.JButton();
        javax.swing.JButton btnExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblMarcas.setAutoCreateRowSorter(true);
        tblMarcas.setBackground(new java.awt.Color(217, 217, 217));
        tblMarcas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMarcas.setColumnSelectionAllowed(true);
        tblMarcas.setMinimumSize(new java.awt.Dimension(1007, 558));
        tblMarcas.setPreferredSize(new java.awt.Dimension(1007, 558));
        scrollPanelTabla.setViewportView(tblMarcas);
        tblMarcas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        lblListadoDeUsuarios.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        lblListadoDeUsuarios.setText("Listado de Marcas");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        cbxEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEstado.setText("Estado");

        btnConsultar.setBackground(new java.awt.Color(38, 34, 249));
        btnConsultar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(255, 102, 102));
        btnLimpiar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
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

        btnBajaReactivar.setBackground(new java.awt.Color(38, 160, 171));
        btnBajaReactivar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnBajaReactivar.setForeground(new java.awt.Color(255, 255, 255));
        btnBajaReactivar.setText("Baja / Activar");
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
                .addGap(513, 513, 513)
                .addComponent(lblListadoDeUsuarios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExportarExcel))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnBajaReactivar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblListadoDeUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado))
                        .addGap(144, 144, 144)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBajaReactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
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
    private javax.swing.JTable tblMarcas;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
