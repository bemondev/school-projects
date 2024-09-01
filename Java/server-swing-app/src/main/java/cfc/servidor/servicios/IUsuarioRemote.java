package cfc.servidor.servicios;

import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de usuario.
 * Define los métodos de negocio sobre la entidad Usuario.
 */
@Remote
public interface IUsuarioRemote {

    /**
     * Registra un usuario en el sistema.<br>
     * La contraseña se encripta antes de ser almacenada.
     *
     * @param usuarioDTO DTO con los datos del usuario a registrar.
     * @param password   Contraseña del usuario.
     * @return DTO actualizado con el ID y nombre de usuario generado.
     */
    UsuarioDTO registrar(UsuarioDTO usuarioDTO, String password) throws EntityException;

    /**
     * Desactiva (Da de baja) un usuario en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del usuario a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un usuario desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del usuario a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario del usuario a obtener.
     * @return DTO con los datos del usuario.
     */
    UsuarioDTO obtenerPorUsername(String username);

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a obtener.
     * @return DTO con los datos del usuario.
     */
    UsuarioDTO obtenerPorID(Integer id);

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los usuarios.
     */
    List<UsuarioDTO> obtenerTodo();

    /**
     * Actualiza los datos de un usuario en el sistema.
     *
     * @param usuarioDTO DTO con los datos del usuario a actualizar.
     */
    void actualizar(UsuarioDTO usuarioDTO, String password, String nuevaPassword) throws EntityException;

    void actualizarAdmin(UsuarioDTO usuarioDTO) throws EntityException;

    public boolean comprobarContrasenia(String password, UsuarioDTO usuarioDTO);
}

