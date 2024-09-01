package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOUsuario;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.entidades.Usuario;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.enumerados.LoginResultEnum;
import cfc.servidor.servicios.ILoginRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 * Implementación del bean remoto de login.
 */
@Stateless
public class LoginBean implements ILoginRemote {

    // ================= Inyecciones de dependencias =================
    @EJB
    private DAOUsuario daoUsuario;

    // ======================= Métodos remotos =======================
    @Override
    public LoginResultEnum verificarCredenciales(String username, String password) {
        // Busca el usuario en la base de datos
        Usuario usuario = this.daoUsuario.obtenerPorUsuario(username);

        // Usuario no encontrado
        if (usuario == null) {
            System.out.println("[LOG-BeanLogin] Usuario '" + username + "' no encontrado");
            return LoginResultEnum.USUARIO_INEXISTENTE;
        }

        // Usuario dado de baja
        if (usuario.getEstado().name().equals(EstadosEnum.ELIMINADO.name())) {
            System.out.println("[LOG-BeanLogin] Usuario '" + username + "' dado de baja");
            return LoginResultEnum.USUARIO_BAJA;
        }

        // Encripta la contraseña ingresada
        String passEncriptada = this.daoUsuario.encriptarConstrasenia(password);

        // Contraseña incorrecta
        if (!usuario.getPassword().equals(passEncriptada)) {
            System.out.println("[LOG-BeanLogin] Contraseña incorrecta para usuario '" + username + "'");
            return LoginResultEnum.CREDENCIALES_INCORRECTAS;
        }


        // Usuario sin validar
        if (usuario.getEstado().name().equals(EstadosEnum.SIN_VALIDAR.name())) {
            System.out.println("[LOG-BeanLogin] Usuario '" + username + "' en espera de validación");
            return LoginResultEnum.USUARIO_NO_VALIDADO;
        }

        // Credenciales correctas
        System.out.println("[LOG-BeanLogin] Inicio correcto para usuario '" + username + "'");
        return LoginResultEnum.INICIO_CORRECTO;
    }

}
