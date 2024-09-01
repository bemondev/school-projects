package pdt.cliente.swing;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.enumerados.PermisosEnum;
import pdt.cliente.App;
import pdt.cliente.Conexion;
import pdt.cliente.swing.equipo.JFListadoEquipos;
import pdt.cliente.swing.equipo.JFRegistroEquipo;
import pdt.cliente.swing.intervencion.JFIngresoIntervencion;
import pdt.cliente.swing.intervencion.JFIntervencionPorFecha;
import pdt.cliente.swing.intervencion.JFIntervencionPorTipoIntervencion;
import pdt.cliente.swing.marca.JFIngresarMarca;
import pdt.cliente.swing.marca.JFListadoMarcas;
import pdt.cliente.swing.modelo.JFIngresarModelo;
import pdt.cliente.swing.modelo.JFListadoModelos;
import pdt.cliente.swing.pais.JFIngresarPais;
import pdt.cliente.swing.pais.JFListadoPaises;
import pdt.cliente.swing.perfil.JFIngresarPerfil;
import pdt.cliente.swing.perfil.JFListadoPerfiles;
import pdt.cliente.swing.tiposEquipos.JFListadoTipoDeEquipo;
import pdt.cliente.swing.tiposEquipos.JFRegistrarTipoEquipo;
import pdt.cliente.swing.tiposIntervenciones.JFListadoTipoIntervencion;
import pdt.cliente.swing.tiposIntervenciones.JFRegistrarTipoIntervencion;
import pdt.cliente.swing.ubicacion.JFListadoMovimientos;
import pdt.cliente.swing.ubicacion.JFListadoUbicacion;
import pdt.cliente.swing.ubicacion.JFRegistrarUbicacion;
import pdt.cliente.swing.usuario.JFListadoUsuarios;
import pdt.cliente.swing.usuario.JFUsuario;

import java.util.List;

/**
 * Clase que representa el Dashboard de la aplicación.
 */
public class JFDashboard extends javax.swing.JFrame {

    // ============================== Atributos ==============================
    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static JFDashboard instancia;

    /**
     * Usuario logueado actualmente.
     */
    private UsuarioDTO usuarioActual;


    // ============================= Constructor =============================

    /**
     * Constructor de la clase.<br>
     * Inicializa los componentes de la ventana.
     */
    private JFDashboard() {
        initComponents();
        setUsername();
        comprobarPermisos();
    }

    public void setUsername() {
        lblUsername.setText(App.getUsuarioLogueado().getNombre() + "." + App.getUsuarioLogueado().getApellido());
    }

    public void comprobarPermisos() {
        PerfilDTO perfil = Conexion.rec_perfil.obtenerPorID(App.getUsuarioLogueado().getIdPerfil());
        List<PermisosEnum> permisos = perfil.getPermisos();

        if (permisos.contains(PermisosEnum.USUARIOS)) {
            UsuarioBtnListado.setEnabled(true);
        } else {
            UsuarioBtnListado.setEnabled(false);
        }
        if (permisos.contains(PermisosEnum.PERFILES)) {
            PerfilesBtnIngreso.setEnabled(true);
            PerfilesBtnListado.setEnabled(true);
        } else {
            PerfilesBtnIngreso.setEnabled(false);
            PerfilesBtnListado.setEnabled(false);
        }

        if (permisos.contains(PermisosEnum.MARCAS)) {
            MarcasBtnIngreso.setEnabled(true);
            MarcasBtnListado.setEnabled(true);
        } else {
            MarcasBtnIngreso.setEnabled(false);
            MarcasBtnListado.setEnabled(false);
        }
        if (permisos.contains(PermisosEnum.PAISES)) {
            PaisesBtnIngreso.setEnabled(true);
            PaisesBtnListado.setEnabled(true);
        } else {
            PaisesBtnIngreso.setEnabled(false);
            PaisesBtnListado.setEnabled(false);
        }
        if (permisos.contains(PermisosEnum.TIPOS_EQUIPOS)) {
            TipoEquipoBtnIngreso.setEnabled(true);
            TipoEquipoBtnListado.setEnabled(true);
        } else {
            TipoEquipoBtnIngreso.setEnabled(false);
            TipoEquipoBtnListado.setEnabled(false);
        }
        if (permisos.contains(PermisosEnum.TIPOS_INTERVENCIONES)) {
            TipoIntervencionBtnIngreso.setEnabled(true);
            TipoIntervencionBtnListado.setEnabled(true);
        } else {
            TipoIntervencionBtnIngreso.setEnabled(false);
            TipoIntervencionBtnListado.setEnabled(false);
        }
    }


