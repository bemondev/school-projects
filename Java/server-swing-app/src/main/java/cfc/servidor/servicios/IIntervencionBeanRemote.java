package cfc.servidor.servicios;

import cfc.servidor.DTOs.IntervencionDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de intervención.<br>
 * Define los métodos de negocio sobre la entidad Intervención.
 */
@Remote
public interface IIntervencionBeanRemote {

    /**
     * Registra una intervención en el sistema.
     *
     * @param intervencionDTO DTO con los datos de la intervención a registrar.
     * @return DTO actualizado con el ID generado.
     */
    IntervencionDTO registrar(IntervencionDTO intervencionDTO) throws EntityException;

    /**
     * Actualiza los datos de una intervención en el sistema.
     *
     * @param intervencionDTO DTO con los datos de la intervención a actualizar.
     */
    void actualizar(IntervencionDTO intervencionDTO) throws EntityException;

    /**
     * Obtiene una intervención por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id de la intervención a obtener.
     * @return DTO con los datos de la intervención encontrada.
     */
    IntervencionDTO obtenerPorID(Integer id);

    /**
     * Obtiene todas las intervenciones registradas en el sistema.
     *
     * @return Lista de DTO con los datos de las intervenciones.
     */
    List<IntervencionDTO> obtenerTodo();
}