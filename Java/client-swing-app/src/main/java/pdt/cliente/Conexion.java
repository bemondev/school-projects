package pdt.cliente;

import cfc.servidor.servicios.*;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Clase que se encarga de la conexión con el servidor.<br>
 * Maneja los EJB de la aplicación en atributos estáticos.
 */
public class Conexion {

    // ========================= ATRIBUTOS =========================
    // EJBs remotos del servidor
    public static ILoginRemote rec_login;
    public static IUsuarioRemote rec_usuario;
    public static IPerfilBeanRemote rec_perfil;
    public static IUbicacionBeanRemote rec_ubicacion;
    public static ITipoIntervencionBeanRemote rec_tipoIntervencion;
    public static ITipoEquipoBeanRemote rec_tipoEquipo;
    public static IIntervencionBeanRemote rec_intervencion;
    public static IEquipoBeanRemote rec_equipo;
    public static IMovimientoBeanRemote rec_movimiento;
    public static IMarcaBeanRemote rec_marca;
    public static IModeloBeanRemote rec_modelo;
    public static IImagenBeanRemote rec_imagen;
    public static IPaisBeanRemote rec_pais;
    public static IBajaEquipoBeanRemote rec_bajaEquipo;

    // =============================================================
    /*
     * Bloque estático (se ejecuta al cargar la clase).
     * Inicializa los EJB remotos del servidor.
     */
    static {
        java.util.Properties jndiProps = new java.util.Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        try {
            Context context = new InitialContext(jndiProps);
            String jdniLogin = "ejb:/pdt-servidor-1.0-SNAPSHOT/LoginBean!cfc.servidor.servicios.ILoginRemote";
            String jdniUsuario = "ejb:/pdt-servidor-1.0-SNAPSHOT/UsuarioBean!cfc.servidor.servicios.IUsuarioRemote";
            String jdniPerfil = "ejb:/pdt-servidor-1.0-SNAPSHOT/PerfilBean!cfc.servidor.servicios.IPerfilBeanRemote";
            String jdniUbicacion = "ejb:/pdt-servidor-1.0-SNAPSHOT/UbicacionBean!cfc.servidor.servicios.IUbicacionBeanRemote";
            String jdniTipoIntervencion = "ejb:/pdt-servidor-1.0-SNAPSHOT/TipoIntervencionBean!cfc.servidor.servicios.ITipoIntervencionBeanRemote";
            String jdniTipoEquipo = "ejb:/pdt-servidor-1.0-SNAPSHOT/TipoEquipoBean!cfc.servidor.servicios.ITipoEquipoBeanRemote";
            String jdniEquipo = "ejb:/pdt-servidor-1.0-SNAPSHOT/EquipoBean!cfc.servidor.servicios.IEquipoBeanRemote";
            String jdniMovimiento = "ejb:/pdt-servidor-1.0-SNAPSHOT/MovimientoBean!cfc.servidor.servicios.IMovimientoBeanRemote";
            String jdniMarca = "ejb:/pdt-servidor-1.0-SNAPSHOT/MarcaBean!cfc.servidor.servicios.IMarcaBeanRemote";
            String jdniModelo = "ejb:/pdt-servidor-1.0-SNAPSHOT/ModeloBean!cfc.servidor.servicios.IModeloBeanRemote";
            String jdniImagen = "ejb:/pdt-servidor-1.0-SNAPSHOT/ImagenBean!cfc.servidor.servicios.IImagenBeanRemote";
            String jdniPais = "ejb:/pdt-servidor-1.0-SNAPSHOT/PaisBean!cfc.servidor.servicios.IPaisBeanRemote";
            String jdniBajaEquipo = "ejb:/pdt-servidor-1.0-SNAPSHOT/BajaEquipoBean!cfc.servidor.servicios.IBajaEquipoBeanRemote";
            String jdniIntervencion = "ejb:/pdt-servidor-1.0-SNAPSHOT/IntervencionBean!cfc.servidor.servicios.IIntervencionBeanRemote";

            rec_login = (ILoginRemote) context.lookup(jdniLogin);
            rec_usuario = (IUsuarioRemote) context.lookup(jdniUsuario);
            rec_perfil = (IPerfilBeanRemote) context.lookup(jdniPerfil);
            rec_ubicacion = (IUbicacionBeanRemote) context.lookup(jdniUbicacion);
            rec_tipoIntervencion = (ITipoIntervencionBeanRemote) context.lookup(jdniTipoIntervencion);
            rec_tipoEquipo = (ITipoEquipoBeanRemote) context.lookup(jdniTipoEquipo);
            rec_equipo = (IEquipoBeanRemote) context.lookup(jdniEquipo);
            rec_movimiento = (IMovimientoBeanRemote) context.lookup(jdniMovimiento);
            rec_marca = (IMarcaBeanRemote) context.lookup(jdniMarca);
            rec_modelo = (IModeloBeanRemote) context.lookup(jdniModelo);
            rec_imagen = (IImagenBeanRemote) context.lookup(jdniImagen);
            rec_pais = (IPaisBeanRemote) context.lookup(jdniPais);
            rec_bajaEquipo = (IBajaEquipoBeanRemote) context.lookup(jdniBajaEquipo);
            rec_intervencion = (IIntervencionBeanRemote) context.lookup(jdniIntervencion);

        } catch (Exception ex) {
            System.out.println("[LOG-ERROR] Error al conectar con el servidor");
            ex.printStackTrace(System.out);
        }
    }
}
