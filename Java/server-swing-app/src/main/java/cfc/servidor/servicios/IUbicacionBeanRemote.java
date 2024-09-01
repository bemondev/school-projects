package cfc.servidor.servicios;

import cfc.servidor.DTOs.UbicacionDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de ubicacion.<br>
 * Define los métodos de negocio sobre la entidad Ubicacion.
 */
@Remote
public interface IUbicacionBeanRemote {
    /**
     * Registra una ubicacion en el sistema.
     *
     * @param ubicacionDTO DTO con los datos de la ubicacion a registrar.
     * @return DTO actualizado con el ID generado.
     */
    UbicacionDTO registrar(UbicacionDTO ubicacionDTO) throws EntityException;

    /**
     * Actualiza los datos de una ubicacion en el sistema.
     *
     * @param ubicacionDTO DTO con los datos de la ubicacion a actualizar.
     */
    void actualizar(UbicacionDTO ubicacionDTO) throws EntityException;

    /**
     * Obtiene una ubicacion por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre de la ubicacion a obtener.
     * @return DTO con los datos de la ubicacion encontrada.
     */
    UbicacionDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene una ubicacion por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id ID de la ubicacion a obtener.
     * @return DTO con los datos de la ubicacion encontrada.
     */
    UbicacionDTO obtenerPorID(Integer id);

    /**
     * Obtiene todas las ubicaciones registradas en el sistema.
     *
     * @return Lista de DTO con los datos de las ubicaciones.
     */
    List<UbicacionDTO> obtenerTodo();
}
