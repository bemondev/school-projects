package cfc.servidor.servicios;

import cfc.servidor.DTOs.TipoIntervencionDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de tipo de intervención.<br>
 * Define los métodos de negocio sobre la entidad TipoIntervencion.
 */
@Remote
public interface ITipoIntervencionBeanRemote {

    /**
     * Registra un tipo de intervención en el sistema.
     *
     * @param tipoIntervencionDTO DTO con los datos del tipo de intervención a registrar.
     * @return DTO actualizado con el ID generado.
     */
    TipoIntervencionDTO registrar(TipoIntervencionDTO tipoIntervencionDTO) throws EntityException;

    /**
     * Actualiza los datos de un tipo de intervención en el sistema.
     *
     * @param tipoIntervencionDTO DTO con los datos del tipo de intervención a actualizar.
     */
    void actualizar(TipoIntervencionDTO tipoIntervencionDTO) throws EntityException;

    /**
     * Activa (Da de alta nuevamente) un tipo de intervención desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del tipo de intervención a activar.
     */
    void activar(Integer id);

    /**
     * Desactiva (Da de baja) un tipo de intervención en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del tipo de intervención a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Obtiene un tipo de intervención por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del tipo de intervención a obtener.
     * @return DTO con los datos del tipo de intervención encontrado.
     */
    TipoIntervencionDTO obtenerPorID(Integer id);

    /**
     * Obtiene todos los tipos de intervención registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los tipos de intervención.
     */
    List<TipoIntervencionDTO> obtenerTodo();

    /**
     * Obtiene un tipo de intervención por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre del tipo de intervención a obtener.
     * @return DTO con los datos del tipo de intervención encontrado.
     */
    TipoIntervencionDTO obtenerPorNombre(String nombre);
}
