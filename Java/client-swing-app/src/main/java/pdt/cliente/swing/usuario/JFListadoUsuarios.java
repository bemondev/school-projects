package pdt.cliente.swing.usuario;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.enumerados.EstadosEnum;
import pdt.cliente.App;
import pdt.cliente.Conexion;
import pdt.cliente.ExcelManager;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que representa la ventana de listado de usuarios.
 */
public class JFListadoUsuarios extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFListadoUsuarios instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFListadoUsuarios() {
        initComponents(); // Inicializa los componentes de la ventana
        limpiarFiltros(); // Limpia los filtros
        cargarComboBoxes(); // Carga los ComboBox
        recargarFilas(); // Filtra los usuarios
        cargarEventoSeleccion(); // Carga el evento de selección de la tabla
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFListadoUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new JFListadoUsuarios();
        }
        return instancia;
    }

    /**
     * Método que limpia los filtros de búsqueda.
     */
    public void limpiarFiltros() {
        this.txtID.setText("");
        this.txtNombre.setText("");
        this.txtApellido.setText("");
        this.txtUsuario.setText("");
        this.cargarComboBoxes();
        this.recargarFilas();
        this.aplicarFiltros();
    }

    /**
     * Rellena una fila de la tabla con los datos de un usuario.<br>
     * Si la fila no existe, crea una nueva y la selecciona.
     *
     * @param usuario Usuario a cargar.
     * @param fila    Fila a cargar.
     */
    private void cargarFila(UsuarioDTO usuario, int fila) {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblUsuarios.getModel();

        // Si la fila no existe, crea una nueva
        if (modeloTabla.getRowCount() <= fila) {
            modeloTabla.addRow(new Object[]{});
            fila = modeloTabla.getRowCount() - 1; // Selecciona la nueva fila
        }

        // Obtiene el nombre del perfil del usuario
        PerfilDTO perfil = Conexion.rec_perfil.obtenerPorID(usuario.getIdPerfil());
        String nombrePerfil = "SIN PERFIL"; // Si no tiene perfil, se muestra "SIN PERFIL"
        if (perfil != null) {
            nombrePerfil = perfil.getNombre();
        }

        // Carga los datos del usuario en la fila
        modeloTabla.setValueAt(usuario.getId(), fila, 0);
        modeloTabla.setValueAt(usuario.getNombre(), fila, 1);
        modeloTabla.setValueAt(usuario.getApellido(), fila, 2);
        modeloTabla.setValueAt(usuario.getUsername(), fila, 3);
        modeloTabla.setValueAt(usuario.getEmail(), fila, 4);
        modeloTabla.setValueAt(usuario.getFechaNacimiento(), fila, 5);
        modeloTabla.setValueAt(nombrePerfil, fila, 6);
        modeloTabla.setValueAt(usuario.getEstado().name(), fila, 7);
    }

    /**
     * Método que recarga las filas de la tabla con los usuarios de la base de datos.
     */
    public void recargarFilas() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblUsuarios.getModel();

        // Limpia las filas de la tabla
        modeloTabla.setRowCount(0);

        // Obtiene la lista completa de usuarios
        List<UsuarioDTO> usuariosDTO = Conexion.rec_usuario.obtenerTodo();

        // Agrega cada usuario a la tabla
        for (UsuarioDTO usr : usuariosDTO) {
            this.cargarFila(usr, modeloTabla.getRowCount());
        }
    }

    /**
     * Método que aplica los filtros de búsqueda a la tabla.
     */
    public void aplicarFiltros() {
        // Obtiene el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblUsuarios.getModel();

        // Crea un ordenador de filas para el modelo
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);

        // Asigna el ordenador de filas al modelo de la tabla
        this.tblUsuarios.setRowSorter(sorter);

        //  Crea un mapa de filtros que se aplicarán al ordenador de filas
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Agrega los filtros basados en los campos de texto a la lista de filtros
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(this.txtID.getText()), 0)); // Filtrar por ID
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(this.txtNombre.getText()), 1)); // Filtrar por nombre
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(this.txtApellido.getText()), 2)); // Filtrar por apellido
        filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(this.txtUsuario.getText()), 3)); // Filtrar por usuario
        String perfil = (String) this.cbxPerfil.getSelectedItem();
        String estado = (String) this.cbxEstado.getSelectedItem();

        if (perfil != null && !perfil.equalsIgnoreCase("Ninguno")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(perfil.toLowerCase()), 6)); // Filtrar por perfil
        }

        if (estado != null && !estado.equalsIgnoreCase("Ninguno")) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(estado.toLowerCase()), 7)); // Filtrar por estado
        }

        // Crea un filtro que combina todos los filtros de la lista
        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.andFilter(filters);

        // Aplica el filtro al ordenador de filas
        sorter.setRowFilter(rowFilter);
    }

    /**
     * Método que carga los items de los ComboBoxes (Perfiles y Estados).
     */
    private void cargarComboBoxes() {
        // Limpia los ComboBox
        this.cbxPerfil.removeAllItems();
        this.cbxEstado.removeAllItems();

        // Agrega los items al ComboBox de Perfiles
        List<PerfilDTO> perfiles = Conexion.rec_perfil.obtenerTodo(); // Obtiene todos los perfiles

        this.cbxPerfil.addItem("NINGUNO");
        for (PerfilDTO perfil : perfiles) {
            this.cbxPerfil.addItem(perfil.getNombre().toUpperCase());
        }


        // Agrega los items al ComboBox de Estados
        this.cbxEstado.addItem("NINGUNO");
        for (EstadosEnum estado : EstadosEnum.values()) {
            if (estado.name().equals(EstadosEnum.ACTIVO.name())) continue; // No agrega el estado ACTIVO
            this.cbxEstado.addItem(estado.toString());
        }
    }

    /**
     * Método que carga el evento de selección de la tabla.<br>
     * Si se selecciona una fila, cambia el mensaje del botón de baja/reactivación.
     */
    private void cargarEventoSeleccion() {
        ListSelectionModel model = this.tblUsuarios.getSelectionModel();
        model.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Evita que se ejecute más de una vez
                if (this.tblUsuarios.getSelectedRow() != -1) {
                    int filaSeleccionada = this.tblUsuarios.getSelectedRow();
                    int id = (int) this.tblUsuarios.getModel().getValueAt(this.tblUsuarios.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
                    UsuarioDTO usuarioDTO = Conexion.rec_usuario.obtenerPorID(id);
                    if (usuarioDTO.getEstado().equals(EstadosEnum.ELIMINADO)) {
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
     * Método que se ejecuta al presionar el botón de Cancelar.<br>
     * Oculta la ventana actual y muestra la ventana de Dashboard.
     *
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
     * Método que se ejecuta al presionar el botón de Limpiar.<br>
     * Limpia los filtros de búsqueda y recarga las filas de la tabla.
     *
     * @param evt Evento de acción.
     */
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.limpiarFiltros();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    /**
     * Método que se ejecuta al presionar el botón de Modificar.<br>
     * Muestra la ventana de Ficha de Usuario con los datos del usuario
     * seleccionado para modificar.
     *
     * @param evt Evento de acción.
     */
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = this.tblUsuarios.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Usuario");
            return;
        }
        int id = (int) this.tblUsuarios.getModel().getValueAt(this.tblUsuarios.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);
        if(App.getUsuarioLogueado().getId() == id){
            JOptionPane.showMessageDialog(this, "No puede modificar su propio usuario");
            return;
        }
        UsuarioDTO usuarioDTO = Conexion.rec_usuario.obtenerPorID(id);
        JFFichaUsuario fichaDeUsuario = new JFFichaUsuario(usuarioDTO);
        fichaDeUsuario.setVisible(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de Baja/Reactivar.<br>
     * Desactiva o activa el usuario seleccionado.
     *
     * @param evt Evento de acción.
     */
    private void btnBajaReactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaReactivarActionPerformed
        // Obtiene la posición seleccionada en la tabla
        int filaSeleccionada = this.tblUsuarios.getSelectedRow();

        // Si no hay ninguna fila seleccionada, muestra un mensaje de error
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Usuario");
            return;
        }

        // Obtiene el ID del usuario seleccionado
        int id = (int) this.tblUsuarios.getModel().getValueAt(this.tblUsuarios.getRowSorter().convertRowIndexToModel(filaSeleccionada), 0);

        // Obtiene el usuario correspondiente al ID
        UsuarioDTO usuario = Conexion.rec_usuario.obtenerPorID(id);

        // Si el usuario no existe, muestra un mensaje de error
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "El usuario seleccionado no se encuentra en la base de datos.");
            return;
        }

        JOptionPane opcion = new JOptionPane();
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            // Sí el usuario está eliminado, lo activa. Sí no, lo desactiva.
            if (usuario.getEstado() == EstadosEnum.ELIMINADO) {
                Conexion.rec_usuario.activar(id); // Activa el usuario

                // Muestra un mensaje de confirmación
                JOptionPane.showMessageDialog(this, "Usuario " + usuario.getUsername() + " activado.");

                // Actualiza la fila de la tabla con el nuevo estado del usuario
                usuario.setEstado(EstadosEnum.VALIDADO);
                this.btnBajaReactivar.setText("Baja");
                this.cargarFila(usuario, filaSeleccionada);

            } else {
                Conexion.rec_usuario.desactivar(id); // Desactiva el usuario

                // Muestra un mensaje de confirmación
                JOptionPane.showMessageDialog(this, "Usuario " + usuario.getUsername() + " desactivado.");

                // Actualiza la fila de la tabla con el nuevo estado del usuario
                usuario.setEstado(EstadosEnum.ELIMINADO);
                this.btnBajaReactivar.setText("Reactivar");
                this.cargarFila(usuario, filaSeleccionada);
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
            ExcelManager.exportarExcel(tblUsuarios);
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
        tblUsuarios = new javax.swing.JTable();
        txtID = new javax.swing.JTextField();
        javax.swing.JLabel lblListadoDeUsuarios = new javax.swing.JLabel();
        javax.swing.JLabel lblID = new javax.swing.JLabel();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        javax.swing.JLabel lblUsuario = new javax.swing.JLabel();
        javax.swing.JLabel lblPerfil = new javax.swing.JLabel();
        cbxPerfil = new javax.swing.JComboBox<>();
        cbxEstado = new javax.swing.JComboBox<>();
        javax.swing.JLabel lblEstado = new javax.swing.JLabel();
        javax.swing.JButton btnConsultar = new javax.swing.JButton();
        javax.swing.JButton btnLimpiar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JButton btnModificar = new javax.swing.JButton();
        btnBajaReactivar = new javax.swing.JButton();
        javax.swing.JButton btnExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblUsuarios.setAutoCreateRowSorter(true);
        tblUsuarios.setBackground(new java.awt.Color(217, 217, 217));
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellido", "Usuario", "Email", "Fecha", "Perfil", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.setColumnSelectionAllowed(true);
        tblUsuarios.setMinimumSize(new java.awt.Dimension(1007, 558));
        tblUsuarios.setPreferredSize(new java.awt.Dimension(1007, 558));
        scrollPanelTabla.setViewportView(tblUsuarios);
        tblUsuarios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        txtID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblListadoDeUsuarios.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        lblListadoDeUsuarios.setText("Listado de Usuarios");

        lblID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblID.setText("ID");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblApellido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblApellido.setText("Apellido");

        txtApellido.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUsuario.setText("Usuario");

        lblPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPerfil.setText("Perfil");

        cbxPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        btnModificar.setBackground(new java.awt.Color(38, 160, 171));
        btnModificar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnBajaReactivar.setBackground(new java.awt.Color(38, 160, 171));
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
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblID))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblApellido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsuario)
                                    .addComponent(lblPerfil)
                                    .addComponent(lblEstado))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                            .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
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
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblApellido))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPerfil))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado))
                        .addGap(24, 24, 24)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBajaReactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
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
    private javax.swing.JComboBox<String> cbxPerfil;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