    // =============================== Métodos ===============================

    /**
     * Método que devuelve la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de la clase.
     */
    public static JFDashboard getInstancia() {
        if (instancia == null) {
            instancia = new JFDashboard();
        }
        instancia.comprobarPermisos();
        instancia.usuarioActual = App.getUsuarioLogueado();
        return instancia;
    }


    // =============================== Eventos ===============================

    // ------------> Intervenciones <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de intervenciones.
     *
     * @param evt Evento de acción.
     */
    private void IntervencionesBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntervencionesBtnIngresoActionPerformed
        this.setVisible(false);
        JFIngresoIntervencion.getInstancia().setVisible(true);
    }//GEN-LAST:event_IntervencionesBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de intervenciones por fecha.
     *
     * @param evt Evento de acción.
     */
    private void IntervencionesBtnPorFecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntervencionesBtnPorFecActionPerformed
        this.setVisible(false);
        JFIntervencionPorFecha jfIntervencionPorFecha = JFIntervencionPorFecha.getInstancia();
        jfIntervencionPorFecha.limpiarFiltros();
        jfIntervencionPorFecha.setVisible(true);
    }//GEN-LAST:event_IntervencionesBtnPorFecActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de intervenciones por tipo.
     *
     * @param evt Evento de acción.
     */
    private void IntervencionesBtnPorTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntervencionesBtnPorTipoActionPerformed
        this.setVisible(false);
        JFIntervencionPorTipoIntervencion jfIntervencionPorTipoIntervencion = JFIntervencionPorTipoIntervencion.getInstancia();
        jfIntervencionPorTipoIntervencion.limpiarFiltros();
        jfIntervencionPorTipoIntervencion.setVisible(true);
    }//GEN-LAST:event_IntervencionesBtnPorTipoActionPerformed


    // ------------> Usuarios <------------

    /**
     * Método que se ejecuta al presionar el botón de listado de usuarios.
     *
     * @param evt Evento de acción.
     */
    private void UsuarioBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioBtnListadoActionPerformed
        this.setVisible(false); // Ocultar el dashboard
        JFListadoUsuarios jfListadoUsuarios = JFListadoUsuarios.getInstancia();
        jfListadoUsuarios.limpiarFiltros();
        jfListadoUsuarios.setVisible(true); // Mostrar el listado de usuarios
    }//GEN-LAST:event_UsuarioBtnListadoActionPerformed

    /**
     * Método que se ejecuta al presionar el nombre de usuario.
     *
     * @param evt Evento de acción.
     */
    private void lblUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsernameMouseClicked
        this.setVisible(false); // Ocultar el dashboard
        JFUsuario.getInstancia().setVisible(true); // Mostrar el perfil del usuario
    }//GEN-LAST:event_lblUsernameMouseClicked

    // ------------> Tipos de Intervención <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de tipo de intervención.
     *
     * @param evt Evento de acción.
     */
    private void TipoIntervencionBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoIntervencionBtnIngresoActionPerformed
        this.setVisible(false);
        JFRegistrarTipoIntervencion.getInstancia().setVisible(true);
    }//GEN-LAST:event_TipoIntervencionBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de tipo de intervención.
     *
     * @param evt Evento de acción.
     */
    private void TipoIntervencionBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoIntervencionBtnListadoActionPerformed
        this.setVisible(false);
        JFListadoTipoIntervencion jfListadoTipoIntervencion = JFListadoTipoIntervencion.getInstancia();
        jfListadoTipoIntervencion.limpiarFiltros();
        jfListadoTipoIntervencion.setVisible(true);
    }//GEN-LAST:event_TipoIntervencionBtnListadoActionPerformed


    // ------------> Perfiles <------------


    /**
     * Método que se ejecuta al presionar el botón de ingreso de perfiles.
     *
     * @param evt Evento de acción.
     */
    private void PerfilesBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerfilesBtnIngresoActionPerformed
        this.setVisible(false); // Ocultar el dashboard
        JFIngresarPerfil.getInstancia().setVisible(true); // Mostrar el ingreso de perfiles
    }//GEN-LAST:event_PerfilesBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de perfiles.
     *
     * @param evt Evento de acción.
     */
    private void PerfilesBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerfilesBtnListadoActionPerformed
        this.setVisible(false);
        JFListadoPerfiles jfListadoPerfiles = JFListadoPerfiles.getInstancia();
        jfListadoPerfiles.limpiarFiltros();
        jfListadoPerfiles.setVisible(true);
    }//GEN-LAST:event_PerfilesBtnListadoActionPerformed

    // ------------> Paises <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de países.
     *
     * @param evt Evento de acción.
     */
    private void PaisesBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaisesBtnIngresoActionPerformed
        setVisible(false);
        JFIngresarPais.getInstancia().setVisible(true);
    }//GEN-LAST:event_PaisesBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de países.
     *
     * @param evt Evento de acción.
     */
    private void PaisesBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaisesBtnListadoActionPerformed
        setVisible(false);
        JFListadoPaises jfListadoPaises = JFListadoPaises.getInstancia();
        jfListadoPaises.limpiarFiltros();
        jfListadoPaises.setVisible(true);
    }//GEN-LAST:event_PaisesBtnListadoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de baja de países.
     *
     * @param evt Evento de acción.
     */
    private void PaisesBtnBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaisesBtnBajaActionPerformed
