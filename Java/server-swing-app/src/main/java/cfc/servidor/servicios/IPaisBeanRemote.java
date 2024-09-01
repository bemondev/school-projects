package cfc.servidor.servicios;

import cfc.servidor.DTOs.PaisDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de pais.<br>
 * Define los métodos de negocio sobre la entidad Pais.
 */
@Remote
public interface IPaisBeanRemote {

    /**
     * Registra un pais en el sistema.
     *
     * @param paisDTO DTO con los datos del pais a registrar.
     * @return DTO actualizado con el ID generado.
     */
    PaisDTO registrar(PaisDTO paisDTO) throws EntityException;

    /**
     * Actualiza los datos de un pais en el sistema.
     *
     * @param paisDTO DTO con los datos del pais a actualizar.
     */
    void actualizar(PaisDTO paisDTO) throws EntityException;

    /**
     * Desactiva (Da de baja) un pais en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del pais a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un pais desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del pais a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene todos los países registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los países.
     */
    List<PaisDTO> obtenerTodo();

    /**
     * Obtiene un pais por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre del pais a obtener.
     * @return DTO con los datos del pais encontrado.
     */
    PaisDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene un pais por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del pais a obtener.
     * @return DTO con los datos del pais encontrado.
     */
    PaisDTO obtenerPorID(Integer id);
}

