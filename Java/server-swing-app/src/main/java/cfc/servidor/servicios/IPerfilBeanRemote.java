package cfc.servidor.servicios;

import cfc.servidor.DTOs.PerfilDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de perfil.<br>
 * Define los métodos de negocio sobre la entidad Perfil.
 */
@Remote
public interface IPerfilBeanRemote {

    /**
     * Registra un perfil en el sistema.
     *
     * @param perfilDTO DTO con los datos del perfil a registrar.
     * @return DTO actualizado con el ID generado.
     */
    PerfilDTO registrar(PerfilDTO perfilDTO) throws EntityException;

    /**
     * Actualiza los datos de un perfil en el sistema.
     *
     * @param perfilDTO DTO con los datos del perfil a actualizar.
     */
    void actualizar(PerfilDTO perfilDTO) throws EntityException;

    /**
     * Obtiene un perfil por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre del perfil a obtener.
     * @return DTO con los datos del perfil encontrado.
     */
    PerfilDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene un perfil por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del perfil a obtener.
     * @return DTO con los datos del perfil encontrado.
     */
    PerfilDTO obtenerPorID(Integer id);

    /**
     * Desactiva (Da de baja) un perfil en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del perfil a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un perfil desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del perfil a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene todos los perfiles registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los perfiles.
     */
    List<PerfilDTO> obtenerTodo();
}

