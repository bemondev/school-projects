package cfc.servidor.servicios;

import cfc.servidor.DTOs.TipoEquipoDTO;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Remote;

import java.util.List;

/**
 * Interfaz remota del bean de tipo de equipo.<br>
 * Define los métodos de negocio sobre la entidad TipoEquipo.
 */
@Remote
public interface ITipoEquipoBeanRemote {

    /**
     * Registra un tipo de equipo en el sistema.
     *
     * @param tipoEquipoDTO DTO con los datos del tipo de equipo a registrar.
     * @return DTO actualizado con el ID generado.
     */
    TipoEquipoDTO registrar(TipoEquipoDTO tipoEquipoDTO) throws EntityException;

    /**
     * Actualiza los datos de un tipo de equipo en el sistema.
     *
     * @param tipoEquipoDTO DTO con los datos del tipo de equipo a actualizar.
     */
    void actualizar(TipoEquipoDTO tipoEquipoDTO) throws EntityException;

    /**
     * Desactiva (Da de baja) un tipo de equipo en el sistema
     * cambiando su estado a Inactivo.
     *
     * @param id Id del tipo de equipo a desactivar.
     */
    void desactivar(Integer id);

    /**
     * Activa (Da de alta nuevamente) un tipo de equipo desactivado en el sistema
     * cambiando su estado a Activo.
     *
     * @param id Id del tipo de equipo a activar.
     */
    void activar(Integer id);

    /**
     * Obtiene un tipo de equipo por su nombre.<br>
     * En caso de no existir, devuelve null.
     *
     * @param nombre Nombre del tipo de equipo a obtener.
     * @return DTO con los datos del tipo de equipo encontrado.
     */
    TipoEquipoDTO obtenerPorNombre(String nombre);

    /**
     * Obtiene un tipo de equipo por su ID.<br>
     * En caso de no existir, devuelve null.
     *
     * @param id Id del tipo de equipo a obtener.
     * @return DTO con los datos del tipo de equipo encontrado.
     */
    TipoEquipoDTO obtenerPorID(Integer id);

    /**
     * Obtiene todos los tipos de equipo registrados en el sistema.
     *
     * @return Lista de DTO con los datos de los tipos de equipo.
     */
    List<TipoEquipoDTO> obtenerTodo();
}