//        this.setVisible(false);
    }//GEN-LAST:event_PaisesBtnBajaActionPerformed

    // ------------> Equipos <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de equipos.
     *
     * @param evt Evento de acción.
     */
    private void EquiposBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EquiposBtnIngresoActionPerformed
        this.setVisible(false);
        JFRegistroEquipo.getInstancia().setVisible(true);
    }//GEN-LAST:event_EquiposBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de equipos.
     *
     * @param evt Evento de acción.
     */
    private void EquiposBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EquiposBtnListadoActionPerformed
        this.setVisible(false);
        JFListadoEquipos jfListadoEquipos = JFListadoEquipos.getInstancia();
        jfListadoEquipos.limpiarFiltros();
        jfListadoEquipos.setVisible(true);
    }//GEN-LAST:event_EquiposBtnListadoActionPerformed

    // ------------> Ubicaciones <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de ubicaciones.
     *
     * @param evt Evento de acción.
     */
    private void UbicacionesBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UbicacionesBtnIngresoActionPerformed
        this.setVisible(false);
        JFRegistrarUbicacion.getInstancia().setVisible(true);
    }//GEN-LAST:event_UbicacionesBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de ubicaciones.
     *
     * @param evt Evento de acción.
     */
    private void UbicacionesBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UbicacionesBtnListadoActionPerformed
        this.setVisible(false);
        JFListadoUbicacion jfListadoUbicacion = JFListadoUbicacion.getInstancia();
        jfListadoUbicacion.limpiarFiltros();
        jfListadoUbicacion.setVisible(true);
    }//GEN-LAST:event_UbicacionesBtnListadoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de movimiento de ubicaciones.
     *
     * @param evt Evento de acción.
     */
    private void UbicacionesBtnMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UbicacionesBtnMovimientoActionPerformed
        this.setVisible(false);
        JFListadoMovimientos jfListadoMovimientos = JFListadoMovimientos.getInstancia();
        jfListadoMovimientos.limpiarFiltros();
        jfListadoMovimientos.setVisible(true);
    }//GEN-LAST:event_UbicacionesBtnMovimientoActionPerformed

    // ------------> Marcas <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de marcas.
     *
     * @param evt Evento de acción.
     */
    private void MarcasBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MarcasBtnIngresoActionPerformed
        this.setVisible(false);
        JFIngresarMarca.getInstance().setVisible(true);
    }//GEN-LAST:event_MarcasBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de marcas.
     *
     * @param evt Evento de acción.
     */
    private void MarcasBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MarcasBtnListadoActionPerformed
        this.setVisible(false);
        JFListadoMarcas jfListadoMarcas = JFListadoMarcas.getInstancia();
        jfListadoMarcas.limpiarFiltros();
        jfListadoMarcas.setVisible(true);
    }//GEN-LAST:event_MarcasBtnListadoActionPerformed

    // ------------> Modelos <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de modelos.
     *
     * @param evt Evento de acción.
     */
    private void ModelosBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModelosBtnIngresoActionPerformed
        this.setVisible(false);
        JFIngresarModelo.getInstancia().setVisible(true);
    }//GEN-LAST:event_ModelosBtnIngresoActionPerformed

    private void ModelosBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModelosBtnListadoActionPerformed
         this.setVisible(false);
         JFListadoModelos jfListadoModelos = JFListadoModelos.getInstancia();
         jfListadoModelos.limpiarFiltros();
         jfListadoModelos.setVisible(true);
    }//GEN-LAST:event_ModelosBtnListadoActionPerformed

    // ------------> Tipos de Equipo <------------

    /**
     * Método que se ejecuta al presionar el botón de ingreso de tipos de equipo.
     *
     * @param evt Evento de acción.
     */
    private void TipoEquipoBtnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoEquipoBtnIngresoActionPerformed
        this.setVisible(false);
        JFRegistrarTipoEquipo.getInstancia().setVisible(true);
    }//GEN-LAST:event_TipoEquipoBtnIngresoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de listado de tipos de equipo.
     *
     * @param evt Evento de acción.
     */
    private void TipoEquipoBtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoEquipoBtnListadoActionPerformed
        this.setVisible(false);
        JFListadoTipoDeEquipo jfListadoTipoDeEquipo = JFListadoTipoDeEquipo.getInstancia();
        jfListadoTipoDeEquipo.limpiarFiltros();
        jfListadoTipoDeEquipo.setVisible(true);
    }//GEN-LAST:event_TipoEquipoBtnListadoActionPerformed

    /**
     * Método que se ejecuta al presionar el botón de baja de tipos de equipo.
     *
     * @param evt Evento de acción.
     */
    private void TipoEquipoBtnBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipoEquipoBtnBajaActionPerformed
