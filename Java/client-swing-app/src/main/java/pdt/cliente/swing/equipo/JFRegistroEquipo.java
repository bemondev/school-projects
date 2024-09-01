package pdt.cliente.swing.equipo;

import cfc.servidor.DTOs.*;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import org.apache.sshd.common.util.io.IoUtils;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.time.ZoneId;
import java.util.List;

/**
 * Clase que representa la ventana de registro de equipos.
 */
public class JFRegistroEquipo extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFRegistroEquipo instancia;

    /**
     * Archivo de la imagen del equipo.
     */
    private File urlImagen;

    // ============================= Constructor =============================
    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFRegistroEquipo() {
        initComponents();
        cargarComboBoxes();
        cargarComboBoxes();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFRegistroEquipo getInstancia() {
        if (instancia == null) {
            instancia = new JFRegistroEquipo();
        }
        instancia.cargarComboBoxes();
        return instancia;
    }

    /**
     * Método que carga los comboBoxes con los datos de la base de datos.
     */
    private void cargarComboBoxes() {
        // Limpia los comboBoxes
        cbxTipoEquipo.removeAllItems();
        cbxUbicacion.removeAllItems();
        cbxPais.removeAllItems();
        cbxModelo.removeAllItems();
        cbxMarca.removeAllItems();

        // Agrega los items a los comboBoxes
        for (PaisDTO paisDTO : Conexion.rec_pais.obtenerTodo()) {
            if(paisDTO.getEstado() != EstadosEnum.ELIMINADO){
                cbxPais.addItem(paisDTO.getNombre());
            }

        }
        for (UbicacionDTO ubicacionDTO : Conexion.rec_ubicacion.obtenerTodo()) {
            if(ubicacionDTO.getEstado() != EstadosEnum.ELIMINADO){
                cbxUbicacion.addItem(ubicacionDTO.getNombre());
            }

        }
        for (TipoEquipoDTO tipoEquipoDTO : Conexion.rec_tipoEquipo.obtenerTodo()) {
            if(tipoEquipoDTO.getEstado() != EstadosEnum.ELIMINADO){
                cbxTipoEquipo.addItem(tipoEquipoDTO.getNombre());
            }

        }
        for (MarcaDTO marcaDTO : Conexion.rec_marca.obtenerTodo()) {
            if(marcaDTO.getEstado() != EstadosEnum.ELIMINADO){
                cbxMarca.addItem(marcaDTO.getNombre());
            }
        }
    }

    /**
     * Cargar comboBox de modelos.
     */
    private void actualizarModelos() {
        cbxModelo.removeAllItems();
        String marcaSeleccionada = (String) cbxMarca.getSelectedItem();
        if(marcaSeleccionada == null) return;
        MarcaDTO marca = Conexion.rec_marca.obtenerPorNombre(marcaSeleccionada);
        List<ModeloDTO> modelos = Conexion.rec_modelo.obtenerPorMarca(marca.getId());
        for (ModeloDTO modelo : modelos) {
            cbxModelo.addItem(modelo.getNombre());
        }
    }

    // =============================== Eventos ===============================

    /**
     * Método que se ejecuta al presionar el botón "Registrar".<br>
     * Registra un nuevo equipo en la base de datos.
     * @param evt Evento de acción.
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JOptionPane opcion = new JOptionPane();
        opcion.setMessage("¿Seguro que quieres registrar este Equipo?");
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmación", "Confirmar registro", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            if (urlImagen != null) {
                try {

                    EquipoDTO equipo = new EquipoDTO();
                    equipo.setIdInterna(Integer.parseInt(txtIDInterna.getText()));
                    equipo.setNombre(txtNombre.getText());
                    equipo.setNumeroSerie(txtNumSerie.getText());
                    equipo.setFechaAdquisicion(dcFechaAdquisicion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    equipo.setGarantia(txtGarantia.getText());
                    equipo.setProveedor(txtProveedor.getText());

                    equipo.setIdTipoEquipo(Conexion.rec_tipoEquipo.obtenerPorNombre(cbxTipoEquipo.getSelectedItem().toString()).getId());


                    try {
                        FileInputStream fis = new FileInputStream(urlImagen);
                        ImagenDTO imagenDTO = new ImagenDTO();
                        imagenDTO.setNombre("img-" + urlImagen.getName());
//                        imagenDTO.setUrl(IoUtils.toByteArray(fis));
                        imagenDTO = Conexion.rec_imagen.registrar(imagenDTO);
                        equipo.setIdImagen(imagenDTO.getId());
                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                    }

                    equipo.setIdModeloEquipo(Conexion.rec_modelo.obtenerPorNombre(cbxModelo.getSelectedItem().toString()).getId());

                    equipo.setEstado(EstadosEnum.ACTIVO);
                    equipo.setIdUbicacion(Conexion.rec_ubicacion.obtenerPorNombre(cbxUbicacion.getSelectedItem().toString()).getId());

                    equipo.setIdPaisDeOrigen(Conexion.rec_pais.obtenerPorNombre(cbxPais.getSelectedItem().toString()).getId());

                    Conexion.rec_equipo.registrar(equipo);

                    JOptionPane.showMessageDialog(this, "Equipo Registrado.");
                }catch (EntityException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }catch (Exception e) {
                    e.printStackTrace(System.out);
                    JOptionPane.showMessageDialog(this, "Error en el registro.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se ha seleccionado una imagen.");
            }
        }else {
            JOptionPane.showMessageDialog(this, "Se ha cancelado el registro");
        }

    }//GEN-LAST:event_btnRegistrarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Cancelar".<br>
     * Cierra la ventana actual y muestra el dashboard.
     * @param evt Evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        JFDashboard.getInstancia().setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Actualizar Modelo".<br>
     * Actualiza el comboBox de modelos según la marca seleccionada.
     * @param evt Evento de acción.
     */
    private void btnActualizarModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarModeloActionPerformed
        actualizarModelos();
    }//GEN-LAST:event_btnActualizarModeloActionPerformed

    /**
     * Método que se ejecuta al presionar el botón "Examinar".<br>
     * Abre un explorador de archivos para seleccionar una imagen.
     * @param evt Evento de acción.
     */
    private void btnExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        urlImagen = fc.getSelectedFile();
    }//GEN-LAST:event_btnExaminarActionPerformed


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
        javax.swing.JButton btnRegistrar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblTipoDeEquipo = new javax.swing.JLabel();
        javax.swing.JLabel lblMarca = new javax.swing.JLabel();
        javax.swing.JLabel lblModelo = new javax.swing.JLabel();
        txtNumSerie = new javax.swing.JTextField();
        javax.swing.JLabel lblNumSerie = new javax.swing.JLabel();
        txtGarantia = new javax.swing.JTextField();
        javax.swing.JLabel lblGarantia = new javax.swing.JLabel();
        javax.swing.JLabel lblPais = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        javax.swing.JLabel lblProveedor = new javax.swing.JLabel();
        javax.swing.JLabel lblFechaAdquisicion = new javax.swing.JLabel();
        txtIDInterna = new javax.swing.JTextField();
        javax.swing.JLabel lblIDInterna = new javax.swing.JLabel();
        javax.swing.JLabel lblUbicacion = new javax.swing.JLabel();
        javax.swing.JLabel lblImagen = new javax.swing.JLabel();
        cbxTipoEquipo = new javax.swing.JComboBox<>();
        cbxMarca = new javax.swing.JComboBox<>();
        cbxUbicacion = new javax.swing.JComboBox<>();
        cbxPais = new javax.swing.JComboBox<>();
        cbxModelo = new javax.swing.JComboBox<>();
        dcFechaAdquisicion = new com.toedter.calendar.JDateChooser();
        javax.swing.JButton btnActualizarModelo = new javax.swing.JButton();
        javax.swing.JButton btnExaminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(217, 217, 217));

        lblFichaUsuario.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblFichaUsuario.setText("Registrar Equipo");

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

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTipoDeEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTipoDeEquipo.setText("Tipo de Equipo");

        lblMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMarca.setText("Marca");

        lblModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblModelo.setText("Modelo");

        txtNumSerie.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblNumSerie.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNumSerie.setText("Número de Serie");

        txtGarantia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblGarantia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGarantia.setText("Garantía");

        lblPais.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPais.setText("País de origen");

        txtProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblProveedor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblProveedor.setText("Proveedor");

        lblFechaAdquisicion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFechaAdquisicion.setText("Fecha de Adquisición");

        txtIDInterna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblIDInterna.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblIDInterna.setText("Identificación Interna");

        lblUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblUbicacion.setText("Ubicación");

        lblImagen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblImagen.setText("Imagen del Equipo");

        cbxTipoEquipo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxTipoEquipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxUbicacion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxUbicacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxPais.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxModelo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnActualizarModelo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnActualizarModelo.setText("■");
        btnActualizarModelo.setAlignmentY(0.1F);
        btnActualizarModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarModeloActionPerformed(evt);
            }
        });

        btnExaminar.setBackground(new java.awt.Color(38, 34, 249));
        btnExaminar.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        btnExaminar.setForeground(new java.awt.Color(255, 255, 255));
        btnExaminar.setText("Examinar");
        btnExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblModelo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnActualizarModelo))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNumSerie)
                                    .addComponent(lblPais)
                                    .addComponent(lblProveedor)
                                    .addComponent(lblFechaAdquisicion)
                                    .addComponent(lblIDInterna)
                                    .addComponent(lblUbicacion)
                                    .addComponent(lblImagen)
                                    .addComponent(lblMarca)
                                    .addComponent(lblGarantia))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumSerie, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtGarantia, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtProveedor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIDInterna, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbxMarca, javax.swing.GroupLayout.Alignment.TRAILING, 0, 248, Short.MAX_VALUE)
                            .addComponent(cbxUbicacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxPais, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxModelo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 248, Short.MAX_VALUE)
                            .addComponent(dcFechaAdquisicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExaminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblTipoDeEquipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre)
                            .addComponent(cbxTipoEquipo, 0, 248, Short.MAX_VALUE))))
                .addGap(14, 14, 14))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(lblFichaUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblFichaUsuario)
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoDeEquipo)
                    .addComponent(cbxTipoEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMarca)
                    .addComponent(cbxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModelo)
                    .addComponent(cbxModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarModelo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(lblFechaAdquisicion)
                        .addGap(12, 12, 12)
                        .addComponent(lblIDInterna)
                        .addGap(37, 37, 37))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNumSerie))
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
                        .addComponent(dcFechaAdquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIDInterna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUbicacion))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExaminar)
                    .addComponent(lblImagen))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxMarca;
    private javax.swing.JComboBox<String> cbxModelo;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JComboBox<String> cbxTipoEquipo;
    private javax.swing.JComboBox<String> cbxUbicacion;
    private com.toedter.calendar.JDateChooser dcFechaAdquisicion;
    private javax.swing.JTextField txtGarantia;
    private javax.swing.JTextField txtIDInterna;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumSerie;
    private javax.swing.JTextField txtProveedor;
    // End of variables declaration//GEN-END:variables
}
