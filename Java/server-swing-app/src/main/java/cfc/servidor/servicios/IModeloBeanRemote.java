package cfc.servidor.servicios;

import cfc.servidor.DTOs.ModeloDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de modelo.<br>
 * Define los métodos de negocio sobre la entidad Modelo.
 */
@Remote
public interface IModeloBeanRemote {

    /**
     * Registra un modelo en el sistema.
     *
     * @param modeloDTO DTO con los datos del modelo a registrar.
     * @return DTO actualizado con el ID generado.
     */
    ModeloDTO registrar(ModeloDTO modeloDTO) throws EntityException;

    /**
     * Actualiza los datos de un modelo en el sistema.
     *
     * @param modeloDTO DTO con los datos del modelo a actualizar.
     */
    void actualizar(ModeloDTO modeloDTO) throws EntityException;

    /**
     * Desactiva (Da de baja) un modelo en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del modelo a desactivar.
     */

    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un modelo desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del modelo a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene todos los modelos registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los modelos.
     */
    List<ModeloDTO> obtenerTodo();

    /**
     * Obtiene un modelo por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre del modelo a obtener.
     * @return DTO con los datos del modelo encontrado.
     */
    ModeloDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene un modelo por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del modelo a obtener.
     * @return DTO con los datos del modelo encontrado.
     */
    ModeloDTO obtenerPorID(Integer id);

    /**
     * Obtiene todos los modelos de una marca específica.
     *
     * @param id Id de la marca de los modelos a obtener.
     * @return Lista de DTO con los datos de los modelos de la marca especificada.
     */
    List<ModeloDTO> obtenerPorMarca(Integer id);


}
