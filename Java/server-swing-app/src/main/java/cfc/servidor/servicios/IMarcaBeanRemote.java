package cfc.servidor.servicios;

import cfc.servidor.DTOs.MarcaDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de marca.<br>
 * Define los métodos de negocio sobre la entidad Marca.
 */
@Remote
public interface IMarcaBeanRemote {
    /**
     * Registra una marca en el sistema.
     *
     * @param marcaDTO DTO con los datos de la marca a registrar.
     * @return DTO actualizado con el ID generado.
     */
    MarcaDTO registrar(MarcaDTO marcaDTO) throws EntityException;

    /**
     * Actualiza los datos de una marca en el sistema.
     *
     * @param marcaDTO DTO con los datos de la marca a actualizar.
     */
    void actualizar(MarcaDTO marcaDTO) throws EntityException;

    /**
     * Desactiva (Da de baja) una marca en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id de la marca a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) una marca desactivada en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id de la marca a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene todas las marcas registradas en el sistema.
     *
     * @return Lista de DTO con los datos de las marcas.
     */
    List<MarcaDTO> obtenerTodo();

    /**
     * Obtiene una marca por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre de la marca a obtener.
     * @return DTO con los datos de la marca encontrada.
     */
    MarcaDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene una marca por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id de la marca a obtener.
     * @return DTO con los datos de la marca encontrada.
     */
    MarcaDTO obtenerPorID(Integer id);
}