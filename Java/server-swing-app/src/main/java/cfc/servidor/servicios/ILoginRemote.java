package cfc.servidor.servicios;

import cfc.servidor.enumerados.LoginResultEnum;
import jakarta.ejb.Remote;

/**
 * Interfaz remota del bean de login.
 * Define el método de negocio para verificar las credenciales de un usuario.
 */
@Remote
public interface ILoginRemote {

    /**
     * Verifica las credenciales de un usuario.
     *
     * @param username Nombre de usuario del usuario.
     * @param password Contraseña del usuario.
     * @return Enum con el resultado de la verificación.
     */
    LoginResultEnum verificarCredenciales(String username, String password);
}