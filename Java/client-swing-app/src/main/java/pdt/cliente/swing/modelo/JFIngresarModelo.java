package pdt.cliente.swing.modelo;

import cfc.servidor.DTOs.MarcaDTO;
import cfc.servidor.DTOs.ModeloDTO;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;
import java.util.List;

/**
 * Clase que representa la ventana de ingreso de modelo.
 */
public class JFIngresarModelo extends javax.swing.JFrame {

    // ======================== Atributos ========================
    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFIngresarModelo instancia;

    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFIngresarModelo() {
        initComponents();
        cargarComboBoxes();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFIngresarModelo getInstancia() {
        if (instancia == null) {
            instancia = new JFIngresarModelo();
        }
        instancia.cargarComboBoxes();
        return instancia;
    }

    /**
     * Método que carga los ComboBoxes con los datos necesarios.
     */
    private void cargarComboBoxes() {
        // Limpia los ComboBox
        this.cbxMarca.removeAllItems();

        // Agrega los items al ComboBox de Marca
        List<MarcaDTO> marcas = Conexion.rec_marca.obtenerTodo(); // Obtiene todas las marcas

        this.cbxMarca.addItem("NINGUNA");
        for (MarcaDTO marca : marcas) {
            if(marca.getEstado() != EstadosEnum.ELIMINADO){
                this.cbxMarca.addItem(marca.getNombre().toUpperCase());
            }
        }
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
     * Método que se ejecuta al presionar el botón "Registrar".<br>
     * Registra un nuevo modelo en la base de datos.
     *
     * @param evt Evento de acción.
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String nombre = txtNombre.getText().trim();
        // Chequeo si el campo nombre no está vacío
        if (nombre.isBlank()) {
            JOptionPane.showMessageDialog(this, "Error en el registro \n Ingrese un nombre para el Modelo.");
            return;
        }

        JOptionPane opcion = new JOptionPane();
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            String marcaSeleccionada = (String) cbxMarca.getSelectedItem();
            if(marcaSeleccionada == null) return;
            if (marcaSeleccionada.equalsIgnoreCase("NINGUNA")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una marca a la cual designar el modelo.");
            } else if (!nombre.isBlank()) {
                try {
                    MarcaDTO marca = Conexion.rec_marca.obtenerPorNombre(String.valueOf(cbxMarca.getSelectedItem().toString()));
                    ModeloDTO modelo = new ModeloDTO();
                    modelo.setNombre(nombre);
                    modelo.setEstado(EstadosEnum.ACTIVO);
                    modelo.setIdMarca(marca.getId());
                    Conexion.rec_modelo.registrar(modelo);
                    JOptionPane.showMessageDialog(this, "Modelo registrado.");
                } catch (EntityException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(this, "Error en el registro.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error en el registro \n Ingrese un nombre para el Modelo.");
            }
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
        javax.swing.JLabel lblCartel = new javax.swing.JLabel();
        javax.swing.JButton btnRegistrar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        javax.swing.JLabel lblMarca = new javax.swing.JLabel();
        cbxMarca = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(217, 217, 217));

        lblCartel.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        lblCartel.setText("Ingresar Modelo");

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
        lblNombre.setText("Nombre Modelo");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMarca.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMarca.setText("Marca");

        cbxMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addComponent(lblCartel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNombre)
                                                        .addComponent(lblMarca))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                                        .addComponent(cbxMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(14, 14, 14))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblCartel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNombre))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMarca)
                                        .addComponent(cbxMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
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
                                .addContainerGap()
                                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxMarca;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