//        this.setVisible(false);
    }//GEN-LAST:event_TipoEquipoBtnBajaActionPerformed

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
        javax.swing.JLabel lblDashboard = new javax.swing.JLabel();
        javax.swing.JPanel panelIntervenciones = new javax.swing.JPanel();
        javax.swing.JLabel lblIntervenciones = new javax.swing.JLabel();
        IntervencionesBtnIngreso = new javax.swing.JButton();
        IntervencionesBtnPorFec = new javax.swing.JButton();
        javax.swing.JLabel imgIntervenciones = new javax.swing.JLabel();
        IntervencionesBtnPorTipo = new javax.swing.JButton();
        javax.swing.JPanel panelUsuarios = new javax.swing.JPanel();
        javax.swing.JLabel lblUsuarios = new javax.swing.JLabel();
        UsuarioBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgUsuarios = new javax.swing.JLabel();
        javax.swing.JPanel panelTiposIntervencion = new javax.swing.JPanel();
        javax.swing.JLabel lblTiposIntervencion = new javax.swing.JLabel();
        TipoIntervencionBtnIngreso = new javax.swing.JButton();
        TipoIntervencionBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgTiposIntervencion = new javax.swing.JLabel();
        javax.swing.JPanel panelPerfiles = new javax.swing.JPanel();
        javax.swing.JLabel lblPerfiles = new javax.swing.JLabel();
        PerfilesBtnIngreso = new javax.swing.JButton();
        PerfilesBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgPerfiles = new javax.swing.JLabel();
        javax.swing.JPanel panelPaises = new javax.swing.JPanel();
        javax.swing.JLabel lblPaises = new javax.swing.JLabel();
        PaisesBtnIngreso = new javax.swing.JButton();
        PaisesBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgPaises = new javax.swing.JLabel();
        javax.swing.JPanel panelEquipos = new javax.swing.JPanel();
        javax.swing.JLabel lblEquipos = new javax.swing.JLabel();
        EquiposBtnIngreso = new javax.swing.JButton();
        EquiposBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgEquipos = new javax.swing.JLabel();
        javax.swing.JPanel panelUbicaciones = new javax.swing.JPanel();
        javax.swing.JLabel lblUbicaciones = new javax.swing.JLabel();
        UbicacionesBtnIngreso = new javax.swing.JButton();
        UbicacionesBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgUbicaciones = new javax.swing.JLabel();
        UbicacionesBtnMovimiento = new javax.swing.JButton();
        javax.swing.JPanel panelMarcas = new javax.swing.JPanel();
        javax.swing.JLabel lblMarcas = new javax.swing.JLabel();
        MarcasBtnIngreso = new javax.swing.JButton();
        MarcasBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgMarcas = new javax.swing.JLabel();
        ModelosBtnIngreso = new javax.swing.JButton();
        ModelosBtnListado = new javax.swing.JButton();
        javax.swing.JPanel panelTiposEquipo = new javax.swing.JPanel();
        javax.swing.JLabel lblTipoEquipo = new javax.swing.JLabel();
        TipoEquipoBtnIngreso = new javax.swing.JButton();
        TipoEquipoBtnListado = new javax.swing.JButton();
        javax.swing.JLabel imgTiposEquipo = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblDashboard.setFont(new java.awt.Font("Unispace", 0, 48)); // NOI18N
        lblDashboard.setText("Dashboard");

        panelIntervenciones.setBackground(new java.awt.Color(217, 217, 217));

        lblIntervenciones.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblIntervenciones.setText("Intervenciones");

        IntervencionesBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        IntervencionesBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        IntervencionesBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        IntervencionesBtnIngreso.setText("Ingreso");
        IntervencionesBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntervencionesBtnIngresoActionPerformed(evt);
            }
        });

        IntervencionesBtnPorFec.setBackground(new java.awt.Color(132, 132, 132));
        IntervencionesBtnPorFec.setFont(new java.awt.Font("Unispace", 0, 15)); // NOI18N
        IntervencionesBtnPorFec.setForeground(new java.awt.Color(255, 255, 255));
        IntervencionesBtnPorFec.setText("Listado por Fechas");
        IntervencionesBtnPorFec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntervencionesBtnPorFecActionPerformed(evt);
            }
        });

        imgIntervenciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgIntervenciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/handyman_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        IntervencionesBtnPorTipo.setBackground(new java.awt.Color(132, 132, 132));
        IntervencionesBtnPorTipo.setFont(new java.awt.Font("Unispace", 0, 15)); // NOI18N
        IntervencionesBtnPorTipo.setForeground(new java.awt.Color(255, 255, 255));
        IntervencionesBtnPorTipo.setText("Listado por Tipo");
        IntervencionesBtnPorTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntervencionesBtnPorTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelIntervencionesLayout = new javax.swing.GroupLayout(panelIntervenciones);
        panelIntervenciones.setLayout(panelIntervencionesLayout);
        panelIntervencionesLayout.setHorizontalGroup(
            panelIntervencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIntervencionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIntervencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIntervencionesLayout.createSequentialGroup()
                        .addComponent(imgIntervenciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addGroup(panelIntervencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IntervencionesBtnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IntervencionesBtnPorFec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IntervencionesBtnPorTipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIntervencionesLayout.createSequentialGroup()
                        .addComponent(lblIntervenciones)
                        .addGap(121, 121, 121))))
        );
        panelIntervencionesLayout.setVerticalGroup(
            panelIntervencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIntervencionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIntervenciones)
                .addGroup(panelIntervencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIntervencionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IntervencionesBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IntervencionesBtnPorFec, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IntervencionesBtnPorTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIntervencionesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imgIntervenciones, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        panelUsuarios.setBackground(new java.awt.Color(217, 217, 217));

        lblUsuarios.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblUsuarios.setText("Usuarios");

        UsuarioBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        UsuarioBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        UsuarioBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        UsuarioBtnListado.setText("Listado");
        UsuarioBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioBtnListadoActionPerformed(evt);
            }
        });

        imgUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/group_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        javax.swing.GroupLayout panelUsuariosLayout = new javax.swing.GroupLayout(panelUsuarios);
        panelUsuarios.setLayout(panelUsuariosLayout);
        panelUsuariosLayout.setHorizontalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(imgUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuariosLayout.createSequentialGroup()
                        .addComponent(lblUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(77, 77, 77))
                    .addComponent(UsuarioBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        panelUsuariosLayout.setVerticalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(52, 52, 52)
                .addComponent(UsuarioBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(76, 76, 76))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(imgUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelTiposIntervencion.setBackground(new java.awt.Color(217, 217, 217));

        lblTiposIntervencion.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblTiposIntervencion.setText("Tipo Intervención");

        TipoIntervencionBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        TipoIntervencionBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        TipoIntervencionBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        TipoIntervencionBtnIngreso.setText("Ingreso");
        TipoIntervencionBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoIntervencionBtnIngresoActionPerformed(evt);
            }
        });

        TipoIntervencionBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        TipoIntervencionBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        TipoIntervencionBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        TipoIntervencionBtnListado.setText("Listado");
        TipoIntervencionBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoIntervencionBtnListadoActionPerformed(evt);
            }
        });

        imgTiposIntervencion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgTiposIntervencion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        javax.swing.GroupLayout panelTiposIntervencionLayout = new javax.swing.GroupLayout(panelTiposIntervencion);
        panelTiposIntervencion.setLayout(panelTiposIntervencionLayout);
        panelTiposIntervencionLayout.setHorizontalGroup(
            panelTiposIntervencionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiposIntervencionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgTiposIntervencion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTiposIntervencionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TipoIntervencionBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TipoIntervencionBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTiposIntervencionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTiposIntervencion)
                .addGap(59, 59, 59))
        );
        panelTiposIntervencionLayout.setVerticalGroup(
            panelTiposIntervencionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiposIntervencionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTiposIntervencion)
                .addGap(18, 18, 18)
                .addComponent(TipoIntervencionBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(TipoIntervencionBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTiposIntervencionLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(imgTiposIntervencion, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelPerfiles.setBackground(new java.awt.Color(217, 217, 217));

        lblPerfiles.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblPerfiles.setText("Perfiles");

        PerfilesBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        PerfilesBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        PerfilesBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        PerfilesBtnIngreso.setText("Ingreso");
        PerfilesBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerfilesBtnIngresoActionPerformed(evt);
            }
        });

        PerfilesBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        PerfilesBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        PerfilesBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        PerfilesBtnListado.setText("Listado");
        PerfilesBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerfilesBtnListadoActionPerformed(evt);
            }
        });

        imgPerfiles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgPerfiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/location_home_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        javax.swing.GroupLayout panelPerfilesLayout = new javax.swing.GroupLayout(panelPerfiles);
        panelPerfiles.setLayout(panelPerfilesLayout);
        panelPerfilesLayout.setHorizontalGroup(
            panelPerfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPerfilesLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(imgPerfiles, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addGroup(panelPerfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPerfilesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelPerfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PerfilesBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(PerfilesBtnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelPerfilesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPerfiles, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        panelPerfilesLayout.setVerticalGroup(
            panelPerfilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPerfilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPerfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(PerfilesBtnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(PerfilesBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPerfilesLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(imgPerfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPaises.setBackground(new java.awt.Color(217, 217, 217));

        lblPaises.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblPaises.setText("Países");

        PaisesBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        PaisesBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        PaisesBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        PaisesBtnIngreso.setText("Ingreso");
        PaisesBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaisesBtnIngresoActionPerformed(evt);
            }
        });

        PaisesBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        PaisesBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        PaisesBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        PaisesBtnListado.setText("Listado");
        PaisesBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaisesBtnListadoActionPerformed(evt);
            }
        });

        imgPaises.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgPaises.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/flag_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        javax.swing.GroupLayout panelPaisesLayout = new javax.swing.GroupLayout(panelPaises);
        panelPaises.setLayout(panelPaisesLayout);
        panelPaisesLayout.setHorizontalGroup(
            panelPaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPaisesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPaisesLayout.createSequentialGroup()
                        .addComponent(lblPaises)
                        .addGap(161, 161, 161))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPaisesLayout.createSequentialGroup()
                        .addComponent(imgPaises, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addGroup(panelPaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(PaisesBtnListado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(PaisesBtnIngreso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
        );
        panelPaisesLayout.setVerticalGroup(
            panelPaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPaisesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPaises)
                .addGap(18, 18, 18)
                .addComponent(PaisesBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PaisesBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPaisesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgPaises, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelEquipos.setBackground(new java.awt.Color(217, 217, 217));

        lblEquipos.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblEquipos.setText("Equipos");

        EquiposBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        EquiposBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        EquiposBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        EquiposBtnIngreso.setText("Ingreso");
        EquiposBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EquiposBtnIngresoActionPerformed(evt);
            }
        });

        EquiposBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        EquiposBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        EquiposBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        EquiposBtnListado.setText("Listado");
        EquiposBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EquiposBtnListadoActionPerformed(evt);
            }
        });

        imgEquipos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgEquipos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/blood_pressure_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        javax.swing.GroupLayout panelEquiposLayout = new javax.swing.GroupLayout(panelEquipos);
        panelEquipos.setLayout(panelEquiposLayout);
        panelEquiposLayout.setHorizontalGroup(
            panelEquiposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEquiposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgEquipos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEquiposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEquiposLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelEquiposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(EquiposBtnListado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(EquiposBtnIngreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEquiposLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEquipos)
                        .addGap(70, 70, 70)))
                .addGap(22, 22, 22))
        );
        panelEquiposLayout.setVerticalGroup(
            panelEquiposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEquiposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEquipos)
                .addGap(18, 18, 18)
                .addComponent(EquiposBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(EquiposBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEquiposLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(imgEquipos, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelUbicaciones.setBackground(new java.awt.Color(217, 217, 217));

        lblUbicaciones.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblUbicaciones.setText("Ubicaciones");

        UbicacionesBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        UbicacionesBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        UbicacionesBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        UbicacionesBtnIngreso.setText("Ingreso");
        UbicacionesBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UbicacionesBtnIngresoActionPerformed(evt);
            }
        });

        UbicacionesBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        UbicacionesBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        UbicacionesBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        UbicacionesBtnListado.setText("Listado");
        UbicacionesBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UbicacionesBtnListadoActionPerformed(evt);
            }
        });

        imgUbicaciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgUbicaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/location_on_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        UbicacionesBtnMovimiento.setBackground(new java.awt.Color(132, 132, 132));
        UbicacionesBtnMovimiento.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        UbicacionesBtnMovimiento.setForeground(new java.awt.Color(255, 255, 255));
        UbicacionesBtnMovimiento.setText("Movimiento");
        UbicacionesBtnMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UbicacionesBtnMovimientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelUbicacionesLayout = new javax.swing.GroupLayout(panelUbicaciones);
        panelUbicaciones.setLayout(panelUbicacionesLayout);
        panelUbicacionesLayout.setHorizontalGroup(
            panelUbicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUbicacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgUbicaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(panelUbicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UbicacionesBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UbicacionesBtnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(UbicacionesBtnMovimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUbicacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUbicaciones)
                .addGap(77, 77, 77))
        );
        panelUbicacionesLayout.setVerticalGroup(
            panelUbicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbicacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUbicaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelUbicacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgUbicaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelUbicacionesLayout.createSequentialGroup()
                        .addComponent(UbicacionesBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UbicacionesBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UbicacionesBtnMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelMarcas.setBackground(new java.awt.Color(217, 217, 217));

        lblMarcas.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblMarcas.setText("Marcas      Modelos");

        MarcasBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        MarcasBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        MarcasBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        MarcasBtnIngreso.setText("Ingreso");
        MarcasBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MarcasBtnIngresoActionPerformed(evt);
            }
        });

        MarcasBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        MarcasBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        MarcasBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        MarcasBtnListado.setText("Listado");
        MarcasBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MarcasBtnListadoActionPerformed(evt);
            }
        });

        imgMarcas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgMarcas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/receipt_long_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        ModelosBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        ModelosBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        ModelosBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        ModelosBtnIngreso.setText("Ingreso");
        ModelosBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModelosBtnIngresoActionPerformed(evt);
            }
        });

        ModelosBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        ModelosBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        ModelosBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        ModelosBtnListado.setText("Listado");
        ModelosBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModelosBtnListadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMarcasLayout = new javax.swing.GroupLayout(panelMarcas);
        panelMarcas.setLayout(panelMarcasLayout);
        panelMarcasLayout.setHorizontalGroup(
            panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMarcasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgMarcas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMarcasLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(MarcasBtnIngreso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MarcasBtnListado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ModelosBtnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ModelosBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))
                    .addGroup(panelMarcasLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(lblMarcas)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelMarcasLayout.setVerticalGroup(
            panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMarcasLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblMarcas)
                .addGap(18, 18, 18)
                .addGroup(panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModelosBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MarcasBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelMarcasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModelosBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MarcasBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMarcasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelTiposEquipo.setBackground(new java.awt.Color(217, 217, 217));

        lblTipoEquipo.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        lblTipoEquipo.setText("Tipos de Equipos");

        TipoEquipoBtnIngreso.setBackground(new java.awt.Color(0, 222, 115));
        TipoEquipoBtnIngreso.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        TipoEquipoBtnIngreso.setForeground(new java.awt.Color(255, 255, 255));
        TipoEquipoBtnIngreso.setText("Ingreso");
        TipoEquipoBtnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoEquipoBtnIngresoActionPerformed(evt);
            }
        });

        TipoEquipoBtnListado.setBackground(new java.awt.Color(38, 34, 249));
        TipoEquipoBtnListado.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        TipoEquipoBtnListado.setForeground(new java.awt.Color(255, 255, 255));
        TipoEquipoBtnListado.setText("Listado");
        TipoEquipoBtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipoEquipoBtnListadoActionPerformed(evt);
            }
        });

        imgTiposEquipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgTiposEquipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/build_FILL0_wght400_GRAD0_opsz24-svg.png"))); // NOI18N

        javax.swing.GroupLayout panelTiposEquipoLayout = new javax.swing.GroupLayout(panelTiposEquipo);
        panelTiposEquipo.setLayout(panelTiposEquipoLayout);
        panelTiposEquipoLayout.setHorizontalGroup(
            panelTiposEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiposEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgTiposEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTiposEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TipoEquipoBtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(TipoEquipoBtnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTiposEquipoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTipoEquipo)
                .addGap(76, 76, 76))
        );
        panelTiposEquipoLayout.setVerticalGroup(
            panelTiposEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTiposEquipoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgTiposEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelTiposEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTipoEquipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TipoEquipoBtnIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TipoEquipoBtnListado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        lblUsername.setFont(new java.awt.Font("Unispace", 1, 18)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("Usuario.Usuario");
        lblUsername.setToolTipText("");
        lblUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsernameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelUbicaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelPerfiles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelIntervenciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelMarcas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTiposIntervencion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelPaises, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 410, Short.MAX_VALUE)
                            .addComponent(panelEquipos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTiposEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 410, Short.MAX_VALUE)))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(495, 495, 495)
                        .addComponent(lblDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(215, 215, 215)
                        .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))
                .addGap(128, 128, 128))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15)))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelIntervenciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPaises, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTiposIntervencion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEquipos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPerfiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTiposEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMarcas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelUbicaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables de los componentes manejados por el editor de formularios.
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EquiposBtnIngreso;
    private javax.swing.JButton EquiposBtnListado;
    private javax.swing.JButton IntervencionesBtnIngreso;
    private javax.swing.JButton IntervencionesBtnPorFec;
    private javax.swing.JButton IntervencionesBtnPorTipo;
    private javax.swing.JButton MarcasBtnIngreso;
    private javax.swing.JButton MarcasBtnListado;
    private javax.swing.JButton ModelosBtnIngreso;
    private javax.swing.JButton ModelosBtnListado;
    private javax.swing.JButton PaisesBtnIngreso;
    private javax.swing.JButton PaisesBtnListado;
    private javax.swing.JButton PerfilesBtnIngreso;
    private javax.swing.JButton PerfilesBtnListado;
    private javax.swing.JButton TipoEquipoBtnIngreso;
    private javax.swing.JButton TipoEquipoBtnListado;
    private javax.swing.JButton TipoIntervencionBtnIngreso;
    private javax.swing.JButton TipoIntervencionBtnListado;
    private javax.swing.JButton UbicacionesBtnIngreso;
    private javax.swing.JButton UbicacionesBtnListado;
    private javax.swing.JButton UbicacionesBtnMovimiento;
    private javax.swing.JButton UsuarioBtnListado;
    private javax.swing.JLabel lblUsername;
    // End of variables declaration//GEN-END:variables
}
