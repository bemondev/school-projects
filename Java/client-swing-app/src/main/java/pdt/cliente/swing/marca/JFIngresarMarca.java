package pdt.cliente.swing.marca;

import cfc.servidor.DTOs.MarcaDTO;
import cfc.servidor.entidades.Marca;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import pdt.cliente.Conexion;
import pdt.cliente.swing.JFDashboard;

import javax.swing.*;

/**
 * Clase que representa la ventana de ingreso de marca.
 */
public class JFIngresarMarca extends javax.swing.JFrame {

    // ======================== Atributos ========================

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFIngresarMarca instancia;


    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFIngresarMarca() {
        initComponents();
    }

    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFIngresarMarca getInstance() {
        if (instancia == null) {
            instancia = new JFIngresarMarca();
        }
        return instancia;
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
     * Registra una nueva marca en la base de datos.
     *
     * @param evt Evento de acción.
     */
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String nombreMarca = txtNombre.getText().trim();
        // Chequeo si el campo nombre no está vacío
        if (nombreMarca.isBlank()) {
            JOptionPane.showMessageDialog(this, "Error en el registro \n Ingrese un nombre para la Marca.");
            return;
        }

        JOptionPane opcion = new JOptionPane();
        opcion.setMessage("¿Seguro que quieres registrar esta Marca?");
        opcion.setOptions(new Object[]{"Confirmar", "Cancelar"});
        int respuesta = opcion.showConfirmDialog(this, "Confirmar registro?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                Marca marca = new Marca();
                marca.setNombre(nombreMarca);
                marca.setEstado(EstadosEnum.ACTIVO);
                MarcaDTO dto = new MarcaDTO(marca);
                Conexion.rec_marca.registrar(dto);
                JOptionPane.showMessageDialog(this, "Marca registrada.");
            } catch (EntityException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }catch (Exception e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(this, "Error en el registro.");
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
        javax.swing.JLabel btnCartel = new javax.swing.JLabel();
        javax.swing.JButton btnRegistrar = new javax.swing.JButton();
        javax.swing.JButton btnCancelar = new javax.swing.JButton();
        javax.swing.JLabel lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(217, 217, 217));

        btnCartel.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        btnCartel.setText("Ingresar Marca");

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
        lblNombre.setText("Nombre Marca");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(btnCartel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(btnCartel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
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
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
